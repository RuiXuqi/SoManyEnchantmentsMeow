package com.shultrea.rin.enchantments.curses;

import com.shultrea.rin.Interfaces.IEnchantmentCurse;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_040;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentCurseofPossession extends EnchantmentBase implements IEnchantmentCurse {
	
	public static int interval;
	public Map<Integer,Integer> dimensionMap;
	
	public EnchantmentCurseofPossession(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, EntityEquipmentSlot.values());
		this.dimensionMap = new HashMap<Integer,Integer>();
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.curseOfPossession;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.curseOfPossession;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 25 * par1;
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return this.getMinEnchantability(par1) + 25;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.curseOfPossession;
	}
	
	//TODO
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof EnchantmentCurseofDecay);
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void toss(ItemTossEvent event) {
		EntityItem item = event.getEntityItem();
		ItemStack theThrown = item.getItem().copy();
		int l = EnchantmentHelper.getEnchantmentLevel(this, theThrown);
		if(l <= 0) {
			return;
		}
		EntityPlayer player = event.getPlayer();
		if(!player.isCreative()) {
			boolean flag = player.inventory.addItemStackToInventory(theThrown);
			if(!flag) {
				EntityItem entityItem = player.entityDropItem(theThrown, 1.3f);
				entityItem.setOwner(player.getName());
				entityItem.setNoPickupDelay();
				entityItem.setEntityInvulnerable(true);
			}
			else {
				this.damagePlayer(player);
			}
			event.setCanceled(true);
		}
	}
	
	//TODO
	@SubscribeEvent
	public void onExist(WorldTickEvent event) {
		if(event.phase == Phase.START) return;
		//Manage dimension timers
		int dimension = event.world.provider.getDimension();
		Integer dimensionCounterObj = dimensionMap.get(dimension);
		int dimCount = dimensionCounterObj == null ? 0 : dimensionCounterObj.intValue();
		dimCount++;
		//Timer check
		//TODO configurable delay
		if(dimCount > 20) {
			//Timer is up, reset timer
			dimensionMap.put(dimension, 0);
		}
		else {
			//Increment timer and skip tick
			dimensionMap.put(dimension, dimCount);
			return;
		}
		if(interval > 3) {
			List<Entity> list = event.world.loadedEntityList;
			//0.4.1
			if(list == null || list.isEmpty()) return;
			for(int x = 0; x < list.size(); x++) {
				Entity listGet = list.get(x);
				if(listGet instanceof EntityItem) {
					EntityItem item = (EntityItem)listGet;
					ItemStack stack = item.getItem();
					if(item.ticksExisted <= 3) continue;
					//TODO Replace checker with Config
					if(Smc_040.CurseofDecay != null && EnchantmentHelper.getEnchantmentLevel(Smc_040.CurseofDecay, stack) > 0) {
						item.lifespan = 40;
						item.setPickupDelay(10);
					}
					if(EnchantmentHelper.getEnchantmentLevel(this, stack) > 0) {
						//0.4.1
						if(EnchantmentHelper.getEnchantmentLevel(Smc_040.CurseofDecay, stack) <= 0) item.setNoDespawn();
						EntityPlayer thrower = event.world.getClosestPlayerToEntity(item, 32);
						if(thrower != null && !thrower.isCreative()) {
							boolean flag = thrower.addItemStackToInventory(stack);
							if(flag) {
								//TODO proper networking and sync
								list.remove(x);
								this.damagePlayer(thrower);
							}
						}
					}
				}
			}
		}
		else {
			interval++;
		}
	}
	/*
	@SubscribeEvent
	public void onChest(PlayerTickEvent e){
		if(e.phase == Phase.START || e.side == Side.CLIENT)
			return;
		
		if(e.player == null)
			return;
		
		InventoryPlayer inv = e.player.inventory;
		Container c = e.player.openContainer;
		
		boolean flag = c instanceof IInteractionObject;
		boolean flag2 = 
		
		if(!(c instanceof IInteractionObject))
			return;
		
		//System.out.println(c);
	
		
		if(c != null && !c.getInventory().isEmpty()) {
			NonNullList<ItemStack> list = c.getInventory();
			
			for(int x = 0; x < list.size(); x++) {
			
				ItemStack stack = list.get(x);	
				int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
				if(level <= 0)
					continue;
				boolean flag = inv.addItemStackToInventory(stack);
				
				if(!flag) {
					
				}
				else stack.setCount(0);
				
			
		}
		}
		//Inventory inv2;
		//if(!inv.getItemStack().isEmpty()) {
		//	inv.getItemStack().is
		//}
	
	}
	*/
	/*
	@SubscribeEvent
	public void onInteract(PlayerContainerEvent.Close e) {
		if(e.getEntityPlayer().world.isRemote)
			return;
		
		if(e.getEntityPlayer() == null)
			return;
		
		if(e.getContainer() == e.getEntityPlayer().inventoryContainer)
			return;
		
		if(e.getContainer().getInventory().isEmpty())
			return;
		
		if(e.getContainer() != null && e.getContainer() instanceof Container) {
			InventoryPlayer inv = e.getEntityPlayer().inventory;
			NonNullList<ItemStack> list = e.getContainer().inventoryItemStacks;
			
			for(int x = 0; x < list.size(); x++) {
				
				ItemStack stack = list.get(x);	
				System.out.println(e.getContainer() + " " + stack);
				
				int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
				if(level <= 0)
					continue;
				
				boolean flag = inv.addItemStackToInventory(stack);
				
				if(!flag) {
					if(inv.offHandInventory.isEmpty()) {						
						inv.offHandInventory.add(stack);
						stack.setCount(0);
					}
					/*
					else
					for(int d = 0; d < inv.mainInventory.size(); d++) {
						ItemStack r = inv.mainInventory.get(d);
						if(!r.isEmpty()) {
							Slot slot = e.getContainer().getSlot(x);
							boolean test = e.getContainer().canAddItemToSlot(slot, stack, false);
							if(test) {
								
							}
						}
					}
					*/
	//}
	//else stack.setCount(0);
	//}
	//}
	//}
}