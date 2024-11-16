package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentHorsDeCombat extends EnchantmentBase {
	
	public EnchantmentHorsDeCombat(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.horsDeCombat;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.horsDeCombat;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.horsDeCombat, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.horsDeCombat, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.horsDeCombat, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.horsDeCombat, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.horsDeCombat;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity victims, ItemStack stack, int level) {
		if(!isEnabled()) return;
		if(!(victims instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)victims;
		if(Math.random() * 100 < 10) {
			if(level == 1 || level == 2) {
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + (level * 10), level - 1));
				victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 + (level * 10), level * 2));
			}
			if(level >= 3) {
				victim.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 20 + (level * 10), level - 1));
				victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 + (level * 10), level - 1));
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + (level * 10), level - 1));
				victim.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20 + (level * 10), level - 3));
			}
		}
	}
}