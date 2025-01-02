package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.util.IEntityPlayerMixin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin implements IEntityPlayerMixin {
	
	@Unique
	private ItemStack soManyEnchantments$lastHeldItem = ItemStack.EMPTY;
	@Unique
	private int soManyEnchantments$lastSwapTime = 0;
	@Unique
	private int soManyEnchantments$lastUnsheatheTrigger = 0;
	
	@Unique
	public void soManyEnchantments$setLastHeldStack(ItemStack stack) {
		this.soManyEnchantments$lastHeldItem = stack;
	}
	
	@Unique
	public void soManyEnchantments$setLastSwapTime(int time) {
		this.soManyEnchantments$lastSwapTime = time;
	}
	
	@Unique
	public void soManyEnchantments$setLastUnsheatheTrigger(int time) {
		this.soManyEnchantments$lastUnsheatheTrigger = time;
	}
	
	@Unique
	public ItemStack soManyEnchantments$getLastHeldStack() {
		return this.soManyEnchantments$lastHeldItem;
	}
	
	@Unique
	public int soManyEnchantments$getLastSwapTime() {
		return this.soManyEnchantments$lastSwapTime;
	}
	
	@Unique
	public int soManyEnchantments$getLastUnsheatheTrigger() {
		return this.soManyEnchantments$lastUnsheatheTrigger;
	}
}