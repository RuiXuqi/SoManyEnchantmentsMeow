package com.shultrea.rin.registry;

import com.shultrea.rin.config.RarityConfig;
import com.shultrea.rin.enchantments.weapon.selfheal.*;
import com.shultrea.rin.util.Slots;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.enchantments.*;
import com.shultrea.rin.enchantments.armor.*;
import com.shultrea.rin.enchantments.armor.protection.*;
import com.shultrea.rin.enchantments.armor.thorns.*;
import com.shultrea.rin.enchantments.base.*;
import com.shultrea.rin.enchantments.bow.*;
import com.shultrea.rin.enchantments.curses.*;
import com.shultrea.rin.enchantments.fishing.*;
import com.shultrea.rin.enchantments.hoe.*;
import com.shultrea.rin.enchantments.rune.*;
import com.shultrea.rin.enchantments.shield.*;
import com.shultrea.rin.enchantments.tool.*;
import com.shultrea.rin.enchantments.weapon.*;
import com.shultrea.rin.enchantments.weapon.ancient.*;
import com.shultrea.rin.enchantments.weapon.conditionaldamage.*;
import com.shultrea.rin.enchantments.weapon.crits.*;
import com.shultrea.rin.enchantments.weapon.damage.*;
import com.shultrea.rin.enchantments.weapon.damagemultiplier.*;
import com.shultrea.rin.enchantments.weapon.potiondebuffer.*;
import com.shultrea.rin.enchantments.weapon.subject.EnchantmentSubjectEnchantments;
import com.shultrea.rin.enchantments.weapon.weather.*;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
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

	public static EnchantmentBase adept = register(new EnchantmentAdept("adept", RarityConfig.get(ModConfig.rarity.adept), Slots.HAND));
	public static EnchantmentBase ancientSealedCurses = register(new EnchantmentAncientSealedCurses("ancientsealedcurses", RarityConfig.get(ModConfig.rarity.ancientSealedCurses), Slots.HAND));
	public static EnchantmentBase ancientSwordMastery = register(new EnchantmentAncientSwordMastery("ancientswordmastery", RarityConfig.get(ModConfig.rarity.ancientSwordMastery), Slots.HAND));
	public static EnchantmentBase arcSlash = register(new EnchantmentArcSlash("arcslash", RarityConfig.get(ModConfig.rarity.arcSlash), Slots.HAND));
	public static EnchantmentBase ashDestroyer = register(new EnchantmentAshDestroyer("ashdestroyer", RarityConfig.get(ModConfig.rarity.ashDestroyer), Slots.HAND));
	public static EnchantmentBase atomicDeconstructor = register(new EnchantmentAtomicDeconstructor("atomicdeconstructor", RarityConfig.get(ModConfig.rarity.atomicDeconstructor), Slots.HAND));
	public static EnchantmentBase blessedEdge = register(new EnchantmentBlessedEdge("blessededge", RarityConfig.get(ModConfig.rarity.blessedEdge), Slots.HAND));
	public static EnchantmentBase brutality = register(new EnchantmentBrutality("brutality", RarityConfig.get(ModConfig.rarity.brutality), Slots.HAND));
	public static EnchantmentBase burningShield = register(new EnchantmentBurningShield("burningshield", RarityConfig.get(ModConfig.rarity.burningShield), Slots.HAND));
	public static EnchantmentBase burningThorns = register(new EnchantmentBurningThorns("burningthorns", RarityConfig.get(ModConfig.rarity.burningThorns), Slots.BODY));
	public static EnchantmentBase butchering = register(new EnchantmentButchering("butchering", RarityConfig.get(ModConfig.rarity.butchering), Slots.HAND));
	public static EnchantmentBase clearskiesFavor = register(new EnchantmentClearskiesFavor("clearskiesfavor", RarityConfig.get(ModConfig.rarity.clearskiesFavor), Slots.HAND));
	public static EnchantmentBase combatMedic = register(new EnchantmentCombatMedic("combatmedic", RarityConfig.get(ModConfig.rarity.combatMedic), Slots.HEAD));
	public static EnchantmentBase counterAttack = register(new EnchantmentCounterAttack("counterattack", RarityConfig.get(ModConfig.rarity.counterAttack), Slots.HAND));
	public static EnchantmentBase criticalStrike = register(new EnchantmentCriticalStrike("criticalstrike", RarityConfig.get(ModConfig.rarity.criticalStrike), Slots.HAND));
	public static EnchantmentBase cryogenic = register(new EnchantmentCryogenic("cryogenic", RarityConfig.get(ModConfig.rarity.cryogenic), Slots.HAND));
	public static EnchantmentBase culling = register(new EnchantmentCulling("culling", RarityConfig.get(ModConfig.rarity.culling), Slots.HAND));
	public static EnchantmentBase darkShadows = register(new EnchantmentDarkShadows("darkshadows", RarityConfig.get(ModConfig.rarity.darkShadows), Slots.HAND));
	public static EnchantmentBase defusingEdge = register(new EnchantmentDefusingEdge("defusingedge", RarityConfig.get(ModConfig.rarity.defusingEdge), Slots.HAND));
	public static EnchantmentBase desolator = register(new EnchantmentDesolator("desolator", RarityConfig.get(ModConfig.rarity.desolator), Slots.HAND));
	public static EnchantmentBase difficultysEndowment = register(new EnchantmentDifficultysEndowment("difficultysendowment", RarityConfig.get(ModConfig.rarity.difficultysEndowment), Slots.HAND));
	public static EnchantmentBase disarmament = register(new EnchantmentDisarmament("disarmament", RarityConfig.get(ModConfig.rarity.disarmament), Slots.HAND));
	public static EnchantmentBase disorientatingBlade = register(new EnchantmentDisorientatingBlade("disorientatingblade", RarityConfig.get(ModConfig.rarity.disorientatingBlade), Slots.HAND));
	public static EnchantmentBase empoweredDefence = register(new EnchantmentEmpoweredDefence("empowereddefence", RarityConfig.get(ModConfig.rarity.empoweredDefence), Slots.HAND));
	public static EnchantmentBase envenomed = register(new EnchantmentEnvenomed("envenomed", RarityConfig.get(ModConfig.rarity.envenomed), Slots.HAND));
	public static EnchantmentBase evasion = register(new EnchantmentEvasion("evasion", RarityConfig.get(ModConfig.rarity.evasion), Slots.LEGS));
	public static EnchantmentBase fieryEdge = register(new EnchantmentFieryEdge("fieryedge", RarityConfig.get(ModConfig.rarity.fieryEdge), Slots.HAND));
	public static EnchantmentBase flinging = register(new EnchantmentFlinging("flinging", RarityConfig.get(ModConfig.rarity.flinging), Slots.HAND));
	public static EnchantmentBase horsDeCombat = register(new EnchantmentHorsDeCombat("horsdecombat", RarityConfig.get(ModConfig.rarity.horsDeCombat), Slots.HAND));
	public static EnchantmentBase inhumane = register(new EnchantmentInhumane("inhumane", RarityConfig.get(ModConfig.rarity.inhumane), Slots.HAND));
	public static EnchantmentBase innerBerserk = register(new EnchantmentInnerBerserk("innerberserk", RarityConfig.get(ModConfig.rarity.innerBerserk), Slots.CHEST));
	public static EnchantmentBase jaggedRake = register(new EnchantmentJaggedRake("jaggedrake", RarityConfig.get(ModConfig.rarity.jaggedRake), Slots.MAINHAND));
	public static EnchantmentBase levitator = register(new EnchantmentLevitator("levitator", RarityConfig.get(ModConfig.rarity.levitator), Slots.HAND));
	public static EnchantmentBase lifesteal = register(new EnchantmentLifesteal("lifesteal", RarityConfig.get(ModConfig.rarity.lifesteal), Slots.HAND));
	public static EnchantmentBase lightWeight = register(new EnchantmentLightWeight("lightweight", RarityConfig.get(ModConfig.rarity.lightWeight), Slots.FEET));
	public static EnchantmentBase luckMagnification = register(new EnchantmentLuckMagnification("luckmagnification", RarityConfig.get(ModConfig.rarity.luckMagnification), Slots.HAND));
	public static EnchantmentBase lunasBlessing = register(new EnchantmentLunasBlessing("lunasblessing", RarityConfig.get(ModConfig.rarity.lunasBlessing), Slots.HAND));
	public static EnchantmentBase magicProtection = register(new EnchantmentMagicProtection("magicprotection", RarityConfig.get(ModConfig.rarity.magicProtection), Slots.BODY));
	public static EnchantmentBase magmaWalker = register(new EnchantmentMagmaWalker("magmawalker", RarityConfig.get(ModConfig.rarity.magmaWalker), Slots.FEET));
	public static EnchantmentBase moisturized = register(new EnchantmentMoisturized("moisturized", RarityConfig.get(ModConfig.rarity.moisturized), Slots.MAINHAND));
	public static EnchantmentBase mortalitas = register(new EnchantmentMortalitas("mortalitas", RarityConfig.get(ModConfig.rarity.mortalitas), Slots.HAND));
	public static EnchantmentBase naturalBlocking = register(new EnchantmentNaturalBlocking("naturalblocking", RarityConfig.get(ModConfig.rarity.naturalBlocking), Slots.HAND));
	public static EnchantmentBase parry = register(new EnchantmentParry("parry", RarityConfig.get(ModConfig.rarity.parry), Slots.HAND));
	public static EnchantmentBase penetratingEdge = register(new EnchantmentPenetratingEdge("penetratingedge", RarityConfig.get(ModConfig.rarity.penetratingEdge), Slots.HAND));
	public static EnchantmentBase physicalProtection = register(new EnchantmentPhysicalProtection("physicalprotection", RarityConfig.get(ModConfig.rarity.physicalProtection), Slots.BODY));
	public static EnchantmentBase purgingBlade = register(new EnchantmentPurgingBlade("purgingblade", RarityConfig.get(ModConfig.rarity.purgingBlade), Slots.HAND));
	public static EnchantmentBase purification = register(new EnchantmentPurification("purification", RarityConfig.get(ModConfig.rarity.purification), Slots.HAND));
	public static EnchantmentBase pushing = register(new EnchantmentPushing("pushing", RarityConfig.get(ModConfig.rarity.pushing), Slots.HAND));
	public static EnchantmentBase rainsBestowment = register(new EnchantmentRainsBestowment("rainsbestowment", RarityConfig.get(ModConfig.rarity.rainsBestowment), Slots.HAND));
	public static EnchantmentBase reviledBlade = register(new EnchantmentReviledBlade("reviledblade", RarityConfig.get(ModConfig.rarity.reviledBlade), Slots.HAND));
	public static EnchantmentBase reinforcedsharpness = register(new EnchantmentReinforcedSharpness("reinforcedsharpness", RarityConfig.get(ModConfig.rarity.reinforcedsharpness), Slots.MAINHAND));
	public static EnchantmentBase smelter = register(new EnchantmentSmelter("smelter", RarityConfig.get(ModConfig.rarity.smelter), Slots.MAINHAND));
	public static EnchantmentBase solsBlessing = register(new EnchantmentSolsBlessing("solsblessing", RarityConfig.get(ModConfig.rarity.solsBlessing), Slots.HAND));
	public static EnchantmentBase spellBreaker = register(new EnchantmentSpellBreaker("spellbreaker", RarityConfig.get(ModConfig.rarity.spellBreaker), Slots.HAND));
	public static EnchantmentBase splitShot = register(new EnchantmentSplitshot("splitshot", RarityConfig.get(ModConfig.rarity.splitShot), Slots.HAND));
	public static EnchantmentBase strafe = register(new EnchantmentStrafe("strafe", RarityConfig.get(ModConfig.rarity.strafe), Slots.HAND));
	public static EnchantmentBase strengthenedVitality = register(new EnchantmentStrengthenedVitality("strengthenedvitality", RarityConfig.get(ModConfig.rarity.strengthenedVitality), Slots.CHEST));
	public static EnchantmentBase swifterSlashes = register(new EnchantmentSwifterSlashes("swifterslashes", RarityConfig.get(ModConfig.rarity.swifterSlashes), Slots.HAND));
	public static EnchantmentBase thunderstormsBestowment = register(new EnchantmentThunderstormsBestowment("thunderstormsbestowment", RarityConfig.get(ModConfig.rarity.thunderstormsBestowment), Slots.HAND));
	public static EnchantmentBase trueStrike = register(new EnchantmentTrueStrike("truestrike", RarityConfig.get(ModConfig.rarity.trueStrike), Slots.HAND));
	public static EnchantmentBase underwaterStrider = register(new EnchantmentUnderwaterStrider("underwaterstrider", RarityConfig.get(ModConfig.rarity.underwaterStrider), Slots.FEET));
	public static EnchantmentBase unreasonable = register(new EnchantmentUnreasonable("unreasonable", RarityConfig.get(ModConfig.rarity.unreasonable), Slots.HAND));
	public static EnchantmentBase unsheathing = register(new EnchantmentUnsheathing("unsheathing", RarityConfig.get(ModConfig.rarity.unsheathing), Slots.MAINHAND));
	public static EnchantmentBase upgradedPotentials = register(new EnchantmentUpgradedPotentials("upgradedpotentials", RarityConfig.get(ModConfig.rarity.upgradedPotentials), Slots.MAINHAND));
	public static EnchantmentBase viper = register(new EnchantmentViper("viper", RarityConfig.get(ModConfig.rarity.viper), Slots.HAND));
	public static EnchantmentBase waterAspect = register(new EnchantmentWaterAspect("wateraspect", RarityConfig.get(ModConfig.rarity.waterAspect), Slots.HAND));
	public static EnchantmentBase plowing = register(new EnchantmentPlowing("plowing", RarityConfig.get(ModConfig.rarity.plowing), Slots.MAINHAND));
	public static EnchantmentBase wintersGrace = register(new EnchantmentWintersGrace("wintersgrace", RarityConfig.get(ModConfig.rarity.wintersGrace), Slots.HAND));

	//Curses
	public static EnchantmentBase ascetic = register(new EnchantmentAscetic("ascetic", RarityConfig.get(ModConfig.rarity.ascetic), Slots.HAND));
	public static EnchantmentBase bluntness = register(new EnchantmentBluntness("bluntness", RarityConfig.get(ModConfig.rarity.bluntness), Slots.HAND));
	public static EnchantmentBase cursedEdge = register(new EnchantmentCursedEdge("cursededge", RarityConfig.get(ModConfig.rarity.cursedEdge), Slots.HAND));
	public static EnchantmentBase curseOfDecay = register(new EnchantmentCurseofDecay("curseofdecay", RarityConfig.get(ModConfig.rarity.curseOfDecay), Slots.ALL));
	public static EnchantmentBase curseOfHolding = register(new EnchantmentCurseofHolding("curseofholding", RarityConfig.get(ModConfig.rarity.curseOfHolding), Slots.ALL));
	public static EnchantmentBase curseOfInaccuracy = register(new EnchantmentCurseofInaccuracy("curseofinaccuracy", RarityConfig.get(ModConfig.rarity.curseOfInaccuracy), Slots.HAND));
	public static EnchantmentBase curseOfPossession = register(new EnchantmentCurseofPossession("curseofpossession", RarityConfig.get(ModConfig.rarity.curseOfPossession), Slots.ALL));
	public static EnchantmentBase curseOfVulnerability = register(new EnchantmentCurseofVulnerability("curseofvulnerability", RarityConfig.get(ModConfig.rarity.curseOfVulnerability), Slots.BODY));
	public static EnchantmentBase dragging = register(new EnchantmentDragging("dragging", RarityConfig.get(ModConfig.rarity.dragging), Slots.HAND));
	public static EnchantmentBase extinguish = register(new EnchantmentExtinguish("extinguish", RarityConfig.get(ModConfig.rarity.extinguish), Slots.HAND));
	public static EnchantmentBase heavyWeight = register(new EnchantmentHeavyWeight("heavyweight", RarityConfig.get(ModConfig.rarity.heavyWeight), Slots.HAND));
	public static EnchantmentBase inefficient = register(new EnchantmentInefficient("inefficient", RarityConfig.get(ModConfig.rarity.inefficient), Slots.MAINHAND));
	public static EnchantmentBase instability = register(new EnchantmentInstability("instability", RarityConfig.get(ModConfig.rarity.instability), Slots.HAND));
	public static EnchantmentBase meltdown = register(new EnchantmentMeltdown("meltdown", RarityConfig.get(ModConfig.rarity.meltdown), Slots.CHEST));
	public static EnchantmentBase pandorasCurse = register(new EnchantmentPandorasCurse("pandorascurse", RarityConfig.get(ModConfig.rarity.pandorasCurse), Slots.ALL));
	public static EnchantmentBase powerless = register(new EnchantmentPowerless("powerless", RarityConfig.get(ModConfig.rarity.powerless), Slots.HAND));
	public static EnchantmentBase rusted = register(new EnchantmentRusted("rusted", RarityConfig.get(ModConfig.rarity.rusted), Slots.ALL));
	public static EnchantmentBase unpredictable = register(new EnchantmentUnpredictable("unpredictable", RarityConfig.get(ModConfig.rarity.unpredictable), Slots.HAND));

	//Rune
	public static EnchantmentBase runeArrowPiercing = register(new EnchantmentRuneArrowPiercing("rune_arrowpiercing", RarityConfig.get(ModConfig.rarity.runeArrowPiercing), Slots.HAND));
	public static EnchantmentBase runeMagicalBlessing = register(new EnchantmentRuneMagicalBlessing("rune_magicalblessing", RarityConfig.get(ModConfig.rarity.runeMagicalBlessing), Slots.HAND));
	public static EnchantmentBase runePiercingCapabilities = register(new EnchantmentRunePiercingCapabilities("rune_piercingcapabilities", RarityConfig.get(ModConfig.rarity.runePiercingCapabilities), Slots.HAND));
	public static EnchantmentBase runeResurrection = register(new EnchantmentRuneResurrection("rune_resurrection", RarityConfig.get(ModConfig.rarity.runeResurrection), Slots.HAND));
	public static EnchantmentBase runeRevival = register(new EnchantmentRuneRevival("rune_revival", RarityConfig.get(ModConfig.rarity.runeRevival), Slots.HAND));

	//Subject
	public static EnchantmentBase subjectBiology = register(new EnchantmentSubjectEnchantments("subjectbiology", RarityConfig.get(ModConfig.rarity.subjectBiology),0, Slots.HAND));
	public static EnchantmentBase subjectChemistry = register(new EnchantmentSubjectEnchantments("subjectchemistry", RarityConfig.get(ModConfig.rarity.subjectChemistry),1, Slots.HAND));
	public static EnchantmentBase subjectEnglish = register(new EnchantmentSubjectEnchantments("subjectenglish", RarityConfig.get(ModConfig.rarity.subjectEnglish),2, Slots.HAND));
	public static EnchantmentBase subjectHistory = register(new EnchantmentSubjectEnchantments("subjecthistory", RarityConfig.get(ModConfig.rarity.subjectHistory),3, Slots.HAND));
	public static EnchantmentBase subjectMathematics = register(new EnchantmentSubjectEnchantments("subjectmathematics", RarityConfig.get(ModConfig.rarity.subjectMathematics),4, Slots.HAND));
	public static EnchantmentBase subjectPE = register(new EnchantmentSubjectEnchantments("subjectpe", RarityConfig.get(ModConfig.rarity.subjectPE),5, Slots.HAND));
	public static EnchantmentBase subjectPhysics = register(new EnchantmentSubjectEnchantments("subjectphysics", RarityConfig.get(ModConfig.rarity.subjectPhysics),6, Slots.HAND));

	//Lesser
	public static EnchantmentBase lesserBaneOfArthropods = register(new EnchantmentTierDamage("lesserbaneofarthropods", RarityConfig.get(ModConfig.rarity.lesserBaneOfArthropods),6, Slots.HAND));
	public static EnchantmentBase lesserFireAspect = register(new EnchantmentTierFA("lesserfireaspect", RarityConfig.get(ModConfig.rarity.lesserFireAspect),0, Slots.HAND));
	public static EnchantmentBase lesserFlame = register(new EnchantmentTierFlame("lesserflame", RarityConfig.get(ModConfig.rarity.lesserFlame),0, Slots.HAND));
	public static EnchantmentBase lesserSharpness = register(new EnchantmentTierDamage("lessersharpness", RarityConfig.get(ModConfig.rarity.lesserSharpness),0, Slots.HAND));
	public static EnchantmentBase lesserSmite = register(new EnchantmentTierDamage("lessersmite", RarityConfig.get(ModConfig.rarity.lesserSmite),3, Slots.HAND));

	//Advanced
	public static EnchantmentBase advancedBaneOfArthropods = register(new EnchantmentTierDamage("advancedbaneofarthropods", RarityConfig.get(ModConfig.rarity.advancedBaneOfArthropods), 7, Slots.HAND));
	public static EnchantmentBase advancedBlastProtection = register(new EnchantmentAdvancedBlastProtection("advancedblastprotection", RarityConfig.get(ModConfig.rarity.advancedBlastProtection), Slots.BODY));
	public static EnchantmentBase advancedEfficiency = register(new EnchantmentAdvancedEfficiency("advancedefficiency", RarityConfig.get(ModConfig.rarity.advancedEfficiency), Slots.MAINHAND));
	public static EnchantmentBase advancedFeatherFalling = register(new EnchantmentAdvancedFeatherFalling("advancedfeatherfalling", RarityConfig.get(ModConfig.rarity.advancedFeatherFalling), Slots.FEET));
	public static EnchantmentBase advancedFireAspect = register(new EnchantmentTierFA("advancedfireaspect", RarityConfig.get(ModConfig.rarity.advancedFireAspect),1, Slots.HAND));
	public static EnchantmentBase advancedFireProtection = register(new EnchantmentAdvancedFireProtection("advancedfireprotection", RarityConfig.get(ModConfig.rarity.advancedFireProtection), Slots.BODY));
	public static EnchantmentBase advancedFlame = register(new EnchantmentTierFlame("advancedflame", RarityConfig.get(ModConfig.rarity.advancedFlame),1, Slots.HAND));
	public static EnchantmentBase advancedKnockback = register(new EnchantmentAdvancedKnockback("advancedknockback", RarityConfig.get(ModConfig.rarity.advancedKnockback), Slots.HAND));
	public static EnchantmentBase advancedLooting = register(new EnchantmentAdvancedLooting("advancedlooting", RarityConfig.get(ModConfig.rarity.advancedLooting), Slots.HAND));
	public static EnchantmentBase advancedLuckOfTheSea = register(new EnchantmentAdvancedLuckOfTheSea("advancedluckofthesea", RarityConfig.get(ModConfig.rarity.advancedLuckOfTheSea), Slots.HAND));
	public static EnchantmentBase advancedLure = register(new EnchantmentAdvancedLure("advancedlure", RarityConfig.get(ModConfig.rarity.advancedLure), Slots.HAND));
	public static EnchantmentBase advancedMending = register(new EnchantmentAdvancedMending("advancedmending", RarityConfig.get(ModConfig.rarity.advancedMending), Slots.ALL));
	public static EnchantmentBase advancedPower = register(new EnchantmentAdvancedPower("advancedpower", RarityConfig.get(ModConfig.rarity.advancedPower), Slots.HAND));
	public static EnchantmentBase advancedProjectileProtection = register(new EnchantmentAdvancedProjectileProtection("advancedprojectileprotection", RarityConfig.get(ModConfig.rarity.advancedProjectileProtection), Slots.BODY));
	public static EnchantmentBase advancedProtection = register(new EnchantmentAdvancedProtection("advancedprotection", RarityConfig.get(ModConfig.rarity.advancedProtection), Slots.BODY));
	public static EnchantmentBase advancedPunch = register(new EnchantmentAdvancedPunch("advancedpunch", RarityConfig.get(ModConfig.rarity.advancedPunch), Slots.HAND));
	public static EnchantmentBase advancedSharpness = register(new EnchantmentTierDamage("advancedsharpness", RarityConfig.get(ModConfig.rarity.advancedSharpness), 1, Slots.HAND));
	public static EnchantmentBase advancedSmite = register(new EnchantmentTierDamage("advancedsmite", RarityConfig.get(ModConfig.rarity.advancedSmite), 4, Slots.HAND));
	public static EnchantmentBase advancedThorns = register(new EnchantmentAdvancedThorns("advancedthorns", RarityConfig.get(ModConfig.rarity.advancedThorns), Slots.BODY));
	//Supreme
	public static EnchantmentBase supremeBaneOfArthropods = register(new EnchantmentTierDamage("supremebaneofarthropods", RarityConfig.get(ModConfig.rarity.supremeBaneOfArthropods),8, Slots.HAND));
	public static EnchantmentBase supremeFireAspect = register(new EnchantmentTierFA("supremefireaspect", RarityConfig.get(ModConfig.rarity.supremeFireAspect),2, Slots.HAND));
	public static EnchantmentBase supremeFlame = register(new EnchantmentTierFlame("supremeflame", RarityConfig.get(ModConfig.rarity.supremeFlame),2, Slots.HAND));
	public static EnchantmentBase supremeProtection = register(new EnchantmentSupremeProtection("supremeprotection", Enchantment.Rarity.VERY_RARE, Slots.BODY));
	public static EnchantmentBase supremeSharpness = register(new EnchantmentTierDamage("supremesharpness", RarityConfig.get(ModConfig.rarity.supremeSharpness),2, Slots.HAND));
	public static EnchantmentBase supremeSmite = register(new EnchantmentTierDamage("supremesmite", RarityConfig.get(ModConfig.rarity.supremeSmite),5, Slots.HAND));
	
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
		if(CompatUtil.isRLCombatLoaded()) {
			MinecraftForge.EVENT_BUS.register(RLCombatCompat.class);
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