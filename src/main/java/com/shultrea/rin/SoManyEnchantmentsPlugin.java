package com.shultrea.rin;

import com.shultrea.rin.config.ModConfig;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class SoManyEnchantmentsPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {
	@Override
	public List<String> getMixinConfigs() {
		List<String> mixins = new ArrayList<>();

		mixins.add("mixins.somanyenchantments.vanilla.json");

		if (ModConfig.upgrade.enableUpgrading) {
			if (!Loader.isModLoaded("apotheosis")) {
				mixins.add("mixins.somanyenchantments.upgrading.json");
			} else {
				SoManyEnchantments.LOGGER.warn("Enchantment table upgrading is incompatible with Apotheosis changes");
			}
		}

		return mixins;
	}
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[0];
	}
	
	@Override
	public String getModContainerClass() {
		return null;
	}
	
	@Override
	public String getSetupClass() {
		return null;
	}
	
	@Override
	public void injectData(Map<String, Object> data) {}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}