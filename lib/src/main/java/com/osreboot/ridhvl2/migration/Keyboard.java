package com.osreboot.ridhvl2.migration;

import java.util.HashSet;

import org.lwjgl.glfw.GLFW;

import com.osreboot.ridhvl2.template.HvlDisplay;

public class Keyboard{

	public static final int KEY_ESCAPE          = GLFW.GLFW_KEY_ESCAPE;
//	public static final int KEY_1               = 0x02;
//	public static final int KEY_2               = 0x03;
//	public static final int KEY_3               = 0x04;
//	public static final int KEY_4               = 0x05;
//	public static final int KEY_5               = 0x06;
//	public static final int KEY_6               = 0x07;
//	public static final int KEY_7               = 0x08;
//	public static final int KEY_8               = 0x09;
//	public static final int KEY_9               = 0x0A;
//	public static final int KEY_0               = 0x0B;
//	public static final int KEY_MINUS           = 0x0C; /* - on main keyboard */
//	public static final int KEY_EQUALS          = 0x0D;
//	public static final int KEY_BACK            = 0x0E; /* backspace */
//	public static final int KEY_TAB             = 0x0F;
//	public static final int KEY_Q               = 0x10;
	public static final int KEY_W               = GLFW.GLFW_KEY_W;
//	public static final int KEY_E               = 0x12;
//	public static final int KEY_R               = 0x13;
//	public static final int KEY_T               = 0x14;
//	public static final int KEY_Y               = 0x15;
//	public static final int KEY_U               = 0x16;
//	public static final int KEY_I               = 0x17;
//	public static final int KEY_O               = 0x18;
//	public static final int KEY_P               = 0x19;
//	public static final int KEY_LBRACKET        = 0x1A;
//	public static final int KEY_RBRACKET        = 0x1B;
//	public static final int KEY_RETURN          = 0x1C; /* Enter on main keyboard */
	public static final int KEY_LCONTROL        = GLFW.GLFW_KEY_LEFT_CONTROL;
	public static final int KEY_A               = GLFW.GLFW_KEY_A;
	public static final int KEY_S               = GLFW.GLFW_KEY_S;
	public static final int KEY_D               = GLFW.GLFW_KEY_D;
//	public static final int KEY_F               = 0x21;
//	public static final int KEY_G               = 0x22;
//	public static final int KEY_H               = 0x23;
//	public static final int KEY_J               = 0x24;
//	public static final int KEY_K               = 0x25;
//	public static final int KEY_L               = 0x26;
//	public static final int KEY_SEMICOLON       = 0x27;
//	public static final int KEY_APOSTROPHE      = 0x28;
//	public static final int KEY_GRAVE           = 0x29; /* accent grave */
	public static final int KEY_LSHIFT          = GLFW.GLFW_KEY_LEFT_SHIFT;
//	public static final int KEY_BACKSLASH       = 0x2B;
//	public static final int KEY_Z               = 0x2C;
	public static final int KEY_X               = GLFW.GLFW_KEY_X;
	public static final int KEY_C               = GLFW.GLFW_KEY_C;
//	public static final int KEY_V               = 0x2F;
//	public static final int KEY_B               = 0x30;
//	public static final int KEY_N               = 0x31;
//	public static final int KEY_M               = 0x32;
//	public static final int KEY_COMMA           = 0x33;
//	public static final int KEY_PERIOD          = 0x34; /* . on main keyboard */
//	public static final int KEY_SLASH           = 0x35; /* / on main keyboard */
//	public static final int KEY_RSHIFT          = 0x36;
//	public static final int KEY_LMENU           = 0x38; /* left Alt */
//	public static final int KEY_SPACE           = 0x39;
	
	public static HashSet<Integer> keysDown = new HashSet<>();
	
	public static boolean isKeyDown(int keyArg){
		//return keysDown.contains(keyArg);
		return GLFW.glfwGetKey(HvlDisplay.getDisplay().getId(), keyArg) == GLFW.GLFW_PRESS;
	}
	
}
