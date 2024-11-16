package com.shultrea.rin.properties;

import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerPropertiesHandler {
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void onPlayerCapAttach(AttachCapabilitiesEvent<Entity> event) {
		if(!event.getObject().hasCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null)) {
			event.addCapability(new ResourceLocation(SoManyEnchantments.MODID + ":player_capabilities"), new PlayerPropertiesProvider());
		}
	}
}
