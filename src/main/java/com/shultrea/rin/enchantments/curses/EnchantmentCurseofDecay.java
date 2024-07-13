package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Interfaces.IEnchantmentCurse;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_040;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCurseofDecay extends EnchantmentBase implements IEnchantmentCurse {
	
	public EnchantmentCurseofDecay(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.curseOfDecay;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.curseOfDecay;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 45;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 45;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfDecay;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof EnchantmentCurseofPossession);
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	//TODO
	@SubscribeEvent
	public void onThrow(ItemTossEvent fEvent) {
		if(fEvent.getEntityItem() != null)
			if(EnchantmentHelper.getEnchantmentLevel(Smc_040.CurseofDecay, fEvent.getEntityItem().getItem()) > 0) {
				fEvent.getEntityItem().lifespan = 80;
				fEvent.getEntityItem().setPickupDelay(10);
			}
	}
}