package com.osreboot.ridhvl2.migration;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import com.osreboot.ridhvl2.template.HvlDisplay;

public class Display{

	public static int getWidth(){
		int width;
		try(MemoryStack stack = MemoryStack.stackPush()){
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			GLFW.glfwGetWindowSize(HvlDisplay.getDisplay().getId(), w, h);
			width = w.get(0);
		}
		return width;
	}

	public static int getHeight(){
		int height;
		try(MemoryStack stack = MemoryStack.stackPush()){
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			GLFW.glfwGetWindowSize(HvlDisplay.getDisplay().getId(), w, h);
			height = h.get(0);
		}
		return height;
	}

}
