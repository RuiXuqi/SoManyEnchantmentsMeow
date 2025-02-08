package com.shultrea.rin.util.compat;

import net.minecraftforge.fml.common.Loader;

public class CompatUtil {
	
	private static final String RLCOMBAT_MODID = "bettercombatmod";
	private static final String SWITCHBOW_MODID = "switchbow";
	private static final String INF_MODID = "iceandfire";
	private static final String SCALINGHEALTH_MODID = "scalinghealth";
	private static final String LYCANITESMOBS_MODID = "lycanitesmobs";
	private static final String SPARTANWEAPONRY_MODID = "spartanweaponry";
	private static final String SPAWNERCONTROL_MODID = "spawnercontrol";
	private static final String SOCKETED_MODID = "socketed";
	
	private static Boolean isRLCombatLoaded = null;
	private static Boolean isSwitchbowLoaded = null;
	private static Boolean isIceAndFireLoaded = null;
	private static Boolean isScalingHealthLoaded = null;
	private static Boolean isLycanitesMobsLoaded = null;
	private static Boolean isSpartanWeaponryLoaded = null;
	private static Boolean isSpawnerControlLoaded = null;
	private static Boolean isSocketedLoaded = null;
	
	public static boolean isRLCombatLoaded() {
		if(isRLCombatLoaded == null) isRLCombatLoaded = Loader.isModLoaded(RLCOMBAT_MODID) && isRLCombatCorrectVersion();
		return isRLCombatLoaded;
	}
	
	public static boolean isSwitchbowLoaded() {
		if(isSwitchbowLoaded == null) isSwitchbowLoaded = Loader.isModLoaded(SWITCHBOW_MODID);
		return isSwitchbowLoaded;
	}
	
	public static boolean isIceAndFireLoaded() {
		if(isIceAndFireLoaded == null) isIceAndFireLoaded = Loader.isModLoaded(INF_MODID);
		return isIceAndFireLoaded;
	}
	
	public static boolean isScalingHealthLoaded() {
		if(isScalingHealthLoaded == null) isScalingHealthLoaded = Loader.isModLoaded(SCALINGHEALTH_MODID);
		return isScalingHealthLoaded;
	}

	public static boolean isLycanitesMobsLoaded() {
		if(isLycanitesMobsLoaded == null) isLycanitesMobsLoaded = Loader.isModLoaded(LYCANITESMOBS_MODID);
		return isLycanitesMobsLoaded;
	}
	
	public static boolean isSpartanWeaponryLoaded() {
		if(isSpartanWeaponryLoaded == null) isSpartanWeaponryLoaded = Loader.isModLoaded(SPARTANWEAPONRY_MODID);
		return isSpartanWeaponryLoaded;
	}
	
	public static boolean isSpawnerControlLoaded() {
		if(isSpawnerControlLoaded == null) isSpawnerControlLoaded = Loader.isModLoaded(SPAWNERCONTROL_MODID);
		return isSpawnerControlLoaded;
	}

	public static boolean isSocketedLoaded() {
		if(isSocketedLoaded == null) isSocketedLoaded = Loader.isModLoaded(SOCKETED_MODID);
		return isSocketedLoaded;
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