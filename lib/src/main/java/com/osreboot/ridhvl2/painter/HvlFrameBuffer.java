package com.osreboot.ridhvl2.painter;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlTexture;
import com.osreboot.ridhvl2.template.HvlDisplay;

public class HvlFrameBuffer extends HvlTexture{

	private static boolean hasPushed = false;

	private static void setCurrentFrameBuffer(HvlFrameBuffer renderFrame){
		setCurrentFrameBuffer(renderFrame, true);
	}

	private static void setCurrentFrameBuffer(HvlFrameBuffer frameBuffer, boolean clear){
		if(hasPushed){
			EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
			GL11.glPopMatrix();
			hasPushed = false;
		}
		if(frameBuffer != null){
			hasPushed = true;
			GL11.glPushMatrix();
			GL11.glScalef(1f, -1f, 1f);
			GL11.glTranslatef(-frameBuffer.x, -frameBuffer.y - HvlDisplay.getHeight(), 0);
			EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, frameBuffer.frameId);
			if(clear) GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		}
	}

	public final int frameId;
	
	public int x, y;

	public HvlFrameBuffer(int widthArg, int heightArg){
		this(0, 0, widthArg, heightArg);
	}

	public HvlFrameBuffer(int xArg, int yArg, int widthArg, int heightArg){
		super(GL11.glGenTextures(), widthArg, heightArg, 1f, 1f);
		
		frameId = EXTFramebufferObject.glGenFramebuffersEXT();
		
		x = xArg;
		y = yArg;
		
		setCurrentFrameBuffer(this);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_INT, (ByteBuffer)null);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, id, 0);

		setCurrentFrameBuffer(null);
	}

	public void capture(HvlAction.A0 actionArg){
		setCurrentFrameBuffer(this);
		actionArg.run();
		setCurrentFrameBuffer(null);
	}

	public void capture(boolean clear, HvlAction.A0 actionArg){
		setCurrentFrameBuffer(this, clear);
		actionArg.run();
		setCurrentFrameBuffer(null);
	}

}