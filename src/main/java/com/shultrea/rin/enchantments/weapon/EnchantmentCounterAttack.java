package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentCounterAttack extends EnchantmentBase {
	
	public EnchantmentCounterAttack(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.counterAttack;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.counterAttack;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.counterAttack, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.counterAttack, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.counterAttack;
	}
	
	//TODO
	@Override
	public void onUserHurt(EntityLivingBase user, Entity target, int level) {
		//TODO figure out how the crash works
		if(target instanceof EntityLivingBase) {
			EntityLivingBase attacker = (EntityLivingBase)target;
			if(user instanceof EntityPlayer) {
				if(attacker.getRNG().nextInt(100) < 20 + (level * 5)) {
					EntityPlayer player = (EntityPlayer)user;
					player.hurtResistantTime = player.maxHurtResistantTime;
					player.attackTargetEntityWithCurrentItem(attacker);
				}
			}
		}
	}
}