package com.shultrea.rin.enchantments.weapon;

import bettercombat.mod.event.RLCombatModifyDamageEvent;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.registry.ModRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAtomicDeconstructor extends EnchantmentBase {
	
	public EnchantmentAtomicDeconstructor(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.atomicDeconstructor;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.atomicDeconstructor;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.atomicDeconstructor, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.atomicDeconstructor, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.atomicDeconstructor, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.atomicDeconstructor, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.atomicDeconstructor;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void modifyAttackDamagePost(RLCombatModifyDamageEvent.Post event) {
		if(event.getEntityPlayer() == null || event.getStack().isEmpty() || event.getTarget() == null || !(event.getTarget() instanceof EntityLivingBase)) return;

		EntityLivingBase target = (EntityLivingBase)event.getTarget();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.atomicDeconstructor, event.getStack());
		if(level > 0 && event.getCooledStrength() > 0.9F && (target.isNonBoss() || ModConfig.miscellaneous.atomicDeconstructorBosses)) {
			if(target.world.rand.nextFloat() < 0.001F*(float)level) {
				target.hurtResistantTime = 0;
				if(!ModConfig.miscellaneous.atomicDeconstructorMaxDamage || !target.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE)) {
					event.setDamageModifier(target.getMaxHealth() * 100.0F);
				}
				event.getEntityPlayer().world.playSound(null, event.getEntityPlayer().posX, event.getEntityPlayer().posY, event.getEntityPlayer().posZ, ModRegistry.ATOMIC_DECONSTRUCT, SoundCategory.PLAYERS, 2.0F, 1.0F /(event.getEntityPlayer().world.rand.nextFloat() * 0.4F + 1.2F)* 1.4F);
			}
		}
	}
}