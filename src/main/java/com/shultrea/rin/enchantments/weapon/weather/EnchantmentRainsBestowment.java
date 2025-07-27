package com.shultrea.rin.enchantments.weapon.weather;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRainsBestowment extends EnchantmentBase {
	
	public EnchantmentRainsBestowment(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.rainsBestowment;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.rainsBestowment;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.rainsBestowment, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.rainsBestowment, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.rainsBestowment, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.rainsBestowment, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.rainsBestowment;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			float strengthMulti = CompatUtil.isRLCombatLoaded() ? RLCombatCompat.getAttackEntityFromStrength() : 1.0F;
			if(attacker.world.isRaining()) {
				if(attacker.getRNG().nextFloat() < 0.001F * (float)level * strengthMulti)
					EnchantUtil.setThundering(attacker.world);

				float dmg = 1.25F + 1.0F * (float)level;
				if(!EnchantUtil.canEntitySeeSky(attacker))
					dmg -= 0.75F + 0.75F * (float)level;

				event.setAmount(event.getAmount() + dmg * strengthMulti);
			}
			else if(attacker.getRNG().nextFloat() < 0.002F * (float)level * strengthMulti)
				EnchantUtil.setRaining(attacker.world);
		}
	}
}