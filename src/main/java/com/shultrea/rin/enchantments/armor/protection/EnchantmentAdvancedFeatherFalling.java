package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdvancedFeatherFalling extends EnchantmentBase {
	
	public EnchantmentAdvancedFeatherFalling(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedFeatherFalling;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedFeatherFalling;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedFeatherFalling, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedFeatherFalling, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedFeatherFalling, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedFeatherFalling, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedFeatherFalling;
	}
	
	//TODO
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		if(Math.random() < 0.35f) return source.canHarmInCreative() ? 0 : (source == DamageSource.FALL ? level * 5 : 0);
		else return source.canHarmInCreative() ? 0 : (source == DamageSource.FALL ? level * 4 : 0);
	}

	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void extraProtectionEffect(LivingHurtEvent fEvent) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		if(!(ModConfig.enabled.advancedFeatherFalling)) return;
		if((fEvent.getSource() != DamageSource.FALL)) return;
		int modifier = 9*EnchantmentsUtility.getTotalEnchantmentLevel(EnchantmentRegistry.advancedFeatherFalling, fEvent.getEntityLiving());
		float damage = EnchantmentsUtility.getDamageAfterMagicAbsorb(fEvent.getAmount(), modifier);
		fEvent.setAmount(damage);
	}
}