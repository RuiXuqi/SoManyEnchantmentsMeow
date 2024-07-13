package com.shultrea.rin.enchantments;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class EnchantmentSwifterSlashes extends EnchantmentBase {
	
	//TODO
	public static final UUID CACHED_UUID = UUID.fromString("e6109481-134f-4c54-a535-29c3ae5c7a21");
	
	public EnchantmentSwifterSlashes(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.swifterSlashes;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.swifterSlashes;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 5 + 10 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 40;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.swifterSlashes;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != Smc_010.Bluntness && fTest != Enchantments.KNOCKBACK && fTest != Smc_010.BlessedEdge && fTest != Smc_010.ReviledBlade && fTest != Smc_010.CursedEdge;
	}
	
	//TODO
	public boolean isValidPlayer(Entity entity) {
		if(entity instanceof EntityPlayer) {
			if(((EntityPlayer)entity).getHeldItemMainhand() != null) {
				return level(((EntityPlayer)entity).getHeldItemMainhand()) > 0;
			}
		}
		return false;
	}
	
	//TODO
	public boolean isValidMob(Entity entity) {
		if(entity instanceof EntityMob || entity instanceof EntityAnimal) {
			if(((EntityMob)entity).getHeldItemMainhand() != null) {
				if(level(((EntityMob)entity).getHeldItemMainhand()) > 0) {
					return true;
				}
			}
			if(((EntityAnimal)entity).getHeldItemMainhand() != null) {
				return level(((EntityAnimal)entity).getHeldItemMainhand()) > 0;
			}
		}
		return false;
	}
	
	//TODO
	public int level(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(Smc_010.SwifterSlashes, stack);
	}
	
	//TODO
	@SubscribeEvent
	public void onEntityHit(AttackEntityEvent event) {
		if(isValidPlayer(event.getEntityPlayer()) || isValidMob(event.getEntityLiving())) {
			ItemStack stack = event.getEntityLiving().getHeldItemMainhand();
			if(EnchantmentHelper.getEnchantmentLevel(Smc_010.SwifterSlashes, stack) == 0) return;
			int levelFasterStrikes = EnchantmentHelper.getEnchantmentLevel(Smc_010.SwifterSlashes, stack);
			if(event.getEntity().world.rand.nextInt(100) < 25 + (level(stack))) {
				event.getTarget().hurtResistantTime = 23 - (level(stack) * 3);
			}
		}
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack weapon = attacker.getHeldItemMainhand();
		if(weapon == null) return;
		int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.SwifterSlashes, weapon);
		if(EnchantmentHelper.getEnchantmentLevel(Smc_010.SwifterSlashes, weapon) <= 0) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		if(fEvent.getEntityLiving().world.rand.nextInt(100) < 25 + (level * 4)) {
			fEvent.getEntityLiving().hurtResistantTime = 23 - (level * 3);
		}
	}
	
	//TODO
	@SubscribeEvent
	public void HandleEnchant(LivingUpdateEvent fEvent) {
		if(!(fEvent.getEntity() instanceof EntityPlayer)) return;
		EntityLivingBase entity = (EntityLivingBase)fEvent.getEntity();
		ItemStack weapon = entity.getHeldItemMainhand();
		if(weapon == null) {
			RemoveAttackSpeedBuff(entity);
			return;
		}
		int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.SwifterSlashes, weapon);
		if(level > 0) AddAttackSpeedBuff(entity);
		else RemoveAttackSpeedBuff(entity);
	}
	
	private void AddAttackSpeedBuff(EntityLivingBase fEntity) {
		ItemStack weapon = fEntity.getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.SwifterSlashes, weapon);
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED);
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "attackSpeed", ((double)level * 0.45D + (0.04D * level)), 1);
		speedAttr.removeModifier(modSpeed);
		speedAttr.applyModifier(modSpeed);
		if(speedAttr.getModifier(CACHED_UUID) != null) {}
	}
	
	private void RemoveAttackSpeedBuff(EntityLivingBase fEntity) {
		ItemStack weapon = fEntity.getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.SwifterSlashes, weapon);
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED);
		if(speedAttr.getModifier(CACHED_UUID) == null) return;
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "attackSpeed", ((double)level * 0.45D + (0.04D * level)), 1);
		speedAttr.removeModifier(modSpeed);
	}
}