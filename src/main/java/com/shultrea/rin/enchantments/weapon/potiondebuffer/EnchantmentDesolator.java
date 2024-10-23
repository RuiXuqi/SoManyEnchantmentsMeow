package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.Interfaces.IPotionDebuffer;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDesolator extends EnchantmentBase implements IPotionDebuffer {
	
	public EnchantmentDesolator(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.desolator;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.desolator;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.desolator, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.desolator, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.desolator;
	}
	
	//TODO
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return super.canApplyTogether(fTest) && !(fTest instanceof IPotionDebuffer);
//	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOW)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = attacker.getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.desolator, stack);
		if(enchantmentLevel <= 0) return;
		if(fEvent.getEntity().world.rand.nextInt(100) < 8 * enchantmentLevel) {
			if(enchantmentLevel >= 3) {
				fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 140, enchantmentLevel - 3));
			}
			fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 140, -enchantmentLevel));
			if(fEvent.getEntityLiving().getActivePotionEffect(MobEffects.RESISTANCE) == null) {
				fEvent.setAmount(fEvent.getAmount() * 1.2f * enchantmentLevel);
			}
		}
	}
}