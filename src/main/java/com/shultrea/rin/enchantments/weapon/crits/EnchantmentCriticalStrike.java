package com.shultrea.rin.enchantments.weapon.crits;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.SoundRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCriticalStrike extends EnchantmentBase {
	
	public EnchantmentCriticalStrike(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.criticalStrike;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.criticalStrike;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.criticalStrike, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.criticalStrike, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.criticalStrike, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.criticalStrike, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.criticalStrike;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onCriticalHitEvent(CriticalHitEvent event) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isCriticalHitEventStrong(event)) return;
		EntityLivingBase attacker = event.getEntityLiving();
		if(attacker == null) return;
		if(!(event.getTarget() instanceof EntityLivingBase)) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(CompatUtil.isRLCombatLoaded()) stack = RLCombatCompat.getCriticalHitEventStack(event, attacker);
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(event.getResult() == Event.Result.ALLOW || (event.isVanillaCritical() && event.getResult() == Event.Result.DEFAULT)) {
				NBTTagCompound compound = stack.getTagCompound();
				if(compound == null) compound = new NBTTagCompound();
				int counter = compound.getInteger("CriticalStrikeFailCount");
				int maxReduction = level * 50;
				
				if(attacker.getRNG().nextInt(1000 - maxReduction) >= 32 * (counter + 1)) {
					compound.setInteger("CriticalStrikeFailCount", counter + 1);
				}
				else {
					compound.setInteger("CriticalStrikeFailCount", 0);
					float crit = 0.4F + 0.4F * (float)level + 0.5F * attacker.getRNG().nextFloat();
					attacker.world.playSound(null, attacker.posX, attacker.posY, attacker.posZ, SoundRegistry.CRITICAL_STRIKE, SoundCategory.PLAYERS, 0.8F, 1.0F / (1.2F + 0.4F * attacker.getRNG().nextFloat()) * 1.6F);
					event.setDamageModifier(event.getDamageModifier() + crit);
				}
				stack.setTagCompound(compound);
			}
		}
	}
}