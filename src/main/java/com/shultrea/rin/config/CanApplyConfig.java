package com.shultrea.rin.config;

import com.shultrea.rin.util.Types;
import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;

public class CanApplyConfig {

	private final HashMap<String, EnumEnchantmentType> itemTypes = new HashMap<>();
	private final HashMap<String, CustomType> customTypeMap = new HashMap<>();

	private static class CustomType {
		String name = "";
		String regex = "";
		boolean inverted = false;

		CustomType(String in){
			String[] split = in.split(";");
			if(split.length>=2) {
				name = split[0];
				regex = split[1];
			}
			if(split.length>=3)
				inverted = "NOT".equals(split[2]);
		}
	}

	public CanApplyConfig(){
		itemTypes.put("ALL_TYPES",EnumEnchantmentType.ALL);
		itemTypes.put("ARMOR",EnumEnchantmentType.ARMOR);
		itemTypes.put("ARMOR_HEAD",EnumEnchantmentType.ARMOR_HEAD);
		itemTypes.put("ARMOR_CHEST",EnumEnchantmentType.ARMOR_CHEST);
		itemTypes.put("ARMOR_LEGS",EnumEnchantmentType.ARMOR_LEGS);
		itemTypes.put("ARMOR_FEET",EnumEnchantmentType.ARMOR_FEET);
		itemTypes.put("SWORD",EnumEnchantmentType.WEAPON);
		itemTypes.put("TOOL",EnumEnchantmentType.DIGGER);
		itemTypes.put("FISHING_ROD",EnumEnchantmentType.FISHING_ROD);
		itemTypes.put("BREAKABLE",EnumEnchantmentType.BREAKABLE);
		itemTypes.put("BOW",EnumEnchantmentType.BOW);
		itemTypes.put("WEARABLE",EnumEnchantmentType.WEARABLE);

		itemTypes.put("ALL_ITEMS", Types.ALL);
		itemTypes.put("AXE", Types.COMBAT_AXE);
		itemTypes.put("PICKAXE", Types.PICKAXE);
		itemTypes.put("HOE", Types.HOE);
		itemTypes.put("SHOVEL", Types.SPADE);
		itemTypes.put("SHIELD", Types.SHIELD);
		itemTypes.put("NONE", Types.NONE);

		for(String s: customTypes){
			CustomType c = new CustomType(s);
			if(!"".equals(c.name) && !"".equals(c.regex))
				customTypeMap.put(c.name,c);
		}
	}

	@Config.Name("Custom Item Types")
	@Config.Comment("Pattern: Name to use;Regex to match item ids to find the item;optional: 'NOT' to invert. If you use the inversion, canApply will use this type as an AND connection instead of an OR connection. So 'NOTGOLD','TOOL','SWORD' will be any sword or tool that is not gold. The custom types can also be used in canApplyAnvil config")
	public String[] customTypes = {
			"BS_BATTLEAXE;mujmajnkraftsbettersurvival\\:item.*battleaxe",
			"BS_OTHER;mujmajnkraftsbettersurvival\\:item.*(dagger|nunchaku|hammer)",
			"NOT_GOLD;.*gold.*;NOT"};

