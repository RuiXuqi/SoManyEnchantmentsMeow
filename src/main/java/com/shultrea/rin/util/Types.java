package com.shultrea.rin.util;

import com.google.common.base.Predicate;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Types {
	
	private static final Predicate<Item> PREDICATE_ALL = item -> true;
	private static final Predicate<Item> PREDICATE_NONE = item -> false;
	private static final Predicate<Item> PREDICATE_AXE = item -> item instanceof ItemAxe;
	private static final Predicate<Item> PREDICATE_PICKAXE = item -> item instanceof ItemPickaxe;
	private static final Predicate<Item> PREDICATE_HOE = item -> item instanceof ItemHoe;
	private static final Predicate<Item> PREDICATE_SPADE = item -> item instanceof ItemSpade;
	private static final Predicate<Item> PREDICATE_SHIELD = item -> item instanceof ItemShield;
	private static final Predicate<Item> PREDICATE_COMBAT_MELEE = item -> item instanceof ItemSword || item instanceof ItemAxe;
	private static final Predicate<Item> PREDICATE_COMBAT_ALL = item -> item instanceof ItemSword || item instanceof ItemAxe || item instanceof ItemBow;
	private static final Predicate<Item> PREDICATE_TOOL = item -> item instanceof ItemTool || item instanceof ItemSword;
	
	public static final EnumEnchantmentType ALL = EnumHelper.addEnchantmentType("All", PREDICATE_ALL);
	public static final EnumEnchantmentType NONE = EnumHelper.addEnchantmentType("None", PREDICATE_NONE);
	public static final EnumEnchantmentType COMBAT_AXE = EnumHelper.addEnchantmentType("Combat Axe", PREDICATE_AXE);
	public static final EnumEnchantmentType PICKAXE = EnumHelper.addEnchantmentType("Tool Pickaxe", PREDICATE_PICKAXE);
	public static final EnumEnchantmentType HOE = EnumHelper.addEnchantmentType("Tool Hoe", PREDICATE_HOE);
	public static final EnumEnchantmentType SPADE = EnumHelper.addEnchantmentType("Tool Shovel", PREDICATE_SPADE);
	public static final EnumEnchantmentType SHIELD = EnumHelper.addEnchantmentType("Combat Shield", PREDICATE_SHIELD);
	public static final EnumEnchantmentType COMBAT = EnumHelper.addEnchantmentType("Combat", PREDICATE_COMBAT_MELEE);
	public static final EnumEnchantmentType COMBAT_WEAPON = EnumHelper.addEnchantmentType("Combat Weapon", PREDICATE_COMBAT_ALL);
	public static final EnumEnchantmentType ALL_TOOL = EnumHelper.addEnchantmentType("All Tools", PREDICATE_TOOL);
	
	public static void initEnchantmentTabs() {
		CreativeTabs combatTab = CreativeTabs.COMBAT;
		CreativeTabs toolTab = CreativeTabs.TOOLS;
		List<EnumEnchantmentType> combatList = new ArrayList<>(Arrays.asList(combatTab.getRelevantEnchantmentTypes()));
		List<EnumEnchantmentType> toolList = new ArrayList<>(Arrays.asList(toolTab.getRelevantEnchantmentTypes()));
		
		combatList.add(COMBAT_AXE);
		combatList.add(SHIELD);
		combatList.add(COMBAT);
		combatList.add(COMBAT_WEAPON);
		
		toolList.add(ALL);
		toolList.add(NONE);
		toolList.add(PICKAXE);
		toolList.add(HOE);
		toolList.add(SPADE);
		toolList.add(ALL_TOOL);
		
		EnumEnchantmentType[] finalCombatEnchantmentType = combatList.toArray(new EnumEnchantmentType[0]);
		EnumEnchantmentType[] finalToolEnchantmentType = toolList.toArray(new EnumEnchantmentType[0]);
		
		combatTab.setRelevantEnchantmentTypes(finalCombatEnchantmentType);
		toolTab.setRelevantEnchantmentTypes(finalToolEnchantmentType);
	}
}