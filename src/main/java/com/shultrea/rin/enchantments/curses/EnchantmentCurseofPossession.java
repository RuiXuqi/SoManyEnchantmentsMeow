package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCurseofPossession extends EnchantmentCurse {
	
	public EnchantmentCurseofPossession(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.curseOfPossession;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.curseOfPossession;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.curseOfPossession, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.curseOfPossession, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.curseOfPossession, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.curseOfPossession, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfPossession;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemTossEvent(ItemTossEvent event) {
		if(!this.isEnabled()) return;
		EntityPlayer player = event.getPlayer();
		if(player == null) return;
		if(player.isCreative()) return;
		ItemStack origStack = event.getEntityItem().getItem();
		if(origStack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, origStack);
		if(level > 0) {
			ItemStack copyStack = origStack.copy();
			boolean flag = player.inventory.addItemStackToInventory(copyStack);
			if(!flag && !player.world.isRemote) {
				EntityItem entityItem = player.entityDropItem(copyStack, (-0.3F + player.getEyeHeight()));
				if(entityItem != null) {
					entityItem.setOwner(player.getName());
					entityItem.setNoPickupDelay();
					entityItem.setEntityInvulnerable(true);
				}
			}
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
		if(!this.isEnabled()) return;
		if(!(event.getEntity() instanceof EntityItem)) return;
		EntityItem item = (EntityItem)event.getEntity();
		if(item.getItem().isEmpty()) return;
		ItemStack copyStack = item.getItem().copy();
		
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.curseOfPossession, copyStack);
		if(level > 0) {
			EntityPlayer player = event.getWorld().getClosestPlayerToEntity(item, 8.0);
			if(player != null && !player.isCreative()) {
				if(player.isEntityAlive()) {
					if(player.addItemStackToInventory(copyStack)) {
						event.setCanceled(true);
						return;
					}
				}
				if(ModConfig.miscellaneous.curseOfPossessionDeathDeletion) {
					item.lifespan = 5;
				}
				else {
					item.lifespan = 200; //10 seconds to pick up if inventory full or player dead
				}
				item.setPickupDelay(10);
			}
		}
	}
}