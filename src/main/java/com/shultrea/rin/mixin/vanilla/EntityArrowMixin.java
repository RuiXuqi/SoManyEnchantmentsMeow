package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.properties.ArrowPropertiesHandler;
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
		ArrowPropertiesHandler.setArrowCapabilities(entity,(EntityArrow)(Object) this);
	}
}