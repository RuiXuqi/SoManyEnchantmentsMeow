package com.shultrea.rin.enchantments.weapon.weather;

import com.shultrea.rin.Interfaces.IWeatherEnchantment;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentSolsBlessing extends EnchantmentBase implements IWeatherEnchantment {
	
	public EnchantmentSolsBlessing(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.solsBlessing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.solsBlessing;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.solsBlessing, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.solsBlessing, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.solsBlessing;
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity entiti, ItemStack stack, int level) {
		if(!(entiti instanceof EntityLivingBase)) return;
		EntityLivingBase entity = (EntityLivingBase)entiti;
		if(user.world.isDaytime() && EnchantmentsUtility.entityCanSeeSky(user)) {
			if(!entity.isPotionActive(MobEffects.GLOWING))
				entity.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 200, 0, false, false));
			else {
				PotionEffect potion = entity.getActivePotionEffect(MobEffects.GLOWING);
				entity.addPotionEffect(new PotionEffect(potion.getPotion(), potion.getDuration() + 80, potion.getAmplifier(), false, false));
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onHurtEvent(LivingHurtEvent e) {
		if(!EnchantmentBase.isDamageSourceAllowed(e.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)e.getSource().getTrueSource();
		float damage = EnchantmentsUtility.modifyDamageForDaytime(attacker, true, attacker.getHeldItemMainhand(), this);
		e.setAmount(damage + e.getAmount());
	}
}