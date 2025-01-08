package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.config.ModConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ContainerRepair.class)
public abstract class ContainerRepairMixin {
    
    @Shadow @Final private IInventory inputSlots;
    @Shadow @Final private IInventory outputSlot;

    //TODO: this only applies to single enchant book + single enchant book
    //TODO: should it also apply to level upgrading when there are multiple enchants?
    @Inject(
            method = "onCraftMatrixChanged",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ContainerRepair;updateRepairOutput()V", shift = At.Shift.AFTER)
    )
    private void soManyEnchantments_vanillaContainerRepair_onCraftMatrixChanged(IInventory inventoryIn, CallbackInfo ci) {
        if(!ModConfig.miscellaneous.removeBookCombinationAnvilCost) return;
        
        ItemStack left = this.inputSlots.getStackInSlot(0);
        ItemStack right = this.inputSlots.getStackInSlot(1);
        //Both enchanted books
        if(left.getItem() != Items.ENCHANTED_BOOK) return;
        if(right.getItem() != Items.ENCHANTED_BOOK) return;

        //Both books no repair cost
        int costLeft = left.getRepairCost();
        int costRight = right.getRepairCost();
        if(costLeft > 0 || costRight > 0) return;

        //Only one enchant, same enchant, same level
        Map<Enchantment, Integer> enchLeft = EnchantmentHelper.getEnchantments(left);
        Map<Enchantment, Integer> enchRight = EnchantmentHelper.getEnchantments(right);
        if(enchLeft.size() != 1 || enchRight.size() != 1) return;
        if(!enchLeft.keySet().toArray()[0].equals(enchRight.keySet().toArray()[0])) return;
        if(!enchLeft.values().toArray()[0].equals(enchRight.values().toArray()[0])) return;

        //Reset repair cost
        ItemStack itemStackOutput = this.outputSlot.getStackInSlot(0);
        itemStackOutput.setRepairCost(0);

        //Also remove the tag for people equating tag count to repair cost existing or not
        if(!itemStackOutput.hasTagCompound()) return;
        if(!itemStackOutput.getTagCompound().hasKey("RepairCost")) return;
        itemStackOutput.getTagCompound().removeTag("RepairCost");
        this.outputSlot.setInventorySlotContents(0,itemStackOutput);
    }
}