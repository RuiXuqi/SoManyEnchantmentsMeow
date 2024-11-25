package com.shultrea.rin.util.compat;

import de.Whitedraco.switchbow.helper.ArrowItemStackEqual;
import de.Whitedraco.switchbow.helper.QuiverArrowHelper;
import de.Whitedraco.switchbow.helper.SwitchBowHelper;
import de.Whitedraco.switchbow.item.ItemSwitchBow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class SwitchbowCompat {
	
	public static boolean isSwitchbow(ItemStack bow) {
		return bow.getItem() instanceof ItemSwitchBow;
	}
	
	public static ItemStack getSwitchbowAmmo(EntityPlayer player, ItemStack bow) {
		if(ArrowItemStackEqual.containsArrow(QuiverArrowHelper.getArrowsInInvAndQuiver(player, player.inventory), SwitchBowHelper.getSelectionArrow(bow))) return SwitchBowHelper.getSelectionArrow(bow);
		else return ItemStack.EMPTY;
	}
}