package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Interfaces.IEnchantmentCurse;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.UUID;

public class EnchantmentCurseofHolding extends EnchantmentBase implements IEnchantmentCurse {
	
	//TODO
	public static final UUID CACHED_UUID = UUID.fromString("e2125897-134f-4c14-a535-19c2ae4c7a21");
	//TODO
	int interval;
	
	public EnchantmentCurseofHolding(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.curseOfHolding;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.curseOfHolding;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 20 * par1;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfHolding;
	}
	
	//TODO
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	//TODO
	@SubscribeEvent
	public void onExist(PlayerTickEvent e) {
		if(e.phase == Phase.START) return;
		if(e.player == null) return;
		if(interval > 10) {
			int level = EnchantmentHelper.getEnchantmentLevel(this, e.player.getHeldItemMainhand());
			int level2 = EnchantmentHelper.getEnchantmentLevel(this, e.player.getHeldItemOffhand());
			if(level2 > level) level = level2;
			if(level > 0) {
				e.player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, level - 1, false, false));
				e.player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20, level > 1 ? 1 : 0, false, false));
				if(level > 1) {
					e.player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 10, level - 1, false, false));
					e.player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 10, level - 1, false, false));
				}
			}
			interval = 0;
			if(level > 0) curseUnluck(e.player, level);
			else removeCurse(e.player, level);
		}
		else interval++;
	}
	
	//TODO
	private void curseUnluck(EntityLivingBase fEntity, int level) {
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.LUCK);
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "luckModifier", (((double)level) * -1), 0);
		speedAttr.removeModifier(modSpeed);
		speedAttr.applyModifier(modSpeed);
		if(speedAttr.getModifier(CACHED_UUID) != null) {}
	}
	
	//TODO
	private void removeCurse(EntityLivingBase fEntity, int level) {
		IAttributeInstance speedAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.LUCK);
		if(speedAttr.getModifier(CACHED_UUID) == null) return;
		AttributeModifier modSpeed = new AttributeModifier(CACHED_UUID, "luckModifier", (((double)level) * -1), 1);
		speedAttr.removeModifier(modSpeed);
	}
}