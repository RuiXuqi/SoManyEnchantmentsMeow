package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.config.ModConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.EnchantRandomly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantRandomly.class)
public abstract class EnchantRandomlyMixin {

    @Final @Shadow
    private List<Enchantment> enchantments;

    @Inject(
            method = "<init>",
            at = @At(value = "TAIL")
    )
    private void soManyEnchantments_vanillaEnchantRandomly_init(LootCondition[] conditionsIn, List<Enchantment> enchantmentsIn, CallbackInfo ci) {
        List<Enchantment> toRemove = new ArrayList<>();
        for(Enchantment enchant : this.enchantments) {
            if(ModConfig.miscellaneous.blacklistedRandomEnchantsIsWhitelist != ModConfig.getRandomEnchantsBlacklist().contains(enchant)) {
                toRemove.add(enchant);
            }
        }
        this.enchantments.removeAll(toRemove);
    }

    @Redirect(
            method = "apply",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z")
    )
    private boolean soManyEnchantments_vanillaEnchantRandomly_apply(List<Enchantment> instance, Object enchantment) {
        if(ModConfig.miscellaneous.blacklistedRandomEnchantsIsWhitelist == ModConfig.getRandomEnchantsBlacklist().contains((Enchantment)enchantment)) {
            instance.add((Enchantment)enchantment);
        }
        return true;
    }
}