package com.shultrea.rin.util.enchantmenttypes;

import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.Types;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnumEnchantmentTypeMatcher implements ITypeMatcher {
    private final EnumEnchantmentType type;

    public EnumEnchantmentTypeMatcher(EnumEnchantmentType type){
        this.type = type;
    }

    @Override
    public boolean matches(EnchantmentBase enchantment, ItemStack stack, Item item, String itemName){
        // This tries to catch all items that pretend to be normal MC items without inheriting from them
        // which then try to get the correct enchantments by overriding item.canApplyAtEnchantingTable(enchantment) using
        // enchantment.type == myPretended_vanillaEnumEnchantment_type

        // The main issue why we cant use the normal system is that vanilla only allows one type per enchant
        // but some SME enchants just go on multiple item types
        if(ModConfig.canApply.allowCustomItems) {
            enchantment.type = this.type;
            boolean doesMatch = item.canApplyAtEnchantingTable(stack, enchantment);
            enchantment.type = Types.NONE; //could paste in an earlier copy but in EnchantmentBase.init its always set to NONE and should be NONE

            return doesMatch;
        }
        else return type.canEnchantItem(item);
    }
}
