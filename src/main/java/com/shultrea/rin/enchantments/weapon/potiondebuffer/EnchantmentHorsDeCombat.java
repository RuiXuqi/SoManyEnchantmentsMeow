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
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isOnEntityDamagedAltStrong()) return;
		if(attacker == null) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;
		
		if(!attacker.world.isRemote) {
			if(attacker.getRNG().nextFloat() <= 0.2F * (float)level) {
				int index = attacker.getRNG().nextInt(8);
				switch(index) {
					case 0: victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + level * 10, level - 1));
					case 1: victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 + level * 10, level - 1));
					case 2: victim.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 20 + level * 10, level - 1));
					case 3: victim.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20 + level * 10, level - 1));
					case 4: victim.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 + level * 10, level - 1));
					case 5: victim.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 + level * 10, level - 1));
					case 6: victim.addPotionEffect(new PotionEffect(MobEffects.WITHER, 20 + level * 10, level - 1));
					case 7: victim.addPotionEffect(new PotionEffect(MobEffects.POISON, 20 + level * 10, level - 1));
				}
			}
		}
	}
}