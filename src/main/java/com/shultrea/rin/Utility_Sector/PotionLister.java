package com.shultrea.rin.Utility_Sector;

import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.potion.Potion;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PotionLister {
	
	static final List<Integer> buff_instant_ids = new ArrayList<>();
	static final List<Integer> debuff_instant_ids = new ArrayList<>();
	static final List<Integer> buff_ids = new ArrayList<>();
	static final List<Integer> debuff_ids = new ArrayList<>();

	public static void initializePotionLists() {
		List<String> potionBlacklist = new ArrayList<>();

		for(String str : ModConfig.miscellaneous.potionBlacklist) {
			potionBlacklist.add(str.toLowerCase(Locale.ENGLISH));
		}

		for(Potion potion : Potion.REGISTRY) {
			String potionName = potion.getRegistryName().toString().toLowerCase(Locale.ENGLISH);
			boolean listMatch = potionBlacklist.contains(potionName);

			if(listMatch == ModConfig.miscellaneous.potionBlacklistAsWhitelist) {
				//0 - buff 1 = insta buff not bad 2 = bad effect not insta  3 = bad eff + insta
				int flag = 0;
				if(potion.isBadEffect()) flag += 2;
				if(potion.isInstant()) flag += 1;
				int id = Potion.getIdFromPotion(potion);
				switch(flag) {
					case 0:
						buff_ids.add(id);
						break;
					case 1:
						buff_instant_ids.add(id);
						break;
					case 2:
						debuff_ids.add(id);
						break;
					case 3:
						debuff_instant_ids.add(id);
						break;
				}
				SoManyEnchantments.LOGGER.debug("PotionLister accepting potion : " + potionName);
			}
			else {
				//Potion was skipped
				SoManyEnchantments.LOGGER.debug("PotionLister skipping potion : " + potionName);
			}
		}
		SoManyEnchantments.LOGGER.debug("PotionLister Instant Debuffs: " + debuff_instant_ids.size());
		SoManyEnchantments.LOGGER.debug("PotionLister Debuffs: " + debuff_ids.size());
	}
}
