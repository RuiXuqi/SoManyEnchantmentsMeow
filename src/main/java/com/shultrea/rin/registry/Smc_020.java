package com.shultrea.rin.registry;

import com.shultrea.rin.enchantments.*;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.curses.EnchantmentUnpredictable;
import com.shultrea.rin.enchantments.rune.EnchantmentRuneMagicalBlessing;
import com.shultrea.rin.enchantments.tier.EnchantmentAdvancedKnockback;
import net.minecraft.enchantment.Enchantment;

public class Smc_020 {
	
	public static void init() {
		Mortalitas = registerAs(new EnchantmentMortalitas());
		PenetratingEdge = registerAs(new EnchantmentPenetratingEdge());
		CombatRegeneration = registerAs(new EnchantmentCombatVeterancy());
		AdvancedKnockback = registerAs(new EnchantmentAdvancedKnockback());
		Rune_MagicalBlessing = registerAs(new EnchantmentRuneMagicalBlessing());
		CounterAttack = registerAs(new EnchantmentCounterAttack());
		Parry = registerAs(new EnchantmentParry());
		Unpredictable = registerAs(new EnchantmentUnpredictable());
		Lifesteal = registerAs(new EnchantmentLifesteal());
		Culling = registerAs(new EnchantmentCulling());
	}
	
	private static EnchantmentBase registerAs(Enchantment enchant) {
		return OrderedRegistry.registerAs(enchant);
	}
}
