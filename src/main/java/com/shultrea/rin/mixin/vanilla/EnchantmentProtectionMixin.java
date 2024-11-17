package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.enchantments.armor.protection.EnchantmentAdvancedBlastProtection;
import com.shultrea.rin.enchantments.armor.protection.EnchantmentAdvancedFireProtection;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentProtection.class)
public abstract class EnchantmentProtectionMixin {
	
	/**
	 * @author fonnymunkey
	 * @reason fix blast protection flooring reduction making it not effective, include advanced blast protection handling
	 */
	@Overwrite
	public static double getBlastDamageReduction(EntityLivingBase entityLivingBaseIn, double damage) {
		int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.BLAST_PROTECTION, entityLivingBaseIn);
		i += EnchantmentAdvancedBlastProtection.getValue(entityLivingBaseIn);
		
		if(i > 0) damage -= damage * Math.min(1D, (double)((float)i * 0.15F));
		
		return damage;
	}
	
	/**
	 * Handling for Advanced Fire Protection enchant
	 */
	@Redirect(
			method = "getFireTimeForEntity",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getMaxEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/EntityLivingBase;)I")
	)
	private static int soManyEnchantments_vanillaEnchantmentProtection_getFireTimeForEntity(Enchantment enchant, EntityLivingBase entity) {
		int i = EnchantmentHelper.getMaxEnchantmentLevel(enchant, entity);
		i += EnchantmentAdvancedFireProtection.getValue(entity);
		return i;
	}
}