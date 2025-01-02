package com.shultrea.rin.util;

import net.minecraft.item.ItemStack;

public interface IEntityPlayerMixin {
	
	void soManyEnchantments$setLastHeldStack(ItemStack stack);
	void soManyEnchantments$setLastSwapTime(int time);
	void soManyEnchantments$setLastUnsheatheTrigger(int time);
	
	ItemStack soManyEnchantments$getLastHeldStack();
	int soManyEnchantments$getLastSwapTime();
	int soManyEnchantments$getLastUnsheatheTrigger();
}