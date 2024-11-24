package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentInefficient extends EnchantmentCurse {
	
	public EnchantmentInefficient(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.inefficient;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.inefficient;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.inefficient, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.inefficient, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.inefficient, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.inefficient, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.inefficient;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
		if(!this.isEnabled()) return;
		EntityPlayer player = event.getEntityPlayer();
		if(player == null) return;
		ItemStack stack = player.getHeldItemMainhand();
		if(!(stack.getItem() instanceof ItemTool)) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(stack.canHarvestBlock(event.getState()) || ForgeHooks.isToolEffective(player.world, event.getPos(), stack)) {
				float speed = (float)level * 0.65F + 2.0F;
				event.setNewSpeed((event.getNewSpeed() / speed) - (0.15F * level));
			}
		}
	}
}