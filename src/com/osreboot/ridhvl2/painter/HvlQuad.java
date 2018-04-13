package com.osreboot.ridhvl2.painter;

import com.osreboot.ridhvl2.HvlCoord;

public final class HvlQuad extends HvlPolygon{

	private static HvlQuad global;

	public static HvlQuad hvlQuad(float x, float y, float xl, float yl){
		if(global == null){
			global = new HvlQuad(
					new HvlCoord(x, y), new HvlCoord(x + xl, y), 
					new HvlCoord(x + xl, y + yl), new HvlCoord(x, y + yl)
					);
		}else{
			global.setVertices(new HvlCoord[]{
					new HvlCoord(x, y), new HvlCoord(x + xl, y), 
					new HvlCoord(x + xl, y + yl), new HvlCoord(x, y + yl)
			});
		}
		return global;
	}

	public static final HvlCoord[] COORDS_DEFAULT_UVS = new HvlCoord[]{
			new HvlCoord(0, 0),
			new HvlCoord(1, 0),
			new HvlCoord(1, 1),
			new HvlCoord(0, 1),
	};

	HvlQuad(HvlCoord c0Arg, HvlCoord c1Arg, HvlCoord c2Arg, HvlCoord c3Arg){
		super(new HvlCoord[]{c0Arg, c1Arg, c2Arg, c3Arg}, COORDS_DEFAULT_UVS);
	}

	HvlQuad(HvlCoord c0Arg, HvlCoord c1Arg, HvlCoord c2Arg, HvlCoord c3Arg, 
			HvlCoord uv0Arg, HvlCoord uv1Arg, HvlCoord uv2Arg, HvlCoord uv3Arg){
		super(new HvlCoord[]{c0Arg, c1Arg, c2Arg, c3Arg}, new HvlCoord[]{uv0Arg, uv1Arg, uv2Arg, uv3Arg});
	}

}
