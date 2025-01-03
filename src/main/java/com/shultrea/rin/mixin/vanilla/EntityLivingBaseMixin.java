package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.enchantments.armor.EnchantmentMagmaWalker;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.IEntityDamageSourceMixin;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.SpartanWeaponryCompat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity {
	
	@Shadow public int maxHurtResistantTime;
	
	@Shadow public abstract ItemStack getHeldItemMainhand();
	
	@Shadow public abstract ItemStack getHeldItemOffhand();
	
	@Shadow public abstract void setHealth(float health);
	
	@Shadow public abstract void clearActivePotions();
	
	@Shadow public abstract void addPotionEffect(PotionEffect potioneffectIn);
	
	@Shadow public abstract float getMaxHealth();
	
	@Shadow protected abstract float applyArmorCalculations(DamageSource source, float damage);
	
	@Shadow protected abstract void damageArmor(float damage);
	
	@Shadow public abstract int getTotalArmorValue();
	
	@Shadow public abstract IAttributeInstance getEntityAttribute(IAttribute attribute);
	
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
	
	/**
	 * Handling for piercing enchants
	 */
	@Redirect(
			method = "damageEntity",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;applyArmorCalculations(Lnet/minecraft/util/DamageSource;F)F")
	)
	private float soManyEnchantments_vanillaEntityLivingBase_damageEntity(EntityLivingBase instance, DamageSource source, float damage) {
		if(!source.isUnblockable() && source instanceof EntityDamageSource && ((IEntityDamageSourceMixin)source).soManyEnchantments$getPiercingPercent() > 0.0F) {
			float percent = ((IEntityDamageSourceMixin)source).soManyEnchantments$getPiercingPercent();
			//Handle SpartanWeaponry piercing ourselves since this would cancel its handling
			if(CompatUtil.isSpartanWeaponryLoaded()) percent += SpartanWeaponryCompat.getDamageSourcePiercing(source);
			percent = Math.max(0, Math.min(1.0F, percent));
			
			this.damageArmor(damage);
			
			float piercingDamage = damage * percent;
			float normalDamage = damage - piercingDamage;
			float absorbedNormalDamage = CombatRules.getDamageAfterAbsorb(normalDamage, (float)this.getTotalArmorValue(), (float)this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
			return piercingDamage + absorbedNormalDamage;
		}
		else return this.applyArmorCalculations(source, damage);
	}
}