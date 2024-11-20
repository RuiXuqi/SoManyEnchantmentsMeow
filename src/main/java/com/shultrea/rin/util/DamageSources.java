package com.shultrea.rin.util;

import net.minecraft.util.DamageSource;

public abstract class DamageSources {
	
	/**
	 * Deconstruct is a damage source added by this mod that is used when an enemy is damaged from a molecular force. It
	 * bypasses armor and is absolute.
	 */
	public static DamageSource Deconstruct = new DamageSource("Deconstruct").setDamageIsAbsolute().setDamageBypassesArmor();
	
	/**
	 * Culled is a damage source added by this mod that is used to finish off low health opponents. Like Deconstruct, it
	 * bypasses armor and is absolute.
	 */
	public static DamageSource Culled = new DamageSource("Culled").setDamageIsAbsolute().setDamageBypassesArmor();
	
	/**
	 * Physical Damage is a damage source added by this mod that is used to deal ordinary damage to a mob. Some
	 * enchantments in this mod make use of it.
	 */
	public static DamageSource PhysicalDamage = new DamageSource("PhysicalDamage");
}