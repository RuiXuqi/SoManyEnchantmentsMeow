package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.enchantments.armor.EnchantmentMagmaWalker;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity {
	
	@Shadow public abstract ItemStack getHeldItemMainhand();
	
	@Shadow public abstract ItemStack getHeldItemOffhand();
	
	@Shadow public abstract void setHealth(float health);
	
	@Shadow public abstract void clearActivePotions();
	
	@Shadow public abstract void addPotionEffect(PotionEffect potioneffectIn);
	
	@Shadow public abstract float getMaxHealth();
	
	@Shadow public int maxHurtResistantTime;
	
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
	private void soManyEnchantments_vanillaEntityLivingBase_frostWalk_head(BlockPos pos, CallbackInfo ci) {
		int i = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.magmaWalker, (EntityLivingBase)(Object)this);
		
		if(i > 0) {
			EnchantmentMagmaWalker.walkOnMagma((EntityLivingBase)(Object)this, this.world, pos, i);
		}
	}

	/**
	 * Handling for Rune Resurrection enchant
	 */
	@Inject(
			method = "checkTotemDeathProtection",
			at = @At("HEAD"),
			cancellable = true
	)
	private void soManyEnchantments_vanillaEntityLivingBase_checkTotemDeathProtection_head(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		if(!source.canHarmInCreative() && EnchantmentRegistry.runeResurrection.isEnabled()) {
			ItemStack stack = this.getHeldItemMainhand();
			int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeResurrection, stack);
			if(level <= 0) {
				stack = this.getHeldItemOffhand();
				level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeResurrection, stack);
			}
			
			if(level > 0) {
				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
				enchantments.remove(EnchantmentRegistry.runeResurrection);
				EnchantmentHelper.setEnchantments(enchantments, stack);
				
				this.setHealth(Math.min(this.getMaxHealth(), this.getMaxHealth() * 0.5F * (float)level));
				this.hurtResistantTime = this.maxHurtResistantTime + 10;
				this.clearActivePotions();
				this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
				this.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
				cir.setReturnValue(true);
			}
		}
	}
}