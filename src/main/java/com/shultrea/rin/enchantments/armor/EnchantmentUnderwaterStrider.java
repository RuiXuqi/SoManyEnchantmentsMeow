package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class EnchantmentUnderwaterStrider extends EnchantmentBase {
	
	private static final UUID UNDERWATERSTRIDER_UUID = UUID.fromString("a612fe81-132f-4c58-a335-13c4ae5cba21");
	
	public EnchantmentUnderwaterStrider(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.underwaterStrider;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.underwaterStrider;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.underwaterStrider, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.underwaterStrider, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.underwaterStrider, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.underwaterStrider, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.underwaterStrider;
	}
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
		if(event.getEntityLiving() == null) return;
		if(event.getEntityLiving().world.isRemote) return;
		if(!(event.getEntityLiving() instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer)event.getEntityLiving();
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, player);
		IAttributeInstance swimSpeed = player.getEntityAttribute(EntityLivingBase.SWIM_SPEED);
		
		if(level > 0) addSwimSpeedModifier(level, swimSpeed);
		else swimSpeed.removeModifier(UNDERWATERSTRIDER_UUID);
	}
	
	private static void addSwimSpeedModifier(int level, IAttributeInstance instance) {
		double amount = 1.30D + 0.4D * (double)level;
		AttributeModifier modifier = new AttributeModifier(UNDERWATERSTRIDER_UUID, "UnderwaterStriderBoost", amount, 1);
		AttributeModifier previous = instance.getModifier(UNDERWATERSTRIDER_UUID);
		if(previous == null) {
			instance.applyModifier(modifier);
		}
		else if(previous.getAmount() != amount) {
			instance.removeModifier(UNDERWATERSTRIDER_UUID);
			instance.applyModifier(modifier);
		}
	}
}