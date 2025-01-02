package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentFlinging extends EnchantmentBase {
	
	public EnchantmentFlinging(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.flinging;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.flinging;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.flinging, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.flinging, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.flinging, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.flinging, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.flinging;
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isOnEntityDamagedAltStrong()) return;
		if(attacker == null) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;
		
		if(!attacker.world.isRemote) {
			victim.isAirBorne = true;
			double knockback = 0.075D + 0.1875D * (double)level;
			victim.motionY += knockback;
			if(!Double.isFinite(victim.motionY)) victim.motionY = 0;
			victim.velocityChanged = true;
		}
	}
}