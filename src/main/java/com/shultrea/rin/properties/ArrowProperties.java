package com.shultrea.rin.properties;

public class ArrowProperties implements IArrowProperties {
	
	private boolean propertiesHandled;
	private int flameLevel;
	private boolean arrowResetsIFrames;
	private int armorPiercingLevel;
	private float draggingPower;
	
	@Override
	public boolean getPropertiesHandled() {
		return this.propertiesHandled;
	}
	
	@Override
	public void setPropertiesHandled(boolean handled) {
		this.propertiesHandled = handled;
	}
	
	@Override
	public boolean getArrowResetsIFrames() {
		return this.arrowResetsIFrames;
	}
	
	@Override
	public void setArrowResetsIFrames(boolean flag) {
		this.arrowResetsIFrames = flag;
	}
	
	@Override
	public int getFlameLevel() {
		return this.flameLevel;
	}
	
	@Override
	public void setFlameLevel(int flameLevel) {
		this.flameLevel = flameLevel;
	}
	
	@Override
	public int getArmorPiercingLevel() {
		return this.armorPiercingLevel;
	}
	
	@Override
	public void setArmorPiercingLevel(int level) {
		this.armorPiercingLevel = level;
	}
	
	@Override
	public float getDraggingPower() {
		return this.draggingPower;
	}
	
	@Override
	public void setDraggingPower(float p) {
		this.draggingPower = p;
	}
}