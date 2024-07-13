package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Interfaces.IEnchantmentCurse;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.EnchantmentTrueStrike;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCurseofInaccuracy extends EnchantmentBase implements IEnchantmentCurse {
	
	public EnchantmentCurseofInaccuracy(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.curseOfInaccuracy;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.curseOfInaccuracy;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int ench) {
		return 15 + (ench - 1) * 15;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int ench) {
		return this.getMinEnchantability(ench) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfInaccuracy;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return !(fTest instanceof EnchantmentTrueStrike) && super.canApplyTogether(fTest);
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onAttack(LivingAttackEvent e) {
		if(!EnchantmentsUtility.checkEventCondition(e, this)) return;
		EntityLivingBase eb = (EntityLivingBase)e.getSource().getTrueSource();
		int level = EnchantmentHelper.getEnchantmentLevel(this, eb.getHeldItemMainhand());
		if(level <= 0) return;
		if(eb.getRNG().nextInt(10) < level * 2) e.setCanceled(true);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onRangeAttack(EntityJoinWorldEvent e) {
		if(e.getEntity() instanceof EntityArrow) {
			EntityArrow arrow = (EntityArrow)e.getEntity();
			if(arrow.shootingEntity == null) return;
			if(!(arrow.shootingEntity instanceof EntityLivingBase)) return;
			EntityLivingBase shooter = (EntityLivingBase)arrow.shootingEntity;
			float level = EnchantmentHelper.getEnchantmentLevel(this, shooter.getHeldItemMainhand());
			if(level <= 0) level = EnchantmentHelper.getEnchantmentLevel(this, shooter.getHeldItemOffhand());
			if(level <= 0) return;
			/**
			 if(shooter.getRNG().nextInt(10) < level * 2){
			 level = 0.99f - level * 0.065f;
			 double minRandomX = MathHelper.clamp(EnchantmentsUtility.RANDOM.nextDouble() * arrow.motionX, arrow.motionX * level, arrow.motionX);
			 double minRandomY = MathHelper.clamp(EnchantmentsUtility.RANDOM.nextDouble() * arrow.motionY, arrow.motionY * level, arrow.motionY);
			 double minRandomZ = MathHelper.clamp(EnchantmentsUtility.RANDOM.nextDouble() * arrow.motionZ, arrow.motionZ * level, arrow.motionZ);
			 
			 arrow.motionX *= minRandomX;
			 arrow.motionY *= minRandomY;
			 arrow.motionZ *= minRandomZ;
			 }
			 */
			if(shooter.getRNG().nextInt(10) < level * 2) {
				float velocity = (float)((Math.abs(arrow.motionX) + Math.abs(arrow.motionY) + Math.abs(arrow.motionZ)) / 3);
				arrow.shoot(shooter, shooter.rotationPitch, shooter.rotationYaw, 0, velocity, level * 10);
			}
		}
	}
}