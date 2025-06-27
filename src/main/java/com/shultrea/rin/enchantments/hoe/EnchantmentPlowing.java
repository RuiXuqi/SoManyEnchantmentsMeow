package com.shultrea.rin.enchantments.hoe;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentPlowing extends EnchantmentBase {
	
	public EnchantmentPlowing(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.plowing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.plowing;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.plowing, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.plowing, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.plowing, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.plowing, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.plowing;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onUseHoeEvent(UseHoeEvent event) {
		if(!this.isEnabled()) return;
		if(event.getResult() == Result.DENY) return;
		World world = event.getWorld();
		if(world.isRemote) return;
		EntityPlayer player = event.getEntityPlayer();
		if(player == null) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, event.getCurrent());
		if(level > 0) {
			BlockPos blockpos = event.getPos();
			BlockPos.MutableBlockPos m = new BlockPos.MutableBlockPos(0, 0, 0);
			double range = Math.min(9, level);
			int levelMoisturized = EnchantmentRegistry.moisturized.isEnabled() ? EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.moisturized, event.getCurrent()) : 0;
			for(BlockPos.MutableBlockPos blockPosMutable : BlockPos.getAllInBoxMutable(blockpos.add(-range, -1.0D, -range), blockpos.add(range, -1.0D, range))) {
				m.setPos(blockPosMutable.getX(), blockPosMutable.getY() + 1, blockPosMutable.getZ());
				IBlockState state = world.getBlockState(m);
				if(state.getBlock() instanceof BlockDirt || state.getBlock() instanceof BlockGrass || state.getBlock() instanceof BlockMycelium) {
					world.setBlockState(m, (levelMoisturized > 0 ? Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE, 7) : Blocks.FARMLAND.getDefaultState()), 3);
					world.scheduleUpdate(m, world.getBlockState(m).getBlock(), MathHelper.getInt(player.getRNG(),120, 240));
					event.getCurrent().damageItem(1, player);
					event.setResult(Result.ALLOW);
					world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_HOE_TILL, SoundCategory.PLAYERS, 1.0F, 1.0F);
				}
			}
		}
	}
}