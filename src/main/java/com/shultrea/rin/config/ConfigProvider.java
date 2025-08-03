package com.shultrea.rin.config;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.Types;
import com.shultrea.rin.util.enchantmenttypes.EnumEnchantmentTypeMatcher;
import com.shultrea.rin.util.enchantmenttypes.CustomTypeMatcher;
import com.shultrea.rin.util.enchantmenttypes.ITypeMatcher;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.*;

public class ConfigProvider {

    // -------------------- Incompatible --------------------

    public static Set<Enchantment> getIncompatibleEnchantmentsFromConfig(Enchantment thisEnch) {
        Set<Enchantment> incompatEnchs = new HashSet<>();

        ResourceLocation regName = thisEnch.getRegistryName();
        if(regName == null) return incompatEnchs;

        for(String configLine : ModConfig.incompatible.incompatibleGroups) {
            if(configLine.contains(regName.getPath())) {
                //Assumes that config lines are enchantments separated by comma
                String[] enchsInList = configLine.split(",");
                for(String lineEntry : enchsInList) {
                    lineEntry = lineEntry.trim();
                    if(lineEntry.isEmpty()) continue;
                    //assumes that the config uses modid:enchantment if its not an SME enchant
                    if(!lineEntry.contains(":")) lineEntry = SoManyEnchantments.MODID + ":" + lineEntry;
                    Enchantment incompatEnch = Enchantment.getEnchantmentByLocation(lineEntry);
                    if(incompatEnch == null) SoManyEnchantments.LOGGER.warn("SME: could not find incompatible enchantment {}", lineEntry);
                    else incompatEnchs.add(incompatEnch);
                }
            }
        }
        // remove the calling enchant
        // every enchantment is incompatible with itself, this is handled by Enchantment class directly though
        // and thus doesn't need to be in this list
        incompatEnchs.remove(thisEnch);

        return incompatEnchs;
    }

    // -------------------- Can Apply --------------------

    private static final HashMap<String, ITypeMatcher> typeMatchers = new HashMap<>();

    public static void resetCanApply(){
        typeMatchers.clear();
        initCanApply();
    }

