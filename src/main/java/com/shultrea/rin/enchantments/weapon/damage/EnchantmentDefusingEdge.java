package com.shultrea.rin.enchantments.weapon.damage;

import com.shultrea.rin.Interfaces.IEnchantmentDamage;
import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDefusingEdge extends EnchantmentBase implements IEnchantmentDamage {
	
	public EnchantmentDefusingEdge(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.defusingEdge;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.defusingEdge;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.defusingEdge, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.defusingEdge, level);
	}
	
	//TODO
	@Override
	public boolean canApply(ItemStack fTest) {
		return fTest.getItem() instanceof ItemAxe || super.canApply(fTest);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.defusingEdge;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
        int levelDefusing = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.defusingEdge, dmgSource);
		if(levelDefusing <= 0) return;
		if(fEvent.getEntity() instanceof EntityCreeper) {
			float Damage = fEvent.getAmount();
			UtilityAccessor.damageTarget(fEvent.getEntityLiving(), SoManyEnchantments.PhysicalDamage, levelDefusing * 2.5f);
			fEvent.setAmount(Damage);
			if(Math.random() < 0.02f * levelDefusing) {
				EntityCreeper creeper = (EntityCreeper)fEvent.getEntityLiving();
				NBTTagCompound fuse = new NBTTagCompound();
				creeper.writeEntityToNBT(fuse);
				short fuseTime = 32767;
				fuse.setShort("Fuse", fuseTime);
				creeper.readEntityFromNBT(fuse);
			}
		}
	}
}