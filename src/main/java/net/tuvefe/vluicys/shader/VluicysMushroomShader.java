package net.tuvefe.vluicys.shader;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Identifier;
import net.tuvefe.vluicys.Vluicys;
import org.ladysnake.satin.api.event.PostWorldRenderCallback;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;
import org.ladysnake.satin.api.managed.ShaderEffectManager;

public class VluicysMushroomShader {
    public static int timerShader = 0;
    public static int timerDelay = 0;

    public static final int EFFECT_DURATION = 1100;
    public static final float PEAK_POINT = 0.3f;

    public static float shaderEffectTimer() {
        float progress = timerShader / (float)EFFECT_DURATION;
        float t = 1.0f - progress;
        float intensity;

        if (t < PEAK_POINT) {
            float ramp = t / PEAK_POINT;
            intensity = ramp * ramp;
        } else {
            float ramp = (t - PEAK_POINT) / (1.0f - PEAK_POINT);
            intensity = 1.0f - (ramp * ramp);
        }

        return intensity;
    }

    public static final ManagedShaderEffect VLUICYS_EFFECT = ShaderEffectManager.getInstance()
            .manage(Identifier.of(Vluicys.MOD_ID, "shaders/post/vmushroom.json"));

    public static void register() {
        PostWorldRenderCallback.EVENT.register((camera, tickDelta) -> {
            if (timerDelay == 0 && timerShader > 0) {
                VLUICYS_EFFECT.setUniformValue("Intensity", shaderEffectTimer());
                VLUICYS_EFFECT.setUniformValue("InstantEffect", 1.0f);
                VLUICYS_EFFECT.render(tickDelta);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null)
                return;

            if (timerDelay > 0) {
                timerDelay--;
                return;
            }

            if (timerShader > 0) {
                timerShader--;
                if (timerShader == 0) {
                    VLUICYS_EFFECT.setUniformValue("InstantEffect", 0.0f);
                }
            }
        });
    }
}