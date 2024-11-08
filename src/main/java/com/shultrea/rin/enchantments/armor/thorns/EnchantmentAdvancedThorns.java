package com.shultrea.rin.enchantments.armor.thorns;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.Random;

public class EnchantmentAdvancedThorns extends EnchantmentBase {

	public EnchantmentAdvancedThorns(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	//TODO
	public static boolean shouldHit(int level, Random rnd) {
		return level > 0 && rnd.nextFloat() < 0.05f + (0.20F * (float)level);
	}
	
	//TODO
	public static int getDamage(int level, Random rnd) {
		return level > 5 ? ((level)) + rnd.nextInt(2 * level) : ((level) + 2) + rnd.nextInt(level + 3);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedThorns;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedThorns;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedThorns, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedThorns, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedThorns;
	}
	
	//TODO
	@Override
	public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
		if(user == null) return;
		if(attacker == null || attacker.isDead) return;
		Random random = user.getRNG();
		ItemStack itemstack = EnchantmentHelper.getEnchantedItem(EnchantmentRegistry.advancedThorns, user);
		if(itemstack.isEmpty()) return;
		if(shouldHit(level, random)) {
			attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float)getDamage(level, random) - 1);
			damageArmor(itemstack, 4 + random.nextInt(6), user);
		}
		else
			damageArmor(itemstack, 2 + random.nextInt(2), user);
	}
	
	//TODO
	private void damageArmor(ItemStack stack, int amount, EntityLivingBase entity) {
		/**
		 * Nischi:
		 * above in onUserHurt we're getting a random item from the specified slots via getEnchantedItem (head,chest,legs,feet), here we search for that slot again but only in armor slots (getArmorInventoryList)
		 * Although this is ripped straight out of forge EnchantmentThorns.java, that code assumes vanilla behavior (thorns only on armor)
		 * I would just search for the stack in the whole equipment list (getEquipmentAndArmor)
		**/
		int slot = -1;
		int x = 0;
		for(ItemStack i : entity.getEquipmentAndArmor()) {
			if(i == stack) {
				slot = x;
				break;
			}
			x++;
		}
		if(slot == -1 || !(stack.getItem() instanceof net.minecraftforge.common.ISpecialArmor)) {
			stack.damageItem(2, entity);
			return;
		}
		net.minecraftforge.common.ISpecialArmor armor = (net.minecraftforge.common.ISpecialArmor)stack.getItem();
		armor.damageArmor(entity, stack, DamageSource.causeThornsDamage(entity), amount, slot);
	}
}