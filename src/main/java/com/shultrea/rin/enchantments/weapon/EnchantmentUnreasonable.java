package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EnchantmentUnreasonable extends EnchantmentBase {

	public EnchantmentUnreasonable(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
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
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.unreasonable, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.unreasonable, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.unreasonable;
	}

	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onAttack(LivingAttackEvent event) {
		if (event.isCanceled() || event.getAmount() <= 0) return;
		if (event.getSource().getTrueSource() == null || !event.getSource().damageType.equals("player")) return;
		EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
		EntityLivingBase victim = event.getEntityLiving();
		World world = victim.getEntityWorld();
		if (victim instanceof EntityPlayer) return;
		if (victim.getHealth() > victim.getMaxHealth()) return;

		int enchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.unreasonable, attacker);

		Random random = victim.getRNG();
		if (random.nextInt(200) >= enchantmentLevel * 10) return;

		//Cap AABB size to 55 to avoid incredibly large AABB checks in the event that someone acquires or cheats in a high enchantmentLevel book
		int bbSize = Math.min(5 + enchantmentLevel * 5, 55);
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(attacker, victim.getEntityBoundingBox().grow(bbSize));
		list = list.stream().filter(v -> v instanceof EntityLiving).collect(Collectors.toList());
		EntityLiving randomTarget = (EntityLiving) list.get(random.nextInt(list.size()));

		if (victim.getDistanceSq(randomTarget) > 8 + enchantmentLevel * 28) return;
		victim.setRevengeTarget(randomTarget);
		//Check if the victim has been given a revenge target or already has one
		if (world instanceof WorldServer) {
			//Schedule the task, otherwise the attacker will become the target every time
			((WorldServer) world).addScheduledTask(() -> {
				try {
					//Make absolutely sure that the three entities involved still exist
					if (victim != null && attacker != null && randomTarget != null) {
						//A dead victim can't get angry
						if (!victim.isDead) {
							//Double check that the victim class is ready for casting
							if (victim instanceof EntityLiving) {
								//Prevent monsters from targeting themselves
								if (!victim.getUniqueID().equals(randomTarget.getUniqueID())) {
									//Get mad!
									EntityLiving victimLiving = (EntityLiving) victim;
									victimLiving.setRevengeTarget(randomTarget);
									victimLiving.setAttackTarget(randomTarget);
								}
							}
						}
					}
				} catch (Exception ex) {
					//Task failed
				}
			});
		}
	}
}