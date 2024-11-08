package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.enchantments.bow.EnchantmentAdvancedPower;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * Enchantment arrow power handled in;
 * com.shultrea.rin.mixin.vanilla.ItemBowMixin
 * com.shultrea.rin.mixin.vanilla.EntityArrowMixin
 */
public class EnchantmentPowerless extends EnchantmentCurse {
	
	public EnchantmentPowerless(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
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
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.powerless;
	}
}