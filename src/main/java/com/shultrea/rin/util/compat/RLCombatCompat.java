package com.shultrea.rin.util.compat;

import bettercombat.mod.compat.EnchantCompatHandler;
import bettercombat.mod.event.RLCombatCriticalHitEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

public abstract class RLCombatCompat {
	
	public static ItemStack getArthropodStack(EntityLivingBase entity) {
		if(EnchantCompatHandler.arthropodFromOffhand) return entity.getHeldItemOffhand();
		return entity.getHeldItemMainhand();
	}
	
	public static boolean isOnEntityDamagedAltStrong() {
		return EnchantCompatHandler.arthropodCooledStrength > 0.9F;
	}
	
	public static boolean isAttackEntityFromStrong() {
		return EnchantCompatHandler.attackEntityFromCooledStrength > 0.9F;
	}
	
	public static boolean isCriticalHitEventStrong(CriticalHitEvent event) {
		if(!(event instanceof RLCombatCriticalHitEvent)) return true;
		RLCombatCriticalHitEvent rlEvent = (RLCombatCriticalHitEvent)event;
		return rlEvent.getCooledStrength() > 0.9F;
	}
	
	public static ItemStack getCriticalHitEventStack(CriticalHitEvent event, EntityLivingBase entity) {
		if(!(event instanceof RLCombatCriticalHitEvent)) return entity.getHeldItemMainhand();
		RLCombatCriticalHitEvent rlEvent = (RLCombatCriticalHitEvent)event;
		return rlEvent.getOffhand() ? entity.getHeldItemOffhand() : entity.getHeldItemMainhand();
	}
}