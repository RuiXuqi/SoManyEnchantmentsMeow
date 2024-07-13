package com.shultrea.rin.enchantments;

import com.shultrea.rin.Interfaces.IEnchantmentDamage;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentBlessedEdge extends EnchantmentBase {
	
	public EnchantmentBlessedEdge(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.blessedEdge;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.blessedEdge;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 20 + 10 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.blessedEdge;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != Enchantments.SMITE && fTest != Smc_010.CursedEdge && fTest != Smc_010.AdvancedSmite && !(fTest instanceof IEnchantmentDamage);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(Smc_010.BlessedEdge, dmgSource) <= 0) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		int levelBless = EnchantmentHelper.getEnchantmentLevel(Smc_010.BlessedEdge, dmgSource);
		attacker.heal(fEvent.getAmount() * (levelBless * 0.03f));
		if(fEvent.getEntityLiving().getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
			float FDamage = EnchantmentsUtility.CalculateDamageIgnoreSwipe(fEvent.getAmount(), 1.00f, 0.60f, 1 + 0.04f * levelBless, attacker, Smc_010.BlessedEdge);
			//UtilityAccessor.damageEntity(fEvent.getEntityLiving(), somanyenchantments.PhysicalDamage, Damage - 0.001f);
			fEvent.setAmount(FDamage);
		}
	}
}