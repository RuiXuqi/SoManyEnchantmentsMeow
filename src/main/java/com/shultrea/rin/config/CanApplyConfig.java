package com.shultrea.rin.config;

import com.shultrea.rin.util.Types;
import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;

//TODO predefined types are registered with EnumHelper and Creative tabs, custom types probably need to as well?
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
		itemTypes.put("GAPPLE", Types.COMBAT_GOLDEN_APPLE);
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
	public String[] customTypes = {"BS_BATTLEAXE;mujmajnkraft\\:item.*battleaxe", "NOT_GOLD;.*gold.*;NOT"};

	@Config.Name("Adept")
	@Config.RequiresMcRestart
	public String[] adept = {"SWORD","AXE","BOW"};
	@Config.Name("Ancient Sealed Curses")
	@Config.RequiresMcRestart
	public String[] ancientSealedCurses = {"SWORD"};
	@Config.Name("Ancient Sword Mastery")
	@Config.RequiresMcRestart
	public String[] ancientSwordMastery = {"SWORD"};
	@Config.Name("Arc Slash")
	@Config.RequiresMcRestart
	public String[] arcSlash = {"SWORD"};
	@Config.Name("Ash Destroyer")
	@Config.RequiresMcRestart
	public String[] ashDestroyer = {"SWORD"};
	@Config.Name("Atomic Deconstructor")
	@Config.RequiresMcRestart
	public String[] atomicDeconstructor = {"SWORD"};
	@Config.Name("Blessed Edge")
	@Config.RequiresMcRestart
	public String[] blessedEdge = {"SWORD"};
	@Config.Name("Brutality")
	@Config.RequiresMcRestart
	public String[] brutality = {"AXE"};
	@Config.Name("Burning Shield")
	@Config.RequiresMcRestart
	public String[] burningShield = {"SHIELD"};
	@Config.Name("Burning Thorns")
	@Config.RequiresMcRestart
	public String[] burningThorns = {"ARMOR"};
	@Config.Name("Butchering")
	@Config.RequiresMcRestart
	public String[] butchering = {"SWORD"};
	@Config.Name("Clearskies' Favor")
	@Config.RequiresMcRestart
	public String[] clearskiesFavor = {"SWORD"};
	@Config.Name("Combat Veterancy")
	@Config.RequiresMcRestart
	public String[] combatVeterancy = {"SWORD"};
	@Config.Name("Counter Attack")
	@Config.RequiresMcRestart
	public String[] counterAttack = {"SWORD"};
	@Config.Name("Critical Strike")
	@Config.RequiresMcRestart
	public String[] criticalStrike = {"SWORD","AXE"};
	@Config.Name("Culling")
	@Config.RequiresMcRestart
	public String[] culling = {"AXE"};
	@Config.Name("Dark Shadows")
	@Config.RequiresMcRestart
	public String[] darkShadows = {"SWORD"};
	@Config.Name("Defusing Edge")
	@Config.RequiresMcRestart
	public String[] defusingEdge = {"SWORD"};
	@Config.Name("Desolator")
	@Config.RequiresMcRestart
	public String[] desolator = {"AXE"};
	@Config.Name("Difficulty's Endowment")
	@Config.RequiresMcRestart
	public String[] difficultysEndowment = {"SWORD"};
	@Config.Name("Disarmament")
	@Config.RequiresMcRestart
	public String[] disarmament = {"AXE"};
	@Config.Name("Disorientating Blade")
	@Config.RequiresMcRestart
	public String[] disorientatingBlade = {"SWORD"};
	@Config.Name("Empowered Defence")
	@Config.RequiresMcRestart
	public String[] empoweredDefence = {"SHIELD"};
	@Config.Name("Envenomed")
	@Config.RequiresMcRestart
	public String[] envenomed = {"SWORD"};
	@Config.Name("Evasion")
	@Config.RequiresMcRestart
	public String[] evasion = {"ARMOR_LEGS"};
	@Config.Name("Fiery Edge")
	@Config.RequiresMcRestart
	public String[] fieryEdge = {"SWORD"};
	@Config.Name("Flinging")
	@Config.RequiresMcRestart
	public String[] flinging = {"SWORD"};
	@Config.Name("Freezing")
	@Config.RequiresMcRestart
	public String[] freezing = {"SWORD"};
	@Config.Name("Hors De Combat")
	@Config.RequiresMcRestart
	public String[] horsDeCombat = {"SWORD"};
	@Config.Name("Inhumane")
	@Config.RequiresMcRestart
	public String[] inhumane = {"SWORD"};
	@Config.Name("Inner Berserk")
	@Config.RequiresMcRestart
	public String[] innerBerserk = {"ARMOR_CHEST"};
	@Config.Name("Jagged Rake")
	@Config.RequiresMcRestart
	public String[] jaggedRake = {"HOE"};
	@Config.Name("Levitator")
	@Config.RequiresMcRestart
	public String[] levitator = {"SWORD"};
	@Config.Name("Lifesteal")
	@Config.RequiresMcRestart
	public String[] lifesteal = {"SWORD"};
	@Config.Name("Light Weight")
	@Config.RequiresMcRestart
	public String[] lightWeight = {"ARMOR_FEET"};
	@Config.Name("Luck Magnification")
	@Config.RequiresMcRestart
	public String[] luckMagnification = {"SWORD","AXE"};
	@Config.Name("Lunars Blessing")
	@Config.RequiresMcRestart
	public String[] lunarsBlessing = {"SWORD"};
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
	public String[] mortalitas = {"SWORD","AXE"};
	@Config.Name("Natural Blocking")
	@Config.RequiresMcRestart
	public String[] naturalBlocking = {"SHIELD"};
	@Config.Name("Parry")
	@Config.RequiresMcRestart
	public String[] parry = {"SWORD"};
	@Config.Name("Penetrating Edge")
	@Config.RequiresMcRestart
	public String[] penetratingEdge = {"AXE"};
	@Config.Name("Physical Protection")
	@Config.RequiresMcRestart
	public String[] physicalProtection = {"ARMOR"};
	@Config.Name("Dragging")
	@Config.RequiresMcRestart
	public String[] dragging = {"BOW"};
	@Config.Name("Purging Blade")
	@Config.RequiresMcRestart
	public String[] purgingBlade = {"SWORD"};
	@Config.Name("Purification")
	@Config.RequiresMcRestart
	public String[] purification = {"AXE"};
	@Config.Name("Pushing")
	@Config.RequiresMcRestart
	public String[] pushing = {"BOW"};
	@Config.Name("Quarrying")
	@Config.RequiresMcRestart
	public String[] quarrying = {"PICKAXE"};
	@Config.Name("Rain's Bestowment")
	@Config.RequiresMcRestart
	public String[] rainsBestowment = {"SWORD"};
	@Config.Name("Reviled Blade")
	@Config.RequiresMcRestart
	public String[] reviledBlade = {"SWORD"};
	@Config.Name("Reinforced Sharpness")
	@Config.RequiresMcRestart
	public String[] reinforcedsharpness = {"TOOL"};
	@Config.Name("Smelter")
	@Config.RequiresMcRestart
	public String[] smelter = {"TOOL"};
	@Config.Name("Sol's Blessing")
	@Config.RequiresMcRestart
	public String[] solsBlessing = {"SWORD"};
	@Config.Name("Spell Breaker")
	@Config.RequiresMcRestart
	public String[] spellBreaker = {"SWORD","AXE"};
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
	public String[] swifterSlashes = {"SWORD"};
	@Config.Name("Thunderstorm's Bestowment")
	@Config.RequiresMcRestart
	public String[] thunderstormsBestowment = {"SWORD"};
	@Config.Name("True Strike")
	@Config.RequiresMcRestart
	public String[] trueStrike = {"SWORD"};
	@Config.Name("Underwater Strider")
	@Config.RequiresMcRestart
	public String[] underwaterStrider = {"ARMOR_FEET"};
	@Config.Name("Unreasonable")
	@Config.RequiresMcRestart
	public String[] unreasonable = {"SWORD"};
	@Config.Name("Unsheathing")
	@Config.RequiresMcRestart
	public String[] unsheathing = {"SWORD"};
	@Config.Name("Upgraded Potentials")
	@Config.RequiresMcRestart
	public String[] upgradedPotentials = {"NONE"};
	@Config.Name("Viper")
	@Config.RequiresMcRestart
	public String[] viper = {"SWORD"};
	@Config.Name("Water Aspect")
	@Config.RequiresMcRestart
	public String[] waterAspect = {"SWORD"};
	@Config.Name("Plowing")
	@Config.RequiresMcRestart
	public String[] plowing = {"HOE"};
	@Config.Name("Winter's Grace")
	@Config.RequiresMcRestart
	public String[] wintersGrace = {"SWORD"};

	//Curses
	@Config.Name("Bluntness")
	@Config.RequiresMcRestart
	public String[] bluntness = {"SWORD","AXE"};
	@Config.Name("Cursed Edge")
	@Config.RequiresMcRestart
	public String[] cursedEdge = {"SWORD"};
	@Config.Name("Curse of Decay")
	@Config.RequiresMcRestart
	public String[] curseOfDecay = {"ALL_ITEMS"};
	@Config.Name("Curse of Holding")
	@Config.RequiresMcRestart
	public String[] curseOfHolding = {"ALL_ITEMS"};
	@Config.Name("Curse of Inaccuracy")
	@Config.RequiresMcRestart
	public String[] curseOfInaccuracy = {"SWORD","AXE","BOW"};
	@Config.Name("Curse of Possession")
	@Config.RequiresMcRestart
	public String[] curseOfPossession = {"ALL_TYPES"};
	@Config.Name("Curse of Vulnerability")
	@Config.RequiresMcRestart
	public String[] curseOfVulnerability = {"ARMOR"};
	@Config.Name("Heavy Weight")
	@Config.RequiresMcRestart
	public String[] heavyWeight = {"SWORD"};
	@Config.Name("Inefficient")
	@Config.RequiresMcRestart
	public String[] inefficient = {"TOOL"};
	@Config.Name("Instability")
	@Config.RequiresMcRestart
	public String[] instability = {"SWORD","TOOL"};
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
	public String[] unpredictable = {"SWORD"};
	
	//Rune
	@Config.Name("Rune: Arrow Piercing")
	@Config.RequiresMcRestart
	public String[] runeArrowPiercing = {"BOW"};
	@Config.Name("Rune: Magical Blessing")
	@Config.RequiresMcRestart
	public String[] runeMagicalBlessing = {"SWORD"};
	@Config.Name("Rune: Piercing Capabilities")
	@Config.RequiresMcRestart
	public String[] runePiercingCapabilities = {"SWORD"};
	@Config.Name("Rune: Resurrection")
	@Config.RequiresMcRestart
	public String[] runeResurrection = {"GAPPLE"};
	@Config.Name("Rune: Revival")
	@Config.RequiresMcRestart
	public String[] runeRevival = {"SWORD"};
	
	//Subject
	@Config.Name("Subject English")
	@Config.RequiresMcRestart
	public String[] subjectEnglish = {"SWORD"};
	@Config.Name("Subject History")
	@Config.RequiresMcRestart
	public String[] subjectHistory = {"SWORD"};
	@Config.Name("Subject Mathematics")
	@Config.RequiresMcRestart
	public String[] subjectMathematics = {"SWORD"};
	@Config.Name("Subject P.E.")
	@Config.RequiresMcRestart
	public String[] subjectPE = {"SWORD"};
	@Config.Name("Subject Science")
	@Config.RequiresMcRestart
	public String[] subjectScience = {"SWORD"};
	//Lesser
	@Config.Name("Lesser Bane of Arthropods")
	@Config.RequiresMcRestart
	public String[] lesserBaneOfArthropods = {"SWORD"};
	@Config.Name("Lesser Fire Aspect")
	@Config.RequiresMcRestart
	public String[] lesserFireAspect = {"SWORD"};
	@Config.Name("Lesser Flame")
	@Config.RequiresMcRestart
	public String[] lesserFlame = {"BOW"};
	@Config.Name("Lesser Sharpness")
	@Config.RequiresMcRestart
	public String[] lesserSharpness = {"SWORD"};
	@Config.Name("Lesser Smite")
	@Config.RequiresMcRestart
	public String[] lesserSmite = {"SWORD"};
	
	//Advanced
	@Config.Name("Advanced Bane of Arthropods")
	@Config.RequiresMcRestart
	public String[] advancedBaneOfArthropods = {"SWORD"};
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
	public String[] advancedFireAspect = {"SWORD"};
	@Config.Name("Advanced Fire Protection")
	@Config.RequiresMcRestart
	public String[] advancedFireProtection = {"ARMOR"};
	@Config.Name("Advanced Flame")
	@Config.RequiresMcRestart
	public String[] advancedFlame = {"BOW"};
	@Config.Name("Advanced Knockback")
	@Config.RequiresMcRestart
	public String[] advancedKnockback = {"SWORD"};
	@Config.Name("Advanced Looting")
	@Config.RequiresMcRestart
	public String[] advancedLooting = {"SWORD"};
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
	public String[] advancedSharpness = {"SWORD"};
	@Config.Name("Advanced Smite")
	@Config.RequiresMcRestart
	public String[] advancedSmite = {"SWORD"};
	@Config.Name("Advanced Thorns")
	@Config.RequiresMcRestart
	public String[] advancedThorns = {"ARMOR"};
	//Supreme
	@Config.Name("Supreme Bane of Arthropods")
	@Config.RequiresMcRestart
	public String[] supremeBaneOfArthropods = {"SWORD"};
	@Config.Name("Supreme Fire Aspect")
	@Config.RequiresMcRestart
	public String[] supremeFireAspect = {"SWORD"};
	@Config.Name("Supreme Flame")
	@Config.RequiresMcRestart
	public String[] supremeFlame = {"BOW"};
	@Config.Name("Supreme Sharpness")
	@Config.RequiresMcRestart
	public String[] supremeSharpness = {"SWORD"};
	@Config.Name("Supreme Smite")
	@Config.RequiresMcRestart
	public String[] supremeSmite = {"SWORD"};

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