package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.UtilityAccessor;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

public class EnchantmentPurgingBlade extends EnchantmentBase {
	
	public EnchantmentPurgingBlade(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.purgingBlade;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.purgingBlade;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.purgingBlade, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.purgingBlade, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.purgingBlade, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.purgingBlade, stack) && super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.purgingBlade;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource())) return;
		ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.purgingBlade, dmgSource);
		if(enchantmentLevel <= 0) return;
		if(Math.random() <= 0.1 + (0.06f * enchantmentLevel)) {
			UtilityAccessor.damageTarget(fEvent.getEntityLiving(), DamageSource.MAGIC, 1.25f + (0.75f * enchantmentLevel));
			fEvent.getEntityLiving().hurtResistantTime = 0;
			fEvent.setAmount(fEvent.getAmount() * 1.2f);
			Collection<PotionEffect> collectPotion = fEvent.getEntityLiving().getActivePotionEffects();

			//TODO most stupid possible way to do this
			int size = collectPotion.size();
			for(int i = 0; i < size; i++) {
				if(!collectPotion.iterator().hasNext()) return;
				PotionEffect Potions = collectPotion.iterator().next();
				if(Math.random() > 0.5f) {
					if(collectPotion.iterator().hasNext()) {
						Potions = collectPotion.iterator().next();
					}
					if(Math.random() > 0.5f) {
						if(collectPotion.iterator().hasNext()) {
							Potions = collectPotion.iterator().next();
						}
					}
				}
				//TODO this is even worse
				Potions.combine(new PotionEffect(Potions.getPotion(), 0, 256));
				//int potionLevel = Potions.getAmplifier();
			}
		}
	}
}