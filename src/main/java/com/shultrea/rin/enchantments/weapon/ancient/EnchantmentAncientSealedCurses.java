package com.shultrea.rin.enchantments.weapon.ancient;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.EnchantUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class EnchantmentAncientSealedCurses extends EnchantmentBase {
	
	public EnchantmentAncientSealedCurses(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.ancientSealedCurses;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.ancientSealedCurses;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.ancientSealedCurses, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.ancientSealedCurses, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.ancientSealedCurses, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.ancientSealedCurses, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.ancientSealedCurses;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.YELLOW.toString();
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingDamageEvent(LivingDamageEvent event) {
		if(!this.isEnabled()) return;
		if(!isDamageSourceAllowed(event.getSource())) return;
		if(!(event.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(event.getEntityLiving() == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(event.getAmount() <= 1.0F) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			Iterable<ItemStack> equipmentList = victim.getEquipmentAndArmor();
			List<Enchantment> curses = EnchantUtil.getCurses();
			if(curses.isEmpty()) return;
			for(ItemStack equipment : equipmentList) {
				if(equipment.isEmpty()) continue;
				if(attacker.getRNG().nextFloat() < 0.125F) {
					Enchantment curse = curses.get(attacker.getRNG().nextInt(curses.size()));
					int curseLevel = 1 + attacker.getRNG().nextInt(curse.getMaxLevel());
					if(!curse.canApply(equipment)) continue;
					
					Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(equipment);
					boolean compatible = true;
					for(Enchantment enchant : enchants.keySet()) {
						if(curse == enchant || !curse.isCompatibleWith(enchant)) {
							compatible = false;
							break;
						}
					}
					if(compatible) {
						equipment.addEnchantment(curse, curseLevel);
					}
				}
			}
		}
	}
}