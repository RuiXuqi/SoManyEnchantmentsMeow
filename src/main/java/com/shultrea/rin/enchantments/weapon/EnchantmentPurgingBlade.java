package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.ReflectionUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentPurgingBlade extends EnchantmentBase {

	public EnchantmentPurgingBlade(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}

	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.purgingBlade;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.purgingBlade;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.purgingBlade, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.purgingBlade, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return ModConfig.canApply.isItemValid(ModConfig.canApply.purgingBlade, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.purgingBlade, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.purgingBlade;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onLivingHurt(LivingHurtEvent fEvent) {
		if (!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase) fEvent.getSource().getTrueSource();
		if (attacker == null) return;
		ItemStack itemStack = attacker.getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.purgingBlade, itemStack);
		if (enchantmentLevel <= 0) return;

		if (attacker.getRNG().nextFloat() <= 0.1F + 0.06F * enchantmentLevel) {
			ReflectionUtil.damageEntityNoEvent(fEvent.getEntityLiving(), DamageSource.MAGIC, 1.25F + 0.75F * enchantmentLevel);
			fEvent.setAmount(fEvent.getAmount() * 1.2F);

			EntityLivingBase victim = fEvent.getEntityLiving();

			for (PotionEffect potionEffect : victim.getActivePotionEffects()) {
				//Removes on average one effect, but can be anything between 0 and all of them
				if (attacker.getRNG().nextInt(victim.getActivePotionEffects().size()) == 0)
					//TODO: increase dmg if removing a dmg increasing debuff to somewhat mimic old buggy behavior but not as insane
					victim.removePotionEffect(potionEffect.getPotion());
			}
		}
	}
}