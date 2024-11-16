package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRunePiercingCapabilities extends EnchantmentBase {
	
	public EnchantmentRunePiercingCapabilities(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runePiercingCapabilities;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runePiercingCapabilities;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.runePiercingCapabilities, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.runePiercingCapabilities, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.runePiercingCapabilities, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.runePiercingCapabilities, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runePiercingCapabilities;
	}
	
	//TODO
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		if(fEvent.getSource().isUnblockable()) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = attacker.getHeldItemMainhand();
		//TODO: this should check the attacking hand, not main
		int pierceLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runePiercingCapabilities, stack);
		if(pierceLevel <= 0) return;
		float damage = fEvent.getAmount() * 0.25f * pierceLevel;
		fEvent.setAmount(fEvent.getAmount() - (fEvent.getAmount() * pierceLevel * 0.25f));
		if(attacker instanceof EntityPlayer)
			UtilityAccessor.damageTargetEvent(fEvent.getEntityLiving(), new EntityDamageSource("player", attacker).setDamageBypassesArmor(), damage);
		else
			UtilityAccessor.damageTargetEvent(fEvent.getEntityLiving(), new EntityDamageSource("mob", attacker).setDamageBypassesArmor(), damage);
	}
}