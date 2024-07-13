package com.shultrea.rin.Utility_Sector;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.registry.Smc_030;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AdditionalProtectionEnchantmentsEffects {
	
	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void protectionOnly(LivingHurtEvent fEvent) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		//System.out.println("EVENT EEEEEEEEEEEEEEE");
		if(!(ModConfig.enabled.advancedProtection)) return;
		if(fEvent.getSource().damageType.equals("null")) return;
		int modifier = EnchantmentsUtility.CalcModgetTotalLevel(4.75f, Smc_030.AdvancedProtection, fEvent.getEntityLiving());
		float damage = EnchantmentsUtility.getDamageAfterMagicAbsorb(fEvent.getAmount(), modifier);
		fEvent.setAmount(damage);
	}
	
	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void projprotection(LivingHurtEvent fEvent) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		//System.out.println("EVENT EEEEEEEEEEEEEEE");
		if(!(ModConfig.enabled.advancedProjectileProtection)) return;
		if(!(fEvent.getSource().isProjectile())) return;
		int modifier = EnchantmentsUtility.CalcModgetTotalLevel(7.5f, Smc_030.AdvancedProjectileProtection, fEvent.getEntityLiving());
		float damage = EnchantmentsUtility.getDamageAfterMagicAbsorb(fEvent.getAmount(), modifier);
		fEvent.setAmount(damage);
	}
	
	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void fireprotection(LivingDamageEvent fEvent) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		//System.out.println("EVENT EEEEEEEEEEEEEEE");
		if(!(ModConfig.enabled.advancedFireProtection)) return;
		if(!(fEvent.getSource().isFireDamage())) return;
		int modifier = EnchantmentsUtility.CalcModgetTotalLevel(7.5f, Smc_030.AdvancedFireProtection, fEvent.getEntityLiving());
		float damage = EnchantmentsUtility.getDamageAfterMagicAbsorb(fEvent.getAmount(), modifier);
		fEvent.setAmount(damage);
	}
	
	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void blastprotection(LivingHurtEvent fEvent) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		//System.out.println("EVENT EEEEEEEEEEEEEEE");
		if(!(ModConfig.enabled.advancedBlastProtection)) return;
		if(!(fEvent.getSource().isExplosion())) return;
		int modifier = EnchantmentsUtility.CalcModgetTotalLevel(7.5f, Smc_030.AdvancedBlastProtection, fEvent.getEntityLiving());
		float damage = EnchantmentsUtility.getDamageAfterMagicAbsorb(fEvent.getAmount(), modifier);
		fEvent.setAmount(damage);
	}
	
	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void featherfall(LivingHurtEvent fEvent) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		//System.out.println("EVENT EEEEEEEEEEEEEEE");
		if(!(ModConfig.enabled.advancedFeatherFalling)) return;
		if((fEvent.getSource() != DamageSource.FALL)) return;
		int modifier = EnchantmentsUtility.CalcModgetTotalLevel(9, Smc_030.AdvancedFeatherFalling, fEvent.getEntityLiving());
		float damage = EnchantmentsUtility.getDamageAfterMagicAbsorb(fEvent.getAmount(), modifier);
		fEvent.setAmount(damage);
	}
}
