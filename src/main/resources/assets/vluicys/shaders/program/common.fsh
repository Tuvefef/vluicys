#version 150

uniform sampler2D DiffuseSampler;
uniform float Intensity;

in vec2 texCoord;
out vec4 fragColor;

#define __mag2(x) dot((x), (x))

vec3 chromaticAberration(vec2 textureCoord)
{
    vec2 centered = textureCoord - 0.5;

    float redOffset = 0.039;
    float greenOffset = 0.036;
    float blueOffset = -0.036;

    float r = texture(DiffuseSampler, textureCoord + (centered * vec2(redOffset))).r;
    float g = texture(DiffuseSampler, textureCoord + (centered * vec2(greenOffset))).g;
    float b = texture(DiffuseSampler, textureCoord + (centered * vec2(blueOffset))).b;

    return vec3(r, g, b);
}

void main(void)
{
    vec2 textureCoord = texCoord;
    vec4 renderColor = texture(DiffuseSampler, textureCoord);

    vec3 chromaticAberrationColor = chromaticAberration(textureCoord);
    vec3 finalRenderColor = mix(renderColor.rgb, chromaticAberrationColor, Intensity);

    fragColor = vec4(finalRenderColor, renderColor.a);
}