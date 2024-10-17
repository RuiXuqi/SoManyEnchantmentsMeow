package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.EnchantabilityConfig;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentTrueStrike extends EnchantmentBase {
	//TODO what is this supposed to do? It's referenced in Parry and Evasion
	
	public EnchantmentTrueStrike(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.trueStrike;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.trueStrike;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.trueStrike, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.trueStrike, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.trueStrike;
	}
}