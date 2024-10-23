package com.shultrea.rin.Utility_Sector;

import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.Prop_Sector.PlayerPropertiesProvider;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OtherHandler {
	//TODO relocate
	
	@SubscribeEvent
	public void dealwithit(EntityJoinWorldEvent fEvent) {
		{
			if(fEvent.getEntity() instanceof EntityArrow) {
				EntityArrow arrow = (EntityArrow)fEvent.getEntity();
				EntityLivingBase shooter = (EntityLivingBase)arrow.shootingEntity;
				if(shooter == null) return;
				ItemStack bow = shooter.getActiveItemStack();
				if(bow == null || bow == ItemStack.EMPTY) {
					bow = shooter.getHeldItemOffhand();
					if(bow == null || bow == ItemStack.EMPTY) {
						return;
					}
				}
				if(!arrow.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) return;
				IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
				int p = ModConfig.enabled.supremeFlame ?
						EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.supremeFlame, bow) : 0;
				int l = ModConfig.enabled.strafe ? EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.strafe, bow) : 0;
				if(Math.random() < 0.125f * l && l > 0) properties.setArrowRapidDamage(true);
				//int pAlt =
				if(p > 0) {
					properties.setFlameLevel(3);
					arrow.setFire(400);
				}
				int s = ModConfig.enabled.advancedFlame ?
						EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedFlame, bow) : 0;
				if(s > 0) {
					properties.setFlameLevel(2);
					arrow.setFire(200);
				}
				int t = ModConfig.enabled.lesserFlame ?
						EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.lesserFlame, bow) : 0;
				if(t > 0) {
					properties.setFlameLevel(1);
					//arrow.setFire(50); //Overrides the 2 seconds with 5 seconds
				}
				//int j = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.ExtremePunch, shooter);
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void subjectHandler(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase) fEvent.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase) fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(stack == null) return;
		int levelMath = ModConfig.enabled.subjectMathematics ?
						  EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.subjectMathematics, stack) : 0;
		int levelHistory =
				ModConfig.enabled.subjectHistory ? EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.subjectHistory, stack) :
				0;
		int levelEnglish =
				ModConfig.enabled.subjectEnglish ? EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.subjectEnglish, stack) :
				0;
		if(levelMath > 0) {
			float hp = fEvent.getEntityLiving().getMaxHealth() * (levelMath * 0.003125f);
			float currentHp = fEvent.getEntityLiving().getHealth() * (0.025f * levelMath + 0.025f);
			float FD = EnchantmentsUtility.modifyDamage(fEvent.getAmount(), hp + currentHp, 0.0f, 1.0f, levelMath);
			fEvent.setAmount(FD);
		}
		if(levelHistory > 0) {
			float history = attacker.getEntityWorld().getTotalWorldTime() / 20 / 3600 / (6-levelHistory);
			history = Math.min(history, 2.5f * levelHistory);
			float FD = EnchantmentsUtility.modifyDamage(fEvent.getAmount(), history, 0.0f, 1.0f, levelHistory);
			fEvent.setAmount(FD);
		}
		if(levelEnglish > 0) {
			if(fEvent.getEntity() instanceof EntityWitch ||
					fEvent.getEntity() instanceof EntityVillager ||
					fEvent.getEntity() instanceof EntityWolf ||
					fEvent.getEntity() instanceof EntitySnowman ||
					fEvent.getEntity() instanceof EntityIronGolem ||
					fEvent.getEntity() instanceof EntityPigZombie ||
					fEvent.getEntity() instanceof EntityVindicator ||
					fEvent.getEntity() instanceof EntityIllusionIllager ||
					fEvent.getEntity() instanceof EntityEvoker)
			{
				float FD = EnchantmentsUtility.modifyDamage(fEvent.getAmount(), 0.0f, 3.0f, 1.0f, levelEnglish);
				fEvent.setAmount(FD);
				if(Math.random() <= 0.4f) fEvent.getEntityLiving().setSilent(true);
			}
		}
	}
	
	/**
	 *
	 */
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onArrowCapAttach(AttachCapabilitiesEvent<Entity> event) {
		if(event.getObject() instanceof EntityArrow && !event.getObject().hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) {
			event.addCapability(new ResourceLocation(SoManyEnchantments.MODID + ":arrow_capabilities"), new ArrowPropertiesProvider());
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void onPlayerCapAttach(AttachCapabilitiesEvent<Entity> event) {
		if(!event.getObject().hasCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null)) {
			event.addCapability(new ResourceLocation(SoManyEnchantments.MODID + ":player_capabilities"), new PlayerPropertiesProvider());
		}
	}
}
