package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDragging extends EnchantmentBase {
	
	public EnchantmentDragging(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.dragging;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.dragging;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.dragging, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.dragging, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.dragging, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.dragging, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.dragging;
	}

	@SubscribeEvent
	public void onArrowHit(LivingDamageEvent fEvent) {
		if(!"arrow".equals(fEvent.getSource().damageType)) return;
		if(!(fEvent.getSource().getImmediateSource() instanceof EntityArrow)) return;

		EntityArrow arrow = (EntityArrow) fEvent.getSource().getImmediateSource();
		EntityLivingBase victim = fEvent.getEntityLiving();

		if(!arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) return;
		IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);

		float draggingPower = properties.getDraggingPower();
		if(draggingPower > 0) {
			double velocityMultiplier = - draggingPower * 0.6 / (double) MathHelper.sqrt(arrow.motionX * arrow.motionX + arrow.motionZ * arrow.motionZ);
			victim.addVelocity(arrow.motionX * velocityMultiplier, 0.1, arrow.motionZ * velocityMultiplier);
			victim.velocityChanged = true;
		}
	}
}