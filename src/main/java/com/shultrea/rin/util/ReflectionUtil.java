package com.shultrea.rin.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;

//TODO is any of this truly needed
public abstract class ReflectionUtil {
	
	/**
	 * Accessible reference to {@code EntityLivingBase#applyArmorCalculations
	 */
	private static Method applyArmorCalculations;
	
	/**
	 * Accessible reference to {@code EntityLivingBase#applyPotionDamageCalculations
	 */
	private static Method applyPotionDamageCalculations;
	
	/**
	 * Applies armor calculation to the target.
	 */
	private static float applyArmorCalculations(EntityLivingBase target, DamageSource source, float amount) {
		if(applyArmorCalculations == null) {
			applyArmorCalculations = ObfuscationReflectionHelper.findMethod(EntityLivingBase.class, "func_70655_b", float.class, DamageSource.class, float.class);
		}
		try {
			amount = (float)applyArmorCalculations.invoke(target, source, amount);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return amount;
	}
	
	/**
	 * Returns the amount of damage the entity will receive after armor and potions are taken into account
	 */
	private static float applyPotionDamageCalculations(EntityLivingBase target, DamageSource source, float amount) {
		if(applyPotionDamageCalculations == null) {
			applyPotionDamageCalculations = ObfuscationReflectionHelper.findMethod(EntityLivingBase.class, "func_70672_c", float.class, DamageSource.class, float.class);
		}
		try {
			amount = (float)applyPotionDamageCalculations.invoke(target, source, amount);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return amount;
	}
	
	/**
	 * Damages target without posting an event!
	 */
	public static void damageEntityNoEvent(EntityLivingBase target, DamageSource damageSrc, float damageAmount) {
		if(!target.isEntityInvulnerable(damageSrc)) {
			if(damageAmount <= 0) return;
			damageAmount = applyArmorCalculations(target, damageSrc, damageAmount);
			damageAmount = applyPotionDamageCalculations(target, damageSrc, damageAmount);
			float f = damageAmount;
			damageAmount = Math.max(damageAmount - target.getAbsorptionAmount(), 0.0F);
			target.setAbsorptionAmount(target.getAbsorptionAmount() - (f - damageAmount));
			if(damageAmount > 0.0F) {
				float f1 = target.getHealth();
				target.getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
				target.setHealth(f1 - damageAmount); // Forge: moved to fix MC-121048
				target.setAbsorptionAmount(target.getAbsorptionAmount() - damageAmount);
			}
		}
	}
	
	/**
	 * Damages target but LivingDamageEvent is fired!
	 */
	public static void damageEntityLivingDamageEvent(EntityLivingBase target, DamageSource damageSrc, float damageAmount) {
		if(!target.isEntityInvulnerable(damageSrc)) {
			if(damageAmount <= 0) return;
			damageAmount = applyArmorCalculations(target, damageSrc, damageAmount);
			damageAmount = applyPotionDamageCalculations(target, damageSrc, damageAmount);
			float f = damageAmount;
			damageAmount = Math.max(damageAmount - target.getAbsorptionAmount(), 0.0F);
			target.setAbsorptionAmount(target.getAbsorptionAmount() - (f - damageAmount));
			damageAmount = net.minecraftforge.common.ForgeHooks.onLivingDamage(target, damageSrc, damageAmount);
			if(damageAmount > 0.0F) {
				float f1 = target.getHealth();
				target.getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
				target.setHealth(f1 - damageAmount); // Forge: moved to fix MC-121048
				target.setAbsorptionAmount(target.getAbsorptionAmount() - damageAmount);
			}
		}
	}
}