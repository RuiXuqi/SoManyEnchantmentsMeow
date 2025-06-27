package com.shultrea.rin.config;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.util.Types;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigProvider {

    // -------------------- Incompatible --------------------

    public static ArrayList<Enchantment> getIncompatibleEnchantmentsString(Enchantment thisEnch) {
        ArrayList<Enchantment> incompatEnchs = new ArrayList<>();

        ResourceLocation regName = thisEnch.getRegistryName();
        if(regName == null) return incompatEnchs;

        for(String s : ModConfig.incompatible.incompatibleGroups) {
            if(s.contains(regName.getPath())) {
                //Assumes that config lines are enchantments separated by comma
                String[] enchsInList = s.split(",");
                for(String s1 : enchsInList) {
                    s1 = s1.trim();
                    if(s1.isEmpty()) continue;
                    //assumes that the config uses modid:enchantment if its not an SME enchant
                    if(!s1.contains(":")) s1 = SoManyEnchantments.MODID + ":" + s1;
                    Enchantment incompatEnch = Enchantment.getEnchantmentByLocation(s1);
                    if(incompatEnch == null) SoManyEnchantments.LOGGER.info("SME: could not find incompatible enchantment {}", s1);
                    else incompatEnchs.add(incompatEnch);
                }
            }
        }
        // remove duplicates of the calling enchant
        // every enchantment is incompatible with itself, this is handled by Enchantment.java though
        // and thus doesnt need to be in this list
        while(incompatEnchs.contains(thisEnch)) {
            incompatEnchs.remove(thisEnch);
        }

        return incompatEnchs;
    }

    // -------------------- Can Apply --------------------

    private static final HashMap<String, EnumEnchantmentType> itemTypes = new HashMap<>();
    private static final HashMap<String, CustomType> customTypeMap = new HashMap<>();

    private static class CustomType {
        String name = "";
        String regex = "";
        boolean inverted = false;

        CustomType(String in){
            String[] split = in.split(";");
            if(split.length>=2) {
                name = split[0];
                regex = split[1];
            }
            if(split.length>=3)
                inverted = "NOT".equals(split[2]);
        }
    }

    public static void initCanApply(){
        itemTypes.put("ALL_TYPES",EnumEnchantmentType.ALL);
        itemTypes.put("ARMOR",EnumEnchantmentType.ARMOR);
        itemTypes.put("ARMOR_HEAD",EnumEnchantmentType.ARMOR_HEAD);
        itemTypes.put("ARMOR_CHEST",EnumEnchantmentType.ARMOR_CHEST);
        itemTypes.put("ARMOR_LEGS",EnumEnchantmentType.ARMOR_LEGS);
        itemTypes.put("ARMOR_FEET",EnumEnchantmentType.ARMOR_FEET);
        itemTypes.put("SWORD",EnumEnchantmentType.WEAPON);
        itemTypes.put("TOOL",EnumEnchantmentType.DIGGER);
        itemTypes.put("FISHING_ROD",EnumEnchantmentType.FISHING_ROD);
        itemTypes.put("BREAKABLE",EnumEnchantmentType.BREAKABLE);
        itemTypes.put("BOW",EnumEnchantmentType.BOW);
        itemTypes.put("WEARABLE",EnumEnchantmentType.WEARABLE);

        itemTypes.put("ALL_ITEMS", Types.ALL);
        itemTypes.put("AXE", Types.COMBAT_AXE);
        itemTypes.put("PICKAXE", Types.PICKAXE);
        itemTypes.put("HOE", Types.HOE);
        itemTypes.put("SHOVEL", Types.SPADE);
        itemTypes.put("SHIELD", Types.SHIELD);
        itemTypes.put("NONE", Types.NONE);

        for(String s: ModConfig.canApply.customTypes){
            CustomType c = new CustomType(s);
            if(!"".equals(c.name) && !"".equals(c.regex))
                customTypeMap.put(c.name,c);
        }
    }

    public static boolean canItemApply(String[] enchantConfig, ItemStack stack){
        Item item = stack.getItem();
        boolean isValid = false;
        boolean invertedMatches = false;
        String itemName = null;
        for(String s: enchantConfig){
            if(itemTypes.containsKey(s)){
                //Normal Types via Predicates
                EnumEnchantmentType enumEnchantmentType = itemTypes.get(s);
                isValid = isValid || enumEnchantmentType.canEnchantItem(item);
                //can't early return bc custom types can also exclude certain types
            } else if(customTypeMap.containsKey(s)) {
                //Custom Types via Regex Matching
                CustomType c = customTypeMap.get(s);
                if(itemName == null) {
                    ResourceLocation loc = item.getRegistryName();
                    if (loc != null) itemName = loc.toString(); //only need to toString once if there's multiple custom types
                    else itemName = ""; //Shouldn't match anything in this edge case
                }
                boolean matches = itemName.matches(c.regex);
                if(!c.inverted)
                    isValid = isValid || matches;
                else
                    invertedMatches = invertedMatches || matches;
            } else {
                SoManyEnchantments.LOGGER.info("SME: Could not find given item type {}", s);
            }
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
