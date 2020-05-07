package com.osreboot.ridhvl2.painter;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlStatics;
import com.osreboot.ridhvl2.painter.HvlPaint.HvlPaintMode;
import com.osreboot.ridhvl2.template.HvlChronology;
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

	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		WebGLRenderingContext glContext = HvlDisplay.getDisplay().getGLContext();
		glContext.enable(WebGLRenderingContext.TEXTURE_2D);
		glContext.enable(WebGLRenderingContext.COLOR_MATERIAL);
		glContext.clearColor(0f, 0f, 0f, 0f);
		glContext.enable(WebGLRenderingContext.BLEND);
		glContext.blendFunc(WebGLRenderingContext.SRC_ALPHA, WebGLRenderingContext.ONE_MINUS_SRC_ALPHA);
		glContext.matrixMode(WebGLRenderingContext.MODELVIEW);

		glContext.matrixMode(WebGLRenderingContext.MATRIX_MODE);
		glContext.loadIdentity();
		glContext.ortho(0, HvlDisplay.getWidth(), HvlDisplay.getHeight(), 0, 1, -1);
		glContext.matrixMode(WebGLRenderingContext.MODELVIEW);
	};

	public static final HvlAction.A2<Boolean, Float> ACTION_PRE_UPDATE = (debug, delta) -> {
		active = HvlChronology.verifyLaunchCode(LAUNCH_CODE);
		WebGLRenderingContext glContext = HvlDisplay.getDisplay().getGLContext();
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT);

		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_MIN_FILTER, WebGLRenderingContext.NEAREST);//TODO usage flexibility
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_MAG_FILTER, WebGLRenderingContext.NEAREST);
	};

	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		active = false;
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
	 * use the methods in {@linkplain com.osreboot.ridhvl2.HvlStatics HvlStatics}.
	 * 
	 * @param polygonArg the polygon to draw
	 * @param paintArg the paint to use for <code>polygonArg</code>
	 * @throws HvlChronology.InactiveException if HvlPainter is not enabled by {@linkplain HvlChronology}'s launch 
	 * code
	 */
	public static void draw(HvlPolygon polygonArg, HvlPaint paintArg){
		if(active){
			WebGLRenderingContext glContext = HvlDisplay.getDisplay().getGLContext();
			if(paintArg.getMode() == HvlPaintMode.COLOR){
				
				// Prepare for solid-color polygon rendering
				glContext.disable(WebGLRenderingContext.TEXTURE_2D);
				glContext.color4f(paintArg.getValueColor().r, paintArg.getValueColor().g, paintArg.getValueColor().b, 
						paintArg.getValueColor().a);
				
			}else if(paintArg.getMode() == HvlPaintMode.TEXTURE){
				
				// Prepare for textured polygon rendering with solid white texture alpha
				glContext.enable(WebGLRenderingContext.TEXTURE_2D);
				Color.white.bind();
				glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, paintArg.getValueTexture().getTextureID());
				
			}else if(paintArg.getMode() == HvlPaintMode.TEXTURE_COLORIZED){
				
				// Prepare for textured polygon rendering with colorized alpha
				glContext.enable(WebGLRenderingContext.TEXTURE_2D);
				glContext.color4f(paintArg.getValueColor().r, paintArg.getValueColor().g, paintArg.getValueColor().b, 
						paintArg.getValueColor().a);
				glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, paintArg.getValueTexture().getTextureID());
				
			}else if(paintArg.getMode() == HvlPaintMode.RENDERFRAME){
				
				// Prepare for HvlRenderFrame rendering
				//TODO
				
			}

			// Render the polygon
			if(polygonArg instanceof HvlQuad) glContext.begin(WebGLRenderingContext.QUADS);
			else GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			for(int i = 0; i < polygonArg.getVertices().length; i++){
				if(paintArg.getMode() != HvlPaintMode.COLOR){
					//TODO test non-square texture drawing again when UV support is added
					if(paintArg.getMode() == HvlPaintMode.TEXTURE || paintArg.getMode() == HvlPaintMode.TEXTURE_COLORIZED){
						GL11.glTexCoord2f(polygonArg.getUVs()[i].x * paintArg.getValueTexture().getWidth(), 
								polygonArg.getUVs()[i].y * paintArg.getValueTexture().getHeight());
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
			WebGLRenderingContext glContext = HvlDisplay.getDisplay().getGLContext();
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
			WebGLRenderingContext glContext = HvlDisplay.getDisplay().getGLContext();
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
