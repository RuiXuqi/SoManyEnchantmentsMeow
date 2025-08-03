package com.shultrea.rin.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityArrow.class)
public abstract class EntityArrowMixin {
	/**
	 * Handling for Curse of Inaccuracy
	 */
	@WrapOperation(
			method = "shoot(Lnet/minecraft/entity/Entity;FFFFF)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/EntityArrow;shoot(DDDFF)V")
	)
	@SuppressWarnings("ConstantConditions") //suppresses bow==null being unnecessary. We need it anyway cause SwitchBow mod has a (fake) entity that returns null ItemStacks
	private void soManyEnchantments_vanillaEntityArrow_shoot(EntityArrow instance, double x, double y, double z, float velocity, float inaccuracy, Operation<Void> original, @Local(argsOnly = true) Entity shooter) {
		if(!EnchantmentRegistry.curseOfInaccuracy.isEnabled() || !(shooter instanceof EntityLivingBase)){
			original.call(instance, x, y, z, velocity, inaccuracy);
			return;
		}
		EntityLivingBase shooterLiving = (EntityLivingBase)shooter;
		ItemStack bow = shooterLiving.getHeldItemMainhand();
		if(bow == null || !(bow.getItem() instanceof ItemBow))
			bow = shooterLiving.getHeldItemOffhand();
		if(bow == null || !(bow.getItem() instanceof ItemBow)) {
			original.call(instance, x, y, z, velocity, inaccuracy);
			return;
		}
		
		int inaccuracyLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.curseOfInaccuracy, bow);
		if(inaccuracyLevel > 0 && shooterLiving.getRNG().nextFloat() < ((float)inaccuracyLevel * 0.20F))
			original.call(instance, x, y, z, velocity, inaccuracy + (float)inaccuracyLevel * 10.0F);
		else
			original.call(instance, x, y, z, velocity, inaccuracy);
	}
}