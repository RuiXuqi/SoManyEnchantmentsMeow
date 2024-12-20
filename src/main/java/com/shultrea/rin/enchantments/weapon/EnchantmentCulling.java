package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.DamageSources;
import com.shultrea.rin.util.ReflectionUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class EnchantmentCulling extends EnchantmentBase {

	public EnchantmentCulling(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}

	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.culling;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.culling;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.culling, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.culling, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return ModConfig.canApply.isItemValid(ModConfig.canApply.culling, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.culling, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.culling;
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingDamage(LivingDamageEvent fEvent) {
		if (!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase) fEvent.getSource().getTrueSource();
		if (attacker == null) return;
		ItemStack itemStack = ((EntityLivingBase) fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.culling, itemStack);
		if (enchantmentLevel <= 0) return;

		float maxHealth = fEvent.getEntityLiving().getMaxHealth();
		float currentHealthPercent = fEvent.getEntityLiving().getHealth() / maxHealth;

		float bossModifier = fEvent.getEntityLiving().isNonBoss() ? 1F : 0.5F;
		float absoluteHealthThreshold = fEvent.getEntityLiving() instanceof EntityPlayer ? 0F : 4F + enchantmentLevel * 2F;

		boolean isLowHealth = fEvent.getEntityLiving().getHealth() <= absoluteHealthThreshold || currentHealthPercent <= 0.09F + enchantmentLevel * bossModifier * 0.07F;

		if (isLowHealth) {
			attacker.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 20 + enchantmentLevel * 5, 1));
		}
		if (attacker.fallDistance > 0.34F) {
			attacker.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 4, -1, false, false));
			if (!isLowHealth) {
				float damagePhysical = fEvent.getAmount() * (0.71F + enchantmentLevel * bossModifier * 0.33F);
				ReflectionUtil.damageEntityNoEvent(fEvent.getEntityLiving(), DamageSources.PhysicalDamage, damagePhysical);
			} else {
				double X = fEvent.getEntity().posX;
				double Y = fEvent.getEntity().posY;
				double Z = fEvent.getEntity().posZ;
				Random random = attacker.getRNG();
				for (int i = 0; i < 20; ++i) {
					double d0 = random.nextGaussian() * 0.02D;
					double d1 = random.nextGaussian() * 0.02D;
					double d2 = random.nextGaussian() * 0.02D;
					attacker.world.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, (float) X + random.nextFloat(), Y + ((double) random.nextFloat() * 2), (float) Z + random.nextFloat(), d0, d1, d2);
				}
				attacker.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60 + (enchantmentLevel * 20), enchantmentLevel - 1));
				float damageCull = maxHealth * 10F;
				ReflectionUtil.damageEntityNoEvent(fEvent.getEntityLiving(), DamageSources.Culled, damageCull);
			}
			fEvent.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent fEvent) {
		if (fEvent.getSource() != DamageSources.Culled) return;
		if (!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase) fEvent.getSource().getTrueSource();
		if (attacker == null) return;
		if (EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.culling, attacker) <= 0) return;

		if (attacker.getRNG().nextFloat() <= 0.35F) {
			ItemStack itemHead = null;
			if (fEvent.getEntity() instanceof EntityCreeper) itemHead = new ItemStack(Items.SKULL, 1, 4);
			if (fEvent.getEntity() instanceof EntitySkeleton) itemHead = new ItemStack(Items.SKULL, 1, 0);
			if (fEvent.getEntity() instanceof EntityZombie) itemHead = new ItemStack(Items.SKULL, 1, 2);
			if (fEvent.getEntity() instanceof EntityPlayer) {
				itemHead = new ItemStack(Items.SKULL, 1, 3);
				itemHead.setTagCompound(new NBTTagCompound());
				itemHead.getTagCompound().setString("SkullOwner", fEvent.getEntityLiving().getName());
			}
			if (fEvent.getEntity() instanceof EntitySpider) {
				itemHead = new ItemStack(Items.SKULL, 1, 3);
				itemHead.setTagCompound(new NBTTagCompound());
				itemHead.getTagCompound().setString("SkullOwner", "MHF_Spider");
			}
			if (fEvent.getEntity() instanceof EntityCaveSpider) {
				itemHead = new ItemStack(Items.SKULL, 1, 3);
				itemHead.setTagCompound(new NBTTagCompound());
				itemHead.getTagCompound().setString("SkullOwner", "MHF_CaveSpider");
			}
			if (fEvent.getEntity() instanceof EntityEnderman) {
				itemHead = new ItemStack(Items.SKULL, 1, 3);
				itemHead.setTagCompound(new NBTTagCompound());
				itemHead.getTagCompound().setString("SkullOwner", "MHF_Enderman");
			}
			if (fEvent.getEntity() instanceof EntityPigZombie) {
				itemHead = new ItemStack(Items.SKULL, 1, 3);
				itemHead.setTagCompound(new NBTTagCompound());
				itemHead.getTagCompound().setString("SkullOwner", "MHF_PigZombie");
			}
			if (fEvent.getEntity() instanceof EntityBlaze) {
				itemHead = new ItemStack(Items.SKULL, 1, 3);
				itemHead.setTagCompound(new NBTTagCompound());
				itemHead.getTagCompound().setString("SkullOwner", "MHF_Blaze");
			}
			if (itemHead != null) {
				EntityItem entityItem = new EntityItem(fEvent.getEntity().world, fEvent.getEntity().posX, fEvent.getEntity().posY, fEvent.getEntity().posZ, itemHead);
				fEvent.getDrops().add(entityItem);
			}

		}
	}
}