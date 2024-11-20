package com.shultrea.rin.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;

public class ClientProxy extends CommonProxy {
	
	@Nullable
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().player;
	}
}