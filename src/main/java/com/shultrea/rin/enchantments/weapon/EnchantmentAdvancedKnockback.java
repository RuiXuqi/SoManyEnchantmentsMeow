package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.EnchantmentHelperMixin
 */
public class EnchantmentAdvancedKnockback extends EnchantmentBase {
	
	public EnchantmentAdvancedKnockback(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	public static int getLevelValue(EntityLivingBase entity) {
		if(!EnchantmentRegistry.advancedKnockback.isEnabled()) return 0;
		if(entity == null) return 0;
		ItemStack stack = entity.getHeldItemMainhand();
		if(CompatUtil.isRLCombatLoaded()) stack = RLCombatCompat.getKnockbackStack(entity);
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedKnockback, stack);
		if(level <= 0) return 0;
		return getLevelMult(level);
	}
	
	public static int getLevelMult(int level) {
		return MathHelper.floor(level * 2.5F);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedKnockback;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedKnockback;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedKnockback, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedKnockback, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.advancedKnockback, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.advancedKnockback, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedKnockback;
	}
}