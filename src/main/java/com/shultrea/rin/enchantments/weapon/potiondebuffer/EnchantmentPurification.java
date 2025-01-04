package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import com.shultrea.rin.util.compat.SpawnerControlCompat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;

public class EnchantmentPurification extends EnchantmentBase {
	
	public EnchantmentPurification(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.purification;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.purification;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.purification, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.purification, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.purification, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.purification, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.purification;
	}

	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !fTest.isCurse();
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isOnEntityDamagedAltStrong()) return;
		if(attacker == null) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;
		
		if(!attacker.world.isRemote) {
			if(victim.isDead || victim.getHealth() <= 0.0F) return;
			if(victim.isEntityUndead()) {
				victim.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20 + level * 10, Math.max(0, Math.min(1, level - 1))));
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + level * 10, Math.max(0, Math.min(2, level - 1))));
			}
			if(victim.getRNG().nextFloat() <= 0.05F * (float)level) {
				convert(victim);
			}
		}
	}
	
	private static void convert(EntityLivingBase entity) {
		if(entity instanceof EntityZombieVillager) {
			EntityVillager villager = new EntityVillager(entity.world);
			villager.copyLocationAndAnglesFrom(entity);
			villager.setProfession(((EntityZombieVillager)entity).getForgeProfession());
			villager.finalizeMobSpawn(entity.world.getDifficultyForLocation(new BlockPos(villager)), null, false);
			villager.setLookingForHome();
			if(entity.isChild()) villager.setGrowingAge(-24000);
			
			if(CompatUtil.isSpawnerControlLoaded()) SpawnerControlCompat.increaseSpawnerCount(entity);
			
			entity.setSilent(true);
			entity.world.removeEntity(entity);
			villager.setNoAI(((EntityLiving)entity).isAIDisabled());
			
			if(entity.hasCustomName()) {
				villager.setCustomNameTag(entity.getCustomNameTag());
				villager.setAlwaysRenderNameTag(entity.getAlwaysRenderNameTag());
			}
			
			entity.world.spawnEntity(villager);
			
			villager.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
			villager.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1));
			entity.world.playEvent(null, 1027, new BlockPos((int)villager.posX, (int)villager.posY, (int)villager.posZ), 0);
			villager.hurtResistantTime = villager.maxHurtResistantTime;
		}
		else if(entity instanceof EntityPigZombie) {
			EntityPig pig = new EntityPig(entity.world);
			pig.copyLocationAndAnglesFrom(entity);
			if(entity.isChild()) pig.setGrowingAge(-24000);
			
			if(CompatUtil.isSpawnerControlLoaded()) SpawnerControlCompat.increaseSpawnerCount(entity);
			
			entity.setSilent(true);
			entity.world.removeEntity(entity);
			pig.setNoAI(((EntityPigZombie)entity).isAIDisabled());
			
			if(entity.hasCustomName()) {
				pig.setCustomNameTag(entity.getCustomNameTag());
				pig.setAlwaysRenderNameTag(entity.getAlwaysRenderNameTag());
			}
			
			entity.world.spawnEntity(pig);
			
			pig.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
			pig.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1));
			entity.world.playEvent(null, 1027, new BlockPos((int)pig.posX, (int)pig.posY, (int)pig.posZ), 0);
			pig.hurtResistantTime = pig.maxHurtResistantTime;
		}
		else if(entity instanceof EntityMagmaCube) {
			EntitySlime slime = new EntitySlime(entity.world);
			slime.copyLocationAndAnglesFrom(entity);
			
			NBTTagCompound entityCompound = entity.serializeNBT();
			NBTTagCompound slimeCompound = slime.serializeNBT();
			slimeCompound.setInteger("Size", entityCompound.getInteger("Size"));
			slime.readEntityFromNBT(slimeCompound);
			
			if(CompatUtil.isSpawnerControlLoaded()) SpawnerControlCompat.increaseSpawnerCount(entity);
			
			entity.setSilent(true);
			entity.world.removeEntity(entity);
			slime.setNoAI(((EntityMagmaCube)entity).isAIDisabled());
			
			if(entity.hasCustomName()) {
				slime.setCustomNameTag(entity.getCustomNameTag());
				slime.setAlwaysRenderNameTag(entity.getAlwaysRenderNameTag());
			}
			
			entity.world.spawnEntity(slime);
			
			slime.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
			slime.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1));
			entity.world.playEvent(null, 1027, new BlockPos((int)slime.posX, (int)slime.posY, (int)slime.posZ), 0);
			slime.hurtResistantTime = slime.maxHurtResistantTime;
		}
	}
}