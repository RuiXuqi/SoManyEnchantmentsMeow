package com.shultrea.rin.handlers;

import com.shultrea.rin.config.ModConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class AnvilUseTooltipHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onItemTooltip(ItemTooltipEvent event){
        if(!ModConfig.miscellaneous.addAnvilUseTooltip) return;
        if(!event.getFlags().isAdvanced()) return;
        if(event.getEntityPlayer() == null || !(event.getEntityPlayer().openContainer instanceof ContainerRepair)) return;

        int repairCost = event.getItemStack().getRepairCost();
        if(repairCost <= 0) return;
        int uses = MathHelper.log2(repairCost + 1); //logarithms baby

        int tooltipIndex = 1;
        if(event.getItemStack().isItemEnchanted()) {
            NBTTagList nbttaglist = event.getItemStack().getEnchantmentTagList();
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(nbttaglist.tagCount() - 1);
            int enchId = nbttagcompound.getShort("id");
            int enchLvl = nbttagcompound.getShort("lvl");
            Enchantment enchantment = Enchantment.getEnchantmentByID(enchId);

            if (enchantment != null) {
                String enchTooltip = enchantment.getTranslatedName(enchLvl);
                tooltipIndex = event.getToolTip().indexOf(enchTooltip) + 1;
                if(tooltipIndex == 0) tooltipIndex = 1;
            }
        }

        event.getToolTip().add(tooltipIndex, TextFormatting.DARK_GRAY + I18n.format((uses == 1) ? "tooltip.anviluses_singular" : "tooltip.anviluses", uses, repairCost) + TextFormatting.RESET);
    }
}
