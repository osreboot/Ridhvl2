package com.osreboot.ridhvl2.template;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlCoord;
import com.osreboot.ridhvl2.menu.HvlEnvironment;

public abstract class HvlDisplay {

	public static final String LABEL = "HvlDisplay";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_EARLIEST + HvlChronology.CHRONOLOGY_INIT_INTERVAL,
	CHRONO_PRE_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_PRE_EARLIEST + HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_POST_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_POST_LATEST - HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_EXIT = HvlChronology.CHRONOLOGY_EXIT_LATE + HvlChronology.CHRONOLOGY_EXIT_INTERVAL,
	LAUNCH_CODE = 1,
	LAUNCH_CODE_RAW = 2;//2^1

	private static boolean active = false;

	private static HvlDisplay preLoadedDisplay;

	public static void preLoadDisplay(HvlDisplay displayArg){
		preLoadedDisplay = displayArg;
	}

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		active = HvlChronology.verifyLaunchCode(LAUNCH_CODE);

		GLFW.glfwInit();
		GLFWErrorCallback.createPrint(System.err).set();

		setDisplay(preLoadedDisplay);
	};

	@HvlChronologyUpdate(label = LABEL, chronology = CHRONO_PRE_UPDATE, launchCode = LAUNCH_CODE)
	public static final HvlAction.A2<Boolean, Float> ACTION_PRE_UPDATE = (debug, delta) -> {
		getDisplay().preUpdate(delta);
	};

	@HvlChronologyUpdate(label = LABEL, chronology = CHRONO_POST_UPDATE, launchCode = LAUNCH_CODE)
	public static final HvlAction.A2<Boolean, Float> ACTION_POST_UPDATE = (debug, delta) -> {
		getDisplay().postUpdate(delta);
	};

	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		try{
			setDisplay(null);

			GLFW.glfwTerminate();
			GLFW.glfwSetErrorCallback(null).free();
		}finally{
			active = false;
		}
	};

	private static void verifyActive(){
		if(!active) throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	private static HvlDisplay display;

	public static void setDisplay(HvlDisplay displayArg){
		verifyActive();

		if(display != null){
			display.unapply();
		}
		//TODO smooth transition (i.e. don't destroy display)

		display = displayArg;
		if(display != null){
			display.id = display.apply();
		}
	}

	public static HvlDisplay getDisplay(){
		verifyActive();

		return display;
	}

	public static void resizeViewport(int widthArg, int heightArg){
		GL11.glViewport(0, 0, widthArg, heightArg);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, widthArg, heightArg, 0, 1, -1);
	}

	public static void registerCallbacks(long idArg){
		GLFW.glfwSetScrollCallback(idArg, (window, xOffset, yOffset) -> {
			HvlMouse.deltaWheel += (float)yOffset;
		});
	}

	public static void clearCallbacks(long idArg){
		GLFW.glfwSetScrollCallback(idArg, null);
	}

	public static int getWidth(){
		return (int)Math.ceil(getDisplay().getSize().x);
	}

	public static int getHeight(){
		return (int)Math.ceil(getDisplay().getSize().y);
	}

	private long id;
	private String title;
	private boolean vsyncEnabled, resizable;
	//TODO iconPath

	protected HvlDisplay(String titleArg, boolean vsyncEnabledArg, boolean resizableArg){
		title = titleArg;
		vsyncEnabled = vsyncEnabledArg;
		resizable = resizableArg;
	}

	protected abstract long apply();
	protected abstract void unapply();

	protected abstract void preUpdate(float delta);
	protected abstract void postUpdate(float delta);

	public final long getId(){
		return id;
	}

	public abstract HvlCoord getSize();
	public abstract int getRefreshRate();

	public void setTitle(String titleArg){
		title = titleArg;
		if(display != null && display.getId() == id) GLFW.glfwSetWindowTitle(getId(), title);
	}

	public String getTitle(){
		return title;
	}

	public void setVsyncEnabled(boolean vsyncEnabledArg){
		vsyncEnabled = vsyncEnabledArg;
		if(display != null && display.getId() == id) GLFW.glfwSwapInterval(vsyncEnabled ? 1 : 0);
	}

	public boolean isVsyncEnabled(){
		return vsyncEnabled;
	}

	public void setResizable(boolean resizableArg){
		resizable = resizableArg;
		if(display != null && display.getId() == id) GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}

	public boolean isResizable(){
		return resizable;
	}

	public HvlEnvironment getEnvironment(){
		return new HvlEnvironment(0, 0, getSize().x, getSize().y, false);
	}

}
