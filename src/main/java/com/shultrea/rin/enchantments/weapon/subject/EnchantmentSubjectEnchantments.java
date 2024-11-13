package com.shultrea.rin.enchantments.weapon.subject;

import com.shultrea.rin.Interfaces.ISubjectEnchantment;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentSubjectEnchantments extends EnchantmentBase implements ISubjectEnchantment {

	public final int damageType;
	public final int MATHEMATICS = 0;
	public final int SCIENCE = 1;
	public final int HISTORY = 2;
	public final int PHYSICS = 3;
	public final int ENGLISH = 4;
	public final int PE = 5;
	
	public EnchantmentSubjectEnchantments(String name, Rarity rarity, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
		this.damageType = damageTypeIn;
	}
	
	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case MATHEMATICS:
				return ModConfig.enabled.subjectMathematics;
			case SCIENCE:
				return ModConfig.enabled.subjectScience;
			case HISTORY:
				return ModConfig.enabled.subjectHistory;
			case PHYSICS:
				return false;
			case ENGLISH:
				return ModConfig.enabled.subjectEnglish;
			case PE:
				return ModConfig.enabled.subjectPE;
			default:
				return false;
		}
	}
	
	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case MATHEMATICS:
				return ModConfig.level.subjectMathematics;
			case SCIENCE:
				return ModConfig.level.subjectScience;
			case HISTORY:
				return ModConfig.level.subjectHistory;
			case PHYSICS:
				return 4;
			case ENGLISH:
				return ModConfig.level.subjectEnglish;
			case PE:
				return ModConfig.level.subjectPE;
			default:
				return 4;
		}
	}

	@Override
	public int getMinEnchantability(int level) {
		switch(this.damageType) {
			case MATHEMATICS:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectMathematics, level);
			case SCIENCE:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectScience, level);
			case HISTORY:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectHistory, level);
			case PHYSICS:
				return 0;
			case ENGLISH:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectEnglish, level);
			case PE:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectPE, level);
			default:
				return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch(this.damageType) {
			case MATHEMATICS:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectMathematics, level);
			case SCIENCE:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectScience, level);
			case HISTORY:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectHistory, level);
			case PHYSICS:
				return 0;
			case ENGLISH:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectEnglish, level);
			case PE:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectPE, level);
			default:
				return 0;
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		switch(this.damageType){
			case MATHEMATICS: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectMathematics, stack) && super.canApplyAtEnchantingTable(stack);
			case SCIENCE: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectScience, stack) && super.canApplyAtEnchantingTable(stack);
			case HISTORY: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectHistory, stack) && super.canApplyAtEnchantingTable(stack);
			case PHYSICS: return false;
			case ENGLISH: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectEnglish, stack) && super.canApplyAtEnchantingTable(stack);
			case PE: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectPE, stack) && super.canApplyAtEnchantingTable(stack);
			default:
				return false;
		}
	}

	@Override
	public boolean canApply(ItemStack stack){
		switch(this.damageType){
			case MATHEMATICS: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectMathematics, stack) && super.canApply(stack);
			case SCIENCE: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectScience, stack) && super.canApply(stack);
			case HISTORY: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectHistory, stack) && super.canApply(stack);
			case PHYSICS: return false;
			case ENGLISH: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectEnglish, stack) && super.canApply(stack);
			case PE: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectPE, stack) && super.canApply(stack);
			default:
				return false;
		}
	}

	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case MATHEMATICS:
				return ModConfig.treasure.subjectMathematics;
			case SCIENCE:
				return ModConfig.treasure.subjectScience;
			case HISTORY:
				return ModConfig.treasure.subjectHistory;
			case PHYSICS:
				return true;
			case ENGLISH:
				return ModConfig.treasure.subjectEnglish;
			case PE:
				return ModConfig.treasure.subjectPE;
			default:
				return true;
		}
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(target instanceof EntityLivingBase) {
			if(this.damageType == SCIENCE && ModConfig.enabled.subjectScience) {
				if(user.getRNG().nextFloat() < 0.2f) {
					user.getEntityWorld().newExplosion(user, target.posX, target.posY - 1.5D, target.posZ, 1.1f + (level * 0.4f), false, false);
				}
			}
			if(this.damageType == PHYSICS) {
				if(user.getRNG().nextFloat() < 0.0833) {
					user.getEntityWorld().newExplosion(user, target.posX, target.posY - 1.5D, target.posZ, 1.0f + (level * 0.65f), true, false);
					user.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 60, 0, true, true));
				}
			}
			if(this.damageType == PE && ModConfig.enabled.subjectPE) {
				if(user.getRNG().nextFloat() < 0.085f) {
					if(level <= 2) {
						user.addPotionEffect(new PotionEffect(MobEffects.HASTE, 150 + (level * 30), level - 1));
						user.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 + (level * 5), level - 1));
					}
					if(level >= 3) {
						user.addPotionEffect(new PotionEffect(MobEffects.HASTE, 150 + (level * 30), level - 2));
						user.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 + (level * 5), level - 2));
						user.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10 + (level * 5), level - 4));
						user.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 + (level * 5), level - 3));
					}
					if(level >= 5) {
						user.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 10 + (level * 5), level - 1));
						user.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20 + (level * 5), level - 2));
					}
				}
			}
		}
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		if(!isEnabled())
			return 0.0f;
		if(damageType == PE)
			return 0.75f + level * 0.25f;
		return 0.80f + level * 0.30f;
	}
}