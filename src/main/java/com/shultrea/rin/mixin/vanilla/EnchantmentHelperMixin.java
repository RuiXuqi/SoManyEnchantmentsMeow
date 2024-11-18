package com.shultrea.rin.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.fishing.EnchantmentAdvancedLuckOfTheSea;
import com.shultrea.rin.enchantments.fishing.EnchantmentAdvancedLure;
import com.shultrea.rin.enchantments.weapon.EnchantmentAdvancedLooting;
import com.shultrea.rin.utility_sector.compat.CompatUtil;
import com.shultrea.rin.utility_sector.compat.RLCombatCompat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EnchantmentHelper.class, priority = 2000)
public abstract class EnchantmentHelperMixin {
	
	/**
	 * Handling for onEntityDamagedAlt
	 */
	@Inject(
			method = "applyArthropodEnchantments",
			at = @At(value = "HEAD")
	)
	private static void soManyEnchantments_vanillaEnchantmentHelper_applyArthropodEnchantments(EntityLivingBase user, Entity target, CallbackInfo ci) {
		if(user != null) {
			ItemStack stack = user.getHeldItemMainhand();
			if(CompatUtil.isRLCombatLoaded()) {
				stack = RLCombatCompat.getArthropodStack(user);
			}
			if(!stack.isEmpty()) {
				NBTTagList nbttaglist = stack.getEnchantmentTagList();
				for(int i = 0; i < nbttaglist.tagCount(); ++i) {
					int j = nbttaglist.getCompoundTagAt(i).getShort("id");
					int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
					if(Enchantment.getEnchantmentByID(j) != null) {
						Enchantment ench = Enchantment.getEnchantmentByID(j);
						if(ench instanceof EnchantmentBase) {
							EnchantmentBase enchant = (EnchantmentBase)ench;
							if(enchant.isEnabled()) enchant.onEntityDamagedAlt(user, target, stack, k);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Handling for Advanced Luck of the Sea enchant
	 */
	@ModifyReturnValue(
			method = "getFishingLuckBonus",
			at = @At("RETURN")
	)
	private static int soManyEnchantments_vanillaEnchantmentHelper_getFishingLuckBonus(int original, ItemStack stack) {
		return Math.max(original, EnchantmentAdvancedLuckOfTheSea.getValue(stack));
	}
	
	/**
	 * Handling for Advanced Lure enchant
	 */
	@ModifyReturnValue(
			method = "getFishingSpeedBonus",
			at = @At("RETURN")
	)
	private static int soManyEnchantments_vanillaEnchantmentHelper_getFishingSpeedBonus(int original, ItemStack stack) {
		return Math.max(original, EnchantmentAdvancedLure.getValue(stack));
	}
	
	/**
	 * Handling for Advanced Looting enchant
	 */
	@ModifyReturnValue(
			method = "getLootingModifier",
			at = @At("RETURN")
	)
	private static int soManyEnchantments_vanillaEnchantmentHelper_getLootingModifier(int original, EntityLivingBase entity) {
		return Math.max(original, EnchantmentAdvancedLooting.getValue(original, entity));
	}
}