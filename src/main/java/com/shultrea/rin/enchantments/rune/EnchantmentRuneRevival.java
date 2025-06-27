package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.ItemStackMixin
 */
public class EnchantmentRuneRevival extends EnchantmentBase {
	
	public EnchantmentRuneRevival(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runeRevival;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runeRevival;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.runeRevival, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.runeRevival, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return ConfigProvider.canItemApply(ModConfig.canApply.runeRevival, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.runeRevival, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runeRevival;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
}