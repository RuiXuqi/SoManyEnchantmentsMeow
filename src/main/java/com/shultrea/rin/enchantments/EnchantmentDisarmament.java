package com.shultrea.rin.enchantments;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_040;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDisarmament extends EnchantmentBase {
	
	public EnchantmentDisarmament(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.disarmament;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.disarmament;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 10 + 20 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 50;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.disarmament;
	}
	
	//TODO
	@SubscribeEvent
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(Smc_040.Disarmament, dmgSource) <= 0) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		int leveldisarm = EnchantmentHelper.getEnchantmentLevel(Smc_040.Disarmament, dmgSource);
		if(Math.random() * 100 < 25) {
			fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 1));
			fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 40 + (leveldisarm * 20), 254));
			if(Math.random() * 100 < (leveldisarm * 5)) {
				EnchantmentsUtility.Disarm(fEvent.getEntityLiving());
			}
		}
	}
}