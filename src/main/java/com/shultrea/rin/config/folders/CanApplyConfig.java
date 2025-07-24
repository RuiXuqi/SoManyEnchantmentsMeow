package com.shultrea.rin.config.folders;

import net.minecraftforge.common.config.Config;

public class CanApplyConfig {

	@Config.Name("Custom Item Types")
	@Config.Comment("Pattern: Name to use;Regex to match item ids to find the item;optional: 'NOT' to invert. If you use the inversion, canApply will use this type as an AND connection instead of an OR connection. So 'NOTGOLD','TOOL','SWORD' will be any sword or tool that is not gold. The custom types can also be used in canApplyAnvil config")
	public String[] customTypes = {
			"BATTLEAXE;(mujmajnkraftsbettersurvival\\:item.*battleaxe)|(spartan(defiled|fire|weaponry)\\:battleaxe.*)",
			"BS_WEAPON;mujmajnkraftsbettersurvival\\:item.*(dagger|nunchaku|hammer|battleaxe)",
			"LYCANITES_EQUIPMENT;lycanitesmobs:equipment",
			"NOT_GOLD;.*gold.*;NOT",
			"SW_CROSSBOW;spartan(defiled|fire|weaponry)\\:crossbow.*",
			"WOLFARMOR;wolfarmor\\:\\w+\\_wolf\\_armor"
	};

