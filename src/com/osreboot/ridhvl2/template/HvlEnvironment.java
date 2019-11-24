package com.osreboot.ridhvl2.template;

import com.osreboot.ridhvl2.template.HvlDisplay;

public final class HvlEnvironment{

	private float x, y, width, height;
	private boolean xLocked, yLocked, widthLocked, heightLocked;

	protected HvlEnvironment(float xArg, float yArg, float widthArg, float heightArg){
		set(xArg, yArg, widthArg, heightArg);
		setLock(true, true, true, true);
	}
	
	public HvlEnvironment(){
		copyFrom(HvlDisplay.getDisplay().getEnvironment());
		setLock(true, true, true, true);
	}

	public HvlEnvironment(HvlEnvironment environment){
		copyFrom(environment);
		setLock(true, true, true, true);
	}
	
	protected void set(float xArg, float yArg, float widthArg, float heightArg){
		x = xArg;
		y = yArg;
		width = widthArg;
		height = heightArg;
	}
	
	public void setLock(boolean xLockedArg, boolean yLockedArg, boolean widthLockedArg, boolean heightLockedArg){
		xLocked = xLockedArg;
		yLocked = yLockedArg;
		widthLocked = widthLockedArg;
		heightLocked = heightLockedArg;
	}
	
	public void copyFrom(HvlEnvironment environment){
		if(xLocked) x = environment.x;
		if(yLocked) y = environment.y;
		if(widthLocked) width = environment.width;
		if(heightLocked) height = environment.height;
	}
	
	public void copyFromAndUnlock(HvlEnvironment environment){
		x = environment.x;
		y = environment.y;
		width = environment.width;
		height = environment.height;
		setLock(false, false, false, false);
	}
	
	public void setAndUnlockX(float xArg){
		x = xArg;
		xLocked = false;
	}

	public void setAndUnlockY(float yArg){
		y = yArg;
		yLocked = false;
	}

	public void setAndUnlockWidth(float widthArg){
		width = widthArg;
		widthLocked = false;
	}

	public void setAndUnlockHeight(float heightArg){
		height = heightArg;
		heightLocked = false;
	}
	
	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}

	public float getWidth(){
		return width;
	}

	public float getHeight(){
		return height;
	}
	
	public boolean isXLocked(){
		return xLocked;
	}
	
	public boolean isYLocked(){
		return yLocked;
	}
	
	public boolean isWidthLocked(){
		return widthLocked;
	}
	
	public boolean isHeightLocked(){
		return heightLocked;
	}

}
