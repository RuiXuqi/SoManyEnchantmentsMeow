package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.util.IEntityDamageSourceMixin;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.SpartanWeaponryCompat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ISpecialArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ISpecialArmor.ArmorProperties.class)
public abstract class ISpecialArmorArmorPropertiesMixin {
	
	/**
	 * Handling for piercing enchants against players
	 */
	@Redirect(
			method = "applyArmor",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CombatRules;getDamageAfterAbsorb(FFF)F")
	)
	private static float soManyEnchantments_vanillaISpecialArmorArmorProperties_applyArmor(float damage, float totalArmor, float toughnessAttribute, EntityLivingBase entity, NonNullList<ItemStack> inventory, DamageSource source, double damageOld) {
		if(!source.isUnblockable() && source instanceof EntityDamageSource) {
			float percent = ((IEntityDamageSourceMixin)source).soManyEnchantments$getPiercingPercent();
			//Handle SpartanWeaponry piercing ourselves since spartan pierce doesn't normally have handling against players
			if(CompatUtil.isSpartanWeaponryLoaded()) percent += SpartanWeaponryCompat.getDamageSourcePiercing(source);
			percent = Math.max(0, Math.min(1.0F, percent));
			
			if(percent > 0.0F) {
				float piercingDamage = damage * percent;
				float normalDamage = damage - piercingDamage;
				float absorbedNormalDamage = CombatRules.getDamageAfterAbsorb(normalDamage, totalArmor, toughnessAttribute);
				return piercingDamage + absorbedNormalDamage;
			}
		}
		return CombatRules.getDamageAfterAbsorb(damage, totalArmor, toughnessAttribute);
	}
}