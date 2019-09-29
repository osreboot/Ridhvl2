package com.osreboot.ridhvl2.painter;

import com.osreboot.ridhvl2.HvlCoord;

/**
 * A polygon in the form of an array of three or more vertex coordinates, and an equally sized array of UV 
 * coordinates. Coordinates are stored in the form of {@linkplain HvlCoord}s. After the constructor polygon vertex
 * counts cannot be changed, however the coordinate values of both vertices and UVs may be adjusted. HvlPolygon
 * objects are used to specify the drawing volume and texture UVs in {@linkplain HvlPainter} operations.
 * 
 * @author os_reboot
 *
 */
public class HvlPolygon {

	private HvlCoord[] vertices, uvs;
	
	/**
	 * Constructs a HvlPolygon from the given vertices and UVs. Both the vertex array and the UV array must have at
	 * least three elements, and be equal in size.
	 * 
	 * @param vArgs the vertices for the polygon
	 * @param uvArgs the UVs for the polygon
	 * @throws InvalidVertexCountException if either <code>vArgs</code> or <code>uvArgs</code> have less than three
	 * elements, or are not equal in size
	 */
	public HvlPolygon(HvlCoord[] vArgs, HvlCoord[] uvArgs){
		if(vArgs.length < 3 || uvArgs.length < 3 || vArgs.length != uvArgs.length) throw new InvalidVertexCountException();
		vertices = vArgs;
		uvs = uvArgs;
	}
	
	/**
	 * @return the HvlPolygon's vertex array
	 */
	public HvlCoord[] getVertices(){
		return vertices;
	}
	
	/**
	 * @return the HvlPolygon's UV array
	 */
	public HvlCoord[] getUVs(){
		return uvs;
	}
	
	/**
	 * Sets the vertices for the HvlPolygon. <code>vArgs</code> must be equal in size to the HvlPolygon's existing
	 * vertex array (see {@linkplain #getVertices()}).
	 * 
	 * @param vArgs the new vertex values for the HvlPolygon
	 * @throws InvalidVertexCountException if <code>vArgs</code> is not equal in size to the HvlPolygon's existing
	 * vertex array (see {@linkplain #getVertices()})
	 */
	public void setVertices(HvlCoord[] vArgs){
		if(vArgs.length < 3 || vArgs.length != vertices.length) throw new InvalidVertexCountException();
		vertices = vArgs;
	}
	
	/**
	 * A convenience alternative to {@linkplain #setVertices(HvlCoord[])} that doesn't require the user to combine
	 * {@linkplain HvlCoord} elements into an array.
	 * 
	 * @param v0Arg the first vertex for the HvlPolygon
	 * @param v1Arg the second vertex for the HvlPolygon
	 * @param v2Arg the third vertex for the HvlPolygon
	 * @param vArgs optional additional vertices for the HvlPolygon
	 * @throws InvalidVertexCountException if the sum of the supplied vertex arguments is not equal in size to the 
	 * HvlPolygon's existing vertex array (see {@linkplain #getVertices()})
	 */
	public void setVertices(HvlCoord v0Arg, HvlCoord v1Arg, HvlCoord v2Arg, HvlCoord... vArgs){
		if(vArgs.length + 3 != vertices.length) throw new InvalidVertexCountException();
		vertices[0] = v0Arg;
		vertices[1] = v1Arg;
		vertices[2] = v2Arg;
		for(int i = 0; i < vArgs.length; i++)
			vertices[3 + i] = vArgs[i];
	}
	
	/**
	 * Sets the UVs for the HvlPolygon. <code>uvArgs</code> must be equal in size to the HvlPolygon's existing UV 
	 * array (see {@linkplain #getUVs()}).
	 * 
	 * @param uvArgs the new UV values for the HvlPolygon
	 * @throws InvalidVertexCountException if <code>uvArgs</code> is not equal in size to the HvlPolygon's existing
	 * UV array (see {@linkplain #getUVs()})
	 */
	public void setUVs(HvlCoord[] uvArgs){
		if(uvArgs.length < 3 || uvArgs.length != uvs.length) throw new InvalidVertexCountException();
		uvs = uvArgs;
	}
	
	/**
	 * A convenience alternative to {@linkplain #setUVs(HvlCoord[])} that doesn't require the user to combine
	 * {@linkplain HvlCoord} elements into an array.
	 * 
	 * @param uv0Arg the first UV for the HvlPolygon
	 * @param uv1Arg the second UV for the HvlPolygon
	 * @param uv2Arg the third UV for the HvlPolygon
	 * @param uvArgs optional additional UVs for the HvlPolygon
	 * @throws InvalidVertexCountException if the sum of the supplied UV arguments is not equal in size to the 
	 * HvlPolygon's existing UV array (see {@linkplain #getUVs()})
	 */
	public void setUVs(HvlCoord uv0Arg, HvlCoord uv1Arg, HvlCoord uv2Arg, HvlCoord... uvArgs){
		if(uvArgs.length + 3 != uvs.length) throw new InvalidVertexCountException();
		uvs[0] = uv0Arg;
		uvs[1] = uv1Arg;
		uvs[2] = uv2Arg;
		for(int i = 0; i < uvArgs.length; i++)
			uvs[3 + i] = uvArgs[i];
	}
	
	/**
	 * Thrown if an attempt is made to supply fewer than three {@linkplain HvlCoord} arguments to any HvlPolygon
	 * set operation. Also thrown if an attempt is made to alter the vertex or UV coordinate count of an existing 
	 * HvlPolygon.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class InvalidVertexCountException extends RuntimeException{
		private static final long serialVersionUID = 5873291550044448526L;
	}
	
}
