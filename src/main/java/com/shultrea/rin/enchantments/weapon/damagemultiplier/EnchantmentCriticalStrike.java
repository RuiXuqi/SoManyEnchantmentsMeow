package com.shultrea.rin.enchantments.weapon.damagemultiplier;

import bettercombat.mod.event.RLCombatCriticalHitEvent;
import com.shultrea.rin.Interfaces.IDamageMultiplier;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.registry.ModRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCriticalStrike extends EnchantmentBase implements IDamageMultiplier {
	
	public EnchantmentCriticalStrike(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.criticalStrike;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.criticalStrike;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.criticalStrike, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.criticalStrike, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.criticalStrike, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.criticalStrike, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.criticalStrike;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onCritical(RLCombatCriticalHitEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if(player == null) return;
		ItemStack stack = event.getOffhand() ? player.getHeldItemOffhand() : player.getHeldItemMainhand();
		//Only trigger on an actual crit, not an attempted crit
		if(!stack.isEmpty() && (event.getResult() == Event.Result.ALLOW || (event.isVanillaCritical() && event.getResult() == Event.Result.DEFAULT))) {
			int csLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.criticalStrike, stack);
			if(csLevel > 0) {
				if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				int counter = stack.getTagCompound().getInteger("failedCritCount");
				int maxReduction = csLevel * 50;

				if(player.world.rand.nextInt(1000 - maxReduction) >= 32 * (counter + 1)) stack.getTagCompound().setInteger("failedCritCount", counter + 1);
				else {
					stack.getTagCompound().setInteger("failedCritCount", 0);
					float crit = 0.4F + (float)csLevel * 0.4F + player.world.rand.nextFloat() * 0.5F;

					player.world.playSound(null, player.posX, player.posY, player.posZ, ModRegistry.CRITICAL_STRIKE, SoundCategory.PLAYERS, 0.8F, 1.0F /(player.world.rand.nextFloat() * 0.4F + 1.2F)* 1.6F);

					event.setDamageModifier(event.getDamageModifier() + crit);
				}
			}
		}
	}
	
	//TODO
//	public void vanillaSweepAttack(EntityPlayer player, float damage, EntityLivingBase victim) {
//		float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * damage;
//		for(EntityLivingBase entitylivingbase : player.world.getEntitiesWithinAABB(EntityLivingBase.class, victim.getEntityBoundingBox().grow(1.0D, 0.25D, 1.0D))) {
//			if(entitylivingbase != player && entitylivingbase != victim && !player.isOnSameTeam(entitylivingbase) && player.getDistanceSq(entitylivingbase) < 9.0D) {
//				entitylivingbase.knockBack(player, 0.4F, MathHelper.sin(player.rotationYaw * 0.017453292F), -MathHelper.cos(player.rotationYaw * 0.017453292F));
//				entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), f3);
//			}
//		}
//		player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
//		player.spawnSweepParticles();
//	}
}