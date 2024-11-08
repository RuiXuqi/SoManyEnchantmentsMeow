package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCombatVeterancy extends EnchantmentBase {

	public EnchantmentCombatVeterancy(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}

	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.combatVeterancy;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.combatVeterancy;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.combatVeterancy, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.combatVeterancy, level);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.combatVeterancy;
	}

	//TODO
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onRegen(LivingHealEvent e) {
		EntityLivingBase user = e.getEntityLiving();
		if (user == null) return;
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.combatVeterancy, user);
		if (enchantmentLevel > 0)
			e.setAmount(e.getAmount() * (1.05f + 0.15f * enchantmentLevel));
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onPlayerTick(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase user = event.getEntityLiving();

		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.combatVeterancy, user);
		if (enchantmentLevel <= 0) return;

		if (user.getEntityWorld().getTotalWorldTime() % (90 - Math.min(enchantmentLevel * 10, 80)) == 0) {
			if (user.getHealth() > user.getMaxHealth()) return;

			user.heal(0.15f * enchantmentLevel + 0.05f);
			if (user instanceof EntityPlayer)
				((EntityPlayer) user).getFoodStats().addStats(((EntityPlayer) user).getFoodStats().getFoodLevel(), -0.05f);
		}
		if (user.getEntityWorld().getTotalWorldTime() % 40 == 0 && enchantmentLevel >= 3) {
			float absorptionLevel = 0.0f;
			if (user.isPotionActive(MobEffects.ABSORPTION))
				absorptionLevel = user.getActivePotionEffect(MobEffects.ABSORPTION).getAmplifier() + 1;

			float absorptionMax = (enchantmentLevel - 1) * 10 + absorptionLevel * 4;

			if (user.getAbsorptionAmount() >= absorptionMax) return;

			if (user.getHealth() == user.getMaxHealth()) {
				if (user.getAbsorptionAmount() < (enchantmentLevel - 1) * 10)
					user.setAbsorptionAmount(user.getAbsorptionAmount() + 0.20f + (absorptionMax * 0.075f));

				if (user.getAbsorptionAmount() > absorptionMax)
					user.setAbsorptionAmount(absorptionMax);
			}
		}
	}
}