package com.shultrea.rin.hook;

import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public class HookArthropod {
	
	//com/Shultrea/Rin/Hook/HookArthropod
	//handleArthropod
	//(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/Entity;Z)V
	public static void handleArthropod(EntityLivingBase user, Entity target, boolean offhand) {
		//System.out.println("handleArthropod: "+offhand);
		if(user != null) {
			ItemStack stack = offhand ? user.getHeldItemOffhand() : user.getHeldItemMainhand();
			if(!stack.isEmpty()) {
				NBTTagList nbttaglist = stack.getEnchantmentTagList();
				for(int i = 0; i < nbttaglist.tagCount(); ++i) {
					int j = nbttaglist.getCompoundTagAt(i).getShort("id");
					int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
					if(Enchantment.getEnchantmentByID(j) != null) {
						Enchantment ench = Enchantment.getEnchantmentByID(j);
						if(ench instanceof EnchantmentBase) {
							EnchantmentBase enchant = (EnchantmentBase)ench;
							if(enchant.isEnabled()) enchant.onEntityDamagedAlt(user, target, stack, k);
						}
					}
				}
			}
		}
	}
	
	//com/Shultrea/Rin/Hook/HookArthropod
	//hookArthropod
	//(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/Entity;)V
	public static void hookArthropod(EntityLivingBase user, Entity target) {
		if(user != null) {
			boolean offhand = stackTraceHasClass("bettercombat.mod.network.PacketOffhandAttack$Handler");
			//FIXME check only if bettercombat is loaded
			ItemStack stack = offhand ? user.getHeldItemOffhand() : user.getHeldItemMainhand();
			if(!stack.isEmpty()) {
				NBTTagList nbttaglist = stack.getEnchantmentTagList();
				for(int i = 0; i < nbttaglist.tagCount(); ++i) {
					int j = nbttaglist.getCompoundTagAt(i).getShort("id");
					int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
					if(Enchantment.getEnchantmentByID(j) != null) {
						Enchantment ench = Enchantment.getEnchantmentByID(j);
						if(ench instanceof EnchantmentBase) {
							EnchantmentBase enchant = (EnchantmentBase)ench;
							if(enchant.isEnabled()) enchant.onEntityDamagedAlt(user, target, stack, k);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Check if the stack trace contains a class
	 **/
	public static boolean stackTraceHasClass(String clazz) {
		for(StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			if(ste.getClassName().equals(clazz)) return true;
		}
		return false;
	}
}
