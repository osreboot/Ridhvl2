package com.osreboot.ridhvl2.template;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import com.osreboot.ridhvl2.HvlCoord;

public class HvlDisplayFullscreen extends HvlDisplay{

	private HvlCoord size;
	
	public HvlDisplayFullscreen(String titleArg){
		super(titleArg, false, false);
		
		size = new HvlCoord();
	}

	@Override
	protected long apply(){
		GLFW.glfwDefaultWindowHints();
		setResizable(isResizable());

		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		long id = GLFW.glfwCreateWindow(vidMode.width(), vidMode.height(), getTitle(), GLFW.glfwGetPrimaryMonitor(), MemoryUtil.NULL);
		size.set(vidMode.width(), vidMode.height());
		
		HvlDisplay.registerCallbacks(id);

		GLFW.glfwMakeContextCurrent(id);
		setVsyncEnabled(isVsyncEnabled());
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
		
		// Update size
		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		size.set(vidMode.width(), vidMode.height());
	}

	@Override
	protected void postUpdate(float delta){
		GLFW.glfwSwapBuffers(getId());
		
		if(GLFW.glfwWindowShouldClose(getId())){
			HvlTemplate.newest().setExiting();
		}
	}

	@Override
	public HvlCoord getIndependentSize(){
		return size;
	}

	@Override
	public int getRefreshRate(){
		return GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).refreshRate();
	}
}
