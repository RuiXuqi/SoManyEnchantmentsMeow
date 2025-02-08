package com.shultrea.rin.util.compat;

import net.minecraft.util.text.TextFormatting;
import socketed.common.config.DefaultJsonConfig;
import socketed.common.jsondata.GemType;
import socketed.common.jsondata.entry.RandomValueRange;
import socketed.common.jsondata.entry.effect.AttributeGemEffect;
import socketed.common.jsondata.entry.effect.slot.SocketedSlotTypes;
import socketed.common.jsondata.entry.filter.OreEntry;

import java.util.Collections;

public class SocketedCompat {
    public static void registerLapisGem() {
        DefaultJsonConfig.registerDefaultGemType("lapis", new GemType("socketed.tooltip.default.lapis", 2, TextFormatting.BLUE,
                Collections.singletonList(new AttributeGemEffect(SocketedSlotTypes.ALL, "somanyenchantments.enchantfocus", new RandomValueRange(2, false), 0)),
                Collections.singletonList(new OreEntry("gemLapis", true))));
    }
}
