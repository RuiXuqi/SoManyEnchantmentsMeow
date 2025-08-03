package com.shultrea.rin.util;

import com.google.common.base.Predicate;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

public abstract class Types {
	
	private static final Predicate<Item> PREDICATE_ALL = item -> true;
	private static final Predicate<Item> PREDICATE_NONE = item -> false;
	private static final Predicate<Item> PREDICATE_AXE = item -> item instanceof ItemAxe;
	private static final Predicate<Item> PREDICATE_PICKAXE = item -> item instanceof ItemPickaxe;
	private static final Predicate<Item> PREDICATE_HOE = item -> item instanceof ItemHoe;
	private static final Predicate<Item> PREDICATE_SPADE = item -> item instanceof ItemSpade;
	private static final Predicate<Item> PREDICATE_SHIELD = item -> item instanceof ItemShield;
	
	public static final EnumEnchantmentType ALL = EnumHelper.addEnchantmentType("All", PREDICATE_ALL);
	public static final EnumEnchantmentType NONE = EnumHelper.addEnchantmentType("None", PREDICATE_NONE);
	public static final EnumEnchantmentType AXE = EnumHelper.addEnchantmentType("Combat Axe", PREDICATE_AXE);
	public static final EnumEnchantmentType PICKAXE = EnumHelper.addEnchantmentType("Tool Pickaxe", PREDICATE_PICKAXE);
	public static final EnumEnchantmentType HOE = EnumHelper.addEnchantmentType("Tool Hoe", PREDICATE_HOE);
	public static final EnumEnchantmentType SPADE = EnumHelper.addEnchantmentType("Tool Shovel", PREDICATE_SPADE);
	public static final EnumEnchantmentType SHIELD = EnumHelper.addEnchantmentType("Combat Shield", PREDICATE_SHIELD);
}