package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class EnchantmentRusted extends EnchantmentBase {
	
	//TODO
	//Delay in ticks for each run
	int delayFactor = 10;
	int divisorMin = 75 / delayFactor;
	
	public EnchantmentRusted(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.rusted;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.rusted;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 25 + 25 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 50;
	}
	
	//TODO
	@Override
	public boolean canApply(ItemStack i) {
		return super.canApply(i) && !i.getItem().getTranslationKey().contains("gold");
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.rusted;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	//TODO
	@Override
	public void onEntityDamagedAlt(EntityLivingBase user, Entity entiti, ItemStack stack, int level) {
		if(level > 0) stack.damageItem(1 + user.getRNG().nextInt(level * 2), user);
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && fTest != Enchantments.UNBREAKING;
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	//TODO
	@SubscribeEvent
	public void rustEvent(PlayerTickEvent e) {
		if(e.phase != Phase.END) return;
		EntityPlayer p = e.player;
		if(p == null) return;
		if(e.side == Side.CLIENT) return;
		//Run every 10 ticks
		if(p.getEntityWorld().getTotalWorldTime() % delayFactor == 0L) {
			p.inventory.mainInventory.stream().forEach(is -> fireRustItemDamage(is, p));
			p.inventory.armorInventory.stream().forEach(is -> fireRustItemDamage(is, p));
			p.inventory.offHandInventory.stream().forEach(is -> fireRustItemDamage(is, p));
		}
	}
	
	//TODO
	private void fireRustItemDamage(ItemStack is, EntityPlayer player) {
		//Reworked to (hopefully) prevent the crashes and run a little bit faster
		if(is == null || player == null) return;
		if(!is.isEmpty() && is.isItemStackDamageable() && is.getMaxDamage() > 0) {
			//Stack exists, is damageable, and max damage is greater than zero
			for(Entry<Enchantment,Integer> entry : EnchantmentHelper.getEnchantments(is).entrySet()) {
				if(entry.getKey() == Smc_010.Rusted) {
					int lvl = entry.getValue();
					if(lvl >= 1) {
						//System.out.print(l + " - Level");
						Random rand = player.getRNG();
						//This isn't mathematically accurate, but it's quite close
						int divisor = 100 - (lvl * delayFactor);
						if(divisor < divisorMin) divisor = divisorMin;
						if(rand.nextInt(100) > divisor) {
							double percentage = is.getMaxDamage() - is.getItemDamage();
							percentage = percentage / is.getMaxDamage();
							if(percentage > 0.15f * lvl + 0.05f) is.damageItem(rand.nextInt(lvl * 2 + 2), player);
						}
						//System.out.println("End, damaged!");
					}
				}
			}
		}
	}
	
	//TODO
	private int getLevel(ItemStack is, Enchantment enchantment) {
		int c = 0;
		if(!is.isEmpty()) {
			Map<Enchantment,Integer> enchs = EnchantmentHelper.getEnchantments(is);
			//Map<Enchantment,Integer> newEnchs = new HashMap<Enchantment,Integer>();
			for(Enchantment en : enchs.keySet()) {
				if(en == enchantment && enchs.get(en) > 1) {
					if(c < enchs.get(en)) c = enchs.get(en);
				}
			}
		}
		return c;
	}
}