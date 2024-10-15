package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Interfaces.IEnchantmentDamage;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentBluntness extends EnchantmentCurse {
	
	public EnchantmentBluntness(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.bluntness;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.bluntness;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 14 + 14 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.bluntness;
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return 0.0F - 1.0f * level;
	}
	
	//TODO maybe allow on all
	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return !(ench instanceof EnchantmentDamage) && !(ench instanceof IEnchantmentDamage) && super.canApplyTogether(ench);
	}
}