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
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return super.canApplyTogether(fTest) && !(fTest instanceof IWeatherEnchantment);
//	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.clearskiesFavor, dmgSource);
		if(enchantmentLevel <= 0) return;
		//System.out.println("ClearSky Initiating Damage");
		//Boolean PrintThis = EnchantmentsUtility.noBlockLight(attacker);
		//System.out.println(PrintThis);
		if(!attacker.world.isRaining() && EnchantmentsUtility.noBlockLight(attacker)) {
			//SkyDamage = (enchantmentLevel * 0.90f);
			//float Damage = fEvent.getAmount();
			//fEvent.setAmount(Damage + SkyDamage);
			float FDamage = EnchantmentsUtility.modifyDamage(fEvent.getAmount(), 0.5f, 0.75f, 1.00f, enchantmentLevel);
			fEvent.setAmount(FDamage);
			//System.out.println(SecondDamage);
			//System.out.println(Damage);
		}
		else if(attacker.world.isRaining() || attacker.world.isThundering()) {
			if(Math.random() * 2000 < 3 + (enchantmentLevel * 2)) {
				EnchantmentsUtility.clearSky(fEvent.getEntityLiving().getEntityWorld());
			}
			float Fi = EnchantmentsUtility.modifyDamage(fEvent.getAmount(), 0.0f, -0.6f, 1.0f, enchantmentLevel);
			fEvent.setAmount(Fi);
		}
	}
}