package com.shultrea.rin.enchantments;

import com.shultrea.rin.Interfaces.IDamageMultiplier;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_040;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDifficultysEndowment extends EnchantmentBase implements IDamageMultiplier {
	
	public EnchantmentDifficultysEndowment(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.difficultysEndowment;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.difficultysEndowment;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 10 + 10 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 20;
	}
	
	//TODO
	@Override
	public boolean canApply(ItemStack fTest) {
		return fTest.getItem() instanceof ItemSword || super.canApply(fTest);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.difficultysEndowment;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void CancelAttack(LivingAttackEvent fEvent) {
		if(!(EnchantmentsUtility.checkEventCondition(fEvent, this))) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack dmgSource = attacker.getHeldItemMainhand();
		if(EnchantmentHelper.getEnchantmentLevel(Smc_040.difficultyscaled, dmgSource) <= 0) return;
		if(attacker.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
			fEvent.setCanceled(true);
		}
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HandleEnchant(LivingDamageEvent fEvent) {
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(this, dmgSource) <= 0) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		int level = EnchantmentHelper.getEnchantmentLevel(this, dmgSource);
		if(attacker.getEntityWorld().getDifficulty() == EnumDifficulty.HARD) {
			fEvent.setAmount(fEvent.getAmount() * (1 + 0.1f * level));
		}
		else if(attacker.getEntityWorld().getDifficulty() == EnumDifficulty.NORMAL) {
		}
		else if(attacker.getEntityWorld().getDifficulty() == EnumDifficulty.EASY) {
			fEvent.setAmount(fEvent.getAmount() * 0.1f * level);
		}
	}
}