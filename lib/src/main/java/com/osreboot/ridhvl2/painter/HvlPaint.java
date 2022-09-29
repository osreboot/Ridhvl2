package com.osreboot.ridhvl2.painter;

import com.osreboot.ridhvl2.HvlColor;
import com.osreboot.ridhvl2.HvlTexture;

/**
 * A flexible texture-esq object that is applied when drawing polygons with {@linkplain HvlPainter}. Usable in 
 * four modes (<code>COLOR, TEXTURE, TEXTURE_COLORIZED</code>). HvlPainter will automatically analyze
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
		COLOR, TEXTURE, TEXTURE_COLORIZED
	}

	private HvlPaintMode mode;

	private HvlColor color;
	private HvlTexture texture;

	/**
	 * Constructs a HvlPaint from a color. Automatically sets the mode to <code>COLOR</code>.
	 * 
	 * @param cArg the value to assign to the 'color' variable
	 */
	public HvlPaint(HvlColor cArg){
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
	public HvlPaint(HvlColor cArg, HvlTexture tArg){
		color = cArg;
		texture = tArg;
		mode = HvlPaintMode.TEXTURE_COLORIZED;
	}

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
	public HvlColor getValueColor(){
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

	/**
	 * Sets the value of the HvlPaint. Automatically sets mode to <code>COLOR</code> and sets non-color variables to
	 * null.
	 * 
	 * @param cArg the value to assign to the 'color' variable
	 */
	public void setValue(HvlColor cArg){
		color = cArg;
		texture = null;
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
		mode = HvlPaintMode.TEXTURE;
	}
	
	/**
	 * Sets the value of the HvlPaint. Automatically sets mode to <code>TEXTURE_COLORIZED</code> and sets non-color, 
	 * non-texture variables to null.
	 * 
	 * @param cArg the value to assign to the 'color' variable
	 * @param tArg the value to assign to the 'texture' variable
	 */
	public void setValue(HvlColor cArg, HvlTexture tArg){
		color = cArg;
		texture = tArg;
		mode = HvlPaintMode.TEXTURE_COLORIZED;
	}

	/**
	 * Thrown if a type-casting getter is improperly executed on a HvlPaint without first checking it's 
	 * <code>mode</code> with {@linkplain HvlPaint#getMode()}. For example, executing the following lines:
	 * 
	 * <p>
	 * 
	 * <code>HvlPaint p = new HvlPaint(HvlColor.WHITE); p.getValueTexture();</code>
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
