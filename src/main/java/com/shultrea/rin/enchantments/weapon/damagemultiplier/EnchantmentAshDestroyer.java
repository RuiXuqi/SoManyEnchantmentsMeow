package com.shultrea.rin.enchantments.weapon.damagemultiplier;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAshDestroyer extends EnchantmentBase {
	
	public EnchantmentAshDestroyer(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.ashDestroyer;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.ashDestroyer;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.ashDestroyer, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.ashDestroyer, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.ashDestroyer, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.ashDestroyer, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.ashDestroyer;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HandleEnchant(LivingDamageEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.ashDestroyer, dmgSource);
		if(enchantmentLevel <= 0) return;

		//UtilityEntityDamager.damageEntity(fEvent.getEntityLiving(), somanyenchantments.PhysicalDamage, Damage);
		//fEvent.setAmount(fEvent.getAmount());
		float Final = EnchantmentsUtility.modifyDamage(fEvent.getAmount(), 0f, 0, 1.0f + (enchantmentLevel * 0.2f), enchantmentLevel);
		fEvent.setAmount(Final);
		//System.out.println("2nd");
		/**float Damager = fEvent.getAmount();
		 float SecondDamage = fEvent.getAmount();

		 if(!(SecondDamage >= 4.0f))
		 return;

		 SecondDamage = SecondDamage * (1 + (0.20f * enchantmentLevel));

		 Damager = (Damager * (1 + 0.20f * enchantmentLevel) - 2.0f);

		 if(Damager == 0.0f){
		 Damager = SecondDamage;
		 }

		 float percentage = SecondDamage / Damager;

		 //System.out.println(SecondDamage);
		 //System.out.println(Damage);

		 float newDamage = Damager * percentage;


		 Damage = newDamage;
		 //fEvent.setAmount(newDamage);
		 */

	}
	/**@Override public void onEntityDamaged (EntityLivingBase user, Entity target, int level) {
	if(user instanceof EntityPlayer){
	if(target.isBurning()){
	
	
	
	//UtilityAccessor.damageEntity((EntityLivingBase)target, DamageSource.causePlayerDamage((EntityPlayer)user), Damage);
	target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)user), damages * (level * 0.2f));
	
	
	}
	
	}
	else if(!(user instanceof EntityPlayer)){
	// target.hurtResistantTime = 0;
	float damages = e.getDamage();
	target.attackEntityFrom(DamageSource.causeMobDamage(user), damages);
	}
	else return;
	}
	 */
}