package com.shultrea.rin.enchantments;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentInnerBerserk extends EnchantmentBase {
	
	public EnchantmentInnerBerserk(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.CHEST});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.innerBerserk;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.innerBerserk;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.innerBerserk;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOW)
	public void HandleEnchant(LivingDamageEvent fEvent) {
		if(!(EnchantmentsUtility.checkEventCondition(fEvent, this))) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(EnchantmentHelper.getMaxEnchantmentLevel(this, attacker) > 0) {
			if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
				return;
			float defenderHealthPercent = attacker.getHealth() / attacker.getMaxHealth();
			int levelfinish = EnchantmentHelper.getMaxEnchantmentLevel(this, attacker);
			float dmgMod = (1.0f - defenderHealthPercent) * ((levelfinish * 0.05f) + 1.1f);
			dmgMod = 1.0F + dmgMod;
			float Damage = fEvent.getAmount();
			fEvent.setAmount(dmgMod * Damage);
		}
	}
}