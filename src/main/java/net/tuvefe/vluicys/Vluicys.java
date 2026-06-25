package net.tuvefe.vluicys;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

import net.tuvefe.vluicys.item.ItemClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vluicys implements ModInitializer {
	public static final String MOD_ID = "vluicys";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("i hate java");
		ItemClass.itemRegister();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
