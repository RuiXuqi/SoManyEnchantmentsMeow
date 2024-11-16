package com.shultrea.rin.registry;

import com.shultrea.rin.Config.RarityConfig;
import com.shultrea.rin.Enum.EnumSlots;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.enchantments.*;
import com.shultrea.rin.enchantments.armor.protection.*;
import com.shultrea.rin.enchantments.armor.thorns.EnchantmentAdvancedThorns;
import com.shultrea.rin.enchantments.bow.*;
import com.shultrea.rin.enchantments.fishing.EnchantmentAdvancedLuckOfTheSea;
import com.shultrea.rin.enchantments.fishing.EnchantmentAdvancedLure;
import com.shultrea.rin.enchantments.tool.EnchantmentAdvancedEfficiency;
import com.shultrea.rin.enchantments.tool.EnchantmentQuarrying;
import com.shultrea.rin.enchantments.tool.EnchantmentSmelter;
import com.shultrea.rin.enchantments.weapon.*;
import com.shultrea.rin.enchantments.weapon.ancient.EnchantmentAncientSealedCurses;
import com.shultrea.rin.enchantments.weapon.ancient.EnchantmentAncientSwordMastery;
import com.shultrea.rin.enchantments.armor.*;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.weapon.conditionaldamage.EnchantmentDarkShadows;
import com.shultrea.rin.enchantments.weapon.conditionaldamage.EnchantmentMortalitas;
import com.shultrea.rin.enchantments.weapon.conditionaldamage.EnchantmentViper;
import com.shultrea.rin.enchantments.curses.*;
import com.shultrea.rin.enchantments.weapon.damage.*;
import com.shultrea.rin.enchantments.weapon.damagemultiplier.*;
import com.shultrea.rin.enchantments.hoe.EnchantmentJaggedRake;
import com.shultrea.rin.enchantments.hoe.EnchantmentMoisturized;
import com.shultrea.rin.enchantments.hoe.EnchantmentPlowing;
import com.shultrea.rin.enchantments.weapon.potiondebuffer.*;
import com.shultrea.rin.enchantments.rune.*;
import com.shultrea.rin.enchantments.shield.EnchantmentBurningShield;
import com.shultrea.rin.enchantments.shield.EnchantmentEmpoweredDefence;
import com.shultrea.rin.enchantments.shield.EnchantmentNaturalBlocking;
import com.shultrea.rin.enchantments.armor.thorns.EnchantmentBurningThorns;
import com.shultrea.rin.enchantments.weapon.subject.EnchantmentSubjectEnchantments;
import com.shultrea.rin.enchantments.weapon.weather.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashSet;
import java.util.Set;

public class EnchantmentRegistry {

	private static final Set<EnchantmentBase> enchantmentSet = new LinkedHashSet<>();

