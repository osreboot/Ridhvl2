package com.osreboot.ridhvl2;

public final class HvlMath {
	
	private HvlMath(){}

	/**
	 * Returns <code>xArg</code> progressed towards <code>goalArg</code> by <code>modifierArg</code> amount.
	 * The sign of <code>modifierArg</code> doesn't matter. Returns <code>goalArg</code> if <code>xArg</code> 
	 * plus or minus a fraction of <code>modifierArg</code> equals <code>goalArg</code>.
	 * 
	 * <p>
	 * 
	 * Examples:<br>
	 * <code>stepTowards(0, 5, 10)</code> returns <code>5</code> because 0 + 5 = 5<br>
	 * <code>stepTowards(0, 5, -10)</code> returns <code>-5</code> because 0 - 5 = -5<br>
	 * <code>stepTowards(0, 20, 10)</code> returns <code>10</code> because 0 + 20 > 10<br>
	 * <code>stepTowards(0, 20, -10)</code> returns <code>-10</code> because 0 - 20 < -10
	 * 
	 * <p>
	 * 
	 * This method can be used, for example, to move an object (whose location is represented by 
	 * <code>xArg</code>) towards a wall (whose location is represented by <code>goalArg</code>) at 
	 * <code>modifierArg</code> speed whereupon arrival the object will stop.
	 * 
	 * @param xArg source value for the step operation
	 * @param modifierArg value by which <code>xArg</code> will be modified (unsigned)
	 * @param goalArg maximum or minimum value to be returned
	 * @return <code>xArg</code> progressed towards <code>goalArg</code> by <code>modifierArg</code> amount
	 */
	public static float stepTowards(float xArg, float modifierArg, float goalArg){
		if(goalArg > xArg){
			if(xArg + modifierArg < goalArg) return xArg + modifierArg; else return goalArg;
		}else{
			if(xArg - modifierArg > goalArg) return xArg - modifierArg; else return goalArg;
		}
	}
	
	/**
	 * A clone of Arduino's <code>map</code> function. This operation performs a 1D linear interpolation
	 * on <code>xArg</code> given a set of boundaries. The boundary arguments are non-restrictive, for
	 * restricting boundaries see TODO <code>mapl</code>.
	 * 
	 * <p>
	 * 
	 * In essence, this operation takes the relation of <code>xArg</code> to <code>inLowArg</code> and
	 * <code>inHighArg</code> and outputs a value of similar relation for the set <code>outLowArg</code>
	 * and <code>outHighArg</code>. If <code>xArg</code> is directly in between <code>inLowArg</code> and
	 * <code>inHighArg</code>, then the output value will be directly in between <code>outLowArg</code> and 
	 * <code>outHighArg</code>. If <code>xArg</code> is equal to <code>inLowArg</code> plus 30% of the difference
	 * between <code>inHighArg</code> and <code>inLowArg</code>, then the output will be equal to 
	 * <code>outLowArg</code> plus 30% of the difference between <code>outHighArg</code> and 
	 * <code>outLowArg</code>. This applies to all cases and all sets of boundaries, including cases where 
	 * <code>xArg</code> is outside of the boundaries specified by <code>inLowArg</code> and 
	 * <code>inHighArg</code>.
	 * 
	 * <p>
	 * 
	 * This method can be used, for example, to interpolate between the output of a <code>sin</code> function
	 * that varies with time and on-screen coordinates, so that an object may appear to bob up and down. In this
	 * case, <code>xArg</code> would be the current output of the <code>sin</code> function and 
	 * <code>inLowArg</code> and <code>inHighArg</code> would be <code>-1</code> and <code>1</code> respectively
	 * (the boundaries of the <code>sin</code> function's output values). <code>outLowArg</code> and 
	 * <code>outHighArg</code> would be the minimum and maximum projected screen coordinates (for example 100 and
	 * 200, so the object repeatedly transitions between the y-coordinates 100 and 200 and then back).
	 * 
	 * @param xArg source value for the map operation
	 * @param inLowArg lower input boundary
	 * @param inHighArg higher input boundary
	 * @param outLowArg lower output boundary
	 * @param outHighArg higher output boundary
	 * @return <code>xArg</code> linearly interpolated from the input boundaries to the output boundaries 
	 * (non-restrictive)
	 */
	public static float map(float xArg, float inLowArg, float inHighArg, float outLowArg, float outHighArg){
		return (xArg - inLowArg) * (outHighArg - outLowArg) / (inHighArg - inLowArg) + outLowArg;
	}
	
	/**
	 * Returns <code>xArg</code> constrained by the two boundary arguments <code>bound1Arg</code> and 
	 * <code>bound2Arg</code>. The order of the boundary arguments doesn't matter, the returned value will always 
	 * be equal to a boundary or between the two. If the boundaries are equal, the first boundary is returned.
	 * 
	 * @param xArg source value for the constrain operation
	 * @param bound1Arg the first boundary for the constrain operation
	 * @param bound2Arg the second boundary for the constrain operation (not necessarily greater than 
	 * <code>bound1Arg</code>)
	 * @return <code>xArg</code> constrained to the boundary set <code>bound1Arg</code> and <code>bound2Arg</code>
	 */
	public static float limit(float xArg, float bound1Arg, float bound2Arg){
		if(bound1Arg > bound2Arg){
			return Math.max(Math.min(xArg, bound1Arg), bound2Arg);
		}else if(bound1Arg < bound2Arg){
			return Math.max(Math.min(xArg, bound2Arg), bound1Arg);
		}else return bound1Arg;
	}
	
}
