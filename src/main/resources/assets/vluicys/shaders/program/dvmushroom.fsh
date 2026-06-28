#version 150

uniform sampler2D DiffuseSampler;
uniform float Intensity;
uniform float time;

in vec2 texCoord;
out vec4 fragColor;

#define __mag2(x) dot((x), (x))
#define __inverse(x) (1.0 - (x))

vec3 chromaticAberration(vec2 textureCoord, float timeValue)
{
    vec2 centered = textureCoord - 0.5;

    float breath = 0.5 + 0.5 * sin(timeValue * 2.4);
    float redOffset = mix(0.024, 0.044, breath);
    float greenOffset = mix(0.021, 0.041, breath);
    float blueOffset = mix(-0.041, -0.021, breath);

    float r = texture(DiffuseSampler, textureCoord + (centered * vec2(redOffset))).r;
    float g = texture(DiffuseSampler, textureCoord + (centered * vec2(greenOffset))).g;
    float b = texture(DiffuseSampler, textureCoord + (centered * vec2(blueOffset))).b;

    return vec3(r, g, b);
}

float vignette(vec2 textureCoord, const float intensity)
{
    textureCoord *= __inverse(textureCoord);

    float vig = (textureCoord.x * textureCoord.y) * intensity;
    vig = pow(vig, 0.1);

    return vig;
}

void main(void)
{
    vec2 textureCoord = texCoord;
    vec4 renderColor = texture(DiffuseSampler, textureCoord);

    float breathingIntensity = Intensity * (0.7 + 0.3 * sin(time * 2.4));
    vec3 chromaticAberrationColor = chromaticAberration(textureCoord, time);
    vec3 finalRenderColor = mix(renderColor.rgb, chromaticAberrationColor, breathingIntensity);

    float intensity = 13.0;
    finalRenderColor *= vec3(vignette(textureCoord, intensity));

    fragColor = vec4(finalRenderColor, renderColor.a);
}