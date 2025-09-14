package com.shultrea.rin.registry;

import com.shultrea.rin.SoManyEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class LegacyRemapHandler {
    @SubscribeEvent
    public static void onMissingMappings(RegistryEvent.MissingMappings<Enchantment> event) {
            /*OLD, NEW*/
        Map<String, String> sme_legacy_aliases = new HashMap<>();
        sme_legacy_aliases.put("science", "subjectchemistry");
        sme_legacy_aliases.put("english", "subjectenglish");
        sme_legacy_aliases.put("mathematics", "subjectmathematics");
        sme_legacy_aliases.put("pe", "subjectpe");
        sme_legacy_aliases.put("history", "subjecthistory");
        sme_legacy_aliases.put("lfl", "lesserflame");
        sme_legacy_aliases.put("afl", "advancedflame");
        sme_legacy_aliases.put("sfl", "supremeflame");
        sme_legacy_aliases.put("lfa", "lesserfireaspect");
        sme_legacy_aliases.put("afa", "advancedfireaspect");
        sme_legacy_aliases.put("sfa", "supremefireaspect");
        sme_legacy_aliases.put("underwaterstrider", "swiftswimming");
        sme_legacy_aliases.put("scythedamage", "jaggedrake");
        sme_legacy_aliases.put("welltilled", "moisturized");
        sme_legacy_aliases.put("tillingpower", "plowing");
        sme_legacy_aliases.put("clearsky", "clearskiesfavor");
        sme_legacy_aliases.put("raining", "rainsbestowment");
        sme_legacy_aliases.put("thunderstorm", "thunderstormsbestowment");
        sme_legacy_aliases.put("winter", "wintersgrace");
        sme_legacy_aliases.put("sunshine", "solsblessing");
        sme_legacy_aliases.put("moonlight", "lunasblessing");
        sme_legacy_aliases.put("ancientcurseinflicter", "ancientsealedcurses");
        sme_legacy_aliases.put("swordmastery", "ancientswordmastery");
        sme_legacy_aliases.put("hors_de_combat", "horsdecombat");
        sme_legacy_aliases.put("advancedefficency", "advancedefficiency");
        sme_legacy_aliases.put("inefficent", "inefficient");
        sme_legacy_aliases.put("freezing", "cryogenic");
        sme_legacy_aliases.put("disorientation", "disorientatingblade");
        sme_legacy_aliases.put("defusion", "defusingedge");
        sme_legacy_aliases.put("rune_armorpiercing", "rune_arrowpiercing");
        sme_legacy_aliases.put("frenzy", "unreasonable");
        sme_legacy_aliases.put("swiper", "arcslash");
        sme_legacy_aliases.put("sharperedge", "reinforcedsharpness");
        sme_legacy_aliases.put("fieryshield", "burningshield");
        sme_legacy_aliases.put("upgrade", "upgradedpotentials");
        sme_legacy_aliases.put("pandora", "pandorascurse");
        sme_legacy_aliases.put("pulling", "dragging");
        //Not mapped:
        // Quarrying (deleted)
        // Subject Science (no clear new candidate, Chem vs Phys vs Bio)
        // Combat Veterancy (Combat Medic now on helmet instead of weapon)

        event.getMappings() //already pre-filtered for getNamespace() == SME.MODID
                .stream()
                .filter(m -> sme_legacy_aliases.containsKey(m.key.getPath()))
                .forEach(mapping -> {
                    String newLoc = SoManyEnchantments.MODID + ":" + sme_legacy_aliases.get(mapping.key.getPath());
                    Enchantment mappedEnch = Enchantment.getEnchantmentByLocation(newLoc);
                    if (mappedEnch != null) //could be unregistered via config
                        mapping.remap(mappedEnch);
                });
    }
}
