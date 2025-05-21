package com.shultrea.rin.util.compat.jei;

import com.shultrea.rin.SoManyEnchantments;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class UpgradeRecipeCategory implements IRecipeCategory<UpgradeRecipeWrapper> {
    public static final String UID = SoManyEnchantments.MODID+".upgrading";

    private final IDrawable icon;
    private final IDrawable background;
    private final String localizedName;

    public UpgradeRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(new ResourceLocation("somanyenchantments:textures/gui/upgrading_jei.png"), 0, 0, 76, 50);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(Blocks.ENCHANTING_TABLE));
        this.localizedName = Translator.translateToLocal("gui.jei.category.upgrading");
    }

    @Override
    @Nonnull
    public String getUid() {
        return UID;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return localizedName;
    }

    @Override
    @Nonnull
    public String getModName() {
        return SoManyEnchantments.NAME;
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return background;
    }

    @Override
    @Nonnull
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull UpgradeRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 0, 18);
        guiItemStacks.init(1, true, 29, 0);
        guiItemStacks.init(2, false, 58, 18);
        guiItemStacks.set(ingredients);
    }
}
