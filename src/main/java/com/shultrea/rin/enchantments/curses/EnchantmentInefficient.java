package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentInefficient extends EnchantmentBase {
	
	public EnchantmentInefficient(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
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
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.inefficient;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != Enchantments.EFFICIENCY && fTest != Smc_010.ExtremeEfficency;
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	//TODO
	@SubscribeEvent
	public void HandleEnchant(PlayerEvent.BreakSpeed fEvent) {
		ItemStack tool = fEvent.getEntityLiving().getHeldItemMainhand();
		if(tool == null) return;
		float OrigSpeed = fEvent.getOriginalSpeed();
		if(!((tool.getItem() instanceof ItemTool))) return;
		float blockHardness = fEvent.getState().getBlockHardness(fEvent.getEntityLiving().world, fEvent.getPos());
		if(EnchantmentHelper.getEnchantmentLevel(Smc_010.Inefficient, tool) <= 0) return;
		int levelInefficent = EnchantmentHelper.getEnchantmentLevel(Smc_010.Inefficient, tool);
		if((tool.getItem().canHarvestBlock(fEvent.getState()))) {
			float Speed = (levelInefficent * 0.65f) + 2.0f;
			fEvent.setNewSpeed((OrigSpeed / Speed) - (0.15f * levelInefficent));
		}
		else if(ForgeHooks.isToolEffective(fEvent.getEntityLiving().world, fEvent.getPos(), tool)) {
			float Speed = ((levelInefficent * 0.65f) + 2.0f);
			fEvent.setNewSpeed((OrigSpeed / Speed) - (0.15f * levelInefficent));
		}
	}
}