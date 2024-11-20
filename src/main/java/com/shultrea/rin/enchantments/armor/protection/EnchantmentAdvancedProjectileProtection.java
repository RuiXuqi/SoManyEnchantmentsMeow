package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdvancedProjectileProtection extends EnchantmentBase {
	
	public EnchantmentAdvancedProjectileProtection(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedProjectileProtection;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedProjectileProtection;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedProjectileProtection, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedProjectileProtection, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedProjectileProtection, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedProjectileProtection, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedProjectileProtection;
	}
	
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 : source.isProjectile() ? level * 3 : 0;
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void livingHurtEvent_extraProtection(LivingHurtEvent fEvent) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		if(!this.isEnabled()) return;
		if(!fEvent.getSource().isProjectile()) return;
		int modifier = (int)(7.5f * EnchantUtil.getTotalEnchantmentLevel(this, fEvent.getEntityLiving()));
		float damage = EnchantUtil.getDamageAfterMagicAbsorb(fEvent.getAmount(), modifier);
		fEvent.setAmount(damage);
	}
}