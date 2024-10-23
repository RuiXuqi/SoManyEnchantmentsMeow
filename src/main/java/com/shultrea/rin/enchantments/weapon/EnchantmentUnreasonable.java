package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EnchantmentUnreasonable extends EnchantmentBase {
	
	public EnchantmentUnreasonable(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.unreasonable;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.unreasonable;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.unreasonable, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.unreasonable, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.unreasonable;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onAttack(LivingAttackEvent event) {
		if(!event.isCanceled() && event.getAmount() > 0) {
			if(event.getSource().getTrueSource() != null && event.getSource().damageType.equals("player")) {
				World world = event.getEntityLiving().world;
				Random random = new Random();
				EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
				EntityLivingBase victim = event.getEntityLiving();
				if(attacker.getHeldItemMainhand().isEmpty()) return;
				if(victim instanceof EntityPlayer) return;
				//Cap enchantmentLevel to 10 to avoid incredibly large AABB checks in the event that someone acquires or cheats in a high enchantmentLevel book
				int enchantmentLevel = Math.min(10, EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.unreasonable, attacker.getHeldItemMainhand()));
				boolean roll = random.nextInt(10) < enchantmentLevel * 200;
				if(roll) {
					List list = world.getEntitiesWithinAABBExcludingEntity(attacker, victim.getEntityBoundingBox().grow(5 + enchantmentLevel * 5, 5 + enchantmentLevel * 5, 5 + enchantmentLevel * 5));
					Collections.shuffle(list);
					for(int x = 0; x < list.size(); x++) {
						if(list.get(x) instanceof EntityLivingBase) {
							EntityLivingBase randomTarget = (EntityLivingBase)list.get(x);
							if(victim.getDistanceSq(randomTarget) <= 36 + (enchantmentLevel - 1) * 28) {
								if(victim.getMaxHealth() <= victim.getHealth()) {
									victim.setRevengeTarget(randomTarget);
									//Check if the victim has been given a revenge target or already has one
									World victimWorld = victim.getEntityWorld();
									EntityLivingBase revenge = victim.getRevengeTarget();
									if(victimWorld instanceof WorldServer && revenge != null) {
										//Schedule the task, otherwise the attacker will become the target every time
										((WorldServer)world).addScheduledTask(() -> {
											try {
												//Make absolutely sure that the three entities involved still exist
												if(victim != null && attacker != null && revenge != null) {
													//A dead victim can't get angry
													if(!victim.isDead) {
														//Double check that the victim class is ready for casting
														if(victim instanceof EntityLiving) {
															//Prevent monsters from targeting themselves
															if(!victim.getUniqueID().equals(revenge.getUniqueID())) {
																//Get mad!
																EntityLiving victimLiving = (EntityLiving)victim;
																victimLiving.setRevengeTarget(revenge);
																victimLiving.setAttackTarget(revenge);
															}
														}
													}
												}
											}
											catch(Exception ex) {
												//Task failed
											}
										});
									}
								}
								//victim.attackEntityFrom(new EntityDamageSource("confusion", attacker), e.getAmount());
							}
						}
					}
				}
			}
		}
	}
}