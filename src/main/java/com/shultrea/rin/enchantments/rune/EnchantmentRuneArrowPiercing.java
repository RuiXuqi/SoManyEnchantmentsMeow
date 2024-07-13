package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.Interfaces.IEnchantmentRune;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_040;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRuneArrowPiercing extends EnchantmentBase implements IEnchantmentRune {
	
	public EnchantmentRuneArrowPiercing(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{
				EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runeArrowPiercing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runeArrowPiercing;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 25 + (par1 - 1) * 15;
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runeArrowPiercing;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onEvent(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityArrow) {
			EntityArrow arrow = (EntityArrow)event.getEntity();
			EntityLivingBase shooter = (EntityLivingBase)arrow.shootingEntity;
			if(shooter == null) return;
			ItemStack bow = shooter.getActiveItemStack();
			if(bow == null || bow == ItemStack.EMPTY) {
				bow = shooter.getHeldItemOffhand();
				if(bow == null || bow == ItemStack.EMPTY) return;
			}
			int p = EnchantmentHelper.getEnchantmentLevel(Smc_040.Rune_PiercingArrows, bow);
			if(p <= 0) return;
			if(!arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) return;
			IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
			properties.setPiercingLevel(p);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onArrowPierce(LivingHurtEvent e) {
		if(e.getSource().damageType != "arrow" && e.getSource().isUnblockable()) return;
		if(!(e.getSource().getImmediateSource() instanceof EntityArrow)) return;
		if(!(e.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityArrow arrow = (EntityArrow)e.getSource().getImmediateSource();
		if(!arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) return;
		IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		int pierceLevel = properties.getArmorPiercingLevel();
		if(pierceLevel <= 0) return;
		EntityLivingBase shooter = (EntityLivingBase)e.getSource().getTrueSource();
		float damage = e.getAmount() * 0.25f * pierceLevel;
		e.setAmount(e.getAmount() - (e.getAmount() * pierceLevel * 0.25f));
		UtilityAccessor.damageTarget(e.getEntityLiving(), new EntityDamageSource("arrow", shooter).setDamageBypassesArmor(), damage);
	}
}