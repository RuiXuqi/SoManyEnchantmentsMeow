package com.shultrea.rin.mixin.vanilla;

import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiEnchantment.class, priority = 2000)
public abstract class GuiEnchantmentMixin extends GuiContainer {
    public GuiEnchantmentMixin(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Inject(
            method = "updateScreen",
            at = @At(value = "HEAD")
    )
    void soManyEnchantments_updateScreen(CallbackInfo ci){
        ItemStack itemStack = this.inventorySlots.getSlot(0).getStack();
        if(itemStack.isItemEnchanted()) {
            //render slot overlay
        } else {
            //remove slot overlay
        }
    }
}