package com.osreboot.ridhvl2.migration;

import java.nio.DoubleBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import com.osreboot.ridhvl2.HvlCoord;
import com.osreboot.ridhvl2.template.HvlDisplay;

public class Mouse{

	public static float dWheel = 0;
	
	public static int getX(){
		return (int)getMousePosition().x;
	}
	
	public static int getY(){
		return Display.getHeight() - (int)getMousePosition().y;
	}
	
	private static HvlCoord getMousePosition(){
		HvlCoord output = new HvlCoord();
		try(MemoryStack stack = MemoryStack.stackPush()){
			DoubleBuffer x = stack.mallocDouble(1);
			DoubleBuffer y = stack.mallocDouble(1);
			GLFW.glfwGetCursorPos(HvlDisplay.getDisplay().getId(), x, y);
			output.set((float)x.get(), (float)y.get());
		}
		return output;
	}
	
	public static boolean isButtonDown(int buttonArg){
		return GLFW.glfwGetMouseButton(HvlDisplay.getDisplay().getId(), buttonArg) == GLFW.GLFW_PRESS;
	}
	
	public static float getDWheel(){
		return dWheel;
	}
	
}
