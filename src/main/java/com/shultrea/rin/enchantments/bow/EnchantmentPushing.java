package com.shultrea.rin.enchantments.bow;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EnchantmentPushing extends EnchantmentBase {
	
	public EnchantmentPushing(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.pushing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.pushing;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.pushing, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.pushing, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.pushing, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.pushing, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.pushing;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingEntityUseItem(LivingEntityUseItemEvent.Tick event) {
		if(!this.isEnabled()) return;
		EntityLivingBase entity = event.getEntityLiving();
		ItemStack bow = event.getItem();
		if(entity == null) return;
		if(entity.world.isRemote) return;
		if(!(bow.getItem() instanceof ItemBow)) return;
		
		if(entity.ticksExisted%10 == 0) return;
		
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(this, bow);
		if(enchantmentLevel > 0) {
			AxisAlignedBB axis = new AxisAlignedBB(entity.getPosition()).grow(4 + enchantmentLevel * 2);
			repelEntitiesInAABBFromPoint(entity.world, axis, entity.posX, entity.posY, entity.posZ, enchantmentLevel);
		}
	}

	private static void repelEntitiesInAABBFromPoint(World world, AxisAlignedBB effectBounds, double x, double y, double z, int enchantmentLevel) {
		List<Entity> list = world.getEntitiesWithinAABB(Entity.class, effectBounds);
		for(Entity ent : list) {
			if(ent instanceof EntityLiving || ent instanceof IProjectile) {
				if(ent.isDead) continue;
				if(ent instanceof EntityArrow && ent.onGround) continue;
				Vec3d bowVec = new Vec3d(x, y, z);
				Vec3d entVec = new Vec3d(ent.posX, ent.posY, ent.posZ);
				Vec3d bowToEntVec = new Vec3d(entVec.x - bowVec.x, entVec.y - bowVec.y, entVec.z - bowVec.z);
				double distance = bowToEntVec.length() + 0.1D;
				double diminishOverDistance = 10 / distance / Math.max(50 - enchantmentLevel * 10, 1);
				ent.motionX += bowToEntVec.x / Math.max(5.25 - enchantmentLevel * 1.5, 1) * diminishOverDistance;
				ent.motionY += bowToEntVec.y / Math.max(6.25 - enchantmentLevel * 1.25, 1) * diminishOverDistance;
				ent.motionZ += bowToEntVec.z / Math.max(5.25 - enchantmentLevel * 1.5, 1) * diminishOverDistance;
			}
		}
	}
}