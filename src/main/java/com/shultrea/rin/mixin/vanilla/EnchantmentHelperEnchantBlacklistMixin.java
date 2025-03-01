package com.shultrea.rin.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.FromEnchTableThreadLocal;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(value = EnchantmentHelper.class, priority = 2000)
public abstract class EnchantmentHelperEnchantBlacklistMixin {

    @ModifyExpressionValue(
            method = "buildEnchantmentList",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEnchantmentDatas(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;")
    )
    private static List<EnchantmentData> soManyEnchantments_vanillaEnchantmentHelper_buildEnchantmentList(List<EnchantmentData> original, @Local(argsOnly = true) int level, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) boolean allowTreasure) {
        boolean fromEnchantingTable = FromEnchTableThreadLocal.getAndRemove();

        return original.stream()
                .filter(e -> soManyEnchantments$enchantmentIsAllowed(e, fromEnchantingTable))
                .collect(Collectors.toList());
    }

    @Unique
    private static boolean soManyEnchantments$enchantmentIsAllowed(EnchantmentData data, boolean fromEnchantingTable){
        if(fromEnchantingTable)
            return ModConfig.getEnchantTableEnchantsBlacklist().contains(data.enchantment) == ModConfig.miscellaneous.blacklistedEnchTableEnchantsIsWhitelist;
        else return ModConfig.getRandomLevelEnchantsBlacklist().contains(data.enchantment) == ModConfig.miscellaneous.blacklistedRandomLevelEnchantsIsWhitelist;
    }
}