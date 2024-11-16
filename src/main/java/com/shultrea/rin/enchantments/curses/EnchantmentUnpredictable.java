package com.shultrea.rin.enchantments.curses;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.utility_sector.CompatUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentUnpredictable extends EnchantmentCurse {
	
	public EnchantmentUnpredictable(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.unpredictable;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.unpredictable;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.unpredictable, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.unpredictable, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.unpredictable, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.unpredictable, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.unpredictable;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onLivingDamageEvent(LivingDamageEvent event) {
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getSource().getTrueSource() instanceof EntityPlayer && CompatUtil.isRLCombatLoaded()) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.unpredictable, stack);
		if(level > 0) {
			float damage = attacker.getRNG().nextFloat() * ((float)level + 1.0F) * event.getAmount();
			event.setAmount(damage);
			if(attacker.getRNG().nextFloat() < (float)level * 0.2F) {
				event.setCanceled(true);
				if(event.getEntityLiving() != null) {
					event.getEntityLiving().heal(damage);
				}
			}
		}
	}
	
	@Optional.Method(modid = "bettercombatmod")
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void modifyDamageEventPre(RLCombatModifyDamageEvent.Pre event) {
		if(event.getEntityPlayer() == null || event.getTarget() == null || event.getStack().isEmpty() || !(event.getTarget() instanceof EntityLivingBase)) return;
		
		EntityPlayer player = event.getEntityPlayer();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.unpredictable, event.getStack());
		if(level > 0) {
			float damage = player.getRNG().nextFloat() * ((float)level + 1.0F) * (event.getBaseDamage() + event.getDamageModifier());
			event.setDamageModifier(damage - event.getBaseDamage());
			if(player.getRNG().nextFloat() < (float)level * 0.2F) {
				event.setDamageModifier(Math.min(-1 - event.getBaseDamage(), -1));
				((EntityLivingBase)event.getTarget()).heal(damage);
			}
		}
	}
}