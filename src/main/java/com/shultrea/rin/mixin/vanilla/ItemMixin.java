package com.shultrea.rin.mixin.vanilla;

import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

@Mixin(Item.class)
public abstract class ItemMixin {
	
	@Unique
	private static final UUID soManyEnchantments$SS_UUID = UUID.fromString("fc1c8dca-9411-4a4e-97a4-90e66c883a77");
	
	@Unique
	private static final UUID soManyEnchantments$HW_UUID = UUID.fromString("e2765897-134f-4c14-a535-29c3ae5c7a21");
	
	/**
	 * Handling for Swifter Slashes and HeavyWeight enchants
	 */
	@ModifyReturnValue(
			method = "getAttributeModifiers",
			at = @At("RETURN"),
			remap = false
	)
	private Multimap<String, AttributeModifier> soManyEnchantments_vanillaItem_getAttributeModifiers(Multimap<String, AttributeModifier> original, EntityEquipmentSlot slot, ItemStack stack) {
		if(stack.isEmpty() || slot != EntityEquipmentSlot.MAINHAND) return original;
		int ssLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.swifterSlashes, stack);
		int hwLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.heavyWeight, stack);
		if(ssLevel > 0 && EnchantmentRegistry.swifterSlashes.isEnabled()) {
			original.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(soManyEnchantments$SS_UUID, "swifterSlashes", 0.20D*(double)ssLevel, 1));
		}
		if(hwLevel > 0 && EnchantmentRegistry.heavyWeight.isEnabled()) {
			original.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(soManyEnchantments$HW_UUID, "heavyWeight", ((double)hwLevel*0.10D + 0.20) * -1.0D, 1));
		}
		return original;
	}
}