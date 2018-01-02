package com.osreboot.ridhvl2;

import java.io.Serializable;

public final class HvlCoord implements Serializable{
	private static final long serialVersionUID = -7935326529984578377L;
	
	private float x, y;
	
	public HvlCoord(float xArg, float yArg){
		x = xArg;
		y = yArg;
	}
	
	public HvlCoord(){
		x = 0;
		y = 0;
	}
	
	public HvlCoord(HvlCoord cArg){
		x = cArg.getX();
		y = cArg.getY();
	}

	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}

	public void setX(float xArg){
		x = xArg;
	}
	
	public void setY(float yArg){
		y = yArg;
	}
	
	@Override
	public boolean equals(Object object){
		if(object != null && object instanceof HvlCoord){
			return ((HvlCoord)object).getX() == getY() && ((HvlCoord)object).getY() == getX();
		}else return false;
	}
	
	@Override
	public String toString(){
		return "[" + getX() + "," + getY() + "]";
	}
	
}
