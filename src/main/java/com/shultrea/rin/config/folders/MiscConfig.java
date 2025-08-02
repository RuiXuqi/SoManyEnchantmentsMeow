package com.shultrea.rin.config.folders;

import net.minecraftforge.common.config.Config;

public class MiscConfig {
    @Config.Comment("Makes zombie villagers keep their trades during infection and conversion")
    @Config.Name("Zombified Villagers keep trades")
    @Config.RequiresMcRestart
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

    @Config.Comment("Enchants in this list will be prevented from being registered in the game. There will be no way to access them at all.")
    @Config.Name("Registered Enchantment Blacklist")
    @Config.RequiresMcRestart
    public String[] blacklistedRegistryEnchants = {
    };

    @Config.Comment("If set to true, remove anvil repair cost increase when combining two single enchant books with the same lvl (Prot 3 + Prot 3)")
    @Config.Name("Remove book combination anvil cost increase")
    public boolean removeBookCombinationAnvilCost = false;

    @Config.Comment("If set to true, Curse of Possession will delete cursed items dropped from inventory on death when no player can be found")
    @Config.Name("Curse Of Possession Death Deletion")
    public boolean curseOfPossessionDeathDeletion = true;
}
