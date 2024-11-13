package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.Interfaces.IEnchantmentProtection;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EnchantmentPhysicalProtection extends EnchantmentBase implements IEnchantmentProtection {
	
	public EnchantmentPhysicalProtection(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.physicalProtection;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.physicalProtection;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.physicalProtection, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.physicalProtection, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.physicalProtection, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.physicalProtection, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.physicalProtection;
	}
	
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 :
			   (!(source.isMagicDamage() || source.isFireDamage() || source.isExplosion() || source.isProjectile() || source.damageType.equals("outOfWorld") || source.damageType.equals("drown") || source.damageType.equals("generic") || source.damageType.equals("wither") || source.damageType.equals("lightningBolt") || source.damageType.equals("inFire") || source.damageType.equals("onFire") || source.damageType.equals("hotFloor") || source.damageType.equals("Ethereal") || source.damageType.equals("Culled")) ?
				level * 3 : 0);
	}
}