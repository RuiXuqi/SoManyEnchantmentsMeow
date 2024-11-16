package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentTierFlame extends EnchantmentBase {

	/**
	 * Defines the type of damage of the enchantment, 0 = lesserfla, 1 = advfla, 2 = supfla
	 */
	public final int damageType;

	public EnchantmentTierFlame(String name, Rarity rarity, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
		this.damageType = damageTypeIn;
	}

	@Override
	public boolean isEnabled() {
		switch (this.damageType) {
			case 0:
				return ModConfig.enabled.lesserFlame;
			case 1:
				return ModConfig.enabled.advancedFlame;
			case 2:
				return ModConfig.enabled.supremeFlame;
			default:
				return false;
		}
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		switch (this.damageType) {
			case 0:
				return ModConfig.level.lesserFlame;
			case 1:
				return ModConfig.level.advancedFlame;
			case 2:
				return ModConfig.level.supremeFlame;
			default:
				return 1;
		}
	}


	@Override
	public int getMinEnchantability(int level) {
		switch (this.damageType) {
			case 0:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserFlame, level);
			case 1:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedFlame, level);
			case 2:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeFlame, level);
			default:
				return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch (this.damageType) {
			case 0:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserFlame, level);
			case 1:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedFlame, level);
			case 2:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeFlame, level);
			default:
				return 0;
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		switch (this.damageType) {
			case 0:
				return ModConfig.canApply.isItemValid(ModConfig.canApply.lesserFlame, stack) && super.canApplyAtEnchantingTable(stack);
			case 1:
				return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedFlame, stack) && super.canApplyAtEnchantingTable(stack);
			case 2:
				return ModConfig.canApply.isItemValid(ModConfig.canApply.supremeFlame, stack) && super.canApplyAtEnchantingTable(stack);
			default:
				return false;
		}
	}

	@Override
	public boolean canApply(ItemStack stack) {
		switch (this.damageType) {
			case 0:
				return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.lesserFlame, stack) || super.canApply(stack);
			case 1:
				return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedFlame, stack) || super.canApply(stack);
			case 2:
				return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.supremeFlame, stack) || super.canApply(stack);
			default:
				return false;
		}
	}

	@Override
	public boolean isTreasureEnchantment() {
		switch (this.damageType) {
			case 0:
				return ModConfig.treasure.lesserFlame;
			case 1:
				return ModConfig.treasure.advancedFlame;
			case 2:
				return ModConfig.treasure.supremeFlame;
			default:
				return false;
		}
	}

	public static int getFireSeconds(int tier) {
		switch (tier) {
			case 0:
				return 2;
			case 1:
				return 15;
			case 2:
				return 30;
		}
		return 0;
	}

	@SubscribeEvent
	public void onArrowHit(LivingDamageEvent fEvent) {
		if(!"arrow".equals(fEvent.getSource().damageType)) return;
		if(!(fEvent.getSource().getImmediateSource() instanceof EntityArrow)) return;

		EntityArrow arrow = (EntityArrow) fEvent.getSource().getImmediateSource();
		EntityLivingBase victim = fEvent.getEntityLiving();

		if(!arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) return;
		IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);

		int flameLevel = properties.getFlameLevel();
		victim.setFire(getFireSeconds(flameLevel));
	}
}