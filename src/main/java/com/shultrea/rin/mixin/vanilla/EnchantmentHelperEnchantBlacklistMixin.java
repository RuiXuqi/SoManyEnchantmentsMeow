package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.config.ModConfig;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = EnchantmentHelper.class, priority = 2000)
public abstract class EnchantmentHelperEnchantBlacklistMixin {

    @Redirect(
            method = "buildEnchantmentList",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEnchantmentDatas(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;")
    )
    private static List<EnchantmentData> soManyEnchantments_vanillaEnchantmentHelper_buildEnchantmentList(int level, ItemStack itemStackIn, boolean allowTreasure) {
        List<EnchantmentData> enchantmentDatas = EnchantmentHelper.getEnchantmentDatas(level, itemStackIn, allowTreasure);
        
        List<EnchantmentData> toRemove = new ArrayList<>();
        //TODO: this technically doesn't distinguish between enchanting table and enchant_with_levels, instead between loot pools that can generate treasure or not.
        //TODO: Purple bookwyrms would fall into the enchanting table category.
        if(allowTreasure) {
            for(EnchantmentData enchantmentData : enchantmentDatas) {
                if(ModConfig.miscellaneous.blacklistedRandomLevelEnchantsIsWhitelist != ModConfig.getRandomLevelEnchantsBlacklist().contains(enchantmentData.enchantment)) {
                    toRemove.add(enchantmentData);
                }
            }
        }
        else {
            for(EnchantmentData enchantmentData : enchantmentDatas) {
                if(ModConfig.miscellaneous.blacklistedEnchTableEnchantsIsWhitelist != ModConfig.getEnchantTableEnchantsBlacklist().contains(enchantmentData.enchantment)) {
                    toRemove.add(enchantmentData);
                }
            }
        }
        enchantmentDatas.removeAll(toRemove);
        return enchantmentDatas;
    }
}