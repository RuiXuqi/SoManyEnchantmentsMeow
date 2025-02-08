package com.shultrea.rin;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import com.shultrea.rin.config.EarlyConfigReader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class SoManyEnchantmentsPlugin implements IFMLLoadingPlugin {
	
	public SoManyEnchantmentsPlugin() {
		MixinBootstrap.init();
		MixinExtrasBootstrap.init();
		Mixins.addConfiguration("mixins.somanyenchantments.vanilla.json");

		if(EarlyConfigReader.getBoolean(".Enable Upgrading Mechanic"))
			Mixins.addConfiguration("mixins.somanyenchantments.upgrading.json");
		if(EarlyConfigReader.getBoolean("Zombified Villagers keep trades"))
			Mixins.addConfiguration("mixins.somanyenchantments.zombietrades.json");
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