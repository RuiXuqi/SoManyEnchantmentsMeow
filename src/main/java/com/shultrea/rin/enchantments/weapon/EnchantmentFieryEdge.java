package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.EnchantabilityConfig;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.weapon.damage.EnchantmentWaterAspect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class EnchantmentFieryEdge extends EnchantmentBase {
	
	public EnchantmentFieryEdge(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.fieryEdge;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.fieryEdge;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.fieryEdge, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.fieryEdge, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.fieryEdge;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		target.setFire(level * 6);
		Random randy = new Random();
		if(victim.isBurning() && randy.nextInt(10 - level * 2) < 3) {
			victim.hurtResistantTime = 0;
		}
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != Enchantments.FIRE_ASPECT && !(fTest instanceof EnchantmentWaterAspect);
	}
}