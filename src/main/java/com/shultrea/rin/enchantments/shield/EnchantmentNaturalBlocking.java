package com.shultrea.rin.enchantments.shield;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentNaturalBlocking extends EnchantmentBase {
	
	public EnchantmentNaturalBlocking(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.naturalBlocking;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.naturalBlocking;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.naturalBlocking, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.naturalBlocking, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.naturalBlocking, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.naturalBlocking, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.naturalBlocking;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void naturalBlock(LivingDamageEvent fEvent) {
		if(!(fEvent.getEntity() instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)fEvent.getEntity();
		if(victim == null) return;
		ItemStack shield = victim.getHeldItemMainhand();
		if(shield.isEmpty() || !shield.getItem().isShield(shield, victim)) {
			shield = victim.getHeldItemOffhand();
			if(shield.isEmpty()) return;
		}
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.naturalBlocking, shield) <= 0) return;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.naturalBlocking, shield);
		float damage = fEvent.getAmount() - fEvent.getAmount() * (level * 0.1f + 0.1f);
		if(shield.getItem().isShield(shield, victim) && victim.getActiveItemStack() != shield) {
			float percentage = damage / fEvent.getAmount();
			percentage = 1 - percentage;
			fEvent.setAmount(damage);
			if(level >= 3) {
				shield.damageItem((int)(1.25f * fEvent.getAmount() * percentage) + 1, victim);
			}
			else {
				shield.damageItem((int)(1.75f * fEvent.getAmount() * percentage) + 1, victim);
			}
		}
	}
}