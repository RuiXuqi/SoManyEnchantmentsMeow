package com.shultrea.rin.mixin.vanilla.upgrading;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.config.UpgradeConfig;
import com.shultrea.rin.util.IContainerEnchantmentMixin;
import com.shultrea.rin.util.RainbowUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.util.glu.Project;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.List;

@Mixin(GuiEnchantment.class)
public abstract class GuiEnchantmentMixin extends GuiContainer {
    
    @Shadow @Final private static ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE;
    @Shadow @Final private static ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE;
    @Shadow public float oOpen;
    @Shadow public float open;
    @Shadow public float oFlip;
    @Shadow public float flip;
    @Shadow @Final private static ModelBook MODEL_BOOK;
    @Shadow @Final private ContainerEnchantment container;
    
    @Unique
    private static final RainbowUtil soManyEnchantments$rainbowHandler0 = new RainbowUtil(0);
    @Unique
    private static final RainbowUtil soManyEnchantments$rainbowHandler1 = new RainbowUtil(1);
    @Unique
    private static final RainbowUtil soManyEnchantments$rainbowHandler2 = new RainbowUtil(2);
    @Unique
    private static final ResourceLocation soManyEnchantments$upgradeTokenTexture = new ResourceLocation("textures/gui/container/upgrade_token.png");
    
    public GuiEnchantmentMixin(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }
    
