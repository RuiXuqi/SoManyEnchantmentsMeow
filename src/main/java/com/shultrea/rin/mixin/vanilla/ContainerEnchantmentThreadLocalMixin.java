package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.attributes.EnchantAttribute;
import com.shultrea.rin.util.FromEnchTableThreadLocal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContainerEnchantment.class)
public abstract class ContainerEnchantmentThreadLocalMixin extends Container {
    @Inject(
            method = "onCraftMatrixChanged",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ContainerEnchantment;getEnchantmentList(Lnet/minecraft/item/ItemStack;II)Ljava/util/List;")
    )
    private void soManyEnchantments_vanillaContainerEnchantment_onCraftMatrixChanged_activateThreadLocals(IInventory inventoryIn, CallbackInfo ci) {
        EntityPlayer player = ((InventoryPlayer) getSlot(2).inventory).player;
        soManyEnchantments$setThreadLocals(player);
    }

    @Inject(
            method = "enchantItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ContainerEnchantment;getEnchantmentList(Lnet/minecraft/item/ItemStack;II)Ljava/util/List;")
    )
    private void soManyEnchantments_vanillaContainerEnchantment_enchantItem_activateThreadLocals(EntityPlayer player, int id, CallbackInfoReturnable<Boolean> cir) {
        soManyEnchantments$setThreadLocals(player);
    }

    @Unique
    private static void soManyEnchantments$setThreadLocals(EntityPlayer player){
        //Pass player attribute to getEnchantmentList -> buildEnchantmentList
        EnchantAttribute.attributeThreadLocal.set(player.getEntityAttribute(EnchantAttribute.ENCHANTFOCUS));
        //Tell Random Level Blacklist Mixin that this call is from Enchanting Table
        FromEnchTableThreadLocal.set(true);
    }
}