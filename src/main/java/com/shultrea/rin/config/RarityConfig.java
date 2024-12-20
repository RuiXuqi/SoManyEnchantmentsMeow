package com.shultrea.rin.config;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.config.Config;

public class RarityConfig {

	static final int COMMON = 0;
	static final int UNCOMMON = 1;
	static final int RARE = 2;
	static final int VERY_RARE = 3;

	@Config.Name("Adept")
	@Config.RequiresMcRestart
	public int adept = RARE;
	@Config.Name("Ancient Sealed Curses")
	@Config.RequiresMcRestart
	public int ancientSealedCurses = VERY_RARE;
	@Config.Name("Ancient Sword Mastery")
	@Config.RequiresMcRestart
	public int ancientSwordMastery = VERY_RARE;
	@Config.Name("Arc Slash")
	@Config.RequiresMcRestart
	public int arcSlash = VERY_RARE;
	@Config.Name("Ash Destroyer")
	@Config.RequiresMcRestart
	public int ashDestroyer = RARE;
	@Config.Name("Atomic Deconstructor")
	@Config.RequiresMcRestart
	public int atomicDeconstructor = RARE;
	@Config.Name("Blessed Edge")
	@Config.RequiresMcRestart
	public int blessedEdge = RARE;
	@Config.Name("Brutality")
	@Config.RequiresMcRestart
	public int brutality = RARE;
	@Config.Name("Burning Shield")
	@Config.RequiresMcRestart
	public int burningShield = RARE;
	@Config.Name("Burning Thorns")
	@Config.RequiresMcRestart
	public int burningThorns = VERY_RARE;
	@Config.Name("Butchering")
	@Config.RequiresMcRestart
	public int butchering = RARE;
	@Config.Name("Clearskies' Favor")
	@Config.RequiresMcRestart
	public int clearskiesFavor = RARE;
	@Config.Name("Combat Veterancy")
	@Config.RequiresMcRestart
	public int combatVeterancy = RARE;
	@Config.Name("Counter Attack")
	@Config.RequiresMcRestart
	public int counterAttack = RARE;
	@Config.Name("Critical Strike")
	@Config.RequiresMcRestart
	public int criticalStrike = RARE;
	@Config.Name("Culling")
	@Config.RequiresMcRestart
	public int culling = VERY_RARE;
	@Config.Name("Dark Shadows")
	@Config.RequiresMcRestart
	public int darkShadows = RARE;
	@Config.Name("Defusing Edge")
	@Config.RequiresMcRestart
	public int defusingEdge = RARE;
	@Config.Name("Desolator")
	@Config.RequiresMcRestart
	public int desolator = RARE;
	@Config.Name("Difficulty's Endowment")
	@Config.RequiresMcRestart
	public int difficultysEndowment = RARE;
	@Config.Name("Disarmament")
	@Config.RequiresMcRestart
	public int disarmament = RARE;
	@Config.Name("Disorientating Blade")
	@Config.RequiresMcRestart
	public int disorientatingBlade = RARE;
	@Config.Name("Empowered Defence")
	@Config.RequiresMcRestart
	public int empoweredDefence = RARE;
	@Config.Name("Envenomed")
	@Config.RequiresMcRestart
	public int envenomed = RARE;
	@Config.Name("Evasion")
	@Config.RequiresMcRestart
	public int evasion = RARE;
	@Config.Name("Fiery Edge")
	@Config.RequiresMcRestart
	public int fieryEdge = VERY_RARE;
	@Config.Name("Flinging")
	@Config.RequiresMcRestart
	public int flinging = UNCOMMON;
	@Config.Name("Freezing")
	@Config.RequiresMcRestart
	public int freezing = RARE;
	@Config.Name("Hors De Combat")
	@Config.RequiresMcRestart
	public int horsDeCombat = RARE;
	@Config.Name("Inhumane")
	@Config.RequiresMcRestart
	public int inhumane = RARE;
	@Config.Name("Inner Berserk")
	@Config.RequiresMcRestart
	public int innerBerserk = VERY_RARE;
	@Config.Name("Jagged Rake")
	@Config.RequiresMcRestart
	public int jaggedRake = RARE;
	@Config.Name("Levitator")
	@Config.RequiresMcRestart
	public int levitator = RARE;
	@Config.Name("Lifesteal")
	@Config.RequiresMcRestart
	public int lifesteal = RARE;
	@Config.Name("Light Weight")
	@Config.RequiresMcRestart
	public int lightWeight = RARE;
	@Config.Name("Luck Magnification")
	@Config.RequiresMcRestart
	public int luckMagnification = RARE;
	@Config.Name("Lunars Blessing")
	@Config.RequiresMcRestart
	public int lunarsBlessing = RARE;
	@Config.Name("Magic Protection")
	@Config.RequiresMcRestart
	public int magicProtection = UNCOMMON;
	@Config.Name("Magma Walker")
	@Config.RequiresMcRestart
	public int magmaWalker = VERY_RARE;
	@Config.Name("Meltdown")
	@Config.RequiresMcRestart
	public int meltdown = VERY_RARE;
	@Config.Name("Moisturized")
	@Config.RequiresMcRestart
	public int moisturized = UNCOMMON;
	@Config.Name("Mortalitas")
	@Config.RequiresMcRestart
	public int mortalitas = VERY_RARE;
	@Config.Name("Natural Blocking")
	@Config.RequiresMcRestart
	public int naturalBlocking = RARE;
	@Config.Name("Parry")
	@Config.RequiresMcRestart
	public int parry = RARE;
	@Config.Name("Penetrating Edge")
	@Config.RequiresMcRestart
	public int penetratingEdge = RARE;
	@Config.Name("Physical Protection")
	@Config.RequiresMcRestart
	public int physicalProtection = RARE;
	@Config.Name("Dragging")
	@Config.RequiresMcRestart
	public int dragging = VERY_RARE;
	@Config.Name("Purging Blade")
	@Config.RequiresMcRestart
	public int purgingBlade = UNCOMMON;
	@Config.Name("Purification")
	@Config.RequiresMcRestart
	public int purification = UNCOMMON;
	@Config.Name("Pushing")
	@Config.RequiresMcRestart
	public int pushing = VERY_RARE;
	@Config.Name("Rain's Bestowment")
	@Config.RequiresMcRestart
	public int rainsBestowment = VERY_RARE;
	@Config.Name("Reviled Blade")
	@Config.RequiresMcRestart
	public int reviledBlade = VERY_RARE;
	@Config.Name("Reinforced Sharpness")
	@Config.RequiresMcRestart
	public int reinforcedsharpness = RARE;
	@Config.Name("Smelter")
	@Config.RequiresMcRestart
	public int smelter = VERY_RARE;
	@Config.Name("Sol's Blessing")
	@Config.RequiresMcRestart
	public int solsBlessing = VERY_RARE;
	@Config.Name("Spell Breaker")
	@Config.RequiresMcRestart
	public int spellBreaker = RARE;
	@Config.Name("Splitshot")
	@Config.RequiresMcRestart
	public int splitShot = VERY_RARE;
	@Config.Name("Strafe")
	@Config.RequiresMcRestart
	public int strafe = UNCOMMON;
	@Config.Name("Strengthened Vitality")
	@Config.RequiresMcRestart
	public int strengthenedVitality = VERY_RARE;
	@Config.Name("Swifter Slashes")
	@Config.RequiresMcRestart
	public int swifterSlashes = VERY_RARE;
	@Config.Name("Thunderstorm's Bestowment")
	@Config.RequiresMcRestart
	public int thunderstormsBestowment = VERY_RARE;
	@Config.Name("True Strike")
	@Config.RequiresMcRestart
	public int trueStrike = RARE;
	@Config.Name("Underwater Strider")
	@Config.RequiresMcRestart
	public int underwaterStrider = RARE;
	@Config.Name("Unreasonable")
	@Config.RequiresMcRestart
	public int unreasonable = RARE;
	@Config.Name("Unsheathing")
	@Config.RequiresMcRestart
	public int unsheathing = VERY_RARE;
	@Config.Name("Upgraded Potentials")
	@Config.RequiresMcRestart
	public int upgradedPotentials = RARE;
	@Config.Name("Viper")
	@Config.RequiresMcRestart
	public int viper = RARE;
	@Config.Name("Water Aspect")
	@Config.RequiresMcRestart
	public int waterAspect = RARE;
	@Config.Name("Plowing")
	@Config.RequiresMcRestart
	public int plowing = RARE;
	@Config.Name("Winter's Grace")
	@Config.RequiresMcRestart
	public int wintersGrace = VERY_RARE;
	//Curses
	@Config.Name("Bluntness")
	@Config.RequiresMcRestart
	public int bluntness = RARE;
	@Config.Name("Cursed Edge")
	@Config.RequiresMcRestart
	public int cursedEdge = RARE;
	@Config.Name("Curse of Decay")
	@Config.RequiresMcRestart
	public int curseOfDecay = VERY_RARE;
	@Config.Name("Curse of Holding")
	@Config.RequiresMcRestart
	public int curseOfHolding = VERY_RARE;
	@Config.Name("Curse of Inaccuracy")
	@Config.RequiresMcRestart
	public int curseOfInaccuracy = VERY_RARE;
	@Config.Name("Curse of Possession")
	@Config.RequiresMcRestart
	public int curseOfPossession = VERY_RARE;
	@Config.Name("Curse of Vulnerability")
	@Config.RequiresMcRestart
	public int curseOfVulnerability = VERY_RARE;
	@Config.Name("Heavy Weight")
	@Config.RequiresMcRestart
	public int heavyWeight = RARE;
	@Config.Name("Inefficient")
	@Config.RequiresMcRestart
	public int inefficient = RARE;
	@Config.Name("Instability")
	@Config.RequiresMcRestart
	public int instability = VERY_RARE;
	@Config.Name("Pandora's Curse")
	@Config.RequiresMcRestart
	public int pandorasCurse = VERY_RARE;
	@Config.Name("Powerless")
	@Config.RequiresMcRestart
	public int powerless = RARE;
	@Config.Name("Rusted")
	@Config.RequiresMcRestart
	public int rusted = RARE;
	@Config.Name("Unpredictable")
	@Config.RequiresMcRestart
	public int unpredictable = VERY_RARE;
	@Config.Name("Ascetic")
	@Config.RequiresMcRestart
	public int ascetic = RARE;
	@Config.Name("Extinguish")
	@Config.RequiresMcRestart
	public int extinguish = VERY_RARE;
	
