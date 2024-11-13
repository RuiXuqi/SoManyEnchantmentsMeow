package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentCounterAttack extends EnchantmentBase {
	
	public EnchantmentCounterAttack(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
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
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.counterAttack, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.counterAttack, stack) && super.canApply(stack);
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