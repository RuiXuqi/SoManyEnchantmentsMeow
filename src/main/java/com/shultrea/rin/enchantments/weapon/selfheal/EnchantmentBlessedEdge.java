package com.shultrea.rin.enchantments.weapon.selfheal;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.LycanitesMobsCompat;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
		return ConfigProvider.canItemApply(ModConfig.canApply.blessedEdge, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.blessedEdge, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.blessedEdge;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			Potion smitedEffect = null;
			if(CompatUtil.isLycanitesMobsLoaded())
				if(!CompatUtil.isRLCombatLoaded() || attacker.getRNG().nextFloat() < RLCombatCompat.getAttackEntityFromStrength())
					smitedEffect = LycanitesMobsCompat.getSmitedPotion();
			if(smitedEffect != null) victim.addPotionEffect(new PotionEffect(smitedEffect, 200, 0));
			
			if(victim.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
				attacker.heal(event.getAmount() * 0.1F * (float)level);
				event.setAmount(event.getAmount() * (1.0F + 0.1F * (float)level));
			}
		}
	}
}