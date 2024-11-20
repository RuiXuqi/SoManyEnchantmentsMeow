package com.shultrea.rin.util.compat;

import bettercombat.mod.compat.EnchantCompatHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract class RLCombatCompat {
	
	public static ItemStack getArthropodStack(EntityLivingBase entity) {
		if(EnchantCompatHandler.arthropodFromOffhand) return entity.getHeldItemOffhand();
		return entity.getHeldItemMainhand();
	}
}