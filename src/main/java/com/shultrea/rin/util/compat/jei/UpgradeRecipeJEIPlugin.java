package com.shultrea.rin.util.compat.jei;

import com.shultrea.rin.util.UpgradeRecipe;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
@JEIPlugin
public class UpgradeRecipeJEIPlugin implements IModPlugin {
    @Override
    public void register(@Nonnull IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        //Adding the recipes
        UpgradeRecipe.UPGRADE_RECIPES.forEach(r -> registry.addRecipes(UpgradeRecipeWrapper.getWrappers(r), UpgradeRecipeCategory.UID));

        //See all Upgrade Recipes when right-clicking the Enchanting table item in JEI
        registry.addRecipeCatalyst(new ItemStack(Blocks.ENCHANTING_TABLE), UpgradeRecipeCategory.UID);
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new UpgradeRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }
}
