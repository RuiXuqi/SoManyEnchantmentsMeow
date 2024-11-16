package com.shultrea.rin.enums;

import com.google.common.base.Predicate;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class Types {
	
	static final Predicate<Item> a = item -> item instanceof Item;
	public static final EnumEnchantmentType ALL = EnumHelper.addEnchantmentType("All", a);
	static final Predicate<Item> ax = item -> item instanceof ItemAxe;
	public static final EnumEnchantmentType COMBAT_AXE = EnumHelper.addEnchantmentType("Combat Axe", ax);
	static final Predicate<Item> pax = item -> item instanceof ItemPickaxe;
	public static final EnumEnchantmentType PICKAXE = EnumHelper.addEnchantmentType("Tool Pickaxe", pax);
	static final Predicate<Item> ho = item -> item instanceof ItemHoe;
	public static final EnumEnchantmentType HOE = EnumHelper.addEnchantmentType("Tool Hoe", ho);
	static final Predicate<Item> shov = item -> item instanceof ItemSpade;
	public static final EnumEnchantmentType SPADE = EnumHelper.addEnchantmentType("Tool Shovel", shov);
	static final Predicate<Item> cons = item -> item instanceof ItemAppleGold;
	public static final EnumEnchantmentType COMBAT_GOLDEN_APPLE = EnumHelper.addEnchantmentType("Golden Apple", cons);
	static final Predicate<Item> shie = item -> item instanceof ItemShield;
	public static final EnumEnchantmentType SHIELD = EnumHelper.addEnchantmentType("Combat Shield", shie);
	static final Predicate<Item> caas = item -> item instanceof ItemSword || item instanceof ItemAxe;
	public static final EnumEnchantmentType COMBAT = EnumHelper.addEnchantmentType("Combat", caas);
	static final Predicate<Item> rom = item -> item instanceof ItemSword || item instanceof ItemAxe || item instanceof ItemBow;
	public static final EnumEnchantmentType COMBAT_WEAPON = EnumHelper.addEnchantmentType("Combat Weapon", rom);
	static final Predicate<Item> allTool = item -> item instanceof ItemTool || item instanceof ItemSword;
	public static final EnumEnchantmentType ALL_TOOL = EnumHelper.addEnchantmentType("All Tools", allTool);
	static final Predicate<Item> n = item -> false;
	public static final EnumEnchantmentType NONE = EnumHelper.addEnchantmentType("None", n);
	
	public Types() {
	}
	
	public static void initializeEnchantmentTab() {
		CreativeTabs c = CreativeTabs.COMBAT;
		CreativeTabs a = CreativeTabs.TOOLS;
		List<EnumEnchantmentType> combatType = new ArrayList();
		List<EnumEnchantmentType> toolTypes = new ArrayList();
		for(int x = 0; x < c.getRelevantEnchantmentTypes().length; x++) {
			EnumEnchantmentType[] e = c.getRelevantEnchantmentTypes();
			combatType.add(e[x]);
		}
		for(int x = 0; x < a.getRelevantEnchantmentTypes().length; x++) {
			EnumEnchantmentType[] e = a.getRelevantEnchantmentTypes();
			toolTypes.add(e[x]);
		}
		combatType.add(COMBAT_AXE);
		combatType.add(COMBAT_GOLDEN_APPLE);
		combatType.add(SHIELD);
		combatType.add(COMBAT);
		combatType.add(COMBAT_WEAPON);
		toolTypes.add(ALL_TOOL);
		toolTypes.add(HOE);
		toolTypes.add(SPADE);
		toolTypes.add(PICKAXE);
		toolTypes.add(ALL);
		toolTypes.add(NONE);
		EnumEnchantmentType[] finalCombatEnchantmentType = combatType.toArray(new EnumEnchantmentType[combatType.size()]);
		EnumEnchantmentType[] finalToolEnchantmentType = toolTypes.toArray(new EnumEnchantmentType[toolTypes.size()]);
		CreativeTabs.COMBAT.setRelevantEnchantmentTypes(finalCombatEnchantmentType);
		CreativeTabs.TOOLS.setRelevantEnchantmentTypes(finalToolEnchantmentType);
	}
}
