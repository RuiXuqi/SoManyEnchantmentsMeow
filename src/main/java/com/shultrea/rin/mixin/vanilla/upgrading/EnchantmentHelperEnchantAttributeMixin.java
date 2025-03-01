package com.shultrea.rin.mixin.vanilla.upgrading;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.shultrea.rin.attributes.EnchantAttribute;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(value = EnchantmentHelper.class, priority = 2000)
public abstract class EnchantmentHelperEnchantAttributeMixin {
	@WrapOperation(
			method = "buildEnchantmentList",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEnchantmentDatas(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;")
	)
	private static List<EnchantmentData> soManyEnchantments_enchantmentHelper_buildEnchantmentList(int level, ItemStack stack, boolean allowTreasure, Operation<List<EnchantmentData>> original){
		//Get ThreadLocal
		IAttributeInstance playerAttribute = EnchantAttribute.getAndRemoveThreadLocal();
		if(playerAttribute != null){
			//In order to properly use op0 as well as op1 and op2, we need to set the current lvl as base value
			playerAttribute.setBaseValue(level);
			//Apply all modifiers and floor
			int attributeValue = MathHelper.floor(playerAttribute.getAttributeValue());
			//Reset base value, because the attribute is still stored inside the player. It might get used somewhere else in the future
			playerAttribute.setBaseValue(0);

			//--> return EnchantmentHelper.getEnchantmentDatas(attributeValue, stack, allowTreasure)
			return original.call(attributeValue, stack, allowTreasure);
		}
		return original.call(level, stack, allowTreasure);
	}
}