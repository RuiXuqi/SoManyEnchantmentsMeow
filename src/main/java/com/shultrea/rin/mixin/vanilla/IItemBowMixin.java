package com.shultrea.rin.mixin.vanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemBow.class)
public interface IItemBowMixin {
	
	@Invoker("findAmmo")
	ItemStack invokeFindAmmo(EntityPlayer player);
}