	//Rune
	@Config.Name("Rune: Arrow Piercing")
	@Config.RequiresMcRestart
	public int runeArrowPiercing = VERY_RARE;
	@Config.Name("Rune: Magical Blessing")
	@Config.RequiresMcRestart
	public int runeMagicalBlessing = VERY_RARE;
	@Config.Name("Rune: Piercing Capabilities")
	@Config.RequiresMcRestart
	public int runePiercingCapabilities = VERY_RARE;
	@Config.Name("Rune: Resurrection")
	@Config.RequiresMcRestart
	public int runeResurrection = VERY_RARE;
	@Config.Name("Rune: Revival")
	@Config.RequiresMcRestart
	public int runeRevival = VERY_RARE;
	
	//Subject
	@Config.Name("Subject English")
	@Config.RequiresMcRestart
	public int subjectEnglish = VERY_RARE;
	@Config.Name("Subject History")
	@Config.RequiresMcRestart
	public int subjectHistory = VERY_RARE;
	@Config.Name("Subject Mathematics")
	@Config.RequiresMcRestart
	public int subjectMathematics = VERY_RARE;
	@Config.Name("Subject P.E.")
	@Config.RequiresMcRestart
	public int subjectPE = VERY_RARE;
	@Config.Name("Subject Science")
	@Config.RequiresMcRestart
	public int subjectScience = VERY_RARE;
	//Lesser
	@Config.Name("Lesser Bane of Arthropods")
	@Config.RequiresMcRestart
	public int lesserBaneOfArthropods = COMMON;
	@Config.Name("Lesser Fire Aspect")
	@Config.RequiresMcRestart
	public int lesserFireAspect = COMMON;
	@Config.Name("Lesser Flame")
	@Config.RequiresMcRestart
	public int lesserFlame = COMMON;
	@Config.Name("Lesser Sharpness")
	@Config.RequiresMcRestart
	public int lesserSharpness = COMMON;
	@Config.Name("Lesser Smite")
	@Config.RequiresMcRestart
	public int lesserSmite = COMMON;
	
