package com.shultrea.rin.config;

import net.minecraftforge.common.config.Config;

public class IncompatibleConfig {
	
	@Config.Comment("List groupings of enchantments that should be incompatible with each other")
	@Config.Name("Incompatible Enchantment Groups")
	@Config.RequiresMcRestart
	public String[] incompatibleGroups = {
			//Protection tiers, prot vs breached
			"minecraft:protection, minecraft:fire_protection, minecraft:blast_protection, minecraft:projectile_protection, magicprotection, physicalprotection, " +
					"advancedprotection, advancedfireprotection, advancedblastprotection, advancedprojectileprotection, supremeprotection",
			//Physical protection prevents breached
			"physicalprotection, breachedplating",
			//Feather falling tier separate from prot
			"minecraft:feather_falling, advancedfeatherfalling",
			//Thorns tiers
			"minecraft:thorns, advancedthorns, burningthorns, meltdown",
			//Medic vs vulnerability
			"combatmedic, curseofvulnerability",
			//Evasion vs heavyweight
			"evasion, heavyweight",
			//Lightweight vs heavyweight
			"lightweight, heavyweight",
			//Walker opposites
			"minecraft:frost_walker, magma_walker",
			//Vitality vs vulnerability
			"strengthenedvitality, curseofvulnerability",
			//Water enchants vs rusted
			"swiftswimming, rusted",
			"minecraft:depth_strider, rusted",
			//Unbreaking vs rusted
			"minecraft:unbreaking, rusted",
			//Mending tiers, mending not compat with infinity in vanilla
			"minecraft:mending, advancedmending, minecraft:infinity",
			//Adept incompat with education
			"adept, mujmajnkraftsbettersurvival:education",
			//Power tiers, power vs powerless
			"minecraft:power, advancedpower, powerless",
			//Punch tiers, punch vs dragging
			"minecraft:punch, advancedpunch, dragging",
			//Splitshot dupes incompat
			"splitshot, mujmajnkraftsbettersurvival:multishot",
			//Flame tiers, flame vs extinguish
			"minecraft:flame, lesserflame, advancedflame, supremeflame, extinguish",
			//Infinity vs strafe balance
			"minecraft:infinity, strafe",
			//Luck of the sea tiers, luck vs ascetic
			"minecraft:luck_of_the_sea, advancedluckofthesea, ascetic",
			//Lure tiers
			"minecraft:lure, advancedlure",
			//Penetration dupes incompat
			"rune_magicalblessing, rune_piercingcapabilities, mujmajnkraftsbettersurvival:penetration",
			//Efficiency tiers, efficiency vs inefficient
			"minecraft:efficiency, advancedefficiency, inefficient",
			//Silk touch vs smelter
			"minecraft:silk_touch, smelter",
			//Conditional damage tiers
			"viper, darkshadows, mortalitas",
			//Crit damage tiers
			"criticalstrike, luckmagnification",
			//Luck vs ascetic
			"luckmagnification, ascetic",
			//Damage tiers
			"butchering, defusingedge, inhumane, wateraspect",
			//Damage mult tiers
			"ashdestroyer, difficultysendowment, reviledblade, instability, cursededge",
			//Fire tiers, fire vs extinguish
			"minecraft:fire_aspect, lesserfireaspect, advancedfireaspect, supremefireaspect, fieryedge, wateraspect, cryogenic, extinguish",
			//Potion debuff tiers
			"cryogenic, desolator, disorientatingblade, envenomed, horsdecombat, levitator, purification",
			//Subject tiers
			"subjectbiology, subjectchemistry, subjectenglish, subjecthistory, subjectmathematics, subjectpe, subjectphysics",
			//Weather/time/temp tiers
			"clearskiesfavor, lunasblessing, rainsbestowment, solsblessing, thunderstormsbestowment, wintersgrace",
			//Knockback tiers, knockback vs dragging
			"minecraft:knockback, advancedknockback, flinging, mujmajnkraftsbettersurvival:fling, dragging",
			//Looting tiers, education incompat with looting, looting vs ascetic
			"minecraft:looting, advancedlooting, mujmajnkraftsbettersurvival:education, ascetic",
			//Sweeping boost variants
			"minecraft:sweeping, arcslash",
			//Fast swing vs slow
			"swifterslashes, heavyweight",
			//True strike prevents inaccuracy
			"truestrike, curseofinaccuracy",
			//Only one Self Heal
			"lifesteal, blessededge",
			//Sharpness tiers, sharpness vs bluntness
			"lessersharpness, minecraft:sharpness, advancedsharpness, supremesharpness, reinforcedsharpness, bluntness",
			//Smite tiers
			"lessersmite, minecraft:smite, advancedsmite, supremesmite, blessededge",
			//BOA tiers
			"lesserbaneofarthropods, minecraft:bane_of_arthropods, advancedbaneofarthropods, supremebaneofarthropods",
			//Lesser tiers incompat
			"lessersharpness, lessersmite, lesserbaneofarthropods",
			//Advanced tiers incompat
			"advancedsharpness, advancedsmite, advancedbaneofarthropods",
			//Supreme tiers + Penetrating Edge and Spellbreaker which reach similar amounts of dmg
			"supremesharpness, supremesmite, supremebaneofarthropods, penetratingedge, spellbreaker"
	};
}