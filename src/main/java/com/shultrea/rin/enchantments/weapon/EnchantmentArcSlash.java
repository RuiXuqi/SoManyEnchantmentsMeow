package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class EnchantmentArcSlash extends EnchantmentBase {
	
	public EnchantmentArcSlash(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.arcSlash;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.arcSlash;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.arcSlash, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.arcSlash, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.arcSlash, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.arcSlash, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.arcSlash;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void HandleEnchant(LivingDamageEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		//Cap out cleave level to avoid large AABB checks
		int enchantmentLevel = Math.min(10, EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.arcSlash, stack));
		int lf = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.fieryEdge, stack) * EnchantmentFieryEdge.getFireSeconds()*5/6;
		lf += EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) * 3;
		lf += EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedFireAspect, stack) * EnchantmentTierFA.getFireSeconds(1)*3/4;
		lf += EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.supremeFireAspect, stack) * EnchantmentTierFA.getFireSeconds(2)*3/4;
		lf += EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.lesserFireAspect, stack) * EnchantmentTierFA.getFireSeconds(0)*3/4;
		if(enchantmentLevel <= 0) return;
		int levitationLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.levitator, stack);
		// We have a cleaving level, let's figure out our damage value.
		float splashDamage = fEvent.getAmount() * (enchantmentLevel * 0.25f);
		// Next, find our entities to hit.
		AxisAlignedBB boundBox = new AxisAlignedBB(attacker.posX - 5 - enchantmentLevel, attacker.posY - 5 - enchantmentLevel, attacker.posZ - 5 - enchantmentLevel, attacker.posX + 5 + enchantmentLevel, attacker.posY + 5 + enchantmentLevel, attacker.posZ + 5 + enchantmentLevel);
		//@SuppressWarnings("unchecked")
		ArrayList<Entity> targetEntities = new ArrayList<Entity>(attacker.getEntityWorld().getEntitiesWithinAABBExcludingEntity(fEvent.getEntity(), boundBox));
		// Let's remove all the entries that aren't within range of our attacker
        for (Entity target : targetEntities) {
            if (!(target instanceof EntityLivingBase)) continue;
            if (target == attacker) continue;
            if (target == fEvent.getEntityLiving()) continue;
            //Old value was 4.00f
            if (target.getDistance(attacker) > 3.00f + enchantmentLevel * 0.25f) continue;
            Vec3d attackerCheck = new Vec3d(target.posX - attacker.posX, target.posY - attacker.posY, target.posZ - attacker.posZ);
            double angle = Math.toDegrees(Math.acos(attackerCheck.normalize().dotProduct(attacker.getLookVec())));
            if (angle < MathHelper.clamp(60.0D + (enchantmentLevel * 10), 60, 359)) {
                // This is within our arc, let's deal our damage.
                DamageSource source = null;
                if (attacker instanceof EntityPlayer) {
                    source = new EntityDamageSource("playerCleave", attacker);//.setDamageBypassesArmor();
                    target.attackEntityFrom(source, splashDamage);
                    target.setFire(lf);
                    if (levitationLevel > 0)
                        ((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 30 + (levitationLevel * 12), 1 + levitationLevel));
                }
                if (attacker instanceof EntityMob) {
                    source = new EntityDamageSource("mobCleave", attacker);//.setDamageBypassesArmor();
                    target.attackEntityFrom(source, splashDamage);
                    target.setFire(lf);
                    if (levitationLevel > 0)
                        ((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 30 + (levitationLevel * 12), 1 + levitationLevel));
                }
                if (source == null) {
                    target.attackEntityFrom(DamageSource.GENERIC, splashDamage);
                }
                if (attacker instanceof EntityLivingBase) {
                    // Apply knockback
                    int modKnockback = 1;
                    modKnockback += EnchantmentHelper.getKnockbackModifier(attacker) * 0.5f;
                    if (attacker.isSprinting()) modKnockback++;
                    //Entity victim = fEvent.getEntityLiving();
                    if (modKnockback > 0)
                        //target.setVelocity((double)(-MathHelper.sin(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)modKnockback * 0.85F), 0.2D, (double)(MathHelper.cos(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)modKnockback * 0.85F));
                        EnchantUtil.knockBackIgnoreKBRes(target, 0.3F * modKnockback, attacker.posX - target.posX, attacker.posZ - target.posZ);
                }
            }
        }
		// Stop the player sprinting
		attacker.setSprinting(false);
	}
}