package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.Interfaces.IEnchantmentFire;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentTierFlame extends EnchantmentBase implements IEnchantmentFire {

	/**
	 * Defines the type of damage of the enchantment, 0 = lesserfla, 1 = advfla, 2 = supfla
	 */
	public final int damageType;

	public EnchantmentTierFlame(String name, Rarity rarity, EnumEnchantmentType type, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
		this.damageType = damageTypeIn;
		this.type = type;
	}

	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case 0:
				return ModConfig.enabled.lesserFlame;
			case 1:
				return ModConfig.enabled.advancedFlame;
			case 2:
				return ModConfig.enabled.supremeFlame;
			default:
				return false;
		}
	}

	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case 0:
				return ModConfig.level.lesserFlame;
			case 1:
				return ModConfig.level.advancedFlame;
			case 2:
				return ModConfig.level.supremeFlame;
			default:
				return 1;
		}
	}


	@Override
	public int getMinEnchantability(int level) {
		switch(this.damageType) {
			case 0:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserFlame, level);
			case 1:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedFlame, level);
			case 2:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeFlame, level);
			default:
				return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch(this.damageType) {
			case 0:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserFlame, level);
			case 1:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedFlame, level);
			case 2:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeFlame, level);
			default:
				return 0;
		}
	}

	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case 0:
				return ModConfig.treasure.lesserFlame;
			case 1:
				return ModConfig.treasure.advancedFlame;
			case 2:
				return ModConfig.treasure.supremeFlame;
			default:
				return false;
		}
	}

	public static int getFireTicks(int tier) {
		switch(tier){
			case 0: return 2;
			case 1: return 15;
			case 2: return 30;
		}
		return 0;
	}
}