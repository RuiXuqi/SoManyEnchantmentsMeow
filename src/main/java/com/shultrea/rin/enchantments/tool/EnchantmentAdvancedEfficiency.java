package com.shultrea.rin.enchantments.tool;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.EnchantmentHelperMixin
 */
public class EnchantmentAdvancedEfficiency extends EnchantmentBase {
	
	public EnchantmentAdvancedEfficiency(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}

	public static int getLevelValue(EntityLivingBase entity) {
		if(!EnchantmentRegistry.advancedEfficiency.isEnabled()) return 0;
		if(entity == null) return 0;
		ItemStack stack = entity.getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedEfficiency, stack);
		if(level <= 0) return 0;
		return getLevelMult(level);
	}
	
	public static int getLevelMult(int level) {
		return MathHelper.floor(level * 2.5F);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedEfficiency;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedEfficiency;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedEfficiency, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedEfficiency, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.advancedEfficiency, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.advancedEfficiency, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedEfficiency;
	}
}