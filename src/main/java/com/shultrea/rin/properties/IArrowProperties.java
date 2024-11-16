package com.shultrea.rin.properties;

public interface IArrowProperties {
	
	boolean getArrowResetsIFrames();
	
	void setArrowResetsIFrames(boolean flag);
	
	int getFlameLevel();
	
	void setFlameLevel(int flameLevel);
	
//	void setExplosion(float power, boolean canDestroyBlocks);
//
//	float getExplosionPower();
//
//	boolean getCanDestroyBlocks();
//
//	boolean getCanRecover();
//
//	void setCanRecover(boolean canRecover);
//
//	void setDidStarFall(boolean didStarFall);
//
//	boolean didStarFall();
//
//	void setLevel(int level);
//
//	int getLevel();
//
//	boolean getIsStarFallMade();
//
//	void setIsStarFallMade(boolean IsStarFallMade);
	
	int getArmorPiercingLevel();
	
	void setArmorPiercingLevel(int level);
	
	float getDraggingPower();
	
	void setDraggingPower(float level);
}
