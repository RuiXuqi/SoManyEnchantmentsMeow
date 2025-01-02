package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentExtinguish extends EnchantmentCurse {
	
	public EnchantmentExtinguish(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.extinguish;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.extinguish;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.extinguish, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.extinguish, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.extinguish, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.extinguish, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.extinguish;
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(attacker == null) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;
		
		if(!attacker.world.isRemote) {
			target.extinguish();
		}
	}
}