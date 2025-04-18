package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentDisorientatingBlade extends EnchantmentBase {
	
	public EnchantmentDisorientatingBlade(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.disorientatingBlade;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.disorientatingBlade;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.disorientatingBlade, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.disorientatingBlade, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.disorientatingBlade, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.disorientatingBlade, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.disorientatingBlade;
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(attacker == null) return;
		if(attacker.world.isRemote) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;

        if (CompatUtil.isRLCombatLoaded() && attacker.getRNG().nextFloat() > RLCombatCompat.getOnEntityDamagedAltStrength()) return;
        if(attacker.getRNG().nextFloat() <= 0.15F * (float)level) {
            victim.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 40 + (level * 10), level - 1));
            if(level > 2) victim.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40 + (level * 10), level - 3));
        }
    }
}