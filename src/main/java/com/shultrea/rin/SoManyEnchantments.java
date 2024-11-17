package com.shultrea.rin;

import com.shultrea.rin.enums.Types;
import com.shultrea.rin.properties.*;
import com.shultrea.rin.utility_sector.*;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.registry.ModRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = SoManyEnchantments.MODID, name = SoManyEnchantments.NAME, version = SoManyEnchantments.VERSION,
		acceptedMinecraftVersions = "[1.12.0, 1.12.2]")
public class SoManyEnchantments {
	
	public static final String MODID = "somanyenchantments";
	public static final String NAME = "Rin's So Many Enchantments?";
	public static final String VERSION = "0.5.5";
	public static boolean hotbarOnly = true;
	public static final Logger LOGGER = LogManager.getLogger(SoManyEnchantments.NAME);

	@Instance(SoManyEnchantments.MODID)
	public static SoManyEnchantments instance;
	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static CommProxy proxy;
	
	/**
	 * Culled is a damage source added by this mod that is used to finish off low health opponents. Like Deconstruct, it
	 * bypasses armor and is absolute.
	 */
	public static DamageSource Culled = new DamageSource("Culled").setDamageIsAbsolute().setDamageBypassesArmor();
	/**
	 * Physical Damage is a damage source added by this mod that is used to deal ordinary damage to a mob. Some
	 * enchantments in this mod make use of it.
	 */
	public static DamageSource PhysicalDamage = new DamageSource("PhysicalDamage");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent fEvent) {
		CapabilityManager.INSTANCE.register(IArrowProperties.class, new ArrowPropertiesStorage(), ArrowProperties::new);
		CapabilityManager.INSTANCE.register(IPlayerProperties.class, new PlayerPropertiesStorage(), PlayerProperties::new);
		ModRegistry.init();
		proxy.preInit(fEvent);
		SMEsounds.registerSounds();
		EnchantmentRegistry.handleSubscribers();
		MinecraftForge.EVENT_BUS.register(new PlayerPropertiesHandler());
		MinecraftForge.EVENT_BUS.register(new ArrowPropertiesHandler());
	}
	
	@EventHandler
	public void load(FMLInitializationEvent fEvent) {
		proxy.onInit(fEvent);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent fEvent) {
		proxy.postInit(fEvent);
		PotionLister.initializePotionLists();
		Types.initializeEnchantmentTab();
		CurseLister.initCursesList();
		EnchantmentRegistry.initIncompatLists();
	}
}