	public static EnchantmentBase adept = register(new EnchantmentAdept("adept", RarityConfig.get(ModConfig.rarity.adept), EnumSlots.MAIN));
	public static EnchantmentBase ancientSealedCurses = register(new EnchantmentAncientSealedCurses("ancientsealedcurses", RarityConfig.get(ModConfig.rarity.ancientSealedCurses), EnumSlots.MAIN));
	public static EnchantmentBase ancientSwordMastery = register(new EnchantmentAncientSwordMastery("ancientswordmastery", RarityConfig.get(ModConfig.rarity.ancientSwordMastery), EnumSlots.MAIN));
	public static EnchantmentBase arcSlash = register(new EnchantmentArcSlash("arcslash", RarityConfig.get(ModConfig.rarity.arcSlash), EnumSlots.MAIN));
	public static EnchantmentBase ashDestroyer = register(new EnchantmentAshDestroyer("ashdestroyer", RarityConfig.get(ModConfig.rarity.ashDestroyer), EnumSlots.MAIN));
	public static EnchantmentBase atomicDeconstructor = register(new EnchantmentAtomicDeconstructor("atomicdeconstructor", RarityConfig.get(ModConfig.rarity.atomicDeconstructor), EnumSlots.MAIN));
	//public static EnchantmentBase biomePresence;
	public static EnchantmentBase blessedEdge = register(new EnchantmentBlessedEdge("blessededge", RarityConfig.get(ModConfig.rarity.blessedEdge), EnumSlots.MAIN));
	public static EnchantmentBase brutality = register(new EnchantmentBrutality("brutality", RarityConfig.get(ModConfig.rarity.brutality), EnumSlots.MAIN));
	public static EnchantmentBase burningShield = register(new EnchantmentBurningShield("burningshield", RarityConfig.get(ModConfig.rarity.burningShield), EnumSlots.HANDS));
	public static EnchantmentBase burningThorns = register(new EnchantmentBurningThorns("burningthorns", RarityConfig.get(ModConfig.rarity.burningThorns), EnumSlots.BODY));
	public static EnchantmentBase butchering = register(new EnchantmentButchering("butchering", RarityConfig.get(ModConfig.rarity.butchering), EnumSlots.MAIN));
	public static EnchantmentBase clearskiesFavor = register(new EnchantmentClearskiesFavor("clearskiesfavor", RarityConfig.get(ModConfig.rarity.clearskiesFavor), EnumSlots.MAIN));
	public static EnchantmentBase combatVeterancy = register(new EnchantmentCombatVeterancy("combatveterancy", RarityConfig.get(ModConfig.rarity.combatVeterancy), EnumSlots.MAIN));
	public static EnchantmentBase counterAttack = register(new EnchantmentCounterAttack("counterattack", RarityConfig.get(ModConfig.rarity.counterAttack), EnumSlots.MAIN));
	public static EnchantmentBase criticalStrike = register(new EnchantmentCriticalStrike("criticalstrike", RarityConfig.get(ModConfig.rarity.criticalStrike), EnumSlots.MAIN));
	public static EnchantmentBase culling = register(new EnchantmentCulling("culling", RarityConfig.get(ModConfig.rarity.culling), EnumSlots.MAIN));
	public static EnchantmentBase darkShadows = register(new EnchantmentDarkShadows("darkshadows", RarityConfig.get(ModConfig.rarity.darkShadows), EnumSlots.MAIN));
	//public static EnchantmentBase debug;
	public static EnchantmentBase defusingEdge = register(new EnchantmentDefusingEdge("defusingedge", RarityConfig.get(ModConfig.rarity.defusingEdge), EnumSlots.MAIN));
	public static EnchantmentBase desolator = register(new EnchantmentDesolator("desolator", RarityConfig.get(ModConfig.rarity.desolator), EnumSlots.MAIN));
	public static EnchantmentBase difficultysEndowment = register(new EnchantmentDifficultysEndowment("difficultysendowment", RarityConfig.get(ModConfig.rarity.difficultysEndowment), EnumSlots.MAIN));
	public static EnchantmentBase disarmament = register(new EnchantmentDisarmament("disarmament", RarityConfig.get(ModConfig.rarity.disarmament), EnumSlots.MAIN));
	public static EnchantmentBase disorientatingBlade = register(new EnchantmentDisorientatingBlade("disorientatingblade", RarityConfig.get(ModConfig.rarity.disorientatingBlade), EnumSlots.MAIN));
	public static EnchantmentBase dragging = register(new EnchantmentDragging("dragging", RarityConfig.get(ModConfig.rarity.dragging), EnumSlots.HANDS));
	public static EnchantmentBase empoweredDefence = register(new EnchantmentEmpoweredDefence("empowereddefence", RarityConfig.get(ModConfig.rarity.empoweredDefence), EnumSlots.HANDS));
	public static EnchantmentBase envenomed = register(new EnchantmentEnvenomed("envenomed", RarityConfig.get(ModConfig.rarity.envenomed), EnumSlots.MAIN));
	public static EnchantmentBase evasion = register(new EnchantmentEvasion("evasion", RarityConfig.get(ModConfig.rarity.evasion), EnumSlots.LEGS));
	public static EnchantmentBase fieryEdge = register(new EnchantmentFieryEdge("fieryedge", RarityConfig.get(ModConfig.rarity.fieryEdge), EnumSlots.MAIN));
	public static EnchantmentBase flinging = register(new EnchantmentFlinging("flinging", RarityConfig.get(ModConfig.rarity.flinging), EnumSlots.MAIN));
	public static EnchantmentBase freezing = register(new EnchantmentFreezing("freezing", RarityConfig.get(ModConfig.rarity.freezing), EnumSlots.MAIN));
	public static EnchantmentBase horsDeCombat = register(new EnchantmentHorsDeCombat("horsdecombat", RarityConfig.get(ModConfig.rarity.horsDeCombat), EnumSlots.MAIN));
	public static EnchantmentBase inhumane = register(new EnchantmentInhumane("inhumane", RarityConfig.get(ModConfig.rarity.inhumane), EnumSlots.MAIN));
	public static EnchantmentBase innerBerserk = register(new EnchantmentInnerBerserk("innerberserk", RarityConfig.get(ModConfig.rarity.innerBerserk), EnumSlots.CHEST));
	public static EnchantmentBase jaggedRake = register(new EnchantmentJaggedRake("jaggedrake", RarityConfig.get(ModConfig.rarity.jaggedRake), EnumSlots.MAIN));
	public static EnchantmentBase levitator = register(new EnchantmentLevitator("levitator", RarityConfig.get(ModConfig.rarity.levitator), EnumSlots.MAIN));
	public static EnchantmentBase lifesteal = register(new EnchantmentLifesteal("lifesteal", RarityConfig.get(ModConfig.rarity.lifesteal), EnumSlots.MAIN));
	public static EnchantmentBase lightWeight = register(new EnchantmentLightWeight("lightweight", RarityConfig.get(ModConfig.rarity.lightWeight), EnumSlots.FEET));
	public static EnchantmentBase luckMagnification = register(new EnchantmentLuckMagnification("luckmagnification", RarityConfig.get(ModConfig.rarity.luckMagnification), EnumSlots.MAIN));
	public static EnchantmentBase lunarsBlessing = register(new EnchantmentLunarsBlessing("lunarsblessing", RarityConfig.get(ModConfig.rarity.lunarsBlessing), EnumSlots.MAIN));
	public static EnchantmentBase magicProtection = register(new EnchantmentMagicProtection("magicprotection", RarityConfig.get(ModConfig.rarity.magicProtection), EnumSlots.BODY));
	public static EnchantmentBase magmaWalker = register(new EnchantmentMagmaWalker("magmawalker", RarityConfig.get(ModConfig.rarity.magmaWalker), EnumSlots.FEET));
	public static EnchantmentBase meltdown = register(new EnchantmentMeltdown("meltdown", RarityConfig.get(ModConfig.rarity.meltdown), EnumSlots.CHEST));
	public static EnchantmentBase moisturized = register(new EnchantmentMoisturized("moisturized", RarityConfig.get(ModConfig.rarity.moisturized), EnumSlots.MAIN));
	//public static EnchantmentBase multiFisher;
	public static EnchantmentBase mortalitas = register(new EnchantmentMortalitas("mortalitas", RarityConfig.get(ModConfig.rarity.mortalitas), EnumSlots.MAIN));
	public static EnchantmentBase naturalBlocking = register(new EnchantmentNaturalBlocking("naturalblocking", RarityConfig.get(ModConfig.rarity.naturalBlocking), EnumSlots.HANDS));
	public static EnchantmentBase parry = register(new EnchantmentParry("parry", RarityConfig.get(ModConfig.rarity.parry), EnumSlots.MAIN));
	public static EnchantmentBase penetratingEdge = register(new EnchantmentPenetratingEdge("penetratingedge", RarityConfig.get(ModConfig.rarity.penetratingEdge), EnumSlots.MAIN));
	public static EnchantmentBase physicalProtection = register(new EnchantmentPhysicalProtection("physicalprotection", RarityConfig.get(ModConfig.rarity.physicalProtection), EnumSlots.BODY));
	public static EnchantmentBase purgingBlade = register(new EnchantmentPurgingBlade("purgingblade", RarityConfig.get(ModConfig.rarity.purgingBlade), EnumSlots.MAIN));
	public static EnchantmentBase purification = register(new EnchantmentPurification("purification", RarityConfig.get(ModConfig.rarity.purification), EnumSlots.MAIN));
	public static EnchantmentBase pushing = register(new EnchantmentPushing("pushing", RarityConfig.get(ModConfig.rarity.pushing), EnumSlots.MAIN));
	public static EnchantmentBase quarrying = register(new EnchantmentQuarrying("quarrying", RarityConfig.get(ModConfig.rarity.quarrying), EnumSlots.MAIN));
	public static EnchantmentBase rainsBestowment = register(new EnchantmentRainsBestowment("rainsbestowment", RarityConfig.get(ModConfig.rarity.rainsBestowment), EnumSlots.MAIN));
	public static EnchantmentBase reviledBlade = register(new EnchantmentReviledBlade("reviledblade", RarityConfig.get(ModConfig.rarity.reviledBlade), EnumSlots.MAIN));
	public static EnchantmentBase reinforcedsharpness = register(new EnchantmentReinforcedSharpness("reinforcedsharpness", RarityConfig.get(ModConfig.rarity.reinforcedsharpness), EnumSlots.MAIN));
	public static EnchantmentBase smelter = register(new EnchantmentSmelter("smelter", RarityConfig.get(ModConfig.rarity.smelter), EnumSlots.MAIN));
	public static EnchantmentBase solsBlessing = register(new EnchantmentSolsBlessing("solsblessing", RarityConfig.get(ModConfig.rarity.solsBlessing), EnumSlots.MAIN));
	public static EnchantmentBase spellBreaker = register(new EnchantmentSpellBreaker("spellbreaker", RarityConfig.get(ModConfig.rarity.spellBreaker), EnumSlots.MAIN));
	public static EnchantmentBase splitShot = register(new EnchantmentSplitshot("splitshot", RarityConfig.get(ModConfig.rarity.splitShot), EnumSlots.HANDS));
	public static EnchantmentBase strafe = register(new EnchantmentStrafe("strafe", RarityConfig.get(ModConfig.rarity.strafe), EnumSlots.HANDS));
	public static EnchantmentBase strengthenedVitality = register(new EnchantmentStrengthenedVitality("strengthenedvitality", RarityConfig.get(ModConfig.rarity.strengthenedVitality), EnumSlots.CHEST));
	public static EnchantmentBase swifterSlashes = register(new EnchantmentSwifterSlashes("swifterslashes", RarityConfig.get(ModConfig.rarity.swifterSlashes), EnumSlots.MAIN));
	public static EnchantmentBase thunderstormsBestowment = register(new EnchantmentThunderstormsBestowment("thunderstormsbestowment", RarityConfig.get(ModConfig.rarity.thunderstormsBestowment), EnumSlots.MAIN));
	public static EnchantmentBase trueStrike = register(new EnchantmentTrueStrike("truestrike", RarityConfig.get(ModConfig.rarity.trueStrike), EnumSlots.MAIN));
	public static EnchantmentBase underwaterStrider = register(new EnchantmentUnderwaterStrider("underwaterstrider", RarityConfig.get(ModConfig.rarity.underwaterStrider), EnumSlots.FEET));
	public static EnchantmentBase unreasonable = register(new EnchantmentUnreasonable("unreasonable", RarityConfig.get(ModConfig.rarity.unreasonable), EnumSlots.MAIN));
	public static EnchantmentBase unsheathing = register(new EnchantmentUnsheathing("unsheathing", RarityConfig.get(ModConfig.rarity.unsheathing), EnumSlots.MAIN));
	public static EnchantmentBase upgradedPotentials = register(new EnchantmentUpgradedPotentials("upgradedpotentials", RarityConfig.get(ModConfig.rarity.upgradedPotentials), EnumSlots.MAIN));
	public static EnchantmentBase viper = register(new EnchantmentViper("viper", RarityConfig.get(ModConfig.rarity.viper), EnumSlots.MAIN));
	public static EnchantmentBase waterAspect = register(new EnchantmentWaterAspect("wateraspect", RarityConfig.get(ModConfig.rarity.waterAspect), EnumSlots.MAIN));
	public static EnchantmentBase plowing = register(new EnchantmentPlowing("plowing", RarityConfig.get(ModConfig.rarity.plowing), EnumSlots.MAIN));
	public static EnchantmentBase wintersGrace = register(new EnchantmentWintersGrace("wintersgrace", RarityConfig.get(ModConfig.rarity.wintersGrace), EnumSlots.MAIN));

