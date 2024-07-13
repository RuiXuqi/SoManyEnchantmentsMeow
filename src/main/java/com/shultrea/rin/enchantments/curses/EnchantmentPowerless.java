package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Interfaces.IEnchantmentCurse;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_030;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentPowerless extends EnchantmentBase implements IEnchantmentCurse {
	
	public EnchantmentPowerless(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.powerless;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.powerless;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 12 + (par1 - 1) * 12;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.powerless;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		if(fTest == Enchantments.POWER || fTest == Smc_030.AdvancedPower) return false;
		return super.canApplyTogether(fTest);
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onEvent(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityArrow) {
			EntityArrow arrow = (EntityArrow)event.getEntity();
			EntityLivingBase shooter = (EntityLivingBase)arrow.shootingEntity;
			if(shooter == null) return;
			ItemStack bow = shooter.getActiveItemStack();
			if(bow == null || bow == ItemStack.EMPTY) {
				bow = shooter.getHeldItemOffhand();
				if(bow == null || bow == ItemStack.EMPTY) {
					return;
				}
			}
			int p = EnchantmentHelper.getEnchantmentLevel(Smc_030.Powerless, bow);
			if(p > 0) {
				double ArrowDamage = arrow.getDamage();
				arrow.setDamage(ArrowDamage - (0.5D + (double)p * 0.50D));
				if(p < 3) {
					if(Math.random() < p * 0.4f) arrow.setIsCritical(false);
				}
				else if(p >= 3) {
					arrow.setIsCritical(false);
				}
			}
		}
	}
}