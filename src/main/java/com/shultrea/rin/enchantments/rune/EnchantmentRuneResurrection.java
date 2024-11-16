package com.shultrea.rin.enchantments.rune;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Prop_Sector.IPlayerProperties;
import com.shultrea.rin.Prop_Sector.PlayerPropertiesProvider;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EnchantmentRuneResurrection extends EnchantmentBase {
	
	private final Map<UUID,InventoryPlayer> INVENTORY_MAP = new HashMap<>();
	
	public EnchantmentRuneResurrection(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.runeResurrection;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.runeResurrection;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.runeResurrection, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.runeResurrection, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.runeResurrection, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.runeResurrection, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.runeResurrection;
	}
	
	@Override
	public String getPrefix() {
		return TextFormatting.GREEN.toString();
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onWielderDeath(LivingDeathEvent e) {
		if(!(e.getEntityLiving() instanceof EntityPlayer)) return;
		EntityPlayer diedPlayer = (EntityPlayer)e.getEntityLiving();
		if(diedPlayer.world.isRemote) return;

		ItemStack stack = diedPlayer.getHeldItemOffhand();
		int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeResurrection, stack);
		if(enchantmentLevel <= 0){
			stack = diedPlayer.getHeldItemMainhand();
			enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeResurrection, stack);
		}
		if(enchantmentLevel <= 0) return;

		if(diedPlayer.world.getWorldInfo().isHardcoreModeEnabled() || diedPlayer.posY < 0) {
			diedPlayer.setHealth(diedPlayer.getMaxHealth() * 0.5f * enchantmentLevel);
			e.setCanceled(true);
			stack.shrink(1);
			diedPlayer.hurtResistantTime = 30;
			return;
		}

		diedPlayer.setSpawnChunk(diedPlayer.getPosition(), true, diedPlayer.dimension);
		diedPlayer.setSpawnPoint(diedPlayer.getPosition(), true);
		BlockPos bp = diedPlayer.getBedLocation();
		BlockPos bedSpawnPos = EntityPlayer.getBedSpawnLocation(diedPlayer.world, bp, false);
		if(bedSpawnPos != null) {
			NBTTagCompound nbt = stack.serializeNBT();
			nbt.setInteger("blockPosX", bedSpawnPos.getX());
			nbt.setInteger("blockPosY", bedSpawnPos.getY());
			nbt.setInteger("blockPosZ", bedSpawnPos.getZ());
			nbt.setInteger("dimension", diedPlayer.dimension);
			stack.deserializeNBT(nbt);
		}
		else {
			BlockPos originalWorldSpawn = diedPlayer.world.getSpawnPoint();
			NBTTagCompound nbt = stack.serializeNBT();
			nbt.setInteger("blockPosX", originalWorldSpawn.getX());
			nbt.setInteger("blockPosY", originalWorldSpawn.getY());
			nbt.setInteger("blockPosZ", originalWorldSpawn.getZ());
			stack.deserializeNBT(nbt);
		}
		InventoryPlayer inv = new InventoryPlayer(null);
		if(diedPlayer.hasCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null)) {
			IPlayerProperties ar = diedPlayer.getCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null);
			ar.setResurrecting(true);
			if(!diedPlayer.world.getGameRules().getBoolean("keepInventory")) {
				keepAllArmor(diedPlayer, inv);
				keepOffHand(diedPlayer, inv);
				for(int i = 0; i < diedPlayer.inventory.mainInventory.size(); i++) {
					inv.mainInventory.set(i, diedPlayer.inventory.mainInventory.get(i));
					diedPlayer.inventory.mainInventory.set(i, ItemStack.EMPTY);
				}
				INVENTORY_MAP.put(diedPlayer.getUniqueID(), inv);
			}
		}
	}

	@SubscribeEvent
	public void onItemDrop(PlayerDropsEvent e) {
		EntityPlayer player = e.getEntityPlayer();
		List<EntityItem> dropList = e.getDrops();
		IPlayerProperties p = player.getCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null);
		if(p == null) return;
		if(!p.isResurrecting()) return;

		e.setCanceled(true);

        for (EntityItem item : dropList) {
            ItemStack stack = item.getItem();
            boolean couldBeAdded = player.inventory.addItemStackToInventory(stack);

            if (!couldBeAdded) {
                item.setOwner(player.getName());
                item.setNoPickupDelay();
                item.setEntityInvulnerable(true);
            }
        }
	}
	
	@SubscribeEvent
	public void onPlayerResurrect(PlayerEvent.Clone e) {
		if(!e.isWasDeath()) return;
		IPlayerProperties p = e.getOriginal().getCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null);
		IPlayerProperties cap = e.getEntityPlayer().getCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null);
		if(p==null || cap==null) return;
		if(p.isResurrecting())
			cap.setResurrecting(true);
	}
	
	@SubscribeEvent
	public void teleportLastDeathLocation(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {
		if(e.isEndConquered()) return;
		EntityPlayer player = e.player;
		regenerateItems(player);
		IPlayerProperties p = player.getCapability(PlayerPropertiesProvider.PLAYERPROPERTIES_CAP, null);

		if(p==null) return;
		InventoryPlayer inv;
		List<ItemStack> list;
		inv = player.inventory;
		list = inv.mainInventory;
		if(p.isResurrecting()) {
			p.setResurrecting(false);
			if(!player.world.getGameRules().getBoolean("keepInventory")) {
				if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeResurrection, player.getHeldItemOffhand()) > 0)
					player.getHeldItemOffhand().shrink(1);
				else
					for (ItemStack itemStack : list) {
						int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeResurrection, itemStack);
						if (level > 0) {
							player.addStat(StatList.DEATHS, -1);
							NBTTagCompound nbt = itemStack.serializeNBT();
							BlockPos pos = new BlockPos(nbt.getInteger("blockPosX"), nbt.getInteger("blockPosY"), nbt.getInteger("blockPosZ"));
							player.setSpawnChunk(pos, false, nbt.getInteger("dimension"));
							player.setSpawnPoint(pos, false);
							itemStack.shrink(1);
							return;
						}
					}
			}
		}
	}
	
	private void regenerateItems(EntityPlayer player) {
		InventoryPlayer keepInventory = INVENTORY_MAP.remove(player.getUniqueID());
		if(keepInventory != null) {
			NonNullList<ItemStack> displaced = NonNullList.create();
			for(int i = 0; i < player.inventory.armorInventory.size(); i++) {
				ItemStack kept = keepInventory.armorInventory.get(i);
				if(!kept.isEmpty()) {
					ItemStack existing = player.inventory.armorInventory.set(i, kept);
					if(!existing.isEmpty()) {
						displaced.add(existing);
					}
				}
			}
			for(int i = 0; i < player.inventory.offHandInventory.size(); i++) {
				ItemStack kept = keepInventory.offHandInventory.get(i);
				if(!kept.isEmpty()) {
					ItemStack existing = player.inventory.offHandInventory.set(i, kept);

					if(!existing.isEmpty()) {
						displaced.add(existing);
					}
				}
			}
			for(int i = 0; i < player.inventory.mainInventory.size(); i++) {
				ItemStack kept = keepInventory.mainInventory.get(i);
				if(!kept.isEmpty()) {
					ItemStack existing = player.inventory.mainInventory.set(i, kept);
					if(!existing.isEmpty()) {
						displaced.add(existing);
					}
				}
			}
			for(ItemStack extra : displaced) {
				ItemHandlerHelper.giveItemToPlayer(player, extra);
			}
		}
	}
	
	private void keepAllArmor(EntityPlayer player, InventoryPlayer keepInventory) {
		for(int i = 0; i < player.inventory.armorInventory.size(); i++) {
			keepInventory.armorInventory.set(i, player.inventory.armorInventory.get(i).copy());
			player.inventory.armorInventory.set(i, ItemStack.EMPTY);
		}
	}
	
	private void keepOffHand(EntityPlayer player, InventoryPlayer keepInventory) {
		for(int i = 0; i < player.inventory.offHandInventory.size(); i++) {
			keepInventory.offHandInventory.set(i, player.inventory.offHandInventory.get(i).copy());
			player.inventory.offHandInventory.set(i, ItemStack.EMPTY);
		}
	}
}
    
	   
   
