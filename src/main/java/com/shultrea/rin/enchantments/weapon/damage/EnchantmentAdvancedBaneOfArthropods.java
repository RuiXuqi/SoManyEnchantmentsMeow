package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class EnchantmentAdvancedBaneOfArthropods extends EnchantmentBase {
	
	public EnchantmentAdvancedBaneOfArthropods(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedBaneOfArthropods;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedBaneOfArthropods;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedBaneOfArthropods, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedBaneOfArthropods, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedBaneOfArthropods, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedBaneOfArthropods, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedBaneOfArthropods;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity entiti, ItemStack stack, int level) {
		if(entiti instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase)entiti;
			if(entity.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
				int i = 30 + user.getRNG().nextInt(15 * level);
				entity.addPotionEffect(new PotionEffect(Potion.getPotionById(2), i, 4));
			}
		}
	}
	
	//TODO
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return creatureType == EnumCreatureAttribute.ARTHROPOD ? (float)level * 3.25F : 0.0f;
	}
}