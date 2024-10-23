package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.Interfaces.IEnchantmentRune;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EnchantmentRuneRevival extends EnchantmentBase implements IEnchantmentRune {
	
	public EnchantmentRuneRevival(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runeRevival;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runeRevival;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.runeRevival, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.runeRevival, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runeRevival;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return super.canApplyAtEnchantingTable(stack) && !(stack.getItem() instanceof ItemArmor);
		//return !(stack.getItem() instanceof ItemArmor) ? super.canApplyAtEnchantingTable(stack) : false;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return super.canApplyTogether(fTest) && !(fTest instanceof IEnchantmentRune);
//	}
	
	@SubscribeEvent
	public void TriggeredEvent(PlayerDestroyItemEvent fEvent) {
		int amount = 0;
		EntityPlayer entity = (EntityPlayer)fEvent.getEntityLiving();
		ItemStack tool = fEvent.getOriginal();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeRevival, tool);
		if(enchantmentLevel <= 0) return;
		enchantmentLevel = Math.min(enchantmentLevel, 2);
		int durability = tool.getMaxDamage();
		float extraChance = durability > 1250 ? 0 :
							durability > 750 ? 4 :
							durability > 200 ? 6 :
							durability > 80 ? 8 :
							10;
		extraChance = extraChance / 100f;
		boolean test = EnchantmentsUtility.RANDOM.nextDouble() < (0.15f + (enchantmentLevel * 0.15f)) + extraChance;
		//System.out.println(extraChance + " - Extra Chance");
		if(test) {
			ItemStack newTool = tool.copy();
			newTool.setItemDamage((int)(newTool.getItemDamage() - (newTool.getItemDamage() * (0.5f * enchantmentLevel))));
			List<ItemStack> list = entity.inventory.mainInventory;
			//boolean flag = entity.inventory.addItemStackToInventory(newTool);
			int slot = entity.inventory.currentItem;
			//if(!flag){
			/**
			 for (int i = 0; i < list.size(); ++i)
			 {
			 if (((ItemStack)list.get(i)).isEmpty() && i != slot)
			 {
			 entity.inventory.mainInventory.set(i, newTool);
			 }
			 }
			 */
			boolean flag = EnchantmentsUtility.addItemStackToInventoryWithoutHolding(newTool, entity.inventory);
			if(!flag) {
				EntityItem entityItem = entity.entityDropItem(newTool, 1.3f);
				entityItem.setOwner(entity.getName());
				entityItem.setNoPickupDelay();
				entityItem.setEntityInvulnerable(true);
			}
		}
	}
}