	//Curses
	public static EnchantmentBase bluntness = register(new EnchantmentBluntness("bluntness", RarityConfig.get(ModConfig.rarity.bluntness), EnumSlots.HANDS));
	public static EnchantmentBase cursedEdge = register(new EnchantmentCursedEdge("cursededge", RarityConfig.get(ModConfig.rarity.cursedEdge), EnumSlots.HANDS));
	public static EnchantmentBase curseOfDecay = register(new EnchantmentCurseofDecay("curseofdecay", RarityConfig.get(ModConfig.rarity.curseOfDecay), EnumSlots.HANDS));
	public static EnchantmentBase curseOfHolding = register(new EnchantmentCurseofHolding("curseofholding", RarityConfig.get(ModConfig.rarity.curseOfHolding), EnumSlots.HANDS));
	public static EnchantmentBase curseOfInaccuracy = register(new EnchantmentCurseofInaccuracy("curseofinaccuracy", RarityConfig.get(ModConfig.rarity.curseOfInaccuracy), EnumSlots.HANDS));
	public static EnchantmentBase curseOfPossession = register(new EnchantmentCurseofPossession("curseofpossession", RarityConfig.get(ModConfig.rarity.curseOfPossession), EnumSlots.ALL));
	public static EnchantmentBase curseOfVulnerability = register(new EnchantmentCurseofVulnerability("curseofvulnerability", RarityConfig.get(ModConfig.rarity.curseOfVulnerability), EnumSlots.BODY));
	public static EnchantmentBase heavyWeight = register(new EnchantmentHeavyWeight("heavyweight", RarityConfig.get(ModConfig.rarity.heavyWeight), EnumSlots.MAIN));
	public static EnchantmentBase inefficient = register(new EnchantmentInefficient("inefficient", RarityConfig.get(ModConfig.rarity.inefficient), EnumSlots.MAIN));
	public static EnchantmentBase instability = register(new EnchantmentInstability("instability", RarityConfig.get(ModConfig.rarity.instability), EnumSlots.MAIN));
	public static EnchantmentBase pandorasCurse = register(new EnchantmentPandorasCurse("pandorascurse", RarityConfig.get(ModConfig.rarity.pandorasCurse), EnumSlots.ALL));
	public static EnchantmentBase powerless = register(new EnchantmentPowerless("powerless", RarityConfig.get(ModConfig.rarity.powerless), EnumSlots.HANDS));
	public static EnchantmentBase rusted = register(new EnchantmentRusted("rusted", RarityConfig.get(ModConfig.rarity.rusted), EnumSlots.HANDS));
	public static EnchantmentBase unpredictable = register(new EnchantmentUnpredictable("unpredictable", RarityConfig.get(ModConfig.rarity.unpredictable), EnumSlots.HANDS));

