package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EnchantmentCurseofHolding extends EnchantmentCurse {
	
	public EnchantmentCurseofHolding(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.curseOfHolding;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.curseOfHolding;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.curseOfHolding, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.curseOfHolding, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.curseOfHolding, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.curseOfHolding, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfHolding;
	}
	
	@SubscribeEvent
	public static void onPlayerTickEvent(PlayerTickEvent event) {
		if(event.phase != Phase.START) return;
		EntityPlayer player = event.player;
		if(player == null) return;
		if(!player.world.isRemote && player.ticksExisted%9 == 0) {
			int level = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.curseOfHolding, player);
			if(level > 0) {
				player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10, level - 1, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 10, level > 1 ? 1 : 0, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 10, level - 1, false, false));
				if(level > 1) {
					player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 10, level - 1, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 10, level - 1, false, false));
				}
			}
		}
	}
}