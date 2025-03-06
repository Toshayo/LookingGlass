package com.xcompwiz.lookingglass;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;


@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions("com.xcompwiz.lookingglass.transformers")
public class LookingGlassPlugin implements IFMLLoadingPlugin {
    public static final Logger LOGGER = LogManager.getLogger("LookingGlassPlugin");

    @Override
    public String[] getASMTransformerClass() {
        ArrayList<String> transformers = new ArrayList<>();
        transformers.add("com.xcompwiz.lookingglass.transformers.ItemCameraMonitorRendererTransformer");
        return transformers.toArray(new String[0]);
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
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
