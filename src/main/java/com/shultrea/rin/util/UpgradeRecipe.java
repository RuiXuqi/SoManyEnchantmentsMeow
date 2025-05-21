package com.shultrea.rin.util;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.ModConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UpgradeRecipe {
    public static final int DENY = -99;

    private final Enchantment enchIn, enchOut;
    private ItemStack tokenCost;
    private UpgradeRecipe cursingRecipe = null;
    private float curseChance = 0;
    private boolean isCursingRecipe = false;
    private Function<Integer, Integer> levelAlgo;

    public UpgradeRecipe(Enchantment enchIn, Enchantment enchOut) {
        this.enchIn = enchIn;
        this.enchOut = enchOut;

        //Default Settings:

        boolean isLevelUpgrade = (enchIn == enchOut);
        this.tokenCost = ConfigProvider.getDefaultUpgradeTokenItems(isLevelUpgrade);

        if(isLevelUpgrade)
            this.levelAlgo = lvlIn -> lvlIn < enchIn.getMaxLevel() ? lvlIn + 1 : DENY;
        else {
            if (ModConfig.upgrade.upgradedTierLevelMode == 0)
                //Reduce by x
                this.levelAlgo = lvlIn -> {
                    int reduction = ModConfig.upgrade.upgradedTierLevelReduction;
                    reduction = Math.min(reduction, this.enchIn.getMaxLevel() - this.enchOut.getMinLevel()); //If max possible lvl span is smaller than the reduction, only allow upgrade from old max to new min, for example for Fire Asp 2 -> Adv FA 1 or for Adv Mend
                    int newLvl = lvlIn - reduction;
                    return newLvl >= enchOut.getMinLevel() ? Math.min(newLvl, enchOut.getMaxLevel()) : DENY ;
                };
            else
                //From Max to Min
                this.levelAlgo = lvlIn -> lvlIn >= enchIn.getMaxLevel() ? enchOut.getMinLevel() : DENY;
        }
    }

    public UpgradeRecipe setCurse(Enchantment curse, float curseChance) {
        if(curse!=null) {
            this.cursingRecipe = new UpgradeRecipe(this.enchIn, curse).setIsCursingRecipe().setLevelAlgo(lvlIn -> 1).setTokenCost(this.tokenCost);
            this.curseChance = curseChance;
        }
        return this;
    }

    public UpgradeRecipe setIsCursingRecipe(){
        this.isCursingRecipe = true;
        return this;
    }

    public UpgradeRecipe setLevelAlgo(Function<Integer, Integer> algo){
        this.levelAlgo = algo;
        return this;
    }

    public UpgradeRecipe setTokenCost(ItemStack token) {
        this.tokenCost = token;
        if(this.cursingRecipe != null)
            this.cursingRecipe.setTokenCost(token);
        addUpgradeToken(token);
        return this;
    }

    public Enchantment getInput(){
        return enchIn;
    }

    public UpgradeRecipe getUsedRecipe(Random rand, boolean isPreview){
        if(this.cursingRecipe != null && !isPreview && rand.nextFloat() < this.curseChance)
            return cursingRecipe;
        return this;
    }

    public Enchantment getOutputEnchant() {
        return this.enchOut;
    }

    public ItemStack getTokenCost(){
        return this.tokenCost;
    }

    /**
     * this returns whether the recipe is used to curse, NOT whether a recipe HAS a curse. For that check cursingRecipe != null
     */
    public boolean getIsCursingRecipe(){
        return isCursingRecipe;
    }

    public UpgradeRecipe getCursingRecipe() {
        return cursingRecipe;
    }

    public float getCurseChance() {
        return curseChance;
    }

    public int getOutputLvl(int enchLvlIn){
        return this.levelAlgo.apply(enchLvlIn);
    }

    public Map<Enchantment, Integer> getOutputStackEnchants(ItemStack stackIn) {
        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stackIn);

        Map<Enchantment, Integer> newEnchantments = new LinkedHashMap<>();

        boolean alreadyUpgraded = false; //For rare situations where one enchant is on an item multiple times
        for(Map.Entry<Enchantment,Integer> entry : enchants.entrySet()) {
            if(!alreadyUpgraded && this.enchIn.equals(entry.getKey())) {
                if(!isCursingRecipe)
                    //Not a failure, allow upgrade
                    newEnchantments.put(this.enchOut, this.levelAlgo.apply(entry.getValue()));
                else if(!ModConfig.upgrade.upgradeFailRemovesOriginal)
                    //Failure but don't remove, just use old enchantment values
                    newEnchantments.put(entry.getKey(), entry.getValue());

                //else if isFail && removes: don't put old enchant into new list
                alreadyUpgraded = true;
            }
            else
                //Not the matching upgrade enchant
                newEnchantments.put(entry.getKey(), entry.getValue());
        }

        //Add the curse if there is one
        if(isCursingRecipe) {
            int minLvl = enchOut.getMinLevel();
            if(newEnchantments.containsKey(enchOut)) {
                int currentLvl  = newEnchantments.get(enchOut);
                int newLvl = MathHelper.clamp(currentLvl + 1, minLvl, enchOut.getMaxLevel());
                newEnchantments.put(enchOut, newLvl);
            } else
                newEnchantments.put(enchOut, minLvl);
        }

        //Set the new enchantments to the item
        return newEnchantments;
    }

    /**
     * Used to check whether an ItemStack has any enchants that can be upgraded by this recipe
     * also checks for canApply of the upgraded enchant
     * and if config for it is enabled it also checks for incompatibilities
     */
    public int canUpgrade(ItemStack itemStack) {
        Map<Enchantment, Integer> currentEnchants = EnchantmentHelper.getEnchantments(itemStack);

        enchantsOnItemLoop:
        for (Map.Entry<Enchantment, Integer> enchants : currentEnchants.entrySet()) {
            int upgradedLevel = this.canUpgrade(enchants.getKey(), enchants.getValue());
            if (upgradedLevel != DENY) {
                //Non-book can apply check
                if (itemStack.getItem() != Items.ENCHANTED_BOOK) {
                    if (!this.enchOut.canApply(itemStack))
                        continue;
                }
                //Book can apply check
                else if (!this.enchOut.isAllowedOnBooks())
                    continue;
                //Compatibility check with existing enchantments
                if (ModConfig.upgrade.onlyAllowCompatible)
                    for (Enchantment ench : currentEnchants.keySet())
                        if (ench != enchants.getKey() && !this.enchOut.isCompatibleWith(ench))
                            continue enchantsOnItemLoop;

                return upgradedLevel;
            }
        }
        return DENY;
    }

    /**
     * Used to check whether a specific enchantment with a specific level can be upgraded by this recipe
     */
    public int canUpgrade(Enchantment enchantment, int level) {
        return enchantment == this.enchIn ? this.levelAlgo.apply(level) : DENY;
    }

    public boolean upgradeTokenIsValid(ItemStack tokenStack){
        if (tokenStack.getItem() != this.tokenCost.getItem()) return false;
        if (this.tokenCost.getMetadata() != 32767 && tokenStack.getMetadata() != this.tokenCost.getMetadata()) return false;
        if (tokenStack.getCount() < this.tokenCost.getCount()) return false;
        return true;
    }

    public String toString(){
        try {
            String s = "UpgradeRecipe from " + this.getInput().getRegistryName().toString();
            s += " to " + this.getOutputEnchant().getRegistryName().toString();
            s += " using " + this.getTokenCost().getItem().getRegistryName().toString() + ":" + (this.getTokenCost().getMetadata() == 3767 ? "*" : this.getTokenCost().getMetadata()) + " count " + this.getTokenCost().getCount();
            if (this.cursingRecipe != null)
                s += " has curse " + this.cursingRecipe.getOutputEnchant().getRegistryName().toString() + " chance " + this.curseChance;
            return s;
        } catch (Exception e){
            return "Caught exception during toString representation of UpgradeRecipe";
        }
    }

    //-------------- STATIC AREA --------------

    public static final List<UpgradeRecipe> UPGRADE_RECIPES = new ArrayList<>();
    private static final List<ItemStack> UPGRADE_TOKENS = new ArrayList<>();

    public static void resetUpgradeRecipes(){
        UPGRADE_RECIPES.clear();
        UPGRADE_TOKENS.clear();
    }

    public static void initUpgradeRecipes(){
        for(Enchantment ench : ForgeRegistries.ENCHANTMENTS.getValuesCollection().stream().sorted(Comparator.comparingInt(Enchantment::getEnchantmentID)).collect(Collectors.toList())){
            Enchantment curse = ConfigProvider.getFailureEnchantFor(ench);
            if(ModConfig.upgrade.allowLevelUpgrades && !ench.isCurse() && ench.getMaxLevel() > ench.getMinLevel()){
                UpgradeRecipe recipe = new UpgradeRecipe(ench, ench).setCurse(curse, ModConfig.upgrade.upgradeFailChanceLevel);
                UPGRADE_RECIPES.add(recipe);
                addUpgradeToken(recipe.getTokenCost());
            }
            if(ModConfig.upgrade.allowTierUpgrades) {
                Enchantment upgradedEnchant = ConfigProvider.getUpgradedEnchantFor(ench);
                if(upgradedEnchant != null) {
                    UpgradeRecipe recipe = new UpgradeRecipe(ench, upgradedEnchant).setCurse(curse, ModConfig.upgrade.upgradeFailChanceTier);
                    UPGRADE_RECIPES.add(recipe);
                    addUpgradeToken(recipe.getTokenCost());
                }
            }
        }
    }

    public static void addUpgradeToken(ItemStack token){
        for(ItemStack stack : UPGRADE_TOKENS)
            if(stack.getItem().equals(token.getItem()) && stack.getMetadata() == token.getMetadata() && stack.getCount() == token.getCount())
                return;
        UPGRADE_TOKENS.add(token);
    }

    //This is only for allowing items into the slot, so the count doesn't matter yet
    public static boolean stackIsValidUpgradeToken(ItemStack stack) {
        for(ItemStack tokenStack : UPGRADE_TOKENS){
            if(tokenStack.getItem() == stack.getItem())
                if(tokenStack.getMetadata() == 32767 || tokenStack.getMetadata() == stack.getMetadata())
                    return true;
        }
        return false;
    }

    public static void cleanupUpgradeTokens() {
        //CT can change upgrade tokens which in theory can make some previously valid tokens now be unused.
        //Thus clean the token list to only allow actually used tokens in upgrade token/lapis slot
        List<ItemStack> toRemove = new ArrayList<>();
        tokens: for(ItemStack token : UPGRADE_TOKENS){
            //If any recipe uses the token, keep it
            for(UpgradeRecipe recipe : UPGRADE_RECIPES)
                if(recipe.upgradeTokenIsValid(token))
                    continue tokens;
            toRemove.add(token);
        }
        UPGRADE_TOKENS.removeAll(toRemove);
    }
}
