package com.shultrea.rin.enchantments.armor;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.BlockRegistry;
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
import net.minecraft.util.math.MathHelper;
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

	public static void walkOnMagma(EntityLivingBase living, World worldIn, BlockPos pos, int level) {
		if (!EnchantmentRegistry.magmaWalker.isEnabled()) return;
		if (!living.onGround) return;
		float range = (float) Math.min(16, 2 + level);
		BlockPos.MutableBlockPos posAbove = new BlockPos.MutableBlockPos(0, 0, 0);
		for (BlockPos.MutableBlockPos mutable : BlockPos.getAllInBoxMutable(pos.add(-range, -1, -range), pos.add(range, -1, range))) {
			if (mutable.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double) (range * range)) {
				posAbove.setPos(mutable.getX(), mutable.getY() + 1, mutable.getZ());
				IBlockState stateAbove = worldIn.getBlockState(posAbove);
				if (stateAbove.getMaterial() == Material.AIR) {
					IBlockState state = worldIn.getBlockState(mutable);
					if (	state.getMaterial() == Material.LAVA &&
							(state.getBlock() == Blocks.LAVA || state.getBlock() == Blocks.FLOWING_LAVA) &&
							state.getValue(BlockLiquid.LEVEL) == 0 &&
							worldIn.mayPlace(BlockRegistry.tempMagma, mutable, false, EnumFacing.DOWN, null)
					) {
						worldIn.setBlockState(mutable, BlockRegistry.tempMagma.getDefaultState());
						worldIn.scheduleUpdate(mutable.toImmutable(), BlockRegistry.tempMagma, MathHelper.getInt(living.getRNG(), 60, 120));
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