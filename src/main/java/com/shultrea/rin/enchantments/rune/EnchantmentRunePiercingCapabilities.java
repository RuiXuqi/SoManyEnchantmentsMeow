package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.IEntityDamageSourceMixin;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRunePiercingCapabilities extends EnchantmentBase {
	
	public EnchantmentRunePiercingCapabilities(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runePiercingCapabilities;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runePiercingCapabilities;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.runePiercingCapabilities, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.runePiercingCapabilities, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.runePiercingCapabilities, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.runePiercingCapabilities, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runePiercingCapabilities;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isAttackEntityFromStrong()) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		if(event.getSource().isUnblockable()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, attacker.getHeldItemMainhand());
		if(level > 0) {
			if(event.getSource() instanceof EntityDamageSource) {
				float percent = 0.25F * (float)level;
				((IEntityDamageSourceMixin)event.getSource()).soManyEnchantments$setPiercingPercent(percent);
			}
		}
	}
}