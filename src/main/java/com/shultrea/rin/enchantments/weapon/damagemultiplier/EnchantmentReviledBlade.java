package com.shultrea.rin.enchantments.weapon.damagemultiplier;

import com.shultrea.rin.Interfaces.IDamageMultiplier;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentReviledBlade extends EnchantmentBase implements IDamageMultiplier {
	
	public EnchantmentReviledBlade(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.reviledBlade;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.reviledBlade;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.reviledBlade, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.reviledBlade, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.reviledBlade;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HandleEnchant(LivingDamageEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack weapon = attacker.getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.reviledBlade, weapon);
		if(enchantmentLevel > 0) {
			float defenderHealthPercent = fEvent.getEntityLiving().getHealth() / fEvent.getEntityLiving().getMaxHealth();
			float dmgMod = (1.0f - defenderHealthPercent) * ((enchantmentLevel * 0.1f) + 0.9f);
			dmgMod = 1.0F + dmgMod;
			float Damage = fEvent.getAmount();
			//UtilityAccessor.damageEntity(fEvent.getEntityLiving(), somanyenchantments.PhysicalDamage, (dmgMod * Damage) - 0.001f);
			fEvent.setAmount(dmgMod * Damage);
		}
	}
}