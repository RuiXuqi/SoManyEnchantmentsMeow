package com.shultrea.rin;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
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