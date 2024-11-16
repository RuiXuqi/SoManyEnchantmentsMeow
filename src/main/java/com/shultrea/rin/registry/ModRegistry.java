package com.shultrea.rin.registry;

import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = SoManyEnchantments.MODID)
public class ModRegistry {

        public static SoundEvent CRITICAL_STRIKE;
        public static SoundEvent ATOMIC_DECONSTRUCT;
        public static SoundEvent PANDORA_REMOVAL;

        public static void init() {
                CRITICAL_STRIKE = new SoundEvent(new ResourceLocation(SoManyEnchantments.MODID, "critical_strike")).setRegistryName("critical_strike");
                ATOMIC_DECONSTRUCT = new SoundEvent(new ResourceLocation(SoManyEnchantments.MODID, "atomic_deconstruct")).setRegistryName("atomic_deconstruct");
                PANDORA_REMOVAL = new SoundEvent(new ResourceLocation(SoManyEnchantments.MODID, "pandora_removal")).setRegistryName("pandora_removal");
        }

        @SubscribeEvent
        public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
                //TODO: move to smesounds?
                event.getRegistry().register(CRITICAL_STRIKE);
                event.getRegistry().register(ATOMIC_DECONSTRUCT);
                event.getRegistry().register(PANDORA_REMOVAL);
        }

}