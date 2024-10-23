package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.Interfaces.IPotionDebuffer;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class EnchantmentFreezing extends EnchantmentBase implements IPotionDebuffer {
	
	public EnchantmentFreezing(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.freezing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.freezing;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.freezing, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.freezing, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.freezing;
	}
	
	//TODO
//	@Override
//	public boolean canApplyTogether(Enchantment fTest) {
//		return super.canApplyTogether(fTest) && !(fTest instanceof IPotionDebuffer);
//	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntityDamaged(LivingDamageEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack stack = attacker.getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.freezing, stack);
		if(enchantmentLevel <= 0) return;
		EntityLivingBase victim = fEvent.getEntityLiving();
		int ice = 0;
		int numb = 0;

		int ampAdd = victim instanceof EntityPlayer ? 0 : 1;

		//Capping at 3 (previously was 7) for Roguelike Dungeons compatibility
		int numbCap = 3;
		int iceCap = 7;
		if(victim.isPotionActive(MobEffects.SLOWNESS) & victim.isPotionActive(MobEffects.MINING_FATIGUE)) {
			PotionEffect pot1 = victim.getActivePotionEffect(MobEffects.SLOWNESS);
			PotionEffect pot2 = victim.getActivePotionEffect(MobEffects.MINING_FATIGUE);
			ice = Math.min(iceCap, pot1.getAmplifier() + 1 + ampAdd);
			numb = Math.min(numbCap, pot2.getAmplifier() + 1 + ampAdd);

			victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30 * enchantmentLevel + 40, ice));
			victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 30 * enchantmentLevel + 40, numb));
		}
		else {
			victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * enchantmentLevel + 40, ampAdd));
			victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 * enchantmentLevel + 40, ampAdd));
		}

		if(ice >= iceCap && numb >= numbCap && victim.getEntityWorld().getGameRules().getBoolean("mobGriefing")) {
			float chance = (float)Math.random() * 100f;
			if(chance < (enchantmentLevel * 10)) {
				victim.extinguish();
				stack.damageItem(6 - enchantmentLevel, attacker);
				float damage = EnchantmentsUtility.modifyDamage(fEvent.getAmount(), 2.0f, 0.65f, 1.0f, enchantmentLevel);
				if(!(attacker instanceof EntityPlayer)) {
					EnchantmentsUtility.ImprovedKnockBack(attacker, 0.25f, attacker.posX - victim.posX, attacker.posZ - victim.posZ);
				}
				victim.getEntityWorld().playSound(null, victim.posX, victim.posY, victim.posZ, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.MASTER, 0.8f, -1f);
				ArrayList<BlockPos> posList = new ArrayList<>();
				posList.add(new BlockPos(victim.posX, victim.posY, victim.posZ));
				posList.add(new BlockPos(victim.posX, victim.posY + 1, victim.posZ));
				posList.add(new BlockPos(victim.posX, victim.posY + 2, victim.posZ));
				posList.add(new BlockPos(victim.posX - 1, victim.posY, victim.posZ));
				posList.add(new BlockPos(victim.posX + 1, victim.posY, victim.posZ));
				posList.add(new BlockPos(victim.posX, victim.posY, victim.posZ - 1));
				posList.add(new BlockPos(victim.posX, victim.posY, victim.posZ + 1));

				if(enchantmentLevel >= 3) {
					damage += EnchantmentsUtility.modifyDamage(fEvent.getAmount(), 0.0f, 0.25f, 1.10f, enchantmentLevel);
					posList.add(new BlockPos(victim.posX - 1, victim.posY, victim.posZ - 1));
					posList.add(new BlockPos(victim.posX - 1, victim.posY, victim.posZ + 1));
					posList.add(new BlockPos(victim.posX + 1, victim.posY, victim.posZ - 1));
					posList.add(new BlockPos(victim.posX + 1, victim.posY, victim.posZ + 1));

					posList.add(new BlockPos(victim.posX - 2, victim.posY, victim.posZ));
					posList.add(new BlockPos(victim.posX + 2, victim.posY, victim.posZ));

					posList.add(new BlockPos(victim.posX, victim.posY, victim.posZ - 2));
					posList.add(new BlockPos(victim.posX, victim.posY, victim.posZ + 2));

					posList.add(new BlockPos(victim.posX - 1, victim.posY + 1, victim.posZ));
					posList.add(new BlockPos(victim.posX + 1, victim.posY + 1, victim.posZ));

					posList.add(new BlockPos(victim.posX, victim.posY + 1, victim.posZ - 1));
					posList.add(new BlockPos(victim.posX, victim.posY + 1, victim.posZ + 1));
				}

				for(BlockPos pos: posList) {
					if (victim.getEntityWorld().mayPlace(Blocks.FROSTED_ICE, pos, true, attacker.getAdjustedHorizontalFacing(), attacker)) {
						victim.getEntityWorld().setBlockState(pos, Blocks.FROSTED_ICE.getDefaultState());
						victim.getEntityWorld().scheduleUpdate(pos, Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 120, 240));
					}
				}

				fEvent.setAmount(damage);
			}
		}
	}
}