package com.shultrea.rin.properties;

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
