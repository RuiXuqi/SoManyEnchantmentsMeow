package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityArrow.class)
public abstract class EntityArrowMixin {
	
	@Shadow
	public abstract void shoot(double x, double y, double z, float velocity, float inaccuracy);
	
	//TODO test inaccuracy works
	/**
	 * Handling for Curse of Inaccuracy
	 */
	@Inject(
			method = "shoot(Lnet/minecraft/entity/Entity;FFFFF)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/EntityArrow;shoot(DDDFF)V", shift = At.Shift.AFTER),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void soManyEnchantments_vanillaEntityArrow_shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy, CallbackInfo ci, float f, float f1, float f2) {
		if(!EnchantmentRegistry.curseOfInaccuracy.isEnabled()) return;
		if(!(shooter instanceof EntityLivingBase)) return;
		EntityLivingBase shooterLiving = (EntityLivingBase)shooter;
		
		ItemStack bow = shooterLiving.getHeldItemMainhand();
		if(!(bow.getItem() instanceof ItemBow)) {
			bow = shooterLiving.getHeldItemOffhand();
		}
		if(!(bow.getItem() instanceof ItemBow)) {
			return;
		}
		
		int inaccuracyLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.curseOfInaccuracy, bow);
		if(inaccuracyLevel > 0 && shooterLiving.getRNG().nextFloat() < ((float)inaccuracyLevel * 0.20F)) {
			this.shoot(f, f1, f2, velocity, inaccuracy + (float)inaccuracyLevel * 10.0F);
		}
	}
}