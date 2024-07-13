package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Interfaces.IEnchantmentGreaterCurse;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Main_Sector.SoManyEnchantments;
import com.shultrea.rin.Utility_Sector.EnchantmentLister;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;
import java.util.Random;

public class EnchantmentPandorasCurse extends EnchantmentBase implements IEnchantmentGreaterCurse {
	
	//An enchantment that is hidden until a certain time where it will reveal itself.
	//While hidden, it infects the inventory of players with a bunch of curse enchantments, not including itself.
	public EnchantmentPandorasCurse(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, EntityEquipmentSlot.values());
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.pandorasCurse;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.pandorasCurse;
	}
	
	@Override
	public int getMinEnchantability(int par1) {
		return 100 + 50 * (par1 - 1);
	}
	
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 100;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public String getTranslatedName(int level) {
		if(!isEnabled()) return super.getTranslatedName(level);
		String s = I18n.translateToLocal(this.getName());
		s = TextFormatting.DARK_RED + s;
		if(level >= 5) return s;
		EntityPlayer player = SoManyEnchantments.proxy.getClientPlayer();
		if(player != null && player.isCreative()) {
			return s;
		}
		return "";
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.pandorasCurse;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	@SubscribeEvent
	public void HandleEnchant(PlayerTickEvent e) {
		if(e.phase == Phase.START) return;
		if(e.player == null || e.side == Side.CLIENT) return;
		EntityPlayer player = e.player;
		InventoryPlayer inv = player.inventory;
		Random randy = new Random();
		if(randy.nextInt(1000) < 1) for(int x = 0; x < inv.mainInventory.size(); x++) {
			ItemStack stack = inv.mainInventory.get(x);
			if(EnchantmentHelper.getEnchantmentLevel(this, stack) <= 0) continue;
			for(int y = 0; y < inv.mainInventory.size(); y++) {
				if(randy.nextInt(1200) < 1) continue;
				ItemStack curse = inv.mainInventory.get(y);
				if(curse.isEmpty()) continue;
				List<Enchantment> list = EnchantmentLister.CURSE;
				int random = player.getRNG().nextInt(list.size());
				Enchantment ench = list.get(random);
				int randLevel = player.getRNG().nextInt(ench.getMaxLevel());
				if(!ench.canApply(curse)) continue;
				NBTTagList nbt = curse.getEnchantmentTagList();
				boolean isCompatible = true;
				for(int z = 0; z < nbt.tagCount(); z++) {
					NBTTagCompound tag = nbt.getCompoundTagAt(x);
					int enchId = tag.getShort("id");
					int currEnchLevel = tag.getShort("lvl");
					Enchantment enchantment = Enchantment.getEnchantmentByID(enchId);
					if(enchantment == null) continue;
					if(enchantment == this) {
						if(randy.nextInt(3) < 2) tag.setShort("lvl", (short)(currEnchLevel + 1));
						if(tag.getShort("lvl") > 5) {
							if(randy.nextInt(3) < 2) nbt.removeTag(x);
							return;
						}
						continue;
					}
					if(enchantment.isCompatibleWith(ench)) continue;
					else isCompatible = false;
					break;
				}
				if((EnchantmentHelper.getEnchantmentLevel(ench, curse) <= 0) && isCompatible)
					curse.addEnchantment(list.get(random), randLevel + 1);
			}
		}
	}
}