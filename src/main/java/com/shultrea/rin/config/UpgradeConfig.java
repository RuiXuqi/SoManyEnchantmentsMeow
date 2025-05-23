package com.shultrea.rin.config;

import net.minecraftforge.common.config.Config;

public class UpgradeConfig {

	@Config.Comment("Using the enchantment upgrading mechanic conflicts with some other mods which also change enchantment table behavior. Set to false to fully disable the mechanic.")
	@Config.Name(".Enable Upgrading Mechanic")
	@Config.RequiresMcRestart
	public boolean enableUpgrading =  true;
	
	@Config.Comment("Enchantment tiers will be upgraded in this order")
	@Config.Name("Upgrade Tier Order")
	public String[] enchantUpgradeOrder = {
			"somanyenchantments:lessersharpness, minecraft:sharpness, somanyenchantments:advancedsharpness, somanyenchantments:supremesharpness",
			"somanyenchantments:lessersmite, minecraft:smite, somanyenchantments:advancedsmite, somanyenchantments:supremesmite",
			"somanyenchantments:lesserbaneofarthropods, minecraft:bane_of_arthropods, somanyenchantments:advancedbaneofarthropods, somanyenchantments:supremebaneofarthropods",
			"somanyenchantments:lesserfireaspect, minecraft:fire_aspect, somanyenchantments:advancedfireaspect, somanyenchantments:supremefireaspect",
			"minecraft:knockback, somanyenchantments:advancedknockback",
			"minecraft:looting, somanyenchantments:advancedlooting",
			"minecraft:efficiency, somanyenchantments:advancedefficiency",
			"minecraft:luck_of_the_sea, somanyenchantments:advancedluckofthesea",
			"minecraft:lure, somanyenchantments:advancedlure",
			"minecraft:mending, somanyenchantments:advancedmending",
			"somanyenchantments:lesserflame, minecraft:flame, somanyenchantments:advancedflame, somanyenchantments:supremeflame",
			"minecraft:punch, somanyenchantments:advancedpunch",
			"minecraft:power, somanyenchantments:advancedpower",
			"minecraft:feather_falling, somanyenchantments:advancedfeatherfalling",
			"minecraft:blast_protection, somanyenchantments:advancedblastprotection",
			"minecraft:fire_protection, somanyenchantments:advancedfireprotection",
			"minecraft:projectile_protection, somanyenchantments:advancedprojectileprotection",
			"minecraft:protection, somanyenchantments:advancedprotection",
			"minecraft:thorns, somanyenchantments:burningthorns, somanyenchantments:advancedthorns"
	};

	@Config.Comment("Enchantment groups and their curse equivalent. If there is no matching curse form but it should still be able to be affected by curse mechanics, use 'none'")
	@Config.Name("Curse Equivalents")
	public String[] enchantUpgradeCursing = {
			"somanyenchantments:lessersharpness, minecraft:sharpness, somanyenchantments:advancedsharpness, somanyenchantments:supremesharpness, somanyenchantments:bluntness",
			"somanyenchantments:lessersmite, minecraft:smite, somanyenchantments:advancedsmite, somanyenchantments:supremesmite, somanyenchantments:bluntness",
			"somanyenchantments:lesserbaneofarthropods, minecraft:bane_of_arthropods, somanyenchantments:advancedbaneofarthropods, somanyenchantments:supremebaneofarthropods, somanyenchantments:bluntness",
			"somanyenchantments:lesserfireaspect, minecraft:fire_aspect, somanyenchantments:advancedfireaspect, somanyenchantments:supremefireaspect, somanyenchantments:extinguish",
			"minecraft:knockback, somanyenchantments:advancedknockback, somanyenchantments:dragging",
			"minecraft:looting, somanyenchantments:advancedlooting, somanyenchantments:ascetic",
			"minecraft:efficiency, somanyenchantments:advancedefficiency, somanyenchantments:inefficient",
			"minecraft:luck_of_the_sea, somanyenchantments:advancedluckofthesea, somanyenchantments:ascetic",
			"minecraft:lure, somanyenchantments:advancedlure, none",
			"minecraft:mending, somanyenchantments:advancedmending, somanyenchantments:rusted",
			"somanyenchantments:lesserflame, minecraft:flame, somanyenchantments:advancedflame, somanyenchantments:supremeflame, somanyenchantments:extinguish",
			"minecraft:punch, somanyenchantments:advancedpunch, somanyenchantments:dragging",
			"minecraft:power, somanyenchantments:advancedpower, somanyenchantments:powerless",
			"minecraft:feather_falling, somanyenchantments:advancedfeatherfalling, somanyenchantments:heavyweight",
			"somanyenchantments:lightweight, somanyenchantments:evasion, somanyenchantments:heavyweight",
			"minecraft:blast_protection, somanyenchantments:advancedblastprotection, somanyenchantments:breachedplating",
			"minecraft:fire_protection, somanyenchantments:advancedfireprotection, somanyenchantments:breachedplating",
			"minecraft:projectile_protection, somanyenchantments:advancedprojectileprotection, somanyenchantments:breachedplating",
			"minecraft:protection, somanyenchantments:advancedprotection, somanyenchantments:supremeprotection, somanyenchantments:breachedplating",
			"minecraft:thorns, somanyenchantments:burningthorns, somanyenchantments:advancedthorns, somanyenchantments:meltdown",
			"somanyenchantments:luckmagnification, somanyenchantments:adept, mujmajnkraftsbettersurvival:education, somanyenchantments:ascetic"
	};

