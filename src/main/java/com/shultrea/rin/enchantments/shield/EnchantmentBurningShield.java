package com.shultrea.rin.enchantments.shield;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.utility_sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentBurningShield extends EnchantmentBase {
	
	public EnchantmentBurningShield(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.burningShield;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.burningShield;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.burningShield, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.burningShield, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.burningShield, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.burningShield, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.burningShield;
	}

	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void shieldBurn(LivingAttackEvent fEvent) {
		if(!(fEvent.getEntity() instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)fEvent.getEntity();

		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.burningShield, victim);
		if(enchantmentLevel <= 0) return;

		if(!EnchantmentsUtility.canBlockDamageSource(fEvent.getSource(), victim)) return;
		if(victim.getRNG().nextInt(100) > 40 + (enchantmentLevel * 10)) return;

		Entity attacker = fEvent.getSource().getImmediateSource();
		float revengeDamage = fEvent.getAmount() * enchantmentLevel * 0.1f;
		if(victim instanceof EntityPlayer)
			attacker.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)victim).setFireDamage(), revengeDamage);
		else
			attacker.attackEntityFrom(DamageSource.causeMobDamage(victim).setFireDamage(), revengeDamage);
		attacker.setFire(4 + enchantmentLevel * 2);
	}
}
//@Override
/**
 * public void onUserHurt(EntityLivingBase user, Entity attacker, int level){
 *
 * if(GotHit == true){
 *
 * double XMot = attacker.motionX; double ZMot = attacker.motionZ; double YMot = attacker.motionY;
 *
 * XMot += (double)(-MathHelper.sin(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float) 0.02f + level * 0.03125F);
 * ZMot += (double)(MathHelper.cos(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)0.02f + level * 0.03125F);
 * attacker.motionX = XMot /1.1D; attacker.motionZ = ZMot /1.1D; attacker.motionY = YMot + level * 0.125;
 *
 * }
 *
 * } }
 */