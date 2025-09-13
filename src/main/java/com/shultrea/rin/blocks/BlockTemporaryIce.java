package com.shultrea.rin.blocks;

import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.block.BlockFrostedIce;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockTemporaryIce extends BlockFrostedIce {
    public BlockTemporaryIce() {
        super();
        //Stats from Frosted Ice
        this.setHardness(0.5F);
        this.setLightOpacity(3);
        this.setSoundType(SoundType.GLASS);
        this.setTranslationKey("frostedIceTemp");
        this.setRegistryName(SoManyEnchantments.MODID, "frostedIceTemp");
    }

    @Override
    protected void turnIntoWater(World worldIn, @Nonnull BlockPos pos) {
        worldIn.setBlockToAir(pos);
    }

    @Override
    public boolean causesSuffocation(@Nonnull IBlockState state) {
        return state.getValue(AGE) == 0;
    }
}
