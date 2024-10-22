package com.shultrea.rin.enchantments.armor.thorns;

import com.shultrea.rin.Main_Sector.EnchantabilityConfig;
import com.shultrea.rin.Main_Sector.ModConfig;
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

public class EnchantmentBurningThorns extends EnchantmentBase {
	
	public EnchantmentBurningThorns(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots) {
		super(name, rarity, type, slots);
	}
	
	//TODO
	public static boolean shouldHit(int level, Random rnd) {
		return level > 0 && rnd.nextFloat() < 0.14F * (float)level;
	}
	
	//TODO
	public static int getDamage(int level, Random rnd) {
		return level > 10 ? level - 9 : 2 + rnd.nextInt(3);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.burningThorns;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.burningThorns;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.burningThorns, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.burningThorns, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.burningThorns;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != EnchantmentRegistry.advancedThorns && fTest != Enchantments.THORNS && super.canApplyTogether(fTest);
	}
	
	//TODO
	@Override
	public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
		if(user == null) return;
		if(attacker == null || attacker.isDead) return;
		Random random = user.getRNG();
		ItemStack itemstack = EnchantmentHelper.getEnchantedItem(EnchantmentRegistry.burningThorns, user);
		if(itemstack.isEmpty()) return;
		if(shouldHit(level, random)) {
			attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float)getDamage(level, random));
			attacker.setFire((level) + 2);

			damageArmor(itemstack, 5, user);
		}
		else
			damageArmor(itemstack, 2, user);
	}
	
	//TODO
	private void damageArmor(ItemStack stack, int amount, EntityLivingBase entity) {
		int slot = -1;
		int x = 0;
		for(ItemStack i : entity.getEquipmentAndArmor()) {	//Swapped from getArmorInventoryList, same comment as Adv Thorns
			if(i == stack) {
				slot = x;
				break;
			}
			x++;
		}
		if(slot == -1 || !(stack.getItem() instanceof net.minecraftforge.common.ISpecialArmor)) {
			stack.damageItem(1, entity);
			return;
		}
		net.minecraftforge.common.ISpecialArmor armor = (net.minecraftforge.common.ISpecialArmor)stack.getItem();
		armor.damageArmor(entity, stack, DamageSource.causeThornsDamage(entity), amount, slot);
	}
}