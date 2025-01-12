package com.shultrea.rin.util;

public class RainbowUtil {
	
	private int lastState;
	private int red = 255;
	private int green = 0;
	private int blue = 0;
	
	public RainbowUtil(int lastState) {
		this.lastState = lastState;
	}
	
	//https://stackoverflow.com/q/31784658
	public void tick() {
		int state = this.lastState;
		int r = this.red;
		int g = this.green;
		int b = this.blue;
		if(state == 0) {
			g++;
			if(g >= 255) {
				g = 255;
				state = 1;
			}
		}
		if(state == 1) {
			r--;
			if(r <= 0) {
				r = 0;
				state = 2;
			}
		}
		if(state == 2){
			b++;
			if(b >= 255) {
				b = 255;
				state = 3;
			}
		}
		if(state == 3){
			g--;
			if(g <= 0) {
				g = 0;
				state = 4;
			}
		}
		if(state == 4){
			r++;
			if(r >= 255) {
				r = 255;
				state = 5;
			}
		}
		if(state == 5){
			b--;
			if(b <= 0) {
				b = 0;
				state = 0;
			}
		}
		this.lastState = state;
		this.red = r;
		this.green = g;
		this.blue = b;
	}
	
	public int getDecimalColor() {
		return 65536 * this.red + 256 * this.green + this.blue;
	}
	
	//https://stackoverflow.com/a/20820649
	public int getDesaturatedDecimalColor(float desaturation) {
		float greyscale = 0.3F * (float)this.red + 0.6F * (float)this.green + 0.1F * (float)this.blue;
		int dRed = Math.min(255, Math.max(0, (int)((float)this.red + desaturation * (greyscale - (float)this.red))));
		int dGreen = Math.min(255, Math.max(0, (int)((float)this.green + desaturation * (greyscale - (float)this.green))));
		int dBlue = Math.min(255, Math.max(0, (int)((float)this.blue + desaturation * (greyscale - (float)this.blue))));
		return 65536 * dRed + 256 * dGreen + dBlue;
	}
}