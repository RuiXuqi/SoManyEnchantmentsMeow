package com.shultrea.rin.util.compat;

import com.lycanitesmobs.ObjectManager;
import net.minecraft.block.Block;
import net.minecraft.potion.Potion;

public abstract class LycanitesMobsCompat {
	
	public static Potion getSmitedPotion() {
		return ObjectManager.getEffect("smited");
	}
	
	public static Block getFrostCloud() {
		return ObjectManager.getBlock("frostcloud");
	}
}
