package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class EnchantmentBreachedPlating extends EnchantmentCurse {
	
	private static final UUID BREACHEDPLATING_UUID = UUID.fromString("8e07ebc9-f6f6-439c-8e65-00a70cd0b226");
	
	public EnchantmentBreachedPlating(String name, Enchantment.Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.breachedPlating;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.breachedPlating;
	}
	
	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.breachedPlating, level);
	}
	
	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.breachedPlating, level);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.breachedPlating, stack) && super.canApplyAtEnchantingTable(stack);
	}
	
	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.breachedPlating, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.breachedPlating;
	}
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
		if(!this.isEnabled()) return;
		if(event.getEntityLiving() == null) return;
		if(event.getEntityLiving().world.isRemote) return;
		if(event.getEntityLiving().ticksExisted%20 != 0) return;
		
		EntityLivingBase user = event.getEntityLiving();
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, user);
		IAttributeInstance armor = user.getEntityAttribute(SharedMonsterAttributes.ARMOR);
		
		if(level > 0) addArmorDebuffModifier(level, armor);
		else armor.removeModifier(BREACHEDPLATING_UUID);
	}
	
	private static void addArmorDebuffModifier(int level, IAttributeInstance instance) {
		double amount = -0.1D * (double)level;
		AttributeModifier modifier = new AttributeModifier(BREACHEDPLATING_UUID, "BreachedPlatingDebuff", amount, 2);
		AttributeModifier previous = instance.getModifier(BREACHEDPLATING_UUID);
		if(previous == null) {
			instance.applyModifier(modifier);
		}
		else if(previous.getAmount() != amount) {
			instance.removeModifier(BREACHEDPLATING_UUID);
			instance.applyModifier(modifier);
		}
	}
}