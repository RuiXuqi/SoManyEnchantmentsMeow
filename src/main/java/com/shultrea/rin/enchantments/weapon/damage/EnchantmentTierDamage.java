package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentTierDamage extends EnchantmentBase {
	
	private static final int LESSSHARP = 0;
	private static final int ADVSHARP = 1;
	private static final int SUPSHARP = 2;
	private static final int LESSSMITE = 3;
	private static final int ADVSMITE = 4;
	private static final int SUPSMITE = 5;
	private static final int LESSBOA = 6;
	private static final int ADVBOA = 7;
	private static final int SUPBOA = 8;
	
	private final int damageType;
	
	public EnchantmentTierDamage(String name, Rarity rarity, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
		this.damageType = damageTypeIn;
	}
	
	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case LESSSHARP: return ModConfig.enabled.lesserSharpness;
			case ADVSHARP: return ModConfig.enabled.advancedSharpness;
			case SUPSHARP: return ModConfig.enabled.supremeSharpness;
			case LESSSMITE: return ModConfig.enabled.lesserSmite;
			case ADVSMITE: return ModConfig.enabled.advancedSmite;
			case SUPSMITE: return ModConfig.enabled.supremeSmite;
			case LESSBOA: return ModConfig.enabled.lesserBaneOfArthropods;
			case ADVBOA: return ModConfig.enabled.advancedBaneOfArthropods;
			case SUPBOA: return ModConfig.enabled.supremeBaneOfArthropods;
			default: return false;
		}
	}
	
	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case LESSSHARP: return ModConfig.level.lesserSharpness;
			case ADVSHARP: return ModConfig.level.advancedSharpness;
			case SUPSHARP: return ModConfig.level.supremeSharpness;
			case LESSSMITE: return ModConfig.level.lesserSmite;
			case ADVSMITE: return ModConfig.level.advancedSmite;
			case SUPSMITE: return ModConfig.level.supremeSmite;
			case LESSBOA: return ModConfig.level.lesserBaneOfArthropods;
			case ADVBOA: return ModConfig.level.advancedBaneOfArthropods;
			case SUPBOA: return ModConfig.level.supremeBaneOfArthropods;
			default: return 5;
		}
	}

	@Override
	public int getMinEnchantability(int level) {
		switch(this.damageType) {
			case LESSSHARP: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserSharpness, level);
			case ADVSHARP: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedSharpness, level);
			case SUPSHARP: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeSharpness, level);
			case LESSSMITE: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserSmite, level);
			case ADVSMITE: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedSmite, level);
			case SUPSMITE: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeSmite, level);
			case LESSBOA: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.lesserBaneOfArthropods, level);
			case ADVBOA: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedBaneOfArthropods, level);
			case SUPBOA: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.supremeBaneOfArthropods, level);
			default: return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch(this.damageType) {
			case LESSSHARP: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserSharpness, level);
			case ADVSHARP: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedSharpness, level);
			case SUPSHARP: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeSharpness, level);
			case LESSSMITE: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserSmite, level);
			case ADVSMITE: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedSmite, level);
			case SUPSMITE: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeSmite, level);
			case LESSBOA: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.lesserBaneOfArthropods, level);
			case ADVBOA: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedBaneOfArthropods, level);
			case SUPBOA: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.supremeBaneOfArthropods, level);
			default: return 0;
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		switch(this.damageType){
			case LESSSHARP: return ConfigProvider.canItemApply(ModConfig.canApply.lesserSharpness, stack) && super.canApplyAtEnchantingTable(stack);
			case ADVSHARP: return ConfigProvider.canItemApply(ModConfig.canApply.advancedSharpness, stack) && super.canApplyAtEnchantingTable(stack);
			case SUPSHARP: return ConfigProvider.canItemApply(ModConfig.canApply.supremeSharpness, stack) && super.canApplyAtEnchantingTable(stack);
			case LESSSMITE: return ConfigProvider.canItemApply(ModConfig.canApply.lesserSmite, stack) && super.canApplyAtEnchantingTable(stack);
			case ADVSMITE: return ConfigProvider.canItemApply(ModConfig.canApply.advancedSmite, stack) && super.canApplyAtEnchantingTable(stack);
			case SUPSMITE: return ConfigProvider.canItemApply(ModConfig.canApply.supremeSmite, stack) && super.canApplyAtEnchantingTable(stack);
			case LESSBOA: return ConfigProvider.canItemApply(ModConfig.canApply.lesserBaneOfArthropods, stack) && super.canApplyAtEnchantingTable(stack);
			case ADVBOA: return ConfigProvider.canItemApply(ModConfig.canApply.advancedBaneOfArthropods, stack) && super.canApplyAtEnchantingTable(stack);
			case SUPBOA: return ConfigProvider.canItemApply(ModConfig.canApply.supremeBaneOfArthropods, stack) && super.canApplyAtEnchantingTable(stack);
			default: return false;
		}
	}

	@Override
	public boolean canApply(ItemStack stack){
		switch(this.damageType){
			case LESSSHARP: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.lesserSharpness, stack) || super.canApply(stack);
			case ADVSHARP: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.advancedSharpness, stack) || super.canApply(stack);
			case SUPSHARP: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.supremeSharpness, stack) || super.canApply(stack);
			case LESSSMITE: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.lesserSmite, stack) || super.canApply(stack);
			case ADVSMITE: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.advancedSmite, stack) || super.canApply(stack);
			case SUPSMITE: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.supremeSmite, stack) || super.canApply(stack);
			case LESSBOA: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.lesserBaneOfArthropods, stack) || super.canApply(stack);
			case ADVBOA: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.advancedBaneOfArthropods, stack) || super.canApply(stack);
			case SUPBOA: return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.supremeBaneOfArthropods, stack) || super.canApply(stack);
			default: return false;
		}
	}

	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case LESSSHARP: return ModConfig.treasure.lesserSharpness;
			case ADVSHARP: return ModConfig.treasure.advancedSharpness;
			case SUPSHARP: return ModConfig.treasure.supremeSharpness;
			case LESSSMITE: return ModConfig.treasure.lesserSmite;
			case ADVSMITE: return ModConfig.treasure.advancedSmite;
			case SUPSMITE: return ModConfig.treasure.supremeSmite;
			case LESSBOA: return ModConfig.treasure.lesserBaneOfArthropods;
			case ADVBOA: return ModConfig.treasure.advancedBaneOfArthropods;
			case SUPBOA: return ModConfig.treasure.supremeBaneOfArthropods;
			default: return false;
		}
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		switch(damageType) {
			case LESSSHARP: return 0.25F + 0.25F * (float)level;
			case ADVSHARP: return 1.25F + 0.95F * (float)level;
			case SUPSHARP: return 4.0F + 1.6F * (float)level;
			case LESSSMITE: if(creatureType == EnumCreatureAttribute.UNDEAD) return 1.25F * (float)level;
			case ADVSMITE: if(creatureType == EnumCreatureAttribute.UNDEAD) return 3.25F * (float)level;
			case SUPSMITE: if(creatureType == EnumCreatureAttribute.UNDEAD) return 5.0F * (float)level;
			case LESSBOA: if(creatureType == EnumCreatureAttribute.ARTHROPOD) return 1.25F * (float)level;
			case ADVBOA: if(creatureType == EnumCreatureAttribute.ARTHROPOD) return 3.25F * (float)level;
			case SUPBOA: if(creatureType == EnumCreatureAttribute.ARTHROPOD) return 5.0F * (float)level;
			default: return 0;
		}
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(attacker == null) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;
		
		if(!attacker.world.isRemote) {
			if(this.damageType == ADVSMITE && victim.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
				int duration = 60 + attacker.getRNG().nextInt(15 * level);
				victim.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, duration, 0));
			}
			else if(this.damageType == SUPSMITE && victim.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
				int duration = 80 + attacker.getRNG().nextInt(20 * level);
				victim.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, duration, 1));
			}
			else if(this.damageType == LESSBOA && victim.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
				int duration = 10 + attacker.getRNG().nextInt(5 * level);
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, duration, 1));
			}
			else if(this.damageType == ADVBOA && victim.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
				int duration = 30 + attacker.getRNG().nextInt(15 * level);
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, duration, 4));
			}
			else if(this.damageType == SUPBOA && victim.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
				int duration = 40 + attacker.getRNG().nextInt(20 * level);
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, duration, 5));
			}
		}
	}
}