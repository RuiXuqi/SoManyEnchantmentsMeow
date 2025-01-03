package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.util.IEntityDamageSourceMixin;
import net.minecraft.util.EntityDamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityDamageSource.class)
public abstract class EntityDamageSourceMixin implements IEntityDamageSourceMixin {
	
	@Unique
	private boolean soManyEnchantments$culling = false;
	@Unique
	private float soManyEnchantments$piercingPercent = 0.0F;
	
	@Unique
	public void soManyEnchantments$setCulling() {
		this.soManyEnchantments$culling = true;
	}
	
	@Unique
	public void soManyEnchantments$setPiercingPercent(float percent) {
		this.soManyEnchantments$piercingPercent = percent;
	}
	
	@Unique
	public boolean soManyEnchantments$getCulling() {
		return this.soManyEnchantments$culling;
	}
	
	@Unique
	public float soManyEnchantments$getPiercingPercent() {
		return this.soManyEnchantments$piercingPercent;
	}
}