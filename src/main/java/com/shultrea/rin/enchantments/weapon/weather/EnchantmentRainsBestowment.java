package com.shultrea.rin.enchantments.weapon.weather;

import com.shultrea.rin.Interfaces.IWeatherEnchantment;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRainsBestowment extends EnchantmentBase implements IWeatherEnchantment {
	
	public EnchantmentRainsBestowment(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
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
	
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return super.canApplyTogether(fTest) && !(fTest instanceof IWeatherEnchantment);
//	}
	
	@SubscribeEvent
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.rainsBestowment, stack);
		if(enchantmentLevel <= 0) return;
		float Damage = fEvent.getAmount();
		if(attacker.world.isRaining() && EnchantmentsUtility.noBlockLight(attacker)) {
			float FDamage = EnchantmentsUtility.modifyDamage(Damage, 0.2f, 0.80f, 1.0f, enchantmentLevel);
			fEvent.setAmount(FDamage);
		}
		else if(!attacker.world.isRaining() && EnchantmentsUtility.noBlockLight(attacker)) {
			float Fi = EnchantmentsUtility.modifyDamage(Damage, -0.2f, -0.3f, 1.0f, enchantmentLevel);
			fEvent.setAmount(Fi);
			if(fEvent.getEntity().world.rand.nextInt(500) < 3 + enchantmentLevel) {
				EnchantmentsUtility.Raining(fEvent.getEntityLiving().getEntityWorld());
			}
			else if(!EnchantmentsUtility.noBlockLight(attacker)) {
				float Fin = EnchantmentsUtility.modifyDamage(Damage, 0.0f, -0.5f, 1.0f, enchantmentLevel);
				fEvent.setAmount(Fin);
			}
		}
	}
}