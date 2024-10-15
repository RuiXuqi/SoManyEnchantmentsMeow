package com.shultrea.rin.Utility_Sector;

import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Mod sound events
 */
public class SMEsounds {
	
	public static final SMEsounds INSTANCE = new SMEsounds();
	// the sound events
	public static SoundEvent critattack;
	public static SoundEvent cullingfinished;
	public static SoundEvent cullingfailed;
	public static SoundEvent wateraspecthit;
	static List<SoundEvent> SOUND_EVENTS = new ArrayList();
	
	public static void mainRegistry() {
		//registerSounds();
	}
	
	/**
	 * Add the sound events needed
	 */
	public static void registerSounds() {
		critattack = registerSound("attack_crit_hit");
		cullingfinished = registerSound("attack_culling_finished");
		cullingfailed = registerSound("attack_culling_failed");
		wateraspecthit = registerSound("attack_wateraspect_hit");
	}
	
	private static SoundEvent registerSound(String name) {
		ResourceLocation res = new ResourceLocation(SoManyEnchantments.MODID, name);
		SoundEvent evt = new SoundEvent(res).setRegistryName(res);
		SOUND_EVENTS.add(evt);
		return evt;
	}
	/** Register the sound event */
	/**   private static SoundEvent registerSound(String soundName) {
	 
	 return
	 }
	 */
	/**
	 * private static SoundEvent setName(String soundName){ final ResourceLocation soundID = new
	 * ResourceLocation(RefStrings.MODID, soundName); return soundName; }
	 *
	 * private static String getRegistryName(String soundID){
	 *
	 * return soundID; }
	 */
	@Mod.EventBusSubscriber
	public static class EventSubscriber {
		
		@SubscribeEvent
		public static void registerEnchantment(RegistryEvent.Register<SoundEvent> event) {
			//event.getRegistry().register(new SoundEvent(new ResourceLocation(RefStrings.MODID, "attack.crit.Hit")).setRegistryName("critAttack"));
			event.getRegistry().registerAll(SOUND_EVENTS.toArray(new SoundEvent[0]));
			//GameRegistry.register(Debug);
		}
	}
}