	//Rune
	public static EnchantmentBase runeArrowPiercing = register(new EnchantmentRuneArrowPiercing("rune_arrowpiercing", RarityConfig.get(ModConfig.rarity.runeArrowPiercing), EnumSlots.HANDS));
	public static EnchantmentBase runeMagicalBlessing = register(new EnchantmentRuneMagicalBlessing("rune_magicalblessing", RarityConfig.get(ModConfig.rarity.runeMagicalBlessing), EnumSlots.MAIN));
	public static EnchantmentBase runePiercingCapabilities = register(new EnchantmentRunePiercingCapabilities("rune_piercingcapabilities", RarityConfig.get(ModConfig.rarity.runePiercingCapabilities), EnumSlots.MAIN));
	public static EnchantmentBase runeResurrection = register(new EnchantmentRuneResurrection("rune_resurrection", RarityConfig.get(ModConfig.rarity.runeResurrection), EnumSlots.HANDS));
	public static EnchantmentBase runeRevival = register(new EnchantmentRuneRevival("rune_revival", RarityConfig.get(ModConfig.rarity.runeRevival), EnumSlots.HANDS));
	//public static EnchantmentBase runeStarfall;

	//Subject
	public static EnchantmentBase subjectMathematics = register(new EnchantmentSubjectEnchantments("subjectmathematics", RarityConfig.get(ModConfig.rarity.subjectMathematics),0));
	public static EnchantmentBase subjectScience = register(new EnchantmentSubjectEnchantments("subjectscience", RarityConfig.get(ModConfig.rarity.subjectScience),1));
	public static EnchantmentBase subjectHistory = register(new EnchantmentSubjectEnchantments("subjecthistory", RarityConfig.get(ModConfig.rarity.subjectHistory),2));
	//public static Enchantment subjectPhysics = register(new EnchantmentSubjectEnchantments("subjectphysics", RarityConfig.get(ModConfig.rarity.subjectPhysics),3));
	public static EnchantmentBase subjectEnglish = register(new EnchantmentSubjectEnchantments("subjectenglish", RarityConfig.get(ModConfig.rarity.subjectEnglish),4));
	public static EnchantmentBase subjectPE = register(new EnchantmentSubjectEnchantments("subjectpe", RarityConfig.get(ModConfig.rarity.subjectPE),5));

