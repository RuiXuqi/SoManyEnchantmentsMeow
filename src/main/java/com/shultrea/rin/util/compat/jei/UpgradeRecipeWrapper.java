package com.shultrea.rin.util.compat.jei;

import com.shultrea.rin.util.UpgradeRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

public class UpgradeRecipeWrapper implements IRecipeWrapper {

    private final ItemStack inputBook;
    private final ItemStack upgradeToken;

    private ItemStack outputBook = null;
    private ItemStack curseBook = null;

    private static final ItemStack bookTemplate = new ItemStack(Items.ENCHANTED_BOOK);

    public UpgradeRecipeWrapper(UpgradeRecipe recipe, int enchLvlIn){
        this.upgradeToken = recipe.getTokenCost();

        //Input book
        this.inputBook = bookTemplate.copy();
        ItemEnchantedBook.addEnchantment(this.inputBook, new EnchantmentData(recipe.getInput(), enchLvlIn));

        //Apply recipe
        Map<Enchantment, Integer> enchantsOut = recipe.getOutputStackEnchants(this.inputBook);
        Enchantment enchOut = recipe.getOutputEnchant();

        //Technically not needed sanity checks
        if(enchantsOut.size() != 1) return;
        if(!enchantsOut.containsKey(enchOut)) return;
        int enchLvlOut = enchantsOut.get(enchOut);
        if(enchLvlOut == UpgradeRecipe.DENY) return;

        //Output book
        this.outputBook = bookTemplate.copy();
        ItemEnchantedBook.addEnchantment(this.outputBook, new EnchantmentData(enchOut, enchLvlOut));

        //Optional curse book
        UpgradeRecipe cursingRecipe = recipe.getCursingRecipe();
        if(cursingRecipe != null){
            this.curseBook = bookTemplate.copy();
            int outputLvl = cursingRecipe.getOutputLvl(enchLvlIn);
            ItemEnchantedBook.addEnchantment(curseBook, new EnchantmentData(cursingRecipe.getOutputEnchant(), outputLvl));
        }
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, Arrays.asList(inputBook, upgradeToken));
        if(curseBook == null)
            ingredients.setOutput(VanillaTypes.ITEM, outputBook);
        else
            ingredients.setOutputLists(VanillaTypes.ITEM, Collections.singletonList(Arrays.asList(outputBook, curseBook)));
    }

    public static List<UpgradeRecipeWrapper> getWrappers(UpgradeRecipe recipe) {
        List<UpgradeRecipeWrapper> recipeWrappers = new ArrayList<>();

        Enchantment enchIn = recipe.getInput();
        for(int enchLvlIn = enchIn.getMinLevel(); enchLvlIn <= enchIn.getMaxLevel(); enchLvlIn++){
            //Not all input lvls are allowed
            if(recipe.canUpgrade(enchIn, enchLvlIn) == UpgradeRecipe.DENY) continue;

            UpgradeRecipeWrapper wrapper = new UpgradeRecipeWrapper(recipe, enchLvlIn);
            //Some wrapper constructors might fail anyhow, even though they shouldn't
            if(wrapper.outputBook != null)
                recipeWrappers.add(wrapper);
        }

        return recipeWrappers;
    }
}
