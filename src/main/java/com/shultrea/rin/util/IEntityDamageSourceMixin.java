package com.shultrea.rin.util;

public interface IEntityDamageSourceMixin {
	
	void soManyEnchantments$setCulling();
	void soManyEnchantments$setPiercingPercent(float percent);
	
	boolean soManyEnchantments$getCulling();
	float soManyEnchantments$getPiercingPercent();
}