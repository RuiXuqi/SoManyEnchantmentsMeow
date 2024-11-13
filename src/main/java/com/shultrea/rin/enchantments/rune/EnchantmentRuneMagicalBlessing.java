package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.Interfaces.IEnchantmentRune;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRuneMagicalBlessing extends EnchantmentBase implements IEnchantmentRune {
	
	public EnchantmentRuneMagicalBlessing(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runeMagicalBlessing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runeMagicalBlessing;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.runeMagicalBlessing, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.runeMagicalBlessing, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.runeMagicalBlessing, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.runeMagicalBlessing, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runeMagicalBlessing;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return (0.75f * level);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		EntityLivingBase victim = fEvent.getEntityLiving();
		if(victim == null) return;

		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.runeMagicalBlessing, attacker);
		if(enchantmentLevel <= 0) return;

		float damage = fEvent.getAmount();
		fEvent.setAmount(fEvent.getAmount() - fEvent.getAmount() * enchantmentLevel * 0.25f);
		damage *= enchantmentLevel * 0.25f;
		if(victim instanceof EntityWitch)
			damage *= 0.15f;
		UtilityAccessor.damageTargetEvent(victim, new EntityDamageSource("player", attacker).setMagicDamage(), damage);
		if(EnchantmentsUtility.RANDOM.nextBoolean()) {
			Potion negaPotion = EnchantmentsUtility.getNonInstantNegativePotion();
			if(negaPotion != null) {
				int duration = (EnchantmentsUtility.RANDOM.nextInt(6) + 1) * 20 * enchantmentLevel;
				int amplifier = EnchantmentsUtility.RANDOM.nextInt(enchantmentLevel) - 1;
				victim.addPotionEffect(new PotionEffect(negaPotion, duration, amplifier));
			}
		}
		else {
			Potion negaIPotion = EnchantmentsUtility.getInstantNegativePotion();
			if(negaIPotion != null) {
				if(negaIPotion == MobEffects.INSTANT_DAMAGE && fEvent.getEntityLiving().isEntityUndead())
					negaIPotion = MobEffects.INSTANT_HEALTH;
				int amplifier = EnchantmentsUtility.RANDOM.nextInt(enchantmentLevel) - 1;
				victim.addPotionEffect(new PotionEffect(negaIPotion, 1, amplifier));
			}
		}
	}
}