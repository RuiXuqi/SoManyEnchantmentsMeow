package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.EnchantUtil;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentSupremeProtection extends EnchantmentBase {
	
	public EnchantmentSupremeProtection(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
		this.type = EnumEnchantmentType.ARMOR;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.supremeProtection;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMinLevel() {
		return 10;
	}

	@Override
	public int getMaxLevel() {
		return 10;
	}

	@Override
	public int getMinEnchantability(int level) {
		return 1000;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return 1000;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return this.type.canEnchantItem(stack.getItem()) && super.canApplyAtEnchantingTable(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return true;
	}
	
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 : 8;
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingHurtEvent_extraProtection(LivingHurtEvent event) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		if(!this.isEnabled()) return;
		int totalLevel = EnchantUtil.getTotalArmorEnchantmentLevel(this, event.getEntityLiving());
		if(totalLevel > 0) {
			float modifier = 1.0F - (0.05F * (float)totalLevel / (float)getMaxLevel());
			event.setAmount(event.getAmount() * modifier);
		}
	}
}