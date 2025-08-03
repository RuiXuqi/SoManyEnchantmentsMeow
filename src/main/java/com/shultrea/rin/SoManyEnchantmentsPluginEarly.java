package com.shultrea.rin;

import com.shultrea.rin.config.EarlyConfigReader;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.compat.CompatUtil;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class SoManyEnchantmentsPluginEarly implements IFMLLoadingPlugin, IEarlyMixinLoader {
	
	public SoManyEnchantmentsPluginEarly() {
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

	@Override
	public List<String> getMixinConfigs() {
		List<String> mixins = new ArrayList<>();
		mixins.add("mixins.somanyenchantments.vanilla.json");

		if (EarlyConfigReader.getBoolean("Zombified Villagers keep trades", ModConfig.miscellaneous.zombieVillagersKeepTrades)) {
			mixins.add("mixins.somanyenchantments.zombietrades.json");
		}
		if (EarlyConfigReader.getBoolean(".Enable Upgrading Mechanic", ModConfig.upgrade.enableUpgrading)) {
			mixins.add("mixins.somanyenchantments.upgrading.json");
		}

		return mixins;
	}
}