package net.tuvefe.vluicys.shader;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Identifier;
import net.tuvefe.vluicys.Vluicys;
import org.ladysnake.satin.api.event.ShaderEffectRenderCallback;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;
import org.ladysnake.satin.api.managed.ShaderEffectManager;

public class VluicysShaderClass
{
    public static int timerShader = 0;
    public static int timerDelay = 0;

    public static float shaderEffectTimer()
    {
        return timerShader / 1100.0f;
    }

    public static final ManagedShaderEffect VLUICYS_EFFECT = ShaderEffectManager.getInstance()
            .manage(Identifier.of(Vluicys.MOD_ID, "shaders/post/common.json"));

    public static void register()
    {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (timerDelay == 0 && timerShader > 0)
            {
                VLUICYS_EFFECT.setUniformValue("Intensity", shaderEffectTimer());
                VLUICYS_EFFECT.render(tickDelta);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (timerDelay > 0)
            {
                timerDelay--;
                return;
            }

            if (timerShader > 0)
            {
                timerShader--;
            }
        });
    }
}