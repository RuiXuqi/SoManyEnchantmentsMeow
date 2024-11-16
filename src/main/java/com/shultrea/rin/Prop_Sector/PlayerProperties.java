package com.shultrea.rin.Prop_Sector;

public class PlayerProperties implements IPlayerProperties {
	
	boolean isResurrecting;
	
	@Override
	public boolean isResurrecting() {
		return this.isResurrecting;
	}
	
	@Override
	public void setResurrecting(boolean flag) {
		this.isResurrecting = flag;
	}
}
