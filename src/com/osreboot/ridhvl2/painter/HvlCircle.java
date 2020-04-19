package com.osreboot.ridhvl2.painter;

import com.osreboot.ridhvl2.HvlCoord;
import com.osreboot.ridhvl2.HvlMath;

/**
 * A pseudo-circular implementation of {@linkplain HvlPolygon}.
 * 
 * @author os_reboot
 *
 */
public final class HvlCircle extends HvlPolygon{
	
	/**
	 * The default vertex resolution (in vertices per circumference pixel) for unspecified circle polygons to use.
	 */
	public static final float DEFAULT_VERTEX_RESOLUTION = 0.25f;
	
	/**
	 * Creates an {@linkplain HvlCoord} array populated with new coordinate instances that fall on the boundary of
	 * the given circle. Useful for initializing circle polygons.
	 * 
	 * @param xCenterArg the x-origin of the circle
	 * @param yCenterArg the y-origin of the circle
	 * @param widthArg the width of the circle (x-axis diameter)
	 * @param heightArg the height of the circle (y-axis diameter)
	 * @param vertexCountArg the number of vertices to comprise the circle
	 * @return an array of new HvlCoord instances that fall on the boundary of the given circle
	 */
	public static HvlCoord[] createCircleVertices(float xCenterArg, float yCenterArg, float widthArg, float heightArg, int vertexCountArg){
		HvlCoord[] output = new HvlCoord[vertexCountArg];
		float circumference;
		for(int i = 0; i < vertexCountArg; i++){
			circumference = HvlMath.map(i, 0, vertexCountArg, 0f, 2 * HvlMath.PI);
			output[i] = new HvlCoord(((float)Math.cos(circumference) * widthArg * 0.5f) + xCenterArg, ((float)Math.sin(circumference) * heightArg * 0.5f) + yCenterArg);
		}
		return output;
	}
	
	/**
	 * Creates an {@linkplain HvlCoord} array populated with new coordinate instances that fall on the boundary of
	 * the given circle (similar to {@linkplain #createCircleVertices(float, float, float, float, int)} but with a
	 * different input format). Useful for initializing circle UVs.
	 * 
	 * @param xMinArg the lower x-axis boundary of the circle
	 * @param yMinArg the lower y-axis boundary of the circle
	 * @param xMaxArg the upper x-axis boundary of the circle
	 * @param yMaxArg the upper y-axis boundary of the circle
	 * @param vertexCountArg the number of vertices to comprise the circle
	 * @return an array of new HvlCoord instances that fall on the boundary of the given circle
	 */
	public static HvlCoord[] createCircleUVs(float xMinArg, float yMinArg, float xMaxArg, float yMaxArg, int vertexCountArg){
		HvlCoord[] output = new HvlCoord[vertexCountArg];
		float width = xMaxArg - xMinArg;
		float height = yMaxArg - yMinArg;
		float circumference;
		for(int i = 0; i < vertexCountArg; i++){
			circumference = HvlMath.map(i, 0, vertexCountArg, 0f, 2 * HvlMath.PI);
			output[i] = new HvlCoord(((float)Math.cos(circumference) * width * 0.5f) + xMinArg + (width / 2), ((float)Math.sin(circumference) * height * 0.5f) + yMinArg + (height / 2));
		}
		return output;
	}
	
	/**
	 * Constructs an instance of HvlCircle with the given origin, width, height and vertex count.
	 * 
	 * @param xCenterArg the x-origin of the circle
	 * @param yCenterArg the y-origin of the circle
	 * @param widthArg the width of the circle (x-axis diameter)
	 * @param heightArg the height of the circle (y-axis diameter)
	 * @param vertexCountArg the number of vertices to comprise the circle
	 */
	public HvlCircle(float xCenterArg, float yCenterArg, float widthArg, float heightArg, int vertexCountArg){
		super(createCircleVertices(xCenterArg, yCenterArg, widthArg, heightArg, vertexCountArg), createCircleUVs(0f, 0f, 1f, 1f, vertexCountArg));
	}
	
	/**
	 * Constructs an instance of HvlCircle with the given origin, width, height and vertex count.
	 * 
	 * @param xCenterArg the x-origin of the circle
	 * @param yCenterArg the y-origin of the circle
	 * @param radiusArg the radius of the circle
	 * @param vertexCountArg the number of vertices to comprise the circle
	 */
	public HvlCircle(float xCenterArg, float yCenterArg, float radiusArg, int vertexCountArg){
		this(xCenterArg, yCenterArg, radiusArg * 2f, radiusArg * 2f, vertexCountArg);
	}
	
	/**
	 * Constructs a HvlCircle with the vertices and UVs being equal to those of the supplied HvlCircle.
	 * 
	 * @param circleArg the HvlCircle to inherit coordinate values from
	 */
	public HvlCircle(HvlCircle circleArg){
		super(circleArg.getVertices(), circleArg.getUVs());
	}
	
	/**
	 * Sets the vertices of the HvlCircle.
	 * 
	 * @param xCenterArg the x-origin of the circle
	 * @param yCenterArg the y-origin of the circle
	 * @param widthArg the width of the circle (x-axis diameter)
	 * @param heightArg the height of the circle (y-axis diameter)
	 */
	public void setVertices(float xCenterArg, float yCenterArg, float widthArg, float heightArg){
		float circumference;
		for(int i = 0; i < getVertices().length; i++){
			circumference = HvlMath.map(i, 0, getVertices().length, 0f, 2 * HvlMath.PI);
			getVertices()[i] = new HvlCoord(((float)Math.cos(circumference) * widthArg * 0.5f) + xCenterArg, ((float)Math.sin(circumference) * heightArg * 0.5f) + yCenterArg);
		}
	}
	
	/**
	 * Sets the vertices of the HvlCircle.
	 * 
	 * @param xCenterArg the x-origin of the circle
	 * @param yCenterArg the y-origin of the circle
	 * @param radiusArg the radius of the circle
	 */
	public void setVertices(float xCenterArg, float yCenterArg, float radiusArg){
		setVertices(xCenterArg, yCenterArg, radiusArg * 2f, radiusArg * 2f);
	}
	
	/**
	 * Sets the UVs of the HvlCircle.
	 * 
	 * @param xMinArg the lower x-axis boundary of the circle
	 * @param yMinArg the lower y-axis boundary of the circle
	 * @param xMaxArg the upper x-axis boundary of the circle
	 * @param yMaxArg the upper y-axis boundary of the circle
	 */
	public void setUVs(float xMinArg, float yMinArg, float xMaxArg, float yMaxArg){
		float width = xMaxArg - xMinArg;
		float height = yMaxArg - yMinArg;
		float circumference;
		for(int i = 0; i < getUVs().length; i++){
			circumference = HvlMath.map(i, 0, getUVs().length, 0f, 2 * HvlMath.PI);
			getUVs()[i] = new HvlCoord(((float)Math.cos(circumference) * width * 0.5f) + xMinArg + (width / 2), ((float)Math.sin(circumference) * height * 0.5f) + yMinArg + (height / 2));
		}
	}

}
