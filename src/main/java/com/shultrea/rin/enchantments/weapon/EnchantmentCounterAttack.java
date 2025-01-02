package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCounterAttack extends EnchantmentBase {
	
	private boolean handlingCounterAttack = false;
	
	public EnchantmentCounterAttack(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.counterAttack;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.counterAttack;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.counterAttack, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.counterAttack, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.counterAttack, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.counterAttack, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.counterAttack;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		if(event.getSource().isProjectile()) return;
		if(!(event.getEntityLiving() instanceof EntityPlayer)) return;
		EntityPlayer victim = (EntityPlayer)event.getEntityLiving();
		if(!(event.getSource().getImmediateSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getImmediateSource();
		
		//Attempt to avoid recursion
		if(this.handlingCounterAttack) return;
		
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, victim);
		if(level > 0) {
			if(victim.getRNG().nextFloat() < 0.05F + 0.05F * (float)level) {
				this.handlingCounterAttack = true;
				victim.attackTargetEntityWithCurrentItem(attacker);
				this.handlingCounterAttack = false;
			}
		}
	}
}