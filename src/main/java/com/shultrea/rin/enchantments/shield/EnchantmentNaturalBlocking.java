package com.shultrea.rin.enchantments.shield;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
		return ConfigProvider.canItemApply(ModConfig.canApply.naturalBlocking, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.naturalBlocking, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.naturalBlocking;
	}
	
	//Applies at normal to reduce damage after additions but before multiplications
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		if(victim.isActiveItemStackBlocking() || event.getSource().isUnblockable()) return;

		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, victim);
		if(level > 0) {
			ItemStack shield = victim.getHeldItemMainhand();
			if(shield.isEmpty() || !shield.getItem().isShield(shield, victim)) {
				shield = victim.getHeldItemOffhand();
				if(shield.isEmpty() || !shield.getItem().isShield(shield, victim)) return;
			}
			float blockedDamage = event.getAmount() * (0.1F * (float)level);
			event.setAmount(event.getAmount() - blockedDamage);
			shield.damageItem((int)(1.0F + 1.5F * blockedDamage), victim);
		}
	}
}