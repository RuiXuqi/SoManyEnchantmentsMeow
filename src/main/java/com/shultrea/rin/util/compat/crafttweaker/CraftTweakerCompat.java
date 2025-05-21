package com.shultrea.rin.util.compat.crafttweaker;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.util.UpgradeRecipe;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.CraftTweaker;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@ZenRegister
@ZenClass(SoManyEnchantments.MODID+".UpgradeEnchantments")
public class CraftTweakerCompat {

    @ZenMethod
    public static void removeAllRecipes() {
        recipesToRemove.add(new ActionRemoveAllUpgradeRecipes());
    }

    @ZenMethod
    public static void removeRecipe(IEnchantmentDefinition in, IEnchantmentDefinition out) {
        recipesToRemove.add(new ActionRemoveUpgradeRecipe((Enchantment) in.getInternal(), (Enchantment) out.getInternal()));
    }

    @ZenMethod
    public static void setUpgradeTokenForRecipe(IEnchantmentDefinition in, IEnchantmentDefinition out, IItemStack upgradeToken) {
        UpgradeRecipe.UPGRADE_RECIPES.stream()
                .filter(r -> r.getInput().equals(in.getInternal()) && r.getOutputEnchant().equals(out.getInternal()))
                .forEach(r -> r.setTokenCost((ItemStack) upgradeToken.getInternal()));
    }

    @ZenMethod
    public static void setCurseForRecipe(IEnchantmentDefinition in, IEnchantmentDefinition out, IEnchantmentDefinition curse, float cursingChance) {
        UpgradeRecipe.UPGRADE_RECIPES.stream()
                .filter(r -> r.getInput().equals(in.getInternal()) && r.getOutputEnchant().equals(out.getInternal()))
                .forEach(r -> r.setCurse((Enchantment) curse.getInternal(), cursingChance));
    }

    @ZenMethod
    public static CraftTweakerUpgradeRecipe addRecipe(IEnchantmentDefinition in, IEnchantmentDefinition out){
        CraftTweakerUpgradeRecipe newRecipe = new CraftTweakerUpgradeRecipe(new UpgradeRecipe((Enchantment) in.getInternal(), (Enchantment) out.getInternal()));
        recipesToAdd.add(new ActionAddUpgradeRecipe(newRecipe));
        return newRecipe;
    }

    //------------------------ STATIC AREA ------------------------

    private static final List<IAction> recipesToAdd = new ArrayList<>();
    private static final List<IAction> recipesToRemove = new ArrayList<>();

    public static void applyActions() {
        CraftTweaker.INSTANCE.applyActions(recipesToRemove,"","");
        CraftTweaker.INSTANCE.applyActions(recipesToAdd,"","");

        UpgradeRecipe.cleanupUpgradeTokens();
    }

    public static class ActionRemoveUpgradeRecipe implements IAction {

        private final Enchantment in, out;

        public ActionRemoveUpgradeRecipe(Enchantment in, Enchantment out){
            this.in = in;
            this.out = out;
        }

        @Override
        public void apply() {
            UpgradeRecipe.UPGRADE_RECIPES.removeIf(r -> r.getInput().equals(this.in) && r.getOutputEnchant().equals(this.out));
        }

        @Override
        public String describe() {
            return "";
        }
    }

    public static class ActionRemoveAllUpgradeRecipes implements IAction {

        @Override
        public void apply() {
            UpgradeRecipe.UPGRADE_RECIPES.clear();
        }

        @Override
        public String describe() {
            return "";
        }
    }

    public static class ActionAddUpgradeRecipe implements IAction {

        CraftTweakerUpgradeRecipe recipe;

        public ActionAddUpgradeRecipe(CraftTweakerUpgradeRecipe recipe){
            this.recipe = recipe;
        }

        @Override
        public void apply() {
            UpgradeRecipe.UPGRADE_RECIPES.add(this.recipe.getInternal());
        }

        @Override
        public String describe() {
            return "";
        }
    }
}