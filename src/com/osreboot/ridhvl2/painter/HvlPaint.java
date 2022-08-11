package com.osreboot.ridhvl2.painter;

import com.osreboot.ridhvl2.migration.Color;
import com.osreboot.ridhvl2.migration.HvlTexture;

//TODO implement HvlRenderFrame functionality
/**
 * A flexible texture-esq object that is applied when drawing polygons with {@linkplain HvlPainter}. Usable in 
 * four modes (<code>COLOR, TEXTURE, TEXTURE_COLORIZED, RENDERFRAME</code>). HvlPainter will automatically analyze
 * the mode and draw the texture with the appropriate commands.
 * 
 * @author os_reboot
 *
 */
public final class HvlPaint {

	/**
	 * The setting of a HvlPaint that determines the commands used to apply the paint to a polygon. This
	 * can also be used to determine which variable in the HvlPaint instance is holding data.
	 * 
	 * @author os_reboot
	 *
	 */
	public enum HvlPaintMode{
		COLOR, TEXTURE, TEXTURE_COLORIZED, RENDERFRAME
	}

	private HvlPaintMode mode;

	private Color color;
	private HvlTexture texture;
	//	private HvlRenderFrame frame;

	/**
	 * Constructs a HvlPaint from a color. Automatically sets the mode to <code>COLOR</code>.
	 * 
	 * @param cArg the value to assign to the 'color' variable
	 */
	public HvlPaint(Color cArg){
		color = cArg;
		mode = HvlPaintMode.COLOR;
	}

	/**
	 * Constructs a HvlPaint from a texture. Automatically sets the mode to <code>TEXTURE</code>.
	 * 
	 * @param tArg the value to assign to the 'texture' variable
	 */
	public HvlPaint(HvlTexture tArg){
		texture = tArg;
		mode = HvlPaintMode.TEXTURE;
	}
	
	/**
	 * Constructs a HvlPaint from a texture. Automatically sets the mode to <code>TEXTURE_COLORIZED</code>.
	 * 
	 * @param tArg the value to assign to the 'texture' variable
	 */
	public HvlPaint(Color cArg, HvlTexture tArg){
		color = cArg;
		texture = tArg;
		mode = HvlPaintMode.TEXTURE_COLORIZED;
	}

	//	/**
	//	 * Constructs a HvlPaint from a {@linkplain HvlRenderFrame}. Automatically sets the mode to <code>RENDERFRAME</code>.
	//	 * 
	//	 * @param fArg the value to assign to the 'frame' variable
	//	 */
	//	public HvlPaint(HvlRenderFrame fArg){
	//		frame = fArg;
	//		mode = Mode.RENDERFRAME;
	//	}

	/**
	 * @return the current mode of the HvlPaint
	 */
	public HvlPaintMode getMode(){
		return mode;
	}

	/**
	 * @return the value of the 'color' variable
	 * @throws WrongModeException if the mode does not match <code>COLOR</code> or <code>TEXTURE_COLORIZED</code>
	 */
	public Color getValueColor(){
		if(mode == HvlPaintMode.COLOR || mode ==  HvlPaintMode.TEXTURE_COLORIZED)
			return color;
		else throw new WrongModeException(HvlPaintMode.COLOR, mode);
	}

	/**
	 * @return the value of the 'texture' variable
	 * @throws WrongModeException if the mode does not match <code>TEXTURE</code> or <code>TEXTURE_COLORIZED</code>
	 */
	public HvlTexture getValueTexture(){
		if(mode == HvlPaintMode.TEXTURE || mode ==  HvlPaintMode.TEXTURE_COLORIZED)
			return texture;
		else throw new WrongModeException(HvlPaintMode.TEXTURE, mode);
	}

	//	/**
	//	 * @return the value of the 'frame' variable
	//	 * @throws WrongModeException if the mode does not match <code>RENDERFRAME</code>
	//	 */
	//	public HvlRenderFrame getValueRF(){
	//		if(mode == Mode.RENDERFRAME)
	//			return frame;
	//		else throw new WrongModeException(Mode.RENDERFRAME, mode);
	//	}

	/**
	 * Sets the value of the HvlPaint. Automatically sets mode to <code>COLOR</code> and sets non-color variables to
	 * null.
	 * 
	 * @param cArg the value to assign to the 'color' variable
	 */
	public void setValue(Color cArg){
		color = cArg;
		texture = null;
		//		frame = null;
		mode = HvlPaintMode.COLOR;
	}

	/**
	 * Sets the value of the HvlPaint. Automatically sets mode to <code>TEXTURE</code> and sets non-texture variables to
	 * null.
	 * 
	 * @param tArg the value to assign to the 'texture' variable
	 */
	public void setValue(HvlTexture tArg){
		color = null;
		texture = tArg;
		//		frame = null;
		mode = HvlPaintMode.TEXTURE;
	}
	
	/**
	 * Sets the value of the HvlPaint. Automatically sets mode to <code>TEXTURE_COLORIZED</code> and sets non-color, 
	 * non-texture variables to null.
	 * 
	 * @param cArg the value to assign to the 'color' variable
	 * @param tArg the value to assign to the 'texture' variable
	 */
	public void setValue(Color cArg, HvlTexture tArg){
		color = cArg;
		texture = tArg;
		//		frame = null;
		mode = HvlPaintMode.TEXTURE_COLORIZED;
	}

	//	/**
	//	 * Sets the value of the HvlPaint. Automatically sets mode to <code>RENDERFRAME</code> and sets non-HvlRenderFrame 
	//	 * variables to null.
	//	 * 
	//	 * @param fArg the value to assign to the 'frame' variable
	//	 */
	//	public void setValue(HvlRenderFrame fArg){
	//		color = null;
	//		texture = null;
	//		frame = fArg;
	//		mode = Mode.RENDERFRAME;
	//	}

	/**
	 * Thrown if a type-casting getter is improperly executed on a HvlPaint without first checking it's 
	 * <code>mode</code> with {@linkplain HvlPaint#getMode()}. For example, executing the following lines:
	 * 
	 * <p>
	 * 
	 * <code>HvlPaint p = new HvlPaint(Color.white); p.getValueTexture();</code>
	 * 
	 * <p>
	 * 
	 * ...will throw a WrongModeException.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class WrongModeException extends RuntimeException{
		private static final long serialVersionUID = -7821681025532438849L;

		private WrongModeException(HvlPaintMode modeError, HvlPaintMode modeCorrect){
			super("Referenced the incorrect HvlPaint mode " + modeError.toString() + "! This HvlPaint's mode is " + modeCorrect.toString() + ".");
		}
	}

}
