package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.Interfaces.IEnchantmentDamage;
import com.shultrea.rin.Interfaces.IEnhancedEnchantment;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentTierDamage extends EnchantmentBase implements IEnchantmentDamage, IEnhancedEnchantment {
	
	public final int damageType;
	public static final int LESSSHARP = 0;
	public static final int SUPSHARP = 1;
	public static final int LESSSMITE = 2;
	public static final int SUPSMITE = 3;
	public static final int LESSBOA = 4;
	public static final int SUPBOA = 5;
	
	public EnchantmentTierDamage(String name, Rarity rarity, EnumEnchantmentType type, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
		this.damageType = damageTypeIn;
		this.type = type;
	}
	
	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case LESSSHARP:
				return ModConfig.enabled.lesserSharpness;
			case SUPSHARP:
				return ModConfig.enabled.supremeSharpness;
			case LESSSMITE:
				return ModConfig.enabled.lesserSmite;
			case SUPSMITE:
				return ModConfig.enabled.supremeSmite;
			case LESSBOA:
				return ModConfig.enabled.lesserBaneOfArthropods;
			case SUPBOA:
				return ModConfig.enabled.supremeBaneOfArthropods;
			default:
				return false;
		}
	}
	
	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case LESSSHARP:
				return ModConfig.level.lesserSharpness;
			case SUPSHARP:
				return ModConfig.level.supremeSharpness;
			case LESSSMITE:
				return ModConfig.level.lesserSmite;
			case SUPSMITE:
				return ModConfig.level.supremeSmite;
			case LESSBOA:
				return ModConfig.level.lesserBaneOfArthropods;
			case SUPBOA:
				return ModConfig.level.supremeBaneOfArthropods;
			default:
				return 5;
		}
	}

	@Override
	public int getMinEnchantability(int level) {
		switch(this.damageType) {
			case LESSSHARP:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserSharpness, level);
			case SUPSHARP:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeSharpness, level);
			case LESSSMITE:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserSmite, level);
			case SUPSMITE:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeSmite, level);
			case LESSBOA:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserBaneOfArthropods, level);
			case SUPBOA:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeBaneOfArthropods, level);
			default:
				return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch(this.damageType) {
			case LESSSHARP:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserSharpness, level);
			case SUPSHARP:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeSharpness, level);
			case LESSSMITE:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserSmite, level);
			case SUPSMITE:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeSmite, level);
			case LESSBOA:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserBaneOfArthropods, level);
			case SUPBOA:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeBaneOfArthropods, level);
			default:
				return 0;
		}
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ItemAxe || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case LESSSHARP:
				return ModConfig.treasure.lesserSharpness;
			case SUPSHARP:
				return ModConfig.treasure.supremeSharpness;
			case LESSSMITE:
				return ModConfig.treasure.lesserSmite;
			case SUPSMITE:
				return ModConfig.treasure.supremeSmite;
			case LESSBOA:
				return ModConfig.treasure.lesserBaneOfArthropods;
			case SUPBOA:
				return ModConfig.treasure.supremeBaneOfArthropods;
			default:
				return false;
		}
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(target instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase) target;
			if (entitylivingbase.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
				if (this.damageType == LESSBOA) {
					int duration = 10 + user.getRNG().nextInt(5 * level);
					entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, duration, 1));
				}
				if (this.damageType == SUPBOA) {
					int duration = 40 + user.getRNG().nextInt(20 * level);
					entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, duration, 5));
				}
			}
		}
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		switch(damageType) {
			case LESSSHARP: return 0.25f + level * 0.25f;
			case SUPSHARP: return 4.0f + level * 1.6f;
			case LESSSMITE: if(creatureType == EnumCreatureAttribute.UNDEAD) return 1.25f * level;
			case SUPSMITE: if(creatureType == EnumCreatureAttribute.UNDEAD) return 5.00f * level;
			case LESSBOA: if(creatureType == EnumCreatureAttribute.ARTHROPOD) return 1.25f * level;
			case SUPBOA: if(creatureType == EnumCreatureAttribute.ARTHROPOD) return 5.0f * level;
			default: return 0;
		}
	}
}