package com.shultrea.rin.enchantments.base;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Main_Sector.SoManyEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public abstract class EnchantmentBase extends Enchantment {
	//TODO canApply from EntityVillager.ListEnchantedBookForEmeralds (grabs from entire registry), might be able to do this with event shenanigans
	//TODO apply from EnchantRandomly (grabs from entire registry, applies to Items.BOOK or canApply)
	
	public EnchantmentBase(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots) {
		super(rarity, type, slots);
		this.name = name;
		this.setRegistryName(SoManyEnchantments.MODID, name);
	}
	
	/**
	 * @return if the enchantment is enabled in the config
	 */
	public abstract boolean isEnabled();
	
	/**
	 * @return if the enchantment class has an event subscriber
	 */
	public boolean hasSubscriber() {
		return false;
	}
	
	/**
	 * @return the maximum level of the enchantment defined in config
	 */
	@Override
	public abstract int getMaxLevel();
	
	/**
	 * @return the minimum level cost needed
	 */
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel);
	}
	
	/**
	 * @return the maximum level cost needed
	 */
	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMaxEnchantability(enchantmentLevel);
	}
	
	/**
	 * @return the name and formatting of the enchantment to use in tooltips
	 */
	@Override
	@SuppressWarnings("deprecation")
	public String getTranslatedName(int level) {
		String s = I18n.translateToLocal(this.getName());
		if(!this.isEnabled()) s = TextFormatting.DARK_RED + "" + TextFormatting.STRIKETHROUGH + s;
		else if(this.isCurse()) s = TextFormatting.RED + s;
		else s = getPrefix() + s;
		return level == 1 && this.getMaxLevel() == 1 ? s :
			   s + " " + I18n.translateToLocal("enchantment.level." + level);
	}
	
	/**
	 * @return if the enchantment is allowed to be applied to the given stack
	 */
	@Override
	public boolean canApply(ItemStack stack) {
		return isEnabled() && super.canApply(stack);
	}
	
	/**
	 * @return if the enchantment is a treasure enchantment as defined in config
	 */
	@Override
	public abstract boolean isTreasureEnchantment();
	
	/**
	 * @return if the enchantment is allowed to be applied from an enchanting table
	 */
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return this.isEnabled() && super.canApplyAtEnchantingTable(stack);
	}
	
	/**
	 * @return if the enchantment is allowed to be put on books in an enchanting table
	 */
	@Override
	public boolean isAllowedOnBooks() {
		return this.isEnabled() && super.isAllowedOnBooks();
	}
	
	/**
	 * @return name formatting prefix of the enchantment
	 */
	public String getPrefix() {
		return "";
	}
	
	/**
	 * handle effects on entity being damaged, use instead of onEntityDamaged
	 */
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack weapon, int level) {}
	
	/**
	 * @return if the given damage sources are allowed for this enchantment
	 */
	public boolean isDamageSourceAllowed(Entity immediateSource, Entity trueSource) {
		if(!ModConfig.miscellaneous.enablePetAttacks) {
			//Disallow indirect damage from players through pets
			return !(trueSource instanceof EntityPlayer) || immediateSource instanceof EntityPlayer || !(immediateSource instanceof EntityLivingBase);
		}
		return true;
	}
}