package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.config.ConfigProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeRegistry.class)
public abstract class ForgeEnchantmentRegistryMixin<V extends IForgeRegistryEntry<V>> {

    @Inject(method = "register", at = @At("HEAD"), cancellable = true, remap = false)
    private void onRegister(V value, CallbackInfo ci) {
        if (!(value instanceof Enchantment)) return;
        if(ConfigProvider.getRegistryEnchantsBlacklist().isEmpty()) return;
        
        ResourceLocation loc = value.getRegistryName();
        if (loc == null) return;

        //Prevent registration of config defined enchants
        if (ConfigProvider.getRegistryEnchantsBlacklist().contains(loc.toString())) {
            SoManyEnchantments.LOGGER.info("SoManyEnchantments preventing registration of enchantment {}", loc.toString());
            ci.cancel();
        }
    }
}

