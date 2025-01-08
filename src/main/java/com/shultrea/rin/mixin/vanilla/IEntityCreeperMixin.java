package com.shultrea.rin.mixin.vanilla;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.network.datasync.DataParameter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityCreeper.class)
public interface IEntityCreeperMixin {
	
	@Accessor(value = "IGNITED")
	DataParameter<Boolean> getIGNITED();
	
	@Accessor(value = "timeSinceIgnited")
	void setTimeSinceIgnited(int timeSinceIgnited);
	
	@Accessor(value = "explosionRadius")
	void setExplosionRadius(int explosionRadius);
}