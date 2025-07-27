package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
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
		return ConfigProvider.canItemApply(this, ModConfig.canApply.ascetic, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.ascetic, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.ascetic;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLootingLevelEvent(LootingLevelEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getDamageSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getDamageSource().getTrueSource();
		if(attacker == null) return;
		
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, attacker);
		if(level > 0) {
			if(attacker.world.isRemote) return;
			event.setLootingLevel(Math.max(0, event.getLootingLevel() - level));
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingDrops(LivingDropsEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		if(victim instanceof EntityPlayer) return;

		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, attacker);
		if(level > 0) {
			if(attacker.world.isRemote) return;
			//Don't delete boss drops, just in case
			if(!victim.isNonBoss()) return;
			if(attacker.getRNG().nextFloat() < 0.25F * (float)level){
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onItemFished(ItemFishedEvent event) {
		if(!this.isEnabled()) return;
		EntityLivingBase user = event.getEntityLiving();
		if(user == null) return;

		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, user);
		if(level > 0) {
			if(user.world.isRemote) return;
			if(user.getRNG().nextFloat() < 0.25F * (float)level){
				event.setCanceled(true);
			}
		}
	}
}