	@Config.Name("Adept")
	@Config.RequiresMcRestart
	public String[] adept = {"SWORD","AXE","BOW", "BS_BATTLEAXE", "BS_OTHER"};
	@Config.Name("Ancient Sealed Curses")
	@Config.RequiresMcRestart
	public String[] ancientSealedCurses = {"SWORD", "BS_OTHER"};
	@Config.Name("Ancient Sword Mastery")
	@Config.RequiresMcRestart
	public String[] ancientSwordMastery = {"SWORD", "BS_OTHER"};
	@Config.Name("Arc Slash")
	@Config.RequiresMcRestart
	public String[] arcSlash = {"SWORD", "BS_OTHER"};
	@Config.Name("Ash Destroyer")
	@Config.RequiresMcRestart
	public String[] ashDestroyer = {"SWORD", "BS_OTHER"};
	@Config.Name("Atomic Deconstructor")
	@Config.RequiresMcRestart
	public String[] atomicDeconstructor = {"SWORD", "BS_OTHER"};
	@Config.Name("Blessed Edge")
	@Config.RequiresMcRestart
	public String[] blessedEdge = {"SWORD", "BS_OTHER"};
	@Config.Name("Brutality")
	@Config.RequiresMcRestart
	public String[] brutality = {"AXE", "BS_BATTLEAXE"};
	@Config.Name("Burning Shield")
	@Config.RequiresMcRestart
	public String[] burningShield = {"SHIELD"};
	@Config.Name("Burning Thorns")
	@Config.RequiresMcRestart
	public String[] burningThorns = {"ARMOR_CHEST"};
	@Config.Name("Butchering")
	@Config.RequiresMcRestart
	public String[] butchering = {"SWORD", "BS_OTHER"};
	@Config.Name("Clearskies' Favor")
	@Config.RequiresMcRestart
	public String[] clearskiesFavor = {"SWORD", "BS_OTHER"};
	@Config.Name("Combat Veterancy")
	@Config.RequiresMcRestart
	public String[] combatVeterancy = {"SWORD", "BS_OTHER"};
	@Config.Name("Counter Attack")
	@Config.RequiresMcRestart
	public String[] counterAttack = {"SWORD", "BS_OTHER"};
	@Config.Name("Critical Strike")
	@Config.RequiresMcRestart
	public String[] criticalStrike = {"SWORD","AXE", "BS_BATTLEAXE", "BS_OTHER"};
	@Config.Name("Culling")
	@Config.RequiresMcRestart
	public String[] culling = {"AXE", "BS_BATTLEAXE"};
	@Config.Name("Dark Shadows")
	@Config.RequiresMcRestart
	public String[] darkShadows = {"SWORD", "BS_OTHER"};
	@Config.Name("Defusing Edge")
	@Config.RequiresMcRestart
	public String[] defusingEdge = {"SWORD", "BS_OTHER"};
	@Config.Name("Desolator")
	@Config.RequiresMcRestart
	public String[] desolator = {"AXE", "BS_BATTLEAXE"};
	@Config.Name("Difficulty's Endowment")
	@Config.RequiresMcRestart
	public String[] difficultysEndowment = {"SWORD", "BS_OTHER"};
	@Config.Name("Disarmament")
	@Config.RequiresMcRestart
	public String[] disarmament = {"AXE", "BS_BATTLEAXE"};
	@Config.Name("Disorientating Blade")
	@Config.RequiresMcRestart
	public String[] disorientatingBlade = {"SWORD", "BS_OTHER"};
	@Config.Name("Empowered Defence")
	@Config.RequiresMcRestart
	public String[] empoweredDefence = {"SHIELD"};
	@Config.Name("Envenomed")
	@Config.RequiresMcRestart
	public String[] envenomed = {"SWORD", "BS_OTHER"};
	@Config.Name("Evasion")
	@Config.RequiresMcRestart
	public String[] evasion = {"ARMOR_LEGS"};
	@Config.Name("Fiery Edge")
	@Config.RequiresMcRestart
	public String[] fieryEdge = {"SWORD", "BS_OTHER"};
	@Config.Name("Flinging")
	@Config.RequiresMcRestart
	public String[] flinging = {"SWORD", "BS_OTHER"};
	@Config.Name("Freezing")
	@Config.RequiresMcRestart
	public String[] freezing = {"SWORD", "BS_OTHER"};
	@Config.Name("Hors De Combat")
	@Config.RequiresMcRestart
	public String[] horsDeCombat = {"SWORD", "BS_OTHER"};
	@Config.Name("Inhumane")
	@Config.RequiresMcRestart
	public String[] inhumane = {"SWORD", "BS_OTHER"};
	@Config.Name("Inner Berserk")
	@Config.RequiresMcRestart
	public String[] innerBerserk = {"ARMOR_CHEST"};
	@Config.Name("Jagged Rake")
	@Config.RequiresMcRestart
	public String[] jaggedRake = {"HOE"};
	@Config.Name("Levitator")
	@Config.RequiresMcRestart
	public String[] levitator = {"SWORD", "BS_OTHER"};
	@Config.Name("Lifesteal")
	@Config.RequiresMcRestart
	public String[] lifesteal = {"SWORD", "BS_OTHER"};
	@Config.Name("Light Weight")
	@Config.RequiresMcRestart
	public String[] lightWeight = {"ARMOR_FEET"};
	@Config.Name("Luck Magnification")
	@Config.RequiresMcRestart
	public String[] luckMagnification = {"SWORD","AXE", "BS_BATTLEAXE", "BS_OTHER"};
	@Config.Name("Lunars Blessing")
	@Config.RequiresMcRestart
	public String[] lunarsBlessing = {"SWORD", "BS_OTHER"};
	@Config.Name("Magic Protection")
	@Config.RequiresMcRestart
	public String[] magicProtection = {"ARMOR"};
	@Config.Name("Magma Walker")
	@Config.RequiresMcRestart
	public String[] magmaWalker = {"ARMOR_FEET"};
	@Config.Name("Meltdown")
	@Config.RequiresMcRestart
	public String[] meltdown = {"ARMOR_CHEST"};
	@Config.Name("Moisturized")
	@Config.RequiresMcRestart
	public String[] moisturized = {"HOE"};
	@Config.Name("Mortalitas")
	@Config.RequiresMcRestart
	public String[] mortalitas = {"SWORD","AXE", "BS_BATTLEAXE", "BS_OTHER"};
	@Config.Name("Natural Blocking")
	@Config.RequiresMcRestart
	public String[] naturalBlocking = {"SHIELD"};
	@Config.Name("Parry")
	@Config.RequiresMcRestart
	public String[] parry = {"SWORD", "BS_OTHER"};
	@Config.Name("Penetrating Edge")
	@Config.RequiresMcRestart
	public String[] penetratingEdge = {"AXE", "BS_BATTLEAXE"};
	@Config.Name("Physical Protection")
	@Config.RequiresMcRestart
	public String[] physicalProtection = {"ARMOR"};
	@Config.Name("Dragging")
	@Config.RequiresMcRestart
	public String[] dragging = {"BOW"};
	@Config.Name("Purging Blade")
	@Config.RequiresMcRestart
	public String[] purgingBlade = {"SWORD", "BS_OTHER"};
	@Config.Name("Purification")
	@Config.RequiresMcRestart
	public String[] purification = {"AXE", "BS_BATTLEAXE"};
	@Config.Name("Pushing")
	@Config.RequiresMcRestart
	public String[] pushing = {"BOW"};
	@Config.Name("Quarrying")
	@Config.RequiresMcRestart
	public String[] quarrying = {"PICKAXE"};
	@Config.Name("Rain's Bestowment")
	@Config.RequiresMcRestart
	public String[] rainsBestowment = {"SWORD", "BS_OTHER"};
	@Config.Name("Reviled Blade")
	@Config.RequiresMcRestart
	public String[] reviledBlade = {"SWORD", "BS_OTHER"};
	@Config.Name("Reinforced Sharpness")
	@Config.RequiresMcRestart
	public String[] reinforcedsharpness = {"TOOL"};
	@Config.Name("Smelter")
	@Config.RequiresMcRestart
	public String[] smelter = {"TOOL"};
	@Config.Name("Sol's Blessing")
	@Config.RequiresMcRestart
	public String[] solsBlessing = {"SWORD", "BS_OTHER"};
	@Config.Name("Spell Breaker")
	@Config.RequiresMcRestart
	public String[] spellBreaker = {"SWORD","AXE", "BS_BATTLEAXE", "BS_OTHER"};
	@Config.Name("Splitshot")
	@Config.RequiresMcRestart
	public String[] splitShot = {"BOW"};
	@Config.Name("Strafe")
	@Config.RequiresMcRestart
	public String[] strafe = {"BOW"};
	@Config.Name("Strengthened Vitality")
	@Config.RequiresMcRestart
	public String[] strengthenedVitality = {"ARMOR_CHEST"};
	@Config.Name("Swifter Slashes")
	@Config.RequiresMcRestart
	public String[] swifterSlashes = {"SWORD", "BS_OTHER"};
	@Config.Name("Thunderstorm's Bestowment")
	@Config.RequiresMcRestart
	public String[] thunderstormsBestowment = {"SWORD", "BS_OTHER"};
	@Config.Name("True Strike")
	@Config.RequiresMcRestart
	public String[] trueStrike = {"SWORD", "BS_OTHER"};
	@Config.Name("Underwater Strider")
	@Config.RequiresMcRestart
	public String[] underwaterStrider = {"ARMOR_FEET"};
	@Config.Name("Unreasonable")
	@Config.RequiresMcRestart
	public String[] unreasonable = {"SWORD", "BS_OTHER"};
	@Config.Name("Unsheathing")
	@Config.RequiresMcRestart
	public String[] unsheathing = {"SWORD", "BS_OTHER"};
	@Config.Name("Upgraded Potentials")
	@Config.RequiresMcRestart
	public String[] upgradedPotentials = {"NONE"};
	@Config.Name("Viper")
	@Config.RequiresMcRestart
	public String[] viper = {"SWORD", "BS_OTHER"};
	@Config.Name("Water Aspect")
	@Config.RequiresMcRestart
	public String[] waterAspect = {"SWORD", "BS_OTHER"};
	@Config.Name("Plowing")
	@Config.RequiresMcRestart
	public String[] plowing = {"HOE"};
	@Config.Name("Winter's Grace")
	@Config.RequiresMcRestart
	public String[] wintersGrace = {"SWORD", "BS_OTHER"};

