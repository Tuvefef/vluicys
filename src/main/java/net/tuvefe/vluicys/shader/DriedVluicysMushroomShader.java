package net.tuvefe.vluicys.shader;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Identifier;
import net.tuvefe.vluicys.Vluicys;
import org.ladysnake.satin.api.event.PostWorldRenderCallback;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;
import org.ladysnake.satin.api.managed.ShaderEffectManager;

import net.tuvefe.vluicys.common.Common;

public class DriedVluicysMushroomShader extends Shader {
    public static final DriedVluicysMushroomShader INSTANCE = new DriedVluicysMushroomShader();

    public static int timerShader = 0;
    public static int timerDelay = 0;

    public static final int EFFECT_DURATION = 1600;
    public static final float PEAK_POINT = 0.3f;

    @Override
    protected float evolutionTimerShader() {
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

    public static final ManagedShaderEffect DRIED_VLUICYS_SHADER = ShaderEffectManager.getInstance()
            .manage(Identifier.of(Vluicys.MOD_ID, "shaders/post/dvmushroom.json"));

    @Override
    public void register() {
        PostWorldRenderCallback.EVENT.register(((camera, tickDelta) -> {
            if (timerDelay == 0 && timerShader > 0) {
                DRIED_VLUICYS_SHADER.setUniformValue("Intensity", evolutionTimerShader());
                DRIED_VLUICYS_SHADER.setUniformValue("time", Common.secondsTimerShader(timerShader));
                DRIED_VLUICYS_SHADER.render(tickDelta);
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
