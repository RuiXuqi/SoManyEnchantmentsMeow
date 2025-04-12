package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.IEntityPlayerMixin;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EnchantmentUnsheathing extends EnchantmentBase {
	
	public EnchantmentUnsheathing(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.unsheathing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.unsheathing;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.unsheathing, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.unsheathing, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return ConfigProvider.canItemApply(ModConfig.canApply.unsheathing, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.unsheathing, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.unsheathing;
	}
	
	@SubscribeEvent
	public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
		if(!this.isEnabled()) return;
		if(event.phase != TickEvent.Phase.END || event.player == null || event.player.world.isRemote) return;
		
		EntityPlayer player = event.player;
		ItemStack lastStack = ((IEntityPlayerMixin)player).soManyEnchantments$getLastHeldStack();
		ItemStack curStack = player.getHeldItemMainhand();
		if(!curStack.isEmpty() && curStack.getItem() != lastStack.getItem()) {
			((IEntityPlayerMixin)player).soManyEnchantments$setLastSwapTime(player.ticksExisted);
		}
		((IEntityPlayerMixin)player).soManyEnchantments$setLastHeldStack(curStack);
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isAttackEntityFromStrong()) return;
		if(event.getAmount() <= 1.0F) return;
		if(!(event.getSource().getTrueSource() instanceof EntityPlayer)) return;
		EntityPlayer attacker = (EntityPlayer)event.getSource().getTrueSource();
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(attacker.world.isRemote) return;
			if(attacker.ticksExisted - ((IEntityPlayerMixin)attacker).soManyEnchantments$getLastUnsheatheTrigger() < 100) return;
			if(attacker.ticksExisted - ((IEntityPlayerMixin)attacker).soManyEnchantments$getLastSwapTime() < 10 + 10 * level) {
				((IEntityPlayerMixin)attacker).soManyEnchantments$setLastUnsheatheTrigger(attacker.ticksExisted);
				event.setAmount(event.getAmount() * (1.0F + 0.25F * (float)level));
			}
		}
	}
}