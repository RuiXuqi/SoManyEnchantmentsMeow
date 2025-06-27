package com.shultrea.rin.util.compat.crafttweaker;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.config.ModConfig;
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
import java.util.stream.Collectors;

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
        recipesToRemove.add(new ActionRemoveUpgradeRecipe(reloadEnchantment(in.getInternal()), reloadEnchantment(out.getInternal())));
    }

    @ZenMethod
    public static void setUpgradeTokenForRecipe(IEnchantmentDefinition in, IEnchantmentDefinition out, IItemStack upgradeToken) {
        recipesToChange.add(new ActionChangeUpgradeToken(in, out, upgradeToken));
    }

    @ZenMethod
    public static void setCurseForRecipe(IEnchantmentDefinition in, IEnchantmentDefinition out, IEnchantmentDefinition curse, float cursingChance) {
        recipesToChange.add(new ActionChangeCurse(in, out, curse, cursingChance));
    }

    @ZenMethod
    public static void setCurseForRecipe(IEnchantmentDefinition in, IEnchantmentDefinition out, IEnchantmentDefinition curse) {
        recipesToChange.add(new ActionChangeCurse(in, out, curse));
    }

    @ZenMethod
    public static CraftTweakerUpgradeRecipe addRecipe(IEnchantmentDefinition in, IEnchantmentDefinition out){
        CraftTweakerUpgradeRecipe newRecipe = new CraftTweakerUpgradeRecipe(new UpgradeRecipe(reloadEnchantment(in.getInternal()), reloadEnchantment(out.getInternal())));
        recipesToAdd.add(new ActionAddUpgradeRecipe(newRecipe));
        return newRecipe;
    }

    //------------------------ STATIC AREA ------------------------

    private static final List<IAction> recipesToAdd = new ArrayList<>();
    private static final List<IAction> recipesToRemove = new ArrayList<>();
    private static final List<IAction> recipesToChange = new ArrayList<>();

    public static void applyActions() {
        CraftTweaker.INSTANCE.applyActions(recipesToRemove,"","");
        CraftTweaker.INSTANCE.applyActions(recipesToChange,"","");
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

    public static class ActionChangeUpgradeToken implements IAction{

        private final ItemStack upgradeToken;
        private final Enchantment enchIn, enchOut;

        public ActionChangeUpgradeToken(IEnchantmentDefinition enchIn, IEnchantmentDefinition enchOut, IItemStack upgradeToken){
            this.upgradeToken = (ItemStack) upgradeToken.getInternal();
            this.enchIn = reloadEnchantment(enchIn.getInternal());
            this.enchOut = reloadEnchantment(enchOut.getInternal());
        }

        @Override
        public void apply() {
            UpgradeRecipe.UPGRADE_RECIPES.stream()
                    .filter(r -> r.getInput().equals(enchIn) && r.getOutputEnchant().equals(enchOut))
                    .forEach(r -> r.setTokenCost(upgradeToken));
        }

        @Override
        public String describe() {
            return "";
        }
    }

    public static class ActionChangeCurse implements IAction{

        private final float cursingChance;
        private final Enchantment enchIn, enchOut, enchCurse;

        public ActionChangeCurse(IEnchantmentDefinition enchIn, IEnchantmentDefinition enchOut, IEnchantmentDefinition curse, float cursingChance){
            this.enchIn = reloadEnchantment(enchIn.getInternal());
            this.enchOut = reloadEnchantment(enchOut.getInternal());
            this.enchCurse = reloadEnchantment(curse.getInternal());
            this.cursingChance = cursingChance;
        }

        public ActionChangeCurse(IEnchantmentDefinition enchIn, IEnchantmentDefinition enchOut, IEnchantmentDefinition curse){
            this(enchIn, enchOut, curse,
                    enchIn.getInternal() == enchOut.getInternal() ? ModConfig.upgrade.upgradeFailChanceLevel : ModConfig.upgrade.upgradeFailChanceTier
            );
        }

        @Override
        public void apply() {
            UpgradeRecipe.UPGRADE_RECIPES.stream()
                    .filter(r -> r.getInput().equals(enchIn) && r.getOutputEnchant().equals(enchOut))
                    .forEach(r -> r.setCurse(enchCurse, cursingChance));
        }

        @Override
        public String describe() {
            return "";
        }
    }

    public static Enchantment reloadEnchantment(Object original){
        if(!(original instanceof Enchantment)) return null;
        Enchantment enchantment = (Enchantment) original;
        if(enchantment.getRegistryName() == null) return enchantment;
        //if(!enchantment.getRegistryName().getNamespace().equals("minecraft")) return enchantment; //from my testing it was only vanilla enchants being weird but being extra safe here doesn't cost a lot of extra cpu cycles
        return Enchantment.getEnchantmentByLocation(enchantment.getRegistryName().toString());
    }
}