    public static void initCanApply() {
        //These do have an associated fake enchant
        typeMatchers.put("ALL_TYPES", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.ALL));
        typeMatchers.put("ARMOR", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.ARMOR));
        typeMatchers.put("ARMOR_HEAD", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.ARMOR_HEAD));
        typeMatchers.put("ARMOR_CHEST", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.ARMOR_CHEST));
        typeMatchers.put("ARMOR_LEGS", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.ARMOR_LEGS));
        typeMatchers.put("ARMOR_FEET", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.ARMOR_FEET));
        typeMatchers.put("SWORD", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.WEAPON));
        typeMatchers.put("TOOL", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.DIGGER));
        typeMatchers.put("FISHING_ROD", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.FISHING_ROD));
        typeMatchers.put("BREAKABLE", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.BREAKABLE));
        typeMatchers.put("BOW", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.BOW));
        typeMatchers.put("WEARABLE", new EnumEnchantmentTypeMatcher(EnumEnchantmentType.WEARABLE));

        //These don't have an associated fake enchant
        typeMatchers.put("ALL_ITEMS", new EnumEnchantmentTypeMatcher(Types.ALL));
        typeMatchers.put("AXE", new EnumEnchantmentTypeMatcher(Types.AXE));
        typeMatchers.put("PICKAXE", new EnumEnchantmentTypeMatcher(Types.PICKAXE));
        typeMatchers.put("HOE", new EnumEnchantmentTypeMatcher(Types.HOE));
        typeMatchers.put("SHOVEL", new EnumEnchantmentTypeMatcher(Types.SPADE));
        typeMatchers.put("SHIELD", new EnumEnchantmentTypeMatcher(Types.SHIELD));
        typeMatchers.put("NONE", new EnumEnchantmentTypeMatcher(Types.NONE));

        for (String s : ModConfig.canApply.customTypes) {
            CustomTypeMatcher c = new CustomTypeMatcher(s);
            if (c.isValid()) typeMatchers.put(c.getName(), c);
        }
    }

    public static boolean canItemApply(EnchantmentBase enchantment, String[] enchantConfig, ItemStack stack){
        Item item = stack.getItem();
        boolean isValid = false;
        boolean invertedMatches = false;
        String itemName = null;

        for(String s: enchantConfig){
            //Configs can list types starting with ! to disable those
            boolean inverted = false;
            if(s.startsWith("!")){
                inverted = true;
                s = s.substring(1);
            }

            ITypeMatcher typeMatcher = typeMatchers.getOrDefault(s, null);
            if(typeMatcher == null) {
                SoManyEnchantments.LOGGER.info("SME: Could not find given item type {}", s);
                continue;
            }

            //First time check of a custom type: get item name
            if(typeMatcher instanceof CustomTypeMatcher && itemName == null) {
                ResourceLocation loc = item.getRegistryName();
                if (loc != null) itemName = loc.toString();
                else itemName = ""; //edge case shouldn't match anything
            }

            boolean matches = typeMatcher.matches(enchantment, stack, item, itemName);

            if(!inverted) isValid = isValid || matches;
            else invertedMatches = invertedMatches || matches;
        }
        return isValid && !invertedMatches;
    }

    // -------------------- Blacklists --------------------

    private static List<Enchantment> randomLevelEnchantsBlacklist = null;
    private static List<Enchantment> randomEnchantsBlacklist = null;
    private static List<Enchantment> librarianEnchantsBlacklist = null;
    private static List<Enchantment> enchantTableEnchantsBlacklist = null;
    private static List<String> registryEnchantsBlacklist = null;

    public static void resetBlacklists(){
        ConfigProvider.randomLevelEnchantsBlacklist = null;
        ConfigProvider.randomEnchantsBlacklist = null;
        ConfigProvider.librarianEnchantsBlacklist = null;
        ConfigProvider.enchantTableEnchantsBlacklist = null;
    }

    public static List<Enchantment> getRandomLevelEnchantsBlacklist() {
        if(randomLevelEnchantsBlacklist == null) {
            randomLevelEnchantsBlacklist = populateEnchantmentList(ModConfig.miscellaneous.blacklistedRandomLevelEnchants);
        }
        return randomLevelEnchantsBlacklist;
    }

    public static List<Enchantment> getRandomEnchantsBlacklist() {
        if(randomEnchantsBlacklist == null) {
            randomEnchantsBlacklist = populateEnchantmentList(ModConfig.miscellaneous.blacklistedRandomEnchants);
        }
        return randomEnchantsBlacklist;
    }

    public static List<Enchantment> getLibrarianEnchantsBlacklist() {
        if(librarianEnchantsBlacklist == null) {
            librarianEnchantsBlacklist = populateEnchantmentList(ModConfig.miscellaneous.blacklistedLibrarianEnchants);
        }
        return librarianEnchantsBlacklist;
    }

    public static List<Enchantment> getEnchantTableEnchantsBlacklist() {
        if(enchantTableEnchantsBlacklist == null) {
            enchantTableEnchantsBlacklist = populateEnchantmentList(ModConfig.miscellaneous.blacklistedEnchTableEnchants);
        }
        return enchantTableEnchantsBlacklist;
    }

    private static List<Enchantment> populateEnchantmentList(String[] names) {
        List<Enchantment> list = new ArrayList<>();
        for(String name : names) {
            name = name.trim();
            if(name.isEmpty()) continue;
            Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(name));
            if(enchant == null) {
                SoManyEnchantments.LOGGER.log(Level.WARN, "Invalid enchantment " + name + " in blacklist");
                continue;
            }
            list.add(enchant);
        }
        return list;
    }

    public static List<String> getRegistryEnchantsBlacklist(){
        if(registryEnchantsBlacklist == null){
            registryEnchantsBlacklist = Arrays.asList(ModConfig.miscellaneous.blacklistedRegistryEnchants);
        }
        return registryEnchantsBlacklist;
    }

    // -------------------- Upgrade --------------------

    private static ItemStack upgradeTokenItemTier = null;
    private static ItemStack upgradeTokenItemLevel = null;
    private static List<UpgradeTierEntry> upgradeTierEntries = null;
    private static List<UpgradeFailEntry> upgradeFailEntries = null;

    public static void resetDefaultUpgradeTokens(){
        upgradeTokenItemTier = null;
        upgradeTokenItemLevel = null;
    }

    public static ItemStack setupDefaultUpgradeToken(String configEntry, int defaultCount){
        ItemStack upgradeTokens = null;
        String[] itemSplit = configEntry.split(":");
        try {
            int metadata = itemSplit.length > 2 ? Integer.parseInt(itemSplit[2].trim()) : 32767;
            int count = itemSplit.length > 3 ? Integer.parseInt(itemSplit[3].trim()) : defaultCount;
            Item token = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemSplit[0].trim(), itemSplit[1].trim()));
            if (token == null) {
                SoManyEnchantments.LOGGER.log(Level.WARN, "Invalid upgrade token item: " + configEntry);
                token = Items.PRISMARINE_SHARD;
            }
            upgradeTokens =  new ItemStack(token, count, metadata);
        } catch (Exception e) {
            SoManyEnchantments.LOGGER.warn("Invalid upgrade token, expected pattern: modid:itemname:optional metadata:optional itemcount, provided was: {}",configEntry);
        }
        return upgradeTokens;
    }

    public static ItemStack getDefaultUpgradeTokenItems(boolean isLevelUpgrade) {
        if(isLevelUpgrade){
            if (upgradeTokenItemLevel == null)
                upgradeTokenItemLevel = setupDefaultUpgradeToken(ModConfig.upgrade.upgradeTokenLevel, ModConfig.upgrade.upgradeTokenAmountLevel);
            return upgradeTokenItemLevel;
        } else {
            if (upgradeTokenItemTier == null)
                upgradeTokenItemTier = setupDefaultUpgradeToken(ModConfig.upgrade.upgradeTokenTier, ModConfig.upgrade.upgradeTokenAmountTier);
            return upgradeTokenItemTier;
        }
    }

    public static Enchantment getFailureEnchantFor(Enchantment ench){
        if(upgradeFailEntries == null) {
            upgradeFailEntries = new ArrayList<>();
            for(String entry : ModConfig.upgrade.enchantUpgradeCursing) {
                entry = entry.trim();
                if(entry.isEmpty()) continue;
                List<Enchantment> enchantmentFailureList = new ArrayList<>();
                Enchantment curseEnchantment = null;
                String[] args = entry.split(",");
                for(int i = 0; i < args.length; i++) {
                    String arg = args[i].trim();
                    if(arg.isEmpty()) continue;
                    if(i == args.length - 1) {
                        if("none".equals(arg)) {
                            curseEnchantment = null;
                        }
                        else {
                            Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(arg));
                            if(enchant == null) {
                                SoManyEnchantments.LOGGER.log(Level.WARN, "Invalid curse equivalent curse: " + arg);
                            }
                            curseEnchantment = enchant;
                        }
                    }
                    else {
                        Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(arg));
                        if(enchant == null) {
                            SoManyEnchantments.LOGGER.log(Level.WARN, "Invalid curse equivalent enchantment: " + arg);
                            continue;
                        }
                        enchantmentFailureList.add(enchant);
                    }
                }
                upgradeFailEntries.add(new UpgradeFailEntry(enchantmentFailureList, curseEnchantment));
            }
        }

        for(UpgradeFailEntry entry : upgradeFailEntries)
            if(entry.enchantmentFailureList.contains(ench))
                return entry.curse;
        return null;
    }

    public static Enchantment getUpgradedEnchantFor(Enchantment ench){
        if(upgradeTierEntries == null) {
            upgradeTierEntries = new ArrayList<>();
            for(String entry : ModConfig.upgrade.enchantUpgradeOrder) {
                entry = entry.trim();
                if(entry.isEmpty()) continue;
                List<Enchantment> enchantmentUpgradeList = new ArrayList<>();
                String[] args = entry.split(",");
                for(String arg : args) {
                    arg = arg.trim();
                    if(arg.isEmpty()) continue;
                    Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(arg));
                    if(enchant == null) {
                        SoManyEnchantments.LOGGER.log(Level.WARN, "Invalid upgrade tier enchantment: " + arg);
                        continue;
                    }
                    enchantmentUpgradeList.add(enchant);
                }
                upgradeTierEntries.add(new UpgradeTierEntry(enchantmentUpgradeList));
            }
        }

        for(UpgradeTierEntry entry : upgradeTierEntries){
            Enchantment potentialUpgrade = entry.getUpgradedEnchantment(ench);
            if(potentialUpgrade != null)
                return potentialUpgrade;
        }
        return null;
    }

    public static class UpgradeTierEntry {
        private final List<Enchantment> enchantmentUpgradeList;

        public UpgradeTierEntry(List<Enchantment> enchantmentUpgradeList) {
            this.enchantmentUpgradeList = enchantmentUpgradeList;
        }

        @Nullable
        public Enchantment getUpgradedEnchantment(Enchantment enchantment) {
            int index = this.enchantmentUpgradeList.indexOf(enchantment);
            if(index != -1 && index + 1 < this.enchantmentUpgradeList.size()) {
                return this.enchantmentUpgradeList.get(index + 1);
            }
            return null;
        }
    }

    public static class UpgradeFailEntry {
        private final List<Enchantment> enchantmentFailureList;
        private final Enchantment curse;

        public UpgradeFailEntry(List<Enchantment> enchantmentFailureList, Enchantment curse) {
            this.enchantmentFailureList = enchantmentFailureList;
            this.curse = curse;
        }

        @Nullable
        public Enchantment getCurse() {
            return this.curse;
        }
    }
}
