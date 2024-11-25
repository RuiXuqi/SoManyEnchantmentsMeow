package com.shultrea.rin.enchantments.shield;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
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

public class EnchantmentEmpoweredDefence extends EnchantmentBase {
	
	public EnchantmentEmpoweredDefence(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.empoweredDefence;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.empoweredDefence;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.empoweredDefence, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.empoweredDefence, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.empoweredDefence, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.empoweredDefence, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.empoweredDefence;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOW)
	public void EmpoweredDefenceEvent(LivingAttackEvent fEvent) {
		if(!(fEvent.getEntity() instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)fEvent.getEntity();

		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.empoweredDefence, victim);
		if(enchantmentLevel <= 0) return;

		if(!EnchantUtil.canBlockDamageSource(fEvent.getSource(), victim)) return;
		if(victim.getRNG().nextInt(100) > 20 + (enchantmentLevel * 5)) return;

		fEvent.setCanceled(true);
		Entity attacker = fEvent.getSource().getImmediateSource();
		EnchantUtil.knockBackIgnoreKBRes(attacker, 0.4f + 0.2F * enchantmentLevel, victim.posX - attacker.posX, victim.posZ - attacker.posZ);
		float revengeDamage = fEvent.getAmount() * 0.225f * enchantmentLevel;
		if(victim instanceof EntityPlayer)
			attacker.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)victim), revengeDamage);
		else
			attacker.attackEntityFrom(DamageSource.causeMobDamage(victim), revengeDamage);
		victim.hurtResistantTime = victim.maxHurtResistantTime-5;
	}
}