package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.Interfaces.IEnchantmentProtection;
import com.shultrea.rin.Interfaces.IEnhancedEnchantment;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EnchantmentAdvancedProjectileProtection extends EnchantmentBase implements IEnchantmentProtection, IEnhancedEnchantment {
	
	public EnchantmentAdvancedProjectileProtection(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedProjectileProtection;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedProjectileProtection;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedProjectileProtection, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedProjectileProtection, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedProjectileProtection, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedProjectileProtection, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedProjectileProtection;
	}
	
	//TODO
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 : source.isProjectile() ? level * 3 : 0;
	}
}