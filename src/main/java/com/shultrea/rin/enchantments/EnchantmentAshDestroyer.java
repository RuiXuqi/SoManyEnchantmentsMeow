package com.shultrea.rin.enchantments;

import com.shultrea.rin.Interfaces.IDamageMultiplier;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_030;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAshDestroyer extends EnchantmentBase implements IDamageMultiplier {
	
	public EnchantmentAshDestroyer(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
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
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 14 + (par1 - 1) * 12;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.ashDestroyer;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return fTest != Enchantments.FIRE_ASPECT && fTest != Smc_010.FieryEdge && !(fTest instanceof IDamageMultiplier) && super.canApplyTogether(fTest);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void HandleEnchant(LivingDamageEvent fEvent) {
		if(!fEvent.getSource().damageType.equals("player") && !fEvent.getSource().damageType.equals("mob")) return;
		if(!(fEvent.getEntityLiving().isBurning())) return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		if(attacker == null) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		if(dmgSource == null) return;
		if(EnchantmentHelper.getEnchantmentLevel(Smc_030.AshDestroyer, dmgSource) <= 0) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		int levelAshBringer = EnchantmentHelper.getEnchantmentLevel(Smc_030.AshDestroyer, dmgSource);
		{
			//UtilityEntityDamager.damageEntity(fEvent.getEntityLiving(), somanyenchantments.PhysicalDamage, Damage);
			//fEvent.setAmount(fEvent.getAmount());
			float Final = EnchantmentsUtility.CalculateDamageIgnoreSwipe(fEvent.getAmount(), 0f, 0, 1.0f + (levelAshBringer * 0.2f), attacker, Smc_030.AshDestroyer);
			fEvent.setAmount(Final);
			//System.out.println("2nd");
			/**float Damager = fEvent.getAmount();
			 float SecondDamage = fEvent.getAmount();
			 
			 if(!(SecondDamage >= 4.0f))
			 return;
			 
			 SecondDamage = SecondDamage * (1 + (0.20f * levelAshBringer));
			 
			 Damager = (Damager * (1 + 0.20f * levelAshBringer) - 2.0f);
			 
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