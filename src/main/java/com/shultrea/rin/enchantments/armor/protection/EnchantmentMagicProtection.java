package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.Interfaces.IEnchantmentProtection;
import com.shultrea.rin.Main_Sector.EnchantabilityConfig;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class EnchantmentMagicProtection extends EnchantmentBase implements IEnchantmentProtection {
	
	public EnchantmentMagicProtection(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.magicProtection;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.magicProtection;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.magicProtection, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.magicProtection, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.magicProtection;
	}
	
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 : (source.isMagicDamage() ? level * 2 : 0);
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		if(fTest instanceof EnchantmentProtection) {
			EnchantmentProtection p = (EnchantmentProtection)fTest;
			if(p.protectionType != EnchantmentProtection.Type.FALL) return false;
			else return super.canApplyTogether(fTest) && !(fTest instanceof IEnchantmentProtection);
		}
		return super.canApplyTogether(fTest) && !(fTest instanceof IEnchantmentProtection);
	}
}

