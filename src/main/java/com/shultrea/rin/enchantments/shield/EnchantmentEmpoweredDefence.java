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

		if(!EnchantmentsUtility.canBlockDamageSource(fEvent.getSource(), victim)) return;
		if(victim.getRNG().nextInt(100) > 20 + (enchantmentLevel * 5)) return;

		fEvent.setCanceled(true);
		Entity attacker = fEvent.getSource().getImmediateSource();
		EnchantmentsUtility.knockBackIgnoreKBRes(attacker, 0.4f + 0.2F * enchantmentLevel, victim.posX - attacker.posX, victim.posZ - attacker.posZ);
		float revengeDamage = fEvent.getAmount() * 0.225f * enchantmentLevel;
		if(victim instanceof EntityPlayer)
			attacker.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)victim), revengeDamage);
		else
			attacker.attackEntityFrom(DamageSource.causeMobDamage(victim), revengeDamage);
		victim.hurtResistantTime = victim.maxHurtResistantTime-5;
	}
	/** @SubscribeEvent public void OnShieldCooldown(LivingUpdateEvent fEvent){
	if(!(fEvent.getEntityLiving() instanceof EntityPlayer))
	return;
	EntityPlayer player = (EntityPlayer) fEvent.getEntityLiving();
	int levelED = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.EmpoweredDefence, player);
	float cooldown = player.getCooldownTracker().getCooldown(Items.SHIELD, 100f);
	if(cooldown == 0)
	return;
	if(cooldown <= 100){
	player.getCooldownTracker().setCooldown(Items.SHIELD, (int)(100 / levelED));
	player.resetActiveHand();
	fEvent.getEntityLiving().getEntityWorld().setEntityState(player, (byte)30);
	}
	}
	 */
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