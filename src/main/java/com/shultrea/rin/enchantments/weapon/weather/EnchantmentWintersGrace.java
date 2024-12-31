package com.shultrea.rin.enchantments.weapon.weather;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentWintersGrace extends EnchantmentBase {
	
	public EnchantmentWintersGrace(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.wintersGrace;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.wintersGrace;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.wintersGrace, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.wintersGrace, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.wintersGrace, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.wintersGrace, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.wintersGrace;
	}
	
	public int level(ItemStack stack) {
		return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.wintersGrace, stack);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isAttackEntityFromStrong()) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(isInColdTemperature(attacker)) {
				float dmg = 2.0F + 1.5F * (float)level;
				if(!attacker.world.isRaining()) {
					dmg -= 0.5F + 0.25F * (float)level;
				}
				if(!EnchantUtil.canEntitySeeSky(attacker)) {
					dmg -= 0.5F + 0.75F * (float)level;
				}
				event.setAmount(event.getAmount() + dmg);
				
				if(attacker.getRNG().nextFloat() < 0.04F * (float)level) {
					victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 + 10 * level, Math.max(0, level - 3)));
					if(level > 3) {
						victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 + 10 * level, Math.max(0, level - 4)));
					}
				}
			}
		}
	}

	private static boolean isInColdTemperature(EntityLivingBase attacker) {
		BlockPos pos = new BlockPos(attacker.posX, attacker.posY, attacker.posZ);
		float temp = attacker.world.getBiome(pos).getTemperature(pos);
		//Vanilla check for snow is 0.15, but expand to account for Taiga and related biomes
		return temp <= 0.3F;
	}
}