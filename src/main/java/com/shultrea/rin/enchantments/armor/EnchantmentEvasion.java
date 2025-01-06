package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentEvasion extends EnchantmentBase {
	
	public EnchantmentEvasion(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.evasion;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.evasion;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.evasion, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.evasion, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.evasion, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.evasion, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.evasion;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		if(event.getSource().isProjectile()) return;
		if(event.getEntityLiving() == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		if(victim.world.isRemote) return;
		if(!(event.getSource().getImmediateSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getImmediateSource();
		
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, victim);
		if(level > 0) {
			if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.trueStrike, attacker.getHeldItemMainhand()) > 0) return;
			
			if(victim.getRNG().nextFloat() < 0.05F + ((float)level * 0.15F)) {
				if(!victim.world.isRemote && ModConfig.miscellaneous.evasionDodgeEffect) {
					double randX = 0.65 + victim.getRNG().nextDouble() * 0.25f;
					randX = victim.getRNG().nextBoolean() ? randX * -1 : randX;
					double randZ = 0.65 + victim.getRNG().nextDouble() * 0.25f;
					randZ = victim.getRNG().nextBoolean() ? randZ * -1 : randZ;
					EnchantUtil.knockBackIgnoreKBRes(victim, 0.7f, (attacker.posX - victim.posX) * randX, (attacker.posZ - victim.posZ) * randZ);
				}
				victim.getEntityWorld().playSound(null, victim.posX, victim.posY, victim.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 0.3f, victim.getRNG().nextFloat() * 2.25f + 0.75f);
				event.setCanceled(true);
				if((float)victim.hurtResistantTime <= (float)victim.maxHurtResistantTime / 2.0F) {
					victim.hurtResistantTime = victim.maxHurtResistantTime + (5 * (level - 1));
				}
			}
		}
	}
}