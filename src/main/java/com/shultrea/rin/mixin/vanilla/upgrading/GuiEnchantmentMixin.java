package com.shultrea.rin.mixin.vanilla.upgrading;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Cancellable;
import com.llamalad7.mixinextras.sugar.Local;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.IContainerEnchantmentMixin;
import com.shultrea.rin.util.IGuiContainerMixin;
import com.shultrea.rin.util.RainbowUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiEnchantment.class)
public abstract class GuiEnchantmentMixin extends GuiContainer implements IGuiContainerMixin {
    @Shadow @Final private ContainerEnchantment container;

    @Unique private static final RainbowUtil soManyEnchantments$rainbowHandler0 = new RainbowUtil(0);
    @Unique private static final RainbowUtil soManyEnchantments$rainbowHandler1 = new RainbowUtil(1);
    @Unique private static final RainbowUtil soManyEnchantments$rainbowHandler2 = new RainbowUtil(2);

    @Unique private boolean soManyEnchantments$isInUpgradeDraw = false;

    public GuiEnchantmentMixin(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    /**-------------------------------- drawGuiContainerBackgroundLayer --------------------------------**/

    @ModifyExpressionValue(
            method = "drawGuiContainerBackgroundLayer",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;experienceLevel:I")
    )
    private int soManyEnchantments_vanillaGuiEnchantment_drawGuiContainerBackgroundLayer_drawGrayIfNotLapis(int original){
        //Adds requirement of lapis in lapis slot, instead of upgrade token (if player not in creative)
        boolean tokenIsLapis = ((IContainerEnchantmentMixin) this.container).soManyEnchantments$getTokenIsLapis();
        if(!tokenIsLapis) return Integer.MIN_VALUE;
        return original;
    }

    @ModifyVariable(
            method = "drawGuiContainerBackgroundLayer",
            at = @At(value = "LOAD", ordinal = 0),
            name = "k1"
    )
    private int soManyEnchantments_vanillaGuiEnchantment_drawGuiContainerBackgroundLayer_upgrade(
            int xpCost,
            @Local(argsOnly = true, ordinal = 0) int mouseX,
            @Local(argsOnly = true, ordinal = 1) int mouseY,
            @Local(name = "l") int button,
            //Don't ask me to give those values actual names :skull:
            @Local(name = "i") int i,
            @Local(name = "j") int j,
            @Local(name = "i1") int i1,
            @Local(name = "j1") int j1
    ){
        soManyEnchantments$isInUpgradeDraw = false; //always reset first

        if(xpCost == 0) return xpCost; //default empty box handling

        String s = "" + xpCost;
        int l1 = 86 - this.fontRenderer.getStringWidth(s);
        String s1 = EnchantmentNameParts.getInstance().generateNewRandomName(this.fontRenderer, l1);
        FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;

        ItemStack upgradeToken = ((IContainerEnchantmentMixin)this.container).soManyEnchantments$getUpgradeTokenCost(button);
        boolean isUpgrade = !upgradeToken.isEmpty();

        if(!isUpgrade) return xpCost; //Default enchanting handling

        boolean tokenEnoughAndCorrect = ((IContainerEnchantmentMixin)this.container).soManyEnchantments$getIsValidAndEnoughToken(button);

        int runeColor;
        int xpColor;

        RainbowUtil rainbow;
        switch(button) {
            case 1: rainbow = soManyEnchantments$rainbowHandler1; break;
            case 2: rainbow = soManyEnchantments$rainbowHandler2; break;
            default: case 0: rainbow = soManyEnchantments$rainbowHandler0; break;
        }

        if(!this.mc.player.capabilities.isCreativeMode && (
                    !tokenEnoughAndCorrect ||
                    this.mc.player.experienceLevel < xpCost ||
                    ((IContainerEnchantmentMixin)this.container).soManyEnchantments$getBookshelfPower() < ModConfig.upgrade.bookshelvesNeeded
                )
        ) {
            this.drawTexturedModalRect(i1, j + 14 + 19 * button, 0, 185, 108, 19);

            runeColor = rainbow.getDesaturatedDecimalColor(0.5F);//Unhighlighted rune text
            rainbow.tick();

            xpColor = 4226832;//XP cost invalid

            fontrenderer.drawSplitString(s1, j1, j + 16 + 19 * button, l1, (runeColor & 16711422) >> 1);
        }
        else {
            int j2 = mouseX - (i + 60);
            int k2 = mouseY - (j + 14 + 19 * button);

            if(j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19) {
                this.drawTexturedModalRect(i1, j + 14 + 19 * button, 0, 204, 108, 19);
                runeColor = rainbow.getDecimalColor();//Highlighted rune text
                rainbow.tick();
            }
            else {
                this.drawTexturedModalRect(i1, j + 14 + 19 * button, 0, 166, 108, 19);
                runeColor = rainbow.getDesaturatedDecimalColor(0.5F);//Unhighlighted rune text
                rainbow.tick();
            }

            xpColor = 8453920;//XP cost valid

            fontrenderer.drawSplitString(s1, j1, j + 16 + 19 * button, l1, runeColor);
        }

        fontrenderer = this.mc.fontRenderer;
        fontrenderer.drawStringWithShadow(s, (float)(j1 + 86 - fontrenderer.getStringWidth(s)), (float)(j + 16 + 19 * button + 7), xpColor);

        soManyEnchantments$isInUpgradeDraw = true;
        return 0; //go into empty rectangle draw (k1 == 0) and cancel that call
    }

    @WrapWithCondition(
            method = "drawGuiContainerBackgroundLayer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiEnchantment;drawTexturedModalRect(IIIIII)V", ordinal = 1)
    )
    private boolean soManyEnchantments_vanillaGuiEnchantment_drawGuiContainerBackgroundLayer_preventEmptyBox(GuiEnchantment instance, int x, int y, int textureX, int textureY, int width, int height){
        //Just an artefact of how we handle the extra branch for upgrading
        //if(xpCost == 0 || isUpgrading) {
            // if (!isUpgrading) empty box   <--- this is the condition we add here
            // else ...
        //} else ... normal enchanting
        return !soManyEnchantments$isInUpgradeDraw;
    }

