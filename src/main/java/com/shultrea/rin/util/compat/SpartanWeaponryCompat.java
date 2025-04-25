package com.shultrea.rin.util.compat;

import com.oblivioussp.spartanweaponry.item.ItemCrossbow;
import com.oblivioussp.spartanweaponry.util.IDamageSourceArmorPiercing;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;

public abstract class SpartanWeaponryCompat {
	
	public static float getDamageSourcePiercing(DamageSource source) {
		float percent = 0.0F;
		if(source instanceof IDamageSourceArmorPiercing) {
			percent = ((IDamageSourceArmorPiercing)source).getPercentage();
		}
		return percent;
	}

	public static boolean itemIsCrossbow(Item item) {
		return item instanceof ItemCrossbow;
	}
}