package com.shultrea.rin.Hook;

import com.shultrea.rin.enchantments.tier.EnchantmentAdvancedBlastProtection;
import com.shultrea.rin.enchantments.tier.EnchantmentAdvancedLooting;
import com.shultrea.rin.enchantments.tier.EnchantmentAdvancedLuckOfTheSea;
import com.shultrea.rin.enchantments.tier.EnchantmentAdvancedLure;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class HookHelper {
	//Enchantment Helper hooks
	
	public static int modifyFishingLuckBonus(int original, ItemStack stack) {
		return Math.max(original, EnchantmentAdvancedLuckOfTheSea.getValue(stack));
	}
	
	public static int modifyFishingSpeedBonus(int original, ItemStack stack) {
		return Math.max(original, EnchantmentAdvancedLure.getValue(stack));
	}
	
	public static int modifyLootingModifier(int original, EntityLivingBase entity) {
		return Math.max(original, EnchantmentAdvancedLooting.getValue(original, entity));
	}
	//EnchantmentProtection hooks
	
	//This is actually knockback, but whatever
	public static double modifyBlastDamageReduction(double damage, EntityLivingBase entity) {
		return EnchantmentAdvancedBlastProtection.getKnockbackValue(damage, entity);
	}
}
