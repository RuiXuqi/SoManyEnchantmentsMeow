package com.shultrea.rin.mixin.vanilla.upgrading;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.attributes.EnchantAttribute;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.FromEnchTableThreadLocal;
import com.shultrea.rin.util.IContainerEnchantmentMixin;
import com.shultrea.rin.util.UpgradeRecipe;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    @Shadow public abstract void detectAndSendChanges();

    @Unique private final UpgradeRecipe[] soManyEnchantments$currentRecipes = new UpgradeRecipe[3]; //NOT SYNCED
    @Unique private IInventory soManyEnchantments$upgradeTokenInventory;
    @Unique private int soManyEnchantments$bookshelfPower = 0;
    @Unique private boolean soManyEnchantments$tokenIsLapis = false;
    @Unique private final boolean[] soManyEnchantments$tokenIsCorrect = new boolean[3];

    @WrapOperation(
            method = "<init>(Lnet/minecraft/entity/player/InventoryPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at= @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ContainerEnchantment;addSlotToContainer(Lnet/minecraft/inventory/Slot;)Lnet/minecraft/inventory/Slot;", ordinal = 1)
    )
    private Slot soManyEnchantments_vanillaContainerEnchantment_init(ContainerEnchantment instance, Slot slot, Operation<Slot> original) {
        //Make Lapis slot also allow upgrade tokens
        if(slot.getSlotIndex() == 1 && slot.xPos == 35 && slot.yPos == 47) {
            return original.call(instance, new Slot(this.tableInventory, 1, 35, 47) {
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {
                    return soManyEnchantments$isTokenLapis(stack) || soManyEnchantments$isUpgradeToken(stack);
                }
            });
        }
        return original.call(instance, slot);
    }

    @Inject(
            method = "<init>(Lnet/minecraft/entity/player/InventoryPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At("TAIL")
    )
    private void setSoManyEnchantments_vanillaContainerEnchantment_init_addTokenSlots(InventoryPlayer playerInv, World worldIn, BlockPos pos, CallbackInfo ci) {
        //Add slots for the token item to allow easier syncing
        this.soManyEnchantments$upgradeTokenInventory = new InventoryBasic("Upgrade Tokens", true, 3);
        for (int i = 0; i < 3; i++) {
            this.addSlotToContainer(new Slot(this.soManyEnchantments$upgradeTokenInventory, i, 61, 15 + i * 19) {
                @Override public boolean canTakeStack(@Nonnull EntityPlayer playerIn) {
                    return false;
                }
                @Override public boolean isItemValid(@Nonnull ItemStack stack){
                    return false;
                }
                @Override @SideOnly(Side.CLIENT) public boolean isEnabled() {
                    return false;
                }
            });
        }
    }

    @Unique
    private void soManyEnchantments$setUpgradeToken(ItemStack stack, int slotId){
        Slot slot = this.getSlotFromInventory(this.soManyEnchantments$upgradeTokenInventory,slotId);
        if(slot != null) {
            if (stack.getMetadata() == 32767)
                slot.putStack(new ItemStack(stack.getItem(), stack.getCount(), 0));
            else
                slot.putStack(stack);
        } else {
            SoManyEnchantments.LOGGER.warn("Unable to find Upgrade Token Slot with id {}",slotId);
        }
    }
    
    @Inject(
            method = "broadcastData",
            at = @At(value = "RETURN")
    )
    private void soManyEnchantments_vanillaContainerEnchantment_broadcastData(IContainerListener crafting, CallbackInfo ci) {
        //Send data to client
        ContainerEnchantment thisContainer = (ContainerEnchantment) (Object) this;
        crafting.sendWindowProperty(thisContainer, 10, this.soManyEnchantments$bookshelfPower);
        crafting.sendWindowProperty(thisContainer, 11, this.soManyEnchantments$tokenIsLapis?1:0);
        crafting.sendWindowProperty(thisContainer, 12, this.soManyEnchantments$tokenIsCorrect[0]?1:0);
        crafting.sendWindowProperty(thisContainer, 13, this.soManyEnchantments$tokenIsCorrect[1]?1:0);
        crafting.sendWindowProperty(thisContainer, 14, this.soManyEnchantments$tokenIsCorrect[2]?1:0);
    }
    
    @Inject(
            method = "updateProgressBar",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void soManyEnchantments_vanillaContainerEnchantment_updateProgressBar(int id, int data, CallbackInfo ci) {
        //Update data on client
        switch (id){
            case 10: this.soManyEnchantments$bookshelfPower = data; ci.cancel(); break;
            case 11: this.soManyEnchantments$tokenIsLapis = data == 1; ci.cancel(); break;
            case 12: this.soManyEnchantments$tokenIsCorrect[0] = data == 1; ci.cancel(); break;
            case 13: this.soManyEnchantments$tokenIsCorrect[1] = data == 1; ci.cancel(); break;
            case 14: this.soManyEnchantments$tokenIsCorrect[2] = data == 1; ci.cancel(); break;
        }
    }
    
    /**
     * @author fonnymunkey/nischhelm
     * @reason enchantment table upgrading mechanics
     *
     * Normally overwrite is gross, but with how invasive these mechanics are, if anything else is modifying the enchantment table, its likely going to break anyways
     */
    @Overwrite
    public void onCraftMatrixChanged(@Nonnull IInventory inventoryIn) {
        if(inventoryIn == this.tableInventory) {
            ItemStack targetItem = inventoryIn.getStackInSlot(0);

            //Client checks token as well, but gets updated by server once server finishes this method. Only matters for stack desyncs
            ItemStack tokenItem = inventoryIn.getStackInSlot(1);
            this.soManyEnchantments$tokenIsLapis = soManyEnchantments$isTokenLapis(tokenItem);
            this.soManyEnchantments$tokenIsCorrect[0] = false;
            this.soManyEnchantments$tokenIsCorrect[1] = false;
            this.soManyEnchantments$tokenIsCorrect[2] = false;

            boolean itemIsEnchantable = targetItem.isItemEnchantable();
            boolean itemIsUpgradeable = (targetItem.isItemEnchanted() || targetItem.getItem() == Items.ENCHANTED_BOOK) && (ModConfig.upgrade.allowLevelUpgrades || ModConfig.upgrade.allowTierUpgrades);

            if(!targetItem.isEmpty() && (itemIsEnchantable || itemIsUpgradeable)) {
                if(this.world.isRemote) return;

                //Serverside reset of stored values, sent to client via detectAndSendChanges -> broadcastData
                soManyEnchantments$resetValues();

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
                if(itemIsEnchantable) {
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

                    //this is so stupid, but it only assumes that slot 2 is already a player slot (first inventory slot)
                    //slot 0: item to enchant, slot 1: lapis, slot 2 to 38: player inventory
                    EntityPlayer player = ((InventoryPlayer) getSlot(2).inventory).player;
                    for(int j1 = 0; j1 < 3; ++j1) {
                        if(this.enchantLevels[j1] > 0) {
                            //Pass player attribute to getEnchantmentList -> buildEnchantmentList
                            EnchantAttribute.attributeThreadLocal.set(player.getEntityAttribute(EnchantAttribute.ENCHANTFOCUS));
                            //Tell Random Level Blacklist Mixin that this call is from Enchanting Table
                            FromEnchTableThreadLocal.set(true);

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
                else if(itemIsUpgradeable) {
                    //Check book only
                    if(ModConfig.upgrade.onlyAllowOnBooks && targetItem.getItem() != Items.ENCHANTED_BOOK){
                        detectAndSendChanges();
                        return;
                    }
                    
                    //Get upgradeable enchant entries
                    List<UpgradeRecipe> upgradeRecipes = new ArrayList<>();
                    List<Integer> upgradeLevels = new ArrayList<>();
                    for(UpgradeRecipe recipe : UpgradeRecipe.UPGRADE_RECIPES){
                        int upgradedLevel = recipe.canUpgrade(targetItem);
                        if(upgradedLevel != UpgradeRecipe.DENY) {
                            upgradeRecipes.add(recipe);
                            upgradeLevels.add(upgradedLevel);
                        }
                    }
                    //Cancel if no valid entries were found
                    if(upgradeRecipes.isEmpty()){
                        detectAndSendChanges();
                        return;
                    }
                    
                    //We have valid possible upgrade paths only now dependent on xp cost
                    this.rand.setSeed((long)this.xpSeed);
                    
                    for(int i = 0; i < 3; i++) {
                        //Recheck empty in case there is less potentials than buttons
                        if(upgradeRecipes.isEmpty()) {
                            this.soManyEnchantments$currentRecipes[i] = null;
                            this.soManyEnchantments$setUpgradeToken(ItemStack.EMPTY, i);
                            continue;
                        }
                        
                        //Determine which possible upgrade to pick
                        int randomIndex = this.rand.nextInt(upgradeRecipes.size());
                        UpgradeRecipe pickedRecipe = upgradeRecipes.get(randomIndex);
                        int pickedLevel = upgradeLevels.get(randomIndex);
                        //Remove picked potential so multiple buttons don't get duplicates
                        upgradeRecipes.remove(randomIndex);
                        upgradeLevels.remove(randomIndex);

                        //Save if current token is correct for this recipe
                        this.soManyEnchantments$tokenIsCorrect[i] = pickedRecipe.upgradeTokenIsValid(tokenItem);

                        //Get xp level cost
                        int levelCost;
                        switch(ModConfig.upgrade.levelCostMode) {
                            case 0: levelCost = 1; break;
                            case 2: levelCost = pickedRecipe.getOutputEnchant().getMinEnchantability(pickedLevel); break;
                            default:
                            case 1: levelCost = pickedLevel * soManyEnchantments$getRarityMultiplier(pickedRecipe.getOutputEnchant().getRarity(), targetItem.getItem() == Items.ENCHANTED_BOOK); break;
                        }
                        levelCost = (int)(ModConfig.upgrade.levelCostMultiplier * levelCost);
                        
                        //Finally set arrays ready for upgrading
                        this.enchantLevels[i] = levelCost;
                        this.enchantClue[i] = ModConfig.upgrade.allowEnchantmentClues ? Enchantment.getEnchantmentID(pickedRecipe.getOutputEnchant()) : -1;
                        this.worldClue[i] = ModConfig.upgrade.allowEnchantmentClues ? pickedLevel : -1;
                        this.soManyEnchantments$currentRecipes[i] = pickedRecipe;
                        this.soManyEnchantments$setUpgradeToken(pickedRecipe.getTokenCost(),i);
                    }
                    
                    this.soManyEnchantments$bookshelfPower = (int)bookshelfPower;
                    
                    this.detectAndSendChanges();
                }
            }
            else {
                //Both client+serverside reset all their values if left slot has nothing or nothing enchantable/upgradeable
                soManyEnchantments$resetValues();
            }
        }
    }

    @Unique
    private void soManyEnchantments$resetValues(){
        for(int i = 0; i < 3; ++i) {
            this.enchantLevels[i] = 0;
            this.enchantClue[i] = -1;
            this.worldClue[i] = -1;
            this.soManyEnchantments$currentRecipes[i] = null;
            this.soManyEnchantments$setUpgradeToken(ItemStack.EMPTY, i);
        }
        this.soManyEnchantments$bookshelfPower = 0;
    }

    /**
     * @author fonnymunkey/nischhelm
     * @reason enchantment table upgrading mechanics
     *
     * Normally overwrite is gross, but with how invasive these mechanics are, if anything else is modifying the enchantment table, its likely going to break anyways
     */
    @Overwrite
    public boolean enchantItem(@Nonnull EntityPlayer playerIn, int id) {
        ItemStack targetItem = this.tableInventory.getStackInSlot(0);
        ItemStack tokenItem = this.tableInventory.getStackInSlot(1);
        
        //Sanity check, should only ever be 3 enchantment buttons
        if(id < 0 || id > 2) return false;
        if(targetItem.isEmpty()) return false;
        boolean isCreative = playerIn.capabilities.isCreativeMode;
        
        //No potential upgrade, assume default handling
        if(this.soManyEnchantments$getUpgradeTokenCost(id).isEmpty()) {
            int tokenCost = id + 1;
            int xpCost = this.enchantLevels[id];
            if(!isCreative && (tokenItem.isEmpty() || tokenItem.getCount() < tokenCost || !soManyEnchantments$tokenIsLapis)) return false;
            if(xpCost > 0 && (isCreative || (playerIn.experienceLevel >= tokenCost && playerIn.experienceLevel >= xpCost))) {
                //Add sanity check to prevent double processing during lag
                if(!targetItem.isItemEnchantable()) return false;
                if(!this.world.isRemote) {
                    //Pass player attribute to getEnchantmentList -> buildEnchantmentList
                    EnchantAttribute.attributeThreadLocal.set(playerIn.getEntityAttribute(EnchantAttribute.ENCHANTFOCUS)); //Pass player attribute to buildEnchantmentList
                    //Tell Random Level Blacklist Mixin that this call is from Enchanting Table
                    FromEnchTableThreadLocal.set(true);

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
            int xpCost = this.enchantLevels[id];
            //Check bookshelf power
            if(!isCreative && this.soManyEnchantments$bookshelfPower < ModConfig.upgrade.bookshelvesNeeded) return false;
            //Check xp cost
            if(xpCost > 0 && (isCreative || playerIn.experienceLevel >= xpCost)) {
                if(!this.world.isRemote) {
                    UpgradeRecipe usedRecipe = this.soManyEnchantments$currentRecipes[id].getUsedRecipe(this.world.rand, false);
                    //Sanity null check
                    if(usedRecipe == null) return false;

                    //Check token validity
                    if(!isCreative && (tokenItem.isEmpty() || !usedRecipe.upgradeTokenIsValid(tokenItem))) return false;

                    //Upgrade the enchants
                    Map<Enchantment, Integer> resultingEnchants = usedRecipe.getOutputStackEnchants(targetItem);

                    //Enchanted books don't get properly cleared first when setting new enchantments
                    if(targetItem.getItem() == Items.ENCHANTED_BOOK) {
                        NBTTagCompound tag = targetItem.getTagCompound();
                        if(tag != null) {
                            tag.setTag("StoredEnchantments", new NBTTagList());
                            targetItem.setTagCompound(tag);
                        }
                    }

                    //Set the new enchantments to the item
                    EnchantmentHelper.setEnchantments(resultingEnchants, targetItem);
                    
                    //Remove levels
                    playerIn.onEnchant(targetItem, xpCost);
                    
                    //Take tokens
                    if(!isCreative) {
                        tokenItem.shrink(usedRecipe.getTokenCost().getCount());
                        if(tokenItem.isEmpty())
                            this.tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
                    }
                    
                    //Anvil repair costs
                    if(ModConfig.upgrade.anvilRepairMode > 0) {
                        int repairCost = targetItem.getRepairCost();
                        switch(ModConfig.upgrade.anvilRepairMode) {
                            case 2:
                                repairCost = (int)(repairCost + ModConfig.upgrade.anvilRepairCostAmount);
                                break;
                            case 3:
                                repairCost = (int)(repairCost * ModConfig.upgrade.anvilRepairCostAmount);
                                break;
                            case 1: default:
                                repairCost = 1 + 2 * repairCost;
                                break;
                        }
                        targetItem.setRepairCost(repairCost);
                    }
                    
                    this.tableInventory.markDirty();
                    this.xpSeed = playerIn.getXPSeed();
                    this.onCraftMatrixChanged(this.tableInventory);
                    //TODO: upgrade-specific sound?
                    this.world.playSound(null, this.position, usedRecipe.getIsCursingRecipe() ? SoundEvents.BLOCK_FIRE_EXTINGUISH : SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
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
    @Nonnull
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        
        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            //Target slot to inventory
            if(index == 0) {
                if(!this.mergeItemStack(itemstack1, 2, 38, true))
                    return ItemStack.EMPTY;
            }
            //Lapis slot to inventory
            else if(index == 1) {
                if(!this.mergeItemStack(itemstack1, 2, 38, true))
                    return ItemStack.EMPTY;
            }
            //Lapis and Upgrade Tokens in inventory are only allowed to go into lapis slot
            else if(soManyEnchantments$isTokenLapis(itemstack1) || soManyEnchantments$isUpgradeToken(itemstack1)) {
                if(!this.mergeItemStack(itemstack1, 1, 2, true))
                    return ItemStack.EMPTY;
            }
            else {
                //From inventory to target slot
                if(this.inventorySlots.get(0).getHasStack() || !this.inventorySlots.get(0).isItemValid(itemstack1))
                    return ItemStack.EMPTY;
                
                if(itemstack1.hasTagCompound())
                    this.inventorySlots.get(0).putStack(itemstack1.splitStack(1));
                else if(!itemstack1.isEmpty()) {
                    this.inventorySlots.get(0).putStack(new ItemStack(itemstack1.getItem(), 1, itemstack1.getMetadata()));
                    itemstack1.shrink(1);
                }
                //No handling for swapping between hotbar + inventory
            }
            
            if(itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
            if(itemstack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }

    @Unique
    public boolean soManyEnchantments$isTokenLapis(ItemStack tokenStack) {
        List<ItemStack> ores = OreDictionary.getOres("gemLapis");

        for(ItemStack ore : ores) {
            if(OreDictionary.itemMatches(ore, tokenStack, false)) return true;
        }
        return false;
    }

    @Unique
    private static boolean soManyEnchantments$isUpgradeToken(ItemStack stack) {
        return UpgradeRecipe.stackIsValidUpgradeToken(stack);
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
    public ItemStack soManyEnchantments$getUpgradeTokenCost(int slotId) {
        return this.getSlotFromInventory(this.soManyEnchantments$upgradeTokenInventory,slotId).getStack();
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

    @Override
    @Unique
    public boolean soManyEnchantments$getIsValidAndEnoughToken(int id){
        return this.soManyEnchantments$tokenIsCorrect[id];
    }
}