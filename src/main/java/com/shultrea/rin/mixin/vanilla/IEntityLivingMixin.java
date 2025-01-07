package com.shultrea.rin.mixin.vanilla;

import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityLiving.class)
public interface IEntityLivingMixin {
	
	@Accessor(value = "inventoryHandsDropChances")
	float[] getInventoryHandsDropChances();
}