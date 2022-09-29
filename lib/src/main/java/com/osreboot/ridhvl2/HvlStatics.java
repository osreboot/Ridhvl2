package com.osreboot.ridhvl2;

import java.util.HashMap;

import com.osreboot.ridhvl2.loader.HvlLoader;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlEnvironmentVolatile;
import com.osreboot.ridhvl2.menu.HvlFont;
import com.osreboot.ridhvl2.painter.HvlCircle;
import com.osreboot.ridhvl2.painter.HvlPaint;
import com.osreboot.ridhvl2.painter.HvlPainter;
import com.osreboot.ridhvl2.painter.HvlPolygon;
import com.osreboot.ridhvl2.painter.HvlQuad;

/**
 * A collection of static methods that grant easy access to the most common Ridhvl2 operations. This class
 * should be statically imported, and is the only class in Ridhvl2 that is designed to be. All methods in this
 * class are designed to be as efficient as possible and have a minimal code profile.
 * 
 * @author os_reboot
 *
 */
public final class HvlStatics {

	private HvlStatics(){}

	//========================\/\/\/   BEGIN POLYGON STATICS   \/\/\/========================//

	private static HvlQuad globalQuad;
	private static HashMap<Integer, HvlCircle> globalCircles;

	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} with the origin of the
	 * quad (specified by <code>x</code> and <code>y</code>) being the upper-left corner of the quad. See 
	 * {@linkplain #hvlQuadc(float, float, float, float)} for other HvlQuad options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param x the x-origin of the quad
	 * @param y the y-origin of the quad
	 * @param xl the x-size of the quad
	 * @param yl the y-size of the quad
	 * @return an instance of HvlQuad with coordinates set to the specified values
	 */
	public static HvlQuad hvlQuad(float x, float y, float xl, float yl){
		if(globalQuad == null){
			globalQuad = new HvlQuad(
					new HvlCoord(x, y), new HvlCoord(x + xl, y), 
					new HvlCoord(x + xl, y + yl), new HvlCoord(x, y + yl)
					);
		}else{
			globalQuad.getVertices()[0].set(x, y);
			globalQuad.getVertices()[1].set(x + xl, y);
			globalQuad.getVertices()[2].set(x + xl, y + yl);
			globalQuad.getVertices()[3].set(x, y + yl);
			globalQuad.setUVs(HvlQuad.COORDS_DEFAULT_UVS[0], HvlQuad.COORDS_DEFAULT_UVS[1], 
					HvlQuad.COORDS_DEFAULT_UVS[2], HvlQuad.COORDS_DEFAULT_UVS[3]);
		}
		return globalQuad;
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} with the origin of the
	 * quad (specified by <code>c</code>) being the upper-left corner of the quad. See 
	 * {@linkplain #hvlQuadc(float, float, float, float)} for other HvlQuad options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param c the origin of the quad
	 * @param xl the x-size of the quad
	 * @param yl the y-size of the quad
	 * @return an instance of HvlQuad with coordinates set to the specified values
	 */
	public static HvlQuad hvlQuad(HvlCoord c, float xl, float yl){
		return hvlQuad(c.x, c.y, xl, yl);
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} with the origin of the
	 * quad (specified by <code>x</code> and <code>y</code>) being the upper-left corner of the quad. See 
	 * {@linkplain #hvlQuadc(float, float, float, float)} for other HvlQuad options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param x the x-origin of the quad
	 * @param y the y-origin of the quad
	 * @param c the size of the quad
	 * @return an instance of HvlQuad with coordinates set to the specified values
	 */
	public static HvlQuad hvlQuad(float x, float y, HvlCoord c){
		return hvlQuad(x, y, c.x, c.y);
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} with the origin of the
	 * quad (specified by <code>c1</code>) being the upper-left corner of the quad. See 
	 * {@linkplain #hvlQuadc(float, float, float, float)} for other HvlQuad options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param c1 the origin of the quad
	 * @param c2 the size of the quad
	 * @return an instance of HvlQuad with coordinates set to the specified values
	 */
	public static HvlQuad hvlQuad(HvlCoord c1, HvlCoord c2){
		return hvlQuad(c1.x, c1.y, c2.x, c2.y);
	}

	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} with the origin of the
	 * quad (specified by <code>x</code> and <code>y</code>) being the center of the quad. See 
	 * {@linkplain #hvlQuad(float, float, float, float)} for other HvlQuad options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param x the x-origin of the quad
	 * @param y the y-origin of the quad
	 * @param xl the x-size of the quad
	 * @param yl the y-size of the quad
	 * @return an instance of HvlQuad with coordinates set to the specified values
	 */
	public static HvlQuad hvlQuadc(float x, float y, float xl, float yl){
		if(globalQuad == null){
			globalQuad = new HvlQuad(
					new HvlCoord(x - (xl/2), y - (yl/2)), new HvlCoord(x + (xl/2), y - (yl/2)), 
					new HvlCoord(x + (xl/2), y + (yl/2)), new HvlCoord(x - (xl/2), y + (yl/2))
					);
		}else{
			globalQuad.getVertices()[0].set(x - (xl/2), y - (yl/2));
			globalQuad.getVertices()[1].set(x + (xl/2), y - (yl/2));
			globalQuad.getVertices()[2].set(x + (xl/2), y + (yl/2));
			globalQuad.getVertices()[3].set(x - (xl/2), y + (yl/2));
			globalQuad.setUVs(HvlQuad.COORDS_DEFAULT_UVS[0], HvlQuad.COORDS_DEFAULT_UVS[1], 
					HvlQuad.COORDS_DEFAULT_UVS[2], HvlQuad.COORDS_DEFAULT_UVS[3]);
		}
		return globalQuad;
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} with the origin of the
	 * quad (specified by <code>c</code>) being the center of the quad. See 
	 * {@linkplain #hvlQuad(float, float, float, float)} for other HvlQuad options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param c the origin of the quad
	 * @param xl the x-size of the quad
	 * @param yl the y-size of the quad
	 * @return an instance of HvlQuad with coordinates set to the specified values
	 */
	public static HvlQuad hvlQuadc(HvlCoord c, float xl, float yl){
		return hvlQuadc(c.x, c.y, xl, yl);
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} with the origin of the
	 * quad (specified by <code>x</code> and <code>y</code>) being the center of the quad. See 
	 * {@linkplain #hvlQuad(float, float, float, float)} for other HvlQuad options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param x the x-origin of the quad
	 * @param y the y-origin of the quad
	 * @param c the size of the quad
	 * @return an instance of HvlQuad with coordinates set to the specified values
	 */
	public static HvlQuad hvlQuadc(float x, float y, HvlCoord c){
		return hvlQuadc(x, y, c.x, c.y);
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} with the origin of the
	 * quad (specified by <code>c1</code>) being the center of the quad. See 
	 * {@linkplain #hvlQuad(float, float, float, float)} for other HvlQuad options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param c1 the origin of the quad
	 * @param c2 the size of the quad
	 * @return an instance of HvlQuad with coordinates set to the specified values
	 */
	public static HvlQuad hvlQuadc(HvlCoord c1, HvlCoord c2){
		return hvlQuadc(c1.x, c1.y, c2.x, c2.y);
	}

	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} with the origin of the
	 * quad (specified by <code>x</code> and <code>y</code>) being the upper-left corner of the quad. See 
	 * {@linkplain #hvlQuad(float, float, float, float)} for other HvlQuad options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param x the x-origin of the quad
	 * @param y the y-origin of the quad
	 * @param xl the x-size of the quad
	 * @param yl the y-size of the quad
	 * @param u0 the x-coordinate of the upper-left texture vertex
	 * @param v0 the y-coordinate of the upper-left texture vertex
	 * @param u1 the x-coordinate of the bottom-right texture vertex
	 * @param v1 the y-coordinate of the bottom-right texture vertex
	 * @return an instance of HvlQuad with coordinates set to the specified values
	 */
	public static HvlQuad hvlQuad(float x, float y, float xl, float yl, float u0, float v0, float u1, float v1){
		if(globalQuad == null){
			globalQuad = new HvlQuad(
					new HvlCoord(x, y), new HvlCoord(x + xl, y), 
					new HvlCoord(x + xl, y + yl), new HvlCoord(x, y + yl),
					new HvlCoord(u0, v0), new HvlCoord(u1, v0), 
					new HvlCoord(u1, v1), new HvlCoord(u0, v1));
		}else{
			globalQuad.getVertices()[0].set(x, y);
			globalQuad.getVertices()[1].set(x + xl, y);
			globalQuad.getVertices()[2].set(x + xl, y + yl);
			globalQuad.getVertices()[3].set(x, y + yl);
			globalQuad.getUVs()[0].set(u0, v0);
			globalQuad.getUVs()[1].set(u1, v0);
			globalQuad.getUVs()[2].set(u1, v1);
			globalQuad.getUVs()[3].set(u0, v1);
		}
		return globalQuad;
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlCircle HvlCircle} with the origin
	 * of the circle (specified by <code>x</code> and <code>y</code>) being the upper-left corner of the circle.
	 * This method uses the default HvlCircle vertex density, as specified by
	 * {@linkplain HvlCircle#DEFAULT_VERTEX_RESOLUTION}. See {@linkplain #hvlCirclec(float, float, float)} for
	 * other HvlCircle options.
	 * 
	 * <p>
	 * 
	 * NOTE: this method is <b>slow</b>! For better performance, use {@linkplain #hvlQuad(float, float, float, float)}
	 * combined with a {@linkplain HvlPaint} that has a circular alpha channel.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlCircle instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param x the x-origin of the circle
	 * @param y the y-origin of the circle
	 * @param radius the radius of the circle
	 * @return an instance of HvlCircle with coordinates set to the specified values
	 */
	public static HvlCircle hvlCircle(float x, float y, float radius){
		int vertexCount = Math.max((int)(2f * HvlMath.PI * radius * HvlCircle.DEFAULT_VERTEX_RESOLUTION), 3);
		if(globalCircles == null) globalCircles = new HashMap<>();
		if(!globalCircles.containsKey(vertexCount)){
			globalCircles.put(vertexCount, new HvlCircle(x + radius, y + radius, radius, vertexCount));
		}else{
			globalCircles.get(vertexCount).setVertices(x + radius, y + radius, radius);
			globalCircles.get(vertexCount).setUVs(0f, 0f, 1f, 1f);
		}
		return globalCircles.get(vertexCount);
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlCircle HvlCircle} with the origin
	 * of the circle (specified by <code>c</code>) being the upper-left corner of the circle.
	 * This method uses the default HvlCircle vertex density, as specified by
	 * {@linkplain HvlCircle#DEFAULT_VERTEX_RESOLUTION}. See {@linkplain #hvlCirclec(float, float, float)} for
	 * other HvlCircle options.
	 * 
	 * <p>
	 * 
	 * NOTE: this method is <b>slow</b>! For better performance, use {@linkplain #hvlQuad(float, float, float, float)}
	 * combined with a {@linkplain HvlPaint} that has a circular alpha channel.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlCircle instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param c the origin of the circle
	 * @param radius the radius of the circle
	 * @return an instance of HvlCircle with coordinates set to the specified values
	 */
	public static HvlCircle hvlCircle(HvlCoord c, float radius){
		return hvlCircle(c.x, c.y, radius);
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlCircle HvlCircle} with the origin
	 * of the circle (specified by <code>x</code> and <code>y</code>) being the center of the circle. This method
	 * uses the default HvlCircle vertex density, as specified by {@linkplain HvlCircle#DEFAULT_VERTEX_RESOLUTION}.
	 * See {@linkplain #hvlCircle(float, float, float)} for other HvlCircle options.
	 * 
	 * <p>
	 * 
	 * NOTE: this method is <b>slow</b>! For better performance, use {@linkplain #hvlQuad(float, float, float, float)}
	 * combined with a {@linkplain HvlPaint} that has a circular alpha channel.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlCircle instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param x the x-origin of the circle
	 * @param y the y-origin of the circle
	 * @param radius the radius of the circle
	 * @return an instance of HvlCircle with coordinates set to the specified values
	 */
	public static HvlCircle hvlCirclec(float x, float y, float radius){
		int vertexCount = Math.max((int)(2f * HvlMath.PI * radius * HvlCircle.DEFAULT_VERTEX_RESOLUTION), 3);
		if(globalCircles == null) globalCircles = new HashMap<>();
		if(!globalCircles.containsKey(vertexCount)){
			globalCircles.put(vertexCount, new HvlCircle(x, y, radius, vertexCount));
		}else{
			globalCircles.get(vertexCount).setVertices(x, y, radius * 2f, radius * 2f);
			globalCircles.get(vertexCount).setUVs(0f, 0f, 1f, 1f);
		}
		return globalCircles.get(vertexCount);
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlCircle HvlCircle} with the origin
	 * of the circle (specified by <code>c</code>) being the center of the circle. This method
	 * uses the default HvlCircle vertex density, as specified by {@linkplain HvlCircle#DEFAULT_VERTEX_RESOLUTION}.
	 * See {@linkplain #hvlCircle(float, float, float)} for other HvlCircle options.
	 * 
	 * <p>
	 * 
	 * NOTE: this method is <b>slow</b>! For better performance, use {@linkplain #hvlQuad(float, float, float, float)}
	 * combined with a {@linkplain HvlPaint} that has a circular alpha channel.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlCircle instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param c the origin of the circle
	 * @param radius the radius of the circle
	 * @return an instance of HvlCircle with coordinates set to the specified values
	 */
	public static HvlCircle hvlCirclec(HvlCoord c, float radius){
		return hvlCirclec(c.x, c.y, radius);
	}

	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlCircle HvlCircle} with the origin
	 * of the circle (specified by <code>x</code> and <code>y</code>) being the upper-left corner of the circle.
	 * The circle instance will have the number of vertices specified by <code>vertexCount</code>. See
	 * {@linkplain #hvlCircle(float, float, float)} for other HvlCircle options.
	 * 
	 * <p>
	 * 
	 * NOTE: this method is <b>slow</b>! For better performance, use {@linkplain #hvlQuad(float, float, float, float)}
	 * combined with a {@linkplain HvlPaint} that has a circular alpha channel.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlCircle instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param x the x-origin of the circle
	 * @param y the y-origin of the circle
	 * @param radius the radius of the circle
	 * @param vertexCount the number of vertices to comprise the circle
	 * @return an instance of HvlCircle with coordinates set to the specified values
	 */
	public static HvlCircle hvlCircle(float x, float y, float radius, int vertexCount){
		if(globalCircles == null) globalCircles = new HashMap<>();
		if(!globalCircles.containsKey(vertexCount)){
			globalCircles.put(vertexCount, new HvlCircle(x + radius, y + radius, radius, vertexCount));
		}else{
			globalCircles.get(vertexCount).setVertices(x + radius, y + radius, radius);
			globalCircles.get(vertexCount).setUVs(0f, 0f, 1f, 1f);
		}
		return globalCircles.get(vertexCount);
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlCircle HvlCircle} with the origin
	 * of the circle (specified by <code>c</code>) being the upper-left corner of the circle.
	 * The circle instance will have the number of vertices specified by <code>vertexCount</code>. See
	 * {@linkplain #hvlCircle(float, float, float)} for other HvlCircle options.
	 * 
	 * <p>
	 * 
	 * NOTE: this method is <b>slow</b>! For better performance, use {@linkplain #hvlQuad(float, float, float, float)}
	 * combined with a {@linkplain HvlPaint} that has a circular alpha channel.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlCircle instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param c the origin of the circle
	 * @param radius the radius of the circle
	 * @param vertexCount the number of vertices to comprise the circle
	 * @return an instance of HvlCircle with coordinates set to the specified values
	 */
	public static HvlCircle hvlCircle(HvlCoord c, float radius, int vertexCount){
		return hvlCircle(c.x, c.y, radius, vertexCount);
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlCircle HvlCircle} with the origin
	 * of the circle (specified by <code>x</code> and <code>y</code>) being the center of the circle. The
	 * circle instance will have the number of vertices specified by <code>vertexCount</code>. See 
	 * {@linkplain #hvlCircle(float, float, float)} for other HvlCircle options.
	 * 
	 * <p>
	 * 
	 * NOTE: this method is <b>slow</b>! For better performance, use {@linkplain #hvlQuad(float, float, float, float)}
	 * combined with a {@linkplain HvlPaint} that has a circular alpha channel.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlCircle instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param x the x-origin of the circle
	 * @param y the y-origin of the circle
	 * @param radius the radius of the circle
	 * @param vertexCount the number of vertices to comprise the circle
	 * @return an instance of HvlCircle with coordinates set to the specified values
	 */
	public static HvlCircle hvlCirclec(float x, float y, float radius, int vertexCount){
		if(globalCircles == null) globalCircles = new HashMap<>();
		if(!globalCircles.containsKey(vertexCount)){
			globalCircles.put(vertexCount, new HvlCircle(x, y, radius, vertexCount));
		}else{
			globalCircles.get(vertexCount).setVertices(x, y, radius * 2f, radius * 2f);
			globalCircles.get(vertexCount).setUVs(0f, 0f, 1f, 1f);
		}
		return globalCircles.get(vertexCount);
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlCircle HvlCircle} with the origin
	 * of the circle (specified by <code>c</code>) being the center of the circle. The
	 * circle instance will have the number of vertices specified by <code>vertexCount</code>. See 
	 * {@linkplain #hvlCircle(float, float, float)} for other HvlCircle options.
	 * 
	 * <p>
	 * 
	 * NOTE: this method is <b>slow</b>! For better performance, use {@linkplain #hvlQuad(float, float, float, float)}
	 * combined with a {@linkplain HvlPaint} that has a circular alpha channel.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlCircle instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param c the origin of the circle
	 * @param radius the radius of the circle
	 * @param vertexCount the number of vertices to comprise the circle
	 * @return an instance of HvlCircle with coordinates set to the specified values
	 */
	public static HvlCircle hvlCirclec(HvlCoord c, float radius, int vertexCount){
		return hvlCirclec(c.x, c.y, radius, vertexCount);
	}

	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} as a representation of
	 * the line between <code>(x1, y1)</code> and <code>(x2, y2)</code> with the width <code>width</code>. This
	 * method simulates line endpoint caps, so the real width of the (rotation-independent) HvlQuad will be
	 * <code>abs(x2 - x1) + width</code>. See {@linkplain #hvlQuad(float, float, float, float)} for other HvlQuad
	 * options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param x1 the x-coordinate of the start coordinate of the line
	 * @param y1 the y-coordinate of the start coordinate of the line
	 * @param x2 the x-coordinate of the end coordinate of the line
	 * @param y2 the y-coordinate of the end coordinate of the line
	 * @param width the width of the line
	 * @return an instance of HvlQuad with coordinates set to a representation of the line produced from the specified values
	 */
	public static HvlQuad hvlLine(float x1, float y1, float x2, float y2, float width){
		float angleRadians = HvlMath.toRadians(HvlMath.angle(x1, y1, x2, y2));
		float cosHalfWidth = width * 0.5f * (float)Math.cos(angleRadians);
		float sinHalfWidth = width * 0.5f * (float)Math.sin(angleRadians);
		if(globalQuad == null){
			globalQuad = new HvlQuad(
					new HvlCoord(-cosHalfWidth + sinHalfWidth + x1, -sinHalfWidth - cosHalfWidth + y1),
					new HvlCoord(cosHalfWidth + sinHalfWidth + x2, sinHalfWidth - cosHalfWidth + y2), 
					new HvlCoord(cosHalfWidth - sinHalfWidth + x2, sinHalfWidth + cosHalfWidth + y2),
					new HvlCoord(-cosHalfWidth - sinHalfWidth + x1, -sinHalfWidth + cosHalfWidth + y1)
					);
		}else{
			globalQuad.getVertices()[0].set(-cosHalfWidth + sinHalfWidth + x1, -sinHalfWidth - cosHalfWidth + y1);
			globalQuad.getVertices()[1].set(cosHalfWidth + sinHalfWidth + x2, sinHalfWidth - cosHalfWidth + y2);
			globalQuad.getVertices()[2].set(cosHalfWidth - sinHalfWidth + x2, sinHalfWidth + cosHalfWidth + y2);
			globalQuad.getVertices()[3].set(-cosHalfWidth - sinHalfWidth + x1, -sinHalfWidth + cosHalfWidth + y1);
			globalQuad.setUVs(HvlQuad.COORDS_DEFAULT_UVS[0], HvlQuad.COORDS_DEFAULT_UVS[1], 
					HvlQuad.COORDS_DEFAULT_UVS[2], HvlQuad.COORDS_DEFAULT_UVS[3]);
		}
		return globalQuad;
	}
	
	/**
	 * Produces an instance of {@linkplain com.osreboot.ridhvl2.painter.HvlQuad HvlQuad} as a representation of
	 * the line between <code>c1</code> and <code>c2</code> with the width <code>width</code>. This method simulates
	 * line endpoint caps, so the real width of the (rotation-independent) HvlQuad will be
	 * <code>abs(x2 - x1) + width</code>. See {@linkplain #hvlQuad(float, float, float, float)} for other HvlQuad options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlQuad instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPolygon values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param c1 the start coordinate of the line
	 * @param c2 the end coordinate of the line
	 * @param width the width of the line
	 * @return an instance of HvlQuad with coordinates set to a representation of the line produced from the specified values
	 */
	public static HvlQuad hvlLine(HvlCoord c1, HvlCoord c2, float width){
		return hvlLine(c1.x, c1.y, c2.x, c2.y, width);
	}

	//========================/\/\/\    END POLYGON STATICS    /\/\/\========================//

	//========================\/\/\/   BEGIN PAINTER STATICS   \/\/\/========================//

	private static HvlColor globalColor;

	/**
	 * Produces an instance of {@linkplain HvlColor} with <code>r</code>, <code>g</code> 
	 * and <code>b</code> values specified by <code>rArg</code>, <code>gArg</code> and <code>bArg</code>, 
	 * respectively. See {@linkplain #hvlColor(float, float, float, float)}, 
	 * {@linkplain #hvlColor(float, float)} for other HvlColor options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlColor instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlColor values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param rArg the red value of the color
	 * @param gArg the green value of the color
	 * @param bArg the blue value of the color
	 * @return an instance of HvlColor set to the specified values
	 */
	public static HvlColor hvlColor(float rArg, float gArg, float bArg){
		if(globalColor == null) globalColor = new HvlColor(rArg, gArg, bArg);
		else{
			globalColor.r = rArg;
			globalColor.g = gArg;
			globalColor.b = bArg;
			globalColor.a = 1f;
		}
		return globalColor;
	}

	/**
	 * Produces an instance of {@linkplain HvlColor} with <code>r</code>, <code>g</code>,
	 * <code>b</code> and <code>a</code> values specified by <code>rArg</code>, <code>gArg</code>, 
	 * <code>bArg</code> and <code>aArg</code>, respectively. See {@linkplain #hvlColor(float, float, float)}, 
	 * {@linkplain #hvlColor(float, float)} for other HvlColor options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlColor instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlColor values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param rArg the red value of the color
	 * @param gArg the green value of the color
	 * @param bArg the blue value of the color
	 * @param aArg the alpha value of the color
	 * @return an instance of HvlColor set to the specified values
	 */
	public static HvlColor hvlColor(float rArg, float gArg, float bArg, float aArg){
		if(globalColor == null) globalColor = new HvlColor(rArg, gArg, bArg, aArg);
		else{
			globalColor.r = rArg;
			globalColor.g = gArg;
			globalColor.b = bArg;
			globalColor.a = aArg;
		}
		return globalColor;
	}

	/**
	 * Produces an instance of {@linkplain HvlColor} with <code>r</code>, <code>g</code>
	 * and <code>b</code> values specified by <code>vArg</code> ("value"; for grayscale colors), and an
	 * <code>a</code> value specified by <code>aArg</code>. See {@linkplain #hvlColor(float, float, float)}, 
	 * {@linkplain #hvlColor(float, float, float, float)} for other HvlColor options.
	 * 
	 * <p>
	 * 
	 * This method re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to create their own HvlColor instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlColor values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param vArg the value of the color
	 * @param aArg the alpha value of the color
	 * @return an instance of HvlColor set to the specified values
	 */
	public static HvlColor hvlColor(float vArg, float aArg){
		if(globalColor == null) globalColor = new HvlColor(vArg, vArg, vArg, aArg);
		else{
			globalColor.r = vArg;
			globalColor.g = vArg;
			globalColor.b = vArg;
			globalColor.a = aArg;
		}
		return globalColor;
	}

	private static HvlPaint globalPaint;

	/**
	 * Casts <code>colorArg</code> to a {@linkplain HvlPaint} and then equivalently draws <code>polygonArg</code>
	 * with said HvlPaint. Uses the same drawing procedure from 
	 * {@linkplain com.osreboot.ridhvl2.painter.HvlPainter HvlPainter}.
	 * 
	 * @param polygonArg the polygon to draw
	 * @param colorArg the paint to use for <code>polygonArg</code>
	 */
	public static void hvlDraw(HvlPolygon polygonArg, HvlColor colorArg){
		if(globalPaint == null) globalPaint = new HvlPaint(colorArg);
		else globalPaint.setValue(colorArg);
		HvlPainter.draw(polygonArg, globalPaint);
	}

	/**
	 * Casts <code>textureArg</code> to a {@linkplain HvlPaint} and then equivalently draws <code>polygonArg</code>
	 * with said HvlPaint. Uses the same drawing procedure from 
	 * {@linkplain com.osreboot.ridhvl2.painter.HvlPainter HvlPainter}.
	 * 
	 * @param polygonArg the polygon to draw
	 * @param textureArg the paint to use for <code>polygonArg</code>
	 */
	public static void hvlDraw(HvlPolygon polygonArg, HvlTexture textureArg){
		if(globalPaint == null) globalPaint = new HvlPaint(textureArg);
		else globalPaint.setValue(textureArg);
		HvlPainter.draw(polygonArg, globalPaint);
	}

	/**
	 * Casts <code>textureArg</code> and <code>colorArg</code> to a {@linkplain HvlPaint} and then equivalently 
	 * draws <code>polygonArg</code> with said HvlPaint. Uses the same drawing procedure from 
	 * {@linkplain com.osreboot.ridhvl2.painter.HvlPainter HvlPainter}.
	 * 
	 * @param polygonArg the polygon to draw
	 * @param textureArg the texture of the paint to use for <code>polygonArg</code>
	 * @param colorArg the color of the paint to use for <code>polygonArg</code>
	 */
	public static void hvlDraw(HvlPolygon polygonArg, HvlTexture textureArg, HvlColor colorArg){
		if(globalPaint == null) globalPaint = new HvlPaint(colorArg, textureArg);
		else globalPaint.setValue(colorArg, textureArg);
		HvlPainter.draw(polygonArg, globalPaint);
	}

	/**
	 * Applies a translation transformation to the body of <code>actionArg</code>, with <code>xArg</code> and
	 * <code>yArg</code> being the offset of the translation. Uses the same translation procedure from
	 * {@linkplain HvlPainter}.
	 * 
	 * @param xArg the x-offset of the translation
	 * @param yArg the y-offset of the translation
	 * @param actionArg the context that the translation is applied to
	 */
	public static void hvlTranslate(float xArg, float yArg, HvlAction.A0 actionArg){
		HvlPainter.translate(xArg, yArg, actionArg);
	}
	
	/**
	 * Applies a translation transformation to the body of <code>actionArg</code>, with <code>cArg</code>
	 * being the offset of the translation. Uses the same translation procedure from
	 * {@linkplain HvlPainter}.
	 * 
	 * @param cArg the coordinate offset of the translation
	 * @param actionArg the context that the translation is applied to
	 */
	public static void hvlTranslate(HvlCoord cArg, HvlAction.A0 actionArg){
		HvlPainter.translate(cArg.x, cArg.y, actionArg);
	}

	/**
	 * Applies a rotation transformation to the body of <code>actionArg</code>, with <code>xArg</code> and
	 * <code>yArg</code> being the origin of the rotation, and <code>degreesArg</code> being the magnitude of
	 * the rotation, in degrees. Uses the same rotation procedure from {@linkplain HvlPainter}.
	 * 
	 * @param xArg the x-origin of the rotation
	 * @param yArg the y-origin of the rotation
	 * @param degreesArg the magnitude of the rotation
	 * @param actionArg the context that the rotation is applied to
	 */
	public static void hvlRotate(float xArg, float yArg, float degreesArg, HvlAction.A0 actionArg){
		HvlPainter.rotate(xArg, yArg, degreesArg, actionArg);
	}
	
	/**
	 * Applies a rotation transformation to the body of <code>actionArg</code>, with <code>cArg</code>
	 * being the origin of the rotation, and <code>degreesArg</code> being the magnitude of
	 * the rotation, in degrees. Uses the same rotation procedure from {@linkplain HvlPainter}.
	 * 
	 * @param cArg the origin of the rotation
	 * @param degreesArg the magnitude of the rotation
	 * @param actionArg the context that the rotation is applied to
	 */
	public static void hvlRotate(HvlCoord cArg, float degreesArg, HvlAction.A0 actionArg){
		HvlPainter.rotate(cArg.x, cArg.y, degreesArg, actionArg);
	}
	
	/**
	 * Applies a scaling transformation to the body of <code>actionArg</code>, with <code>xArg</code> and
	 * <code>yArg</code> being the origin of the scale, and <code>scaleArg</code> being the magnitude of
	 * the scale. Uses the same scaling procedure from {@linkplain HvlPainter}.
	 * 
	 * @param xArg the x-origin of the scale
	 * @param yArg the y-origin of the scale
	 * @param scaleArg the magnitude of the scale
	 * @param actionArg the context that the scale is applied to
	 */
	public static void hvlScale(float xArg, float yArg, float scaleArg, HvlAction.A0 actionArg){
		HvlPainter.scale(xArg, yArg, scaleArg, actionArg);
	}
	
	/**
	 * Applies a scaling transformation to the body of <code>actionArg</code>, with <code>cArg</code>
	 * being the origin of the scale, and <code>scaleArg</code> being the magnitude of
	 * the scale. Uses the same scaling procedure from {@linkplain HvlPainter}.
	 * 
	 * @param cArg the origin of the scale
	 * @param scaleArg the magnitude of the scale
	 * @param actionArg the context that the scale is applied to
	 */
	public static void hvlScale(HvlCoord cArg, float scaleArg, HvlAction.A0 actionArg){
		HvlPainter.scale(cArg.x, cArg.y, scaleArg, actionArg);
	}

	//========================/\/\/\    END PAINTER STATICS    /\/\/\========================//

	//========================\/\/\/   BEGIN LOADER STATICS    \/\/\/========================//

	/**
	 * Loads the argument-specified resource (if a compatible {@linkplain HvlLoader} instance is available)
	 * and stores it in said HvlLoader. That resource can then be accessed by performing a search of available
	 * HvlLoader instances with 
	 * {@linkplain com.osreboot.ridhvl2.loader.HvlLoader#getLoaders() HvlLoader.getLoaders()} and finding the
	 * one with the appropriate <code>TYPELABEL</code> or by using one of the helper methods supplied in this 
	 * class, such as {@linkplain #hvlTexture(int)} (strongly recommended).
	 * 
	 * <p>
	 * 
	 * This method can be used to load any type of resource (textures, sounds, etc.). The <code>pathArg</code>
	 * MUST include the file name AND file extension. This version of the method assumes a default sub-path,
	 * determined by the value of 
	 * {@linkplain com.osreboot.ridhvl2.loader.HvlLoader#PATH_DEFAULT HvlLoader.PATH_DEFAULT} (usually "res\").
	 * 
	 * <p>
	 * 
	 * NOTE: the order of these calls matters! HvlLoader resources are referenced with the (integer value) order 
	 * in which they were loaded (this value resets for resources with different <code>TYPELABEL</code> values). 
	 * For example, if load calls are executed in the following order:
	 * 
	 * <p>
	 * 
	 * <code>hvlLoad("RIDHVL2.png"); hvlLoad("JordanKouncell.jpg"); hvlLoad("REEEE.wav");</code> 
	 * 
	 * <p>
	 * 
	 * Then the integers to reference the resources are as follows: 
	 * 
	 * <p>
	 * 
	 * <code>0</code> for <code>RIDHVL2.png</code>, <code>1</code> for <code>JordanKouncell.jpg</code> and 
	 * <code>0</code> for <code>REEEE.wav</code>.
	 * 
	 * @param pathArg the complete path (including file name and extension) of the resource to be loaded
	 * @return <code>true</code> if the load operation succeeded
	 */
	public static boolean hvlLoad(String pathArg){
		for(HvlLoader<?> l : HvlLoader.getLoaders()){
			if(l.canLoad(pathArg)){
				if(l.load(pathArg)){
					HvlLogger.println(HvlLoader.debug, HvlLoader.class, "Loaded \"" + pathArg + "\" with type label " + l.getTypeLabel() + ".");
					return true;
				}else{
					HvlLogger.println(HvlLoader.debug, HvlLoader.class, "Failed to load \"" + pathArg + "\"!");
					return false;
				}
			}
		}
		HvlLogger.println(HvlLoader.debug, HvlLoader.class, "Couldn't find a loader that can load \"" + pathArg + "\".");
		return false;
	}

	/**
	 * Fetches a {@linkplain org.newdawn.slick.opengl.Texture Texture} instance stored in an {@linkplain HvlLoader}.
	 * This resource must have been loaded previously. See {@linkplain #hvlLoad(String)} for more information on
	 * loading resources. Additionally, (and similarly to {@linkplain #hvlLoad(String)}) an HvlLoader instance
	 * with the specific <code>TYPELABEL</code> must be available for use and have said Texture loaded into it.
	 * 
	 * <p>
	 * 
	 * Texture instances are referenced by the order in which they were loaded (relative to other Texture instances). 
	 * Again see {@linkplain #hvlLoad(String)} for more specifics.
	 * 
	 * @param indexArg the index specifying the Texture instance to be fetched
	 * @return the fetched Texture instance, if it exists
	 */
	@SuppressWarnings("unchecked")
	public static HvlTexture hvlTexture(int indexArg){
		for(HvlLoader<?> l : HvlLoader.getLoaders()){
			if(l.getTypeLabel().equalsIgnoreCase(HvlLoader.TYPELABEL_TEXTURE)){
				return ((HvlLoader<HvlTexture>)l).get(indexArg);//TODO resolve this warning
			}
		}
		return null;
	}

	/**
	 * Fetches an {@linkplain HvlFont} instance stored in an {@linkplain HvlLoader}.
	 * This resource must have been loaded previously. See {@linkplain #hvlLoad(String)} for more information on
	 * loading resources. Additionally, (and similarly to {@linkplain #hvlLoad(String)}) an HvlLoader instance
	 * with the specific <code>TYPELABEL</code> must be available for use and have said HvlFont loaded into it.
	 * 
	 * <p>
	 * 
	 * HvlFont instances are referenced by the order in which they were loaded (relative to other HvlFont instances). 
	 * Again see {@linkplain #hvlLoad(String)} for more specifics.
	 * 
	 * @param indexArg the index specifying the HvlFont instance to be fetched
	 * @return the fetched HvlFont instance, if it exists
	 */
	@SuppressWarnings("unchecked")
	public static HvlFont hvlFont(int indexArg){
		for(HvlLoader<?> l : HvlLoader.getLoaders()){
			if(l.getTypeLabel().equalsIgnoreCase(HvlLoader.TYPELABEL_FONT)){
				return ((HvlLoader<HvlFont>)l).get(indexArg);//TODO resolve this warning
			}
		}
		return null;
	}

	//========================/\/\/\    END LOADER STATICS     /\/\/\========================//

	//========================\/\/\/    BEGIN MENU STATICS     \/\/\/========================//

	private static HvlEnvironmentVolatile globalEnvironment;

	@SuppressWarnings("deprecation")
	public static HvlEnvironment hvlEnvironment(float xArg, float yArg, float widthArg, float heightArg){
		if(globalEnvironment == null)
			globalEnvironment = new HvlEnvironmentVolatile(xArg, yArg, widthArg, heightArg, false);
		else globalEnvironment.setAndRefresh(xArg, yArg, widthArg, heightArg, false);
		return globalEnvironment;
	}

	@SuppressWarnings("deprecation")
	public static HvlEnvironment hvlEnvironment(float xArg, float yArg, float widthArg, float heightArg, boolean blockedArg){
		if(globalEnvironment == null)
			globalEnvironment = new HvlEnvironmentVolatile(xArg, yArg, widthArg, heightArg, blockedArg);
		else globalEnvironment.setAndRefresh(xArg, yArg, widthArg, heightArg, blockedArg);
		return globalEnvironment;
	}
	
	@SuppressWarnings("deprecation")
	public static HvlEnvironment hvlEnvironmentc(float xArg, float yArg, float widthArg, float heightArg){
		if(globalEnvironment == null)
			globalEnvironment = new HvlEnvironmentVolatile(xArg - (widthArg/2), yArg - (heightArg/2), widthArg, heightArg, false);
		else globalEnvironment.setAndRefresh(xArg - (widthArg/2), yArg - (heightArg/2), widthArg, heightArg, false);
		return globalEnvironment;
	}
	
	@SuppressWarnings("deprecation")
	public static HvlEnvironment hvlEnvironmentc(float xArg, float yArg, float widthArg, float heightArg, boolean blockedArg){
		if(globalEnvironment == null)
			globalEnvironment = new HvlEnvironmentVolatile(xArg - (widthArg/2), yArg - (heightArg/2), widthArg, heightArg, blockedArg);
		else globalEnvironment.setAndRefresh(xArg - (widthArg/2), yArg - (heightArg/2), widthArg, heightArg, blockedArg);
		return globalEnvironment;
	}

	//========================/\/\/\     END MENU STATICS      /\/\/\========================//

}
