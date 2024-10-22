package com.shultrea.rin.enchantments.fishing;

import com.shultrea.rin.Main_Sector.EnchantabilityConfig;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentAdvancedLuckOfTheSea extends EnchantmentBase {
	
	public EnchantmentAdvancedLuckOfTheSea(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots) {
		super(name, rarity, type, slots);
	}
	
	//TODO
	public static int getValue(ItemStack stack) {
		if(!EnchantmentRegistry.advancedLuckOfTheSea.isEnabled()) return 0;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedLuckOfTheSea, stack);
		if(level <= 0) return 0;
		int toReturn = level * 2 + 2;
		if(Math.random() < 0.15f) toReturn = level * 3 + 3;
		return toReturn;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedLuckOfTheSea;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedLuckOfTheSea;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedLuckOfTheSea, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedLuckOfTheSea, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedLuckOfTheSea;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Enchantments.LUCK_OF_THE_SEA && super.canApplyTogether(fTest);
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
			int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.AdvancedLuckOfTheSea, fishingRod);
			
			if(level <= 0)
			return;
			
			hook.setLuck(level * 2 + 2);
			if(Math.random() < 0.15f){
			hook.setLuck(level * 3 + 3);
			}
		}
	}
	*/
}