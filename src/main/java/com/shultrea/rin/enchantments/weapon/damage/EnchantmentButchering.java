package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentButchering extends EnchantmentBase {
	
	public EnchantmentButchering(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.butchering;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.butchering;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.butchering, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.butchering, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.butchering, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.butchering, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.butchering;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.butchering, stack);
		if(enchantmentLevel <= 0) return;
		if(fEvent.getEntity() instanceof EntityAnimal) {
			float Damage = 1.25f + enchantmentLevel * 1.25f;
			UtilityAccessor.damageTarget(fEvent.getEntityLiving(), SoManyEnchantments.PhysicalDamage, Damage);
			fEvent.setAmount(fEvent.getAmount());
		}
	}
}