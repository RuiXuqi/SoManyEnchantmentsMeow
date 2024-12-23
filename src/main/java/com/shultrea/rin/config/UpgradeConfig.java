package com.shultrea.rin.config;

import net.minecraftforge.common.config.Config;

import java.util.ArrayList;
import java.util.List;

public class UpgradeConfig {
	@Config.Comment("Enchantments will be upgraded in this order. [ench1, ench2, ..., itemToUse, amountOfItem]")
	@Config.Name("Enchantment upgrade order")
	public String[] enchantUpgradeOrder = {
			"lessersharpness, minecraft:sharpness, advancedsharpness, minecraft:prismarine_shard, 16",
			"lessersmite, minecraft:smite, advancedsmite, minecraft:prismarine_shard, 16",
			"lesserbaneofarthropods, minecraft:bane_of_arthropods, advancedbaneofarthropods, minecraft:prismarine_shard, 16",
			"lesserfireaspect, minecraft:fire_aspect, advancedfireaspect, minecraft:prismarine_shard, 16",
			"minecraft:knockback, advancedknockback, minecraft:prismarine_shard, 16",
			"minecraft:looting, advancedlooting, minecraft:prismarine_shard, 16",
			"minecraft:efficiency, advancedefficiency, minecraft:prismarine_shard, 16",
			"minecraft:luck_of_the_sea, advancedluckofthesea, minecraft:prismarine_shard, 16",
			"minecraft:lure, advancedlure, minecraft:prismarine_shard, 16",
			"minecraft:mending, advancedmending, minecraft:prismarine_shard, 16",
			"lesserflame, minecraft:flame, advancedflame, minecraft:prismarine_shard, 16",
			"minecraft:punch, advancedpunch, minecraft:prismarine_shard, 16",
			"minecraft:power, advancedpower, minecraft:prismarine_shard, 16",
			"minecraft:feather_falling, advancedfeatherfalling, minecraft:prismarine_shard, 16",
			"minecraft:blast_protection, advancedblastprotection, minecraft:prismarine_shard, 16",
			"minecraft:fire_protection, advancedfireprotection, minecraft:prismarine_shard, 16",
			"minecraft:projectile_protection, advancedprojectileprotection, minecraft:prismarine_shard, 16",
			"minecraft:protection, advancedprotection, minecraft:prismarine_shard, 16",
			"minecraft:thorns, burningthorns, advancedthorns, minecraft:prismarine_shard, 16",
			"advancedflame, supremeflame, minecraft:nether_star, 1",
			"advancedfireaspect, supremefireaspect, minecraft:nether_star, 1",
			"advancedsharpness, supremesharpness, minecraft:nether_star, 1",
			"advancedsmite, supremesmite, minecraft:nether_star, 1",
			"advancedbaneofarthropods, supremebaneofarthropods, minecraft:nether_star, 1",
			"advancedprotection, supremeprotection, minecraft:dragon_egg, 1"
	};

	@Config.Comment("Enchantments will be turned into their curse form. Curse is last in list. none means it will be removed instead")
	@Config.Name("Enchantment upgrade cursing")
	public String[] enchantUpgradeCursing = {
			"lessersharpness, minecraft:sharpness, advancedsharpness, bluntness",
			"lessersmite, minecraft:smite, advancedsmite, supremesmite, bluntness",
			"lesserbaneofarthropods, minecraft:bane_of_arthropods, advancedbaneofarthropods, supremebaneofarthropods, bluntness",
			"lesserfireaspect, minecraft:fire_aspect, advancedfireaspect, supremefireaspect, extinguish",
			"minecraft:knockback, advancedknockback, dragging",
			"minecraft:looting, advancedlooting, ascetic",
			"minecraft:efficiency, advancedefficiency, inefficient",
			"minecraft:luck_of_the_sea, advancedluckofthesea, ascetic",
			"minecraft:lure, advancedlure, none",
			"minecraft:mending, advancedmending, rusted",
			"lesserflame, minecraft:flame, advancedflame, supremeflame, extinguish",
			"minecraft:punch, advancedpunch, dragging",
			"powerless, minecraft:power, advancedpower, powerless",
			"minecraft:feather_falling, advancedfeatherfalling, none",
			"minecraft:blast_protection, advancedblastprotection, curseofvulnerability",
			"minecraft:fire_protection, advancedfireprotection, curseofvulnerability",
			"minecraft:projectile_protection, advancedprojectileprotection, curseofvulnerability",
			"minecraft:protection, advancedprotection, supremeprotection, curseofvulnerability",
			"minecraft:thorns, burningthorns, advancedthorns, meltdown"
	};

