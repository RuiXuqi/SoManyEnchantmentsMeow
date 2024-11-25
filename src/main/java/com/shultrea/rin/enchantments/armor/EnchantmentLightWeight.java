package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EnchantmentLightWeight extends EnchantmentBase {
	
	public EnchantmentLightWeight(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.lightWeight;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.lightWeight;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lightWeight, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lightWeight, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.lightWeight, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.lightWeight, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.lightWeight;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onPlayerTick(PlayerTickEvent event) {
		if(!this.isEnabled()) return;
		if(event.phase != Phase.END) return;
		EntityPlayer player = event.player;
		if(player == null) return;
		if(!player.world.isRemote) return;
		if(player.onGround) return;
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(this, player);
		if(enchantmentLevel > 0) {
			if(!player.capabilities.isFlying) {
				float cloud = MathHelper.clamp((float)enchantmentLevel / 3.0F, 0.33F, 1);
				if(player.fallDistance >= 3.0F) {
					for(int i = 0; i < 3; i++) {
						player.world.spawnParticle(EnumParticleTypes.CLOUD, player.posX, player.posY - 2.0D, player.posZ, (player.getRNG().nextFloat() - 0.5F) / 2.0F * cloud, -0.5D * cloud, (player.getRNG().nextFloat() - 0.5F) / 2.0F * cloud);
					}
				}
				else if((player.isSprinting())) {
					player.world.spawnParticle(EnumParticleTypes.CLOUD, player.posX, player.posY - 1.5D, player.posZ, (player.getRNG().nextFloat() - 0.5F) / 2.0F * cloud, 0.1D * cloud, (player.getRNG().nextFloat() - 0.5F) / 2.0F * cloud);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingJumpEvent(LivingJumpEvent event) {
		if(!this.isEnabled()) return;
		if(event.getEntityLiving() == null) return;
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(this, event.getEntityLiving());
		if(enchantmentLevel > 0) {
			event.getEntityLiving().motionY *= (1.05D + (double)enchantmentLevel * 0.15D);
		}
	}
	
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if(!this.isEnabled()) return;
		if(event.getEntityLiving() == null) return;
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(this, event.getEntityLiving());
		if(enchantmentLevel > 0) {
			event.setDistance(MathHelper.clamp(event.getDistance() - 4 - enchantmentLevel * 2, 0, 20));
		}
	}
}