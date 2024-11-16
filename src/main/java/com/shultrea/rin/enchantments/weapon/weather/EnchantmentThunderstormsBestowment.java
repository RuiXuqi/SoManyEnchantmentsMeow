package com.shultrea.rin.enchantments.weapon.weather;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentThunderstormsBestowment extends EnchantmentBase {
	
	public EnchantmentThunderstormsBestowment(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.thunderstormsBestowment;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.thunderstormsBestowment;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.thunderstormsBestowment, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.thunderstormsBestowment, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.thunderstormsBestowment, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.thunderstormsBestowment, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.thunderstormsBestowment;
	}
	
	public boolean isValidPlayer(Entity entity) {
		if(entity instanceof EntityPlayer) {
			if(((EntityPlayer)entity).getHeldItemMainhand() != null) {
				return level(((EntityPlayer)entity).getHeldItemMainhand()) > 0;
			}
		}
		return false;
	}
	
	public boolean isValidMob(Entity entity) {
		if(entity instanceof EntityMob || entity instanceof EntityAnimal) {
			if(((EntityMob)entity).getHeldItemMainhand() != null) {
				if(level(((EntityMob)entity).getHeldItemMainhand()) > 0) {
					return true;
				}
			}
			if(((EntityAnimal)entity).getHeldItemMainhand() != null) {
				return level(((EntityAnimal)entity).getHeldItemMainhand()) > 0;
			}
		}
		return false;
	}
	
	public int level(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.thunderstormsBestowment, stack);
	}
	
	/**
	 *
	 */
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.thunderstormsBestowment, stack);
		if(enchantmentLevel <= 0) return;
		float damage = fEvent.getAmount();
		if(EnchantmentsUtility.entityCanSeeSky(attacker)) {
			if(attacker.world.isThundering()) {
				float modifiedDamage = EnchantmentsUtility.modifyDamage(damage, 0.0f, 1.25f, 1.00f, enchantmentLevel);
				fEvent.setAmount(modifiedDamage);
			} else {
				float modifiedDamage = EnchantmentsUtility.modifyDamage(damage, 0.00f, -0.5f, 1.0f, enchantmentLevel);
				fEvent.setAmount(modifiedDamage);
				if(fEvent.getEntity().world.rand.nextInt(800) < 2 + (enchantmentLevel * 2)) {
					EnchantmentsUtility.setThunderstorm(fEvent.getEntityLiving().getEntityWorld());
				}
			}
		}
		else {
			float modifiedDamage = EnchantmentsUtility.modifyDamage(damage, -0.05f, -0.75f, 1.0f, enchantmentLevel);
			fEvent.setAmount(modifiedDamage);
		}
	}
}