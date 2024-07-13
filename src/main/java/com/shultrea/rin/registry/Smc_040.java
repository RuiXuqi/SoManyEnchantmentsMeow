package com.shultrea.rin.registry;

import com.shultrea.rin.Enum.EnumList;
import com.shultrea.rin.enchantments.*;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.curses.*;
import com.shultrea.rin.enchantments.rune.EnchantmentRuneArrowPiercing;
import com.shultrea.rin.enchantments.rune.EnchantmentRuneResurrection;
import com.shultrea.rin.enchantments.subject.EnchantmentSubjectEnchantments;
import com.shultrea.rin.enchantments.tier.EnchantmentAdvancedMending;
import com.shultrea.rin.enchantments.tier.EnchantmentTierDamage;
import com.shultrea.rin.enchantments.tier.EnchantmentTierFA;
import com.shultrea.rin.enchantments.tier.EnchantmentTierFlame;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class Smc_040 {
	
	public static void init() {
		Mathematics = registerAs(new EnchantmentSubjectEnchantments(Rarity.RARE, EnumList.SWORD, 0, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("Mathematics").setRegistryName("Mathematics"));
		Science = registerAs(new EnchantmentSubjectEnchantments(Rarity.RARE, EnumList.SWORD, 1, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("Science").setRegistryName("Science"));
		History = registerAs(new EnchantmentSubjectEnchantments(Rarity.RARE, EnumList.SWORD, 2, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("History").setRegistryName("History"));
		//Physics =     new EnchantmentSubjectEnchantments(Rarity.VERY_RARE, 3, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}).setName("Physics").setRegistryName("Physics");
		English = registerAs(new EnchantmentSubjectEnchantments(Rarity.RARE, EnumList.SWORD, 4, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("English").setRegistryName("English"));
		PE = registerAs(new EnchantmentSubjectEnchantments(Rarity.RARE, EnumList.SWORD, 5, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("PE").setRegistryName("PE"));
		AtomicDeconstructor = registerAs(new EnchantmentAtomicDeconstructor());
		Disarmament = registerAs(new EnchantmentDisarmament());
		Hors_de_combat = registerAs(new EnchantmentHorsDeCombat());
		SupremeSharpness = registerAs(new EnchantmentTierDamage(Rarity.VERY_RARE, EnumList.COMBAT, 1, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("SupremeSharpness").setRegistryName("SupremeSharpness"));
		LesserSharpness = registerAs(new EnchantmentTierDamage(Rarity.COMMON, EnumList.COMBAT, 0, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("LesserSharpness").setRegistryName("LesserSharpness"));
		LesserBaneOfArthropods = registerAs(new EnchantmentTierDamage(Rarity.COMMON, EnumList.COMBAT, 4, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("LesserBaneOfArthropods").setRegistryName("LesserBaneOfArthropods"));
		SupremeBaneOfArthropods = registerAs(new EnchantmentTierDamage(Rarity.VERY_RARE, EnumList.COMBAT, 5, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("SupremeBaneOfArthropods").setRegistryName("SupremeBaneOfArthropods"));
		LesserSmite = registerAs(new EnchantmentTierDamage(Rarity.COMMON, EnumList.COMBAT, 2, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("LesserSmite").setRegistryName("LesserSmite"));
		SupremeSmite = registerAs(new EnchantmentTierDamage(Rarity.VERY_RARE, EnumList.COMBAT, 3, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("SupremeSmite").setRegistryName("SupremeSmite"));
		//AdvancedRespiration        = new EnchantmentAdvancedRespiration();
		CurseofPossession = registerAs(new EnchantmentCurseofPossession());
		SupremeFireAspect = registerAs(new EnchantmentTierFA(Rarity.VERY_RARE, EnumList.SWORD, 2, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("sfa").setRegistryName("sfa"));
		AdvancedFireAspect = registerAs(new EnchantmentTierFA(Rarity.RARE, EnumList.SWORD, 1, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("afa").setRegistryName("afa"));
		LesserFireAspect = registerAs(new EnchantmentTierFA(Rarity.COMMON, EnumList.SWORD, 0, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND}).setName("lfa").setRegistryName("lfa"));
		Swiper = registerAs(new EnchantmentArcSlash());
		freezing = registerAs(new EnchantmentFreezing());
		advancedmending = registerAs(new EnchantmentAdvancedMending());
		lesserflame = registerAs(new EnchantmentTierFlame(Rarity.COMMON, EnumEnchantmentType.BOW, 0, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND}).setName("lfl").setRegistryName("lfl"));
		advancedflame = registerAs(new EnchantmentTierFlame(Rarity.VERY_RARE, EnumEnchantmentType.BOW, 1, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND}).setName("afl").setRegistryName("afl"));
		supremeflame = registerAs(new EnchantmentTierFlame(Rarity.VERY_RARE, EnumEnchantmentType.BOW, 2, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND}).setName("sfl").setRegistryName("sfl"));
		splitshot = registerAs(new EnchantmentSplitshot());
		sharperedge = registerAs(new EnchantmentSharperEdge());
		Rune_Resurrection = registerAs(new EnchantmentRuneResurrection());
		flinging = registerAs(new EnchantmentFlinging());
		//rune_starfall               = registerAs(new EnchantmentRune_StarFall());
		strengthenedvitality = registerAs(new EnchantmentStrengthenedVitality());
		difficultyscaled = registerAs(new EnchantmentDifficultysEndowment());
		meltdown = registerAs(new EnchantmentMeltdown());
		welltilled = registerAs(new EnchantmentMoisturized());
		upgrade = registerAs(new EnchantmentUpgradedPotentials());
		scythedamage = registerAs(new EnchantmentJaggedRake());
		Adept = registerAs(new EnchantmentAdept());
		CurseofDecay = registerAs(new EnchantmentCurseofDecay());
		//PierceThrough 				= new EnchantmentPierceThrough();
		Brutality = registerAs(new EnchantmentBrutality());
		MagmaWalker = registerAs(new EnchantmentMagmaWalker());
		Inhumane = registerAs(new EnchantmentInhumane());
		fieryshield = registerAs(new EnchantmentBurningShield());
		NaturalBlocking = registerAs(new EnchantmentNaturalBlocking());
		DarkShadows = registerAs(new EnchantmentDarkShadows());
		CurseOfInaccuracy = registerAs(new EnchantmentCurseofInaccuracy());
		Rune_PiercingArrows = registerAs(new EnchantmentRuneArrowPiercing());
		InnerBerserk = registerAs(new EnchantmentInnerBerserk());
		AncientCurseInflicter = registerAs(new EnchantmentAncientSealedCurses());
		TillingPower = registerAs(new EnchantmentWellTilled());
		CurseofHolding = registerAs(new EnchantmentCurseofHolding());
		CurseofVulnerability = registerAs(new EnchantmentCurseofVulnerability());
		LuckMagnification = registerAs(new EnchantmentLuckMagnification());
		LightWeight = registerAs(new EnchantmentLightWeight());
		//Multifisher					= new EnchantmentMultifisher();
		UnderwaterStrider = registerAs(new EnchantmentUnderwaterStrider());
		Frenzy = registerAs(new EnchantmentUnreasonable());
		Pushing = registerAs(new EnchantmentPushing());
		Pulling = registerAs(new EnchantmentDrag());
		Evasion = registerAs(new EnchantmentEvasion());
		Instability = registerAs(new EnchantmentInstability());
		Unsheathing = registerAs(new EnchantmentUnsheathing());
		EnchantmentAncientSwordMastery = registerAs(new EnchantmentAncientSwordMastery());
		TrueStrike = registerAs(new EnchantmentTrueStrike());
		Pandora = registerAs(new EnchantmentPandorasCurse());
	}
	
	private static EnchantmentBase registerAs(Enchantment enchant) {
		return OrderedRegistry.registerAs(enchant);
	}
}