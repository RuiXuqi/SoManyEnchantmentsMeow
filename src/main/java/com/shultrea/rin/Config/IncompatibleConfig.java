package com.shultrea.rin.Config;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.Utility_Sector.SMElogM;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;

import java.util.ArrayList;

public class IncompatibleConfig {
	
	@Config.Name("Groups of incompatible Enchantments")
	@Config.RequiresMcRestart
	public String[] incompatibleGroups = {"minecraft:feather_falling, advancedfeatherfalling",
			"minecraft:feather_falling, curseofvulnerability",
			"minecraft:depth_strider, underwaterstrider",
			"heavyweight, swifterslashes",
			"rusted, minecraft:unbreaking",
			"bluntness, advancedefficiency",
			"bluntness, spellbreaker",
			"bluntness, swifterslashes",
			"bluntness, penetratingedge",
			"minecraft:smite, blessededge",
			"minecraft:knockback, swifterslashes",
			"minecraft:sweeping, arcslash",
			"minecraft:sweeping, ancientswordmastery",
			"butchering, purification",
			"minecraft:silk_touch, smelter",
			"minecraft:silk_touch, minecraft:fortune, quarrying",
			"minecraft:unbreaking, instability",
			"minecraft:punch, strafe",
			"minecraft:infinity, strafe",
			"strafe, advancedpunch",
			"strafe, pushing",
			"minecraft:luck_of_the_sea, advancedluckofthesea",
			"minecraft:lure, advancedlure",
			"minecraft:mending, advancedmending",
			"curseofpossession, curseofdecay",
			//"splitshot, mujmajnkraftsbettersurvival:multishot",
			//"adept, mujmajnkraftsbettersurvival:education",
			"truestrike, curseofinaccuracy",
			"minecraft:thorns, advancedthorns, burningthorns",
			"minecraft:efficiency, advancedefficiency, inefficient",
			"advancedefficiency, blessededge, wateraspect",
			"minecraft:knockback, advancedknockback, flinging", //mujmajnkraftsbettersurvival:fling",
			"minecraft:fire_aspect, fieryedge, ashdestroyer",
			"minecraft:fire_aspect, fieryedge, wateraspect",
			"minecraft:looting, advancedlooting", //mujmajnkraftsbettersurvival:education",
			"blessededge, butchering, cursededge",
			"blessededge, butchering, spellbreaker",
			"blessededge, cursededge, swifterslashes",
			"blessededge, cursededge, lifesteal",
			"blessededge, spellbreaker, wateraspect",
			"blessededge, wateraspect, penetratingedge",
			"cursededge, reviledblade, swifterslashes",
			"rune_piercingcapabilities, penetratingedge, rune_magicalblessing, rune_revival, rune_resurrection",
			"rune_piercingcapabilities, penetratingedge, rune_magicalblessing, rune_revival, rune_arrowpiercing",
			"viper, darkshadows, mortalitas",
			"minecraft:power, advancedpower, powerless",
			"minecraft:punch, advancedpunch, drag",
			"subjectpe, subjectenglish, subjectscience, subjectmathematics, subjecthistory",
			"minecraft:fire_aspect, lesserfireaspect, advancedfireaspect, supremefireaspect",
			"minecraft:flame, lesserflame, advancedflame, supremeflame",
			"reviledblade, criticalstrike, ashdestroyer, luckmagnification, instability, difficultysendowment, cursededge",
			"reviledblade, criticalstrike, ashdestroyer, luckmagnification, instability, difficultysendowment, mortalitas",
			"clearskiesfavor, rainsbestowment, thunderstormsbestowment, wintersgrace, solsblessing, lunarsblessing",
			"purification, levitator, desolator, disorientatingblade, envenomed, horsdecombat, freezing",
			"minecraft:protection, minecraft:fire_protection, minecraft:blast_protection, minecraft:projectile_protection, magicprotection, physicalprotection, advancedprotection, advancedfireprotection, advancedblastprotection, advancedprojectileprotection, curseofvulnerability",
			"advancedsharpness, advancedsmite, advancedbaneofarthropods, defusion, lessersharpness, supremesharpness, lessersmite, supremesmite, lesserbaneofarthropods, supremebaneofarthropods, reinforcedsharpness, inhumane, blessededge, advancedefficiency",
			"advancedsharpness, advancedsmite, advancedbaneofarthropods, defusion, lessersharpness, supremesharpness, lessersmite, supremesmite, lesserbaneofarthropods, supremebaneofarthropods, reinforcedsharpness, inhumane, blessededge, penetratingedge",
			"advancedsharpness, advancedsmite, advancedbaneofarthropods, defusion, lessersharpness, supremesharpness, lessersmite, supremesmite, lesserbaneofarthropods, supremebaneofarthropods, reinforcedsharpness, inhumane, blessededge, spellbreaker",
			"advancedsharpness, advancedsmite, advancedbaneofarthropods, defusion, lessersharpness, supremesharpness, lessersmite, supremesmite, lesserbaneofarthropods, supremebaneofarthropods, reinforcedsharpness, inhumane, bluntness, minecraft:sharpness, minecraft:smite, minecraft:bane_of_arthropods, butchering, wateraspect"};

	public ArrayList<Enchantment> getIncompatibleEnchantmentsString(ResourceLocation name) {
		ArrayList<Enchantment> incompatEnchs = new ArrayList<>();

		Enchantment thisEnch = Enchantment.getEnchantmentByLocation(name.toString());
		if (thisEnch == null) {
            SMElogM.logger.info("SME: could not find enchantment to get incompatible list for {}", name.toString());
			return incompatEnchs;
		}

		for (String s : incompatibleGroups){
			if (s.contains(name.getPath())) {
				//Assumes that config lines are enchantments separated by comma before optional whitespaces
				String[] enchsInList = s.split(", *");
				for (String s1 : enchsInList) {
					//assumes that the config uses modid:enchantment if its not an SME enchant
					if(!s1.contains(":"))
						s1 = SoManyEnchantments.MODID+ ":" + s1;
					Enchantment incompatEnch = Enchantment.getEnchantmentByLocation(s1);
					if (incompatEnch == null)
                        SMElogM.logger.info("SME: could not find incompatible enchantment {}", s1);
					else
						incompatEnchs.add(incompatEnch);
				}
			}
		}
		// remove duplicates of the calling enchant
		// every enchantment is incompatible with itself, this is handled by Enchantment.java though
		// and thus doesnt need to be in this list
		while(incompatEnchs.contains(thisEnch))
			incompatEnchs.remove(thisEnch);

		return incompatEnchs;
	}
}