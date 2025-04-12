package com.shultrea.rin.mixin.vanilla;

import com.llamalad7.mixinextras.sugar.Local;
import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.ModConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.passive.EntityVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(EntityVillager.ListEnchantedBookForEmeralds.class)
public abstract class EntityVillagerListEnchantedBookForEmeraldsMixin {

    @ModifyVariable(
            method = "addMerchantRecipe",
            at = @At("STORE")
    )
    private Enchantment soManyEnchantments_vanillaEntityVillagerListEnchantedBookForEmeralds_addMerchantRecipe(Enchantment enchantment, @Local(argsOnly = true) Random random) {
        if(soManyEnchantments$enchantmentIsAllowed(enchantment)) return enchantment;

        List<Enchantment> validEnchantsArr = new ArrayList<>();
        Enchantment.REGISTRY.forEach(e -> { if(soManyEnchantments$enchantmentIsAllowed(e)) validEnchantsArr.add(e); });
        if(!validEnchantsArr.isEmpty()) return validEnchantsArr.get(random.nextInt(validEnchantsArr.size()));

        return enchantment;
    }

    @Unique
    private static boolean soManyEnchantments$enchantmentIsAllowed(Enchantment enchantment){
        return ConfigProvider.getLibrarianEnchantsBlacklist().contains(enchantment) == ModConfig.miscellaneous.blacklistedLibrarianEnchantsIsWhitelist;
    }
}