package com.shultrea.rin.mixin.vanilla;

import com.google.common.collect.Lists;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.config.ModConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static net.minecraftforge.common.ForgeHooks.getEnchantPower;

@Mixin(value = ContainerEnchantment.class, priority = 2000)
public abstract class ContainerEnchantmentMixin extends Container {
    @Shadow public IInventory tableInventory;
    @Final @Shadow private World world;
    @Final @Shadow private BlockPos position;
    @Shadow public int[] enchantLevels;
    @Shadow public int[] enchantClue;
    @Shadow public int[] worldClue;
    @Shadow @Final private Random rand;

    @Unique EnchantmentData soManyEnchantments$currentEnch;
    @Unique EnchantmentData soManyEnchantments$upgradedEnch;
    @Unique int soManyEnchantments$levelCost;

    @Redirect(
            method = "<init>(Lnet/minecraft/entity/player/InventoryPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at= @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ContainerEnchantment;addSlotToContainer(Lnet/minecraft/inventory/Slot;)Lnet/minecraft/inventory/Slot;")
    )
    Slot soManyEnchantments_init_addSlotToContainer(ContainerEnchantment instance, Slot slot){
        if(slot.getSlotIndex()==1 && slot.xPos==35 && slot.yPos==47) {
            Slot newSlot = new Slot(this.tableInventory, 1, 35, 47) {
                java.util.List<ItemStack> ores = net.minecraftforge.oredict.OreDictionary.getOres("gemLapis");

                public boolean isItemValid(ItemStack stack) {
                    for (ItemStack ore : ores)
                        if (net.minecraftforge.oredict.OreDictionary.itemMatches(ore, stack, false))
                            return true;
                    return soManyEnchantments$isUpgradeToken(stack);
                }
            };
            return addSlotToContainer(newSlot);
        }
        return addSlotToContainer(slot);
    }

    @Inject(
            method = "transferStackInSlot",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    void soManyEnchantments_transferStackInSlot_head(EntityPlayer playerIn, int index, CallbackInfoReturnable<ItemStack> cir) {
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            if (index != 0 && index != 1 && soManyEnchantments$isUpgradeToken(itemstack1)) {
                if (!this.mergeItemStack(itemstack1, 1, 2, true)) {
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            }
        }
    }

    @Inject(
            method = "onCraftMatrixChanged",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void soManyEnchantments_onCraftMatrixChanged_head(IInventory inventoryIn, CallbackInfo ci) {
        if (inventoryIn != this.tableInventory) return;

        ItemStack itemstackToken = inventoryIn.getStackInSlot(1);
        if (itemstackToken.isEmpty()) return;
        if (!soManyEnchantments$isUpgradeToken(itemstackToken)) return;
        if(itemstackToken.getCount() < ModConfig.upgrade.upgradeTokenAmount) return;

        ItemStack itemstackTargetItem = inventoryIn.getStackInSlot(0);
        boolean isBook = itemstackTargetItem.getItem() == Items.ENCHANTED_BOOK;
        if (itemstackTargetItem.isEmpty() || !itemstackTargetItem.isItemEnchanted()) return;
        if (this.world.isRemote) return;

        float bookshelfPower = 0;
        for (int z = -1; z <= 1; ++z) {
            for (int x = -1; x <= 1; ++x) {
                if ((z != 0 || x != 0) && this.world.isAirBlock(this.position.add(x, 0, z)) && this.world.isAirBlock(this.position.add(x, 1, z))) {
                    bookshelfPower += getEnchantPower(world, position.add(x * 2, 0, z * 2));
                    bookshelfPower += getEnchantPower(world, position.add(x * 2, 1, z * 2));
                    if (x != 0 && z != 0) {
                        bookshelfPower += getEnchantPower(world, position.add(x * 2, 0, z));
                        bookshelfPower += getEnchantPower(world, position.add(x * 2, 1, z));
                        bookshelfPower += getEnchantPower(world, position.add(x, 0, z * 2));
                        bookshelfPower += getEnchantPower(world, position.add(x, 1, z * 2));
                    }
                }
            }
        }
        if(MathHelper.ceil(bookshelfPower) < ModConfig.upgrade.bookshelvesNeeded) return;

        Map<Enchantment, Integer> currentEnchants = EnchantmentHelper.getEnchantments(itemstackTargetItem);

        if(ModConfig.upgrade.onlyAllowOnBooks){
            if(!isBook) return;
            if(currentEnchants.size() != 1) return;
        }

        List<EnchantmentData> upgradeableEnchantments = Lists.<EnchantmentData>newArrayList();
        List<EnchantmentData> upgradedEnchantments = Lists.<EnchantmentData>newArrayList();
        for(Enchantment currEnch: currentEnchants.keySet()) {
            int enchLvl = currentEnchants.get(currEnch);

            Enchantment nextEnchantment = soManyEnchantments$getNextEnchInUpgradeOrder(currEnch);
            if(nextEnchantment==null) continue;
            //TODO: could be a good balance to subtract one lvl when upgrading
            int newLvl = Math.min(enchLvl,nextEnchantment.getMaxLevel()) - ModConfig.upgrade.enchantLvlsReduced;
            if(newLvl<1) continue;
            upgradeableEnchantments.add(new EnchantmentData(currEnch, enchLvl));
            upgradedEnchantments.add(new EnchantmentData(nextEnchantment,newLvl));
        }
        if(upgradeableEnchantments.isEmpty()) return;

        int upgradingIndex = 0;
        switch(ModConfig.upgrade.upgradeMode){
            case "RANDOM": upgradingIndex = rand.nextInt(upgradeableEnchantments.size()); break;
            case "LAST": upgradingIndex = upgradeableEnchantments.size()-1; break;
        }
        soManyEnchantments$currentEnch = upgradeableEnchantments.get(upgradingIndex);
        soManyEnchantments$upgradedEnch = upgradedEnchantments.get(upgradingIndex);

        soManyEnchantments$levelCost = 0;
        switch(ModConfig.upgrade.levelCostMode){
            case "ANVIL": soManyEnchantments$levelCost = soManyEnchantments$getRarityMultiplier(soManyEnchantments$upgradedEnch.enchantment.getRarity(),isBook)* soManyEnchantments$upgradedEnch.enchantmentLevel; break;
            case "ENCHANTABILITY": soManyEnchantments$levelCost = soManyEnchantments$upgradedEnch.enchantment.getMinEnchantability(soManyEnchantments$upgradedEnch.enchantmentLevel); break;
            /*case "NONE":*/ default: break;
        }

        this.enchantLevels[0] = soManyEnchantments$levelCost;
        this.enchantClue[0] = -1;
        this.worldClue[0] = -1;

        if (this.enchantLevels[0] > 0) {
            this.enchantClue[0] = Enchantment.getEnchantmentID(soManyEnchantments$upgradedEnch.enchantment);
            this.worldClue[0] = soManyEnchantments$upgradedEnch.enchantmentLevel;
        }

        this.detectAndSendChanges();
        ci.cancel();
    }

    @Inject(
            method = "enchantItem",
            at = @At("HEAD"),
            cancellable = true
    )
    void soManyEnchantments_enchantItem_head(EntityPlayer playerIn, int id, CallbackInfoReturnable<Boolean> cir){
        ItemStack itemstackTargetItem = this.tableInventory.getStackInSlot(0);
        ItemStack itemstackToken = this.tableInventory.getStackInSlot(1);

        if(id > 0) return;
        if ((itemstackToken.isEmpty() || itemstackToken.getCount() < ModConfig.upgrade.upgradeTokenAmount || !soManyEnchantments$isUpgradeToken(itemstackToken)) && !playerIn.capabilities.isCreativeMode) return;

        if (this.enchantLevels[0] <= 0 || itemstackTargetItem.isEmpty() || (playerIn.experienceLevel < this.enchantLevels[0] && !playerIn.capabilities.isCreativeMode)) return;
        if(this.world.isRemote) return;

        playerIn.onEnchant(itemstackTargetItem, soManyEnchantments$levelCost);

        //Replace enchantment with upgraded version
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemstackTargetItem);
        enchantments.remove(soManyEnchantments$currentEnch.enchantment);
        enchantments.put(soManyEnchantments$upgradedEnch.enchantment, soManyEnchantments$upgradedEnch.enchantmentLevel);
        EnchantmentHelper.setEnchantments(enchantments, itemstackTargetItem);

        if (!playerIn.capabilities.isCreativeMode) {
            itemstackToken.shrink(ModConfig.upgrade.upgradeTokenAmount);

            if (itemstackToken.isEmpty()) {
                this.tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
            }
        }

        this.tableInventory.markDirty();
        this.onCraftMatrixChanged(this.tableInventory);
        this.world.playSound(null, this.position, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        cir.setReturnValue(true);
    }

    @Unique
    private boolean soManyEnchantments$isUpgradeToken(ItemStack stack){
        return stack.getItem().getRegistryName().toString().equals(ModConfig.upgrade.upgradeToken);
    }

    @Unique
    private int soManyEnchantments$getRarityMultiplier(Enchantment.Rarity rarity, boolean isBook){
        switch(rarity){
            case COMMON: return 1;
            case UNCOMMON: return isBook ? 1 : 2;
            case RARE: return isBook ? 2 : 4;
            case VERY_RARE: return isBook ? 4 : 8;
            default: return 0;
        }
    }

    @Unique
    private Enchantment soManyEnchantments$getNextEnchInUpgradeOrder(Enchantment currEnch){
        String enchName = currEnch.getRegistryName().toString();
        if(enchName.contains("somanyenchantments:"))
            enchName = enchName.split(":")[1];
        for (String upgradeList : ModConfig.upgrade.enchantUpgradeOrder) {
            if (!upgradeList.contains(enchName)) continue;

            String[] order = upgradeList.split(" *, *"); //Removing optional whitespace
            int foundIndex = Arrays.asList(order).indexOf(enchName);
            //If we're already maxed, we don't need to upgrade further
            if (foundIndex >= order.length-1) continue;

            String nextEnchName = order[foundIndex+1];
            if(!nextEnchName.contains(":")) nextEnchName = "somanyenchantments:"+nextEnchName;
            Enchantment nextEnchantment = Enchantment.getEnchantmentByLocation(nextEnchName);
            if(nextEnchantment == null){
                SoManyEnchantments.LOGGER.info("Could not find upgraded enchantment {}", order[foundIndex + 1]);
                return null;
            }
            return nextEnchantment;
        }
        return null;
    }
}