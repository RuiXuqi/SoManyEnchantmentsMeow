package com.shultrea.rin.util.enchantmenttypes;

import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ITypeMatcher {
    boolean matches(EnchantmentBase enchantment, ItemStack stack, Item item, String itemName);
}
