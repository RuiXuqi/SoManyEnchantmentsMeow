package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.EnchantmentHelperMixin
 */
public class EnchantmentFieryEdge extends EnchantmentBase {
	
	public EnchantmentFieryEdge(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	public static int getLevelValue(EntityLivingBase entity) {
		if(!EnchantmentRegistry.fieryEdge.isEnabled()) return 0;
		if(entity == null) return 0;
		ItemStack stack = entity.getHeldItemMainhand();
		if(CompatUtil.isRLCombatLoaded()) stack = RLCombatCompat.getFireAspectStack(entity);
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.fieryEdge, stack);
		if(level <= 0) return 0;
		return getLevelMult(level);
	}
	
	public static int getLevelMult(int level) {
		return 2 * level;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.fieryEdge;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.fieryEdge;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.fieryEdge, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.fieryEdge, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.fieryEdge, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.fieryEdge, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.fieryEdge;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isAttackEntityFromStrong()) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		if(attacker.world.isRemote) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(attacker.world.isRemote) return;
			if(victim.isBurning() && (float)victim.hurtResistantTime > (float)victim.maxHurtResistantTime / 2.0F) {
				if(attacker.getRNG().nextFloat() < 0.05F * (float)level) {
					victim.hurtResistantTime = 0;
				}
			}
		}
	}
}