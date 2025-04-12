package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.mixin.vanilla.IItemBowMixin;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.IceAndFireCompat;
import com.shultrea.rin.util.compat.SwitchbowCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentSplitshot extends EnchantmentBase {
	
	public EnchantmentSplitshot(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.splitShot;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.splitShot;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.splitShot, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.splitShot, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.splitShot, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.splitShot, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.splitShot;
	}
	
	//TODO test inaccuracy change
	//TODO better way to handle split shot without recreating vanilla method?
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onArrowLooseEvent(ArrowLooseEvent event) {
		if(!this.isEnabled()) return;
		EntityPlayer player = event.getEntityPlayer();
		if(player == null) return;
		ItemStack bow = event.getBow();
		if(!(bow.getItem() instanceof ItemBow)) return;
		if(player.world.isRemote) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, bow);
		if(level > 0) {
			for(int x = 0; x < level; x++) {
				boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;
				ItemStack ammo;
				if(CompatUtil.isSwitchbowLoaded() && SwitchbowCompat.isSwitchbow(bow)) {
					ammo = SwitchbowCompat.getSwitchbowAmmo(player, bow);
				}
				else ammo = ((IItemBowMixin)bow.getItem()).invokeFindAmmo(player);
				if(!ammo.isEmpty() || flag) {
					if(ammo.isEmpty()) {
						if(CompatUtil.isIceAndFireLoaded() && IceAndFireCompat.isDragonboneBow(bow)) {
							ammo = new ItemStack(IceAndFireCompat.getDragonboneArrow());
						}
						else ammo = new ItemStack(Items.ARROW);
					}
					float f = ItemBow.getArrowVelocity(event.getCharge());
					if((double)f >= 0.1D) {
						ItemArrow itemarrow = (ItemArrow)(ammo.getItem() instanceof ItemArrow ? ammo.getItem() : Items.ARROW);
						EntityArrow entityarrow = itemarrow.createArrow(player.world, ammo, player);
						entityarrow = ((ItemBow)bow.getItem()).customizeArrow(entityarrow);
						entityarrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 3.0F, 3.0F + player.getRNG().nextFloat() * 6.0F);
						
						if(f == 1.0F) {
							entityarrow.setIsCritical(true);
						}
						
						int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow);
						if(j > 0) {
							entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
						}
						
						int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bow);
						if(k > 0) {
							entityarrow.setKnockbackStrength(k);
						}
						
						if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) > 0) {
							entityarrow.setFire(100);
						}
						
						bow.damageItem(1, player);
						
						entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
						
						player.world.spawnEntity(entityarrow);
					}
				}
			}
		}
	}
}