	@Config.Comment("Upgrading enchantments will use this material in the enchanting table lapis slot")
	@Config.Name("Upgrade Token on Level Upgrades")
	public String upgradeTokenLevel = "minecraft:prismarine_shard";

	@Config.Comment("Upgrading enchantments will use this material in the enchanting table lapis slot")
	@Config.Name("Upgrade Token on Tier Upgrades")
	public String upgradeTokenTier = "minecraft:prismarine_shard";

	@Config.Comment("Upgrading enchantment levels will use up this amount of the token")
	@Config.Name("Token Level Cost")
	public int upgradeTokenAmountLevel = 1;
	
	@Config.Comment("Upgrading enchantment tiers will use up this amount of the token")
	@Config.Name("Token Tier Cost")
	public int upgradeTokenAmountTier = 8;

	@Config.Comment("Calculation mode to determine how many XP levels are used while upgrading enchantments" + "\n" +
			"0: Single level" + "\n" +
			"1: How much the resulting enchant would cost on an anvil" + "\n" +
			"2: Minimum enchantability of the resulting enchant")
	@Config.Name("XP Cost Base Calculation Mode")
	public int levelCostMode = 1;

	@Config.Comment("Multiplier for the cost determined by the base enchantment upgrade cost calculation")
	@Config.Name("XP Cost Multiplier")
	public float levelCostMultiplier = 2.0F;

	@Config.Comment("Restricts enchantment upgrading to only work on enchanted books")
	@Config.Name("Only Allow Upgrading on Enchant Books")
	public boolean onlyAllowOnBooks = false;

	@Config.Comment("Only allow upgrading tiers of enchantments if the upgraded tier is compatible with the other enchants on the item")
	@Config.Name("Only Allow Compatible Tier Upgrades")
	public boolean onlyAllowCompatible = true;

	@Config.Comment("Chance to apply failure mechanics during tier upgrades")
	@Config.Name("Failure Chance on Tier Upgrade")
	public float upgradeFailChanceTier = 0.1F;

	@Config.Comment("Chance to apply failure mechanics during level upgrades")
	@Config.Name("Failure Chance on Level Upgrade")
	public float upgradeFailChanceLevel = 0.0F;

	@Config.Comment("If true, the enchantment being upgraded will instead be replaced with its defined curse equivalent, or removed if set to none. If false, the curse is an additional enchantment, or does nothing if set to none.")
	@Config.Name("Failure Removes Original")
	public boolean upgradeFailRemovesOriginal = false;

	@Config.Comment("Mode to determine the resulting level of enchantments during tier upgrades (Respects minimum and maximum levels)" + "\n" +
			"0: Existing level minus defined amount" + "\n" +
			"1: Minimum level")
	@Config.Name("Tier Upgrade Mode")
	public int upgradedTierLevelMode = 1;

	@Config.Comment("If the Tier Upgrade Mode is set to 0, reduces the level of the resulting enchantment by this amount")
	@Config.Name("Tier Upgrade Mode Subtraction Amount")
	public int upgradedTierLevelReduction = 1;
	
	@Config.Comment("Allows enchantment tiers to be upgraded as defined in the Upgrade Tier Order")
	@Config.Name("Allow Enchantment Tier Upgrades")
	public boolean allowTierUpgrades = true;
	
	@Config.Comment("Allows enchantment levels to be upgraded. If enabled, tiers will only be upgraded when the enchant is on its maximum level first")
	@Config.Name("Allow Enchantment Level Upgrades")
	public boolean allowLevelUpgrades = true;

	@Config.Comment("Mode of how anvil repair cost is increased" + "\n" +
			"0: No additional repair cost" + "\n" +
			"1: Default exponential cost anvil calculation" + "\n" +
			"2: Adds a defined amount to existing cost" + "\n" +
			"3: Multiplies existing cost by a defined amount")
	@Config.Name("Anvil Repair Cost Mode")
	public int anvilRepairMode = 2;

	@Config.Comment("How many levels to either add or multiply to the existing repair cost when upgrading enchantments")
	@Config.Name("Anvil Repair Cost Amount")
	public float anvilRepairCostAmount = 10.0F;

	@Config.Comment("How many bookshelves surrounding the enchantment table are required to be able to upgrade enchantments")
	@Config.Name("Enchantment Upgrading Bookshelf Requirement")
	public int bookshelvesNeeded = 30;
	
	@Config.Comment("If true, allows enchantment clue tooltips to be displayed showing the possible result, like vanilla enchanting does")
	@Config.Name("Allow Upgrading Enchantment Clues")
	public boolean allowEnchantmentClues = true;
}