	@Config.Comment("Upgrading enchantment levels (not tiers) will use up this material")
	@Config.Name("Upgrade Token for levels")
	public String upgradeToken = "minecraft:prismarine_shard";

	@Config.Comment("Upgrading enchantment levels will use up this amount of the token material")
	@Config.Name("Upgrade Token for levels - Amount")
	public int upgradeTokenAmount = 1;

	@Config.Comment("Mode how many XP levels are used while upgrading enchants: NONE=no xp cost, ANVIL=how much the resulting enchant would cost on anvil, ENCHANTABILITY=minimum enchantability of the resulting enchant")
	@Config.Name("Level Cost Mode")
	public String levelCostMode = "ANVIL";

	@Config.Comment("By how much the resulting level cost will be multiplied")
	@Config.Name("Level Cost Multiplier")
	public float levelCostMultiplier = 2.0F;

	@Config.Comment("Set to true to allow upgrading enchants only on enchanted books with one enchantment")
	@Config.Name("Only allow upgrading for single enchant books")
	public boolean onlyAllowOnBooks = false;

	@Config.Comment("Only allow upgrading tiers of enchantments if the upgraded enchantment is compatible with the other enchants on the item")
	@Config.Name("Only allow compatible")
	public boolean onlyAllowCompatible = true;

	@Config.Comment("Chance to turn into curse or to remove entirely instead of upgrading")
	@Config.Name("Cursing Chance")
	public float cursingChance = 0.1F;

	@Config.Comment("If set to true, randomly rolled curses will replace the upgraded enchant. If false, they will be an additional enchant")
	@Config.Name("Curses Replace Upgrade")
	public boolean cursesReplaceUpgrade = false;

	@Config.Comment("If upgrading is allowed on items with multiple enchants, upgrade them randomly (RANDOM), top to bottom (FIRST) or bottom to top (LAST)")
	@Config.Name("Selection mode")
	public String selectionMode = "RANDOM";

	@Config.Comment("Upgraded Enchant will have the current level minus x levels set in config (SUBTRACT), or it will have the min possible level of the enchant, usually 1 (MINLVL)")
	@Config.Name("Upgraded tier level mode")
	public String upgradedTierLevelMode = "MINLVL";

	@Config.Comment("How many lvls of the enchant to reduce by while upgrading in SUBTRACT mode. For example 1 means Prot 4 turns to Adv Prot 3. Does not apply for cursing")
	@Config.Name("Upgraded tier level reduction")
	public int enchantLvlsReduced = 1;

	@Config.Comment("Whether you can also upgrade the level of the enchants without changing the tier. This makes upgrading tier only possible at max lvl. Prot 3 -> Prot 4 -> Adv Prot x")
	@Config.Name("Allow level increase")
	public boolean allowLevelIncrease = true;

	@Config.Comment("Whether to increase anvil repair cost when upgrading")
	@Config.Name("Anvil repair cost increases")
	public boolean increaseAnvilRepairCost = false;

	@Config.Comment("Mode of how anvil repair cost is increased. ANVIL= normal exponential anvil behavior, ADD= add a flat amount, MULT= multiply by a number")
	@Config.Name("Anvil repair cost increase mode")
	public String anvilRepairMode = "ADD";

	@Config.Comment("How many levels to add or multiply anvil repair cost by when upgrading")
	@Config.Name("Anvil repair cost amount")
	public float anvilRepairCostAmount = 10.0F;

	@Config.Comment("How many bookshelves are needed to be able to upgrade")
	@Config.Name("Amount of bookshelves needed")
	public int bookshelvesNeeded = 30;

	public static List<String> upgradeTokens = new ArrayList<>();

	public static void initUpgradeTokens(){
		if(ModConfig.upgrade.allowLevelIncrease) upgradeTokens.add(ModConfig.upgrade.upgradeToken);
		for(String line : ModConfig.upgrade.enchantUpgradeOrder){
			String[] split = line.split("\\s*,\\s*");
			if(split.length>3){
				upgradeTokens.add(split[split.length-2]);
			}
		}
	}
}