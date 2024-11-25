package com.shultrea.rin.registry;

import com.shultrea.rin.config.RarityConfig;
import com.shultrea.rin.enchantments.armor.thorns.EnchantmentMeltdown;
import com.shultrea.rin.util.Slots;
import com.shultrea.rin.config.ModConfig;
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

	public static EnchantmentBase adept = register(new EnchantmentAdept("adept", RarityConfig.get(ModConfig.rarity.adept), Slots.MAINHAND));
	public static EnchantmentBase ancientSealedCurses = register(new EnchantmentAncientSealedCurses("ancientsealedcurses", RarityConfig.get(ModConfig.rarity.ancientSealedCurses), Slots.MAINHAND));
	public static EnchantmentBase ancientSwordMastery = register(new EnchantmentAncientSwordMastery("ancientswordmastery", RarityConfig.get(ModConfig.rarity.ancientSwordMastery), Slots.MAINHAND));
	public static EnchantmentBase arcSlash = register(new EnchantmentArcSlash("arcslash", RarityConfig.get(ModConfig.rarity.arcSlash), Slots.MAINHAND));
	public static EnchantmentBase ashDestroyer = register(new EnchantmentAshDestroyer("ashdestroyer", RarityConfig.get(ModConfig.rarity.ashDestroyer), Slots.MAINHAND));
	public static EnchantmentBase atomicDeconstructor = register(new EnchantmentAtomicDeconstructor("atomicdeconstructor", RarityConfig.get(ModConfig.rarity.atomicDeconstructor), Slots.MAINHAND));
	//public static EnchantmentBase biomePresence;
	public static EnchantmentBase blessedEdge = register(new EnchantmentBlessedEdge("blessededge", RarityConfig.get(ModConfig.rarity.blessedEdge), Slots.MAINHAND));
	public static EnchantmentBase brutality = register(new EnchantmentBrutality("brutality", RarityConfig.get(ModConfig.rarity.brutality), Slots.MAINHAND));
	public static EnchantmentBase burningShield = register(new EnchantmentBurningShield("burningshield", RarityConfig.get(ModConfig.rarity.burningShield), Slots.HAND));
	public static EnchantmentBase burningThorns = register(new EnchantmentBurningThorns("burningthorns", RarityConfig.get(ModConfig.rarity.burningThorns), Slots.BODY));
	public static EnchantmentBase butchering = register(new EnchantmentButchering("butchering", RarityConfig.get(ModConfig.rarity.butchering), Slots.MAINHAND));
	public static EnchantmentBase clearskiesFavor = register(new EnchantmentClearskiesFavor("clearskiesfavor", RarityConfig.get(ModConfig.rarity.clearskiesFavor), Slots.MAINHAND));
	public static EnchantmentBase combatVeterancy = register(new EnchantmentCombatVeterancy("combatveterancy", RarityConfig.get(ModConfig.rarity.combatVeterancy), Slots.MAINHAND));
	public static EnchantmentBase counterAttack = register(new EnchantmentCounterAttack("counterattack", RarityConfig.get(ModConfig.rarity.counterAttack), Slots.MAINHAND));
	public static EnchantmentBase criticalStrike = register(new EnchantmentCriticalStrike("criticalstrike", RarityConfig.get(ModConfig.rarity.criticalStrike), Slots.MAINHAND));
	public static EnchantmentBase culling = register(new EnchantmentCulling("culling", RarityConfig.get(ModConfig.rarity.culling), Slots.MAINHAND));
	public static EnchantmentBase darkShadows = register(new EnchantmentDarkShadows("darkshadows", RarityConfig.get(ModConfig.rarity.darkShadows), Slots.MAINHAND));
	//public static EnchantmentBase debug;
	public static EnchantmentBase defusingEdge = register(new EnchantmentDefusingEdge("defusingedge", RarityConfig.get(ModConfig.rarity.defusingEdge), Slots.MAINHAND));
	public static EnchantmentBase desolator = register(new EnchantmentDesolator("desolator", RarityConfig.get(ModConfig.rarity.desolator), Slots.MAINHAND));
	public static EnchantmentBase difficultysEndowment = register(new EnchantmentDifficultysEndowment("difficultysendowment", RarityConfig.get(ModConfig.rarity.difficultysEndowment), Slots.MAINHAND));
	public static EnchantmentBase disarmament = register(new EnchantmentDisarmament("disarmament", RarityConfig.get(ModConfig.rarity.disarmament), Slots.MAINHAND));
	public static EnchantmentBase disorientatingBlade = register(new EnchantmentDisorientatingBlade("disorientatingblade", RarityConfig.get(ModConfig.rarity.disorientatingBlade), Slots.MAINHAND));
	public static EnchantmentBase dragging = register(new EnchantmentDragging("dragging", RarityConfig.get(ModConfig.rarity.dragging), Slots.HAND));
	public static EnchantmentBase empoweredDefence = register(new EnchantmentEmpoweredDefence("empowereddefence", RarityConfig.get(ModConfig.rarity.empoweredDefence), Slots.HAND));
	public static EnchantmentBase envenomed = register(new EnchantmentEnvenomed("envenomed", RarityConfig.get(ModConfig.rarity.envenomed), Slots.MAINHAND));
	public static EnchantmentBase evasion = register(new EnchantmentEvasion("evasion", RarityConfig.get(ModConfig.rarity.evasion), Slots.LEGS));
	public static EnchantmentBase fieryEdge = register(new EnchantmentFieryEdge("fieryedge", RarityConfig.get(ModConfig.rarity.fieryEdge), Slots.MAINHAND));
	public static EnchantmentBase flinging = register(new EnchantmentFlinging("flinging", RarityConfig.get(ModConfig.rarity.flinging), Slots.MAINHAND));
	public static EnchantmentBase freezing = register(new EnchantmentFreezing("freezing", RarityConfig.get(ModConfig.rarity.freezing), Slots.MAINHAND));
	public static EnchantmentBase horsDeCombat = register(new EnchantmentHorsDeCombat("horsdecombat", RarityConfig.get(ModConfig.rarity.horsDeCombat), Slots.MAINHAND));
	public static EnchantmentBase inhumane = register(new EnchantmentInhumane("inhumane", RarityConfig.get(ModConfig.rarity.inhumane), Slots.MAINHAND));
	public static EnchantmentBase innerBerserk = register(new EnchantmentInnerBerserk("innerberserk", RarityConfig.get(ModConfig.rarity.innerBerserk), Slots.CHEST));
	public static EnchantmentBase jaggedRake = register(new EnchantmentJaggedRake("jaggedrake", RarityConfig.get(ModConfig.rarity.jaggedRake), Slots.MAINHAND));
	public static EnchantmentBase levitator = register(new EnchantmentLevitator("levitator", RarityConfig.get(ModConfig.rarity.levitator), Slots.MAINHAND));
	public static EnchantmentBase lifesteal = register(new EnchantmentLifesteal("lifesteal", RarityConfig.get(ModConfig.rarity.lifesteal), Slots.MAINHAND));
	public static EnchantmentBase lightWeight = register(new EnchantmentLightWeight("lightweight", RarityConfig.get(ModConfig.rarity.lightWeight), Slots.FEET));
	public static EnchantmentBase luckMagnification = register(new EnchantmentLuckMagnification("luckmagnification", RarityConfig.get(ModConfig.rarity.luckMagnification), Slots.MAINHAND));
	public static EnchantmentBase lunarsBlessing = register(new EnchantmentLunarsBlessing("lunarsblessing", RarityConfig.get(ModConfig.rarity.lunarsBlessing), Slots.MAINHAND));
	public static EnchantmentBase magicProtection = register(new EnchantmentMagicProtection("magicprotection", RarityConfig.get(ModConfig.rarity.magicProtection), Slots.BODY));
	public static EnchantmentBase magmaWalker = register(new EnchantmentMagmaWalker("magmawalker", RarityConfig.get(ModConfig.rarity.magmaWalker), Slots.FEET));
	public static EnchantmentBase meltdown = register(new EnchantmentMeltdown("meltdown", RarityConfig.get(ModConfig.rarity.meltdown), Slots.CHEST));
	public static EnchantmentBase moisturized = register(new EnchantmentMoisturized("moisturized", RarityConfig.get(ModConfig.rarity.moisturized), Slots.MAINHAND));
	//public static EnchantmentBase multiFisher;
	public static EnchantmentBase mortalitas = register(new EnchantmentMortalitas("mortalitas", RarityConfig.get(ModConfig.rarity.mortalitas), Slots.MAINHAND));
	public static EnchantmentBase naturalBlocking = register(new EnchantmentNaturalBlocking("naturalblocking", RarityConfig.get(ModConfig.rarity.naturalBlocking), Slots.HAND));
	public static EnchantmentBase parry = register(new EnchantmentParry("parry", RarityConfig.get(ModConfig.rarity.parry), Slots.MAINHAND));
	public static EnchantmentBase penetratingEdge = register(new EnchantmentPenetratingEdge("penetratingedge", RarityConfig.get(ModConfig.rarity.penetratingEdge), Slots.MAINHAND));
	public static EnchantmentBase physicalProtection = register(new EnchantmentPhysicalProtection("physicalprotection", RarityConfig.get(ModConfig.rarity.physicalProtection), Slots.BODY));
	public static EnchantmentBase purgingBlade = register(new EnchantmentPurgingBlade("purgingblade", RarityConfig.get(ModConfig.rarity.purgingBlade), Slots.MAINHAND));
	public static EnchantmentBase purification = register(new EnchantmentPurification("purification", RarityConfig.get(ModConfig.rarity.purification), Slots.MAINHAND));
	public static EnchantmentBase pushing = register(new EnchantmentPushing("pushing", RarityConfig.get(ModConfig.rarity.pushing), Slots.MAINHAND));
	public static EnchantmentBase quarrying = register(new EnchantmentQuarrying("quarrying", RarityConfig.get(ModConfig.rarity.quarrying), Slots.MAINHAND));
	public static EnchantmentBase rainsBestowment = register(new EnchantmentRainsBestowment("rainsbestowment", RarityConfig.get(ModConfig.rarity.rainsBestowment), Slots.MAINHAND));
	public static EnchantmentBase reviledBlade = register(new EnchantmentReviledBlade("reviledblade", RarityConfig.get(ModConfig.rarity.reviledBlade), Slots.MAINHAND));
	public static EnchantmentBase reinforcedsharpness = register(new EnchantmentReinforcedSharpness("reinforcedsharpness", RarityConfig.get(ModConfig.rarity.reinforcedsharpness), Slots.MAINHAND));
	public static EnchantmentBase smelter = register(new EnchantmentSmelter("smelter", RarityConfig.get(ModConfig.rarity.smelter), Slots.MAINHAND));
	public static EnchantmentBase solsBlessing = register(new EnchantmentSolsBlessing("solsblessing", RarityConfig.get(ModConfig.rarity.solsBlessing), Slots.MAINHAND));
	public static EnchantmentBase spellBreaker = register(new EnchantmentSpellBreaker("spellbreaker", RarityConfig.get(ModConfig.rarity.spellBreaker), Slots.MAINHAND));
	public static EnchantmentBase splitShot = register(new EnchantmentSplitshot("splitshot", RarityConfig.get(ModConfig.rarity.splitShot), Slots.HAND));
	public static EnchantmentBase strafe = register(new EnchantmentStrafe("strafe", RarityConfig.get(ModConfig.rarity.strafe), Slots.HAND));
	public static EnchantmentBase strengthenedVitality = register(new EnchantmentStrengthenedVitality("strengthenedvitality", RarityConfig.get(ModConfig.rarity.strengthenedVitality), Slots.CHEST));
	public static EnchantmentBase swifterSlashes = register(new EnchantmentSwifterSlashes("swifterslashes", RarityConfig.get(ModConfig.rarity.swifterSlashes), Slots.MAINHAND));
	public static EnchantmentBase thunderstormsBestowment = register(new EnchantmentThunderstormsBestowment("thunderstormsbestowment", RarityConfig.get(ModConfig.rarity.thunderstormsBestowment), Slots.MAINHAND));
	public static EnchantmentBase trueStrike = register(new EnchantmentTrueStrike("truestrike", RarityConfig.get(ModConfig.rarity.trueStrike), Slots.MAINHAND));
	public static EnchantmentBase underwaterStrider = register(new EnchantmentUnderwaterStrider("underwaterstrider", RarityConfig.get(ModConfig.rarity.underwaterStrider), Slots.FEET));
	public static EnchantmentBase unreasonable = register(new EnchantmentUnreasonable("unreasonable", RarityConfig.get(ModConfig.rarity.unreasonable), Slots.MAINHAND));
	public static EnchantmentBase unsheathing = register(new EnchantmentUnsheathing("unsheathing", RarityConfig.get(ModConfig.rarity.unsheathing), Slots.MAINHAND));
	public static EnchantmentBase upgradedPotentials = register(new EnchantmentUpgradedPotentials("upgradedpotentials", RarityConfig.get(ModConfig.rarity.upgradedPotentials), Slots.MAINHAND));
	public static EnchantmentBase viper = register(new EnchantmentViper("viper", RarityConfig.get(ModConfig.rarity.viper), Slots.MAINHAND));
	public static EnchantmentBase waterAspect = register(new EnchantmentWaterAspect("wateraspect", RarityConfig.get(ModConfig.rarity.waterAspect), Slots.MAINHAND));
	public static EnchantmentBase plowing = register(new EnchantmentPlowing("plowing", RarityConfig.get(ModConfig.rarity.plowing), Slots.MAINHAND));
	public static EnchantmentBase wintersGrace = register(new EnchantmentWintersGrace("wintersgrace", RarityConfig.get(ModConfig.rarity.wintersGrace), Slots.MAINHAND));

	//Curses
	public static EnchantmentBase bluntness = register(new EnchantmentBluntness("bluntness", RarityConfig.get(ModConfig.rarity.bluntness), Slots.HAND));
	public static EnchantmentBase cursedEdge = register(new EnchantmentCursedEdge("cursededge", RarityConfig.get(ModConfig.rarity.cursedEdge), Slots.HAND));
	public static EnchantmentBase curseOfDecay = register(new EnchantmentCurseofDecay("curseofdecay", RarityConfig.get(ModConfig.rarity.curseOfDecay), Slots.HAND));
	public static EnchantmentBase curseOfHolding = register(new EnchantmentCurseofHolding("curseofholding", RarityConfig.get(ModConfig.rarity.curseOfHolding), Slots.HAND));
	public static EnchantmentBase curseOfInaccuracy = register(new EnchantmentCurseofInaccuracy("curseofinaccuracy", RarityConfig.get(ModConfig.rarity.curseOfInaccuracy), Slots.HAND));
	public static EnchantmentBase curseOfPossession = register(new EnchantmentCurseofPossession("curseofpossession", RarityConfig.get(ModConfig.rarity.curseOfPossession), Slots.ALL));
	public static EnchantmentBase curseOfVulnerability = register(new EnchantmentCurseofVulnerability("curseofvulnerability", RarityConfig.get(ModConfig.rarity.curseOfVulnerability), Slots.BODY));
	public static EnchantmentBase heavyWeight = register(new EnchantmentHeavyWeight("heavyweight", RarityConfig.get(ModConfig.rarity.heavyWeight), Slots.MAINHAND));
	public static EnchantmentBase inefficient = register(new EnchantmentInefficient("inefficient", RarityConfig.get(ModConfig.rarity.inefficient), Slots.MAINHAND));
	public static EnchantmentBase instability = register(new EnchantmentInstability("instability", RarityConfig.get(ModConfig.rarity.instability), Slots.MAINHAND));
	public static EnchantmentBase pandorasCurse = register(new EnchantmentPandorasCurse("pandorascurse", RarityConfig.get(ModConfig.rarity.pandorasCurse), Slots.ALL));
	public static EnchantmentBase powerless = register(new EnchantmentPowerless("powerless", RarityConfig.get(ModConfig.rarity.powerless), Slots.HAND));
	public static EnchantmentBase rusted = register(new EnchantmentRusted("rusted", RarityConfig.get(ModConfig.rarity.rusted), Slots.HAND));
	public static EnchantmentBase unpredictable = register(new EnchantmentUnpredictable("unpredictable", RarityConfig.get(ModConfig.rarity.unpredictable), Slots.HAND));

	//Rune
	public static EnchantmentBase runeArrowPiercing = register(new EnchantmentRuneArrowPiercing("rune_arrowpiercing", RarityConfig.get(ModConfig.rarity.runeArrowPiercing), Slots.HAND));
	public static EnchantmentBase runeMagicalBlessing = register(new EnchantmentRuneMagicalBlessing("rune_magicalblessing", RarityConfig.get(ModConfig.rarity.runeMagicalBlessing), Slots.MAINHAND));
	public static EnchantmentBase runePiercingCapabilities = register(new EnchantmentRunePiercingCapabilities("rune_piercingcapabilities", RarityConfig.get(ModConfig.rarity.runePiercingCapabilities), Slots.MAINHAND));
	public static EnchantmentBase runeResurrection = register(new EnchantmentRuneResurrection("rune_resurrection", RarityConfig.get(ModConfig.rarity.runeResurrection), Slots.HAND));
	public static EnchantmentBase runeRevival = register(new EnchantmentRuneRevival("rune_revival", RarityConfig.get(ModConfig.rarity.runeRevival), Slots.HAND));
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
	public static EnchantmentBase advancedBaneOfArthropods = register(new EnchantmentAdvancedBaneOfArthropods("advancedbaneofarthropods", RarityConfig.get(ModConfig.rarity.advancedBaneOfArthropods), Slots.MAINHAND));
	public static EnchantmentBase advancedBlastProtection = register(new EnchantmentAdvancedBlastProtection("advancedblastprotection", RarityConfig.get(ModConfig.rarity.advancedBlastProtection), Slots.BODY));
	public static EnchantmentBase advancedEfficiency = register(new EnchantmentAdvancedEfficiency("advancedefficiency", RarityConfig.get(ModConfig.rarity.advancedEfficiency), Slots.MAINHAND));
	public static EnchantmentBase advancedFeatherFalling = register(new EnchantmentAdvancedFeatherFalling("advancedfeatherfalling", RarityConfig.get(ModConfig.rarity.advancedFeatherFalling), Slots.FEET));
	public static EnchantmentBase advancedFireAspect = register(new EnchantmentTierFA("advancedfireaspect", RarityConfig.get(ModConfig.rarity.advancedFireAspect),1));
	public static EnchantmentBase advancedFireProtection = register(new EnchantmentAdvancedFireProtection("advancedfireprotection", RarityConfig.get(ModConfig.rarity.advancedFireProtection), Slots.BODY));
	public static EnchantmentBase advancedFlame = register(new EnchantmentTierFlame("advancedflame", RarityConfig.get(ModConfig.rarity.advancedFlame),1));
	//public static EnchantmentBase advancedFortune;
	public static EnchantmentBase advancedKnockback = register(new EnchantmentAdvancedKnockback("advancedknockback", RarityConfig.get(ModConfig.rarity.advancedKnockback), Slots.MAINHAND));
	public static EnchantmentBase advancedLooting = register(new EnchantmentAdvancedLooting("advancedlooting", RarityConfig.get(ModConfig.rarity.advancedLooting), Slots.MAINHAND));
	public static EnchantmentBase advancedLuckOfTheSea = register(new EnchantmentAdvancedLuckOfTheSea("advancedluckofthesea", RarityConfig.get(ModConfig.rarity.advancedLuckOfTheSea), Slots.HAND));
	public static EnchantmentBase advancedLure = register(new EnchantmentAdvancedLure("advancedlure", RarityConfig.get(ModConfig.rarity.advancedLure), Slots.HAND));
	public static EnchantmentBase advancedMending = register(new EnchantmentAdvancedMending("advancedmending", RarityConfig.get(ModConfig.rarity.advancedMending), Slots.ALL));
	public static EnchantmentBase advancedPower = register(new EnchantmentAdvancedPower("advancedpower", RarityConfig.get(ModConfig.rarity.advancedPower), Slots.HAND));
	public static EnchantmentBase advancedProjectileProtection = register(new EnchantmentAdvancedProjectileProtection("advancedprojectileprotection", RarityConfig.get(ModConfig.rarity.advancedProjectileProtection), Slots.BODY));
	public static EnchantmentBase advancedProtection = register(new EnchantmentAdvancedProtection("advancedprotection", RarityConfig.get(ModConfig.rarity.advancedProtection), Slots.BODY));
	public static EnchantmentBase advancedPunch = register(new EnchantmentAdvancedPunch("advancedpunch", RarityConfig.get(ModConfig.rarity.advancedPunch), Slots.HAND));
	//public static EnchantmentBase advancedRespiration;
	public static EnchantmentBase advancedSharpness = register(new EnchantmentAdvancedSharpness("advancedsharpness", RarityConfig.get(ModConfig.rarity.advancedSharpness), Slots.MAINHAND));
	public static EnchantmentBase advancedSmite = register(new EnchantmentAdvancedSmite("advancedsmite", RarityConfig.get(ModConfig.rarity.advancedSmite), Slots.MAINHAND));
	public static EnchantmentBase advancedThorns = register(new EnchantmentAdvancedThorns("advancedthorns", RarityConfig.get(ModConfig.rarity.advancedThorns), Slots.BODY));
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

	public static void initIncompatLists() {
		for(EnchantmentBase enchantment: enchantmentSet) {
			enchantment.incompatibleEnchantments = ModConfig.incompatible.getIncompatibleEnchantmentsString(enchantment);
		}
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