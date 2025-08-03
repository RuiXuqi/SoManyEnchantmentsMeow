package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment handling in com.shultrea.rin.properties.ArrowPropertiesHandler
 */
public class EnchantmentDragging extends EnchantmentCurse {
	
	public EnchantmentDragging(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.dragging;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.dragging;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.dragging, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.dragging, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.dragging, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.dragging, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.dragging;
	}

	public static int getLevelValue(EntityLivingBase entity) {
		if(!EnchantmentRegistry.dragging.isEnabled()) return 0;
		if(entity == null) return 0;
		ItemStack stack = entity.getHeldItemMainhand();
		if(CompatUtil.isRLCombatLoaded()) stack = RLCombatCompat.getKnockbackStack(entity);
		return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.dragging, stack);
	}

	@SubscribeEvent
	public void onLivingDamage(LivingDamageEvent event) {
		if (!EnchantmentRegistry.dragging.isEnabled()) return;
		if (!(event.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
		EntityLivingBase victim = event.getEntityLiving();

		int draggingLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.dragging, attacker.getHeldItemMainhand());
		if (draggingLevel <= 0) return;
		double velocityMultiplier = -0.6F * (1.25F + draggingLevel * 1.75F);
		float piDiv180 = 0.017453292F;	// pi/180
		victim.addVelocity(-MathHelper.sin(attacker.rotationYaw * piDiv180) * velocityMultiplier, 0.1, MathHelper.cos(attacker.rotationYaw * piDiv180) * velocityMultiplier);
		victim.velocityChanged = true;
	}
}