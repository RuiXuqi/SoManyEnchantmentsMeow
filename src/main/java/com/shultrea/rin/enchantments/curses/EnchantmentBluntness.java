package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Interfaces.IEnchantmentDamage;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class EnchantmentBluntness extends EnchantmentBase {
	
	public EnchantmentBluntness(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
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
	
	//TODO
	@Override
	public boolean canApply(ItemStack fTest) {
		return super.canApply(fTest) && fTest.getItem() instanceof ItemAxe;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.bluntness;
	}
	
	//TODO
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return (-0.50f - 0.50f * level);
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof EnchantmentDamage) && !(fTest instanceof IEnchantmentDamage);
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
}