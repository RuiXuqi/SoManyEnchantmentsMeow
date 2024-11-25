package com.shultrea.rin.properties;

public interface IArrowProperties {
	
	boolean getPropertiesHandled();
	
	void setPropertiesHandled(boolean handled);
	
	boolean getArrowResetsIFrames();
	
	void setArrowResetsIFrames(boolean flag);
	
	int getFlameLevel();
	
	void setFlameLevel(int flameLevel);
	
	int getArmorPiercingLevel();
	
	void setArmorPiercingLevel(int level);
	
	float getDraggingPower();
	
	void setDraggingPower(float level);
}