	//Curses
	@Config.Name("Bluntness")
	@Config.RequiresMcRestart
	public String[] bluntness = {"SWORD","AXE", "BS_BATTLEAXE", "BS_OTHER"};
	@Config.Name("Cursed Edge")
	@Config.RequiresMcRestart
	public String[] cursedEdge = {"SWORD", "BS_OTHER"};
	@Config.Name("Curse of Decay")
	@Config.RequiresMcRestart
	public String[] curseOfDecay = {"ALL_ITEMS"};
	@Config.Name("Curse of Holding")
	@Config.RequiresMcRestart
	public String[] curseOfHolding = {"ALL_ITEMS"};
	@Config.Name("Curse of Inaccuracy")
	@Config.RequiresMcRestart
	public String[] curseOfInaccuracy = {"SWORD","AXE","BOW", "BS_BATTLEAXE", "BS_OTHER"};
	@Config.Name("Curse of Possession")
	@Config.RequiresMcRestart
	public String[] curseOfPossession = {"ALL_TYPES"};
	@Config.Name("Curse of Vulnerability")
	@Config.RequiresMcRestart
	public String[] curseOfVulnerability = {"ARMOR"};
	@Config.Name("Heavy Weight")
	@Config.RequiresMcRestart
	public String[] heavyWeight = {"SWORD", "BS_OTHER"};
	@Config.Name("Inefficient")
	@Config.RequiresMcRestart
	public String[] inefficient = {"TOOL"};
	@Config.Name("Instability")
	@Config.RequiresMcRestart
	public String[] instability = {"SWORD","TOOL", "BS_OTHER"};
	@Config.Name("Pandora's Curse")
	@Config.RequiresMcRestart
	public String[] pandorasCurse = {"ALL_ITEMS"};
	@Config.Name("Powerless")
	@Config.RequiresMcRestart
	public String[] powerless = {"BOW"};
	@Config.Name("Rusted")
	@Config.RequiresMcRestart
	public String[] rusted = {"BREAKABLE","NOT_GOLD"};
	@Config.Name("Unpredictable")
	@Config.RequiresMcRestart
	public String[] unpredictable = {"SWORD", "BS_OTHER"};
	
