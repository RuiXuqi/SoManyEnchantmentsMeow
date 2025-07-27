package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class EnchantmentSwiftSwimming extends EnchantmentBase {
	
	private static final UUID SWIFTSWIMMING_UUID = UUID.fromString("a612fe81-132f-4c58-a335-13c4ae5cba21");
	
	public EnchantmentSwiftSwimming(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.swiftSwimming;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.swiftSwimming;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.swiftSwimming, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.swiftSwimming, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.swiftSwimming, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.swiftSwimming, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.swiftSwimming;
	}
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
		if(!this.isEnabled()) return;
		if(event.getEntityLiving() == null) return;
		if(event.getEntityLiving().world.isRemote) return;
		if(event.getEntityLiving().ticksExisted%20 != 0) return;
		
		EntityLivingBase user = event.getEntityLiving();
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, user);
		IAttributeInstance swimSpeed = user.getEntityAttribute(EntityLivingBase.SWIM_SPEED);
		
		if(level > 0) addSwimSpeedModifier(level, swimSpeed);
		else swimSpeed.removeModifier(SWIFTSWIMMING_UUID);
	}
	
	private static void addSwimSpeedModifier(int level, IAttributeInstance instance) {
		double amount = 1.30D + 0.4D * (double)level;
		AttributeModifier modifier = new AttributeModifier(SWIFTSWIMMING_UUID, "SwiftSwimmingBoost", amount, 1);
		AttributeModifier previous = instance.getModifier(SWIFTSWIMMING_UUID);
		if(previous == null) {
			instance.applyModifier(modifier);
		}
		else if(previous.getAmount() != amount) {
			instance.removeModifier(SWIFTSWIMMING_UUID);
			instance.applyModifier(modifier);
		}
	}
}