package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.ItemMixin
 */
public class EnchantmentHeavyWeight extends EnchantmentCurse {
	
	public EnchantmentHeavyWeight(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.heavyWeight;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.heavyWeight;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.heavyWeight, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.heavyWeight, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.heavyWeight, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.heavyWeight, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.heavyWeight;
	}
	
	@SubscribeEvent
	public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
		if(!this.isEnabled()) return;
		if(event.getEntityLiving() == null) return;
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(this, event.getEntityLiving());
		if(enchantmentLevel > 0) {
			event.getEntityLiving().motionY *= (1.0D - (double)enchantmentLevel * 0.1D);
		}
	}
	
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if(!this.isEnabled()) return;
		if(event.getEntityLiving() == null) return;
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(this, event.getEntityLiving());
		if(enchantmentLevel > 0) {
			event.setDamageMultiplier(event.getDamageMultiplier() * (1.0F + (float)enchantmentLevel * 0.25F));
		}
	}
}