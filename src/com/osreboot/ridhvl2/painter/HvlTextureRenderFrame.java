package com.osreboot.ridhvl2.painter;

import org.newdawn.slick.opengl.Texture;

public class HvlTextureRenderFrame implements Texture{

	private HvlRenderFrame renderFrame;
	
	public HvlTextureRenderFrame(HvlRenderFrame renderFrameArg){
		renderFrame = renderFrameArg;
	}
	
	@Override
	public void bind(){}

	@Override
	public float getHeight(){
		return 1;
	}

	@Override
	public int getImageHeight(){
		return renderFrame.getHeight();
	}

	@Override
	public int getImageWidth(){
		return renderFrame.getWidth();
	}

	@Override
	public byte[] getTextureData(){
		return null;
	}

	@Override
	public int getTextureHeight(){
		return renderFrame.getHeight();
	}

	@Override
	public int getTextureID(){
		return renderFrame.getTextureID();
	}

	@Override
	public String getTextureRef(){
		return null;
	}

	@Override
	public int getTextureWidth(){
		return renderFrame.getWidth();
	}

	@Override
	public float getWidth(){
		return 1;
	}

	@Override
	public boolean hasAlpha(){
		return false;
	}

	@Override
	public void release(){}

	@Override
	public void setTextureFilter(int textureFilter){}

}