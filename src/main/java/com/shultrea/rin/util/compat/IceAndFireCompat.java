package com.shultrea.rin.util.compat;

import com.github.alexthe666.iceandfire.core.ModItems;
import com.github.alexthe666.iceandfire.item.ItemDragonBow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class IceAndFireCompat {
	
	public static boolean isDragonboneBow(ItemStack bow) {
		return bow.getItem() instanceof ItemDragonBow;
	}
	
	public static Item getDragonboneArrow() {
		return ModItems.dragonbone_arrow;
	}
}