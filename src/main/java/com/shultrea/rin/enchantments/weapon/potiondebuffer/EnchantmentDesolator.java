package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentDesolator extends EnchantmentBase {
	
	public EnchantmentDesolator(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.desolator;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.desolator;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.desolator, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.desolator, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.desolator, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.desolator, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.desolator;
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isOnEntityDamagedAltStrong()) return;
		if(attacker == null) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;
		
		if(!attacker.world.isRemote) {
			if(attacker.getRNG().nextFloat() <= 0.15F * (float)level) {
				PotionEffect effect = victim.getActivePotionEffect(MobEffects.RESISTANCE);
				int amp = -level;
				if(effect != null) {
					amp = Math.min(effect.getAmplifier(), amp);
				}
				victim.removePotionEffect(MobEffects.RESISTANCE);
				victim.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40 + (level * 10), amp));
				if(level > 2) {
					victim.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 40 + (level * 10), level - 3));
				}
			}
		}
	}
}