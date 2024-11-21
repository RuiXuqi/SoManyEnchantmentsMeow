package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.enchantments.armor.EnchantmentMagmaWalker;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity {
	
	public EntityLivingBaseMixin(World worldIn) {
		super(worldIn);
	}
	
	/**
	 * Handling for MagmaWalker enchant
	 */
	@Inject(
			method = "frostWalk",
			at = @At("HEAD")
	)
	public void soManyEnchantments_vanillaEntityLivingBase_frostWalk_head(BlockPos pos, CallbackInfo ci) {
		int i = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.magmaWalker, (EntityLivingBase)(Object)this);
		
		if(i > 0) {
			EnchantmentMagmaWalker.walkOnMagma((EntityLivingBase)(Object)this, this.world, pos, i);
		}
	}
}