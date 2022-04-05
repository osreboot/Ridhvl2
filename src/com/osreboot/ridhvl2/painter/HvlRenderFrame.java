package com.osreboot.ridhvl2.painter;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import com.osreboot.ridhvl2.HvlAction;

public class HvlRenderFrame {

	private static boolean hasPushed = false;

	private static void setCurrentRenderFrame(HvlRenderFrame renderFrame){
		setCurrentRenderFrame(renderFrame, true);
	}

	private static void setCurrentRenderFrame(HvlRenderFrame renderFrame, boolean clear){
		if(hasPushed){
			EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
			GL11.glPopMatrix();
			hasPushed = false;
		}
		if(renderFrame != null){
			hasPushed = true;
			GL11.glPushMatrix();
			GL11.glTranslatef(-renderFrame.getX(), (Display.getHeight() - renderFrame.getHeight() - renderFrame.getY()), 0);
			EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, renderFrame.getID());
			if(clear){
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			}
		}
	}

	private int frameID, textureID, width, height, x = 0, y = 0;
	private HvlTextureRenderFrame texture;

	public HvlRenderFrame(int widthArg, int heightArg){
		this(0, 0, widthArg, heightArg);
	}

	public HvlRenderFrame(int xArg, int yArg, int widthArg, int heightArg){
		x = xArg;
		y = yArg;
		width = widthArg;
		height = heightArg;
		frameID = EXTFramebufferObject.glGenFramebuffersEXT();
		textureID = GL11.glGenTextures();

		setCurrentRenderFrame(this);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_INT, (ByteBuffer)null);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, textureID, 0);

		setCurrentRenderFrame(null);
		
		texture = new HvlTextureRenderFrame(this);
	}

	public void doCapture(HvlAction.A0 actionArg){
		setCurrentRenderFrame(this);
		actionArg.run();
		setCurrentRenderFrame(null);
	}

	public void doCapture(boolean clear, HvlAction.A0 actionArg){
		setCurrentRenderFrame(this, clear);
		actionArg.run();
		setCurrentRenderFrame(null);
	}

	public void doCapture(HvlShader shaderArg, final HvlAction.A0 actionArg){
		setCurrentRenderFrame(this);
		shaderArg.doShade(new HvlAction.A0(){
			@Override
			public void run(){
				actionArg.run();
			}
		});
		setCurrentRenderFrame(null);
	}

	public void doCapture(boolean clear, HvlShader shaderArg, final HvlAction.A0 actionArg){
		setCurrentRenderFrame(this, clear);
		shaderArg.doShade(new HvlAction.A0(){
			@Override
			public void run(){
				actionArg.run();
			}
		});
		setCurrentRenderFrame(null);
	}

	public int getID(){
		return frameID;
	}

	public int getTextureID(){
		return textureID;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public int getX(){
		return x;
	}

	public void setX(int xArg){
		x = xArg;
	}

	public int getY(){
		return y;
	}

	public void setY(int yArg){
		y = yArg;
	}
	
	public HvlTextureRenderFrame getTexture(){
		return texture;
	}

}