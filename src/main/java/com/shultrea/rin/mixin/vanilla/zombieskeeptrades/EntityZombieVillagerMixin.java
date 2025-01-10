package com.shultrea.rin.mixin.vanilla.zombieskeeptrades;

import com.shultrea.rin.enchantments.weapon.potiondebuffer.EnchantmentPurification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityZombieVillager.class)
public abstract class EntityZombieVillagerMixin {
    @Redirect(
            method = "finishConversion",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean soManyEnchantments_vanillaEntityZombieVillager_finishConversion(World world, Entity newVillager) {
        EnchantmentPurification.setVillagerTradesFromZombieVillager((EntityZombieVillager) (Object) this, (EntityVillager) newVillager);
        return world.spawnEntity(newVillager);
    }
}