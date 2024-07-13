package com.shultrea.rin.Main_Sector;

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
	@Config.Comment("Miscellaneous")
	@Config.Name("Miscellaneous")
	public static MiscellaneousConfig miscellaneous = new MiscellaneousConfig();
	
	public static class MiscellaneousConfig {
		
		@Config.Comment("Allow enchantments to change the weather")
		@Config.Name("Allow Weather Changing Effects")
		public boolean enableWeatherChanges = true;
		@Config.Comment("Enables a bug that doubles xp orbs when picked up, present from older versions")
		@Config.Name("Retain Double XP Orbs Bug")
		public boolean enableDoubleXPBug = false;
		@Config.Comment(
				"Fixes a bug that allows for enchantment effects to still trigger when the initial attack is 0 (Such as while having weakness)")
		@Config.Name("Fix Weak Attack Effects")
		public boolean fixWeakAttackEffects = false;
		@Config.Comment(
				"Evasion makes the player perform a dodge, disable if you want potentially dangerous forced dodges")
		@Config.Name("Evasion Dodge Effect")
		public boolean evasionDodgeEffect = true;
		@Config.Comment("Restricts Quarry enchant to ore only")
		@Config.Name("Quarry Ore Only")
		public boolean quarryOreOnly = true;
		@Config.Comment(
				"Allow Quarry enchant to work on blocks that are tile entities, not recommended as it may cause bugs")
		@Config.Name("Quarry Allow Tile Entities")
		public boolean quarryAllowTileEntities = false;
		@Config.Comment("Ignores registering enabled enchantments so they do not show up in game")
		@Config.Name("Don't Register Disabled Enchants")
		@Config.RequiresMcRestart
		public boolean dontRegisterDisabledEnchants = true;
		@Config.Comment(
				"List of potion effects blacklisted from being applied by enchantments, in the format modname:potionid")
		@Config.Name("Potion Blacklist")
		@Config.RequiresMcRestart
		public String[] potionBlacklist = new String[]{};
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
	}
	
	@Mod.EventBusSubscriber(modid = SoManyEnchantments.MODID)
	private static class EventHandler {
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(SoManyEnchantments.MODID)) {
				ConfigManager.sync(SoManyEnchantments.MODID, Config.Type.INSTANCE);
			}
		}
	}
}