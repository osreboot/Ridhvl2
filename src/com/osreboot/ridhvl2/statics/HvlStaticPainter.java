package com.osreboot.ridhvl2.statics;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl2.painter.HvlPaint;
import com.osreboot.ridhvl2.painter.HvlPainter;
import com.osreboot.ridhvl2.painter.HvlPolygon;

public class HvlStaticPainter {

	private static HvlPaint globalPaint;
	
	/**
	 * Casts <code>colorArg</code> to a {@linkplain HvlPaint} and then equivalently draws <code>polygonArg</code>
	 * with said HvlPaint. Uses the same drawing procedure from 
	 * {@linkplain com.osreboot.ridhvl2.painter.HvlPainter HvlPainter}.
	 * 
	 * @param polygonArg the polygon to draw
	 * @param colorArg the paint to use for <code>polygonArg</code>
	 */
	public static void hvlDraw(HvlPolygon polygonArg, Color colorArg){
		if(globalPaint == null) globalPaint = new HvlPaint(colorArg);
		else globalPaint.setValue(colorArg);
		HvlPainter.draw(polygonArg, globalPaint);
	}
	
}
