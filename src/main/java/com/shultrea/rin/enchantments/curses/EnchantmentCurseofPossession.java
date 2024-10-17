package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Main_Sector.EnchantabilityConfig;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCurseofPossession extends EnchantmentCurse {
	
	public EnchantmentCurseofPossession(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, EntityEquipmentSlot.values());
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
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfPossession;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentCurseofDecay) && super.canApplyTogether(ench);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onItemTossEvent(ItemTossEvent event) {
		EntityPlayer player = event.getPlayer();
		if(player == null) return;
		if(event.getEntityItem().getItem().isEmpty()) return;
		ItemStack copyStack = event.getEntityItem().getItem().copy();
		
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.curseOfPossession, copyStack);
		if(level > 0 && !player.isCreative()) {
			boolean flag = player.inventory.addItemStackToInventory(copyStack);
			if(!flag) {
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
	public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
		if(event.getEntity() == null || !(event.getEntity() instanceof EntityItem)) return;
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
				item.lifespan = 5;
				item.setPickupDelay(10);
			}
		}
	}
}