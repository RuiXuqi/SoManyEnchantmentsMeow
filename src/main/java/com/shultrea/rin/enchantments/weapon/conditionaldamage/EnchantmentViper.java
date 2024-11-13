package com.shultrea.rin.enchantments.weapon.conditionaldamage;

import com.shultrea.rin.Interfaces.IConditionalDamage;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentViper extends EnchantmentBase implements IConditionalDamage {
	
	public EnchantmentViper(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.viper;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.viper;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.viper, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.viper, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.viper, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.viper, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.viper;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = attacker.getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.viper, stack);
		if(enchantmentLevel <= 0) return;
		float forgeDamage = fEvent.getAmount();
		float FDamage = 0;
		if(fEvent.getEntityLiving().isPotionActive(MobEffects.POISON)) {
			FDamage += 1.25f + 0.75f * enchantmentLevel;
		}
		if(fEvent.getEntityLiving().isPotionActive(MobEffects.WITHER)) {
			FDamage += 0.5f + 0.5f * enchantmentLevel;
		}
		//System.out.println(FDamage + " - Additional Damage Viper");

		//This only adds +1 dmg flat, could just do forgeDamage += 1;
		forgeDamage += EnchantmentsUtility.modifyDamage(forgeDamage + FDamage, 0.0f, 1.00f, 1.0f, 1);
		fEvent.setAmount(forgeDamage);
	}
}