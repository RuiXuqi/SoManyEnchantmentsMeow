package com.shultrea.rin.util;

import net.minecraft.item.ItemStack;

public interface IContainerEnchantmentMixin {

	int soManyEnchantments$getBookshelfPower();

	boolean soManyEnchantments$getTokenIsLapis();

	ItemStack soManyEnchantments$getUpgradeTokenCost(int slot);
	boolean soManyEnchantments$getIsValidAndEnoughToken(int id);
}