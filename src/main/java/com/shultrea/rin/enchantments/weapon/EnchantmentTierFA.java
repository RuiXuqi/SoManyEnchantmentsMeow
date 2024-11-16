package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentTierFA extends EnchantmentBase {
	
	/**
	 * Defines the type of damage of the enchantment, 0 = lesserfasp, 1 = advfasp, 2 = supfasp
	 */
	public final int damageType;
	
	public EnchantmentTierFA(String name, Rarity rarity, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
		this.damageType = damageTypeIn;
	}
	
	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case 0:
				return ModConfig.enabled.lesserFireAspect;
			case 1:
				return ModConfig.enabled.advancedFireAspect;
			case 2:
				return ModConfig.enabled.supremeFireAspect;
			default:
				return false;
		}
	}
	
	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case 0:
				return ModConfig.level.lesserFireAspect;
			case 1:
				return ModConfig.level.advancedFireAspect;
			case 2:
				return ModConfig.level.supremeFireAspect;
			default:
				return 2;
		}
	}

	@Override
	public int getMinEnchantability(int level) {
		switch(this.damageType) {
			case 0:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserFireAspect, level);
			case 1:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedFireAspect, level);
			case 2:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeFireAspect, level);
			default:
				return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch(this.damageType) {
			case 0:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserFireAspect, level);
			case 1:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedFireAspect, level);
			case 2:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeFireAspect, level);
			default:
				return 0;
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		switch(this.damageType){
			case 0: return ModConfig.canApply.isItemValid(ModConfig.canApply.lesserFireAspect, stack) && super.canApplyAtEnchantingTable(stack);
			case 1: return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedFireAspect, stack) && super.canApplyAtEnchantingTable(stack);
			case 2: return ModConfig.canApply.isItemValid(ModConfig.canApply.supremeFireAspect, stack) && super.canApplyAtEnchantingTable(stack);
			default:
				return false;
		}
	}

	@Override
	public boolean canApply(ItemStack stack){
		switch(this.damageType){
			case 0: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.lesserFireAspect, stack) || super.canApply(stack);
			case 1: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedFireAspect, stack) || super.canApply(stack);
			case 2: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.supremeFireAspect, stack) || super.canApply(stack);
			default:
				return false;
		}
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case 0:
				return ModConfig.treasure.lesserFireAspect;
			case 1:
				return ModConfig.treasure.advancedFireAspect;
			case 2:
				return ModConfig.treasure.supremeFireAspect;
			default:
				return false;
		}
	}
	//TODO
	
	/**
	 * Called whenever a mob is damaged with an item that has this enchantment on it.
	 */
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(!isEnabled()) return;
		if(target instanceof EntityLivingBase) {
			if(level > 0)
				target.setFire(getFireSeconds(this.damageType) * level);
		}
	}

	public static int getFireSeconds(int tier) {
		switch(tier){
			case 0: return 2;
			case 1: return 8;
			case 2: return 16;
		}
		return 0;
	}
}