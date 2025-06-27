package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.EnchantmentHelperMixin
 */
public class EnchantmentAdvancedLooting extends EnchantmentBase {
	
	public EnchantmentAdvancedLooting(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	public static int getLevelValue(EntityLivingBase entity) {
		if(!EnchantmentRegistry.advancedLooting.isEnabled()) return 0;
		if(entity == null) return 0;
		ItemStack stack = entity.getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedLooting, stack);
		if(level <= 0) return 0;
		return getLevelMult(level);
	}
	
	public static int getLevelMult(int level) {
		int toReturn = 2 + 2 * level;
		if(Math.random() < 0.25F) toReturn = 3 + 3 * level;
		return toReturn;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedLooting;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedLooting;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedLooting, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedLooting, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.advancedLooting, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.advancedLooting, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedLooting;
	}
}