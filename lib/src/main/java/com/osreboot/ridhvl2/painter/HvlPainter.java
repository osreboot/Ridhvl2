package com.osreboot.ridhvl2.painter;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlStatics;
import com.osreboot.ridhvl2.painter.HvlPaint.HvlPaintMode;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyExit;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;
import com.osreboot.ridhvl2.template.HvlChronologyUpdate;
import com.osreboot.ridhvl2.template.HvlDisplay;

/**
 * A drawing system based on LWJGL's {@linkplain org.lwjgl.opengl.GL11 GL11} access. Must be enabled by
 * {@linkplain HvlChronology}'s launch code for methods to function.
 * 
 * @author os_reboot
 *
 */
public final class HvlPainter{

	public static final String LABEL = "HvlPainter";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_LATE + HvlChronology.CHRONOLOGY_INIT_INTERVAL,
	CHRONO_PRE_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_PRE_LATE + HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_POST_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_POST_EARLY - HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_EXIT = HvlChronology.CHRONOLOGY_EXIT_LATE - HvlChronology.CHRONOLOGY_EXIT_INTERVAL,
	LAUNCH_CODE = 2,
	LAUNCH_CODE_RAW = 4;//2^2

	private static boolean active = false;

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glClearColor(0f, 0f, 0f, 0f);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_MATRIX_MODE);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, HvlDisplay.getWidth(), HvlDisplay.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	};

	@HvlChronologyUpdate(label = LABEL, chronology = CHRONO_PRE_UPDATE, launchCode = LAUNCH_CODE)
	public static final HvlAction.A2<Boolean, Float> ACTION_PRE_UPDATE = (debug, delta) -> {
		active = HvlChronology.verifyLaunchCode(LAUNCH_CODE);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);//TODO usage flexibility
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);//TODO usage flexibility
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	};

	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		active = false;
	};

	private HvlPainter(){}

	/**
	 * Draws <code>polygonArg</code> in <code>paintArg</code> to the current HvlDisplay. The display should
	 * already be specified by {@linkplain HvlDisplay} if that utility is active (it is in
	 * most cases).
	 * 
	 * <p>
	 * 
	 * NOTE: this method should only be used by internal Ridhvl2 processes! If trying to draw inside a template,
	 * use the methods in {@linkplain com.osreboot.ridhvl2.HvlStatics HvlStatics}.
	 * 
	 * @param polygonArg the polygon to draw
	 * @param paintArg the paint to use for <code>polygonArg</code>
	 * @throws HvlChronology.InactiveException if HvlPainter is not enabled by {@linkplain HvlChronology}'s launch 
	 * code
	 */
	public static void draw(HvlPolygon polygonArg, HvlPaint paintArg){
		if(active){
			if(paintArg.getMode() == HvlPaintMode.COLOR){
				
				// Prepare for solid-color polygon rendering
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor4f(paintArg.getValueColor().r, paintArg.getValueColor().g, paintArg.getValueColor().b, 
						paintArg.getValueColor().a);
				
			}else if(paintArg.getMode() == HvlPaintMode.TEXTURE){
				
				// Prepare for textured polygon rendering with solid white texture alpha
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glColor4f(1f, 1f, 1f, 1f);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, paintArg.getValueTexture().id);
				
			}else if(paintArg.getMode() == HvlPaintMode.TEXTURE_COLORIZED){
				
				// Prepare for textured polygon rendering with colorized alpha
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glColor4f(paintArg.getValueColor().r, paintArg.getValueColor().g, paintArg.getValueColor().b, 
						paintArg.getValueColor().a);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, paintArg.getValueTexture().id);
				
			}

			// Render the polygon
			if(polygonArg instanceof HvlQuad) GL11.glBegin(GL11.GL_QUADS);
			else GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			for(int i = 0; i < polygonArg.getVertices().length; i++){
				if(paintArg.getMode() != HvlPaintMode.COLOR){
					//TODO test non-square texture drawing again when UV support is added
					if(paintArg.getMode() == HvlPaintMode.TEXTURE || paintArg.getMode() == HvlPaintMode.TEXTURE_COLORIZED){
						GL11.glTexCoord2f(polygonArg.getUVs()[i].x * paintArg.getValueTexture().uCrop,
								polygonArg.getUVs()[i].y * paintArg.getValueTexture().vCrop);
					}else GL11.glTexCoord2f(polygonArg.getUVs()[i].x, polygonArg.getUVs()[i].y);
				}
				GL11.glVertex2f(polygonArg.getVertices()[i].x, polygonArg.getVertices()[i].y);
			}
			GL11.glEnd();
			
			// Cleanup after polygon rendering
			if(paintArg.getMode() == HvlPaintMode.TEXTURE || paintArg.getMode() == HvlPaintMode.TEXTURE_COLORIZED){
				GL11.glDisable(GL11.GL_TEXTURE_2D);
			}
			
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	/**
	 * Applies a translation transformation to the body of <code>actionArg</code>, with <code>xArg</code> and
	 * <code>yArg</code> being the offset of the translation.
	 * 
	 * <p>
	 * 
	 * NOTE: this method should only be used by internal Ridhvl2 processes! If trying to draw inside a template,
	 * use the methods in {@linkplain HvlStatics}.
	 * 
	 * @param xArg the x-offset of the translation
	 * @param yArg the y-offset of the translation
	 * @param actionArg the context that the translation is applied to
	 * @throws HvlChronology.InactiveException if HvlPainter is not enabled by {@linkplain HvlChronology}'s launch 
	 * code
	 */
	public static void translate(float xArg, float yArg, HvlAction.A0 actionArg){
		if(active){
			GL11.glPushMatrix();
			GL11.glTranslatef(xArg, yArg, 0);
			actionArg.run();
			GL11.glPopMatrix();
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	/**
	 * Applies a rotation transformation to the body of <code>actionArg</code>, with <code>xArg</code> and
	 * <code>yArg</code> being the origin of the rotation, and <code>degreesArg</code> being the magnitude of
	 * the rotation, in degrees.
	 * 
	 * <p>
	 * 
	 * NOTE: this method should only be used by internal Ridhvl2 processes! If trying to draw inside a template,
	 * use the methods in {@linkplain HvlStatics}.
	 * 
	 * @param xArg the x-origin of the rotation
	 * @param yArg the y-origin of the rotation
	 * @param degreesArg the magnitude of the rotation
	 * @param actionArg the context that the rotation is applied to
	 * @throws HvlChronology.InactiveException if HvlPainter is not enabled by {@linkplain HvlChronology}'s launch 
	 * code
	 */
	public static void rotate(float xArg, float yArg, float degreesArg, HvlAction.A0 actionArg){
		if(active){
			GL11.glPushMatrix();
			GL11.glTranslatef(xArg, yArg, 0);
			GL11.glRotatef(degreesArg, 0, 0, 1);
			GL11.glTranslatef(-xArg, -yArg, 0);
			actionArg.run();
			GL11.glPopMatrix();
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}
	
	/**
	 * Applies a scaling transformation to the body of <code>actionArg</code>, with <code>xArg</code> and
	 * <code>yArg</code> being the origin of the scale, and <code>scaleArg</code> being the magnitude of
	 * the scale.
	 * 
	 * <p>
	 * 
	 * NOTE: this method should only be used by internal Ridhvl2 processes! If trying to draw inside a template,
	 * use the methods in {@linkplain HvlStatics}.
	 * 
	 * @param xArg the x-origin of the scale
	 * @param yArg the y-origin of the scale
	 * @param scaleArg the magnitude of the scale
	 * @param actionArg the context that the scale is applied to
	 * @throws HvlChronology.InactiveException if HvlPainter is not enabled by {@linkplain HvlChronology}'s launch 
	 * code
	 */
	public static void scale(float xArg, float yArg, float scaleArg, HvlAction.A0 actionArg){
		if(active){
			GL11.glPushMatrix();
			GL11.glTranslatef(xArg, yArg, 0);
			GL11.glScalef(scaleArg, scaleArg, 1f);
			GL11.glTranslatef(-xArg, -yArg, 0);
			actionArg.run();
			GL11.glPopMatrix();
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

}
