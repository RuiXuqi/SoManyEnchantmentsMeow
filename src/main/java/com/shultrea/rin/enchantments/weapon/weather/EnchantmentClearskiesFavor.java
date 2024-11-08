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
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentClearskiesFavor extends EnchantmentBase implements IWeatherEnchantment {
	
	public EnchantmentClearskiesFavor(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.clearskiesFavor;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.clearskiesFavor;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.clearskiesFavor, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.clearskiesFavor, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.clearskiesFavor;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.clearskiesFavor, stack);
		if(enchantmentLevel <= 0) return;
		if(!attacker.world.isRaining() && EnchantmentsUtility.entityCanSeeSky(attacker)) {
			float FDamage = EnchantmentsUtility.modifyDamage(fEvent.getAmount(), 0.5f, 0.75f, 1.00f, enchantmentLevel);
			fEvent.setAmount(FDamage);
		}
		else if(attacker.world.isRaining() || attacker.world.isThundering()) {
			if(Math.random() * 2000 < 3 + (enchantmentLevel * 2)) {
				EnchantmentsUtility.setClearWeather(fEvent.getEntityLiving().getEntityWorld());
			}
			float modifiedDamage = EnchantmentsUtility.modifyDamage(fEvent.getAmount(), 0.0f, -0.6f, 1.0f, enchantmentLevel);
			fEvent.setAmount(modifiedDamage);
		}
	}
}