package com.shultrea.rin.util.compat.crafttweaker;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.UpgradeRecipe;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.item.IItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenRegister
@ZenClass(SoManyEnchantments.MODID+".UpgradeRecipe")
public class CraftTweakerUpgradeRecipe {
    private final UpgradeRecipe internal;

    public CraftTweakerUpgradeRecipe(UpgradeRecipe recipe){
        this.internal = recipe;
    }

    public UpgradeRecipe getInternal(){
        return internal;
    }

    @ZenMethod
    public CraftTweakerUpgradeRecipe setCurse(IEnchantmentDefinition curse, float chance){
        this.internal.setCurse((Enchantment) curse.getInternal(), chance);
        return this;
    }

    @ZenMethod
    public CraftTweakerUpgradeRecipe setUpgradeToken(IItemStack stack){
        this.internal.setTokenCost((ItemStack) stack.getInternal());
        return this;
    }

    @ZenMethod
    public CraftTweakerUpgradeRecipe setLevelReduction(int reduction){
        if(ModConfig.upgrade.upgradedTierLevelMode != 0) {
            SoManyEnchantments.LOGGER.warn("SME CT Compat: Can't set level reduction for upgrade recipe, only allowed if Tier Upgrade Mode is 0.");
            return this;
        }
        if(this.internal.getOutputEnchant() == this.internal.getInput()){
            SoManyEnchantments.LOGGER.warn("SME CT Compat: Can't set level reduction for level upgrade recipe, only allowed for tier upgrade recipes.");
            return this;
        }
        this.internal.setLevelAlgo(lvlIn -> {
                    int actualReduction = Math.min(reduction, this.internal.getInput().getMaxLevel() - this.internal.getOutputEnchant().getMinLevel());
                    int newLvl = lvlIn - actualReduction;
                    return newLvl >= this.internal.getOutputEnchant().getMinLevel() ? Math.min(newLvl, this.internal.getOutputEnchant().getMaxLevel()) : UpgradeRecipe.DENY ;
                });
        return this;
    }
}
