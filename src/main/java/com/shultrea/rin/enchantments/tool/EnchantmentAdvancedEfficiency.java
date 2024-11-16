package com.shultrea.rin.enchantments.tool;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdvancedEfficiency extends EnchantmentBase {
	
	public EnchantmentAdvancedEfficiency(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedEfficiency;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedEfficiency;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedEfficiency, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedEfficiency, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedEfficiency, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedEfficiency, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedEfficiency;
	}
	
	//TODO
	@SubscribeEvent
	public void HandleEnchant(PlayerEvent.BreakSpeed fEvent) {
		ItemStack tool = fEvent.getEntityLiving().getHeldItemMainhand();
		if(tool == null) return;
		float OrigSpeed = fEvent.getOriginalSpeed();
		if(!((tool.getItem() instanceof ItemTool))) return;
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedEfficiency, tool) <= 0) return;
		int levelExtremeEfficency = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedEfficiency, tool);
		if((tool.getItem().canHarvestBlock(fEvent.getState()))) {
			float Speed = ((levelExtremeEfficency + 1) * levelExtremeEfficency * levelExtremeEfficency) + 3;
			fEvent.setNewSpeed(OrigSpeed + Speed);
		}
		else if(ForgeHooks.isToolEffective(fEvent.getEntityLiving().world, fEvent.getPos(), tool)) {
			float Speed = ((levelExtremeEfficency + 1) * levelExtremeEfficency * levelExtremeEfficency) + 3;
			fEvent.setNewSpeed(OrigSpeed + Speed);
		}
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAxeAttackEfficiency(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(!(stack.getItem() instanceof ItemAxe)) return;
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedEfficiency, stack);
		if(enchantmentLevel <= 0) return;
		float chance = 0.15f + 0.15f * enchantmentLevel;
		if(fEvent.getEntityLiving() instanceof EntityPlayer)
			if(fEvent.getEntityLiving().getEntityWorld().rand.nextFloat() < chance) {
				EntityPlayer player = (EntityPlayer)fEvent.getEntityLiving();
				player.getCooldownTracker().setCooldown(Items.SHIELD, 100);
				player.resetActiveHand();
				fEvent.getEntityLiving().getEntityWorld().setEntityState(player, (byte)30);
			}
	}
}