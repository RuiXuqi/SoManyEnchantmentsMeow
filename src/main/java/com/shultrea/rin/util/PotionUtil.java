package com.shultrea.rin.util;

import com.shultrea.rin.config.ModConfig;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class PotionUtil {
	
	public static final Random RANDOM = new Random();
	
	public static final List<Potion> BUFF_INSTANT = new ArrayList<>();
	public static final List<Potion> DEBUFF_INSTANT = new ArrayList<>();
	public static final List<Potion> BUFF_NONINSTANT = new ArrayList<>();
	public static final List<Potion> DEBUFF_NONINSTANT = new ArrayList<>();
	
	@Nullable
	public static Potion getNonInstantNegativePotion() {
		if(DEBUFF_NONINSTANT.isEmpty()) return null;
		int index = RANDOM.nextInt(DEBUFF_NONINSTANT.size());
		return DEBUFF_NONINSTANT.get(index);
	}
	
	@Nullable
	public static Potion getInstantNegativePotion() {
		if(DEBUFF_INSTANT.isEmpty()) return null;
		int index = RANDOM.nextInt(DEBUFF_INSTANT.size());
		return DEBUFF_INSTANT.get(index);
	}
	
	@Nullable
	public static Potion getNonInstantPositivePotion() {
		if(BUFF_NONINSTANT.isEmpty()) return null;
		int index = RANDOM.nextInt(BUFF_NONINSTANT.size());
		return BUFF_NONINSTANT.get(index);
	}
	
	@Nullable
	public static Potion getInstantPositivePotion() {
		if(BUFF_INSTANT.isEmpty()) return null;
		int index = RANDOM.nextInt(BUFF_INSTANT.size());
		return BUFF_INSTANT.get(index);
	}
	
	public static void initializePotionLists() {
		List<ResourceLocation> potionBlacklist = new ArrayList<>();

		for(String str : ModConfig.miscellaneous.potionBlacklist) {
			str = str.trim();
			if(!str.isEmpty()) {
				ResourceLocation loc = new ResourceLocation(str);
				potionBlacklist.add(loc);
			}
		}

		for(Potion potion : Potion.REGISTRY) {
			ResourceLocation potionResource = potion.getRegistryName();
			boolean listMatch = potionBlacklist.contains(potionResource);

			if(listMatch == ModConfig.miscellaneous.potionBlacklistAsWhitelist) {
				if(potion.isBadEffect()) {
					if(potion.isInstant()) DEBUFF_INSTANT.add(potion);
					else DEBUFF_NONINSTANT.add(potion);
				}
				else {
					if(potion.isInstant()) BUFF_INSTANT.add(potion);
					else BUFF_NONINSTANT.add(potion);
				}
			}
		}
	}
}