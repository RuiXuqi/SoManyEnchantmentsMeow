package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.util.compat.CompatUtil;
import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class EnchantmentArcSlash extends EnchantmentBase {
	
	private boolean handlingArcSlash = false;
	
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
		return ConfigProvider.canItemApply(ModConfig.canApply.arcSlash, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.arcSlash, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.arcSlash;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		//Handle RLCombat separately using Sweep event
		if(CompatUtil.isRLCombatLoaded()) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		//Attempt to avoid recursion
		if(this.handlingArcSlash) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			this.handlingArcSlash = true;
			
			int fireLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
			if(EnchantmentRegistry.fieryEdge.isEnabled()) {
				int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.fieryEdge, stack);
				if(lvl > 0) fireLevel += EnchantmentFieryEdge.getLevelMult(lvl);
			}
			if(EnchantmentRegistry.lesserFireAspect.isEnabled()) {
				int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.lesserFireAspect, stack);
				if(lvl > 0) fireLevel += EnchantmentTierFA.getLevelMult(lvl, 0);
			}
			if(EnchantmentRegistry.advancedFireAspect.isEnabled()) {
				int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedFireAspect, stack);
				if(lvl > 0) fireLevel += EnchantmentTierFA.getLevelMult(lvl, 1);
			}
			if(EnchantmentRegistry.supremeFireAspect.isEnabled()) {
				int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.supremeFireAspect, stack);
				if(lvl > 0) fireLevel += EnchantmentTierFA.getLevelMult(lvl, 2);
			}
			fireLevel /= 2;
			
			int levitationLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.levitator, stack);
			
			float damage = event.getAmount() * 0.25F * (float)level;
			AxisAlignedBB boundBox = new AxisAlignedBB(attacker.posX - 5 - level, attacker.posY - 5 - level, attacker.posZ - 5 - level, attacker.posX + 5 + level, attacker.posY + 5 + level, attacker.posZ + 5 + level);
			ArrayList<Entity> targetEntities = new ArrayList<Entity>(attacker.getEntityWorld().getEntitiesWithinAABBExcludingEntity(event.getEntity(), boundBox));
			for(Entity target : targetEntities) {
				if(!(target instanceof EntityLivingBase)) continue;
				if(target == attacker) continue;
				if(target == victim) continue;
				if(target.getDistance(attacker) > 3.00F + 0.25F * (float)level) continue;
				
				Vec3d attackerCheck = new Vec3d(target.posX - attacker.posX, target.posY - attacker.posY, target.posZ - attacker.posZ);
				double angle = Math.toDegrees(Math.acos(attackerCheck.normalize().dotProduct(attacker.getLookVec())));
				if(angle < MathHelper.clamp(60.0D + (level * 10), 60, 359)) {
					DamageSource source = null;
					if(attacker instanceof EntityPlayer) {
						source = new EntityDamageSource("playerCleave", attacker);
						target.attackEntityFrom(source, damage);
						target.setFire(fireLevel * 4);
						if(levitationLevel > 0 && !attacker.world.isRemote) ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 30 + (levitationLevel * 12), 1 + levitationLevel));
					}
					if(attacker instanceof EntityMob) {
						source = new EntityDamageSource("mobCleave", attacker);
						target.attackEntityFrom(source, damage);
						target.setFire(fireLevel * 4);
						if(levitationLevel > 0 && !attacker.world.isRemote) ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 30 + (levitationLevel * 12), 1 + levitationLevel));
					}
					if(source == null) {
						target.attackEntityFrom(DamageSource.GENERIC, damage);
					}
					
					int levelKnockback = 1;
					levelKnockback += EnchantmentHelper.getKnockbackModifier(attacker);
					if(attacker.isSprinting()) levelKnockback++;
					levelKnockback /= 2;
					
					if(levelKnockback > 0) EnchantUtil.knockBackIgnoreKBRes(target, 0.3F * levelKnockback, attacker.posX - target.posX, attacker.posZ - target.posZ);
				}
			}
			attacker.setSprinting(false);
			
			this.handlingArcSlash = false;
		}
	}
}