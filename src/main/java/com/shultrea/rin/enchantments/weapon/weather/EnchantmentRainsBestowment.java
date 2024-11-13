package com.shultrea.rin.enchantments.weapon.weather;

import com.shultrea.rin.Interfaces.IWeatherEnchantment;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRainsBestowment extends EnchantmentBase implements IWeatherEnchantment {
	
	public EnchantmentRainsBestowment(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.rainsBestowment;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.rainsBestowment;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.rainsBestowment, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.rainsBestowment, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.rainsBestowment;
	}
	
	@SubscribeEvent
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.rainsBestowment, stack);
		if(enchantmentLevel <= 0) return;
		float damage = fEvent.getAmount();
		if(attacker.world.isRaining() && EnchantmentsUtility.entityCanSeeSky(attacker)) {
			float modifiedDamage = EnchantmentsUtility.modifyDamage(damage, 0.2f, 0.80f, 1.0f, enchantmentLevel);
			fEvent.setAmount(modifiedDamage);
		}
		else if(!attacker.world.isRaining() && EnchantmentsUtility.entityCanSeeSky(attacker)) {
			float modifiedDamage = EnchantmentsUtility.modifyDamage(damage, -0.2f, -0.3f, 1.0f, enchantmentLevel);
			fEvent.setAmount(modifiedDamage);
			if(fEvent.getEntity().world.rand.nextInt(500) < 3 + enchantmentLevel) {
				EnchantmentsUtility.setRaining(fEvent.getEntityLiving().getEntityWorld());
			}
			//This is never called, wrong spot in ifelse
			else if(!EnchantmentsUtility.entityCanSeeSky(attacker)) {
				modifiedDamage = EnchantmentsUtility.modifyDamage(damage, 0.0f, -0.5f, 1.0f, enchantmentLevel);
				fEvent.setAmount(modifiedDamage);
			}
		}
	}
}