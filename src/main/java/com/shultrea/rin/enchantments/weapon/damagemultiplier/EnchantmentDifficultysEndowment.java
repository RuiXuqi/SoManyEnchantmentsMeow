package com.shultrea.rin.enchantments.weapon.damagemultiplier;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDifficultysEndowment extends EnchantmentBase {
	
	public EnchantmentDifficultysEndowment(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
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

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.difficultysEndowment, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.difficultysEndowment, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.difficultysEndowment, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.difficultysEndowment, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.difficultysEndowment;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void CancelAttack(LivingAttackEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack dmgSource = attacker.getHeldItemMainhand();
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.difficultysEndowment, dmgSource) <= 0) return;
		if(attacker.getEntityWorld().getDifficulty() == EnumDifficulty.PEACEFUL) {
			fEvent.setCanceled(true);
		}
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HandleEnchant(LivingDamageEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.difficultysEndowment, dmgSource);
		if(enchantmentLevel <= 0) return;
		if(attacker.getEntityWorld().getDifficulty() == EnumDifficulty.HARD) {
			fEvent.setAmount(fEvent.getAmount() * (1 + 0.1f * enchantmentLevel));
		}
		else if(attacker.getEntityWorld().getDifficulty() == EnumDifficulty.NORMAL) {

		}
		else if(attacker.getEntityWorld().getDifficulty() == EnumDifficulty.EASY) {
			fEvent.setAmount(fEvent.getAmount() * 0.1f * enchantmentLevel);
		}
	}
}