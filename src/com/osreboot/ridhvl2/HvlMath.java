package com.osreboot.ridhvl2;

import java.util.Random;

/**
 * 
 * A collection of logic operations that are commonly used in game design, but don't appear in Java's standard 
 * libraries. Also contains a small collection of the <code>float</code> mappings of common {@linkplain Math}
 * operations, for the sake of efficiency.
 * 
 * @author os_reboot
 *
 */
public final class HvlMath {

	private HvlMath(){}

	/**
	 * The <code>float</code> equivalent of {@linkplain Math#PI}.
	 */
	public static final float PI = (float)Math.PI;

	/**
	 * A {@linkplain Random} object used for random number generation.
	 */
	private static Random random = new Random();

	/**
	 * Returns a random <code>float</code> constrained by the two boundary arguments <code>bound1Arg</code> and 
	 * <code>bound2Arg</code>. The order of the boundary arguments doesn't matter, the returned value will always 
	 * be equal to a boundary or between the two.
	 * 
	 * @param bound1Arg The first boundary the returned <code>float</code> is constrained by
	 * @param bound2Arg The second boundary the returned <code>float</code> is constrained by
	 * @return A random <code>float</code> constrained to the boundary set <code>bound1Arg</code> and <code>bound2Arg</code>, inclusive.
	 */
	public static float randomFloat(float bound1Arg, float bound2Arg){
		float max = Math.max(bound1Arg, bound2Arg);
		float min = Math.min(bound1Arg, bound2Arg);
		return min + ((float)Math.random() * (max - min));
	}
	
