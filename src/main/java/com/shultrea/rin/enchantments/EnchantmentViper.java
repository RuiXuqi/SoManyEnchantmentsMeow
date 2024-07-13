package com.shultrea.rin.enchantments;

import com.shultrea.rin.Interfaces.IConditionalDamage;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_030;
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
	
	public EnchantmentViper(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND})
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
	public int getMinEnchantability(int par1) {
		return 18 + 10 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 42;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.viper;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IConditionalDamage);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(Smc_030.Viper, dmgSource) <= 0) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		int levelViper = EnchantmentHelper.getEnchantmentLevel(Smc_030.Viper, dmgSource);
		float forgeDamage = fEvent.getAmount();
		float FDamage = 0;
		if(fEvent.getEntityLiving().isPotionActive(MobEffects.POISON)) {
			FDamage += 1.25 + 0.75f * levelViper;
		}
		if(fEvent.getEntityLiving().isPotionActive(MobEffects.WITHER)) {
			FDamage += 0.5 + 0.5f * levelViper;
		}
		//System.out.println(FDamage + " - Additional Damage Viper");
		forgeDamage = EnchantmentsUtility.CalculateDamageIgnoreSwipe(forgeDamage + FDamage, 0.0f, 1.00f, 1.0f, attacker, null);
		fEvent.setAmount(forgeDamage);
	}
}