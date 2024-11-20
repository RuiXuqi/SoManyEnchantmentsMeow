package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentUnsheathing extends EnchantmentBase {
	
	public EnchantmentUnsheathing(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.unsheathing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.unsheathing;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.unsheathing, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.unsheathing, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.unsheathing, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.unsheathing, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.unsheathing;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAttack(LivingAttackEvent e) {
		if(!e.isCanceled() && e.getAmount() > 0) {
			if(e.getSource().getTrueSource() != null && e.getEntityLiving() != null && e.getEntityLiving() instanceof EntityPlayer) {
				EntityPlayer victim = (EntityPlayer)e.getEntityLiving();
				InventoryPlayer inv = victim.inventory;
				for(int x = 0; x < InventoryPlayer.getHotbarSize(); x++) {
					ItemStack stack = inv.getStackInSlot(x);
					int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.unsheathing, stack);
					if(enchantmentLevel <= 0) continue;
					if(EnchantUtil.RANDOM.nextInt(2) <= -1 + enchantmentLevel)
						continue;
					if(inv.getStackInSlot(inv.currentItem) == stack) continue;
					ItemStack s = inv.getStackInSlot(inv.currentItem);
					inv.setInventorySlotContents(inv.currentItem, stack);
					inv.setInventorySlotContents(x, s);
					stack.setAnimationsToGo(0);
				}
			}
		}
	}
}