	//Rune
	@Config.Name("Rune: Arrow Piercing")
	@Config.RequiresMcRestart
	public String[] runeArrowPiercing = {"BOW"};
	@Config.Name("Rune: Magical Blessing")
	@Config.RequiresMcRestart
	public String[] runeMagicalBlessing = {"SWORD", "BS_OTHER"};
	@Config.Name("Rune: Piercing Capabilities")
	@Config.RequiresMcRestart
	public String[] runePiercingCapabilities = {"SWORD", "BS_OTHER"};
	@Config.Name("Rune: Resurrection")
	@Config.RequiresMcRestart
	public String[] runeResurrection = {"SHIELD"};
	@Config.Name("Rune: Revival")
	@Config.RequiresMcRestart
	public String[] runeRevival = {"SWORD", "BS_OTHER"};
	
	//Subject
	@Config.Name("Subject English")
	@Config.RequiresMcRestart
	public String[] subjectEnglish = {"SWORD", "BS_OTHER"};
	@Config.Name("Subject History")
	@Config.RequiresMcRestart
	public String[] subjectHistory = {"SWORD", "BS_OTHER"};
	@Config.Name("Subject Mathematics")
	@Config.RequiresMcRestart
	public String[] subjectMathematics = {"SWORD", "BS_OTHER"};
	@Config.Name("Subject P.E.")
	@Config.RequiresMcRestart
	public String[] subjectPE = {"SWORD", "BS_OTHER"};
	@Config.Name("Subject Science")
	@Config.RequiresMcRestart
	public String[] subjectScience = {"SWORD", "BS_OTHER"};
	//Lesser
	@Config.Name("Lesser Bane of Arthropods")
	@Config.RequiresMcRestart
	public String[] lesserBaneOfArthropods = {"SWORD", "BS_OTHER"};
	@Config.Name("Lesser Fire Aspect")
	@Config.RequiresMcRestart
	public String[] lesserFireAspect = {"SWORD", "BS_OTHER"};
	@Config.Name("Lesser Flame")
	@Config.RequiresMcRestart
	public String[] lesserFlame = {"BOW"};
	@Config.Name("Lesser Sharpness")
	@Config.RequiresMcRestart
	public String[] lesserSharpness = {"SWORD", "BS_OTHER"};
	@Config.Name("Lesser Smite")
	@Config.RequiresMcRestart
	public String[] lesserSmite = {"SWORD", "BS_OTHER"};
	
