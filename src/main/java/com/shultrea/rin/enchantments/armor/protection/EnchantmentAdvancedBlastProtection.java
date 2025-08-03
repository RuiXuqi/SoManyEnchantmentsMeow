package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.EnchantmentProtectionMixin
 */
public class EnchantmentAdvancedBlastProtection extends EnchantmentBase {
	
	public EnchantmentAdvancedBlastProtection(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	public static int getValue(EntityLivingBase entity) {
		if(!EnchantmentRegistry.advancedBlastProtection.isEnabled()) return 0;
		if(entity == null) return 0;
		int i = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.advancedBlastProtection, entity);

		return 2 * i;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedBlastProtection;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedBlastProtection;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedBlastProtection, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedBlastProtection, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.advancedBlastProtection, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.advancedBlastProtection, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedBlastProtection;
	}
	
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 : source.isExplosion() ? level * 3 : 0;
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingHurtEvent_extraProtection(LivingHurtEvent event) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		if(!this.isEnabled()) return;
		if(!event.getSource().isExplosion()) return;
		int modifier = (int)(7.5F * EnchantUtil.getTotalArmorEnchantmentLevel(this, event.getEntityLiving()));
		float damage = EnchantUtil.getDamageAfterMagicAbsorb(event.getAmount(), modifier);
		event.setAmount(damage);
	}
}