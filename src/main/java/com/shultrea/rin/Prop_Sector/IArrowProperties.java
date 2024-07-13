package com.shultrea.rin.Prop_Sector;

public interface IArrowProperties {
	
	boolean isArrowRapidDamage();
	
	void setArrowRapidDamage(boolean flag);
	
	int getFlameLevel();
	
	void setFlameLevel(int flameLevel);
	
	void setExplosion(float power, boolean canDestroyBlocks);
	
	float getExplosionPower();
	
	boolean getCanDestroyBlocks();
	
	boolean getNoDrag();
	
	void setNoDrag(boolean noDrag);
	
	boolean getCanRecover();
	
	void setCanRecover(boolean canRecover);
	
	void setDidStarFall(boolean didStarFall);
	
	boolean didStarFall();
	
	int setLevel(int level);
	
	int getLevel();
	
	boolean getIsStarFallMade();
	
	void setIsStarFallMade(boolean IsStarFallMade);
	
	int getArmorPiercingLevel();
	
	void setPiercingLevel(int level);
	
	float getPullPower();
	
	void setPullPower(float level);
}
