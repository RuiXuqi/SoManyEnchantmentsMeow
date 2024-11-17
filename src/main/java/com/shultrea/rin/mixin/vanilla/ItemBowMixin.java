package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.properties.ArrowPropertiesHandler;
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
	
	/**
	 * Handling for Inaccuracy enchant and arrow properties
	 */
	@Inject(
			method = "onPlayerStoppedUsing",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damageItem(ILnet/minecraft/entity/EntityLivingBase;)V"),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void soManyEnchantments_vanillaItemBow_onPlayerStoppedUsing(ItemStack bow, World world, EntityLivingBase entity, int timeLeft, CallbackInfo ci, EntityPlayer player, boolean infinity, ItemStack ammo, int i, float velocity, boolean infinite, ItemArrow itemArrow, EntityArrow entityArrow, int powerLevel, int punchLevel) {
		int inaccuracyLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.curseOfInaccuracy, bow);
		if(inaccuracyLevel > 0 && player.getRNG().nextFloat() < ((float)inaccuracyLevel * 0.20F)) {
			entityArrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity * 3.0F, inaccuracyLevel * 10);
		}

		ArrowPropertiesHandler.setArrowCapabilities(entity, entityArrow);
	}
}