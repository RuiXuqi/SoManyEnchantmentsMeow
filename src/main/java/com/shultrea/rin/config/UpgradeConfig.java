package com.shultrea.rin.config;

import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class UpgradeConfig {
	
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
	@Config.Name("Upgrade Token")
	public String upgradeToken = "minecraft:prismarine_shard";

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

	@Config.Comment("Chance to apply failure mechanics during enchantment upgrades")
	@Config.Name("Failure Chance")
	public float upgradeFailChance = 0.1F;
	
	@Config.Comment("If true, upgrade failure mechanics only apply to enchantments that are being upgraded by a tier and not just level")
	@Config.Name("Failure Tier Only")
	public boolean upgradeFailTierOnly = true;

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

	private static Item upgradeTokenItem = null;
	private static List<UpgradeTierEntry> upgradeTierEntries = null;
	private static List<UpgradeFailEntry> upgradeFailEntries = null;

	public static Item getUpgradeTokenItem() {
		if(upgradeTokenItem == null) {
			Item token = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModConfig.upgrade.upgradeToken.trim()));
			if(token == null) {
				SoManyEnchantments.LOGGER.log(Level.WARN, "Invalid upgrade token: " + ModConfig.upgrade.upgradeToken);
				token = Items.PRISMARINE_SHARD;
			}
			upgradeTokenItem = token;
		}
		return upgradeTokenItem;
	}
	
	public static List<UpgradeTierEntry> getUpgradeTierEntries() {
		if(upgradeTierEntries == null) {
			upgradeTierEntries = new ArrayList<>();
			for(String entry : ModConfig.upgrade.enchantUpgradeOrder) {
				entry = entry.trim();
				if(entry.isEmpty()) continue;
				List<Enchantment> enchantmentUpgradeList = new ArrayList<>();
				String[] args = entry.split(",");
				for(String arg : args) {
					arg = arg.trim();
					if(arg.isEmpty()) continue;
					Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(arg));
					if(enchant == null) {
						SoManyEnchantments.LOGGER.log(Level.WARN, "Invalid upgrade tier enchantment: " + arg);
						continue;
					}
					enchantmentUpgradeList.add(enchant);
				}
				upgradeTierEntries.add(new UpgradeTierEntry(enchantmentUpgradeList));
			}
		}
		return upgradeTierEntries;
	}
	
	public static List<UpgradeFailEntry> getUpgradeFailEntries() {
		if(upgradeFailEntries == null) {
			upgradeFailEntries = new ArrayList<>();
			for(String entry : ModConfig.upgrade.enchantUpgradeCursing) {
				entry = entry.trim();
				if(entry.isEmpty()) continue;
				List<Enchantment> enchantmentFailureList = new ArrayList<>();
				Enchantment curseEnchantment = null;
				String[] args = entry.split(",");
				for(int i = 0; i < args.length; i++) {
					String arg = args[i].trim();
					if(arg.isEmpty()) continue;
					if(i == args.length - 1) {
						if("none".equals(arg)) {
							curseEnchantment = null;
						}
						else {
							Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(arg));
							if(enchant == null) {
								SoManyEnchantments.LOGGER.log(Level.WARN, "Invalid curse equivalent curse: " + arg);
							}
							curseEnchantment = enchant;
						}
					}
					else {
						Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(arg));
						if(enchant == null) {
							SoManyEnchantments.LOGGER.log(Level.WARN, "Invalid curse equivalent enchantment: " + arg);
							continue;
						}
						enchantmentFailureList.add(enchant);
					}
				}
				upgradeFailEntries.add(new UpgradeFailEntry(enchantmentFailureList, curseEnchantment));
			}
		}
		return upgradeFailEntries;
	}
	
	public static class UpgradeTierEntry {
		
		private final List<Enchantment> enchantmentUpgradeList;
		
		public UpgradeTierEntry(List<Enchantment> enchantmentUpgradeList) {
			this.enchantmentUpgradeList = enchantmentUpgradeList;
		}
		
		public boolean isEnchantmentUpgradeable(Enchantment enchantment) {
			if(this.enchantmentUpgradeList.contains(enchantment)) {
				//The final enchantment in the list can not be upgraded to anything
				return this.enchantmentUpgradeList.get(this.enchantmentUpgradeList.size() - 1) != enchantment;
			}
			return false;
		}
		
		@Nullable
		public Enchantment getUpgradedEnchantment(Enchantment enchantment) {
			int index = this.enchantmentUpgradeList.indexOf(enchantment);
			if(index != -1 && index + 1 < this.enchantmentUpgradeList.size()) {
				return this.enchantmentUpgradeList.get(index + 1);
			}
			return null;
		}
	}
	
	public static class UpgradeFailEntry {
		
		private final List<Enchantment> enchantmentFailureList;
		private final Enchantment curse;
		
		public UpgradeFailEntry(List<Enchantment> enchantmentFailureList, Enchantment curse) {
			this.enchantmentFailureList = enchantmentFailureList;
			this.curse = curse;
		}
		
		public boolean canEnchantmentFail(Enchantment enchantment) {
			return this.enchantmentFailureList.contains(enchantment);
		}
		
		@Nullable
		public Enchantment getCurse() {
			return this.curse;
		}
	}
	
	public static class UpgradePotentialEntry {
		
		private final Enchantment enchantmentPrevious;
		private final Enchantment enchantmentUpgrade;
		
		private final int levelPrevious;
		private final int levelUpgrade;
		
		private final int tokenCost;
		
		private final Enchantment enchantmentFail;
		private final boolean failRemovesOriginal;
		
		public UpgradePotentialEntry(Enchantment enchantmentPrevious, Enchantment enchantmentUpgrade, int levelPrevious, int levelUpgrade, int tokenCost, Enchantment enchantmentFail, boolean failRemovesOriginal) {
			this.enchantmentPrevious = enchantmentPrevious;
			this.enchantmentUpgrade = enchantmentUpgrade;
			this.levelPrevious = levelPrevious;
			this.levelUpgrade = levelUpgrade;
			this.tokenCost = tokenCost;
			this.enchantmentFail = enchantmentFail;
			this.failRemovesOriginal = failRemovesOriginal;
		}
		
		public Enchantment getEnchantmentPrevious() {
			return this.enchantmentPrevious;
		}
		
		public Enchantment getEnchantmentUpgrade() {
			return this.enchantmentUpgrade;
		}
		
		public int getLevelPrevious() {
			return this.levelPrevious;
		}
		
		public int getLevelUpgrade() {
			return this.levelUpgrade;
		}
		
		public int getTokenCost() {
			return this.tokenCost;
		}
		
		public Enchantment getEnchantmentFail() {
			return this.enchantmentFail;
		}
		
		public boolean getFailRemovesOriginal() {
			return this.failRemovesOriginal;
		}
	}
}