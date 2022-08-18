package com.osreboot.ridhvl2.template;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import com.osreboot.ridhvl2.HvlCoord;

public class HvlDisplayFullscreen extends HvlDisplay{

	public HvlDisplayFullscreen(String titleArg){
		super(titleArg, false, false);
	}

	@Override
	protected long apply(){
		GLFW.glfwDefaultWindowHints();

		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		long id = GLFW.glfwCreateWindow(vidMode.width(), vidMode.height(), getTitle(), GLFW.glfwGetPrimaryMonitor(), MemoryUtil.NULL);
		
		HvlDisplay.registerCallbacks(id);

		setResizable(isResizable());
		setVsyncEnabled(isVsyncEnabled());
		
		GLFW.glfwMakeContextCurrent(id);
		GLFW.glfwShowWindow(id);
		
		return id;
	}

	@Override
	protected void unapply(){
		HvlDisplay.clearCallbacks(getId());
		
		GLFW.glfwDestroyWindow(getId());
	}

	@Override
	protected void preUpdate(float delta){
		GL.createCapabilities();
		GLFW.glfwPollEvents();
	}

	@Override
	protected void postUpdate(float delta){
		// TODO Display.sync(getRefreshRate());
		GLFW.glfwSwapBuffers(getId());
		
		if(GLFW.glfwWindowShouldClose(getId())){
			HvlTemplate.newest().setExiting();
		}
	}

	@Override
	public HvlCoord getSize(){
		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		return new HvlCoord(vidMode.width(), vidMode.height());
	}

	@Override
	public int getRefreshRate(){
		return GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).refreshRate();
	}
}
