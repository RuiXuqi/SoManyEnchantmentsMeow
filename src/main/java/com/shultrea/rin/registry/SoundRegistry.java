package com.shultrea.rin.registry;

import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoundRegistry {

        public static SoundEvent CRITICAL_STRIKE;
        public static SoundEvent CRITICAL_STRIKE_FAIL;
        public static SoundEvent ATOMIC_DECONSTRUCT;
        public static SoundEvent PANDORA_REMOVAL;
        public static SoundEvent CULLING;
        
        static {
                CRITICAL_STRIKE = registerSound("critical_strike");
                CRITICAL_STRIKE_FAIL = registerSound("critical_strike_fail");
                ATOMIC_DECONSTRUCT = registerSound("atomic_deconstruct");
                PANDORA_REMOVAL = registerSound("pandora_removal");
                CULLING = registerSound("culling");
        }
        
        private static SoundEvent registerSound(String name) {
                ResourceLocation res = new ResourceLocation(SoManyEnchantments.MODID, name);
                return new SoundEvent(res).setRegistryName(res);
        }
        
        @Mod.EventBusSubscriber
        public static class EventSubscriber {
                
                @SubscribeEvent
                public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
                        event.getRegistry().register(CRITICAL_STRIKE);
                        event.getRegistry().register(CRITICAL_STRIKE_FAIL);
                        event.getRegistry().register(ATOMIC_DECONSTRUCT);
                        event.getRegistry().register(PANDORA_REMOVAL);
                        event.getRegistry().register(CULLING);
                }
        }
}