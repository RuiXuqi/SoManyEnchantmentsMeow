package com.shultrea.rin.enchantments.fishing;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentAdvancedLure extends EnchantmentBase {
	
	public EnchantmentAdvancedLure(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	//TODO
	public static int getValue(ItemStack stack) {
		if(!EnchantmentRegistry.advancedLure.isEnabled()) return 0;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedLure, stack);
		if(level <= 0) return 0;
		int toReturn = level + 1;
		if(Math.random() < 0.15f) toReturn = level + 2;
		return toReturn;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedLure;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedLure;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedLure, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedLure, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedLure, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedLure, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedLure;
	}
	
	/*
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onEvent(EntityJoinWorldEvent fEvent)
	{
		if (fEvent.getEntity() instanceof EntityFishHook)
		{
			EntityFishHook hook = (EntityFishHook) fEvent.getEntity();
			EntityLivingBase fisher = hook.getAngler();
			ItemStack fishingRod = fisher.getHeldItemMainhand();
			
			if(fishingRod == null || fishingRod == ItemStack.EMPTY){
				
			fishingRod = fisher.getHeldItemOffhand();
			if(fishingRod == null || fishingRod == ItemStack.EMPTY){
				
				return;
			}
		}
		
			
			//ItemStack item = EnchantmentHelper.getEnchantedItem(EnchantmentRegistry.AdvancedLure, fisher);
			
			int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.AdvancedLure, fishingRod);
			
			if(level <= 0)
			return;
			
			hook.setLureSpeed(level + 1);
			if(Math.random() < 0.15f){
			hook.setLureSpeed(level + 2);
			}
		}
	}
	 */
}