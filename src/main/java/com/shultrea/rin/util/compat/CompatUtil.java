package com.shultrea.rin.util.compat;

import net.minecraftforge.fml.common.Loader;

public class CompatUtil {
	
	private static final String RLCOMBAT_MODID = "bettercombatmod";
	private static Boolean isRLCombatLoaded = null;
	
	public static boolean isRLCombatLoaded() {
		if(isRLCombatLoaded == null) isRLCombatLoaded = Loader.isModLoaded(RLCOMBAT_MODID) && isRLCombatCorrectVersion();
		return isRLCombatLoaded;
	}
	
	private static boolean isRLCombatCorrectVersion() {
		String[] arrOfStr = Loader.instance().getIndexedModList().get(RLCOMBAT_MODID).getVersion().split("\\.");
		try {
			int i = Integer.parseInt(String.valueOf(arrOfStr[0]));
			if(i == 2) return true;
		}
		catch(Exception ignored) { }
		return false;
	}
}