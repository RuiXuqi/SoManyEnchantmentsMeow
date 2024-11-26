package com.shultrea.rin.config;

import net.minecraftforge.common.config.Config;

public class UpgradeConfig {
	@Config.Comment("Enchantments will be upgraded in this order")
	@Config.Name("Enchantment upgrade order")
	public String[] enchantUpgradeOrder = {
			"lessersharpness, minecraft:sharpness, advancedsharpness, supremesharpness",
			"lessersmite, minecraft:smite, advancedsmite, supremesmite",
			"lesserbaneofarthropods, minecraft:bane_of_arthropods, advancedbaneofarthropods, supremebaneofarthropods",
			"lesserfireaspect, minecraft:fire_aspect, advancedfireaspect, supremefireaspect",
			"minecraft:knockback, advancedknockback",
			"minecraft:looting, advancedlooting",
			"inefficient, minecraft:efficiency, advancedefficiency",
			"minecraft:luck_of_the_sea, advancedluckofthesea",
			"minecraft:lure, advancedlure",
			"minecraft:mending, advancedmending",
			"lesserflame, minecraft:flame, advancedflame, supremeflame",
			"minecraft:punch, advancedpunch",
			"powerless, minecraft:power, advancedpower",
			"minecraft:feather_falling, advancedfeatherfalling",
			"minecraft:blast_protection, advancedblastprotection",
			"minecraft:fire_protection, advancedfireprotection",
			"minecraft:projectile_protection, advancedprojectileprotection",
			"minecraft:protection, advancedprotection",
			"meltdown, minecraft:thorns, burningthorns, advancedthorns"
	};

	@Config.Comment("Upgrading enchantments will use up this material")
	@Config.Name("Upgrade Token")
	public String upgradeToken = "minecraft:prismarine_shard";

	@Config.Comment("Upgrading enchantments will use up this amount of the token material")
	@Config.Name("Upgrade Token Amount")
	public int upgradeTokenAmount = 1;

	@Config.Comment("Mode how XP is used while upgrading enchants: NONE=no xp cost, ANVIL=how much the resulting enchant would cost on anvil, ENCHANTABILITY=minimum enchantability of the resulting enchant")
	@Config.Name("XP Cost Mode")
	public String levelCostMode = "ANVIL";

	@Config.Comment("Set to true to allow upgrading enchants only on enchanted books with one enchantment")
	@Config.Name("Only allow upgrading for single enchant books")
	public boolean onlyAllowOnBooks = false;

	@Config.Comment("If upgrading is allowed on items with multiple enchants, upgrade them randomly (RANDOM), top to bottom (FIRST) or bottom to top (LAST)")
	@Config.Name("Upgrade mode")
	public String upgradeMode = "RANDOM";

	@Config.Comment("How many bookshelves are needed to be able to upgrade")
	@Config.Name("Amount of bookshelves needed")
	public int bookshelvesNeeded = 30;

	@Config.Comment("How many lvls of the enchant to reduce by while upgrading. For example 1 means Prot 4 turns to Adv Prot 3")
	@Config.Name("Enchant Lvls reduced during upgrade")
	public int enchantLvlsReduced = 1;
}