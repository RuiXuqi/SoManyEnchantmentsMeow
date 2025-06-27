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

import java.util.Random;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.EnchantmentHelperMixin
 */
public class EnchantmentTierFA extends EnchantmentBase {
	
	private static final Random RAND = new Random();
	
	private static final int LESSFIRE = 0;
	private static final int ADVFIRE = 1;
	private static final int SUPFIRE = 2;
	
	private final int damageType;
	
	public EnchantmentTierFA(String name, Rarity rarity, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
		this.damageType = damageTypeIn;
	}
	
	public static int getLevelValue(EntityLivingBase entity) {
		if(entity == null) return 0;
		ItemStack stack = entity.getHeldItemMainhand();
		if(CompatUtil.isRLCombatLoaded()) stack = RLCombatCompat.getFireAspectStack(entity);
		int level = 0;
		if(EnchantmentRegistry.lesserFireAspect.isEnabled()) {
			level += getLevelMult(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.lesserFireAspect, stack), LESSFIRE);
		}
		if(EnchantmentRegistry.advancedFireAspect.isEnabled()) {
			level += getLevelMult(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedFireAspect, stack), ADVFIRE);
		}
		if(EnchantmentRegistry.supremeFireAspect.isEnabled()) {
			level += getLevelMult(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.supremeFireAspect, stack), SUPFIRE);
		}
		return level;
	}
	
	public static int getLevelMult(int level, int tier) {
		switch(tier){
			case LESSFIRE: return RAND.nextInt(2) * level;
			case ADVFIRE: return 2 * level;
			case SUPFIRE: return 4 * level;
		}
		return 0;
	}
	
	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case LESSFIRE: return ModConfig.enabled.lesserFireAspect;
			case ADVFIRE: return ModConfig.enabled.advancedFireAspect;
			case SUPFIRE: return ModConfig.enabled.supremeFireAspect;
			default: return false;
		}
	}
	
	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case LESSFIRE: return ModConfig.level.lesserFireAspect;
			case ADVFIRE: return ModConfig.level.advancedFireAspect;
			case SUPFIRE: return ModConfig.level.supremeFireAspect;
			default: return 2;
		}
	}

	@Override
	public int getMinEnchantability(int level) {
		switch(this.damageType) {
			case LESSFIRE: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserFireAspect, level);
			case ADVFIRE: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedFireAspect, level);
			case SUPFIRE: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeFireAspect, level);
			default: return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch(this.damageType) {
			case LESSFIRE: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserFireAspect, level);
			case ADVFIRE: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedFireAspect, level);
			case SUPFIRE: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeFireAspect, level);
			default: return 0;
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		switch(this.damageType){
			case LESSFIRE: return ConfigProvider.canItemApply(ModConfig.canApply.lesserFireAspect, stack) && super.canApplyAtEnchantingTable(stack);
			case ADVFIRE: return ConfigProvider.canItemApply(ModConfig.canApply.advancedFireAspect, stack) && super.canApplyAtEnchantingTable(stack);
			case SUPFIRE: return ConfigProvider.canItemApply(ModConfig.canApply.supremeFireAspect, stack) && super.canApplyAtEnchantingTable(stack);
			default: return false;
		}
	}

	@Override
	public boolean canApply(ItemStack stack){
		switch(this.damageType){
			case LESSFIRE: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.lesserFireAspect, stack) || super.canApply(stack);
			case ADVFIRE: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.advancedFireAspect, stack) || super.canApply(stack);
			case SUPFIRE: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.supremeFireAspect, stack) || super.canApply(stack);
			default: return false;
		}
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case LESSFIRE: return ModConfig.treasure.lesserFireAspect;
			case ADVFIRE: return ModConfig.treasure.advancedFireAspect;
			case SUPFIRE: return ModConfig.treasure.supremeFireAspect;
			default: return false;
		}
	}
}