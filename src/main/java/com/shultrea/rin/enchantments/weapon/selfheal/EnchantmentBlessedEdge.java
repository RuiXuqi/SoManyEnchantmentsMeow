package com.shultrea.rin.enchantments.weapon.selfheal;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EnchantmentBlessedEdge extends EnchantmentBase {

	public EnchantmentBlessedEdge(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}

	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.blessedEdge;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.blessedEdge;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.blessedEdge, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.blessedEdge, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return ModConfig.canApply.isItemValid(ModConfig.canApply.blessedEdge, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.blessedEdge, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.blessedEdge;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onLivingHurt(LivingHurtEvent fEvent) {
		if (!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase) fEvent.getSource().getTrueSource();
		if (attacker == null) return;
		ItemStack itemStack = ((EntityLivingBase) fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.blessedEdge, itemStack);
		if (enchantmentLevel <= 0) return;

		EntityLivingBase victim = fEvent.getEntityLiving();

		Potion smited = null;
		if (CompatUtil.isLycanitesMobsLoaded())
			smited = ForgeRegistries.POTIONS.getValue(new ResourceLocation("lycanitesmobs", "smited"));

		if (smited != null)
			victim.addPotionEffect(new PotionEffect(smited, 200, 0));

		if (victim.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD || (victim.isBurning() && (smited == null || victim.isPotionActive(smited)))) {
			float damage = fEvent.getAmount();
			attacker.heal(damage * enchantmentLevel * 0.03f);
			float modifiedDamage = EnchantUtil.modifyDamage(damage, 1.00f, 0.60f, 1 + 0.04f * enchantmentLevel, enchantmentLevel);
			fEvent.setAmount(modifiedDamage);
		}
	}
}