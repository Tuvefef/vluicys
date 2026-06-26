package net.tuvefe.vluicys.shader;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Identifier;
import net.tuvefe.vluicys.Vluicys;
import org.ladysnake.satin.api.event.PostWorldRenderCallback;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;
import org.ladysnake.satin.api.managed.ShaderEffectManager;

import static net.tuvefe.vluicys.common.Common.randomRange;

public class DriedVluicysMushroomShader {
    public static int timerShader = 0;
    public static int timerDelay = 0;

    public static final int EFFECT_DURATION = 1600;
    public static final float PEAK_POINT = 0.3f;

    private static float peak1Intensity = 1.0f;
    private static float peak2Intensity = 1.0f;

    public static void randomEffectShader() {
        peak1Intensity = randomRange(0.3f, 0.5f);
        peak2Intensity = randomRange(0.3f, 0.5f);
    }

    public static float shaderEffectTimer() {
        float progress = timerShader / (float)EFFECT_DURATION;
        float cycle = progress * 2.0f;

        int peakIndex = (int)cycle;
        float t = 1.0f - (cycle % 1.0f);

        float peakStrength = (peakIndex == 0) ? peak1Intensity : peak2Intensity;

        float intensity;
        if (t < PEAK_POINT) {
            float ramp = t / PEAK_POINT;
            intensity = ramp * ramp;
        } else {
            float ramp = (t - PEAK_POINT) / (1.0f - PEAK_POINT);
            intensity = 1.0f - (ramp * ramp);
        }

        return intensity * peakStrength;
    }

    public static final ManagedShaderEffect DRIED_VLUICYS_EFFECT = ShaderEffectManager.getInstance()
            .manage(Identifier.of(Vluicys.MOD_ID, "shaders/post/dvmushroom.json"));

    public static void register() {
        PostWorldRenderCallback.EVENT.register(((camera, tickDelta) -> {
            if (timerDelay == 0 && timerShader > 0) {
                DRIED_VLUICYS_EFFECT.setUniformValue("Intensity", shaderEffectTimer());
                DRIED_VLUICYS_EFFECT.render(tickDelta);
            }
        }));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null)
                return;

            if (timerDelay > 0) {
                timerDelay--;
                return;
            }

            if (timerShader > 0)
                timerShader--;
        });
    }
}
