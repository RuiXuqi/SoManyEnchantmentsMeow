package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.util.EnchantUtil;
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
        List<EnchantmentData> list = EnchantmentHelper.getEnchantmentDatas(level, itemStackIn, allowTreasure);

        List<EnchantmentData> toRemove = new ArrayList<>();
        for(EnchantmentData ed : list){
            //TODO: this technically doesn't distinguish between enchanting table and enchant_with_levels but between loot pools that can generate treasure or not. Purple bookwyrms would fall into the enchanting table category.
            if(EnchantUtil.isBlackListedEnchant(ed.enchantment.getRegistryName(),allowTreasure ? 0 : 3))
                toRemove.add(ed);
        }
        list.removeAll(toRemove);

        return list;
    }
}