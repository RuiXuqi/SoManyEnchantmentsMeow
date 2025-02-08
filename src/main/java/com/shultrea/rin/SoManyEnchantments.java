package com.shultrea.rin;

import com.shultrea.rin.attributes.EnchantAttribute;
import com.shultrea.rin.util.Types;
import com.shultrea.rin.properties.*;
import com.shultrea.rin.util.*;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.SocketedCompat;
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
import socketed.common.config.JsonConfig;

@Mod(modid = SoManyEnchantments.MODID, name = SoManyEnchantments.NAME, version = SoManyEnchantments.VERSION,
	 acceptedMinecraftVersions = "[1.12.0, 1.12.2]")
public class SoManyEnchantments {
	
	public static final String MODID = "somanyenchantments";
	public static final String NAME = "Rin's So Many Enchantments?";
	public static final String VERSION = "1.0.2";
	public static final Logger LOGGER = LogManager.getLogger(SoManyEnchantments.MODID);

	@Instance(SoManyEnchantments.MODID)
	public static SoManyEnchantments INSTANCE;
	
	@SidedProxy(clientSide = "com.shultrea.rin.util.ClientProxy", serverSide = "com.shultrea.rin.util.CommonProxy")
	public static CommonProxy PROXY;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent fEvent) {
		CapabilityManager.INSTANCE.register(IArrowProperties.class, new ArrowPropertiesStorage(), ArrowProperties::new);
		MinecraftForge.EVENT_BUS.register(new ArrowPropertiesHandler());
		EnchantmentRegistry.handleSubscribers();
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		if(CompatUtil.isSocketedLoaded())
			SocketedCompat.registerLapisGem();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent fEvent) {
		PotionUtil.initializePotionLists();
		Types.initEnchantmentTabs();
		EnchantmentRegistry.initIncompatLists();
	}
}