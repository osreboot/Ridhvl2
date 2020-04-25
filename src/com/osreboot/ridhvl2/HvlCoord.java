package com.osreboot.ridhvl2;

import java.io.Serializable;
import java.util.Objects;

/**
 * A single 2D coordinate represented by an <code>x</code> and <code>y</code> floating point value.
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
	 * @param xArg the value to assign to the <code>x</code> variable
	 * @param yArg the value to assign to the <code>y</code> variable
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
	 * Assigns new <code>x</code> and <code>y</code> values.
	 * 
	 * @param xArg the new value of <code>x</code>
	 * @param yArg the new value of <code>y</code>
	 */
	public void set(float xArg, float yArg){
		x = xArg;
		y = yArg;
	}
	
	/**
	 * Assigns new <code>x</code> and <code>y</code> values inherited from the given HvlCoord instance.
	 * 
	 * @param cArg the HvlCoord to inherit values from
	 */
	public void set(HvlCoord cArg){
		x = cArg.x;
		y = cArg.y;
	}
	
	/**
	 * Adds <code>xArg</code> and <code>yArg</code> to the HvlCoord's existing <code>x</code>
	 * and <code>y</code> values.
	 * 
	 * @param xArg the value to add to <code>x</code>
	 * @param yArg the value to add to <code>y</code>
	 * @return a reference to the HvlCoord for method chaining
	 */
	public HvlCoord add(float xArg, float yArg){
		x += xArg;
		y += yArg;
		return this;
	}
	
	/**
	 * Adds <code>cArg</code>'s <code>x</code> and <code>y</code> values to the HvlCoord's existing
	 * <code>x</code> and <code>y</code> values.
	 * 
	 * @param cArg the coordinate values to add to <code>x</code> and <code>y</code>
	 * @return a reference to the HvlCoord for method chaining
	 */
	public HvlCoord add(HvlCoord cArg){
		x += cArg.x;
		y += cArg.y;
		return this;
	}
	
	/**
	 * Multiplies the HvlCoord's existing <code>x</code> and <code>y</code> values by
	 * <code>xArg</code> and <code>yArg</code>.
	 * 
	 * @param xArg the value to multiply <code>x</code> by
	 * @param yArg the value to multiply <code>y</code> by
	 * @return a reference to the HvlCoord for method chaining
	 */
	public HvlCoord multiply(float xArg, float yArg){
		x *= xArg;
		y *= yArg;
		return this;
	}
	
	/**
	 * Multiplies the HvlCoord's existing <code>x</code> and <code>y</code> values by
	 * <code>cArg</code>'s <code>x</code> and <code>y</code>.
	 * 
	 * @param cArg the coordinate values to multiply <code>x</code> and <code>y</code> by
	 * @return a reference to the HvlCoord for method chaining
	 */
	public HvlCoord multiply(HvlCoord cArg){
		x *= cArg.x;
		y *= cArg.y;
		return this;
	}
	
	/**
	 * Returns the Euclidean distance between the HvlCoord and the origin <code>(0, 0)</code>.
	 * 
	 * @return the Euclidean distance between the HvlCoord and <code>(0, 0)</code>
	 */
	public float magnitude(){
		return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	/**
	 * Returns the angle from the origin <code>(0, 0)</code> to the HvlCoord, as calculated by
	 * {@linkplain HvlMath#angle(float, float, float, float)}.
	 * 
	 * @return the angle from <code>(0, 0)</code> to the HvlCoord
	 */
	public float angle(){
		return HvlMath.angle(0, 0, x, y);
	}
	
	/**
	 * Normalizes the HvlCoord. In other words, the HvlCoord is constrained to the unit circle
	 * by dividing the HvlCoord's <code>x</code> and <code>y</code> values by the HvlCoord's
	 * collective {@linkplain #magnitude()}. This method does not handle <code>NaN</code>
	 * situations.
	 * 
	 * @return a reference to the HvlCoord for method chaining
	 */
	public HvlCoord normalize(){
		float magnitude = magnitude();
		x /= magnitude;
		y /= magnitude;
		return this;
	}
	
	/**
	 * Checks <code>x</code> and <code>y</code> for <code>NaN</code> values, and independently
	 * sets each to <code>xFixArg</code> and <code>yFixArg</code> (respectively) if either is
	 * found to be <code>NaN</code>.
	 * 
	 * @param xFixArg the value to replace <code>x</code> with if <code>x</code> is <code>NaN</code>
	 * @param yFixArg the value to replace <code>y</code> with if <code>y</code> is <code>NaN</code>
	 * @return a reference to the HvlCoord for method chaining
	 */
	public HvlCoord fixNaN(float xFixArg, float yFixArg){
		x = Float.isNaN(x) ? xFixArg : x;
		y = Float.isNaN(y) ? yFixArg : y;
		return this;
	}
	
	/**
	 * Checks <code>x</code> and <code>y</code> for <code>NaN</code> values, and independently
	 * sets each to <code>0</code> if either is found to be <code>NaN</code>.
	 * 
	 * @return a reference to the HvlCoord for method chaining
	 */
	public HvlCoord fixNaN(){
		x = Float.isNaN(x) ? 0 : x;
		y = Float.isNaN(y) ? 0 : y;
		return this;
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
