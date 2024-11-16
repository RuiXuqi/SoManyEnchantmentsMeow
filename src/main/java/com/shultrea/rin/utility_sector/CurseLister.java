package com.shultrea.rin.utility_sector;

import com.shultrea.rin.enchantments.curses.EnchantmentPandorasCurse;
import net.minecraft.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class CurseLister {
	
	public static final List<Enchantment> CURSE = new ArrayList<>();

	public static void initCursesList() {
		for(Enchantment e : Enchantment.REGISTRY)
			if(e.isCurse() && !(e instanceof EnchantmentPandorasCurse))
				CURSE.add(e);
	}
}
