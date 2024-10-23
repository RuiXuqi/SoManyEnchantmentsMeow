package com.shultrea.rin.Config;

import net.minecraftforge.common.config.Config;


public class EnchantabilityConfig {
	public static final int MIN = 0;
	public static final int SUPER = 1;
	public static final int FIXED = 2;
	public static final int LINEAR = 3;

	@Config.Name("Adept")
	@Config.RequiresMcRestart
	public int[] adept = {26, 12, 40, MIN};
	@Config.Name("Ancient Sealed Curses")
	@Config.RequiresMcRestart
	public int[] ancientSealedCurses = {240,240,720,LINEAR};
	@Config.Name("Ancient Sword Mastery")
	@Config.RequiresMcRestart
	public int[] ancientSwordMastery = {80,80,160,LINEAR};
	@Config.Name("Arc Slash")
	@Config.RequiresMcRestart
	public int[] arcSlash = {20, 15, 40, SUPER};
	@Config.Name("Ash Destroyer")
	@Config.RequiresMcRestart
	public int[] ashDestroyer = {14, 12, 30, MIN};
	@Config.Name("Atomic Deconstructor")
	@Config.RequiresMcRestart
	public int[] atomicDeconstructor = {16, 14, 40, SUPER};
	@Config.Name("Blessed Edge")
	@Config.RequiresMcRestart
	public int[] blessedEdge = {20, 10, 31, MIN};
	@Config.Name("Brutality")
	@Config.RequiresMcRestart
	public int[] brutality = {15, 15, 50, SUPER};
	@Config.Name("Burning Shield")
	@Config.RequiresMcRestart
	public int[] burningShield = {18, 12, 40, MIN};
	@Config.Name("Burning Thorns")
	@Config.RequiresMcRestart
	public int[] burningThorns = {12, 9, 30, MIN};
	@Config.Name("Butchering")
	@Config.RequiresMcRestart
	public int[] butchering = {12, 12, 30, SUPER};
	@Config.Name("Clear Skies' Favor")
	@Config.RequiresMcRestart
	public int[] clearSkiesFavor = {15, 15, 30, MIN};
	@Config.Name("Combat Veterancy")
	@Config.RequiresMcRestart
	public int[] combatVeterancy = {25,15,25, MIN};
	@Config.Name("Counter Attack")
	@Config.RequiresMcRestart
	public int[] counterAttack = {20,15,40, MIN};
	@Config.Name("Critical Strike")
	@Config.RequiresMcRestart
	public int[] criticalStrike ={15, 15, 30, SUPER};
	@Config.Name("Culling")
	@Config.RequiresMcRestart
	public int[] culling = {20, 15, 30, MIN};
	@Config.Name("Dark Shadows")
	@Config.RequiresMcRestart
	public int[] darkShadows = {16, 12, 40, MIN};
	@Config.Name("Defusion")
	@Config.RequiresMcRestart
	public int[] defusion = {10, 12, 30, SUPER};
	@Config.Name("Desolator")
	@Config.RequiresMcRestart
	public int[] desolator = {17, 8, 30, SUPER};
	@Config.Name("Difficulty's Endowment")
	@Config.RequiresMcRestart
	public int[] difficultysEndowment = {10,10,20, SUPER};
	@Config.Name("Disarmament")
	@Config.RequiresMcRestart
	public int[] disarmament = {10, 20, 50, SUPER};
	@Config.Name("Disorientating Blade")
	@Config.RequiresMcRestart
	public int[] disorientatingBlade = {20, 10, 41, MIN};
	@Config.Name("Empowered Defence")
	@Config.RequiresMcRestart
	public int[] empoweredDefence = {15, 15, 30, MIN};
	@Config.Name("Envenomed")
	@Config.RequiresMcRestart
	public int[] envenomed = {16, 12, 30, SUPER};
	@Config.Name("Evasion")
	@Config.RequiresMcRestart
	public int[] evasion = {25, 15, 26, MIN};
	@Config.Name("Fiery Edge")
	@Config.RequiresMcRestart
	public int[] fieryEdge = {25, 15, 40, SUPER};
	@Config.Name("Flinging")
	@Config.RequiresMcRestart
	public int[] flinging = {5, 10, 26, MIN};
	@Config.Name("Freezing")
	@Config.RequiresMcRestart
	public int[] freezing = {24, 13, 40, MIN};
	@Config.Name("Hors De Combat")
	@Config.RequiresMcRestart
	public int[] horsDeCombat = {20, 10, 40, SUPER};
	@Config.Name("Inhumane")
	@Config.RequiresMcRestart
	public int[] inhumane = {15, 15, 40, SUPER};
	@Config.Name("Inner Berserk")
	@Config.RequiresMcRestart
	public int[] innerBerserk = {15, 15, 40, SUPER};
	@Config.Name("Jagged Rake")
	@Config.RequiresMcRestart
	public int[] jaggedRake = {14, 12, 30, SUPER};
	@Config.Name("Levitator")
	@Config.RequiresMcRestart
	public int[] levitator = {15, 15, 30, SUPER};
	@Config.Name("Lifesteal")
	@Config.RequiresMcRestart
	public int[] lifesteal = {6, 8, 30, SUPER};
	@Config.Name("Light Weight")
	@Config.RequiresMcRestart
	public int[] lightWeight = {15, 15, 30, MIN};
	@Config.Name("Luck Magnification")
	@Config.RequiresMcRestart
	public int[] luckMagnification = {15, 15, 30, SUPER};
	@Config.Name("Lunar's Blessing")
	@Config.RequiresMcRestart
	public int[] lunarsBlessing = {16, 12, 40, MIN};
	@Config.Name("Magic Protection")
	@Config.RequiresMcRestart
	public int[] magicProtection = {12, 14, 45, MIN};
	@Config.Name("Magma Walker")
	@Config.RequiresMcRestart
	public int[] magmaWalker = {25, 25, 50, SUPER};
	@Config.Name("Meltdown")
	@Config.RequiresMcRestart
	public int[] meltdown = {20, 20, 40, MIN};
	@Config.Name("Moisturized")
	@Config.RequiresMcRestart
	public int[] moisturized = {20, 0, 21, MIN};
	@Config.Name("Mortalitas")
	@Config.RequiresMcRestart
	public int[] mortalitas = {18, 9, 40, MIN};
	@Config.Name("Natural Blocking")
	@Config.RequiresMcRestart
	public int[] naturalBlocking = {18, 12, 40, MIN};
	@Config.Name("Parry")
	@Config.RequiresMcRestart
	public int[] parry = {20, 15, 40, MIN};
	@Config.Name("Penetrating Edge")
	@Config.RequiresMcRestart
	public int[] penetratingEdge = {14, 12, 40, SUPER};
	@Config.Name("Physical Protection")
	@Config.RequiresMcRestart
	public int[] physicalProtection = {14, 12, 45, MIN};
	@Config.Name("Dragging")
	@Config.RequiresMcRestart
	public int[] drag = {10, 8, 30, MIN};
	@Config.Name("Purging Blade")
	@Config.RequiresMcRestart
	public int[] purgingBlade = {15, 8, 40, MIN};
	@Config.Name("Purification")
	@Config.RequiresMcRestart
	public int[] purification = {18, 12, 30, SUPER};
	@Config.Name("Pushing")
	@Config.RequiresMcRestart
	public int[] pushing = {25, 25, 50, MIN};
	@Config.Name("Quarrying")
	@Config.RequiresMcRestart
	public int[] quarrying = {25,17,50, MIN};
	@Config.Name("Rain's Bestowment")
	@Config.RequiresMcRestart
	public int[] rainsBestowment = {15, 15, 30, MIN};
	@Config.Name("Reviled Blade")
	@Config.RequiresMcRestart
	public int[] reviledBlade = {15, 15, 40, SUPER};
	@Config.Name("Reinforced Sharpness")
	@Config.RequiresMcRestart
	public int[] reinforcedsharpness = {18, 16, 40, SUPER};
	@Config.Name("Smelter")
	@Config.RequiresMcRestart
	public int[] smelter = {30, 30, 50, MIN};
	@Config.Name("Sol's Blessing")
	@Config.RequiresMcRestart
	public int[] solsBlessing = {16, 12, 40, MIN};
	@Config.Name("Spell Breaker")
	@Config.RequiresMcRestart
	public int[] spellBreaker = {15, 15, 30, SUPER};
	@Config.Name("Splitshot")
	@Config.RequiresMcRestart
	public int[] splitShot = {20, 10, 21, MIN};
	@Config.Name("Strafe")
	@Config.RequiresMcRestart
	public int[] strafe = {14, 12, 30, MIN};
	@Config.Name("Strengthened Vitality")
	@Config.RequiresMcRestart
	public int[] strengthenedVitality = {25, 15, 75, MIN};
	@Config.Name("Swifter Slashes")
	@Config.RequiresMcRestart
	public int[] swifterSlashes = {5, 10, 46, MIN};
	@Config.Name("Thunderstorm's Bestowment")
	@Config.RequiresMcRestart
	public int[] thunderstormsBestowment = {20, 15, 40, MIN};
	@Config.Name("True Strike")
	@Config.RequiresMcRestart
	public int[] trueStrike = {15, 15, 30, MIN};
	@Config.Name("Underwater Strider")
	@Config.RequiresMcRestart
	public int[] underwaterStrider = {15, 15, 30, MIN};
	@Config.Name("Unreasonable")
	@Config.RequiresMcRestart
	public int[] unreasonable = {30, 15, 30, MIN};
	@Config.Name("Unsheathing")
	@Config.RequiresMcRestart
	public int[] unsheathing = {30, 15, 30, MIN};
	@Config.Name("Upgraded Potentials")
	@Config.RequiresMcRestart
	public int[] upgradedPotentials = {35, 0, 21, MIN};
	@Config.Name("Viper")
	@Config.RequiresMcRestart
	public int[] viper = {18, 10, 35, MIN};
	@Config.Name("Water Aspect")
	@Config.RequiresMcRestart
	public int[] waterAspect = {5, 10, 36, MIN};
	@Config.Name("Plowing")
	@Config.RequiresMcRestart
	public int[] plowing = {25, 15, 30, SUPER};
	@Config.Name("Winter's Grace")
	@Config.RequiresMcRestart
	public int[] wintersGrace = {10, 10, 40, MIN};

