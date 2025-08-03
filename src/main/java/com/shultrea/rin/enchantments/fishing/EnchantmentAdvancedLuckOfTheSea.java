package com.shultrea.rin.enchantments.fishing;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.EnchantmentHelperMixin
 */
public class EnchantmentAdvancedLuckOfTheSea extends EnchantmentBase {
	
	public EnchantmentAdvancedLuckOfTheSea(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	public static int getLevelValue(ItemStack stack) {
		if(!EnchantmentRegistry.advancedLuckOfTheSea.isEnabled()) return 0;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedLuckOfTheSea, stack);
		if(level <= 0) return 0;
		return getLevelMult(level);
	}
	
	public static int getLevelMult(int level) {
		int toReturn = 2 + 2 * level;
		if(Math.random() < 0.25F) toReturn = 3 + 3 * level;
		return toReturn;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedLuckOfTheSea;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedLuckOfTheSea;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedLuckOfTheSea, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedLuckOfTheSea, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.advancedLuckOfTheSea, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.advancedLuckOfTheSea, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedLuckOfTheSea;
	}
}