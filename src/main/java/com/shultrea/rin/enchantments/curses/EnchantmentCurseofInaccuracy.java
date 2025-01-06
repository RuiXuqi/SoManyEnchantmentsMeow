package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.base.EnchantmentCurse;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment arrow inaccuracy handled in com.shultrea.rin.mixin.vanilla.EntityArrowMixin
 */
public class EnchantmentCurseofInaccuracy extends EnchantmentCurse {
	
	public EnchantmentCurseofInaccuracy(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.curseOfInaccuracy;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.curseOfInaccuracy;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.curseOfInaccuracy, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.curseOfInaccuracy, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.curseOfInaccuracy, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.curseOfInaccuracy, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfInaccuracy;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		EntityLivingBase entity = (EntityLivingBase)event.getSource().getTrueSource();
		if(entity == null) return;
		if(entity.world.isRemote) return;
		ItemStack weapon = entity.getHeldItemMainhand();
		if(weapon.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, weapon);
		if(level > 0 && entity.getRNG().nextFloat() < ((float)level * 0.20F)) {
			event.setCanceled(true);
		}
	}
}