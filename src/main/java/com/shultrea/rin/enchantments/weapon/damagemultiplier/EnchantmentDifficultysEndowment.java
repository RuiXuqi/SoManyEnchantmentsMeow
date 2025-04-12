package com.shultrea.rin.enchantments.weapon.damagemultiplier;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDifficultysEndowment extends EnchantmentBase {
	
	public EnchantmentDifficultysEndowment(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.difficultysEndowment;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.difficultysEndowment;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.difficultysEndowment, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.difficultysEndowment, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.difficultysEndowment, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.difficultysEndowment, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.difficultysEndowment;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingDamageEvent(LivingDamageEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(attacker.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
				event.setAmount(event.getAmount() * 0.1F * (float)level);
			}
			else if(attacker.world.getDifficulty() == EnumDifficulty.EASY) {
				event.setAmount(event.getAmount() * (0.25F + 0.1F * (float)level));
			}
			else if(attacker.world.getDifficulty() == EnumDifficulty.HARD) {
				if(!attacker.world.getWorldInfo().isHardcoreModeEnabled()) {
					event.setAmount(event.getAmount() * (1.0F + 0.1F * (float)level));
				}
				else {
					event.setAmount(event.getAmount() * (1.0F + 0.2F * (float)level));
				}
			}
		}
	}
}