package com.shultrea.rin.enchantments.shield;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
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
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.naturalBlocking, stack) || super.canApply(stack);
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

		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.naturalBlocking, victim);
		if(enchantmentLevel <= 0) return;

		ItemStack shield = victim.getHeldItemMainhand();
		if(shield.isEmpty() || !shield.getItem().isShield(shield, victim)) {
			shield = victim.getHeldItemOffhand();
			if(shield.isEmpty() || !shield.getItem().isShield(shield, victim)) return;
		}

		if(victim.getActiveItemStack() != shield) {
			float damageReduction = 0.1f + enchantmentLevel * 0.1f;
			float blockedDamage = fEvent.getAmount() * damageReduction;
			fEvent.setAmount(fEvent.getAmount() - blockedDamage);
			if(enchantmentLevel >= 3)
				shield.damageItem((int) (1 + 1.25f * blockedDamage), victim);
			else
				shield.damageItem((int) (1 + 1.75f * blockedDamage), victim);
		}
	}
}