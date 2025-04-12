package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Enchantment handling in com.shultrea.rin.properties.ArrowPropertiesHandler
 */
public class EnchantmentTierFlame extends EnchantmentBase {

	/**
	 * Defines the type of damage of the enchantment, 0 = lesserfla, 1 = advfla, 2 = supfla
	 */
	private final int damageType;

	public EnchantmentTierFlame(String name, Rarity rarity, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
		this.damageType = damageTypeIn;
	}

	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case 0: return ModConfig.enabled.lesserFlame;
			case 1: return ModConfig.enabled.advancedFlame;
			case 2: return ModConfig.enabled.supremeFlame;
			default: return false;
		}
	}
	
	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case 0: return ModConfig.level.lesserFlame;
			case 1: return ModConfig.level.advancedFlame;
			case 2: return ModConfig.level.supremeFlame;
			default: return 1;
		}
	}

	@Override
	public int getMinEnchantability(int level) {
		switch(this.damageType) {
			case 0: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserFlame, level);
			case 1: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedFlame, level);
			case 2: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeFlame, level);
			default: return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch(this.damageType) {
			case 0: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserFlame, level);
			case 1: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedFlame, level);
			case 2: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeFlame, level);
			default: return 0;
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		switch(this.damageType) {
			case 0: return ConfigProvider.canItemApply(ModConfig.canApply.lesserFlame, stack) && super.canApplyAtEnchantingTable(stack);
			case 1: return ConfigProvider.canItemApply(ModConfig.canApply.advancedFlame, stack) && super.canApplyAtEnchantingTable(stack);
			case 2: return ConfigProvider.canItemApply(ModConfig.canApply.supremeFlame, stack) && super.canApplyAtEnchantingTable(stack);
			default: return false;
		}
	}

	@Override
	public boolean canApply(ItemStack stack) {
		switch(this.damageType) {
			case 0: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.lesserFlame, stack) || super.canApply(stack);
			case 1: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.advancedFlame, stack) || super.canApply(stack);
			case 2: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.supremeFlame, stack) || super.canApply(stack);
			default: return false;
		}
	}

	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case 0: return ModConfig.treasure.lesserFlame;
			case 1: return ModConfig.treasure.advancedFlame;
			case 2: return ModConfig.treasure.supremeFlame;
			default: return false;
		}
	}
}