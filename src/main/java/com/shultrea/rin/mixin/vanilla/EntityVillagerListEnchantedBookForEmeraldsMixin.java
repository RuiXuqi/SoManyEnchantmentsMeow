package com.shultrea.rin.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.shultrea.rin.config.ModConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(EntityVillager.ListEnchantedBookForEmeralds.class)
public abstract class EntityVillagerListEnchantedBookForEmeraldsMixin {

    @ModifyExpressionValue(
            method = "addMerchantRecipe",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/RegistryNamespaced;getRandomObject(Ljava/util/Random;)Ljava/lang/Object;")
    )
    private Enchantment soManyEnchantments_vanillaEntityVillagerListEnchantedBookForEmeralds_addMerchantRecipe(Enchantment original, IMerchant merchant, MerchantRecipeList recipeList, Random random) {
        List<Enchantment> validEnchantsArr = new ArrayList<>();
        for(Enchantment enchant : ForgeRegistries.ENCHANTMENTS.getValuesCollection()) {
            if(ModConfig.miscellaneous.blacklistedLibrarianEnchantsIsWhitelist == ModConfig.getLibrarianEnchantsBlacklist().contains(enchant)) {
                validEnchantsArr.add(enchant);
            }
        }
        if(!validEnchantsArr.isEmpty()) {
            return validEnchantsArr.get(random.nextInt(validEnchantsArr.size()));
        }
        return original;
    }
}