package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
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
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.brutality, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.brutality;
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isOnEntityDamagedAltStrong()) return;
		if(attacker == null) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;
		
		Iterable<ItemStack> iter = victim.getArmorInventoryList();

		int armorPieceCount = 0;
		for(ItemStack stack : iter) {
			if(!stack.isEmpty()) armorPieceCount++;
		}
		for(ItemStack stack : iter) {
			if(!stack.isEmpty() && stack.isItemStackDamageable()) {
				stack.damageItem((int)(stack.getMaxDamage() * 0.0025F * (float)level / armorPieceCount + victim.getRNG().nextInt(level + 2)) + 1, victim);
			}
		}
	}
}