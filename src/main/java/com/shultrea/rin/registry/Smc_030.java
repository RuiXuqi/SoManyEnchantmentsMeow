package com.shultrea.rin.registry;

import com.shultrea.rin.enchantments.*;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.curses.EnchantmentPowerless;
import com.shultrea.rin.enchantments.rune.EnchantmentRuneRevival;
import com.shultrea.rin.enchantments.tier.*;
import net.minecraft.enchantment.Enchantment;

public class Smc_030 {
	
	public static void init() {
		Clearsky = registerAs(new EnchantmentClearSkiesFavor());
		Moonlight = registerAs(new EnchantmentLunasBlessing());
		Raining = registerAs(new EnchantmentRainsBestowment());
		Sunshine = registerAs(new EnchantmentSolsBlessing());
		Thunderstorm = registerAs(new EnchantmentThunderstormsBestowment());
		Winter = registerAs(new EnchantmentWintersGrace());
		Smelter = registerAs(new EnchantmentSmelter());
		EmpoweredDefence = registerAs(new EnchantmentEmpoweredDefence());
		Strafe = registerAs(new EnchantmentStrafe());
		CriticalStrike = registerAs(new EnchantmentCriticalStrike());
		AdvancedLooting = registerAs(new EnchantmentAdvancedLooting());
		Levitator = registerAs(new EnchantmentLevitator());
		MagicProtection = registerAs(new EnchantmentMagicProtection());
		PhysicalProtection = registerAs(new EnchantmentPhysicalProtection());
		AshDestroyer = registerAs(new EnchantmentAshDestroyer());
		Desolator = registerAs(new EnchantmentDesolator());
		Disorientation = registerAs(new EnchantmentDisorientatingBlade());
		PurgingBlade = registerAs(new EnchantmentPurgingBlade());
		Viper = registerAs(new EnchantmentViper());
		AdvancedPower = registerAs(new EnchantmentAdvancedPower());
		Envenomed = registerAs(new EnchantmentEnvenomed());
		Powerless = registerAs(new EnchantmentPowerless());
		Rune_Revival = registerAs(new EnchantmentRuneRevival());
		AdvancedPunch = registerAs(new EnchantmentAdvancedPunch());
		AdvancedLure = registerAs(new EnchantmentAdvancedLure());
		AdvancedLuckOfTheSea = registerAs(new EnchantmentAdvancedLuckOfTheSea());
		AdvancedFeatherFalling = registerAs(new EnchantmentAdvancedFeatherFalling());
		AdvancedThorns = registerAs(new EnchantmentAdvancedThorns());
		BurningThorns = registerAs(new EnchantmentBurningThorns());
		AdvancedProtection = registerAs(new EnchantmentAdvancedProtection());
		AdvancedFireProtection = registerAs(new EnchantmentAdvancedFireProtection());
		AdvancedBlastProtection = registerAs(new EnchantmentAdvancedBlastProtection());
		AdvancedProjectileProtection = registerAs(new EnchantmentAdvancedProjectileProtection());
		Quarrying = registerAs(new EnchantmentQuarrying());
	}
	
	private static EnchantmentBase registerAs(Enchantment enchant) {
		return OrderedRegistry.registerAs(enchant);
	}
}