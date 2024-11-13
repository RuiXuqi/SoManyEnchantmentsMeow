package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentEvasion extends EnchantmentBase {
	
	public EnchantmentEvasion(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.evasion;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.evasion;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.evasion, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.evasion, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.evasion, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.evasion, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.evasion;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = false)
	public void HandleEnchant(LivingAttackEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		if("arrow".equals(fEvent.getSource().damageType)) return;
		EntityLivingBase victim = fEvent.getEntityLiving();
		if(victim == null) return;
		EntityLivingBase attacker = (EntityLivingBase) fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		if(EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.trueStrike, attacker) > 0) {
			if(EnchantmentsUtility.RANDOM.nextInt(100) < 75) return;
		}
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.evasion, victim);
		if(enchantmentLevel <= 0) return;
		double randX = 0.65 + victim.getRNG().nextDouble() * 0.25f;
		randX = victim.getRNG().nextBoolean() ? randX * -1 : randX;
		double randZ = 0.65 + victim.getRNG().nextDouble() * 0.25f;
		randZ = victim.getRNG().nextBoolean() ? randZ * -1 : randZ;
		if(fEvent.getEntityLiving().world.rand.nextInt(100) < 5 + (enchantmentLevel * 15)) {
			if(!attacker.world.isRemote && ModConfig.miscellaneous.evasionDodgeEffect)
				EnchantmentsUtility.ImprovedKnockBack(victim, 0.7f, (attacker.posX - victim.posX) * randX, (attacker.posZ - victim.posZ) * randZ);
			victim.getEntityWorld().playSound(null, victim.posX, victim.posY, victim.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 0.3f, victim.getRNG().nextFloat() * 2.25f + 0.75f);
			fEvent.setCanceled(true);
			victim.hurtResistantTime = 15 + 5 * enchantmentLevel;
		}
	}
}