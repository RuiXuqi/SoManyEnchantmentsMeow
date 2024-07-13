package com.shultrea.rin.Utility_Sector;

import com.google.common.collect.Lists;
import com.shultrea.rin.Main_Sector.SoManyEnchantments;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The common proxy (shared server and client code)
 */
public class CommProxy {
	
	final List<SoundEvent> SOUND_EVENTS = Lists.newArrayList();
	
	public void preInit(FMLPreInitializationEvent event) {
		SMElogM.logger.info("PreInitializing " + SoManyEnchantments.NAME + "...");
		//SMEsounds.mainRegistry();
		SMEnetwork.mainRegistry();
		//MinecraftForge.EVENT_BUS.register(new LivingAttackFixerHandler());
	}
	
	public void onInit(FMLInitializationEvent event) {
		SMElogM.logger.info("Initializing " + SoManyEnchantments.NAME + "...");
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		SMElogM.logger.info("PostInitializing " + SoManyEnchantments.NAME + "...");
	}
	
	@Nullable
	public EntityPlayer getClientPlayer() {
		return null;
	}
}