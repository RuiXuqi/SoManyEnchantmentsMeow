package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.SoManyEnchantments;
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
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(value = ContainerRepair.class, priority = 2000)
public abstract class ContainerRepairMixin {
    @Shadow @Final private IInventory inputSlots;

    @Shadow public abstract void updateRepairOutput();

    @Shadow @Final private IInventory outputSlot;

    @Redirect(
            method = "onCraftMatrixChanged",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ContainerRepair;updateRepairOutput()V")
    )
    void soManyEnchantments_onCraftMatrixChanged_updateRepairOutput(ContainerRepair instance){
        //Default behavior
        this.updateRepairOutput();

        if(!ModConfig.miscellaneous.removeBookCombinationAnvilCost) return;
        ItemStack itemStackL = this.inputSlots.getStackInSlot(0);
        ItemStack itemStackR = this.inputSlots.getStackInSlot(1);
        //Both enchanted books
        if(itemStackL.getItem()!= Items.ENCHANTED_BOOK) return;
        if(itemStackR.getItem()!= Items.ENCHANTED_BOOK) return;

        //Both books no repair cost
        int repairCostL = itemStackL.getRepairCost();
        int repairCostR = itemStackR.getRepairCost();
        if(repairCostL > 0 || repairCostR > 0) return;

        //Only one enchant, same enchant, same level
        Map<Enchantment, Integer> enchsL = EnchantmentHelper.getEnchantments(itemStackL);
        Map<Enchantment, Integer> enchsR = EnchantmentHelper.getEnchantments(itemStackR);
        if(enchsL.size() != 1 || enchsR.size() != 1) return;
        if(!enchsL.keySet().toArray()[0].equals(enchsR.keySet().toArray()[0])) return;
        if(!enchsL.values().toArray()[0].equals(enchsR.values().toArray()[0])) return;

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