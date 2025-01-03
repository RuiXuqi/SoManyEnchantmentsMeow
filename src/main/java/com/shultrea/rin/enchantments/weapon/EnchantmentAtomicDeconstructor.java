package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.SoundRegistry;
import com.shultrea.rin.util.DamageSources;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAtomicDeconstructor extends EnchantmentBase {
	
	private boolean deconstructing = false;
	
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
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isAttackEntityFromStrong()) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		if(deconstructing) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(victim.isNonBoss() || ModConfig.miscellaneous.atomicDeconstructorBosses) {
				if(!attacker.world.isRemote && attacker.world.rand.nextFloat() < 0.001F * (float)level) {
					attacker.world.playSound(null, attacker.posX, attacker.posY, attacker.posZ, SoundRegistry.ATOMIC_DECONSTRUCT, SoundCategory.PLAYERS, 2.0F, 1.0F /(attacker.world.rand.nextFloat() * 0.4F + 1.2F)* 1.4F);
					event.setCanceled(true);
					deconstructing = true;
					victim.attackEntityFrom(DamageSources.DECONSTRUCTED, Float.MAX_VALUE);
					deconstructing = false;
				}
			}
		}
	}
}