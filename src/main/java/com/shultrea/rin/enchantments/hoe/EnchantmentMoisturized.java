package com.shultrea.rin.enchantments.hoe;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
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

public class EnchantmentMoisturized extends EnchantmentBase {
	
	public EnchantmentMoisturized(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.moisturized;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.moisturized;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.moisturized, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.moisturized, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.moisturized, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.moisturized, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.moisturized;
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
			IBlockState state = world.getBlockState(blockpos);
			if(state.getBlock() instanceof BlockDirt || state.getBlock() instanceof BlockGrass || state.getBlock() instanceof BlockMycelium) {
				world.setBlockState(blockpos, Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE, 7), 3);
				world.scheduleUpdate(blockpos, world.getBlockState(blockpos).getBlock(), MathHelper.getInt(player.getRNG(),120, 240));
				event.setResult(Result.ALLOW);
				world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_HOE_TILL, SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
		}
	}
}