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

public class EnchantmentAdvancedThorns extends EnchantmentBase {

	public EnchantmentAdvancedThorns(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
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
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedThorns, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedThorns, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedThorns;
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
		if(shouldHit(lvl, random)) {
			attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float)getDamage(lvl, random));
			damageArmor(itemstack, 4 + random.nextInt(lvl), user);
		}
		else {
			damageArmor(itemstack, 2 + random.nextInt(lvl), user);
		}
	}
	
	private static boolean shouldHit(int level, Random rnd) {
		return level > 0 && rnd.nextFloat() < 0.05F + (0.20F * (float)level);
	}
	
	private static int getDamage(int level, Random rnd) {
		return 2 + level + rnd.nextInt(level + 3);
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