package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentInhumane extends EnchantmentBase {
	
	public EnchantmentInhumane(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.inhumane;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.inhumane;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.inhumane, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.inhumane, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.inhumane, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.inhumane, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.inhumane;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity entiti, ItemStack stack, int level) {
		if(!ModConfig.enabled.inhumane) return;
		if(!(entiti instanceof EntityLivingBase)) return;
		EntityLivingBase entity = (EntityLivingBase)entiti;
		if(entity.getCreatureAttribute() == EnumCreatureAttribute.ILLAGER) {
			entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 70 + (level * 10), 1));
		}
	}
	
	//TODO
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return creatureType == EnumCreatureAttribute.ILLAGER ? (float)level * 2.5F : 0.0f;
	}
}