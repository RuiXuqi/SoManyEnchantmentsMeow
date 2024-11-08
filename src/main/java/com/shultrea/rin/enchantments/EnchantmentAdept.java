package com.shultrea.rin.enchantments;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdept extends EnchantmentBase {
	
	public EnchantmentAdept(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.adept;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.adept;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.adept, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.adept, level);
	}
	
	//TODO
	@Override
	public boolean canApply(ItemStack fTest) {
		return fTest.getItem() instanceof ItemAxe || super.canApply(fTest);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.adept;
	}
	
	//TODO
	@SubscribeEvent
	public void onDeath(LivingExperienceDropEvent fEvent) {
		EntityPlayer player = fEvent.getAttackingPlayer();
		if(player == null) return;
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.adept, player);
		if(enchantmentLevel <= 0) return;
		//Don't add experience to drops that otherwise would have no experience
		if(fEvent.getOriginalExperience() <= 0) return;
		if(fEvent.getEntityLiving() != null && !fEvent.getEntityLiving().isNonBoss())
			fEvent.setDroppedExperience(2 + enchantmentLevel + (int)(fEvent.getOriginalExperience() * (0.75f + 0.5f * enchantmentLevel)));
		else
			fEvent.setDroppedExperience(2 + enchantmentLevel + (int)(fEvent.getOriginalExperience() * (1.05f + 0.15f * enchantmentLevel)));
	}
}