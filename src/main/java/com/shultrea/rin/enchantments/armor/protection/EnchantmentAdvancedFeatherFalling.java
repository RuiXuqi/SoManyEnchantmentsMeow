package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.Interfaces.IEnhancedEnchantment;
import com.shultrea.rin.Main_Sector.EnchantabilityConfig;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class EnchantmentAdvancedFeatherFalling extends EnchantmentBase implements IEnhancedEnchantment {
	
	public EnchantmentAdvancedFeatherFalling(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedFeatherFalling;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedFeatherFalling;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedFeatherFalling, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedFeatherFalling, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedFeatherFalling;
	}
	
	//TODO
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		if(Math.random() < 0.35f) return source.canHarmInCreative() ? 0 : (source == DamageSource.FALL ? level * 5 : 0);
		else return source.canHarmInCreative() ? 0 : (source == DamageSource.FALL ? level * 4 : 0);
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Enchantments.FEATHER_FALLING && super.canApplyTogether(fTest);
	}
}