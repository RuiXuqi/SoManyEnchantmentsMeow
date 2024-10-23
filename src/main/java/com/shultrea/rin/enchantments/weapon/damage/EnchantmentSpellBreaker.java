package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.Interfaces.IEnchantmentDamage;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentSpellBreaker extends EnchantmentBase implements IEnchantmentDamage {
	
	public EnchantmentSpellBreaker(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.spellBreaker;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.spellBreaker;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.spellBreaker, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.spellBreaker, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.spellBreaker;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onHurt(LivingHurtEvent e) {
		if(!EnchantmentBase.isDamageSourceAllowed(e.getSource())) return;
		ItemStack stack = ((EntityLivingBase)e.getSource().getTrueSource()).getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.spellBreaker, stack);
		if(level <= 0) return;
		if(!e.getEntityLiving().getActivePotionEffects().isEmpty())
			e.setAmount(e.getAmount() + (0.625f * level) * e.getEntityLiving().getActivePotionEffects().size());
		if(e.getEntityLiving() instanceof EntityWitch || e.getEntityLiving() instanceof EntityEvoker)
			e.setAmount(e.getAmount() + 1.5f * level);
	}
}