package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDisarmament extends EnchantmentBase {
	
	public EnchantmentDisarmament(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.disarmament;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.disarmament;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.disarmament, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.disarmament, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.disarmament, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.disarmament, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.disarmament;
	}
	
	//TODO
	@SubscribeEvent
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase victim = fEvent.getEntityLiving();
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.disarmament, stack);
		if(enchantmentLevel <= 0) return;
		if(Math.random() * 100 < 25) {
			victim.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 1));
			victim.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 40 + (enchantmentLevel * 20), 254));
			if(Math.random() * 100 < (enchantmentLevel * 5)) {
				disarm(victim);
			}
		}
	}

	public static void disarm(EntityLivingBase entityLiving) {
		if(!entityLiving.getHeldItemMainhand().isEmpty()) {
			entityLiving.entityDropItem(entityLiving.getHeldItemMainhand(), 4.5f);
			entityLiving.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
		}
		else if(!entityLiving.getHeldItemOffhand().isEmpty()) {
			entityLiving.entityDropItem(entityLiving.getHeldItemOffhand(), 4.5f);
			entityLiving.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
		}
	}
}