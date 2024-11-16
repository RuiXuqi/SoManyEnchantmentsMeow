package com.shultrea.rin.Config;

import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;

import java.util.ArrayList;

public class IncompatibleConfig {
	
	@Config.Name("Groups of incompatible Enchantments")
	@Config.RequiresMcRestart
	public String[] incompatibleGroups = {"minecraft:feather_falling, advancedfeatherfalling",
			"minecraft:depth_strider, underwaterstrider",
			"heavyweight, swifterslashes",
			"minecraft:unbreaking, rusted",
			"minecraft:unbreaking, instability",
			"minecraft:sweeping, arcslash",
			"minecraft:sweeping, ancientswordmastery",
			"minecraft:silk_touch, smelter",
			"minecraft:silk_touch, minecraft:fortune, quarrying",
			"minecraft:luck_of_the_sea, advancedluckofthesea",
			"minecraft:lure, advancedlure",
			"minecraft:mending, advancedmending",
			"curseofpossession, curseofdecay",
			"truestrike, curseofinaccuracy",
			"minecraft:thorns, advancedthorns, burningthorns",
			"minecraft:efficiency, advancedefficiency, inefficient",
			"minecraft:knockback, advancedknockback, flinging",
			"fieryedge, ashdestroyer",
			"minecraft:looting, advancedlooting",
			"blessededge, lifesteal",
			"rune_piercingcapabilities, rune_arrowpiercing, penetratingedge, rune_magicalblessing, rune_revival, rune_resurrection",
			"viper, darkshadows, mortalitas",
			"minecraft:infinity, strafe",
			"minecraft:power, advancedpower, powerless",
			"minecraft:punch, advancedpunch, dragging, pushing",
			"subjectpe, subjectenglish, subjectscience, subjectmathematics, subjecthistory",
			"minecraft:fire_aspect, lesserfireaspect, advancedfireaspect, supremefireaspect, wateraspect",
			"minecraft:flame, lesserflame, advancedflame, supremeflame",
			"reviledblade, criticalstrike, ashdestroyer, luckmagnification, instability, difficultysendowment, cursededge",
			"clearskiesfavor, rainsbestowment, thunderstormsbestowment, wintersgrace, solsblessing, lunarsblessing",
			"purification, levitator, desolator, disorientatingblade, envenomed, horsdecombat, freezing",
			"minecraft:protection, minecraft:fire_protection, minecraft:blast_protection, minecraft:projectile_protection, magicprotection, physicalprotection, advancedprotection, advancedfireprotection, advancedblastprotection, advancedprojectileprotection, curseofvulnerability",
			"lessersharpness, minecraft:sharpness, advancedsharpness, supremesharpness, reinforcedsharpness, bluntness",
			"lessersmite, minecraft:smite, advancedsmite, supremesmite, blessededge, reinforcedsharpness, bluntness",
			"lesserbaneofarthropods, minecraft:bane_of_arthropods, advancedbaneofarthropods, supremebaneofarthropods, reinforcedsharpness, bluntness",
			"lessersharpness, lessersmite, lesserbaneofarthropods",
			"advancedsharpness, advancedsmite, advancedbaneofarthropods, supremesharpness, supremesmite, supremebaneofarthropods, spellbreaker",
			"defusingedge, inhumane, butchering"};

	public ArrayList<Enchantment> getIncompatibleEnchantmentsString(ResourceLocation name) {
		ArrayList<Enchantment> incompatEnchs = new ArrayList<>();

		Enchantment thisEnch = Enchantment.getEnchantmentByLocation(name.toString());
		if (thisEnch == null) {
            SoManyEnchantments.LOGGER.info("SME: could not find enchantment to get incompatible list for {}", name.toString());
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
                        SoManyEnchantments.LOGGER.info("SME: could not find incompatible enchantment {}", s1);
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