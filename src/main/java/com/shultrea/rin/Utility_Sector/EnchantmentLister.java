package com.shultrea.rin.Utility_Sector;

import com.shultrea.rin.Interfaces.IAncientEnchantment;
import com.shultrea.rin.Interfaces.IEnchantmentRune;
import com.shultrea.rin.enchantments.curses.EnchantmentPandorasCurse;
import net.minecraft.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentLister {
	
	public static final List<Enchantment> NORMAL = new ArrayList();
	public static final List<Enchantment> CURSE = new ArrayList();
	public static final List<Enchantment> ANCIENT = new ArrayList();
	public static final List<Enchantment> RUNE = new ArrayList();
	static List<Enchantment> collection = new ArrayList();
	
	public static void initEnchantmentList() {
		for(Enchantment s : Enchantment.REGISTRY) {
			collection.add(s);
		}
		for(Enchantment e : collection) {
			if(e instanceof IAncientEnchantment) {
				ANCIENT.add(e);
			}
			else if(e instanceof IEnchantmentRune) {
				RUNE.add(e);
			}
			else if(e.isCurse() && !(e instanceof EnchantmentPandorasCurse)) {
				CURSE.add(e);
			}
			else {
				NORMAL.add(e);
			}
		}
	}
}
