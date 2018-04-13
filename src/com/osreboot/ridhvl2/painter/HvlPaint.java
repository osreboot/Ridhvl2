package com.osreboot.ridhvl2.painter;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

//TODO implement HvlRenderFrame functionality
/**
 * A flexible texture that is applied when drawing polygons with {@linkplain HvlPainter}. Usable in three modes 
 * (<code>COLOR, TEXTURE, RENDERFRAME</code>). HvlPainter will automatically analyze the mode and draw the texture with the
 * appropriate commands.
 * 
 * <p>
 * 
 * This class is intended to be statically imported.
 * 
 * @author os_reboot
 *
 */
public final class HvlPaint {

	private static HvlPaint global;

	/**
	 * Re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to store their own HvlPaint instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPaint values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param cArg the color value assigned to the returned HvlPaint
	 * @return an instance of HvlPaint set to the specified value and mode
	 */
	public static HvlPaint hvlPaint(Color cArg){
		if(global == null) global = new HvlPaint(cArg);
		else global.setValue(cArg);
		return global;
	}

	/**
	 * Re-assigns the value of a global variable and returns that variable. This is an optimization 
	 * technique that removes the need for users to store their own HvlPaint instances and allows for rapid
	 * {@linkplain HvlPainter} draw calls with varying HvlPaint values.
	 * 
	 * <p>
	 * 
	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	 * draw calls!
	 * 
	 * @param tArg the texture value assigned to the returned HvlPaint
	 * @return an instance of HvlPaint set to the specified value and mode
	 */
	public static HvlPaint hvlPaint(Texture tArg){
		if(global == null) global = new HvlPaint(tArg);
		else global.setValue(tArg);
		return global;
	}

	//	/**
	//	 * Re-assigns the value of a global variable and returns that variable. This is an optimization 
	//	 * technique that removes the need for users to store their own HvlPaint instances and allows for rapid
	//	 * {@linkplain HvlPainter} draw calls with varying HvlPaint values.
	//	 * 
	//	 * <p>	
	//	 * 
	//	 * NOTE: this references a volatile memory location and should only be used directly inside HvlPainter
	//	 * draw calls!
	//	 * 
	//	 * @param fArg the frame value assigned to the returned HvlPaint
	//	 * @return an instance of HvlPaint set to the specified value and mode
	//	 */
	//	public static HvlPaint hvlPaint(HvlRenderFrame fArg){
	//		if(global == null) global = new HvlPaint(fArg);
	//		else global.setValue(fArg);
	//		return global;
	//	}

	/**
	 * The setting of a HvlPaint that determines the commands used to apply the paint to a polygon. This
	 * can also be used to determine which variable in the HvlPaint instance is holding data.
	 * 
	 * @author os_reboot
	 *
	 */
	public enum HvlPaintMode{
		COLOR, TEXTURE, RENDERFRAME
	}

	private HvlPaintMode mode;

	private Color color;
	private Texture texture;
	//	private HvlRenderFrame frame;

	/**
	 * Constructs a HvlPaint from a color. Automatically sets the mode to <code>COLOR</code>.
	 * 
	 * @param cArg the value to assign to the 'color' variable
	 */
	HvlPaint(Color cArg){
		color = cArg;
		mode = HvlPaintMode.COLOR;
	}

	/**
	 * Constructs a HvlPaint from a texture. Automatically sets the mode to <code>TEXTURE</code>.
	 * 
	 * @param tArg the value to assign to the 'texture' variable
	 */
	HvlPaint(Texture tArg){
		texture = tArg;
		mode = HvlPaintMode.TEXTURE;
	}

	//	/**
	//	 * Constructs a HvlPaint from a {@linkplain HvlRenderFrame}. Automatically sets the mode to <code>RENDERFRAME</code>.
	//	 * 
	//	 * @param fArg the value to assign to the 'frame' variable
	//	 */
	//	HvlPaint(HvlRenderFrame fArg){
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
	 * @throws WrongModeException if the mode does not match <code>COLOR</code>
	 */
	public Color getValueColor(){
		if(mode == HvlPaintMode.COLOR)
			return color;
		else throw new WrongModeException(HvlPaintMode.COLOR, mode);
	}

	/**
	 * @return the value of the 'texture' variable
	 * @throws WrongModeException if the mode does not match <code>TEXTURE</code>
	 */
	public Texture getValueTexture(){
		if(mode == HvlPaintMode.TEXTURE)
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
	public void setValue(Texture tArg){
		color = null;
		texture = tArg;
		//		frame = null;
		mode = HvlPaintMode.TEXTURE;
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

	private static class WrongModeException extends RuntimeException{
		private static final long serialVersionUID = -7821681025532438849L;

		private WrongModeException(HvlPaintMode modeError, HvlPaintMode modeCorrect){
			super("Referenced the incorrect HvlPaint mode " + modeError.toString() + "! This HvlPaint's mode is " + modeCorrect.toString() + ".");
		}
	}

}
