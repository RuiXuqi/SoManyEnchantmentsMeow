package com.shultrea.rin.enchantments.weapon.subject;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentSubjectEnchantments extends EnchantmentBase {
	
	private static final int BIOLOGY = 0;
	private static final int CHEMISTRY = 1;
	private static final int ENGLISH = 2;
	private static final int HISTORY = 3;
	private static final int MATHEMATICS = 4;
	private static final int PE = 5;
	private static final int PHYSICS = 6;
	
	private final int damageType;
	
	public EnchantmentSubjectEnchantments(String name, Rarity rarity, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
		this.damageType = damageTypeIn;
	}
	
	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case BIOLOGY: return ModConfig.enabled.subjectBiology;
			case CHEMISTRY: return ModConfig.enabled.subjectChemistry;
			case ENGLISH: return ModConfig.enabled.subjectEnglish;
			case HISTORY: return ModConfig.enabled.subjectHistory;
			case MATHEMATICS: return ModConfig.enabled.subjectMathematics;
			case PE: return ModConfig.enabled.subjectPE;
			case PHYSICS: return ModConfig.enabled.subjectPhysics;
			default: return false;
		}
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		switch(this.damageType) {
			case BIOLOGY: return ModConfig.level.subjectBiology;
			case CHEMISTRY: return ModConfig.level.subjectChemistry;
			case ENGLISH: return ModConfig.level.subjectEnglish;
			case HISTORY: return ModConfig.level.subjectHistory;
			case MATHEMATICS: return ModConfig.level.subjectMathematics;
			case PE: return ModConfig.level.subjectPE;
			case PHYSICS: return ModConfig.level.subjectPhysics;
			default: return 4;
		}
	}

	@Override
	public int getMinEnchantability(int level) {
		switch(this.damageType) {
			case BIOLOGY: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectBiology, level);
			case CHEMISTRY: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectChemistry, level);
			case ENGLISH: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectEnglish, level);
			case HISTORY: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectHistory, level);
			case MATHEMATICS: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectMathematics, level);
			case PE: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectPE, level);
			case PHYSICS: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectPhysics, level);
			default: return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch(this.damageType) {
			case BIOLOGY: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectBiology, level);
			case CHEMISTRY: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectChemistry, level);
			case ENGLISH: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectEnglish, level);
			case HISTORY: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectHistory, level);
			case MATHEMATICS: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectMathematics, level);
			case PE: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectPE, level);
			case PHYSICS: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectPhysics, level);
			default: return 0;
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		switch(this.damageType) {
			case BIOLOGY: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectBiology, stack) && super.canApplyAtEnchantingTable(stack);
			case CHEMISTRY: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectChemistry, stack) && super.canApplyAtEnchantingTable(stack);
			case ENGLISH: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectEnglish, stack) && super.canApplyAtEnchantingTable(stack);
			case HISTORY: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectHistory, stack) && super.canApplyAtEnchantingTable(stack);
			case MATHEMATICS: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectMathematics, stack) && super.canApplyAtEnchantingTable(stack);
			case PE: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectPE, stack) && super.canApplyAtEnchantingTable(stack);
			case PHYSICS: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectPhysics, stack) && super.canApplyAtEnchantingTable(stack);
			default: return false;
		}
	}

	@Override
	public boolean canApply(ItemStack stack){
		switch(this.damageType) {
			case BIOLOGY: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectBiology, stack) || super.canApply(stack);
			case CHEMISTRY: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectChemistry, stack) || super.canApply(stack);
			case ENGLISH: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectEnglish, stack) || super.canApply(stack);
			case HISTORY: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectHistory, stack) || super.canApply(stack);
			case MATHEMATICS: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectMathematics, stack) || super.canApply(stack);
			case PE: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectPE, stack) || super.canApply(stack);
			case PHYSICS: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectPhysics, stack) || super.canApply(stack);
			default: return false;
		}
	}

	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case BIOLOGY: return ModConfig.treasure.subjectBiology;
			case CHEMISTRY: return ModConfig.treasure.subjectChemistry;
			case ENGLISH: return ModConfig.treasure.subjectEnglish;
			case HISTORY: return ModConfig.treasure.subjectHistory;
			case MATHEMATICS: return ModConfig.treasure.subjectMathematics;
			case PE: return ModConfig.treasure.subjectPE;
			case PHYSICS: return ModConfig.treasure.subjectPhysics;
			default: return true;
		}
	}

	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isOnEntityDamagedAltStrong()) return;
		if(attacker == null) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;
		
		if(this.damageType == PE) {
			if(attacker.getRNG().nextFloat() < 0.05F * (float)level && !attacker.world.isRemote) {
				attacker.addPotionEffect(new PotionEffect(MobEffects.HASTE, 120 + (level * 20), Math.min(3, level - 1)));
				attacker.addPotionEffect(new PotionEffect(MobEffects.SPEED, 80 + (level * 20), Math.min(3, level - 1)));
				if(level > 2) {
					attacker.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60 + (level * 20), level - 3));
					attacker.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 60 + (level * 20), level - 3));
				}
				if(level > 4) {
					attacker.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 + (level * 20), level - 5));
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isAttackEntityFromStrong()) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(this.damageType == BIOLOGY) {
				if(!(victim instanceof EntityLiving)) return;
				int taskCount = ((EntityLiving)victim).tasks.taskEntries.size();
				if(taskCount > 15 - level) {
					event.setAmount(event.getAmount() + 2.5F * (float)level);
				}
			}
			else if(this.damageType == CHEMISTRY) {
				int count = attacker.getActivePotionEffects().size();
				if(count > 0) {
					event.setAmount(event.getAmount() + 0.25F * (float)level * (float)count);
				}
			}
			else if(this.damageType == ENGLISH) {
				int count = victim.getName().length();
				if(count > 0) {
					event.setAmount(event.getAmount() + 0.25F * (float)level * (float)count);
				}
			}
			else if(this.damageType == HISTORY) {
				float dmg = 1.5F * (float)level * attacker.world.getDifficultyForLocation(attacker.getPosition()).getAdditionalDifficulty() / 6.75F;
				if(dmg > 0) {
					event.setAmount(event.getAmount() + dmg);
				}
			}
			else if(this.damageType == MATHEMATICS) {
				float perc = victim.getHealth() / victim.getMaxHealth();
				float dmg = 1.5F * (float)level * MathHelper.clamp(4.0F*perc*(1.0F-perc),0F,1F);
				if(dmg > 0) {
					event.setAmount(event.getAmount() + dmg);
				}
			}
			else if(this.damageType == PHYSICS) {
				AxisAlignedBB victimAABB = victim.getEntityBoundingBox();
				AxisAlignedBB attackerAABB = attacker.getEntityBoundingBox();
				double victimSize = (victimAABB.maxX - victimAABB.minX) * (victimAABB.maxY - victimAABB.minY) * (victimAABB.maxZ - victimAABB.minZ);
				double attackerSize = (attackerAABB.maxX - attackerAABB.minX) * (attackerAABB.maxY - attackerAABB.minY) * (attackerAABB.maxZ - attackerAABB.minZ);
				if(victimSize > 0 && attackerSize > 0) {
					float perc = (float)(victimSize / attackerSize);
					if(perc < 1) perc = 1.0F / perc;
					event.setAmount(event.getAmount() + 0.25F * (float)level * Math.min(15.0F, perc));
				}
			}
		}
	}
}