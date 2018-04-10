package com.osreboot.ridhvl2;

import java.io.Serializable;

/**
 * A single 2D coordinate represented by an x and y floating point value.
 * 
 * @author os_reboot
 *
 */
public final class HvlCoord implements Serializable{
	private static final long serialVersionUID = -7935326529984578377L;
	
	private float x, y;
	
	/**
	 * Constructs a HvlCoord from the given coordinate values.
	 * 
	 * @param xArg the value to assign to the 'x' variable
	 * @param yArg the value to assign to the 'y' variable
	 */
	public HvlCoord(float xArg, float yArg){
		x = xArg;
		y = yArg;
	}
	
	/**
	 * Constructs a HvlCoord with coordinate values at the origin.
	 */
	public HvlCoord(){
		x = 0;
		y = 0;
	}
	
	/**
	 * Constructs a HvlCoord that inherits the coordinate values from the given HvlCoord instance.
	 * 
	 * @param cArg the HvlCoord to inherit values from
	 */
	public HvlCoord(HvlCoord cArg){
		x = cArg.getX();
		y = cArg.getY();
	}

	/**
	 * @return the 'x' value of the HvlCoord
	 */
	public float getX(){
		return x;
	}

	/**
	 * @return the 'x' value of the HvlCoord
	 */
	public float getY(){
		return y;
	}

	/**
	 * Assigns a new 'x' value.
	 * 
	 * @param xArg to new value of 'x'
	 */
	public void setX(float xArg){
		x = xArg;
	}
	
	/**
	 * Assigns a new 'y' value.
	 * 
	 * @param yArg to new value of 'y'
	 */
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
