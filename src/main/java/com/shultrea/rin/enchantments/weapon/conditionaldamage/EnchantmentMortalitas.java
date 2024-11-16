package com.shultrea.rin.enchantments.weapon.conditionaldamage;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentMortalitas extends EnchantmentBase {
	
	public EnchantmentMortalitas(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.mortalitas;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.mortalitas;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.mortalitas, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.mortalitas, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.mortalitas, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.mortalitas, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.mortalitas;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onDamage(LivingHurtEvent e) {
		if(e.getSource().getTrueSource() != null && e.getSource().getTrueSource() instanceof EntityPlayer && e.getSource().damageType.equals("player")) {
			if(!EnchantmentBase.isDamageSourceAllowed(e.getSource())) return;
			EntityPlayer attacker = (EntityPlayer)e.getSource().getTrueSource();
			ItemStack stack = attacker.getHeldItemMainhand();
			if(stack.isEmpty()) return;
			int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.mortalitas, stack);
			if(level <= 0) return;
			if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
			float damage = stack.getTagCompound().getFloat("additionalDamage");
			e.setAmount(e.getAmount() + damage);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void Death(LivingDeathEvent fEvent) {
		if(fEvent.getSource().getTrueSource() == null) return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityPlayer)) return;
		EntityLivingBase victim = fEvent.getEntityLiving();
		if(victim == null) return;
		EntityPlayer attacker = (EntityPlayer)fEvent.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(stack.isEmpty() || stack == null) return;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.mortalitas, stack);
		if(level <= 0) return;
		if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		float amount = stack.getTagCompound().getFloat("additionalDamage");
		amount = amount + (0.05f / this.getMaxLevel()) * level;
		amount = MathHelper.clamp(amount, 0, level);
		stack.getTagCompound().setFloat("additionalDamage", amount);
	}
}