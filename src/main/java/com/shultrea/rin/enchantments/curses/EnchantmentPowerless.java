package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Enchantment handling in com.shultrea.rin.properties.ArrowPropertiesHandler
 */
public class EnchantmentPowerless extends EnchantmentCurse {
	
	public EnchantmentPowerless(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.powerless;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.powerless;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.powerless, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.powerless, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.powerless, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.powerless, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.powerless;
	}
}