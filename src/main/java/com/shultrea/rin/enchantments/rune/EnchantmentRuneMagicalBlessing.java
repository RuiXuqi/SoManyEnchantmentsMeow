package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.IEntityDamageSourceMixin;
import com.shultrea.rin.util.PotionUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentRuneMagicalBlessing extends EnchantmentBase {
	
	public EnchantmentRuneMagicalBlessing(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runeMagicalBlessing;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runeMagicalBlessing;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.runeMagicalBlessing, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.runeMagicalBlessing, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.runeMagicalBlessing, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.runeMagicalBlessing, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runeMagicalBlessing;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
	@Override
	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
		return 0.75F * (float)level;
	}
	
	//Has to be highest for PotionCore Magic Boost compat
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isAttackEntityFromStrong()) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;

		int level = EnchantmentHelper.getEnchantmentLevel(this, attacker.getHeldItemMainhand());
		if(level > 0) {
			if(event.getSource() instanceof EntityDamageSource) {
				float currPercent = ((IEntityDamageSourceMixin)event.getSource()).soManyEnchantments$getPiercingPercent();
				float percent = Math.min(currPercent + 0.25F * (float)level, 1.0F);
				((IEntityDamageSourceMixin)event.getSource()).soManyEnchantments$setPiercingPercent(percent);
				event.getSource().setMagicDamage();
			}
			
			if(!attacker.world.isRemote) {
				if(attacker.getRNG().nextBoolean()) {
					Potion negaPotion = PotionUtil.getNonInstantNegativePotion();
					if(negaPotion != null) {
						int duration = (1 + attacker.getRNG().nextInt(6)) * 20 * level;
						int amplifier = Math.max(0, attacker.getRNG().nextInt(level) - 1);
						victim.addPotionEffect(new PotionEffect(negaPotion, duration, amplifier));
					}
				}
				else {
					Potion negaIPotion = PotionUtil.getInstantNegativePotion();
					if(negaIPotion != null) {
						if(negaIPotion == MobEffects.INSTANT_DAMAGE && victim.isEntityUndead()) negaIPotion = MobEffects.INSTANT_HEALTH;
						int amplifier = Math.max(0, attacker.getRNG().nextInt(level) - 1);
						negaIPotion.affectEntity(attacker, null, victim, amplifier, 1.0D);
					}
				}
			}
		}
	}
}