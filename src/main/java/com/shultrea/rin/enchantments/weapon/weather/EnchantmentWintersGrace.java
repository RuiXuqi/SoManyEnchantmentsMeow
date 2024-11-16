package com.shultrea.rin.enchantments.weapon.weather;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.utility_sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentWintersGrace extends EnchantmentBase {
	
	public EnchantmentWintersGrace(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.wintersGrace;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.wintersGrace;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.wintersGrace, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.wintersGrace, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.wintersGrace, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.wintersGrace, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.wintersGrace;
	}
	
	public int level(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.wintersGrace, stack);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(stack == null) return;
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.wintersGrace, stack);
		if(enchantmentLevel <= 0) return;
		BlockPos blockpos = attacker.getPosition();
		float Damage = fEvent.getAmount();
		if(attacker.world.isRaining() && EnchantmentsUtility.entityCanSeeSky(attacker) && isInColdTemperature(attacker, attacker.getEntityWorld().getBiome(blockpos))) {

			float modifiedDamage = EnchantmentsUtility.modifyDamage(Damage, 0.10f, 0.90f, 1.15f, enchantmentLevel);
			fEvent.setAmount(modifiedDamage);

			fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 60, enchantmentLevel - 3));
			if(enchantmentLevel >= 2)
				fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, enchantmentLevel - 2));
			if(enchantmentLevel >= 4)
				fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 60, enchantmentLevel - 4));
		}
		else if((!attacker.world.isRaining() && EnchantmentsUtility.entityCanSeeSky(attacker)) || !isInColdTemperature(attacker, attacker.getEntityWorld().getBiome(blockpos))) {
			float modifiedDamage = EnchantmentsUtility.modifyDamage(Damage, 0.00f, -0.6f, 1.0f, enchantmentLevel);
			fEvent.setAmount(modifiedDamage);
			if(Math.random() * 5 < 0.02f + enchantmentLevel)
				EnchantmentsUtility.setRaining(attacker.getEntityWorld());
		}
		else if(!EnchantmentsUtility.entityCanSeeSky(attacker)) {
			float modifiedDamage = EnchantmentsUtility.modifyDamage(Damage, 0.00f, -0.8f, 1.0f, enchantmentLevel);
			fEvent.setAmount(modifiedDamage);
		}
	}

	public static boolean isInColdTemperature(EntityLivingBase attacker, Biome biome) {
		BlockPos pos = new BlockPos(attacker.posX, attacker.posY, attacker.posZ);
		float temperature = biome.getTemperature(pos);
		return temperature < 1.51f;
	}

}