package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Interfaces.IDamageMultiplier;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentInstability extends EnchantmentBase implements IDamageMultiplier {
	
	public EnchantmentInstability(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.instability;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.instability;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 30 + (par1 - 1) * 15;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.instability;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof EnchantmentDurability);
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onAttack(LivingDamageEvent e) {
		if(!e.isCanceled() && e.getAmount() > 0) {
			if(e.getSource().getTrueSource() != null && e.getSource().damageType.equals("player")) {
				EntityLivingBase attacker = (EntityLivingBase)e.getSource().getTrueSource();
				if(attacker.getHeldItemMainhand().isEmpty()) return;
				ItemStack stack = attacker.getHeldItemMainhand();
				int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
				if(level <= 0) return;
				if(this.isOffensivePetDisallowed(e.getSource().getImmediateSource(), e.getSource().getTrueSource()))
					return;
				float percentage = ((float)stack.getItemDamage() / (float)stack.getMaxDamage());
				//System.out.println(percentage + " percentage");
				percentage = 1 + percentage * (0.75f * level);
				e.setAmount(e.getAmount() * percentage);
				stack.damageItem((int)(e.getAmount() * attacker.getRNG().nextFloat() / (8 - level)) + 1, attacker);
			}
		}
	}
}