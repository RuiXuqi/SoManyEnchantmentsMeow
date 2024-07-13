package com.shultrea.rin.enchantments.tier;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_030;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentAdvancedLooting extends EnchantmentBase {
	
	public EnchantmentAdvancedLooting(String name, Rarity rarity, EnumEnchantmentType type) {
		super(Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setName("AdvancedLooting");
		this.setRegistryName("AdvancedLooting");
	}
	
	//TODO
	public static int getValue(int original, EntityLivingBase entity) {
		if(!Smc_030.AdvancedLooting.isEnabled()) return 0;
		int levelLooting = EnchantmentHelper.getMaxEnchantmentLevel(Smc_030.AdvancedLooting, entity);
		if(levelLooting <= 0) return 0;
		int toReturn = original + 2 + ((levelLooting - 1) * 2);
		if(Math.random() < 0.25f) toReturn = toReturn + 2 + (levelLooting * 2);
		return toReturn;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedLooting;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedLooting;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 30 + 15 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 60;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedLooting;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Enchantments.LOOTING && super.canApplyTogether(fTest);
	}
	
	/*
	@SubscribeEvent(priority = EventPriority.LOWEST) 
	public void HandleEnchant(LootingLevelEvent fEvent)
	{	
		if(!(fEvent.getDamageSource().getTrueSource() instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer) fEvent.getDamageSource().getTrueSource();
		ItemStack sword = player.getHeldItemMainhand();
					
		if(sword == null)
			return;
		
		int levelLooting = EnchantmentHelper.getEnchantmentLevel(Smc_030.AdvancedLooting, sword);
			
		if(levelLooting <= 0)
			return;
		
		fEvent.setLootingLevel(fEvent.getLootingLevel() + 2 + ((levelLooting - 1) * 2));
			
		if(Math.random() < 0.25f){
			
		fEvent.setLootingLevel(fEvent.getLootingLevel() + 2 + (levelLooting * 2));
		}	 
	}
	*/
}