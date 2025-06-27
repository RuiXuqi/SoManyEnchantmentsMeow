package com.shultrea.rin.enchantments.shield;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
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
		return ConfigProvider.canItemApply(ModConfig.canApply.empoweredDefence, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.empoweredDefence, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.empoweredDefence;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		if(event.getSource().isProjectile()) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		if(victim.world.isRemote) return;
		if(!(event.getSource().getImmediateSource() instanceof EntityLivingBase)) return;
		//Only affect actual attacks
		if(!"player".equals(event.getSource().damageType) && !"mob".equals(event.getSource().damageType)) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getImmediateSource();
		if(!EnchantUtil.canBlockDamageSource(event.getSource(), victim)) return;
		
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, victim);
		if(level > 0 && victim.getRNG().nextFloat() < 0.2F + 0.05F * (float)level) {
			float revengeDamage = event.getAmount() * 0.225F * (float)level;
			//Non-armor-piercing magic damage reflection
			attacker.attackEntityFrom(new EntityDamageSource("magic", victim).setMagicDamage(), revengeDamage);
			EnchantUtil.knockBackIgnoreKBRes(attacker, 0.4F + 0.2F * (float)level, victim.posX - attacker.posX, victim.posZ - attacker.posZ);
			event.setCanceled(true);
			victim.hurtResistantTime = victim.maxHurtResistantTime - 5;
		}
	}
}