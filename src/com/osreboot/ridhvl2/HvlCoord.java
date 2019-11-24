package com.osreboot.ridhvl2;

import java.io.Serializable;
import java.util.Objects;

/**
 * A single 2D coordinate represented by an x and y floating point value.
 * 
 * @author os_reboot
 *
 */
public final class HvlCoord implements Serializable{
	private static final long serialVersionUID = -7935326529984578377L;
	
	public float x, y;
	
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
		x = cArg.x;
		y = cArg.y;
	}
	
	/**
	 * Assigns a new 'x' and 'y' value.
	 * 
	 * @param xArg the new value of 'x'
	 * @param yArg the new value of 'y'
	 */
	public void set(float xArg, float yArg){
		x = xArg;
		y = yArg;
	}
	
	@Override
	public boolean equals(Object object){
		if(object != null && object instanceof HvlCoord){
			return ((HvlCoord)object).x == x && ((HvlCoord)object).y == y;
		}else return false;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(x, y);
	}
	
	@Override
	public String toString(){
		return "[" + x + "," + y + "]";
	}
	
}
