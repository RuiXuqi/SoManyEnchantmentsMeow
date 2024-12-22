package com.shultrea.rin.enchantments.weapon.subject;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentSubjectEnchantments extends EnchantmentBase {
	
	private static final int ENGLISH = 0;
	private static final int HISTORY = 1;
	private static final int MATHEMATICS = 2;
	private static final int PE = 3;
	private static final int PHYSICS = 4;
	private static final int SCIENCE = 5;
	
	private final int damageType;
	
	public EnchantmentSubjectEnchantments(String name, Rarity rarity, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
		this.damageType = damageTypeIn;
	}
	
	@Override
	public boolean isEnabled() {
		switch(this.damageType) {
			case ENGLISH: return ModConfig.enabled.subjectEnglish;
			case HISTORY: return ModConfig.enabled.subjectHistory;
			case MATHEMATICS: return ModConfig.enabled.subjectMathematics;
			case PE: return ModConfig.enabled.subjectPE;
			case PHYSICS: return ModConfig.enabled.subjectPhysics;
			case SCIENCE: return ModConfig.enabled.subjectScience;
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
			case ENGLISH: return ModConfig.level.subjectEnglish;
			case HISTORY: return ModConfig.level.subjectHistory;
			case MATHEMATICS: return ModConfig.level.subjectMathematics;
			case PE: return ModConfig.level.subjectPE;
			case PHYSICS: return ModConfig.level.subjectPhysics;
			case SCIENCE: return ModConfig.level.subjectScience;
			default: return 4;
		}
	}

	@Override
	public int getMinEnchantability(int level) {
		switch(this.damageType) {
			case ENGLISH: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectEnglish, level);
			case HISTORY: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectHistory, level);
			case MATHEMATICS: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectMathematics, level);
			case PE: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectPE, level);
			case PHYSICS: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectPhysics, level);
			case SCIENCE: return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectScience, level);
			default: return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch(this.damageType) {
			case ENGLISH: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectEnglish, level);
			case HISTORY: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectHistory, level);
			case MATHEMATICS: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectMathematics, level);
			case PE: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectPE, level);
			case PHYSICS: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectPhysics, level);
			case SCIENCE: return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectScience, level);
			default: return 0;
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		switch(this.damageType) {
			case ENGLISH: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectEnglish, stack) && super.canApplyAtEnchantingTable(stack);
			case HISTORY: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectHistory, stack) && super.canApplyAtEnchantingTable(stack);
			case MATHEMATICS: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectMathematics, stack) && super.canApplyAtEnchantingTable(stack);
			case PE: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectPE, stack) && super.canApplyAtEnchantingTable(stack);
			case PHYSICS: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectPhysics, stack) && super.canApplyAtEnchantingTable(stack);
			case SCIENCE: return ModConfig.canApply.isItemValid(ModConfig.canApply.subjectScience, stack) && super.canApplyAtEnchantingTable(stack);
			default: return false;
		}
	}

	@Override
	public boolean canApply(ItemStack stack){
		switch(this.damageType) {
			case ENGLISH: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectEnglish, stack) || super.canApply(stack);
			case HISTORY: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectHistory, stack) || super.canApply(stack);
			case MATHEMATICS: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectMathematics, stack) || super.canApply(stack);
			case PE: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectPE, stack) || super.canApply(stack);
			case PHYSICS: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectPhysics, stack) || super.canApply(stack);
			case SCIENCE: return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.subjectScience, stack) || super.canApply(stack);
			default: return false;
		}
	}

	@Override
	public boolean isTreasureEnchantment() {
		switch(this.damageType) {
			case ENGLISH: return ModConfig.treasure.subjectEnglish;
			case HISTORY: return ModConfig.treasure.subjectHistory;
			case MATHEMATICS: return ModConfig.treasure.subjectMathematics;
			case PE: return ModConfig.treasure.subjectPE;
			case PHYSICS: return ModConfig.treasure.subjectPhysics;
			case SCIENCE: return ModConfig.treasure.subjectScience;
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
			if(user.getRNG().nextFloat() < 0.085f) {
				if(level <= 2) {
					user.addPotionEffect(new PotionEffect(MobEffects.HASTE, 150 + (level * 30), level - 1));
					user.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 + (level * 5), level - 1));
				}
				if(level >= 3) {
					user.addPotionEffect(new PotionEffect(MobEffects.HASTE, 150 + (level * 30), level - 2));
					user.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 + (level * 5), level - 2));
					user.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10 + (level * 5), level - 4));
					user.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 + (level * 5), level - 3));
				}
				if(level >= 5) {
					user.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 10 + (level * 5), level - 1));
					user.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20 + (level * 5), level - 2));
				}
			}
		}
		if(this.damageType == PHYSICS) {
			if(user.getRNG().nextFloat() < 0.0833) {
				user.getEntityWorld().newExplosion(user, target.posX, target.posY - 1.5D, target.posZ, 1.0f + (level * 0.65f), true, false);
				user.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 60, 0, true, true));
			}
		}
		if(this.damageType == SCIENCE) {
			if(user.getRNG().nextFloat() < 0.2f) {
				user.getEntityWorld().newExplosion(user, target.posX, target.posY - 1.5D, target.posZ, 1.1f + (level * 0.4f), false, false);
			}
		}
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		if(damageType == SCIENCE & isEnabled())
			return 0.8F + level * 0.3F;
		return 0F;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingHurt(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase) fEvent.getSource().getTrueSource();
		if(attacker == null) return;
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
			float percHealth = fEvent.getEntityLiving().getHealth()/fEvent.getEntityLiving().getMaxHealth();
			float maths = MathHelper.clamp(4*percHealth*(1-percHealth),0F,1F);	//quadratic function, 0 for percHealth=0% and percHealth=100%, 1 for percHealth=50%
			float addedDmg = maths * levelMath * 1.5F;
			fEvent.setAmount(fEvent.getAmount() + addedDmg);
		}
		if(levelHistory > 0) {
			float localDifficulty = attacker.getEntityWorld().getDifficultyForLocation(attacker.getPosition()).getAdditionalDifficulty();
			float addedDmg = (localDifficulty / 6.75f) * levelHistory * 1.5f;
			fEvent.setAmount(fEvent.getAmount() + addedDmg);
		}
		if(levelEnglish > 0) {
			EntityLivingBase victim = fEvent.getEntityLiving();
			if(!(victim instanceof EntityLiving)) return;
			int taskCount = ((EntityLiving) victim).tasks.taskEntries.size();
			if(taskCount > 12){
				fEvent.setAmount(fEvent.getAmount() + 1.5F * levelEnglish);
				if(attacker.getRNG().nextFloat() <= 0.4f) fEvent.getEntityLiving().setSilent(true);
			}
		}
	}
}