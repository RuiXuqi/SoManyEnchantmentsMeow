package com.shultrea.rin.enchantments;

import com.shultrea.rin.Interfaces.IDamageMultiplier;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentReviledBlade extends EnchantmentBase implements IDamageMultiplier {
	
	public EnchantmentReviledBlade(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.reviledBlade;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.reviledBlade;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 15 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.reviledBlade;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IDamageMultiplier);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HandleEnchant(LivingDamageEvent fEvent) {
		if(!(EnchantmentsUtility.checkEventCondition(fEvent, this))) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack weapon = attacker.getHeldItemMainhand();
		if(EnchantmentHelper.getEnchantmentLevel(Smc_010.ReviledBlade, weapon) > 0) {
			if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
				return;
			float defenderHealthPercent = fEvent.getEntityLiving().getHealth() / fEvent.getEntityLiving().getMaxHealth();
			int levelfinish = EnchantmentHelper.getEnchantmentLevel(Smc_010.ReviledBlade, weapon);
			float dmgMod = (1.0f - defenderHealthPercent) * ((levelfinish * 0.1f) + 0.9f);
			dmgMod = 1.0F + dmgMod;
			float Damage = fEvent.getAmount();
			//UtilityAccessor.damageEntity(fEvent.getEntityLiving(), somanyenchantments.PhysicalDamage, (dmgMod * Damage) - 0.001f);
			fEvent.setAmount(dmgMod * Damage);
		}
	}
}