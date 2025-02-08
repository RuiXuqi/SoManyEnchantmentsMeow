package com.shultrea.rin.attributes;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EnchantAttribute {
    public static final IAttribute ENCHANTFOCUS = (new RangedAttribute(null, "somanyenchantments.enchantfocus", 0.0, -1000.0, 1000.0)).setDescription("Enchanting Focus").setShouldWatch(true);

    @SubscribeEvent
    public static void onEntityConstruction(EntityEvent.EntityConstructing event) {
        if(event.getEntity() instanceof EntityPlayer) {
            ((EntityPlayer) event.getEntity()).getAttributeMap().registerAttribute(ENCHANTFOCUS);
        }
    }

    //ThreadLocal to be able to pass the player attribute from ContainerEnchantment over to EnchantmentHelper.buildEnchantmentList
    public static final ThreadLocal<IAttributeInstance> attributeThreadLocal = ThreadLocal.withInitial(() -> null);
    public static IAttributeInstance getAndRemoveThreadLocal(){
        IAttributeInstance value = attributeThreadLocal.get();
        attributeThreadLocal.remove();
        return value;
    }
}
