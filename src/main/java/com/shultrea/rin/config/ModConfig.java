package com.shultrea.rin.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.config.folders.*;
import com.shultrea.rin.util.UpgradeRecipe;
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
	
	@Config.Comment({"Types of items each enchantment can apply on at enchantment table and anvil. \n" +
			"This is using the vanilla EnumEnchantmentType system where an item is checked against various conditions to decide whether it can get an enchant.\n" +
			"For example to get SWORD enchants an item needs to inherit the vanilla ItemSword class. This means modded swords can also fit to SWORD, if their classes do inherit ItemSword.\n" +
			"Available types: ALL_TYPES (=any of the following types), ARMOR, ARMOR_HEAD, ARMOR_CHEST, ARMOR_LEGS, ARMOR_FEET, SWORD, TOOL, FISHING_ROD, BREAKABLE, BOW, WEARABLE, ALL_ITEMS (=any Item), AXE, PICKAXE, HOE, SHOVEL, SHIELD, NONE\n" +
			"To remove a subset of an item type (like all gold swords) from an enchant, use ! in front of a (custom) type.\n" +
			"Blacklisting like this will always be stronger than whitelisting, so an item that fits a listed type but also fits a listed type with ! will not be able to get the enchant."})
	@Config.Name("Can apply on enchantment table and anvil")
	public static CanApplyConfig canApply = new CanApplyConfig();
	
	@Config.Comment("Additional types of items each enchantment can apply on at the anvil. Works like vanilla sharpness only being applicable on axes via anvil.\n" +
			"For an explanation on how to use this config system, check the comment on the other CanApply config section.")
	@Config.Name("Can apply additionally on anvil")
	public static CanApplyAnvilConfig canApplyAnvil = new CanApplyAnvilConfig();

	@Config.Comment("Config for upgrading tiered enchantments")
	@Config.Name("Upgrade")
	public static UpgradeConfig upgrade = new UpgradeConfig();
	
	@Config.Comment("Miscellaneous")
	@Config.Name("Miscellaneous")
	public static MiscConfig miscellaneous = new MiscConfig();

	static {
		ConfigAnytime.register(ModConfig.class);
	}
	
	@Mod.EventBusSubscriber(modid = SoManyEnchantments.MODID)
	private static class EventHandler {
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(SoManyEnchantments.MODID)) {
				ConfigManager.sync(SoManyEnchantments.MODID, Config.Type.INSTANCE);
				ConfigProvider.resetBlacklists();
				ConfigProvider.resetCanApply();

				ConfigProvider.resetDefaultUpgradeTokens();
				UpgradeRecipe.resetUpgradeRecipes();
				UpgradeRecipe.initUpgradeRecipes();
			}
		}
	}
}