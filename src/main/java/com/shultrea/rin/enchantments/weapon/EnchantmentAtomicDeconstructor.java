package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAtomicDeconstructor extends EnchantmentBase {
	
	public EnchantmentAtomicDeconstructor(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.atomicDeconstructor;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.atomicDeconstructor;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.atomicDeconstructor, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.atomicDeconstructor, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.atomicDeconstructor;
	}
	
	//TODO
	@SubscribeEvent
	public void HandleEnchant(LivingAttackEvent fEvent) {
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.atomicDeconstructor, dmgSource) <= 0) return;
		int levelAtomicDeconstructor = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.atomicDeconstructor, dmgSource);
		if(fEvent.getEntityLiving().getEntityWorld().rand.nextInt(3000) < (levelAtomicDeconstructor * 3)) {
			float DeadHP = fEvent.getEntityLiving().getMaxHealth();
			fEvent.getEntityLiving().hurtResistantTime = 0;
			if(!(fEvent.getEntityLiving() instanceof EntityPlayer)) {
				fEvent.getEntityLiving().attackEntityFrom(SoManyEnchantments.Deconstruct, DeadHP * 100f);
			}
		}
	}
}