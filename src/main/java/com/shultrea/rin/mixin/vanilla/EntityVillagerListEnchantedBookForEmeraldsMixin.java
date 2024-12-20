package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.util.EnchantUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipeList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Mixin(EntityVillager.ListEnchantedBookForEmeralds.class)
public abstract class EntityVillagerListEnchantedBookForEmeraldsMixin {

    @Unique
    private Random soManyEnchantments_random;

    @Inject(
            method = "addMerchantRecipe",
            at = @At(value = "HEAD")
    )
    public void soManyEnchantments_vanillaEntityVillagerListEnchantedBookForEmeralds_addMerchantRecipe_head(IMerchant merchant, MerchantRecipeList recipeList, Random random, CallbackInfo ci){
        this.soManyEnchantments_random = random;
    }

    @ModifyVariable(
            method = "addMerchantRecipe",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    public Enchantment soManyEnchantments_vanillaEntityVillagerListEnchantedBookForEmeralds_addMerchantRecipe_modify(Enchantment enchantment){
        List<ResourceLocation> validEnchantsArr = new ArrayList<>();
        for(ResourceLocation r : Enchantment.REGISTRY.getKeys()){
            if(!EnchantUtil.isBlackListedEnchant(r,2))
                validEnchantsArr.add(r);
        }

        SoManyEnchantments.LOGGER.info("Nischhelm says (SME), valid enchants: {}", validEnchantsArr.size());
        if(!validEnchantsArr.isEmpty()) {
            ResourceLocation chosenEnchant = validEnchantsArr.get(soManyEnchantments_random.nextInt(validEnchantsArr.size()));
            enchantment = Enchantment.REGISTRY.getObject(chosenEnchant);
        }
        return enchantment;
    }
}