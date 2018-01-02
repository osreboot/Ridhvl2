package com.osreboot.ridhvl2.painter;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

//TODO implement HvlRenderFrame functionality
public final class HvlPaint {

	public static enum Mode{
		COLOR, TEXTURE, RENDERFRAME
	}

	private Mode mode;

	private Color color;
	private Texture texture;
//	private HvlRenderFrame frame;

	public HvlPaint(Color cArg){
		color = cArg;
		mode = Mode.COLOR;
	}

	public HvlPaint(Texture tArg){
		texture = tArg;
		mode = Mode.TEXTURE;
	}
	
//	public HvlPaint(HvlRenderFrame fArg){
//		frame = fArg;
//		mode = Mode.RENDERFRAME;
//	}
	
	public Mode getMode(){
		return mode;
	}
	
	public Color getValueColor(){
		if(mode == Mode.COLOR)
			return color;
		else throw new WrongModeException(Mode.COLOR, mode);
	}
	
	public Texture getValueTexture(){
		if(mode == Mode.TEXTURE)
			return texture;
		else throw new WrongModeException(Mode.TEXTURE, mode);
	}
	
//	public HvlRenderFrame getValueRF(){
//		if(mode == Mode.RENDERFRAME)
//			return frame;
//		else throw new WrongModeException(Mode.RENDERFRAME, mode);
//	}
	
	public void setValue(Color cArg){
		color = cArg;
		texture = null;
//		frame = null;
		mode = Mode.COLOR;
	}
	
	public void setValue(Texture tArg){
		color = null;
		texture = tArg;
//		frame = null;
		mode = Mode.TEXTURE;
	}
	
//	public void setValue(HvlRenderFrame fArg){
//		color = null;
//		texture = null;
//		frame = fArg;
//		mode = Mode.RENDERFRAME;
//	}

	private static class WrongModeException extends RuntimeException{
		private static final long serialVersionUID = -7821681025532438849L;
		
		private WrongModeException(Mode modeError, Mode modeCorrect){
			super("Referenced the incorrect HvlPaint mode " + modeError.toString() + "! This HvlPaint's mode is " + modeCorrect.toString() + ".");
		}
	}

}
