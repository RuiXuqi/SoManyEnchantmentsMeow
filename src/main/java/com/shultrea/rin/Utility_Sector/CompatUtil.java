package com.shultrea.rin.Utility_Sector;

import net.minecraftforge.fml.common.Loader;

public class CompatUtil {
	
	private static Boolean isRLCombatLoaded = null;
	
	public static boolean isRLCombatLoaded() {
		if(isRLCombatLoaded == null) isRLCombatLoaded = Loader.isModLoaded("bettercombatmod");
		return isRLCombatLoaded;
	}
}