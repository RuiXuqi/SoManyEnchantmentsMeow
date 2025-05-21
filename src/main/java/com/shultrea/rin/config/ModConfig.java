package com.shultrea.rin.config;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.util.UpgradeRecipe;
import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Config(modid = SoManyEnchantments.MODID)
public class ModConfig {
	
	@Config.Comment("What enchantments should be enabled")
	@Config.Name("Enabled Enchantments")
	public static EnabledConfig enabled = new EnabledConfig();
	
	@Config.Comment("What enchantments should be treasure only")
	@Config.Name("Treasure Enchantments")
	public static TreasureConfig treasure = new TreasureConfig();
	
	@Config.Comment("Maximum levels of each enchantment")
	@Config.Name("Enchantment Levels")
	public static LevelConfig level = new LevelConfig();
	
	@Config.Comment("Enchantabilities of each enchantment: {start, lvlspan, range, max_mode} with max_mode = 0 being standard behavior and 1,2,3 being legacy oversights. Legacy max_modes: 1=SUPER, using maxench = super.min+range. 2=FIXED, using maxench = range. 3=LINEAR, using maxench = range*lvl")
	@Config.Name("Enchantabilities")
	public static EnchantabilityConfig enchantability = new EnchantabilityConfig();
	
	@Config.Comment("Each line is a group of pairwise incompatible enchantments. Enchantments are separated by comma and optional whitespace. When using enchantments from other mods, use modid:enchantmentname.")
	@Config.Name("Incompatible Groups")
	public static IncompatibleConfig incompatible = new IncompatibleConfig();
	
	@Config.Comment("Rarity of each enchantment: COMMON, UNCOMMON, RARE, VERY_RARE")
	@Config.Name("Rarity")
	public static RarityConfig rarity = new RarityConfig();
	
	@Config.Comment("Types of items each enchantment can apply on at enchantment table and anvil. Available types: ALL_TYPES (=any of the following types), ARMOR, ARMOR_HEAD, ARMOR_CHEST, ARMOR_LEGS, ARMOR_FEET, SWORD, TOOL, FISHING_ROD, BREAKABLE, BOW, WEARABLE, ALL_ITEMS (=any Item), AXE, PICKAXE, HOE, SHOVEL, GAPPLE, SHIELD, NONE")
	@Config.Name("Can apply on enchantment table and anvil")
	public static CanApplyConfig canApply = new CanApplyConfig();
	
	@Config.Comment("Additional types of items each enchantment can apply on at the anvil. Works like vanilla sharpness only being applicable on axes via anvil.")
	@Config.Name("Can apply additionally on anvil")
	public static CanApplyAnvilConfig canApplyAnvil = new CanApplyAnvilConfig();

	@Config.Comment("Config for upgrading tiered enchantments")
	@Config.Name("Upgrade")
	@MixinConfig.SubInstance
	public static UpgradeConfig upgrade = new UpgradeConfig();
	
	@Config.Comment("Miscellaneous")
	@Config.Name("Miscellaneous")
	@MixinConfig.SubInstance
	public static MiscellaneousConfig miscellaneous = new MiscellaneousConfig();
	
	public static class MiscellaneousConfig {
		@Config.Comment("Makes zombie villagers keep their trades during infection and conversion")
		@Config.Name("Zombified Villagers keep trades")
		@Config.RequiresMcRestart
		@MixinConfig.EarlyMixin(name = "mixins.somanyenchantments.zombietrades.json")
		public boolean zombieVillagersKeepTrades = true;

		@Config.Comment("Allow enchantments to change the weather")
		@Config.Name("Allow Weather Changing Effects")
		public boolean enableWeatherChanges = true;
		
		@Config.Comment("Enables a bug that doubles xp orbs when picked up, present from older versions")
		@Config.Name("Retain Double XP Orbs Bug")
		public boolean enableDoubleXPBug = false;
		
		@Config.Comment("Evasion makes the player perform a dodge, disable if you want potentially dangerous forced dodges")
		@Config.Name("Evasion Dodge Effect")
		public boolean evasionDodgeEffect = true;
		
		@Config.Comment("Ignores registering enabled enchantments so they do not show up in game")
		@Config.Name("Don't Register Disabled Enchants")
		@Config.RequiresMcRestart
		public boolean dontRegisterDisabledEnchants = true;
		
		@Config.Comment("List of potion effects blacklisted from being applied by enchantments, in the format modname:potionid")
		@Config.Name("Potion Blacklist")
		@Config.RequiresMcRestart
		public String[] potionBlacklist = {};
		
		@Config.Comment("If enabled, the potion blacklist will be treated as a whitelist")
		@Config.Name("Potion Whitelist Toggle")
		@Config.RequiresMcRestart
		public boolean potionBlacklistAsWhitelist = false;
		
		@Config.Comment("If enchantment effects should apply through a player's pet attacking entities")
		@Config.Name("Enable Pet Attacks")
		public boolean enablePetAttacks = false;
		
		@Config.Comment("Enables extra protection effects")
		@Config.Name("Extra Protection Effects")
		public boolean extraProtectionEffects = true;

		@Config.Comment("If curses should be allowed to apply to books")
		@Config.Name("Curses Apply To Books")
		public boolean canCursesBeAppliedToBooks = false;
		
		@Config.Comment("Tick interval that Pandora's Curse will check a players inventory")
		@Config.Name("Pandora's Curse Interval")
		@Config.RangeInt(min = 200)
		public int pandorasCurseInterval = 600;
		
		@Config.Comment("Whether or not Atomic Deconstructor should work on bosses (May cause bugs)")
		@Config.Name("Atomic Deconstructor Works on Bosses")
		public boolean atomicDeconstructorBosses = false;
		
