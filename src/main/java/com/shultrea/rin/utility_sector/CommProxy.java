package com.shultrea.rin.utility_sector;

import com.google.common.collect.Lists;
import com.shultrea.rin.SoManyEnchantments;
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
		SoManyEnchantments.LOGGER.info("PreInitializing " + SoManyEnchantments.NAME + "...");
		//SMEsounds.mainRegistry();
		SMEnetwork.mainRegistry();
	}
	
	public void onInit(FMLInitializationEvent event) {
		SoManyEnchantments.LOGGER.info("Initializing " + SoManyEnchantments.NAME + "...");
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		SoManyEnchantments.LOGGER.info("PostInitializing " + SoManyEnchantments.NAME + "...");
	}
	
	@Nullable
	public EntityPlayer getClientPlayer() {
		return null;
	}
}