package com.shultrea.rin.Transformer;

import com.shultrea.rin.Transformer.helper.ObfHelper;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.Name("SoManyEnchantments ASM")
@IFMLLoadingPlugin.SortingIndex(1002)
@IFMLLoadingPlugin.TransformerExclusions({"com.Shultrea.Rin.Transformer", "com.Shultrea.Rin.Transformer."})
public class CoreLoader implements IFMLLoadingPlugin {
	//
	// IFMLLoadingPlugin
	// 
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"com.Shultrea.Rin.Transformer.SMEASM"};
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
	public void injectData(Map<String,Object> data) {
		ObfHelper.setObfuscated((Boolean)data.get("runtimeDeobfuscationEnabled"));
		ObfHelper.setRunsAfterDeobfRemapper(true);
	}
	
	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}

