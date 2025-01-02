package com.shultrea.rin.util;

import net.minecraft.util.DamageSource;

public abstract class DamageSources {
	
	/**
	 * Deconstruct is a damage source added by this mod that is used when an enemy is damaged from a molecular force. It
	 * bypasses armor and is absolute.
	 */
	public static final DamageSource DECONSTRUCTED = new DamageSource("deconstructed").setDamageIsAbsolute().setDamageBypassesArmor().setDamageAllowedInCreativeMode();
	
	/**
	 * Culled is a damage source added by this mod that is used to finish off low health opponents. Like Deconstruct, it
	 * bypasses armor and is absolute.
	 */
	public static final DamageSource CULLED = new DamageSource("culled").setDamageIsAbsolute().setDamageBypassesArmor();
}