package com.shultrea.rin.mixin.vanilla;

import net.minecraft.entity.monster.EntityZombieVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityZombieVillager.class)
public interface EntityZombieVillagerAccessor {
    @Invoker("finishConversion")
    void invokeFinishConversion();
}
