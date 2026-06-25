package net.tuvefe.vluicys.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tuvefe.vluicys.shader.VluicysShaderClass;

public class VluicysMushroomItem extends Item {
    public VluicysMushroomItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);
        if (world.isClient) {
            VluicysShaderClass.timerShader = 1200;
            VluicysShaderClass.timerDelay = 150;
        }

        return result;
    }
}