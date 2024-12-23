package com.shultrea.rin.enchantments.weapon.selfheal;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCombatVeterancy extends EnchantmentBase {

	public EnchantmentCombatVeterancy(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
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
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.combatVeterancy, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.combatVeterancy, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.combatVeterancy;
	}

	//TODO: onLivingDamage decrease overall dmg taken by like 15% or smth

	//TODO IDEAS: only while sneaking, at higher health trigger alr, two-handed/single-handed only (no offfhand)?

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingHeal(LivingHealEvent e) {
		EntityLivingBase user = e.getEntityLiving();
		if (user == null) return;
		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.combatVeterancy, user);
		if (enchantmentLevel > 0)
			//TODO: balance this against rejuvenation which is x2, x4 and x6 for lvl I, II, III respectively
			e.setAmount(e.getAmount() * (1.05f + 0.15f * enchantmentLevel));
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onPlayerTick(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase user = event.getEntityLiving();

		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.combatVeterancy, user);
		if (enchantmentLevel <= 0) return;

		//Every 4 / 3.5 / 3 seconds (for lvl I,II,III), heal for up to 0.5 health, reduce saturation slightly
		if (user.getEntityWorld().getTotalWorldTime() % (90 - Math.min(enchantmentLevel * 10, 80)) == 0) {
			if (user.getHealth() > user.getMaxHealth()) return;

			//TODO: regen 3 heals ~4.8 HP per 3 seconds, combat vet 3 heals 0.5 HP (x1.5) per 3 seconds
			user.heal(0.15f * enchantmentLevel + 0.05f);
			if (user instanceof EntityPlayer)
				((EntityPlayer) user).getFoodStats().addStats(((EntityPlayer) user).getFoodStats().getFoodLevel(), -0.05f);
		}
		//Every 2 seconds, gives absorption hearts for up to 0,5,10 extra absorption hearts, not running out.
		//Slowly heals used up absorption potion effect hearts.
		//Takes about 25 seconds to fully heal the absorption hearts.

		//TODO: maybe like leather armor ticking it every tick but low absorption?
		if (user.getEntityWorld().getTotalWorldTime() % 40 == 0 && enchantmentLevel >= 3) {
			float absorptionLevel = 0.0f;
			if (user.isPotionActive(MobEffects.ABSORPTION))
				absorptionLevel = user.getActivePotionEffect(MobEffects.ABSORPTION).getAmplifier() + 1;

			float absorptionMax = (enchantmentLevel - 1) * 10 + absorptionLevel * 4;

			if (user.getAbsorptionAmount() >= absorptionMax) return;

			if(!user.isSneaking()) return;
			if (user.getHealth() >= user.getMaxHealth()*0.9F) {
				if (user.getAbsorptionAmount() < (enchantmentLevel - 1) * 10)
					user.setAbsorptionAmount(Math.min(absorptionMax,user.getAbsorptionAmount() + 0.20f + (absorptionMax * 0.075f)));
			}
		}
	}
}