	//Advanced
	@Config.Name("Advanced Bane of Arthropods")
	@Config.RequiresMcRestart
	public int advancedBaneOfArthropods = RARE;
	@Config.Name("Advanced Blast Protection")
	@Config.RequiresMcRestart
	public int advancedBlastProtection = RARE;
	@Config.Name("Advanced Efficiency")
	@Config.RequiresMcRestart
	public int advancedEfficiency = RARE;
	@Config.Name("Advanced Feather Falling")
	@Config.RequiresMcRestart
	public int advancedFeatherFalling = VERY_RARE;
	@Config.Name("Advanced Fire Aspect")
	@Config.RequiresMcRestart
	public int advancedFireAspect = RARE;
	@Config.Name("Advanced Fire Protection")
	@Config.RequiresMcRestart
	public int advancedFireProtection = RARE;
	@Config.Name("Advanced Flame")
	@Config.RequiresMcRestart
	public int advancedFlame = VERY_RARE;
	@Config.Name("Advanced Knockback")
	@Config.RequiresMcRestart
	public int advancedKnockback = VERY_RARE;
	@Config.Name("Advanced Looting")
	@Config.RequiresMcRestart
	public int advancedLooting = VERY_RARE;
	@Config.Name("Advanced Luck of the Sea")
	@Config.RequiresMcRestart
	public int advancedLuckOfTheSea = VERY_RARE;
	@Config.Name("Advanced Lure")
	@Config.RequiresMcRestart
	public int advancedLure = VERY_RARE;
	@Config.Name("Advanced Mending")
	@Config.RequiresMcRestart
	public int advancedMending = VERY_RARE;
	@Config.Name("Advanced Power")
	@Config.RequiresMcRestart
	public int advancedPower = VERY_RARE;
	@Config.Name("Advanced Projectile Protection")
	@Config.RequiresMcRestart
	public int advancedProjectileProtection = RARE;
	@Config.Name("Advanced Protection")
	@Config.RequiresMcRestart
	public int advancedProtection = VERY_RARE;
	@Config.Name("Advanced Punch")
	@Config.RequiresMcRestart
	public int advancedPunch = VERY_RARE;
	@Config.Name("Advanced Sharpness")
	@Config.RequiresMcRestart
	public int advancedSharpness = RARE;
	@Config.Name("Advanced Smite")
	@Config.RequiresMcRestart
	public int advancedSmite = RARE;
	@Config.Name("Advanced Thorns")
	@Config.RequiresMcRestart
	public int advancedThorns = RARE;
	//Supreme
	@Config.Name("Supreme Bane of Arthropods")
	@Config.RequiresMcRestart
	public int supremeBaneOfArthropods = VERY_RARE;
	@Config.Name("Supreme Fire Aspect")
	@Config.RequiresMcRestart
	public int supremeFireAspect = VERY_RARE;
	@Config.Name("Supreme Flame")
	@Config.RequiresMcRestart
	public int supremeFlame = VERY_RARE;
	@Config.Name("Supreme Sharpness")
	@Config.RequiresMcRestart
	public int supremeSharpness = VERY_RARE;
	@Config.Name("Supreme Smite")
	@Config.RequiresMcRestart
	public int supremeSmite = VERY_RARE;

	public static Enchantment.Rarity get(int rarity){
		switch(rarity){
			case COMMON: return Enchantment.Rarity.COMMON;
			case UNCOMMON: return Enchantment.Rarity.UNCOMMON;
			case RARE: return Enchantment.Rarity.RARE;
			case VERY_RARE: return Enchantment.Rarity.VERY_RARE;
			default: return Enchantment.Rarity.COMMON;
		}
	}
}