package net.tuvefe.vluicys;

import net.fabricmc.api.ClientModInitializer;
import net.tuvefe.vluicys.shader.VluicysShaderClass;

public class VluicysInitClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        VluicysShaderClass.register();
    }
}
