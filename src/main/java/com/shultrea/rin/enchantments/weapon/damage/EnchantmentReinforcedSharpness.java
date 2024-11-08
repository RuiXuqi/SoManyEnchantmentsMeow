package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.Interfaces.IEnchantmentDamage;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;

public class EnchantmentReinforcedSharpness extends EnchantmentBase implements IEnchantmentDamage {
	
	public EnchantmentReinforcedSharpness(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.reinforcedsharpness;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.reinforcedsharpness;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.reinforcedsharpness, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.reinforcedsharpness, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.reinforcedsharpness;
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level) {
		if(ModConfig.enabled.reinforcedsharpness && target instanceof EntityLivingBase && user instanceof EntityPlayer) {
			EntityLivingBase victim = (EntityLivingBase)target;
			int x = victim.getTotalArmorValue();
			if(x > 20) x = 20;
			if(level >= 9) level = 9;
			if(!victim.isSilent()) {
				victim.setSilent(true);
				victim.hurtResistantTime = 0;
				victim.attackEntityFrom(new EntityDamageSource("player", user), (20 - x) / (10 - level));
				victim.setSilent(false);
			}
			else victim.attackEntityFrom(new EntityDamageSource("player", user), (20 - x) / (10 - level));
		}
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		if(ModConfig.enabled.reinforcedsharpness) return (0.75f * level + 0.5f);
		return 0;
	}
}