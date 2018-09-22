package com.osreboot.ridhvl2.painter;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.painter.HvlPaint.HvlPaintMode;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;
import com.osreboot.ridhvl2.template.HvlChronologyUpdate;

public final class HvlPainter{

	public static final String LABEL = "HvlPainter";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_LATE + HvlChronology.CHRONOLOGY_INIT_INTERVAL,
	CHRONO_PRE_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_PRE_LATE + HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_POST_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_POST_EARLY - HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	LAUNCH_CODE = 2,
	LAUNCH_CODE_RAW = 4;//2^2
	
	private static boolean active;

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glClearColor(0f, 0f, 0f, 0f);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_MATRIX_MODE);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	};

	@HvlChronologyUpdate(label = LABEL, chronology = CHRONO_PRE_UPDATE, launchCode = LAUNCH_CODE)
	public static final HvlAction.A2<Boolean, Float> ACTION_PRE_UPDATE = (debug, delta) -> {
		active = HvlChronology.verifyLaunchCode(LAUNCH_CODE);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	};

	private HvlPainter(){}

	/**
	 * Draws <code>polygonArg</code> in <code>paintArg</code> to the current 
	 * {@linkplain org.lwjgl.opengl.Display Display}. The Display should already be specified by
	 * {@linkplain com.osreboot.ridhvl2.template.HvlDisplay HvlDisplay} if that utility is active (it is in
	 * most cases).
	 * 
	 * <p>
	 * 
	 * NOTE: this method should only be used by internal Ridhvl2 processes! If trying to draw inside a template,
	 * use the methods in {@linkplain com.osreboot.ridhvl2.statics.HvlStaticPainter HvlStaticPainter}.
	 * 
	 * @param polygonArg the polygon to draw
	 * @param paintArg the paint to use for <code>polygonArg</code>
	 */
	public static void draw(HvlPolygon polygonArg, HvlPaint paintArg){
		if(active){
			if(paintArg.getMode() == HvlPaintMode.COLOR){
				GL11.glColor4f(paintArg.getValueColor().r, paintArg.getValueColor().g, paintArg.getValueColor().b, 
						paintArg.getValueColor().a);
			}else if(paintArg.getMode() == HvlPaintMode.TEXTURE){
				//TODO
			}else if(paintArg.getMode() == HvlPaintMode.RENDERFRAME){
				//TODO
			}

			if(polygonArg instanceof HvlQuad) GL11.glBegin(GL11.GL_QUADS);
			else GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			for(int i = 0; i < polygonArg.getVertices().length; i++){
				GL11.glVertex2f(polygonArg.getVertices()[i].getX(), polygonArg.getVertices()[i].getY());
				if(paintArg.getMode() != HvlPaintMode.COLOR) 
					GL11.glTexCoord2f(polygonArg.getUVs()[i].getX(), polygonArg.getUVs()[i].getY());
			}
			GL11.glEnd();
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

}
