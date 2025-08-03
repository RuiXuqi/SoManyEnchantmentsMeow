package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.SoundRegistry;
import com.shultrea.rin.util.IEntityDamageSourceMixin;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCulling extends EnchantmentBase {

	public EnchantmentCulling(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}

	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.culling;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.culling;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.culling, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.culling, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return ConfigProvider.canItemApply(this, ModConfig.canApply.culling, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.culling, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.culling;
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingDamageEvent(LivingDamageEvent event) {
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
		if(level > 0 && !attacker.world.isRemote) {
			float curHealth = victim.getHealth();
			if(curHealth - event.getAmount() <= 0) return;
			if((curHealth - event.getAmount()) / victim.getMaxHealth() <= 0.075F + 0.025F * (float)level) {
				for(int i = 0; i < 8; ++i) {
					attacker.world.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, victim.posX, victim.posY + 1.0F, victim.posZ, victim.getRNG().nextGaussian() * 0.02D, victim.getRNG().nextGaussian() * 0.02D, victim.getRNG().nextGaussian() * 0.02D);
				}
				if(attacker.getRNG().nextFloat() < 0.001F) {
					//get out of my head get out of my head
					attacker.world.playSound(null, attacker.posX, attacker.posY, attacker.posZ, SoundRegistry.CULLING, SoundCategory.PLAYERS, 2.0F, 1.0F);
				}
				
				event.setAmount(Math.max(event.getAmount(), victim.getMaxHealth()) * 10.0F);
				if(event.getSource() instanceof EntityDamageSource) {
					((IEntityDamageSourceMixin)event.getSource()).soManyEnchantments$setCulling();
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingDropsEvent(LivingDropsEvent event) {
		if(!this.isEnabled()) return;
		if(!(event.getSource() instanceof EntityDamageSource)) return;
		if(!((IEntityDamageSourceMixin)event.getSource()).soManyEnchantments$getCulling()) return;
		EntityLivingBase entity = event.getEntityLiving();
		if(entity == null) return;
		if(entity.world.isRemote) return;
		
		if(entity.getRNG().nextFloat() <= 0.25F) {
			ItemStack skull;
			if(entity instanceof EntitySkeleton) skull = new ItemStack(Items.SKULL, 1, 0);
			else if(entity instanceof EntityWitherSkeleton) skull = new ItemStack(Items.SKULL, 1, 1);
			else if(entity instanceof EntityZombie && !(entity instanceof EntityPigZombie)) skull = new ItemStack(Items.SKULL, 1, 2);
			else if(entity instanceof EntityCreeper) skull = new ItemStack(Items.SKULL, 1, 4);
			else if(entity instanceof EntityPlayer) {
				skull = new ItemStack(Items.SKULL, 1, 3);
				NBTTagCompound compound = new NBTTagCompound();
				compound.setString("SkullOwner", entity.getName());
				skull.setTagCompound(compound);
			}
			else {
				skull = new ItemStack(Items.SKULL, 1, 3);
				NBTTagCompound compound = new NBTTagCompound();
				
				if(entity instanceof EntityBlaze) {
					compound.setString("SkullOwner", "MHF_Blaze");
				}
				else if(entity instanceof EntityPigZombie) {
					compound.setString("SkullOwner", "MHF_PigZombie");
				}
				else if(entity instanceof EntityCaveSpider) {
					compound.setString("SkullOwner", "MHF_CaveSpider");
				}
				else if(entity instanceof EntitySpider) {
					compound.setString("SkullOwner", "MHF_Spider");
				}
				else if(entity instanceof EntityChicken) {
					compound.setString("SkullOwner", "MHF_Chicken");
				}
				else if(entity instanceof EntityMooshroom) {
					compound.setString("SkullOwner", "MHF_MushroomCow");
				}
				else if(entity instanceof EntityCow) {
					compound.setString("SkullOwner", "MHF_Cow");
				}
				else if(entity instanceof EntityEnderman) {
					compound.setString("SkullOwner", "MHF_Enderman");
				}
				else if(entity instanceof EntityGhast) {
					compound.setString("SkullOwner", "MHF_Ghast");
				}
				else if(entity instanceof EntityIronGolem) {
					compound.setString("SkullOwner", "MHF_Golem");
				}
				else if(entity instanceof EntityMagmaCube) {
					compound.setString("SkullOwner", "MHF_LavaSlime");
				}
				else if(entity instanceof EntityOcelot) {
					compound.setString("SkullOwner", "MHF_Ocelot");
				}
				else if(entity instanceof EntityPig) {
					compound.setString("SkullOwner", "MHF_Pig");
				}
				else if(entity instanceof EntitySheep) {
					compound.setString("SkullOwner", "MHF_Sheep");
				}
				else if(entity instanceof EntitySlime) {
					compound.setString("SkullOwner", "MHF_Slime");
				}
				else if(entity instanceof EntitySquid) {
					compound.setString("SkullOwner", "MHF_Squid");
				}
				else if(entity instanceof EntityVillager) {
					compound.setString("SkullOwner", "MHF_Villager");
				}
				else return;
				
				skull.setTagCompound(compound);
			}
			EntityItem entityItem = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, skull);
			event.getDrops().add(entityItem);
		}
	}
}