	//Advanced
	@Config.Name("Advanced Bane of Arthropods")
	@Config.RequiresMcRestart
	public String[] advancedBaneOfArthropods = {"SWORD", "BS_OTHER"};
	@Config.Name("Advanced Blast Protection")
	@Config.RequiresMcRestart
	public String[] advancedBlastProtection = {"ARMOR"};
	@Config.Name("Advanced Efficiency")
	@Config.RequiresMcRestart
	public String[] advancedEfficiency = {"TOOL"};
	@Config.Name("Advanced Feather Falling")
	@Config.RequiresMcRestart
	public String[] advancedFeatherFalling = {"ARMOR_FEET"};
	@Config.Name("Advanced Fire Aspect")
	@Config.RequiresMcRestart
	public String[] advancedFireAspect = {"SWORD", "BS_OTHER"};
	@Config.Name("Advanced Fire Protection")
	@Config.RequiresMcRestart
	public String[] advancedFireProtection = {"ARMOR"};
	@Config.Name("Advanced Flame")
	@Config.RequiresMcRestart
	public String[] advancedFlame = {"BOW"};
	@Config.Name("Advanced Knockback")
	@Config.RequiresMcRestart
	public String[] advancedKnockback = {"SWORD", "BS_OTHER"};
	@Config.Name("Advanced Looting")
	@Config.RequiresMcRestart
	public String[] advancedLooting = {"SWORD", "BS_OTHER"};
	@Config.Name("Advanced Luck of the Sea")
	@Config.RequiresMcRestart
	public String[] advancedLuckOfTheSea = {"FISHING_ROD"};
	@Config.Name("Advanced Lure")
	@Config.RequiresMcRestart
	public String[] advancedLure = {"FISHING_ROD"};
	@Config.Name("Advanced Mending")
	@Config.RequiresMcRestart
	public String[] advancedMending = {"BREAKABLE"};
	@Config.Name("Advanced Power")
	@Config.RequiresMcRestart
	public String[] advancedPower = {"BOW"};
	@Config.Name("Advanced Projectile Protection")
	@Config.RequiresMcRestart
	public String[] advancedProjectileProtection = {"ARMOR"};
	@Config.Name("Advanced Protection")
	@Config.RequiresMcRestart
	public String[] advancedProtection = {"ARMOR"};
	@Config.Name("Advanced Punch")
	@Config.RequiresMcRestart
	public String[] advancedPunch = {"BOW"};
	@Config.Name("Advanced Sharpness")
	@Config.RequiresMcRestart
	public String[] advancedSharpness = {"SWORD", "BS_OTHER"};
	@Config.Name("Advanced Smite")
	@Config.RequiresMcRestart
	public String[] advancedSmite = {"SWORD", "BS_OTHER"};
	@Config.Name("Advanced Thorns")
	@Config.RequiresMcRestart
	public String[] advancedThorns = {"ARMOR_CHEST"};
	//Supreme
	@Config.Name("Supreme Bane of Arthropods")
	@Config.RequiresMcRestart
	public String[] supremeBaneOfArthropods = {"SWORD", "BS_OTHER"};
	@Config.Name("Supreme Fire Aspect")
	@Config.RequiresMcRestart
	public String[] supremeFireAspect = {"SWORD", "BS_OTHER"};
	@Config.Name("Supreme Flame")
	@Config.RequiresMcRestart
	public String[] supremeFlame = {"BOW"};
	@Config.Name("Supreme Sharpness")
	@Config.RequiresMcRestart
	public String[] supremeSharpness = {"SWORD", "BS_OTHER"};
	@Config.Name("Supreme Smite")
	@Config.RequiresMcRestart
	public String[] supremeSmite = {"SWORD", "BS_OTHER"};

	public boolean isItemValid(String[] enchantConfig, ItemStack stack){
		Item item = stack.getItem();
		boolean isValid = false;
		boolean invertedMatches = false;
		for(String s: enchantConfig){
			if(this.itemTypes.containsKey(s)){
				EnumEnchantmentType enumEnchantmentType = itemTypes.get(s);
				isValid = isValid || enumEnchantmentType.canEnchantItem(item);
			} else if(this.customTypeMap.containsKey(s)){
				CustomType c = customTypeMap.get(s);
				String itemName = item.getRegistryName().toString();
				boolean matches = itemName.matches(c.regex);
				if(!c.inverted)
					isValid = isValid || matches;
				else
					invertedMatches = invertedMatches || matches;
			} else {
                SoManyEnchantments.LOGGER.info("SME: Could not find given item type {}", s);
			}
		}
		return isValid && !invertedMatches;
	}
}