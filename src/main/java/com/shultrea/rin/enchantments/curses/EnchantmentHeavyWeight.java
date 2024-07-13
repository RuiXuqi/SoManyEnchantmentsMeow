package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class EnchantmentHeavyWeight extends EnchantmentBase {
	
	//TODO
	public static final UUID CACHED_UUID = UUID.fromString("e2765897-134f-4c14-a535-29c3ae5c7a21");
	
	public EnchantmentHeavyWeight(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.heavyWeight;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.heavyWeight;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 5 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 25;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.heavyWeight;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != Smc_010.SwifterSlashes;
	}
	
	@Override
	public boolean isCurse() {
		return true;
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
		int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.HeavyWeight, weapon);
		if(level > 0) AddAttackSpeedBuff(entity);
		else RemoveAttackSpeedBuff(entity);
	}
	
	//TODO
	private void AddAttackSpeedBuff(EntityLivingBase fEntity) {
		ItemStack weapon = fEntity.getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.HeavyWeight, weapon);
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED);
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "attackSpeed", (((double)level * 0.10D + 0.20D) * -1), 1);
		speedAttr.removeModifier(modSpeed);
		speedAttr.applyModifier(modSpeed);
		if(speedAttr.getModifier(CACHED_UUID) != null) {}
	}
	
	//TODO
	private void RemoveAttackSpeedBuff(EntityLivingBase fEntity) {
		ItemStack weapon = fEntity.getHeldItemMainhand();
		int level = EnchantmentHelper.getEnchantmentLevel(Smc_010.HeavyWeight, weapon);
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED);
		if(speedAttr.getModifier(CACHED_UUID) == null) return;
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "attackSpeed", (((double)level * 0.10D + 0.20D) * -1), 1);
		speedAttr.removeModifier(modSpeed);
	}
}