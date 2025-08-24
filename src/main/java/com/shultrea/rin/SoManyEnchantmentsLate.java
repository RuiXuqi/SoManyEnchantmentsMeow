package com.shultrea.rin;

import com.shultrea.rin.config.ModConfig;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SoManyEnchantmentsLate implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        List<String> mixins = new ArrayList<>();

        if (ModConfig.miscellaneous.betterSurvivalCompatibility) {
            if (Loader.isModLoaded("mujmajnkraftsbettersurvival")) {
                mixins.add("mixins.somanyenchantments.compatcancel_bs.json");
            } else {
                SoManyEnchantments.LOGGER.warn("Mod Better Survival required for option function");
            }
        }
        if (ModConfig.miscellaneous.infernalMobsCompatibility_randomEnchants) {
            if (Loader.isModLoaded("infernalmobs")) {
                mixins.add("mixins.somanyenchantments.infernalmobs_randomenchants.json");
            } else {
                SoManyEnchantments.LOGGER.warn("Mod Infernal Mobs required for option function");
            }
        }

        return mixins;
    }
}
