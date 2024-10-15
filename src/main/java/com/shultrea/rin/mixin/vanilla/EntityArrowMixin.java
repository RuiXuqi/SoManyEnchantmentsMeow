package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityArrow.class)
public abstract class EntityArrowMixin {
	
	@Inject(
			method = "setEnchantmentEffectsFromEntity",
			at = @At("RETURN")
	)
	public void soManyEnchantments_vanillaEntityArrow_setEnchantmentEffectsFromEntity(EntityLivingBase entity, float distanceFactor, CallbackInfo ci) {
		int powerlessLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.powerless, entity);
		if(powerlessLevel > 0) {
			double damage = ((EntityArrow)(Object)this).getDamage();
			((EntityArrow)(Object)this).setDamage(damage - (0.5D + (double)powerlessLevel * 0.5D));
			if(powerlessLevel > 2 || entity.getRNG().nextFloat() < powerlessLevel * 0.4F) {
				((EntityArrow)(Object)this).setIsCritical(false);
			}
		}
		
		int runeArrowPiercingLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.runeArrowPiercing, entity);
		if(runeArrowPiercingLevel > 0) {
			IArrowProperties cap = ((EntityArrow)(Object)this).getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
			if(cap != null) cap.setPiercingLevel(runeArrowPiercingLevel);
		}
	}
}