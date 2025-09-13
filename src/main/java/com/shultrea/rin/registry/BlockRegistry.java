package com.shultrea.rin.registry;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.blocks.BlockTemporaryIce;
import com.shultrea.rin.blocks.BlockTemporaryMagma;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = SoManyEnchantments.MODID)
public class BlockRegistry {
    public static BlockTemporaryIce tempIce;
    public static BlockTemporaryMagma tempMagma;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        tempIce = new BlockTemporaryIce();
        event.getRegistry().register(tempIce);

        tempMagma = new BlockTemporaryMagma();
        event.getRegistry().register(tempMagma);
    }
}
