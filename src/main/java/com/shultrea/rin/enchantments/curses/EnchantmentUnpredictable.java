package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Interfaces.IEnchantmentCurse;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_020;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentUnpredictable extends EnchantmentBase implements IEnchantmentCurse {
	
	public EnchantmentUnpredictable(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.unpredictable;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.unpredictable;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + 10 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.unpredictable;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(Smc_020.Unpredictable, dmgSource) <= 0) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		int levelDamageRandomizer = EnchantmentHelper.getEnchantmentLevel(Smc_020.Unpredictable, dmgSource);
		{
			float random = (float)Math.random() * (fEvent.getAmount() * (levelDamageRandomizer * 1.25f));
			random = (float)(Math.floor(random) + 1);
			fEvent.setAmount(random + 2.0f);
			if(fEvent.getEntity().world.rand.nextInt(100) <= 45) {
				fEvent.setAmount(0.0f);
				fEvent.getEntityLiving().heal((random));
			}
		}
	}
}