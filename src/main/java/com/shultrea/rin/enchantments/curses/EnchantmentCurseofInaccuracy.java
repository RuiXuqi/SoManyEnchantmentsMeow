package com.shultrea.rin.enchantments.curses;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.CompatUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment arrow inaccuracy handled in com.shultrea.rin.mixin.vanilla.ItemBowMixin
 */
public class EnchantmentCurseofInaccuracy extends EnchantmentCurse {
	
	public EnchantmentCurseofInaccuracy(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.curseOfInaccuracy;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.curseOfInaccuracy;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.curseOfInaccuracy, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.curseOfInaccuracy, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.curseOfInaccuracy, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.curseOfInaccuracy, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfInaccuracy;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLivingAttackEvent(LivingAttackEvent event) {
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getSource().getTrueSource() instanceof EntityPlayer && CompatUtil.isRLCombatLoaded()) return;
		EntityLivingBase entity = (EntityLivingBase)event.getSource().getTrueSource();
		if(entity == null) return;
		int level = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.curseOfInaccuracy, entity);
		if(level > 0 && entity.getRNG().nextFloat() < ((float)level * 0.20F)) {
			event.setCanceled(true);
		}
	}
	
	@Optional.Method(modid = "bettercombatmod")
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void modifyDamageEventPre(RLCombatModifyDamageEvent.Pre event) {
		if(event.getEntityPlayer() == null || event.getTarget() == null || event.getStack().isEmpty() || !(event.getTarget() instanceof EntityLivingBase)) return;
		
		EntityPlayer player = event.getEntityPlayer();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.curseOfInaccuracy, event.getStack());
		if(level > 0) {
			if(player.getRNG().nextFloat() < (float)level * 0.20F) {
				event.setDamageModifier(Math.min(-1 - event.getBaseDamage(), -1));
			}
		}
	}
}