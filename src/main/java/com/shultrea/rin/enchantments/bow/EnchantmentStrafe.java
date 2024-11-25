package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment handling in com.shultrea.rin.properties.ArrowPropertiesHandler
 */
public class EnchantmentStrafe extends EnchantmentBase {
	
	public EnchantmentStrafe(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.strafe;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.strafe;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.strafe, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.strafe, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.strafe, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.strafe, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.strafe;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingEntityUseItem(LivingEntityUseItemEvent.Tick event) {
		if(!this.isEnabled()) return;
		ItemStack bow = event.getItem();
		if(!(bow.getItem() instanceof ItemBow)) return;

		int levelStrafe = EnchantmentHelper.getEnchantmentLevel(this, bow);
		if(levelStrafe > 0) {
			event.setDuration(event.getDuration() - levelStrafe * 40);
			if(event.getDuration() < 5000) event.setDuration(20000);
		}
	}
}