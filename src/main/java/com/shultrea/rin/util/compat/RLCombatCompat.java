package com.shultrea.rin.util.compat;

import bettercombat.mod.compat.EnchantCompatHandler;
import bettercombat.mod.event.RLCombatCriticalHitEvent;
import bettercombat.mod.event.RLCombatSweepEvent;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class RLCombatCompat {
	
	public static ItemStack getArthropodStack(EntityLivingBase entity) {
		if(EnchantCompatHandler.arthropodFromOffhand) return entity.getHeldItemOffhand();
		return entity.getHeldItemMainhand();
	}
	
	public static ItemStack getKnockbackStack(EntityLivingBase entity) {
		if(EnchantCompatHandler.knockbackFromOffhand) return entity.getHeldItemOffhand();
		return entity.getHeldItemMainhand();
	}
	
	public static ItemStack getFireAspectStack(EntityLivingBase entity) {
		if(EnchantCompatHandler.fireAspectFromOffhand) return entity.getHeldItemOffhand();
		return entity.getHeldItemMainhand();
	}

	public static float getOnEntityDamagedAltStrength() {
		return EnchantCompatHandler.arthropodCooledStrength;
	}
	
	public static boolean isOnEntityDamagedAltStrong() {
		return getOnEntityDamagedAltStrength() > 0.9F;
	}

	public static float getAttackEntityFromStrength() {
		return EnchantCompatHandler.attackEntityFromCooledStrength;
	}

	public static boolean isAttackEntityFromStrong() {
		return getAttackEntityFromStrength() > 0.9F;
	}

	public static float getCriticalHitEventStrength(CriticalHitEvent event) {
		if(!(event instanceof RLCombatCriticalHitEvent)) return 1.0F;
		RLCombatCriticalHitEvent rlEvent = (RLCombatCriticalHitEvent)event;
		return rlEvent.getCooledStrength();
	}
	
	public static boolean isCriticalHitEventStrong(CriticalHitEvent event) {
		return getCriticalHitEventStrength(event) > 0.9F;
	}
	
	public static ItemStack getCriticalHitEventStack(CriticalHitEvent event, EntityLivingBase entity) {
		if(!(event instanceof RLCombatCriticalHitEvent)) return entity.getHeldItemMainhand();
		RLCombatCriticalHitEvent rlEvent = (RLCombatCriticalHitEvent)event;
		return rlEvent.getOffhand() ? entity.getHeldItemOffhand() : entity.getHeldItemMainhand();
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onRLCombatSweepEvent(RLCombatSweepEvent event) {
		if(!EnchantmentRegistry.arcSlash.isEnabled()) return;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.arcSlash, event.getItemStack());
		if(level > 0 && event.getEntityPlayer() != null && event.getTargetEntity() != null) {
			event.setDoSweep(true);
			event.setSweepModifier(Math.max(event.getSweepModifier(), level * 0.20F));
		}
	}
}