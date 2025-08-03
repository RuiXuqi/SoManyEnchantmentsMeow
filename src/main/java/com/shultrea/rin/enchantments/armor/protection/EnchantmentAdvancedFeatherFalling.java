package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
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
		return ConfigProvider.canItemApply(this, ModConfig.canApply.advancedFeatherFalling, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.advancedFeatherFalling, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedFeatherFalling;
	}
	
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 : source == DamageSource.FALL ? level * 5 : 0;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingHurtEvent_extraProtection(LivingHurtEvent event) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		if(!this.isEnabled()) return;
		if(event.getSource() != DamageSource.FALL) return;
		int modifier = (int)(9.0F * EnchantUtil.getTotalArmorEnchantmentLevel(this, event.getEntityLiving()));
		float damage = EnchantUtil.getDamageAfterMagicAbsorb(event.getAmount(), modifier);
		event.setAmount(damage);
	}
}