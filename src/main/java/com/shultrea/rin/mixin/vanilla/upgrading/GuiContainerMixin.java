package com.shultrea.rin.mixin.vanilla.upgrading;

import com.shultrea.rin.util.IGuiContainerMixin;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class GuiContainerMixin implements IGuiContainerMixin {
    @Inject(
            method = "drawScreen",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderHelper;disableStandardItemLighting()V", ordinal = 1)
    )
    private void soManyEnchantments_vanillaGuiContainer_drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
        soManyEnchantments$drawUpgradeSlots();
    }
}