    /**
     * @author fonnymunkey/nischhelm
     * @reason enchantment table upgrading mechanics
     *
     * Normally overwrite is gross, but with how invasive these mechanics are, if anything else is modifying the enchantment table, its likely going to break anyways
     */
    @Overwrite
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        GlStateManager.viewport((scaledresolution.getScaledWidth() - 320) / 2 * scaledresolution.getScaleFactor(), (scaledresolution.getScaledHeight() - 240) / 2 * scaledresolution.getScaleFactor(), 320 * scaledresolution.getScaleFactor(), 240 * scaledresolution.getScaleFactor());
        GlStateManager.translate(-0.34F, 0.23F, 0.0F);
        Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(0.0F, 3.3F, -16.0F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.scale(5.0F, 5.0F, 5.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
        GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
        float f2 = this.oOpen + (this.open - this.oOpen) * partialTicks;
        GlStateManager.translate((1.0F - f2) * 0.2F, (1.0F - f2) * 0.1F, (1.0F - f2) * 0.25F);
        GlStateManager.rotate(-(1.0F - f2) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        float f3 = this.oFlip + (this.flip - this.oFlip) * partialTicks + 0.25F;
        float f4 = this.oFlip + (this.flip - this.oFlip) * partialTicks + 0.75F;
        f3 = (f3 - (float)MathHelper.fastFloor(f3)) * 1.6F - 0.3F;
        f4 = (f4 - (float)MathHelper.fastFloor(f4)) * 1.6F - 0.3F;
        
        if(f3 < 0.0F) {
            f3 = 0.0F;
        }
        
        if(f4 < 0.0F) {
            f4 = 0.0F;
        }
        
        if(f3 > 1.0F) {
            f3 = 1.0F;
        }
        
        if(f4 > 1.0F) {
            f4 = 1.0F;
        }
        
        GlStateManager.enableRescaleNormal();
        MODEL_BOOK.render(null, 0.0F, f3, f4, f2, 0.0F, 0.0625F);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.matrixMode(5889);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        EnchantmentNameParts.getInstance().reseedRandomGenerator(this.container.xpSeed);
        int tokenAmount = this.container.getLapisAmount();
        boolean tokenIsLapis = ((IContainerEnchantmentMixin) this.container).soManyEnchantments$getTokenIsLapis();

        for(int button = 0; button < 3; ++button) {
            int i1 = i + 60;
            int j1 = i1 + 20;
            this.zLevel = 0.0F;
            this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
            int xpCost = this.container.enchantLevels[button];
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            
            if(xpCost == 0) {
                this.drawTexturedModalRect(i1, j + 14 + 19 * button, 0, 185, 108, 19);
            }
            else {
                String s = "" + xpCost;
                int l1 = 86 - this.fontRenderer.getStringWidth(s);
                String s1 = EnchantmentNameParts.getInstance().generateNewRandomName(this.fontRenderer, l1);
                FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;
                
                int runeColor;
                int xpColor;
                
                int upgradeTokenCost = ((IContainerEnchantmentMixin)this.container).soManyEnchantments$getUpgradeTokenCost(button);
                boolean isUpgrade = upgradeTokenCost >= 0;

                if(!isUpgrade) {
                    if(((!tokenIsLapis || tokenAmount < button + 1 || this.mc.player.experienceLevel < xpCost) && !this.mc.player.capabilities.isCreativeMode) || this.container.enchantClue[button] == -1) {
                        this.drawTexturedModalRect(i1, j + 14 + 19 * button, 0, 185, 108, 19);
                        this.drawTexturedModalRect(i1 + 1, j + 15 + 19 * button, 16 * button, 239, 16, 16);
                        runeColor = 6839882;//Unhighlighted rune text
                        xpColor = 4226832;//XP cost invalid
                        fontrenderer.drawSplitString(s1, j1, j + 16 + 19 * button, l1, (runeColor & 16711422) >> 1);
                    }
                    else {
                        int j2 = mouseX - (i + 60);
                        int k2 = mouseY - (j + 14 + 19 * button);
                        
                        if(j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19) {
                            this.drawTexturedModalRect(i1, j + 14 + 19 * button, 0, 204, 108, 19);
                            runeColor = 16777088;//Highlighted rune text
                        }
                        else {
                            this.drawTexturedModalRect(i1, j + 14 + 19 * button, 0, 166, 108, 19);
                            runeColor = 6839882;//Unhighlighted rune text
                        }
                        xpColor = 8453920;//XP cost valid
                        
                        this.drawTexturedModalRect(i1 + 1, j + 15 + 19 * button, 16 * button, 223, 16, 16);
                        fontrenderer.drawSplitString(s1, j1, j + 16 + 19 * button, l1, runeColor);
                    }
                }
                else {
                    RainbowUtil rainbow;
                    switch(button) {
                        case 0:
                            rainbow = soManyEnchantments$rainbowHandler0;
                            break;
                        case 1:
                            rainbow = soManyEnchantments$rainbowHandler1;
                            break;
                        case 2:
                            rainbow = soManyEnchantments$rainbowHandler2;
                            break;
                        default:
                            rainbow = soManyEnchantments$rainbowHandler0;
                            break;
                    }
                    
                    int tokenCostColor;
                    String tokenCostString = "" + upgradeTokenCost;
                    
                    if(!this.mc.player.capabilities.isCreativeMode &&
                            (tokenIsLapis ||
                            tokenAmount < upgradeTokenCost ||
                            this.mc.player.experienceLevel < xpCost ||
                            ((IContainerEnchantmentMixin)this.container).soManyEnchantments$getBookshelfPower() < ModConfig.upgrade.bookshelvesNeeded)) {
                        this.drawTexturedModalRect(i1, j + 14 + 19 * button, 0, 185, 108, 19);
                        
                        runeColor = rainbow.getDesaturatedDecimalColor(0.5F);//Unhighlighted rune text
                        rainbow.tick();
                        
                        xpColor = 4226832;//XP cost invalid
                        tokenCostColor = 11028807;//Token cost invalid
                        
                        this.mc.getTextureManager().bindTexture(soManyEnchantments$upgradeTokenTexture);
                        Gui.drawModalRectWithCustomSizedTexture(i1 + 1, j + 15 + 19 * button, 0, 0, 16, 16, 16, 16);
                        
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
                        tokenCostColor = 8453920;//Token cost valid
                        
                        this.mc.getTextureManager().bindTexture(soManyEnchantments$upgradeTokenTexture);
                        Gui.drawModalRectWithCustomSizedTexture(i1 + 1, j + 15 + 19 * button, 0, 0, 16, 16, 16, 16);
                        
                        fontrenderer.drawSplitString(s1, j1, j + 16 + 19 * button, l1, runeColor);
                    }
                    
                    fontrenderer = this.mc.fontRenderer;
                    fontrenderer.drawStringWithShadow(tokenCostString, (float)(i1 + 16 - fontrenderer.getStringWidth(tokenCostString)), (float)(j + 16 + 19 * button + 7), tokenCostColor);
                }
                
                fontrenderer = this.mc.fontRenderer;
                fontrenderer.drawStringWithShadow(s, (float)(j1 + 86 - fontrenderer.getStringWidth(s)), (float)(j + 16 + 19 * button + 7), xpColor);
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
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        partialTicks = this.mc.getTickLength();
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        boolean isCreative = this.mc.player.capabilities.isCreativeMode;
        int tokenAmount = this.container.getLapisAmount();
        boolean tokenIsLapis = ((IContainerEnchantmentMixin) this.container).soManyEnchantments$getTokenIsLapis();

        for(int button = 0; button < 3; ++button) {
            int xpCost = this.container.enchantLevels[button];
            Enchantment enchantmentClue = Enchantment.getEnchantmentByID(this.container.enchantClue[button]);
            int worldClue = this.container.worldClue[button];
            
            if(this.isPointInRegion(60, 14 + 19 * button, 108, 17, mouseX, mouseY) && xpCost > 0) {
                List<String> list = new ArrayList<>();
                list.add("" + TextFormatting.WHITE + TextFormatting.ITALIC + I18n.format("container.enchant.clue", enchantmentClue == null ? "" : enchantmentClue.getTranslatedName(worldClue)));
                
                int upgradeTokenCost = ((IContainerEnchantmentMixin)this.container).soManyEnchantments$getUpgradeTokenCost(button);
                boolean isUpgrade = upgradeTokenCost >= 0;
                
                if(enchantmentClue == null && !isUpgrade) java.util.Collections.addAll(list, "", TextFormatting.RED + I18n.format("forge.container.enchant.limitedEnchantability"));
                else if(!isCreative) {
                    list.add("");
                    
                    if(this.mc.player.experienceLevel < xpCost) {
                        list.add(TextFormatting.RED + I18n.format("container.enchant.level.requirement", xpCost));
                    }
                    else if(!isUpgrade) {
                        int lapisCost = button + 1;

                        String s;
                        if(lapisCost == 1) {
                            s = I18n.format("container.enchant.lapis.one");
                        }
                        else {
                            s = I18n.format("container.enchant.lapis.many", lapisCost);
                        }
                        
                        TextFormatting textformatting = tokenAmount >= lapisCost && tokenIsLapis ? TextFormatting.GRAY : TextFormatting.RED;
                        list.add(textformatting + "" + s);
                        
                        if(lapisCost == 1) {
                            s = I18n.format("container.enchant.level.one");
                        }
                        else {
                            s = I18n.format("container.enchant.level.many", lapisCost);
                        }
                        
                        list.add(TextFormatting.GRAY + "" + s);
                    }
                    else {
                        String s;
                        
                        if(upgradeTokenCost > 0) {
                            s = upgradeTokenCost + " " + I18n.format(ConfigProvider.getUpgradeTokenItem().getTranslationKey() + ".name");
                            
                            TextFormatting textformatting = tokenAmount >= upgradeTokenCost && !tokenIsLapis ? TextFormatting.GRAY : TextFormatting.RED;
                            list.add(textformatting + "" + s);
                        }
                        
                        if(ModConfig.upgrade.bookshelvesNeeded > 0) {
                            s = ModConfig.upgrade.bookshelvesNeeded + " " + I18n.format("enchantment.gui.bookshelfpower");
                            
                            TextFormatting textformatting = ((IContainerEnchantmentMixin)this.container).soManyEnchantments$getBookshelfPower() >= ModConfig.upgrade.bookshelvesNeeded ? TextFormatting.GRAY : TextFormatting.RED;
                            list.add(textformatting + "" + s);
                        }
                        
                        if(xpCost == 1) {
                            s = I18n.format("container.enchant.level.one");
                        }
                        else {
                            s = I18n.format("container.enchant.level.many", xpCost);
                        }
                        
                        list.add(TextFormatting.GRAY + "" + s);
                    }
                }
                
                this.drawHoveringText(list, mouseX, mouseY);
                break;
            }
        }
    }
}