package com.osreboot.ridhvl2.painter;

import com.osreboot.ridhvl2.HvlCoord;

public final class HvlQuad extends HvlPolygon{

	public static final HvlCoord[] COORDS_DEFAULT_UVS = new HvlCoord[]{
			new HvlCoord(1, 0),
			new HvlCoord(1, 1),
			new HvlCoord(0, 1),
			new HvlCoord(0, 0),
	};

	public HvlQuad(HvlCoord c0Arg, HvlCoord c1Arg, HvlCoord c2Arg, HvlCoord c3Arg){
		super(new HvlCoord[]{c0Arg, c1Arg, c2Arg, c3Arg}, COORDS_DEFAULT_UVS);
	}

	public HvlQuad(HvlCoord c0Arg, HvlCoord c1Arg, HvlCoord c2Arg, HvlCoord c3Arg, 
			HvlCoord uv0Arg, HvlCoord uv1Arg, HvlCoord uv2Arg, HvlCoord uv3Arg){
		super(new HvlCoord[]{c0Arg, c1Arg, c2Arg, c3Arg}, new HvlCoord[]{uv0Arg, uv1Arg, uv2Arg, uv3Arg});
	}
	
	public HvlQuad(HvlQuad quadArg){
		super(quadArg.getVertices(), quadArg.getUVs());
	}
	
	public HvlCoord[] getVertices(){
		return super.getVertices();
	}
	
	public HvlCoord[] getUVs(){
		return super.getUVs();
	}
	
	public void setVertices(HvlCoord c0Arg, HvlCoord c1Arg, HvlCoord c2Arg, HvlCoord c3Arg){
		super.setVertices(c0Arg, c1Arg, c2Arg, c3Arg);
	}
	
	public void setUVs(HvlCoord uv0Arg, HvlCoord uv1Arg, HvlCoord uv2Arg, HvlCoord uv3Arg){
		super.setUVs(uv0Arg, uv1Arg, uv2Arg, uv3Arg);
	}

}
