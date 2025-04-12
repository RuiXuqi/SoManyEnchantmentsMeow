package com.shultrea.rin;

import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.compat.CompatUtil;
import fermiumbooter.FermiumRegistryAPI;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class SoManyEnchantmentsPlugin implements IFMLLoadingPlugin {
	
	public SoManyEnchantmentsPlugin() {
		MixinBootstrap.init();

		FermiumRegistryAPI.registerAnnotatedMixinConfig(ModConfig.class, null);
		FermiumRegistryAPI.enqueueMixin(false,"mixins.somanyenchantments.vanilla.json");

		FermiumRegistryAPI.enqueueMixin(true,"mixins.somanyenchantments.compatcancel_bs.json", () -> Loader.isModLoaded("mujmajnkraftsbettersurvival") && !Loader.isModLoaded("rlmixins") && CompatUtil.isBetterSurvivalCorrectVersion());
		FermiumRegistryAPI.enqueueMixin(true,"mixins.somanyenchantments.compatcancel_sw.json", () -> Loader.isModLoaded("spartanweaponry") && !Loader.isModLoaded("rlmixins") && CompatUtil.isSpartanWeaponryCorrectVersion());
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