package com.shultrea.rin.enchantments.tool;

import com.shultrea.rin.config.EnchantabilityConfig;
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
	
	public static int getValue(int original, EntityLivingBase entity) {
		if(!EnchantmentRegistry.advancedEfficiency.isEnabled()) return 0;
		if(entity == null) return 0;
		ItemStack stack = entity.getHeldItemMainhand();
		int levelEfficiency = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedEfficiency, stack);
		if(levelEfficiency <= 0) return 0;
		return original + MathHelper.floor(levelEfficiency * 2.5f);
		/*TODO:
		   	old value was original + (levelEfficiency + 1) * levelEfficiency * levelEfficiency + 3
			the new value here is getting squared and +1 in EntityPlayer.getDigSpeed
			old value was already in that state
			old value could go to 0 and below
			so we compare (level*3)² (new) to level³+level²+3+1 (old)
			plotting that, you see that new value is about twice as fast as old value
			using 2.5 instead of 3 and flooring/casting back to int would prob be closer to old behavior
			(as far as i understand the code, would need to test)
		*/
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
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedEfficiency, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedEfficiency, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedEfficiency;
	}
}