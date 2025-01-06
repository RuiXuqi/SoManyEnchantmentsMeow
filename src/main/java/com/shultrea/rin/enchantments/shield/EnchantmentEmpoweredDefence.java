package com.shultrea.rin.enchantments.shield;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentEmpoweredDefence extends EnchantmentBase {
	
	public EnchantmentEmpoweredDefence(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.empoweredDefence;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.empoweredDefence;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.empoweredDefence, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.empoweredDefence, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.empoweredDefence, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.empoweredDefence, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.empoweredDefence;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		if(victim.world.isRemote) return;
		if(!(event.getSource().getImmediateSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getImmediateSource();
		if(!EnchantUtil.canBlockDamageSource(event.getSource(), victim)) return;
		
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, victim);
		if(level > 0 && victim.getRNG().nextFloat() < 0.2F + 0.05F * (float)level) {
			if(!victim.world.isRemote) {
				EnchantUtil.knockBackIgnoreKBRes(attacker, 0.4F + 0.2F * (float)level, victim.posX - attacker.posX, victim.posZ - attacker.posZ);
			}
			float revengeDamage = event.getAmount() * 0.225F * (float)level;
			attacker.attackEntityFrom(new EntityDamageSource(victim instanceof EntityPlayer ? "player" : "mob", victim).setFireDamage(), revengeDamage);
			event.setCanceled(true);
			victim.hurtResistantTime = victim.maxHurtResistantTime - 5;
		}
	}
}