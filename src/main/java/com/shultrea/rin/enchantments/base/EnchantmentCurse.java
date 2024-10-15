package com.shultrea.rin.enchantments.base;

import com.shultrea.rin.Main_Sector.ModConfig;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public abstract class EnchantmentCurse extends EnchantmentBase {
	
	public EnchantmentCurse(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return ModConfig.miscellaneous.canCursesBeAppliedAtEnchantingTable && super.canApplyAtEnchantingTable(stack);
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return ModConfig.miscellaneous.canCursesBeAppliedToBooks && super.isAllowedOnBooks();
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.RED + "";
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
}