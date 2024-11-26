package com.shultrea.rin.config;

import net.minecraftforge.common.config.Config;

public class UpgradeConfig {
	@Config.Comment("Enchantments will be upgraded in this order")
	@Config.Name("Enchanment upgrade order")
	public String[] enchantUpgradeOrder = {
			"lessersharpness, minecraft:sharpness, advancedsharpness, supremesharpness",
			"lessersmite, minecraft:smite, advancedsmite, supremesmite",
			"lesserbaneofarthropods, minecraft:bane_of_arthropods, advancedbaneofarthropods, supremebaneofarthropods",
			"lesserflame, minecraft:flame, advancedflame, supremeflame",
			"lesserfireaspect, minecraft:fire_aspect, advancedfireaspect, supremefireaspect",
			"minecraft:blast_protection, advancedblastprotection",
			"inefficient, minecraft:efficiency, advancedefficiency",
			"minecraft:feather_falling, advancedfeatherfalling",
			"minecraft:fire_protection, advancedfireprotection",
			"minecraft:knockback, advancedknockback",
			"minecraft:looting, advancedlooting",
			"minecraft:luck_of_the_sea, advancedluckofthesea",
			"minecraft:lure, advancedlure",
			"minecraft:mending, advancedmending",
			"powerless, minecraft:power, advancedpower",
			"minecraft:projectile_protection, advancedprojectileprotection",
			"minecraft:protection, advancedprotection",
			"minecraft:punch, advancedpunch",
			"minecraft:thorns, advancedthorns"
	};

	@Config.Comment("Upgrading enchantments will use up this material")
	@Config.Name("Upgrade Token")
	public String upgradeToken = "minecraft:prismarine_shard";

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
}