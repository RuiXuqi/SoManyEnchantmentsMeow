package com.shultrea.rin.blocks;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMagma;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockTemporaryMagma extends BlockMagma {
    //Melting behavior copied from BlockFrostedIce
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

    public BlockTemporaryMagma() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
        //Stats from Magma
        this.setHardness(0.5F);
        this.setSoundType(SoundType.STONE);
        this.setTranslationKey("magmaTemp");
        this.setRegistryName(SoManyEnchantments.MODID, "magmaTemp");
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, MathHelper.clamp(meta, 0, 3));
    }

    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, Random rand) {
        if (countNeighborsOfBlock(world, pos, this) < 4 && countNeighborsOfBlock(world, pos, Blocks.LAVA) > 2 - state.getValue(AGE)) {
            this.slightlyMelt(world, pos, state, rand, true);
        } else {
            world.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
        }
    }

    protected void slightlyMelt(World worldIn, BlockPos pos, IBlockState state, Random rand, boolean meltNeighbors) {
        int i = state.getValue(AGE);

        if (i < 3) {
            worldIn.setBlockState(pos, state.withProperty(AGE, i + 1), 2);
            worldIn.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
        } else {
            this.turnIntoLava(worldIn, pos);
            if (!meltNeighbors) return;
            for (EnumFacing enumfacing : EnumFacing.values()) {
                BlockPos blockpos = pos.offset(enumfacing);
                IBlockState iblockstate = worldIn.getBlockState(blockpos);

                if (iblockstate.getBlock() == this)
                    this.slightlyMelt(worldIn, blockpos, iblockstate, rand, false);
            }
        }
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos) {
        if (blockIn == this && countNeighborsOfBlock(worldIn, pos, this) < 2)
            this.turnIntoLava(worldIn, pos);
    }

    protected void turnIntoLava(World world, @Nonnull BlockPos pos) {
        world.setBlockState(pos, Blocks.LAVA.getDefaultState());
    }

    private static int countNeighborsOfBlock(World world, BlockPos pos, Block comparison) {
        int counter = 0;
        for (EnumFacing facing : EnumFacing.values()) {
            if (world.getBlockState(pos.offset(facing)).getBlock() == comparison) {
                ++counter;
                if (counter >= 4) return counter;
            }
        }
        return counter;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AGE);
    }

    @Override @Nonnull
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return ItemStack.EMPTY;
    }

    @Override @Nonnull
    public MapColor getMapColor(@Nonnull IBlockState state, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return MapColor.NETHERRACK;
    }

    @Override
    public void onEntityWalk(@Nonnull World world, @Nonnull BlockPos pos, Entity entity) {
        if (!entity.isImmuneToFire() && entity instanceof EntityLivingBase){
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            if(!EnchantmentHelper.hasFrostWalkerEnchantment(entityLivingBase) && EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.magmaWalker, entityLivingBase) <= 0)
                entity.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
        }
        super.onEntityWalk(world, pos, entity);
    }

    @Override @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return 15728880;
    }

    @Override
    public boolean canEntitySpawn(@Nonnull IBlockState state, Entity entityIn) {
        return entityIn.isImmuneToFire();
    }
}
