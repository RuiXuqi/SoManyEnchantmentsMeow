package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment handling in com.shultrea.rin.properties.ArrowPropertiesHandler
 */
public class EnchantmentStrafe extends EnchantmentBase {
	
	public EnchantmentStrafe(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.strafe;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.strafe;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.strafe, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.strafe, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.strafe, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.strafe, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.strafe;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingEntityUseItem(LivingEntityUseItemEvent.Tick event) {
		if(!this.isEnabled()) return;
		ItemStack bow = event.getItem();
		if(!(bow.getItem() instanceof ItemBow)) return;

		int levelStrafe = EnchantmentHelper.getEnchantmentLevel(this, bow);
		if(levelStrafe <= 0) return;

		int duration = event.getDuration();

		//Speeding up by less than one tick per tick means we can only speed up by one tick every x ticks
		if (levelStrafe < 4) {
			if(duration % (5 - levelStrafe) == 0)
			//1 -> every 4th tick, (+20%, 5 ticks instead of 4)
			//2 -> every 3rd tick, (+33%, 4 ticks instead of 3)
			//3 -> every 2nd tick, (+50%, 3 ticks instead of 2)
				event.setDuration(duration - 1);
		//Otherwise we can just add x ticks to the elapsed tick
		} else {
			//4 -> 1 added tick every tick, (+100%, 2 ticks instead of 1)
			//5 -> 2 added ticks every tick, (+200%, 3 ticks instead of 1)
			//Etc, going up linearly from here
			event.setDuration(duration - (levelStrafe - 3));
		}
	}
}