package com.osreboot.ridhvl2.painter;

import com.osreboot.ridhvl2.HvlCoord;

/**
 * A four-vertex implementation of {@linkplain HvlPolygon}.
 * 
 * @author os_reboot
 *
 */
public final class HvlQuad extends HvlPolygon{

	/**
	 * Standard rotation-appropriate UV coordinate set that, when applied to an on-screen quad, draws an upright 
	 * texture.
	 */
	public static final HvlCoord[] COORDS_DEFAULT_UVS = new HvlCoord[]{
			new HvlCoord(0, 0),
			new HvlCoord(1, 0),
			new HvlCoord(1, 1),
			new HvlCoord(0, 1),
	};

	/**
	 * Constructs a HvlQuad from the given vertices with a default UV coordinate set (see 
	 * {@linkplain #COORDS_DEFAULT_UVS}).
	 * 
	 * @param v0Arg the first vertex for the HvlQuad
	 * @param v1Arg the second vertex for the HvlQuad
	 * @param v2Arg the third vertex for the HvlQuad
	 * @param v3Arg the fourth vertex for the HvlQuad
	 */
	public HvlQuad(HvlCoord v0Arg, HvlCoord v1Arg, HvlCoord v2Arg, HvlCoord v3Arg){
		super(new HvlCoord[]{v0Arg, v1Arg, v2Arg, v3Arg}, COORDS_DEFAULT_UVS);
	}

	/**
	 * Constructs a HvlQuad from the given vertices and UVs.
	 * 
	 * @param v0Arg the first vertex for the HvlQuad
	 * @param v1Arg the second vertex for the HvlQuad
	 * @param v2Arg the third vertex for the HvlQuad
	 * @param v3Arg the fourth vertex for the HvlQuad
	 * @param uv0Arg the first UV for the HvlQuad
	 * @param uv1Arg the second UV for the HvlQuad
	 * @param uv2Arg the third UV for the HvlQuad
	 * @param uv3Arg the fourth UV for the HvlQuad
	 */
	public HvlQuad(HvlCoord v0Arg, HvlCoord v1Arg, HvlCoord v2Arg, HvlCoord v3Arg, 
			HvlCoord uv0Arg, HvlCoord uv1Arg, HvlCoord uv2Arg, HvlCoord uv3Arg){
		super(new HvlCoord[]{v0Arg, v1Arg, v2Arg, v3Arg}, new HvlCoord[]{uv0Arg, uv1Arg, uv2Arg, uv3Arg});
	}
	
	/**
	 * Constructs a HvlQuad with the vertices and UVs being equal to those of the supplied HvlQuad.
	 * 
	 * @param quadArg the HvlQuad to inherit coordinate values from
	 */
	public HvlQuad(HvlQuad quadArg){
		super(quadArg.getVertices(), quadArg.getUVs());
	}
	
	/**
	 * Sets the vertices of the HvlQuad.
	 * 
	 * @param v0Arg the first vertex for the HvlQuad
	 * @param v1Arg the second vertex for the HvlQuad
	 * @param v2Arg the third vertex for the HvlQuad
	 * @param v3Arg the fourth vertex for the HvlQuad
	 */
	public void setVertices(HvlCoord v0Arg, HvlCoord v1Arg, HvlCoord v2Arg, HvlCoord v3Arg){
		super.setVertices(v0Arg, v1Arg, v2Arg, v3Arg);
	}
	
	/**
	 * Sets the UVs of the HvlQuad.
	 * 
	 * @param uv0Arg the first UV for the HvlQuad
	 * @param uv1Arg the second UV for the HvlQuad
	 * @param uv2Arg the third UV for the HvlQuad
	 * @param uv3Arg the fourth UV for the HvlQuad
	 */
	public void setUVs(HvlCoord uv0Arg, HvlCoord uv1Arg, HvlCoord uv2Arg, HvlCoord uv3Arg){
		super.setUVs(uv0Arg, uv1Arg, uv2Arg, uv3Arg);
	}

}
