package net.tuvefe.vluicys;

import net.fabricmc.api.ClientModInitializer;
import net.tuvefe.vluicys.shader.DriedVluicysMushroomShader;
import net.tuvefe.vluicys.shader.VluicysMushroomShader;

public class VluicysInitClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VluicysMushroomShader.register();
        DriedVluicysMushroomShader.register();
    }
}
