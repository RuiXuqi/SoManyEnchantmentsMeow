package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.config.UpgradeConfig;
import com.shultrea.rin.util.IContainerEnchantmentMixin;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.*;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ContainerEnchantment.class)
public abstract class ContainerEnchantmentMixin extends Container implements IContainerEnchantmentMixin {
    
    @Shadow public IInventory tableInventory;
    @Shadow public int[] enchantLevels;
    @Shadow public int[] enchantClue;
    @Shadow public int[] worldClue;
    @Shadow @Final private World world;
    @Shadow @Final private BlockPos position;
    @Shadow @Final private Random rand;
    @Shadow public int xpSeed;
    
    @Shadow protected abstract List<EnchantmentData> getEnchantmentList(ItemStack stack, int enchantSlot, int level);
    
    @Unique
    private final UpgradeConfig.UpgradePotentialEntry[] soManyEnchantments$currentPotentials = new UpgradeConfig.UpgradePotentialEntry[3];
    @Unique
    private final int[] soManyEnchantments$upgradeTokenCost = new int[3];
    @Unique
    private int soManyEnchantments$bookshelfPower = 0;
    @Unique
    private boolean soManyEnchantments$tokenIsLapis = false;

    @Redirect(
            method = "<init>(Lnet/minecraft/entity/player/InventoryPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at= @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ContainerEnchantment;addSlotToContainer(Lnet/minecraft/inventory/Slot;)Lnet/minecraft/inventory/Slot;", ordinal = 1)
    )
    private Slot soManyEnchantments_vanillaContainerEnchantment_init(ContainerEnchantment instance, Slot slot) {
        if(slot.getSlotIndex() == 1 && slot.xPos == 35 && slot.yPos == 47) {
            return addSlotToContainer(new Slot(this.tableInventory, 1, 35, 47) {
                @Override
                public boolean isItemValid(ItemStack stack) {
                    return soManyEnchantments$isTokenLapis(stack) || soManyEnchantments$isUpgradeToken(stack);
                }
            });
        }
        return addSlotToContainer(slot);
    }

    @Unique
    public boolean soManyEnchantments$isTokenLapis(ItemStack tokenStack) {
        List<ItemStack> ores = OreDictionary.getOres("gemLapis");

        for(ItemStack ore : ores) {
            if(OreDictionary.itemMatches(ore, tokenStack, false)) return true;
        }
        return false;
    }
    
    @Inject(
            method = "broadcastData",
            at = @At(value = "RETURN")
    )
    private void soManyEnchantments_vanillaContainerEnchantment_broadcastData(IContainerListener crafting, CallbackInfo ci) {
        //Send data to client
        crafting.sendWindowProperty(((ContainerEnchantment)(Object)this), 10, this.soManyEnchantments$upgradeTokenCost[0]);
        crafting.sendWindowProperty(((ContainerEnchantment)(Object)this), 11, this.soManyEnchantments$upgradeTokenCost[1]);
        crafting.sendWindowProperty(((ContainerEnchantment)(Object)this), 12, this.soManyEnchantments$upgradeTokenCost[2]);
        crafting.sendWindowProperty(((ContainerEnchantment)(Object)this), 13, this.soManyEnchantments$bookshelfPower);
        crafting.sendWindowProperty(((ContainerEnchantment)(Object)this), 14, this.soManyEnchantments$tokenIsLapis?1:0);
    }
    
    @Inject(
            method = "updateProgressBar",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void soManyEnchantments_vanillaContainerEnchantment_updateProgressBar(int id, int data, CallbackInfo ci) {
        //Update data on client
        if(id == 10) {
            this.soManyEnchantments$upgradeTokenCost[0] = data;
            ci.cancel();
        }
        else if(id == 11) {
            this.soManyEnchantments$upgradeTokenCost[1] = data;
            ci.cancel();
        }
        else if(id == 12) {
            this.soManyEnchantments$upgradeTokenCost[2] = data;
            ci.cancel();
        }
        else if(id == 13) {
            this.soManyEnchantments$bookshelfPower = data;
            ci.cancel();
        }
        else if(id == 14) {
            this.soManyEnchantments$tokenIsLapis = data == 1;
            ci.cancel();
        }
    }
    
    /**
     * @author fonnymunkey/nischhelm
     * @reason enchantment table upgrading mechanics
     *
     * Normally overwrite is gross, but with how invasive these mechanics are, if anything else is modifying the enchantment table, its likely going to break anyways
     */
    @Overwrite
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        if(inventoryIn == this.tableInventory) {
            //Clear everything first regardless to avoid edge cases?
            for(int i = 0; i < 3; ++i) {
                this.enchantLevels[i] = 0;
                this.enchantClue[i] = -1;
                this.worldClue[i] = -1;
                this.soManyEnchantments$currentPotentials[i] = null;
                this.soManyEnchantments$upgradeTokenCost[i] = -1;
                this.soManyEnchantments$bookshelfPower = 0;
                this.soManyEnchantments$tokenIsLapis = false;
            }
            
            ItemStack targetItem = inventoryIn.getStackInSlot(0);
            ItemStack tokenItem = inventoryIn.getStackInSlot(1);
            this.soManyEnchantments$tokenIsLapis = soManyEnchantments$isTokenLapis(tokenItem);

            if(!targetItem.isEmpty() && !this.world.isRemote) {
                //Get bookshelf power
                float bookshelfPower = 0;
                for(int z = -1; z <= 1; ++z) {
                    for(int x = -1; x <= 1; ++x) {
                        if((z != 0 || x != 0) && this.world.isAirBlock(this.position.add(x, 0, z)) && this.world.isAirBlock(this.position.add(x, 1, z))) {
                            bookshelfPower += ForgeHooks.getEnchantPower(world, position.add(x * 2, 0, z * 2));
                            bookshelfPower += ForgeHooks.getEnchantPower(world, position.add(x * 2, 1, z * 2));
                            if(x != 0 && z != 0) {
                                bookshelfPower += ForgeHooks.getEnchantPower(world, position.add(x * 2, 0, z));
                                bookshelfPower += ForgeHooks.getEnchantPower(world, position.add(x * 2, 1, z));
                                bookshelfPower += ForgeHooks.getEnchantPower(world, position.add(x, 0, z * 2));
                                bookshelfPower += ForgeHooks.getEnchantPower(world, position.add(x, 1, z * 2));
                            }
                        }
                    }
                }
                
                //Enchanting
                if(targetItem.isItemEnchantable()) {
                    this.rand.setSeed((long)this.xpSeed);
                    
                    for(int i1 = 0; i1 < 3; ++i1) {
                        this.enchantLevels[i1] = EnchantmentHelper.calcItemStackEnchantability(this.rand, i1, (int)bookshelfPower, targetItem);
                        this.enchantClue[i1] = -1;
                        this.worldClue[i1] = -1;
                        
                        if(this.enchantLevels[i1] < i1 + 1) {
                            this.enchantLevels[i1] = 0;
                        }
                        this.enchantLevels[i1] = net.minecraftforge.event.ForgeEventFactory.onEnchantmentLevelSet(world, position, i1, (int)bookshelfPower, targetItem, enchantLevels[i1]);
                    }
                    
                    for(int j1 = 0; j1 < 3; ++j1) {
                        if(this.enchantLevels[j1] > 0) {
                            List<EnchantmentData> list = this.getEnchantmentList(targetItem, j1, this.enchantLevels[j1]);
                            
                            if(list != null && !list.isEmpty()) {
                                EnchantmentData enchantmentdata = list.get(this.rand.nextInt(list.size()));
                                this.enchantClue[j1] = Enchantment.getEnchantmentID(enchantmentdata.enchantment);
                                this.worldClue[j1] = enchantmentdata.enchantmentLevel;
                            }
                        }
                    }
                    
                    this.detectAndSendChanges();
                }
                //Upgrading
                else if((targetItem.isItemEnchanted() || targetItem.getItem() == Items.ENCHANTED_BOOK) && (ModConfig.upgrade.allowLevelUpgrades || ModConfig.upgrade.allowTierUpgrades)) {
                    //Check book only
                    if(ModConfig.upgrade.onlyAllowOnBooks && targetItem.getItem() != Items.ENCHANTED_BOOK) return;
                    
                    //Get upgradeable enchant entries
                    List<UpgradeConfig.UpgradePotentialEntry> upgradeEntries = new ArrayList<>();
                    Map<Enchantment, Integer> currentEnchants = EnchantmentHelper.getEnchantments(targetItem);
                    for(Map.Entry<Enchantment, Integer> currentEnchantEntry : currentEnchants.entrySet()) {
                        Enchantment currentEnchantment = currentEnchantEntry.getKey();
                        int currentLevel = currentEnchantEntry.getValue();
                        
                        //Level check first
                        if(ModConfig.upgrade.allowLevelUpgrades && !currentEnchantment.isCurse() && currentLevel < currentEnchantment.getMaxLevel()) {
                            //Check fail handling
                            Enchantment upgradeFailEnchant = null;
                            boolean upgradeFailRemovesOriginal = false;
                            if(ModConfig.upgrade.upgradeFailChance > 0 && !ModConfig.upgrade.upgradeFailTierOnly) {
                                for(UpgradeConfig.UpgradeFailEntry failEntry : UpgradeConfig.getUpgradeFailEntries()) {
                                    if(failEntry.canEnchantmentFail(currentEnchantment)) {
                                        upgradeFailEnchant = failEntry.getCurse();
                                        upgradeFailRemovesOriginal = ModConfig.upgrade.upgradeFailRemovesOriginal;
                                        break;
                                    }
                                }
                            }
                            //Valid upgrade path
                            upgradeEntries.add(new UpgradeConfig.UpgradePotentialEntry(currentEnchantment, currentEnchantment, currentLevel, currentLevel + 1, ModConfig.upgrade.upgradeTokenAmountLevel, upgradeFailEnchant, upgradeFailRemovesOriginal));
                        }
                        //Else tier check
                        else if(ModConfig.upgrade.allowTierUpgrades) {
                            //Check for valid tiers
                            for(UpgradeConfig.UpgradeTierEntry upgradeableEntry : UpgradeConfig.getUpgradeTierEntries()) {
                                if(upgradeableEntry.isEnchantmentUpgradeable(currentEnchantment)) {
                                    Enchantment upgradedEnchantment = upgradeableEntry.getUpgradedEnchantment(currentEnchantment);
                                    //Compatibility check with existing enchantments
                                    if(upgradedEnchantment != null && ModConfig.upgrade.onlyAllowCompatible) {
                                        for(Enchantment ench : currentEnchants.keySet()) {
                                            if(ench != currentEnchantment && !upgradedEnchantment.isCompatibleWith(ench)) {
                                                upgradedEnchantment = null;
                                                break;
                                            }
                                        }
                                    }
                                    //Upgrade enchantment is valid
                                    if(upgradedEnchantment != null) {
                                        //Get upgraded level value
                                        int upgradedLevel = Math.min(upgradedEnchantment.getMaxLevel(),
                                                                     Math.max(upgradedEnchantment.getMinLevel(),
                                                                              ModConfig.upgrade.upgradedTierLevelMode == 0 ?
                                                                              currentLevel - ModConfig.upgrade.upgradedTierLevelReduction :
                                                                              upgradedEnchantment.getMinLevel()));
                                        //Check fail handling
                                        Enchantment upgradeFailEnchant = null;
                                        boolean upgradeFailRemovesOriginal = false;
                                        if(ModConfig.upgrade.upgradeFailChance > 0) {
                                            for(UpgradeConfig.UpgradeFailEntry failEntry : UpgradeConfig.getUpgradeFailEntries()) {
                                                if(failEntry.canEnchantmentFail(currentEnchantment)) {
                                                    upgradeFailEnchant = failEntry.getCurse();
                                                    upgradeFailRemovesOriginal = ModConfig.upgrade.upgradeFailRemovesOriginal;
                                                    break;
                                                }
                                            }
                                        }
                                        //Valid upgrade path
                                        upgradeEntries.add(new UpgradeConfig.UpgradePotentialEntry(currentEnchantment, upgradedEnchantment, currentLevel, upgradedLevel, ModConfig.upgrade.upgradeTokenAmountTier, upgradeFailEnchant, upgradeFailRemovesOriginal));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    //Cancel if no valid entries were found
                    if(upgradeEntries.isEmpty()) return;
                    
                    //We have valid possible upgrade paths only now dependent on xp cost
                    this.rand.setSeed((long)this.xpSeed);
                    
                    for(int i = 0; i < 3; i++) {
                        //Recheck empty in case there is less potentials than buttons
                        if(upgradeEntries.isEmpty()) {
                            this.soManyEnchantments$currentPotentials[i] = null;
                            this.soManyEnchantments$upgradeTokenCost[i] = -1;
                            continue;
                        }
                        
                        //Determine which possible upgrade to pick
                        UpgradeConfig.UpgradePotentialEntry pickedPotential = upgradeEntries.get(rand.nextInt(upgradeEntries.size()));
                        //Remove picked potential so multiple buttons don't get duplicates
                        upgradeEntries.remove(pickedPotential);
                        
                        //Get xp level cost
                        int levelCost;
                        switch(ModConfig.upgrade.levelCostMode) {
                            case 0:
                                levelCost = 1;
                                break;
                            case 1:
                                levelCost = pickedPotential.getLevelUpgrade() * soManyEnchantments$getRarityMultiplier(pickedPotential.getEnchantmentUpgrade().getRarity(), targetItem.getItem() == Items.ENCHANTED_BOOK);
                                break;
                            case 2:
                                levelCost = pickedPotential.getEnchantmentUpgrade().getMinEnchantability(pickedPotential.getLevelUpgrade());
                                break;
                            default:
                                levelCost = pickedPotential.getLevelUpgrade() * soManyEnchantments$getRarityMultiplier(pickedPotential.getEnchantmentUpgrade().getRarity(), targetItem.getItem() == Items.ENCHANTED_BOOK);
                                break;
                        }
                        levelCost = (int)(ModConfig.upgrade.levelCostMultiplier * (float)levelCost);
                        
                        //Finally set arrays ready for upgrading
                        this.enchantLevels[i] = levelCost;
                        this.enchantClue[i] = ModConfig.upgrade.allowEnchantmentClues ? Enchantment.getEnchantmentID(pickedPotential.getEnchantmentUpgrade()) : -1;
                        this.worldClue[i] = ModConfig.upgrade.allowEnchantmentClues ? pickedPotential.getLevelUpgrade() : -1;
                        this.soManyEnchantments$currentPotentials[i] = pickedPotential;
                        this.soManyEnchantments$upgradeTokenCost[i] = pickedPotential.getTokenCost();
                    }
                    
                    this.soManyEnchantments$bookshelfPower = (int)bookshelfPower;
                    
                    this.detectAndSendChanges();
                }
            }
        }
    }
    
    /**
     * @author fonnymunkey/nischhelm
     * @reason enchantment table upgrading mechanics
     *
     * Normally overwrite is gross, but with how invasive these mechanics are, if anything else is modifying the enchantment table, its likely going to break anyways
     */
    @Overwrite
    public boolean enchantItem(EntityPlayer playerIn, int id) {
        ItemStack targetItem = this.tableInventory.getStackInSlot(0);
        ItemStack tokenItem = this.tableInventory.getStackInSlot(1);
        
        //Sanity check, should only ever be 3 enchantment buttons
        if(id < 0 || id > 2) return false;
        if(targetItem.isEmpty()) return false;
        boolean isCreative = playerIn.capabilities.isCreativeMode;
        
        //No potential upgrade, assume default handling
        if(this.soManyEnchantments$upgradeTokenCost[id] < 0) {
            int tokenCost = id + 1;
            int xpCost = this.enchantLevels[id];
            if(!isCreative && (tokenItem.isEmpty() || tokenItem.getCount() < tokenCost || !soManyEnchantments$tokenIsLapis)) return false;
            if(xpCost > 0 && (isCreative || (playerIn.experienceLevel >= tokenCost && playerIn.experienceLevel >= xpCost))) {
                //Add sanity check to prevent double processing during lag
                if(!targetItem.isItemEnchantable()) return false;
                if(!this.world.isRemote) {
                    List<EnchantmentData> list = this.getEnchantmentList(targetItem, id, xpCost);
                    if(!list.isEmpty()) {
                        playerIn.onEnchant(targetItem, tokenCost);
                        
                        boolean isBook = targetItem.getItem() == Items.BOOK;
                        if(isBook) {
                            targetItem = new ItemStack(Items.ENCHANTED_BOOK);
                            this.tableInventory.setInventorySlotContents(0, targetItem);
                        }
						
						for(EnchantmentData enchantmentdata : list) {
							if(isBook) ItemEnchantedBook.addEnchantment(targetItem, enchantmentdata);
							else targetItem.addEnchantment(enchantmentdata.enchantment, enchantmentdata.enchantmentLevel);
						}
                        
                        if(!isCreative) {
                            tokenItem.shrink(tokenCost);
                            if(tokenItem.isEmpty()) {
                                this.tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
                            }
                        }
                        
                        playerIn.addStat(StatList.ITEM_ENCHANTED);
                        if(playerIn instanceof EntityPlayerMP) {
                            CriteriaTriggers.ENCHANTED_ITEM.trigger((EntityPlayerMP)playerIn, targetItem, tokenCost);
                        }
                        
                        this.tableInventory.markDirty();
                        this.xpSeed = playerIn.getXPSeed();
                        this.onCraftMatrixChanged(this.tableInventory);
                        this.world.playSound(null, this.position, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
                    }
                }
                return true;
            }
            return false;
        }
        //Potential upgrade exists
        else {
            int tokenCost = this.soManyEnchantments$upgradeTokenCost[id];
            int xpCost = this.enchantLevels[id];
            //Check token validity
            if(tokenItem.isEmpty() || !soManyEnchantments$isUpgradeToken(tokenItem)) return false;
            //Check token cost
            if(!isCreative && tokenItem.getCount() < tokenCost) return false;
            //Check bookshelf power
            if(!isCreative && this.soManyEnchantments$bookshelfPower < ModConfig.upgrade.bookshelvesNeeded) return false;
            //Check xp cost
            if(xpCost > 0 && (isCreative || playerIn.experienceLevel >= xpCost)) {
                if(!this.world.isRemote) {
                    UpgradeConfig.UpgradePotentialEntry potentialEntry = this.soManyEnchantments$currentPotentials[id];
                    //Sanity null check
                    if(potentialEntry == null) return false;
                    //Get existing enchants
                    Map<Enchantment,Integer> currentEnchantments = EnchantmentHelper.getEnchantments(targetItem);
                    //Need to recreate the ordered map otherwise modification will mess up existing ordering
                    Map<Enchantment,Integer> newEnchantments = new LinkedHashMap<>();
                    //Check if failure, avoid using xpSeed rand to maintain randomness and avoid affecting other results
                    boolean isFail = this.world.rand.nextFloat() < ModConfig.upgrade.upgradeFailChance && (!ModConfig.upgrade.upgradeFailTierOnly || potentialEntry.getEnchantmentPrevious() != potentialEntry.getEnchantmentUpgrade());
                    
                    for(Map.Entry<Enchantment,Integer> entry : currentEnchantments.entrySet()) {
                        if(potentialEntry.getEnchantmentPrevious().equals(entry.getKey())) {
                            if(!isFail) {
                                //Not a failure, allow upgrade
                                newEnchantments.put(potentialEntry.getEnchantmentUpgrade(), potentialEntry.getLevelUpgrade());
                            }
                            else if(!potentialEntry.getFailRemovesOriginal()) {
                                //Failure but don't remove, just use old enchantment values
                                newEnchantments.put(entry.getKey(), entry.getValue());
                            }
                        }
                        else {
                            //Not the matching upgrade enchant
                            newEnchantments.put(entry.getKey(), entry.getValue());
                        }
                    }
                    
                    //Add the curse if there is one
                    if(isFail && potentialEntry.getEnchantmentFail() != null) {
                        Enchantment curse = potentialEntry.getEnchantmentFail();
                        int minLvl = curse.getMinLevel();
                        if(newEnchantments.containsKey(curse)) {
                            int currentLvl  = newEnchantments.get(curse);
                            int newLvl = MathHelper.clamp(currentLvl+1,minLvl,curse.getMaxLevel());
                            newEnchantments.put(curse, newLvl);
                        } else
                            newEnchantments.put(curse, minLvl);
                    }
                    
                    //Enchanted books don't get properly cleared first when setting new enchantments
                    if(targetItem.getItem() == Items.ENCHANTED_BOOK) {
                        NBTTagCompound tag = targetItem.getTagCompound();
                        if(tag != null) {
                            tag.setTag("StoredEnchantments", new NBTTagList());
                            targetItem.setTagCompound(tag);
                        }
                    }
                    //Set the new enchantments to the item
                    EnchantmentHelper.setEnchantments(newEnchantments, targetItem);
                    
                    //Remove levels
                    playerIn.onEnchant(targetItem, xpCost);
                    
                    //Take tokens
                    if(!isCreative) {
                        tokenItem.shrink(tokenCost);
                        if(tokenItem.isEmpty()) {
                            this.tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
                        }
                    }
                    
                    //Anvil repair costs
                    if(ModConfig.upgrade.anvilRepairMode > 0) {
                        int repairCost = targetItem.getRepairCost();
                        switch(ModConfig.upgrade.anvilRepairMode) {
                            case 1:
                                repairCost = 1 + 2 * repairCost;
                                break;
                            case 2:
                                repairCost = (int)(repairCost + ModConfig.upgrade.anvilRepairCostAmount);
                                break;
                            case 3:
                                repairCost = (int)(repairCost * ModConfig.upgrade.anvilRepairCostAmount);
                                break;
                            default:
                                repairCost = 1 + 2 * repairCost;
                                break;
                        }
                        targetItem.setRepairCost(repairCost);
                    }
                    
                    this.tableInventory.markDirty();
                    this.xpSeed = playerIn.getXPSeed();
                    this.onCraftMatrixChanged(this.tableInventory);
                    //TODO: upgrade-specific sound?
                    this.world.playSound(null, this.position, isFail ? SoundEvents.BLOCK_FIRE_EXTINGUISH : SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
                }
                return true;
            }
            return false;
        }
    }
    
    /**
     * @author fonnymunkey/nischhelm
     * @reason enchantment table upgrading mechanics
     *
     * Normally overwrite is gross, but with how invasive these mechanics are, if anything else is modifying the enchantment table, its likely going to break anyways
     */
    @Overwrite
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        
        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if(index == 0) {
                if(!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if(index == 1) {
                if(!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if(itemstack1.getItem() == Items.DYE && EnumDyeColor.byDyeDamage(itemstack1.getMetadata()) == EnumDyeColor.BLUE) {
                if(!this.mergeItemStack(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if(soManyEnchantments$isUpgradeToken(itemstack1)) {
                if(!this.mergeItemStack(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else {
                if(this.inventorySlots.get(0).getHasStack() || !this.inventorySlots.get(0).isItemValid(itemstack1)) {
                    return ItemStack.EMPTY;
                }
                
                if(itemstack1.hasTagCompound()) {
                    this.inventorySlots.get(0).putStack(itemstack1.splitStack(1));
                }
                else if(!itemstack1.isEmpty()) {
                    this.inventorySlots.get(0).putStack(new ItemStack(itemstack1.getItem(), 1, itemstack1.getMetadata()));
                    itemstack1.shrink(1);
                }
            }
            
            if(itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
            if(itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }
    
    @Unique
    private static boolean soManyEnchantments$isUpgradeToken(ItemStack stack) {
        return UpgradeConfig.getUpgradeTokenItem() == stack.getItem();
    }

    @Unique
    private static int soManyEnchantments$getRarityMultiplier(Enchantment.Rarity rarity, boolean isBook) {
        //Vanilla lvl cost multipliers when using anvil
        switch(rarity){
            case COMMON: return 1;
            case UNCOMMON: return isBook ? 1 : 2;
            case RARE: return isBook ? 2 : 4;
            case VERY_RARE: return isBook ? 4 : 8;
            default: return 0;
        }
    }
    
    @Override
    @Unique
    public int soManyEnchantments$getUpgradeTokenCost(int slot) {
        return this.soManyEnchantments$upgradeTokenCost[slot];
    }
    
    @Override
    @Unique
    public int soManyEnchantments$getBookshelfPower() {
        return this.soManyEnchantments$bookshelfPower;
    }

    @Override
    @Unique
    public boolean soManyEnchantments$getTokenIsLapis(){
        return this.soManyEnchantments$tokenIsLapis;
    }
}