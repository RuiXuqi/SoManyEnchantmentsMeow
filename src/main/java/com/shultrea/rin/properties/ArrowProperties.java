package com.shultrea.rin.properties;

public class ArrowProperties implements IArrowProperties {
	
//	private float ExplosionPower;
//	private boolean CanDestroyBLocks;
//	private boolean CanRecover;
//	private boolean didStarFall;
//	private int level;
//	private boolean isStarFallMade;
	private int flameLevel;
	private boolean arrowResetsIFrames;
	private int armorPiercingLevel;
	private float draggingPower;
	
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
	
//	@Override
//	public void setExplosion(float power, boolean canDestroyBlocks) {
//		this.ExplosionPower = power;
//		this.CanDestroyBLocks = canDestroyBlocks;
//	}
//
//	@Override
//	public float getExplosionPower() {
//		return this.ExplosionPower;
//	}
//
//	@Override
//	public boolean getCanDestroyBlocks() {
//		return this.CanDestroyBLocks;
//	}
//
//	@Override
//	public boolean getCanRecover() {
//		return this.CanRecover;
//	}
//
//	@Override
//	public void setCanRecover(boolean canRecover) {
//		this.CanRecover = canRecover;
//	}
//
//	@Override
//	public void setDidStarFall(boolean didStarFall) {
//		this.didStarFall = didStarFall;
//	}
//
//	@Override
//	public boolean didStarFall() {
//		return this.didStarFall;
//	}
//
//	@Override
//	public void setLevel(int level) {
//        this.level = level;
//    }
//
//	@Override
//	public int getLevel() {
//		return this.level;
//	}
//
//	@Override
//	public boolean getIsStarFallMade() {
//		return isStarFallMade;
//	}
//
//	@Override
//	public void setIsStarFallMade(boolean isStarFall) {
//		this.isStarFallMade = isStarFall;
//	}
	
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
