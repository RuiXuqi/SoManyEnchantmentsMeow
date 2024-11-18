package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	
	/**
	 * Handling for Rusted enchant
	 */
	@ModifyVariable(
			method = "damageItem",
			at = @At("HEAD"),
			argsOnly = true
	)
	private int soManyEnchantments_vanillaItemStack_damageItem(int amount) {
		if(amount > 0) {
			int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.rusted, (ItemStack)(Object)this);
			if(level > 0) return amount * (level+1);
		}
		return amount;
	}
}