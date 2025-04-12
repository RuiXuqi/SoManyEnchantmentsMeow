package com.shultrea.rin.enchantments;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentAdvancedMending extends EnchantmentBase {
	
	public EnchantmentAdvancedMending(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedMending;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedMending;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedMending, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedMending, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.advancedMending, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.advancedMending, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedMending;
	}
	
	@SubscribeEvent
	public void onPlayerPickupXPEvent(PlayerPickupXpEvent event) {
		if(!this.isEnabled()) return;
		EntityPlayer player = event.getEntityPlayer();
		if(player == null) return;
		EntityXPOrb orb = event.getOrb();
		if(orb == null) return;
		if(orb.xpValue <= 0) return;
		
		if(ModConfig.miscellaneous.enableDoubleXPBug) {
			player.addExperience(orb.xpValue);
		}
		
		ItemStack stack = ItemStack.EMPTY;
		if(ModConfig.miscellaneous.advancedMendingPrioritizeDamaged) {
			List<ItemStack> equipment = this.getEntityEquipment(player);
			
			if(!equipment.isEmpty()) {
				List<ItemStack> damagedEquipment = new ArrayList<>();
				for(ItemStack itemstack : equipment) {
					if(!itemstack.isEmpty() && EnchantmentHelper.getEnchantmentLevel(this, itemstack) > 0 && itemstack.isItemDamaged()) {
						damagedEquipment.add(itemstack);
					}
				}
				if(!damagedEquipment.isEmpty()) {
					stack = damagedEquipment.get(player.getRNG().nextInt(damagedEquipment.size()));
				}
			}
		}
		else {
			stack = EnchantmentHelper.getEnchantedItem(this, player);
		}
		
		if(!stack.isEmpty() && stack.isItemDamaged()) {
			float ratio = stack.getItem().getXpRepairRatio(stack);
			int value = Math.min(roundAverage(orb.xpValue * ratio * 1.5F), stack.getItemDamage());
			orb.xpValue -= roundAverage(value / ratio);
			stack.setItemDamage(stack.getItemDamage() - value);
			if(orb.xpValue < 0) orb.xpValue = 0;
		}
	}
	
	private static int roundAverage(float value) {
		double floor = Math.floor(value);
		return (int) floor + (Math.random() < value - floor ? 1 : 0);
	}
}