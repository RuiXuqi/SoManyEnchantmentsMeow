package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.properties.ArrowPropertiesProvider;
import com.shultrea.rin.properties.IArrowProperties;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onBowDraw(LivingEntityUseItemEvent.Tick event) {
		ItemStack bow = event.getItem();
		if(bow.isEmpty()) return;
		if(!(bow.getItem() instanceof ItemBow)) return;

		int levelStrafe = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.strafe, bow);
		if(levelStrafe <= 0) return;

		int d = event.getDuration();
		if(levelStrafe <= 4) {
			if(d % (5 - levelStrafe) == 0)
				event.setDuration(d - 1);
		}
		else if(levelStrafe == 5)
			event.setDuration(d - levelStrafe - 8);
		else
			event.setDuration(d - levelStrafe - 1200);

		if(event.getDuration() < 5000) event.setDuration(20000);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onArrowHit(LivingHurtEvent fEvent) {
		if(!(fEvent.getSource().getDamageType().equals("arrow"))) return;
		if(!(fEvent.getSource().getImmediateSource() instanceof EntityArrow)) return;
		EntityArrow arrow = (EntityArrow)fEvent.getSource().getImmediateSource();

		IArrowProperties cap = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		if(cap==null) return;
		if(cap.getArrowResetsIFrames())
			fEvent.getEntityLiving().hurtResistantTime = 0;
	}
}