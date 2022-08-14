package com.osreboot.ridhvl2.template;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.migration.Display;
import com.osreboot.ridhvl2.migration.Mouse;

/**
 * An implementation of {@linkplain HvlDisplay} that handles a windowed LWJGL 
 * {@linkplain org.lwjgl.opengl.Display Display}.
 * 
 * <p>
 * 
 * {@linkplain #setVsyncEnabled(boolean)} is disabled for HvlDisplayWindowed.
 * 
 * <p>
 * 
 * In order to change significant display properties that aren't included as instance methods, instantiate a new
 * HvlDisplay subclass and apply it with {@linkplain HvlDisplay#setDisplay(HvlDisplay)}.
 * 
 * @author os_reboot
 *
 */
public class HvlDisplayWindowed extends HvlDisplay{

	private String initialTitle;
	private int initialWidth, initialHeight;
	public boolean initialResizable;
	private long id;

	/**
	 * Constructor that defaults to a decorated window. Use 
	 * {@linkplain #HvlDisplayWindowed(int, int, int, String, boolean, boolean)} to supply a custom decorated value.
	 * 
	 * <p>
	 * 
	 * See {@linkplain HvlDisplayWindowed} for more information.
	 * 
	 * @param refreshRateArg the maximum refresh rate of the display
	 * @param widthArg the width (in pixels) of the display
	 * @param heightArg the height (in pixels) of the display
	 * @param titleArg the title of the window
	 * @param resizableArg whether or not the window is resizable
	 */
	public HvlDisplayWindowed(int refreshRateArg, int widthArg, int heightArg, String titleArg, boolean resizableArg){
		super(refreshRateArg, false, resizableArg);
		initialWidth = widthArg;
		initialHeight = heightArg;
		initialTitle = titleArg;
	}

	/**
	 * Constructor that takes a custom decorated value. Use 
	 * {@linkplain #HvlDisplayWindowed(int, int, int, String, boolean)} to assume a default decorated value.
	 * 
	 * <p>
	 * 
	 * See {@linkplain HvlDisplayWindowed} for more information.
	 * 
	 * @param refreshRateArg the maximum refresh rate of the display
	 * @param widthArg the width (in pixels) of the display
	 * @param heightArg the height (in pixels) of the display
	 * @param titleArg the title of the window
	 * @param resizableArg whether or not the window is resizable
	 * @param undecoratedArg whether or not the window is undecorated
	 */
	public HvlDisplayWindowed(int refreshRateArg, int widthArg, int heightArg, String titleArg, boolean resizableArg, boolean undecoratedArg){
		super(refreshRateArg, false, resizableArg);
		initialWidth = widthArg;
		initialHeight = heightArg;
		initialTitle = titleArg;
	}

	@Override
	protected void apply(){
		GLFW.glfwInit();
		GLFWErrorCallback.createPrint(System.err).set();
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, isResizable() ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);

		id = GLFW.glfwCreateWindow(initialWidth, initialHeight, initialTitle, GLFW.glfwGetPrimaryMonitor(), MemoryUtil.NULL);

		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(id,
				(vidmode.width() - initialWidth) / 2,
				(vidmode.height() - initialHeight) / 2
				);

		GLFW.glfwSetWindowSizeCallback(id, (window, width, height) -> {
			GL11.glViewport(0, 0, width, height);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, width, height, 0, 1, -1);
		});
		
		GLFW.glfwSetScrollCallback(id, (window, xoffset, yoffset) -> {
			Mouse.dWheel = (float)yoffset;
		});

		GLFW.glfwMakeContextCurrent(id);
		GLFW.glfwSwapInterval(isVsyncEnabled() ? 1 : 0);
		GLFW.glfwShowWindow(id);
	}

	@Override
	protected void unapply(){
		GLFW.glfwDestroyWindow(id);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}

	@Override
	protected void preUpdate(float delta){
		GL.createCapabilities();
		GLFW.glfwPollEvents();
	}

	@Override
	protected void postUpdate(float delta){
		// TODO Display.sync(getRefreshRate());
		GLFW.glfwSwapBuffers(id);
		
		if(GLFW.glfwWindowShouldClose(id)){
			HvlTemplate.newest().setExiting();
		}

		Mouse.dWheel = 0;
	}

	/**
	 * This method is disabled for HvlDisplayWindowed.
	 * 
	 * @param vsyncEnabledArg <code>vsyncEnabled</code> is always false, regardless of this value
	 */
	@Override
	public void setVsyncEnabled(boolean vsyncEnabledArg){
		super.setVsyncEnabled(false);
		HvlLogger.println("VSync cannot be enabled with a windowed display!");
	}

	@Override
	public void setResizable(boolean resizableArg){
		super.setResizable(resizableArg);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizableArg ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}

	@Override
	public HvlEnvironment getEnvironment(){
		return new HvlEnvironment(0, 0, Display.getWidth(), Display.getHeight(), false);
	}

	@Override
	public long getId(){
		return id;
	}

}
