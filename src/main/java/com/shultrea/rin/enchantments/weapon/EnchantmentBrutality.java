package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentBrutality extends EnchantmentBase {
	
	public EnchantmentBrutality(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.brutality;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.brutality;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.brutality, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.brutality, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.brutality, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.brutality, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.brutality;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(!ModConfig.enabled.brutality) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		Iterable<ItemStack> iter = victim.getArmorInventoryList();
		int x = 5;
		for(ItemStack item : iter) {
			x--;
		}
		for(ItemStack item : iter) {
			ItemStack armor = item;
			armor.damageItem((int)(armor.getMaxDamage() * (0.0025f * x) + EnchantmentsUtility.RANDOM.nextInt(x + 2)) + 1, victim);
		}
	}
}