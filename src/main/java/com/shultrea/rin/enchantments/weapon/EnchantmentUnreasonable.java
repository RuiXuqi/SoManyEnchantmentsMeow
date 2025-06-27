package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EnchantmentUnreasonable extends EnchantmentBase {

	public EnchantmentUnreasonable(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}

	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.unreasonable;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.unreasonable;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.unreasonable, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.unreasonable, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.unreasonable, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.unreasonable, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.unreasonable;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		if(attacker.world.isRemote) return;
		if(!(event.getEntityLiving() instanceof EntityLiving)) return;
		EntityLiving victim = (EntityLiving)event.getEntityLiving();
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level <= 0) return;
		if(CompatUtil.isRLCombatLoaded() && attacker.getRNG().nextFloat() > RLCombatCompat.getAttackEntityFromStrength()) return;
		if(attacker.getRNG().nextFloat() < 0.05F * (float)level) {
			List<EntityLiving> entities = attacker.world.getEntitiesWithinAABB(EntityLiving.class, victim.getEntityBoundingBox().grow(Math.min(3 + 3 * level, 16)), e -> e != attacker && e != victim);
			if(entities.isEmpty()) return;
			EntityLiving target = entities.get(attacker.getRNG().nextInt(entities.size()));
			if(attacker.world instanceof WorldServer) {
				((WorldServer)attacker.world).addScheduledTask(() -> {
					try {
						if(victim != null && target != null) {
							if(!victim.isDead && !target.isDead) {
								if(!victim.getUniqueID().equals(target.getUniqueID())) {
									victim.setRevengeTarget(target);
									victim.setAttackTarget(target);
								}
							}
						}
					}
					catch(Exception ignored) {}
				});
			}
		}
	}
}