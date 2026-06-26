package net.tuvefe.vluicys.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tuvefe.vluicys.shader.DriedVluicysMushroomShader;

public class DriedVluicysMushroomItem extends Item {
    public DriedVluicysMushroomItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);
        if (world.isClient) {
            DriedVluicysMushroomShader.timerShader = 1700;
            DriedVluicysMushroomShader.timerDelay = 150;
            DriedVluicysMushroomShader.randomEffectShader();
        }

        return result;
    }
}
