package com.shultrea.rin.mixin.vanilla.zombieskeeptrades;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityZombie.class)
public abstract class EntityZombieMixin {

    @Unique NBTTagCompound soManyEnchantments$killedVillagerTags = new NBTTagCompound();

    @Redirect(
            method = "onKillEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;removeEntity(Lnet/minecraft/entity/Entity;)V")
    )
    public void soManyEnchantments_vanillaEntityZombie_removeEntity(World world, Entity deadVillager) {
        NBTTagCompound villagerTags = new NBTTagCompound();
        deadVillager.writeToNBT(villagerTags);

        soManyEnchantments$killedVillagerTags.setInteger("Profession", villagerTags.getInteger("Profession"));
        soManyEnchantments$killedVillagerTags.setString("ProfessionName", villagerTags.getString("ProfessionName"));
        soManyEnchantments$killedVillagerTags.setInteger("Riches", villagerTags.getInteger("Riches"));
        soManyEnchantments$killedVillagerTags.setInteger("Career", villagerTags.getInteger("Career"));
        soManyEnchantments$killedVillagerTags.setInteger("CareerLevel", villagerTags.getInteger("CareerLevel"));
        soManyEnchantments$killedVillagerTags.setTag("Offers", villagerTags.getTag("Offers"));

        world.removeEntity(deadVillager);
    }

    @Redirect(
            method = "onKillEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z")
    )
    public boolean soManyEnchantments_vanillaEntityZombie_spawnEntity(World world, Entity newZombieVillager) {
        newZombieVillager.getEntityData().setTag("villagerTags", soManyEnchantments$killedVillagerTags);
        return world.spawnEntity(newZombieVillager);
    }
}