package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.UUID;

public class EnchantmentStrengthenedVitality extends EnchantmentBase {
	
	public static final UUID CACHED_UUID = UUID.fromString("e681-134f-4c54-a535-29c3ae5c7a21");
	
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
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.strengthenedVitality, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.strengthenedVitality;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void updateHealth(PlayerTickEvent fEvent) {
		if(fEvent.phase != Phase.END) return;
		int i = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strengthenedVitality, fEvent.player);
		if(i <= 0) {
			RemoveHealth(fEvent.player);
			return;
		}
		AddHealth(fEvent.player);
	}
	
	private void AddHealth(EntityPlayer fEntity) {
		int level = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strengthenedVitality, fEntity);
		IAttributeInstance healthAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
		AttributeModifier modHealth = new AttributeModifier(CACHED_UUID, "StrengthenedHealthBoost", 0.1D * level, 2);
		healthAttr.removeModifier(modHealth);
		healthAttr.applyModifier(modHealth);
		if(healthAttr.getModifier(CACHED_UUID) != null) {}
	}
	
	private void RemoveHealth(EntityLivingBase fEntity) {
		int level = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.strengthenedVitality, fEntity);
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
		if(speedAttr.getModifier(CACHED_UUID) == null) return;
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "StrengthenedHealthBoost", 0.2D * level, 2);
		speedAttr.removeModifier(modSpeed);
	}
}