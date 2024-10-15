package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemBow.class)
public abstract class ItemBowMixin {
	
	@Inject(
			method = "onPlayerStoppedUsing",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damageItem(ILnet/minecraft/entity/EntityLivingBase;)V"),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void soManyEnchantments_vanillaItemBow_onPlayerStoppedUsing(ItemStack bow, World world, EntityLivingBase entity, int timeLeft, CallbackInfo ci, EntityPlayer player, boolean infinity, ItemStack ammo, int i, float velocity, boolean infinite, ItemArrow itemArrow, EntityArrow entityArrow, int powerLevel, int punchLevel) {
		int inaccuracyLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.curseOfInaccuracy, bow);
		if(inaccuracyLevel > 0 && player.getRNG().nextFloat() < ((float)inaccuracyLevel * 0.20F)) {
			entityArrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity * 3.0F, inaccuracyLevel * 10);
		}
		
		int powerlessLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.powerless, bow);
		if(powerlessLevel > 0) {
			double damage = entityArrow.getDamage();
			entityArrow.setDamage(damage - (0.5D + (double)powerlessLevel * 0.5D));
			if(powerlessLevel > 2 || player.getRNG().nextFloat() < powerlessLevel * 0.4F) {
				entityArrow.setIsCritical(false);
			}
		}
		
		int runeArrowPiercingLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeArrowPiercing, bow);
		if(runeArrowPiercingLevel > 0) {
			IArrowProperties cap = entityArrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
			if(cap != null) cap.setPiercingLevel(runeArrowPiercingLevel);
		}
	}
}