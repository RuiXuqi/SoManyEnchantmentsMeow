package com.shultrea.rin.mixin.infernalmobs;

import atomicstryker.infernalmobs.common.InfernalMobsCore;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.EnchantRandomly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InfernalMobsCore.class)
public class InfernalMobsCoreMixin {
    @WrapOperation(
            method = "dropRandomEnchantedItems",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemEnchantedBook;getEnchantedItemStack(Lnet/minecraft/enchantment/EnchantmentData;)Lnet/minecraft/item/ItemStack;")
    )
    private ItemStack soManyEnchantments_infernalMobsCore_dropRandomEnchantedItems(EnchantmentData p_92111_0_, Operation<ItemStack> original, @Local(argsOnly = true) EntityLivingBase mob) {
        //this could easily just be null. this only exists for any possible mixin into EnchantRandomly that assumes it's not null
        LootContext context = new LootContext.Builder((WorldServer) mob.world).withLootedEntity(mob).build();
        return new EnchantRandomly(new LootCondition[0], null).apply(new ItemStack(Items.ENCHANTED_BOOK), mob.getRNG(), context);
    }
}
