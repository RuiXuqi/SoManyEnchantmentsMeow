package com.shultrea.rin.enchantments.tier;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_030;
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
	
	public EnchantmentAdvancedThorns(String name, Rarity rarity, EnumEnchantmentType type) {
		super(Rarity.RARE, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS,
				EntityEquipmentSlot.FEET,});
		this.setName("AdvancedThorns");
		this.setRegistryName("AdvancedThorns");
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
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 16 + (par1 - 1) * 12;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedThorns;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Smc_030.BurningThorns && fTest != Enchantments.THORNS && super.canApplyTogether(fTest);
	}
	
	//TODO
	@Override
	public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
		if(user == null) return;
		if(attacker == null || attacker.isDead) return;
		Random random = user.getRNG();
		ItemStack itemstack = EnchantmentHelper.getEnchantedItem(Smc_030.AdvancedThorns, user);
		if(shouldHit(level, random)) {
			if(attacker != null) {
				attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), (float)getDamage(level, random) - 1);
			}
			if(!itemstack.isEmpty()) {
				damageArmor(itemstack, 4 + random.nextInt(6), user);
			}
		}
		else if(!itemstack.isEmpty()) {
			damageArmor(itemstack, 2 + random.nextInt(2), user);
		}
	}
	
	//TODO
	private void damageArmor(ItemStack stack, int amount, EntityLivingBase entity) {
		int slot = -1;
		int x = 0;
		for(ItemStack i : entity.getArmorInventoryList()) {
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