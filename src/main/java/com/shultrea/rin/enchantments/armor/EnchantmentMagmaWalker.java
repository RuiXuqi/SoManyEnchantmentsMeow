package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Enchantment handled in com.shultrea.rin.mixin.vanilla.EntityLivingBaseMixin
 */
public class EnchantmentMagmaWalker extends EnchantmentBase {
	
	public EnchantmentMagmaWalker(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	//TODO replace Magma with temporary version?
	public static void walkOnMagma(EntityLivingBase living, World worldIn, BlockPos pos, int level) {
		if(!EnchantmentRegistry.magmaWalker.isEnabled()) return;
		if(living.onGround) {
			float f = (float)Math.min(16, 2 + level);
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);
			for(BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add(-f, -1.0D, -f), pos.add(f, -1.0D, f))) {
				if(blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double)(f * f)) {
					blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
					IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos);
					if(iblockstate.getMaterial() == Material.AIR) {
						IBlockState iblockstate1 = worldIn.getBlockState(blockpos$mutableblockpos1);
						if(iblockstate1.getMaterial() == Material.LAVA && (iblockstate1.getBlock() == net.minecraft.init.Blocks.LAVA || iblockstate1.getBlock() == net.minecraft.init.Blocks.FLOWING_LAVA) && iblockstate1.getValue(BlockLiquid.LEVEL).intValue() == 0 && worldIn.mayPlace(Blocks.MAGMA, blockpos$mutableblockpos1, false, EnumFacing.DOWN, null)) {
							worldIn.setBlockState(blockpos$mutableblockpos1, Blocks.MAGMA.getDefaultState());
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.magmaWalker;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.magmaWalker;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.magmaWalker, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.magmaWalker, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.magmaWalker, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.magmaWalker, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.magmaWalker;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		
		if(event.getSource() != DamageSource.LAVA && event.getSource() != DamageSource.HOT_FLOOR) return;
		if(event.getSource() == DamageSource.LAVA && victim.isInLava()) return;
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, victim);
		if(level > 0) {
			event.setCanceled(true);
		}
	}
}