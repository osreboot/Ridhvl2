package com.osreboot.ridhvl2.template;

import java.nio.DoubleBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlCoord;

public final class HvlMouse{

	public static final String LABEL = "HvlMouse";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_LATE - (3 * HvlChronology.CHRONOLOGY_INIT_INTERVAL),
	CHRONO_PRE_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE - (2 * HvlChronology.CHRONOLOGY_UPDATE_INTERVAL),
	CHRONO_POST_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_POST_MIDDLE - (2 * HvlChronology.CHRONOLOGY_UPDATE_INTERVAL),
	CHRONO_EXIT = HvlChronology.CHRONOLOGY_EXIT_MIDDLE - (3 * HvlChronology.CHRONOLOGY_EXIT_INTERVAL),
	LAUNCH_CODE = 8,
	LAUNCH_CODE_RAW = 256;//2^8

	private static boolean active = false;

	private static HvlCoord location;
	static float deltaWheel;

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		active = HvlChronology.verifyLaunchCode(LAUNCH_CODE);

		location = new HvlCoord();
		deltaWheel = 0f;
	};

	@HvlChronologyUpdate(label = LABEL, chronology = CHRONO_PRE_UPDATE, launchCode = LAUNCH_CODE)
	public static final HvlAction.A2<Boolean, Float> ACTION_PRE_UPDATE = (debug, delta) -> {
		// Update location
		try(MemoryStack stack = MemoryStack.stackPush()){
			DoubleBuffer x = stack.mallocDouble(1);
			DoubleBuffer y = stack.mallocDouble(1);
			GLFW.glfwGetCursorPos(HvlDisplay.getDisplay().getId(), x, y);
			location.set((float)x.get(), (float)y.get());
		}
	};

	@HvlChronologyUpdate(label = LABEL, chronology = CHRONO_POST_UPDATE, launchCode = LAUNCH_CODE)
	public static final HvlAction.A2<Boolean, Float> ACTION_POST_UPDATE = (debug, delta) -> {
	};

	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		active = false;

		location = null;
	};

	private static void verifyActive(){
		if(!active) throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static final int
	BUTTON_LEFT = GLFW.GLFW_MOUSE_BUTTON_LEFT,
	BUTTON_MIDDLE = GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
	BUTTON_RIGHT = GLFW.GLFW_MOUSE_BUTTON_RIGHT;

	public static int getX(){
		verifyActive();

		return Math.round(location.x);
	}

	public static int getY(){
		verifyActive();

		return Math.round(location.y);
	}

	public static HvlCoord getLocation(){
		return new HvlCoord(location); // TODO immutable coordinate when possible
	}

	public static boolean isButtonDown(int buttonArg){
		verifyActive();

		return GLFW.glfwGetMouseButton(HvlDisplay.getDisplay().getId(), buttonArg) == GLFW.GLFW_PRESS;
	}
	
	public static float pollWheel(){
		float output = deltaWheel;
		deltaWheel = 0f;
		return output;
	}

}
