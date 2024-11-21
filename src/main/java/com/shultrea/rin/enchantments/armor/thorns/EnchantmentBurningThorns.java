package com.shultrea.rin.enchantments.armor.thorns;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

import java.util.Random;

public class EnchantmentBurningThorns extends EnchantmentBase {
	
	public EnchantmentBurningThorns(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
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
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.burningThorns, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.burningThorns, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.burningThorns;
	}
	
	@Override
	public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
		Random random = user.getRNG();
		ItemStack itemstack = EnchantmentHelper.getEnchantedItem(this, user);
		if(itemstack.isEmpty()) return;
		//Use the level of the actual stack being used
		int lvl = EnchantmentHelper.getEnchantmentLevel(this, itemstack);
		//Shouldn't ever be 0 but just incase weirdness
		if(lvl <= 0) return;
		if(shouldHit(level, random)) {
			attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float)getDamage(level, random));
			attacker.setFire((level) + 2);
			damageArmor(itemstack, 4, user);
		}
		else {
			damageArmor(itemstack, 2, user);
		}
	}
	
	private static boolean shouldHit(int level, Random rnd) {
		return level > 0 && rnd.nextFloat() < (0.15F * (float)level);
	}
	
	private static int getDamage(int level, Random rnd) {
		return 2 + rnd.nextInt(4);
	}
	
	private static void damageArmor(ItemStack stack, int amount, EntityLivingBase entity) {
		int slot = -1;
		int x = 0;
		for(ItemStack i : entity.getArmorInventoryList()) {
			if(i == stack) {
				slot = x;
				break;
			}
			x++;
		}
		if(slot == -1 || !(stack.getItem() instanceof ISpecialArmor)) {
			stack.damageItem(amount, entity);
			return;
		}
		ISpecialArmor armor = (ISpecialArmor)stack.getItem();
		armor.damageArmor(entity, stack, DamageSource.causeThornsDamage(entity), amount, slot);
	}
}