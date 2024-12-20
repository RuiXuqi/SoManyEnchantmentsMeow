package com.shultrea.rin.enchantments.weapon;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.ItemMixin
 */
public class EnchantmentSwifterSlashes extends EnchantmentBase {
	
	//public static final UUID CACHED_UUID = UUID.fromString("e6109481-134f-4c54-a535-29c3ae5c7a21");
	
	public EnchantmentSwifterSlashes(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.swifterSlashes;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.swifterSlashes;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.swifterSlashes, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.swifterSlashes, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.swifterSlashes, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.swifterSlashes, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.swifterSlashes;
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingAttack(LivingAttackEvent event) {
		if (!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		EntityLivingBase attacker = ((EntityLivingBase) event.getSource().getTrueSource());
		if (attacker == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if (enchantmentLevel <= 0) return;
		EntityLivingBase target = event.getEntityLiving();
		if((target.hurtResistantTime <= target.maxHurtResistantTime / 2.0F)) return;

		if (attacker.getRNG().nextFloat() < 0.02F * (float) enchantmentLevel) {
			target.hurtResistantTime = 0;
		}
	}
}