    /**-------------------------------- drawScreen --------------------------------**/

    @ModifyVariable(
            method = "drawScreen",
            at = @At(value = "LOAD", ordinal = 0),
            name = "i"
    )
    private int soManyEnchantments_vanillaGuiEnchantment_drawScreen_drawGrayIfNotLapis(int value){
        //TextFormatting textformatting = i >= i1 ? TextFormatting.GRAY : TextFormatting.RED;
            //turns to
        //TextFormatting textformatting = i >= i1 && tokenIsLapis ? TextFormatting.GRAY : TextFormatting.RED;

        //draw lapis amount text red if theres not lapis but upgrade token in the lapis slot
        boolean tokenIsLapis = ((IContainerEnchantmentMixin) this.container).soManyEnchantments$getTokenIsLapis();
        if(!tokenIsLapis) return Integer.MIN_VALUE;
        return value;
    }

    @ModifyExpressionValue(
            method = "drawScreen",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;experienceLevel:I")
    )
    private int soManyEnchantments_vanillaGuiEnchantment_drawScreen_upgrading(
            int experienceLevel,
            @Local(argsOnly = true, ordinal = 0) int mouseX,
            @Local(argsOnly = true, ordinal = 1) int mouseY,
            @Local(name = "j") int button,
            @Local(name = "list") List<String> list,
            @Cancellable CallbackInfo ci
    ){
        int xpCost = this.container.enchantLevels[button];
        if(experienceLevel < xpCost) return experienceLevel; //default handling if too few lvls

        ItemStack upgradeToken = ((IContainerEnchantmentMixin)this.container).soManyEnchantments$getUpgradeTokenCost(button);
        boolean isUpgrade = !upgradeToken.isEmpty();

        if(!isUpgrade) return experienceLevel; //will end up in enchanting code

        int upgradeTokenCost = upgradeToken.getCount();
        boolean tokenEnoughAndCorrect = ((IContainerEnchantmentMixin)this.container).soManyEnchantments$getIsValidAndEnoughToken(button);

        String s;

        if(upgradeTokenCost > 0) {
            s = upgradeTokenCost + " " + upgradeToken.getItem().getItemStackDisplayName(upgradeToken);

            TextFormatting textformatting = tokenEnoughAndCorrect ? TextFormatting.GRAY : TextFormatting.RED;
            list.add(textformatting + "" + s);
        }

        if(ModConfig.upgrade.bookshelvesNeeded > 0) {
            s = ModConfig.upgrade.bookshelvesNeeded + " " + I18n.format("enchantment.gui.bookshelfpower");

            TextFormatting textformatting = ((IContainerEnchantmentMixin)this.container).soManyEnchantments$getBookshelfPower() >= ModConfig.upgrade.bookshelvesNeeded ? TextFormatting.GRAY : TextFormatting.RED;
            list.add(textformatting + "" + s);
        }

        if(xpCost == 1) s = I18n.format("container.enchant.level.one");
        else s = I18n.format("container.enchant.level.many", xpCost);
        list.add(TextFormatting.GRAY + "" + s);

        this.drawHoveringText(list, mouseX, mouseY);

        ci.cancel(); //don't run enchanting code
        return experienceLevel;
    }

    /**-------------------------------- drawUpgradeSlots (from GuiContainer) --------------------------------**/

    @Override
    public void soManyEnchantments$drawUpgradeSlots() {
        int id = 0;
        for (Slot slot : this.container.inventorySlots)
            if(!slot.isEnabled())
                this.soManyEnchantments$drawUpgradeTokenSlot(slot, id++);
    }

    @Unique
    private void soManyEnchantments$drawUpgradeTokenSlot(Slot slot, int buttonId) {
        ItemStack stack = slot.getStack();

        this.zLevel = 100.0F;
        this.itemRender.zLevel = 100.0F;

        GlStateManager.enableDepth();
        this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, stack, slot.xPos, slot.yPos);

        boolean upgradeIsEnabled = ((IContainerEnchantmentMixin) this.container).soManyEnchantments$getIsValidAndEnoughToken(buttonId);
        soManyEnchantments$renderItemOverlayIntoGUI(this.fontRenderer, stack, slot.xPos, slot.yPos, upgradeIsEnabled);

        this.itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    @Unique
    public void soManyEnchantments$renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, boolean isEnough) {
        if (!stack.isEmpty() && stack.getCount() > 0) {
            String s = String.valueOf(stack.getCount());
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            fr.drawStringWithShadow(s, (float) (xPosition + 15 - fr.getStringWidth(s)), (float) (yPosition + 8), isEnough ? 0x80FF20 : 0xA84947);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableBlend();
        }
    }
}