		@Config.Comment("If Advanced Mending should prioritize repairing damaged items")
		@Config.Name("Advanced Mending Prioritize Damaged Items")
		public boolean advancedMendingPrioritizeDamaged = true;

		@Config.Comment("Librarians will not be able to generate enchantments in this list")
		@Config.Name("Librarian Enchantment Blacklist")
		public String[] blacklistedLibrarianEnchants = {
				"somanyenchantments:ancientswordmastery",
				"somanyenchantments:ancientsealedcurses",
				"somanyenchantments:advancedbaneofarthropods",
				"somanyenchantments:advancedblastprotection",
				"somanyenchantments:advancedefficiency",
				"somanyenchantments:advancedfeatherfalling",
				"somanyenchantments:advancedfireaspect",
				"somanyenchantments:advancedfireprotection",
				"somanyenchantments:advancedflame",
				"somanyenchantments:advancedknockback",
				"somanyenchantments:advancedlooting",
				"somanyenchantments:advancedluckofthesea",
				"somanyenchantments:advancedlure",
				"somanyenchantments:advancedmending",
				"somanyenchantments:advancedpower",
				"somanyenchantments:advancedprojectileprotection",
				"somanyenchantments:advancedprotection",
				"somanyenchantments:advancedpunch",
				"somanyenchantments:advancedsharpness",
				"somanyenchantments:advancedsmite",
				"somanyenchantments:advancedthorns",
				"somanyenchantments:supremebaneofarthropods",
				"somanyenchantments:supremefireaspect",
				"somanyenchantments:supremeflame",
				"somanyenchantments:supremesharpness",
				"somanyenchantments:supremesmite",
				"somanyenchantments:pandorascurse",
				"somanyenchantments:supremeprotection"
		};

		@Config.Comment("Enchantment blacklist will be treated as a Whitelist")
		@Config.Name("Librarian Enchantment Whitelist Toggle")
		public boolean blacklistedLibrarianEnchantsIsWhitelist = false;

		@Config.Comment("Loot enchanted with levels (enchant_with_levels) will not be able to generate enchantments in this list")
		@Config.Name("Level Enchantment Blacklist")
		public String[] blacklistedRandomLevelEnchants = {
				"somanyenchantments:supremeprotection",
				"somanyenchantments:pandorascurse"
		};

		@Config.Comment("Level Enchantment blacklist will be treated as a Whitelist")
		@Config.Name("Level Enchantment Whitelist Toggle")
		public boolean blacklistedRandomLevelEnchantsIsWhitelist = false;

		@Config.Comment("Enchanting table will not be able to generate enchantments in this list")
		@Config.Name("Enchanting Table Blacklist")
		public String[] blacklistedEnchTableEnchants = {
				"somanyenchantments:ancientswordmastery",
				"somanyenchantments:ancientsealedcurses",
				"somanyenchantments:advancedbaneofarthropods",
				"somanyenchantments:advancedblastprotection",
				"somanyenchantments:advancedefficiency",
				"somanyenchantments:advancedfeatherfalling",
				"somanyenchantments:advancedfireaspect",
				"somanyenchantments:advancedfireprotection",
				"somanyenchantments:advancedflame",
				"somanyenchantments:advancedknockback",
				"somanyenchantments:advancedlooting",
				"somanyenchantments:advancedluckofthesea",
				"somanyenchantments:advancedlure",
				"somanyenchantments:advancedmending",
				"somanyenchantments:advancedpower",
				"somanyenchantments:advancedprojectileprotection",
				"somanyenchantments:advancedprotection",
				"somanyenchantments:advancedpunch",
				"somanyenchantments:advancedsharpness",
				"somanyenchantments:advancedsmite",
				"somanyenchantments:advancedthorns",
				"somanyenchantments:supremebaneofarthropods",
				"somanyenchantments:supremefireaspect",
				"somanyenchantments:supremeflame",
				"somanyenchantments:supremesharpness",
				"somanyenchantments:supremesmite",
				"somanyenchantments:supremeprotection",
				"somanyenchantments:pandorascurse"
		};

		@Config.Comment("Enchantment Table blacklist will be treated as a Whitelist")
		@Config.Name("Enchantment Table Whitelist Toggle")
		public boolean blacklistedEnchTableEnchantsIsWhitelist = false;

		@Config.Comment("Fully random books (enchant_randomly) will not be able to generate enchantments in this list")
		@Config.Name("Random Enchantment Blacklist")
		public String[] blacklistedRandomEnchants = {
				"somanyenchantments:supremeprotection",
				"somanyenchantments:pandorascurse"
		};

		@Config.Comment("Random Enchantment blacklist will be treated as a Whitelist")
		@Config.Name("Random Enchantment Whitelist Toggle")
		public boolean blacklistedRandomEnchantsIsWhitelist = false;

		@Config.Comment("If set to true, remove anvil repair cost increase when combining two single enchant books with the same lvl (Prot 3 + Prot 3)")
		@Config.Name("Remove book combination anvil cost increase")
		public boolean removeBookCombinationAnvilCost = false;
		
		@Config.Comment("If set to true, Curse of Possession will delete cursed items dropped from inventory on death when no player can be found")
		@Config.Name("Curse Of Possession Death Deletion")
		public boolean curseOfPossessionDeathDeletion = true;
	}
	
	@Mod.EventBusSubscriber(modid = SoManyEnchantments.MODID)
	private static class EventHandler {
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(SoManyEnchantments.MODID)) {
				ConfigManager.sync(SoManyEnchantments.MODID, Config.Type.INSTANCE);
				ConfigProvider.resetBlacklists();
				ConfigProvider.resetDefaultUpgradeTokens();
				UpgradeRecipe.resetUpgradeRecipes();
				UpgradeRecipe.initUpgradeRecipes();
			}
		}
	}
}