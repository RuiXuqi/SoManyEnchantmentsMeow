package com.shultrea.rin;

import com.shultrea.rin.util.compat.CompatUtil;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class SoManyEnchantmentsPluginLate implements ILateMixinLoader {

	@Override
	public List<String> getMixinConfigs() {
		List<String> mixins = new ArrayList<>();

		if (Loader.isModLoaded("mujmajnkraftsbettersurvival") && !Loader.isModLoaded("rlmixins") && CompatUtil.isBetterSurvivalCorrectVersion()) {
			mixins.add("mixins.somanyenchantments.compatcancel_bs.json");
		}
		if (Loader.isModLoaded("spartanweaponry") && !Loader.isModLoaded("rlmixins") && CompatUtil.isSpartanWeaponryCorrectVersion()) {
			mixins.add("mixins.somanyenchantments.compatcancel_sw.json");
		}

		return mixins;
	}
}