	//Curses
	@Config.Name("Bluntness")
	@Config.RequiresMcRestart
	public int[] bluntness = {14, 14, 40, SUPER};
	@Config.Name("Cursed Edge")
	@Config.RequiresMcRestart
	public int[] cursedEdge = {20, 12, 40, SUPER};
	@Config.Name("Curse of Decay")
	public int[] curseOfDecay = {45, 0, 45, MIN};
	@Config.Name("Curse of Holding")
	@Config.RequiresMcRestart
	public int[] curseOfHolding = {20, 20, 30, MIN};
	@Config.Name("Curse of Inaccuracy")
	@Config.RequiresMcRestart
	public int[] curseOfInaccuracy = {15, 15, 30, MIN};
	@Config.Name("Curse of Possession")
	@Config.RequiresMcRestart
	public int[] curseOfPossession = {25, 25, 25, MIN};
	@Config.Name("Curse of Vulnerability")
	@Config.RequiresMcRestart
	public int[] curseOfVulnerability = {20, 10, 30, MIN};
	@Config.Name("Heavy Weight")
	@Config.RequiresMcRestart
	public int[] heavyWeight = {5, 15, 25, SUPER};
	@Config.Name("Inefficient")
	@Config.RequiresMcRestart
	public int[] inefficient = {15, 15, 30, SUPER};
	@Config.Name("Instability")
	@Config.RequiresMcRestart
	public int[] instability = {30, 15, 30, MIN};
	@Config.Name("Pandora's Curse")
	@Config.RequiresMcRestart
	public int[] pandorasCurse = {100, 50, 11, MIN};
	@Config.Name("Powerless")
	@Config.RequiresMcRestart
	public int[] powerless = {12, 12, 30, MIN};
	@Config.Name("Rusted")
	@Config.RequiresMcRestart
	public int[] rusted = {25, 25, 50, SUPER};
	@Config.Name("Unpredictable")
	@Config.RequiresMcRestart
	public int[] unpredictable = {20, 10, 40, MIN};
	//Rune
	@Config.Name("Rune: Arrow Piercing")
	@Config.RequiresMcRestart
	public int[] runeArrowPiercing = {25, 15, 30, MIN};
	@Config.Name("Rune: Magical Blessing")
	@Config.RequiresMcRestart
	public int[] runeMagicalBlessing = {25,15,50, MIN};
	@Config.Name("Rune: Piercing Capabilities")
	@Config.RequiresMcRestart
	public int[] runePiercingCapabilities = {20, 10, 41, MIN};
	@Config.Name("Rune: Resurrection")
	@Config.RequiresMcRestart
	public int[] runeResurrection = {30,30,50, SUPER};
	@Config.Name("Rune: Revival")
	@Config.RequiresMcRestart
	public int[] runeRevival = {30,30,60, MIN};
	//Subject
	@Config.Name("Subject English")
	@Config.RequiresMcRestart
	public int[] subjectEnglish = {5, 9, 21, MIN};
	@Config.Name("Subject History")
	@Config.RequiresMcRestart
	public int[] subjectHistory = {7,15,23, MIN};
	@Config.Name("Subject Mathematics")
	@Config.RequiresMcRestart
	public int[] subjectMathematics = {8,13,25, MIN};
	@Config.Name("Subject P.E.")
	@Config.RequiresMcRestart
	public int[] subjectPE = {6, 10, 20, MIN};
	@Config.Name("Subject Science")
	@Config.RequiresMcRestart
	public int[] subjectScience = {9, 14, 28, MIN};
	//Lesser
	@Config.Name("Lesser Bane of Arthropods")
	@Config.RequiresMcRestart
	public int[] lesserBaneOfArthropods = {1, 4, 20, MIN};
	@Config.Name("Lesser Fire Aspect")
	@Config.RequiresMcRestart
	public int[] lesserFireAspect = {5, 4, 15, MIN};
	@Config.Name("Lesser Flame")
	@Config.RequiresMcRestart
	public int[] lesserFlame = {10, 0, 30, MIN};
	@Config.Name("Lesser Sharpness")
	@Config.RequiresMcRestart
	public int[] lesserSharpness = {1, 4, 20, MIN};
	@Config.Name("Lesser Smite")
	@Config.RequiresMcRestart
	public int[] lesserSmite = {1, 5, 20, MIN};
	//TODO why so many more advanced?
	//Advanced
	@Config.Name("Advanced Bane of Arthropods")
	@Config.RequiresMcRestart
	public int[] advancedBaneOfArthropods = {15, 15, 40, SUPER};
	@Config.Name("Advanced Blast Protection")
	@Config.RequiresMcRestart
	public int[] advancedBlastProtection = {24, 14, 50, MIN};
	@Config.Name("Advanced Efficiency")
	@Config.RequiresMcRestart
	public int[] advancedEfficiency = {5, 15, 30, SUPER};
	@Config.Name("Advanced Feather Falling")
	@Config.RequiresMcRestart
	public int[] advancedFeatherFalling = {27, 12, 40, MIN};
	@Config.Name("Advanced Fire Aspect")
	@Config.RequiresMcRestart
	public int[] advancedFireAspect = {20, 10, 30, MIN};
	@Config.Name("Advanced Fire Protection")
	@Config.RequiresMcRestart
	public int[] advancedFireProtection = {27, 11, 30, MIN};
	@Config.Name("Advanced Flame")
	@Config.RequiresMcRestart
	public int[] advancedFlame = {35, 0, 70, MIN};
	@Config.Name("Advanced Knockback")
	@Config.RequiresMcRestart
	public int[] advancedKnockback = {25, 10, 16, MIN};
	@Config.Name("Advanced Looting")
	@Config.RequiresMcRestart
	public int[] advancedLooting = {30, 15, 60, SUPER};
	@Config.Name("Advanced Luck of the Sea")
	@Config.RequiresMcRestart
	public int[] advancedLuckOfTheSea = {30, 15, 30, SUPER};
	@Config.Name("Advanced Lure")
	@Config.RequiresMcRestart
	public int[] advancedLure = {30, 15, 30, SUPER};
	@Config.Name("Advanced Mending")
	@Config.RequiresMcRestart
	public int[] advancedMending = {40, 40, 65, MIN};
	@Config.Name("Advanced Power")
	@Config.RequiresMcRestart
	public int[] advancedPower = {10, 8, 30, MIN};
	@Config.Name("Advanced Projectile Protection")
	@Config.RequiresMcRestart
	public int[] advancedProjectileProtection = {27, 11, 40, MIN};
	@Config.Name("Advanced Protection")
	@Config.RequiresMcRestart
	public int[] advancedProtection = {32, 11, 50, MIN};
	@Config.Name("Advanced Punch")
	@Config.RequiresMcRestart
	public int[] advancedPunch = {10, 8, 30, MIN};
	@Config.Name("Advanced Sharpness")
	@Config.RequiresMcRestart
	public int[] advancedSharpness = {15, 15, 40, SUPER};
	@Config.Name("Advanced Smite")
	@Config.RequiresMcRestart
	public int[] advancedSmite = {15, 15, 40, SUPER};
	@Config.Name("Advanced Thorns")
	@Config.RequiresMcRestart
	public int[] advancedThorns = {16, 12, 30, MIN};
	//Supreme
	@Config.Name("Supreme Bane of Arthropods")
	@Config.RequiresMcRestart
	public int[] supremeBaneOfArthropods = {120, 80, 180, MIN};
	@Config.Name("Supreme Fire Aspect")
	@Config.RequiresMcRestart
	public int[] supremeFireAspect = {160, 60, 140, MIN};
	@Config.Name("Supreme Flame")
	@Config.RequiresMcRestart
	public int[] supremeFlame = {160, 0, 140, MIN};
	@Config.Name("Supreme Sharpness")
	@Config.RequiresMcRestart
	public int[] supremeSharpness = {120, 80, 180, MIN};
	@Config.Name("Supreme Smite")
	@Config.RequiresMcRestart
	public int[] supremeSmite = {120, 80, 180, MIN};

	public static int getMinEnchantability(int[] enchantabilityConfig, int level){
		int min = enchantabilityConfig[0];
		int lvlSpan = enchantabilityConfig[1];
		return min+lvlSpan*(level-1);
	}

	public static final int[] superEnchantability = {11,10,5,MIN};

	public static int getMaxEnchantability(int[] enchantabilityConfig, int level){
		int range = enchantabilityConfig[2];
		int maxMode = enchantabilityConfig[3];
		switch(maxMode){
			case MIN: return getMinEnchantability(enchantabilityConfig,level)+range;
			case SUPER: return getMinEnchantability(superEnchantability,level)+range;
			case FIXED: return range;
			case LINEAR: return range*level;
			default: return 0;
		}
	}
}