	//Lesser
	public static EnchantmentBase lesserBaneOfArthropods = register(new EnchantmentTierDamage("lesserbaneofarthropods", RarityConfig.get(ModConfig.rarity.lesserBaneOfArthropods),4));
	//public static EnchantmentBase lesserBlastProtection;
	//public static EnchantmentBase lesserEfficiency;
	//public static EnchantmentBase lesserFeatherFalling;
	public static EnchantmentBase lesserFireAspect = register(new EnchantmentTierFA("lesserfireaspect", RarityConfig.get(ModConfig.rarity.lesserFireAspect),0));
	//public static EnchantmentBase lesserFireProtection;
	public static EnchantmentBase lesserFlame = register(new EnchantmentTierFlame("lesserflame", RarityConfig.get(ModConfig.rarity.lesserFlame),0));
	//public static EnchantmentBase lesserFortune;
	//public static EnchantmentBase lesserKnockback;
	//public static EnchantmentBase lesserLooting;
	//public static EnchantmentBase lesserLuckOfTheSea;
	//public static EnchantmentBase lesserLure;
	//public static EnchantmentBase lesserMending;
	//public static EnchantmentBase lesserPower;
	//public static EnchantmentBase lesserProjectileProtection;
	//public static EnchantmentBase lesserProtection;
	//public static EnchantmentBase lesserPunch;
	//public static EnchantmentBase lesserRespiration;
	public static EnchantmentBase lesserSharpness = register(new EnchantmentTierDamage("lessersharpness", RarityConfig.get(ModConfig.rarity.lesserSharpness),0));
	public static EnchantmentBase lesserSmite = register(new EnchantmentTierDamage("lessersmite", RarityConfig.get(ModConfig.rarity.lesserSmite),2));
	//public static EnchantmentBase lesserThorns;

