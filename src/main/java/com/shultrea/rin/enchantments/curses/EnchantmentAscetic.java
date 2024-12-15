package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAscetic extends EnchantmentCurse {
	
	public EnchantmentAscetic(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.ascetic;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.ascetic;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.ascetic, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.ascetic, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.ascetic, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.ascetic, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.ascetic;
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLootingLevelEvent(LootingLevelEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getDamageSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getDamageSource().getTrueSource();
		if(attacker == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level <= 0) return;
		event.setLootingLevel(Math.max(0,event.getLootingLevel()-level));
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingDrops(LivingDropsEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim instanceof EntityPlayer) return;

		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level <= 0) return;
		if(attacker.getRNG().nextFloat() < level/4.){
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onItemFished(ItemFishedEvent event) {
		if(!this.isEnabled()) return;
		EntityLivingBase user = event.getEntityLiving();
		if(user == null) return;
		ItemStack stack = user.getHeldItemMainhand();
		if(stack.isEmpty()) return;

		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, user);
		if(level <= 0) return;
		if(user.getRNG().nextFloat() < level/4.){
			event.setCanceled(true);
		}
	}
}