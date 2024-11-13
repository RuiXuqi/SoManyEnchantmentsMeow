package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.Interfaces.IEnchantmentProtection;
import com.shultrea.rin.Interfaces.IEnhancedEnchantment;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdvancedFireProtection extends EnchantmentBase implements IEnchantmentProtection, IEnhancedEnchantment {
	
	public EnchantmentAdvancedFireProtection(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedFireProtection;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedFireProtection;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedFireProtection, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedFireProtection, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedFireProtection, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedFireProtection, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedFireProtection;
	}
	
	//TODO
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 : source.isFireDamage() ? level * 3 : 0;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void updateFireEntity(LivingUpdateEvent fEvent) {
		if(!(fEvent.getEntity() instanceof EntityLivingBase)) return;
		EntityLivingBase victim = fEvent.getEntityLiving();
		int totalFireProtectLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.advancedFireProtection, victim);
		if(totalFireProtectLevel <= 0) return;
		if(!victim.isBurning()) return;
		if(Math.random() < 0.025 + (0.0025 * totalFireProtectLevel)) {
			victim.extinguish();
		}
	}
}