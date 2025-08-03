package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;

public class EnchantmentHorsDeCombat extends EnchantmentBase {
	
	public EnchantmentHorsDeCombat(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.horsDeCombat;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.horsDeCombat;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.horsDeCombat, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.horsDeCombat, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.horsDeCombat, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.horsDeCombat, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.horsDeCombat;
	}

	private static final List<Potion> effects = Arrays.asList(
			MobEffects.SLOWNESS,
			MobEffects.MINING_FATIGUE,
			MobEffects.HUNGER,
			MobEffects.WEAKNESS,
			MobEffects.BLINDNESS,
			MobEffects.NAUSEA,
			MobEffects.WITHER,
			MobEffects.POISON
	);
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(attacker == null) return;
		if(attacker.world.isRemote) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;

		if (CompatUtil.isRLCombatLoaded() && attacker.getRNG().nextFloat() > RLCombatCompat.getOnEntityDamagedAltStrength()) return;
		if (attacker.getRNG().nextFloat() <= 0.2F * (float) level) {
            int index = attacker.getRNG().nextInt(effects.size());
			victim.addPotionEffect(new PotionEffect(effects.get(index), 20 + level * 10, level - 1));
        }
    }
}