	@Config.Name("Adept")
	@Config.RequiresMcRestart
	public String[] adept = {"SWORD", "WOLFARMOR", "AXE", "BOW", "BATTLEAXE", "BS_WEAPON", "LYCANITES_EQUIPMENT", "SW_CROSSBOW"};
	@Config.Name("Ancient Sealed Curses")
	@Config.RequiresMcRestart
	public String[] ancientSealedCurses = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Ancient Sword Mastery")
	@Config.RequiresMcRestart
	public String[] ancientSwordMastery = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Arc Slash")
	@Config.RequiresMcRestart
	public String[] arcSlash = {"SWORD", "WOLFARMOR", "BS_WEAPON"};
	@Config.Name("Ash Destroyer")
	@Config.RequiresMcRestart
	public String[] ashDestroyer = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Atomic Deconstructor")
	@Config.RequiresMcRestart
	public String[] atomicDeconstructor = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Blessed Edge")
	@Config.RequiresMcRestart
	public String[] blessedEdge = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Brutality")
	@Config.RequiresMcRestart
	public String[] brutality = {"AXE", "BATTLEAXE"};
	@Config.Name("Burning Shield")
	@Config.RequiresMcRestart
	public String[] burningShield = {"SHIELD"};
	@Config.Name("Burning Thorns")
	@Config.RequiresMcRestart
	public String[] burningThorns = {"ARMOR_CHEST"};
	@Config.Name("Butchering")
	@Config.RequiresMcRestart
	public String[] butchering = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Clearskies' Favor")
	@Config.RequiresMcRestart
	public String[] clearskiesFavor = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Combat Medic")
	@Config.RequiresMcRestart
	public String[] combatMedic = {"ARMOR_HEAD"};
	@Config.Name("Counter Attack")
	@Config.RequiresMcRestart
	public String[] counterAttack = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Critical Strike")
	@Config.RequiresMcRestart
	public String[] criticalStrike = {"SWORD","AXE", "BATTLEAXE", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Culling")
	@Config.RequiresMcRestart
	public String[] culling = {"AXE", "BATTLEAXE"};
	@Config.Name("Dark Shadows")
	@Config.RequiresMcRestart
	public String[] darkShadows = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Defusing Edge")
	@Config.RequiresMcRestart
	public String[] defusingEdge = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Desolator")
	@Config.RequiresMcRestart
	public String[] desolator = {"AXE", "BATTLEAXE"};
	@Config.Name("Difficulty's Endowment")
	@Config.RequiresMcRestart
	public String[] difficultysEndowment = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Disarmament")
	@Config.RequiresMcRestart
	public String[] disarmament = {"AXE", "BATTLEAXE"};
	@Config.Name("Disorientating Blade")
	@Config.RequiresMcRestart
	public String[] disorientatingBlade = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Empowered Defence")
	@Config.RequiresMcRestart
	public String[] empoweredDefence = {"SHIELD"};
	@Config.Name("Envenomed")
	@Config.RequiresMcRestart
	public String[] envenomed = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Evasion")
	@Config.RequiresMcRestart
	public String[] evasion = {"ARMOR_LEGS"};
	@Config.Name("Fiery Edge")
	@Config.RequiresMcRestart
	public String[] fieryEdge = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Flinging")
	@Config.RequiresMcRestart
	public String[] flinging = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Cryogenic")
	@Config.RequiresMcRestart
	public String[] cryogenic = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Hors De Combat")
	@Config.RequiresMcRestart
	public String[] horsDeCombat = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Inhumane")
	@Config.RequiresMcRestart
	public String[] inhumane = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Inner Berserk")
	@Config.RequiresMcRestart
	public String[] innerBerserk = {"ARMOR_CHEST"};
	@Config.Name("Jagged Rake")
	@Config.RequiresMcRestart
	public String[] jaggedRake = {"HOE"};
	@Config.Name("Levitator")
	@Config.RequiresMcRestart
	public String[] levitator = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Lifesteal")
	@Config.RequiresMcRestart
	public String[] lifesteal = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Light Weight")
	@Config.RequiresMcRestart
	public String[] lightWeight = {"ARMOR_FEET", "WOLFARMOR"};
	@Config.Name("Luck Magnification")
	@Config.RequiresMcRestart
	public String[] luckMagnification = {"SWORD","AXE", "BATTLEAXE", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Lunas Blessing")
	@Config.RequiresMcRestart
	public String[] lunasBlessing = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Magic Protection")
	@Config.RequiresMcRestart
	public String[] magicProtection = {"ARMOR"};
	@Config.Name("Magma Walker")
	@Config.RequiresMcRestart
	public String[] magmaWalker = {"ARMOR_FEET", "WOLFARMOR"};
	@Config.Name("Meltdown")
	@Config.RequiresMcRestart
	public String[] meltdown = {"ARMOR_CHEST"};
	@Config.Name("Moisturized")
	@Config.RequiresMcRestart
	public String[] moisturized = {"HOE"};
	@Config.Name("Mortalitas")
	@Config.RequiresMcRestart
	public String[] mortalitas = {"SWORD","AXE", "BATTLEAXE", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Natural Blocking")
	@Config.RequiresMcRestart
	public String[] naturalBlocking = {"SHIELD"};
	@Config.Name("Parry")
	@Config.RequiresMcRestart
	public String[] parry = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Penetrating Edge")
	@Config.RequiresMcRestart
	public String[] penetratingEdge = {"AXE", "BATTLEAXE"};
	@Config.Name("Physical Protection")
	@Config.RequiresMcRestart
	public String[] physicalProtection = {"ARMOR"};
	@Config.Name("Dragging")
	@Config.RequiresMcRestart
	public String[] dragging = {"BOW","SW_CROSSBOW"};
	@Config.Name("Purging Blade")
	@Config.RequiresMcRestart
	public String[] purgingBlade = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Purification")
	@Config.RequiresMcRestart
	public String[] purification = {"AXE", "BATTLEAXE"};
	@Config.Name("Pushing")
	@Config.RequiresMcRestart
	public String[] pushing = {"BOW"};
	@Config.Name("Rain's Bestowment")
	@Config.RequiresMcRestart
	public String[] rainsBestowment = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Reviled Blade")
	@Config.RequiresMcRestart
	public String[] reviledBlade = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Reinforced Sharpness")
	@Config.RequiresMcRestart
	public String[] reinforcedsharpness = {"TOOL", "LYCANITES_EQUIPMENT"};
	@Config.Name("Smelter")
	@Config.RequiresMcRestart
	public String[] smelter = {"TOOL"};
	@Config.Name("Sol's Blessing")
	@Config.RequiresMcRestart
	public String[] solsBlessing = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Spell Breaker")
	@Config.RequiresMcRestart
	public String[] spellBreaker = {"SWORD","AXE", "BATTLEAXE", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
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
	public String[] swifterSlashes = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Thunderstorm's Bestowment")
	@Config.RequiresMcRestart
	public String[] thunderstormsBestowment = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("True Strike")
	@Config.RequiresMcRestart
	public String[] trueStrike = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Swift Swimming")
	@Config.RequiresMcRestart
	public String[] swiftSwimming = {"ARMOR_FEET", "WOLFARMOR"};
	@Config.Name("Unreasonable")
	@Config.RequiresMcRestart
	public String[] unreasonable = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Unsheathing")
	@Config.RequiresMcRestart
	public String[] unsheathing = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Upgraded Potentials")
	@Config.RequiresMcRestart
	public String[] upgradedPotentials = {"NONE"};
	@Config.Name("Viper")
	@Config.RequiresMcRestart
	public String[] viper = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Water Aspect")
	@Config.RequiresMcRestart
	public String[] waterAspect = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Plowing")
	@Config.RequiresMcRestart
	public String[] plowing = {"HOE"};
	@Config.Name("Winter's Grace")
	@Config.RequiresMcRestart
	public String[] wintersGrace = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};

	//Curses
	@Config.Name("Bluntness")
	@Config.RequiresMcRestart
	public String[] bluntness = {"SWORD","AXE", "BATTLEAXE", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Breached Plating")
	@Config.RequiresMcRestart
	public String[] breachedPlating = {"ARMOR"};
	@Config.Name("Cursed Edge")
	@Config.RequiresMcRestart
	public String[] cursedEdge = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Curse of Decay")
	@Config.RequiresMcRestart
	public String[] curseOfDecay = {"ALL_ITEMS"};
	@Config.Name("Curse of Holding")
	@Config.RequiresMcRestart
	public String[] curseOfHolding = {"ALL_ITEMS"};
	@Config.Name("Curse of Inaccuracy")
	@Config.RequiresMcRestart
	public String[] curseOfInaccuracy = {"SWORD","AXE","BOW", "BATTLEAXE", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Curse of Possession")
	@Config.RequiresMcRestart
	public String[] curseOfPossession = {"ALL_TYPES"};
	@Config.Name("Curse of Vulnerability")
	@Config.RequiresMcRestart
	public String[] curseOfVulnerability = {"ARMOR"};
	@Config.Name("Heavy Weight")
	@Config.RequiresMcRestart
	public String[] heavyWeight = {"ALL_ITEMS"};
	@Config.Name("Inefficient")
	@Config.RequiresMcRestart
	public String[] inefficient = {"TOOL", "LYCANITES_EQUIPMENT"};
	@Config.Name("Instability")
	@Config.RequiresMcRestart
	public String[] instability = {"SWORD","TOOL", "BS_WEAPON"};
	@Config.Name("Pandora's Curse")
	@Config.RequiresMcRestart
	public String[] pandorasCurse = {"ALL_ITEMS"};
	@Config.Name("Powerless")
	@Config.RequiresMcRestart
	public String[] powerless = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Rusted")
	@Config.RequiresMcRestart
	public String[] rusted = {"BREAKABLE","NOT_GOLD"};
	@Config.Name("Unpredictable")
	@Config.RequiresMcRestart
	public String[] unpredictable = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Ascetic")
	@Config.RequiresMcRestart
	public String[] ascetic = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT", "AXE", "FISHING_ROD"};
	@Config.Name("Extinguish")
	@Config.RequiresMcRestart
	public String[] extinguish = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT", "AXE","BOW", "SW_CROSSBOW"};

	//Rune
	@Config.Name("Rune: Arrow Piercing")
	@Config.RequiresMcRestart
	public String[] runeArrowPiercing = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Rune: Magical Blessing")
	@Config.RequiresMcRestart
	public String[] runeMagicalBlessing = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Rune: Piercing Capabilities")
	@Config.RequiresMcRestart
	public String[] runePiercingCapabilities = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Rune: Resurrection")
	@Config.RequiresMcRestart
	public String[] runeResurrection = {"SHIELD"};
	@Config.Name("Rune: Revival")
	@Config.RequiresMcRestart
	public String[] runeRevival = {"BREAKABLE"};
	
	//Subject
	@Config.Name("Subject Biology")
	@Config.RequiresMcRestart
	public String[] subjectBiology = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Subject Chemistry")
	@Config.RequiresMcRestart
	public String[] subjectChemistry = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Subject English")
	@Config.RequiresMcRestart
	public String[] subjectEnglish = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Subject History")
	@Config.RequiresMcRestart
	public String[] subjectHistory = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Subject Mathematics")
	@Config.RequiresMcRestart
	public String[] subjectMathematics = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Subject P.E.")
	@Config.RequiresMcRestart
	public String[] subjectPE = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Subject Physics")
	@Config.RequiresMcRestart
	public String[] subjectPhysics = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	//Lesser
	@Config.Name("Lesser Bane of Arthropods")
	@Config.RequiresMcRestart
	public String[] lesserBaneOfArthropods = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Lesser Fire Aspect")
	@Config.RequiresMcRestart
	public String[] lesserFireAspect = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Lesser Flame")
	@Config.RequiresMcRestart
	public String[] lesserFlame = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Lesser Sharpness")
	@Config.RequiresMcRestart
	public String[] lesserSharpness = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Lesser Smite")
	@Config.RequiresMcRestart
	public String[] lesserSmite = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	
	//Advanced
	@Config.Name("Advanced Bane of Arthropods")
	@Config.RequiresMcRestart
	public String[] advancedBaneOfArthropods = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Advanced Blast Protection")
	@Config.RequiresMcRestart
	public String[] advancedBlastProtection = {"ARMOR"};
	@Config.Name("Advanced Efficiency")
	@Config.RequiresMcRestart
	public String[] advancedEfficiency = {"TOOL", "LYCANITES_EQUIPMENT"};
	@Config.Name("Advanced Feather Falling")
	@Config.RequiresMcRestart
	public String[] advancedFeatherFalling = {"ARMOR_FEET", "WOLFARMOR"};
	@Config.Name("Advanced Fire Aspect")
	@Config.RequiresMcRestart
	public String[] advancedFireAspect = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Advanced Fire Protection")
	@Config.RequiresMcRestart
	public String[] advancedFireProtection = {"ARMOR"};
	@Config.Name("Advanced Flame")
	@Config.RequiresMcRestart
	public String[] advancedFlame = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Advanced Knockback")
	@Config.RequiresMcRestart
	public String[] advancedKnockback = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Advanced Looting")
	@Config.RequiresMcRestart
	public String[] advancedLooting = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
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
	public String[] advancedPower = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Advanced Projectile Protection")
	@Config.RequiresMcRestart
	public String[] advancedProjectileProtection = {"ARMOR"};
	@Config.Name("Advanced Protection")
	@Config.RequiresMcRestart
	public String[] advancedProtection = {"ARMOR"};
	@Config.Name("Advanced Punch")
	@Config.RequiresMcRestart
	public String[] advancedPunch = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Advanced Sharpness")
	@Config.RequiresMcRestart
	public String[] advancedSharpness = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Advanced Smite")
	@Config.RequiresMcRestart
	public String[] advancedSmite = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Advanced Thorns")
	@Config.RequiresMcRestart
	public String[] advancedThorns = {"ARMOR_CHEST"};
	//Supreme
	@Config.Name("Supreme Bane of Arthropods")
	@Config.RequiresMcRestart
	public String[] supremeBaneOfArthropods = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Supreme Fire Aspect")
	@Config.RequiresMcRestart
	public String[] supremeFireAspect = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Supreme Flame")
	@Config.RequiresMcRestart
	public String[] supremeFlame = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Supreme Sharpness")
	@Config.RequiresMcRestart
	public String[] supremeSharpness = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
	@Config.Name("Supreme Smite")
	@Config.RequiresMcRestart
	public String[] supremeSmite = {"SWORD", "WOLFARMOR", "BS_WEAPON", "LYCANITES_EQUIPMENT"};
}