	/**
	 * Returns a random <code>int</code> constrained by the two boundary arguments <code>bound1Arg</code> and 
	 * <code>bound2Arg</code>. The order of the boundary arguments doesn't matter, the returned value will always 
	 * be equal to a boundary or between the two.
	 * 
	 * @param bound1Arg The first boundary the returned <code>int</code> is constrained by
	 * @param bound2Arg The second boundary the returned <code>int</code> is constrained by
	 * @return A random <code>int</code> constrained to the boundary set <code>bound1Arg</code> and <code>bound2Arg</code>, inclusive.
	 */
	public static int randomInt(int bound1Arg, int bound2Arg){
		int max = Math.max(bound1Arg, bound2Arg);
		int min = Math.min(bound1Arg, bound2Arg);
		max++;
		return min + random.nextInt(max - min);
	}

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
	 * <code>stepTowards(0, 20, -10)</code> returns <code>-10</code> because 0 - 20 < -10<br>
	 * <code>stepTowards(0, -1, 2)</code> returns <code>1</code> because 0 + 1 = 1<br>
	 * <code>stepTowards(0, -1, -2)</code> returns <code>-1</code> because 0 - 1 = -1
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
			if(xArg + Math.abs(modifierArg) < goalArg) return xArg + Math.abs(modifierArg); else return goalArg;
		}else{
			if(xArg - Math.abs(modifierArg) > goalArg) return xArg - Math.abs(modifierArg); else return goalArg;
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

	/**
	 * Returns the Euclidean distance between the points specified by <code>(x1Arg, y1Arg)</code> and
	 * <code>(x2Arg, y2Arg)</code>.
	 * 
	 * @param x1Arg the x-coordinate of the first point for the distance calculation
	 * @param y1Arg the y-coordinate of the first point for the distance calculation
	 * @param x2Arg the x-coordinate of the second point for the distance calculation
	 * @param y2Arg the y-coordinate of the second point for the distance calculation
	 * @return the Euclidean distance between the two points
	 */
	public static float distance(float x1Arg, float y1Arg, float x2Arg, float y2Arg){
		return (float)Math.sqrt(Math.pow(x1Arg - x2Arg, 2) + Math.pow(y1Arg - y2Arg, 2));
	}

	/**
	 * Returns the Euclidean distance between the points specified by <code>c1Arg</code> and <code>c2Arg</code>.
	 * 
	 * @param c1Arg the first point for the distance calculation
	 * @param c2Arg the second point for the distance calculation
	 * @return the Euclidean distance between the two points
	 */
	public static float distance(HvlCoord c1Arg, HvlCoord c2Arg){
		return distance(c1Arg.x, c1Arg.y, c2Arg.x, c2Arg.y);
	}

	/**
	 * Returns the Euclidean distance between the point specified by <code>cArg</code> and the infinite line formed by
	 * <code>lStartArg</code> and <code>lEndArg</code>. This method effectively projects the line to infinity on both
	 * ends, and consequently is not restricted to the two endpoints supplied as arguments.
	 * 
	 * <p>
	 * 
	 * Algorithm source: <a href="http://paulbourke.net/geometry/pointlineplane/">http://paulbourke.net/geometry/pointlineplane/</a>
	 * 
	 * @param c1Arg the point for the distance calculation
	 * @param lStartArg the first reference point for the infinite line
	 * @param lEndArg the second reference point for the infinite line
	 * @return the Euclidean distance between the point and the infinite line
	 */
	public static float distance(HvlCoord cArg, HvlCoord lStartArg, HvlCoord lEndArg){
		HvlCoord delta = new HvlCoord(lEndArg).subtract(lStartArg);
		float i = ((cArg.x - lStartArg.x) * delta.x + (cArg.y - lStartArg.y) * delta.y) / (delta.x * delta.x + delta.y * delta.y);
		return distance(cArg.x, cArg.y, lStartArg.x + (i * delta.x), lStartArg.y + (i * delta.y));
	}

	/**
	 * Returns the angle (in degrees) from <code>(x1Arg, y1Arg)</code> to <code>(x2Arg, y2Arg)</code>.
	 * The output of this method is constrained by <code>-180f</code> and <code>180f</code>.
	 * 
	 * @param x1Arg "from" x-coordinate for the angle calculation
	 * @param y1Arg "from" y-coordinate for the angle calculation
	 * @param x2Arg "to" x-coordinate for the angle calculation
	 * @param y2Arg "to" y-coordinate for the angle calculation
	 * @return the angle (in degrees) between the two coordinates
	 */
	public static float angle(float x1Arg, float y1Arg, float x2Arg, float y2Arg){
		return toDegrees((float)Math.atan2(y2Arg - y1Arg, x2Arg - x1Arg));
	}

	/**
	 * Returns the angle (in degrees) from <code>c1Arg</code> to <code>c2Arg</code>. The output of this
	 * method is constrained by <code>-180f</code> and <code>180f</code>.
	 * 
	 * @param c1Arg "from" coordinate for the angle calculation
	 * @param c2Arg "to" coordinate for the angle calculation
	 * @return the angle (in degrees) between the two coordinates
	 */
	public static float angle(HvlCoord c1Arg, HvlCoord c2Arg){
		return angle(c1Arg.x, c1Arg.y, c2Arg.x, c2Arg.y);
	}

	/**
	 * Returns the intersection of the two line segments represented by <code>(l1x1Arg, l1y1Arg)</code> to <code>(l1x2Arg, l1y2Arg)</code>
	 * and <code>(l2x1Arg, l2y1Arg)</code> to <code>(l2x2Arg, l2y2Arg)</code>, if an intersection exists. If no intersection between the two
	 * segments exists, this method will return <code>null</code>. This method will also return an intersection if the intersection occurs at the
	 * endpoint of one or more segments. In the event that the intersection represents an entire line segment (the start and end coordinates
	 * of the segment are the same) or the intersection can be represented by multiple points, this method will return <code>null</code>.
	 * 
	 * @param l1x1Arg the x-coordinate of the start point for the first line segment
	 * @param l1y1Arg the y-coordinate of the start point for the first line segment
	 * @param l1x2Arg the x-coordinate of the end point for the first line segment
	 * @param l1y2Arg the y-coordinate of the end point for the first line segment
	 * @param l2x1Arg the x-coordinate of the start point for the second line segment
	 * @param l2y1Arg the y-coordinate of the start point for the second line segment
	 * @param l2x2Arg the x-coordinate of the end point for the second line segment
	 * @param l2y2Arg the y-coordinate of the end point for the second line segment
	 * @return the intersection between the two line segments, if it exists
	 */
	public static HvlCoord intersection(float l1x1Arg, float l1y1Arg, float l1x2Arg, float l1y2Arg, float l2x1Arg, float l2y1Arg, float l2x2Arg, float l2y2Arg){
		float d1x = l1x2Arg - l1x1Arg;
		float d1y = l1y2Arg - l1y1Arg;
		float d2x = l2x2Arg - l2x1Arg;
		float d2y = l2y2Arg - l2y1Arg;

		float i1 = (-d1y * (l1x1Arg - l2x1Arg) + d1x * (l1y1Arg - l2y1Arg)) / (-d2x * d1y + d1x * d2y);
		float i2 = (d2x * (l1y1Arg - l2y1Arg) - d2y * (l1x1Arg - l2x1Arg)) / (-d2x * d1y + d1x * d2y);

		if(i1 >= 0 && i1 <= 1 && i2 >= 0 && i2 <= 1){
			return new HvlCoord(l1x1Arg + (i2  * d1x), l1y1Arg + (i2  * d1y));
		}else return null;
	}

	/**
	 * Returns the intersection of the two line segments represented by <code>l1StartArg</code> to <code>l1EndArg</code>
	 * and <code>l2StartArg</code> to <code>l2EndArg</code>, if an intersection exists. If no intersection between the two
	 * segments exists, this method will return <code>null</code>. This method will also return an intersection if the intersection occurs at the
	 * endpoint of one or more segments. In the event that the intersection represents an entire line segment (the start and end coordinates
	 * of the segment are the same) or the intersection can be represented by multiple points, this method will return <code>null</code>.
	 * 
	 * @param l1StartArg the start point for the first line segment
	 * @param l1EndArg the end point for the first line segment
	 * @param l2StartArg the start point for the second line segment
	 * @param l2EndArg the end point for the second line segment
	 * @return the intersection between the two line segments, if it exists
	 */
	public static HvlCoord intersection(HvlCoord l1StartArg, HvlCoord l1EndArg, HvlCoord l2StartArg, HvlCoord l2EndArg){
		return intersection(l1StartArg.x, l1StartArg.y, l1EndArg.x, l1EndArg.y, l2StartArg.x, l2StartArg.y, l2EndArg.x, l2EndArg.y);
	}

	/**
	 * The <code>float</code> equivalent of {@linkplain Math#toDegrees(double)}.
	 * 
	 * @param radiansArg the value to be converted to degrees
	 * @return the equivalent of <code>radiansArg</code> in degrees
	 */
	public static float toDegrees(float radiansArg){
		return radiansArg * 180f / PI;
	}

	/**
	 * The <code>float</code> equivalent of {@linkplain Math#toRadians(double)}.
	 * 
	 * @param degreesArg the value to be converted to radians
	 * @return the equivalent of <code>degreesArg</code> in radians
	 */
	public static float toRadians(float degreesArg){
		return degreesArg * PI / 180f;
	}

}
