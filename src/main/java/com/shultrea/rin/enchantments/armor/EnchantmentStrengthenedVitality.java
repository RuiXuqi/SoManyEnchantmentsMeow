package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.UUID;

public class EnchantmentStrengthenedVitality extends EnchantmentBase {
	
	private static final UUID STRENGTHENEDVITALITY_UUID = UUID.fromString("eabe21c1-dc07-4ca0-9992-468ef792ef49");
	
	public EnchantmentStrengthenedVitality(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.strengthenedVitality;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.strengthenedVitality;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.strengthenedVitality, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.strengthenedVitality, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.strengthenedVitality, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.strengthenedVitality, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.strengthenedVitality;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
		if(!this.isEnabled()) return;
		if(event.getEntityLiving() == null) return;
		if(event.getEntityLiving().world.isRemote) return;
		if(event.getEntityLiving().ticksExisted%20 != 0) return;
		
		EntityLivingBase user = event.getEntityLiving();
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, user);
		IAttributeInstance maxHealth = user.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		
		if(level > 0) addMaxHealthModifier(level, maxHealth);
		else maxHealth.removeModifier(STRENGTHENEDVITALITY_UUID);
	}
	
	private static void addMaxHealthModifier(int level, IAttributeInstance instance) {
		double amount = 0.1D * (double)level;
		AttributeModifier modifier = new AttributeModifier(STRENGTHENEDVITALITY_UUID, "StrengthenedVitalityBoost", amount, 2);
		AttributeModifier previous = instance.getModifier(STRENGTHENEDVITALITY_UUID);
		if(previous == null) {
			instance.applyModifier(modifier);
		}
		else if(previous.getAmount() != amount) {
			instance.removeModifier(STRENGTHENEDVITALITY_UUID);
			instance.applyModifier(modifier);
		}
	}
}