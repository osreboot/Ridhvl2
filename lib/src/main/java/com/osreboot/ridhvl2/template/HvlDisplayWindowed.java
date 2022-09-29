package com.osreboot.ridhvl2.template;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import com.osreboot.ridhvl2.HvlCoord;

public class HvlDisplayWindowed extends HvlDisplay{
	
	private HvlCoord size;

	public HvlDisplayWindowed(int widthArg, int heightArg, String titleArg, boolean resizableArg){
		super(titleArg, false, resizableArg);
		size = new HvlCoord(widthArg, heightArg);
		
		setResizable(resizableArg);
	}

	@Override
	protected long apply(){
		GLFW.glfwDefaultWindowHints();
		setResizable(isResizable());

		long id = GLFW.glfwCreateWindow((int)Math.ceil(size.x), (int)Math.ceil(size.y), getTitle(), MemoryUtil.NULL, MemoryUtil.NULL);

		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(id,
				(vidMode.width() - (int)Math.ceil(size.x)) / 2,
				(vidMode.height() - (int)Math.ceil(size.y)) / 2
				);
		
		GLFW.glfwSetWindowSizeCallback(id, (window, width, height) -> {
			HvlDisplay.resizeViewport(width, height);

			size.set(width, height);
		});
		
		HvlDisplay.registerCallbacks(id);

		GLFW.glfwMakeContextCurrent(id);
		setVsyncEnabled(isVsyncEnabled());
		GLFW.glfwShowWindow(id);
		
		return id;
	}

	@Override
	protected void unapply(){
		GLFW.glfwSetWindowSizeCallback(getId(), null);
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
		return GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor()).refreshRate(); // TODO this isn't correct
	}

}
