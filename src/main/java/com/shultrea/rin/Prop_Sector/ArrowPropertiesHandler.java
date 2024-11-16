package com.shultrea.rin.Prop_Sector;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArrowPropertiesHandler {

	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onArrowCapAttach(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityArrow && !event.getObject().hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) {
			event.addCapability(new ResourceLocation(SoManyEnchantments.MODID + ":arrow_capabilities"), new ArrowPropertiesProvider());
		}
	}

	public static void setArrowCapabilities(EntityLivingBase shooter, EntityArrow arrow){
		int powerlessLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.powerless, shooter);
		if(powerlessLevel > 0) {
			arrow.setDamage(arrow.getDamage() - 0.5D - powerlessLevel * 0.5D);
			if(powerlessLevel > 2 || shooter.getRNG().nextFloat() < powerlessLevel * 0.4F) {
				arrow.setIsCritical(false);
			}
		}

		int advPowerLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.advancedPower, shooter);
		if(advPowerLevel > 0) {
			arrow.setDamage(arrow.getDamage() + 1.25D + (double)advPowerLevel * 0.75D);
			if(advPowerLevel >= 4) {
				arrow.setIsCritical(true);
			}
			else if(shooter.getRNG().nextFloat() < advPowerLevel * 0.25f) {
				arrow.setIsCritical(true);
			}
		}

		int advPunchLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.advancedPunch, shooter);
		if(advPunchLevel > 0)
			arrow.setKnockbackStrength(1 + advPunchLevel * 2);

		IArrowProperties cap = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		if(cap == null) return;

		int runeArrowPiercingLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.runeArrowPiercing, shooter);
		if(runeArrowPiercingLevel > 0)
			 cap.setArmorPiercingLevel(runeArrowPiercingLevel);

		int draggingLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.dragging, shooter);
		if(draggingLevel > 0)
			cap.setDraggingPower(1.25f + draggingLevel * 1.75f);

		int levelStrafe = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strafe, shooter);
		if (shooter.getRNG().nextFloat() < 0.125f * levelStrafe)
			cap.setArrowResetsIFrames(true);

		int levelLessFlame = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.lesserFlame, shooter);
		if (levelLessFlame > 0) {
			//arrow.setFire(50); //Overrides the 2 seconds with 5 seconds
			cap.setFlameLevel(0);
		}

		int levelAdvFlame = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.advancedFlame, shooter);
		if (levelAdvFlame > 0) {
			arrow.setFire(200);
			cap.setFlameLevel(1);
		}

		int levelSupFlame = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.supremeFlame, shooter);
		if (levelSupFlame > 0) {
			arrow.setFire(400);
			cap.setFlameLevel(2);
		}
	}
}