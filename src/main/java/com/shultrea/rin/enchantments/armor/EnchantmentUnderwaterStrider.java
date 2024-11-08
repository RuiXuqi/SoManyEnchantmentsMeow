package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentWaterWalker;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.UUID;

public class EnchantmentUnderwaterStrider extends EnchantmentBase {
	
	public static final UUID CACHED_UUID = UUID.fromString("a612fe81-132f-4c58-a335-13c4ae5cba21");
	
	public EnchantmentUnderwaterStrider(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
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
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.underwaterStrider;
	}
	
	@SubscribeEvent
	public void onUnder(PlayerTickEvent e) {
		if(e.phase == Phase.END) return;
		EntityPlayer player = e.player;
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.underwaterStrider, player);
		IAttributeInstance s = player.getEntityAttribute(EntityLivingBase.SWIM_SPEED);
		if(enchantmentLevel <= 0) {
			removeSpeed(s, player);
			return;
		}
		IBlockState water = player.world.getBlockState(new BlockPos(player.posX, player.posY + 1, player.posZ));
		if(player.isInWater() && water.getMaterial() == Material.WATER) {
			removeSpeed(s, player);
			addSpeed(s, player);
		}
		else removeSpeed(s, player);
	}
	
	private void addSpeed(IAttributeInstance s, EntityLivingBase p) {
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.underwaterStrider, p);
		if(s.getModifier(CACHED_UUID) != null) return;
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "moveSpeed", 1.30 + ((double)enchantmentLevel * 0.4D), 1);
		s.removeModifier(modSpeed);
		s.applyModifier(modSpeed);
	}
	
	private void removeSpeed(IAttributeInstance s, EntityLivingBase p) {
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.underwaterStrider, p);
		if(s.getModifier(CACHED_UUID) == null) return;
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "moveSpeed", 1.30 + ((double)enchantmentLevel * 0.4D), 1);
		s.removeModifier(modSpeed);
	}
}