	//Advanced
	public static EnchantmentBase advancedBaneOfArthropods = register(new EnchantmentAdvancedBaneOfArthropods("advancedbaneofarthropods", RarityConfig.get(ModConfig.rarity.advancedBaneOfArthropods), EnumSlots.MAIN));
	public static EnchantmentBase advancedBlastProtection = register(new EnchantmentAdvancedBlastProtection("advancedblastprotection", RarityConfig.get(ModConfig.rarity.advancedBlastProtection), EnumSlots.BODY));
	public static EnchantmentBase advancedEfficiency = register(new EnchantmentAdvancedEfficiency("advancedefficiency", RarityConfig.get(ModConfig.rarity.advancedEfficiency), EnumSlots.MAIN));
	public static EnchantmentBase advancedFeatherFalling = register(new EnchantmentAdvancedFeatherFalling("advancedfeatherfalling", RarityConfig.get(ModConfig.rarity.advancedFeatherFalling), EnumSlots.FEET));
	public static EnchantmentBase advancedFireAspect = register(new EnchantmentTierFA("advancedfireaspect", RarityConfig.get(ModConfig.rarity.advancedFireAspect),1));
	public static EnchantmentBase advancedFireProtection = register(new EnchantmentAdvancedFireProtection("advancedfireprotection", RarityConfig.get(ModConfig.rarity.advancedFireProtection), EnumSlots.BODY));
	public static EnchantmentBase advancedFlame = register(new EnchantmentTierFlame("advancedflame", RarityConfig.get(ModConfig.rarity.advancedFlame),1));
	//public static EnchantmentBase advancedFortune;
	public static EnchantmentBase advancedKnockback = register(new EnchantmentAdvancedKnockback("advancedknockback", RarityConfig.get(ModConfig.rarity.advancedKnockback), EnumSlots.MAIN));
	public static EnchantmentBase advancedLooting = register(new EnchantmentAdvancedLooting("advancedlooting", RarityConfig.get(ModConfig.rarity.advancedLooting), EnumSlots.MAIN));
	public static EnchantmentBase advancedLuckOfTheSea = register(new EnchantmentAdvancedLuckOfTheSea("advancedluckofthesea", RarityConfig.get(ModConfig.rarity.advancedLuckOfTheSea), EnumSlots.HANDS));
	public static EnchantmentBase advancedLure = register(new EnchantmentAdvancedLure("advancedlure", RarityConfig.get(ModConfig.rarity.advancedLure), EnumSlots.HANDS));
	public static EnchantmentBase advancedMending = register(new EnchantmentAdvancedMending("advancedmending", RarityConfig.get(ModConfig.rarity.advancedMending), EnumSlots.ALL));
	public static EnchantmentBase advancedPower = register(new EnchantmentAdvancedPower("advancedpower", RarityConfig.get(ModConfig.rarity.advancedPower), EnumSlots.HANDS));
	public static EnchantmentBase advancedProjectileProtection = register(new EnchantmentAdvancedProjectileProtection("advancedprojectileprotection", RarityConfig.get(ModConfig.rarity.advancedProjectileProtection), EnumSlots.BODY));
	public static EnchantmentBase advancedProtection = register(new EnchantmentAdvancedProtection("advancedprotection", RarityConfig.get(ModConfig.rarity.advancedProtection), EnumSlots.BODY));
	public static EnchantmentBase advancedPunch = register(new EnchantmentAdvancedPunch("advancedpunch", RarityConfig.get(ModConfig.rarity.advancedPunch), EnumSlots.HANDS));
	//public static EnchantmentBase advancedRespiration;
	public static EnchantmentBase advancedSharpness = register(new EnchantmentAdvancedSharpness("advancedsharpness", RarityConfig.get(ModConfig.rarity.advancedSharpness), EnumSlots.MAIN));
	public static EnchantmentBase advancedSmite = register(new EnchantmentAdvancedSmite("advancedsmite", RarityConfig.get(ModConfig.rarity.advancedSmite), EnumSlots.MAIN));
	public static EnchantmentBase advancedThorns = register(new EnchantmentAdvancedThorns("advancedthorns", RarityConfig.get(ModConfig.rarity.advancedThorns), EnumSlots.BODY));
	//Supreme
	public static EnchantmentBase supremeBaneOfArthropods = register(new EnchantmentTierDamage("supremebaneofarthropods", RarityConfig.get(ModConfig.rarity.supremeBaneOfArthropods),5));
	//public static EnchantmentBase supremeBlastProtection;
	//public static EnchantmentBase supremeEfficiency;
	//public static EnchantmentBase supremeFeatherFalling;
	public static EnchantmentBase supremeFireAspect = register(new EnchantmentTierFA("supremefireaspect", RarityConfig.get(ModConfig.rarity.supremeFireAspect),2));
	//public static EnchantmentBase supremeFireProtection;
	public static EnchantmentBase supremeFlame = register(new EnchantmentTierFlame("supremeflame", RarityConfig.get(ModConfig.rarity.supremeFlame),2));
	//public static EnchantmentBase supremeFortune;
	//public static EnchantmentBase supremeKnockback;
	//public static EnchantmentBase supremeLooting;
	//public static EnchantmentBase supremeLuckOfTheSea;
	//public static EnchantmentBase supremeLure;
	//public static EnchantmentBase supremeMending;
	//public static EnchantmentBase supremePower;
	//public static EnchantmentBase supremeProjectileProtection;
	//public static EnchantmentBase supremeProtection;
	//public static EnchantmentBase supremePunch;
	//public static EnchantmentBase supremeRespiration;
	public static EnchantmentBase supremeSharpness = register(new EnchantmentTierDamage("supremesharpness", RarityConfig.get(ModConfig.rarity.supremeSharpness),1));
	public static EnchantmentBase supremeSmite = register(new EnchantmentTierDamage("supremesmite", RarityConfig.get(ModConfig.rarity.supremeSmite),3));
    //public static EnchantmentBase supremeThorns;

	private static EnchantmentBase register(EnchantmentBase enchantment) {
		if(!ModConfig.miscellaneous.dontRegisterDisabledEnchants || enchantment.isEnabled()) {
			enchantmentSet.add(enchantment);
		}
		return enchantment;
	}

	public static void handleSubscribers() {
		for(EnchantmentBase enchantment : enchantmentSet) {
			if(enchantment.isEnabled() && enchantment.hasSubscriber()) MinecraftForge.EVENT_BUS.register(enchantment);
		}
	}

	public static void initIncompatLists(){
		for(EnchantmentBase e: enchantmentSet)
			e.incompatibleEnchantments = ModConfig.incompatible.getIncompatibleEnchantmentsString(e.getRegistryName());
	}

	@Mod.EventBusSubscriber(modid = SoManyEnchantments.MODID)
	public static class EventSubscriber {

		@SubscribeEvent
		public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
			IForgeRegistry<Enchantment> registry = event.getRegistry();
			for(EnchantmentBase enchant : enchantmentSet) {
				registry.register(enchant);
			}
		}
	}
}