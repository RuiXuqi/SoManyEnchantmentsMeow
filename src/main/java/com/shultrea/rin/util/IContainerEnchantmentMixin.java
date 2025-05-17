package com.shultrea.rin.util;

import net.minecraft.item.ItemStack;

public interface IContainerEnchantmentMixin {
	
	ItemStack soManyEnchantments$getUpgradeTokenCost(int slot);
	
	int soManyEnchantments$getBookshelfPower();

	boolean soManyEnchantments$getTokenIsLapis();
}