package com.osreboot.ridhvl2;

public class HvlLogger {

	public static void println(String sArg){
		String name = Thread.currentThread().getStackTrace()[2].getClassName();
		name = name.substring(name.lastIndexOf('.') + 1);
		System.out.println("[" + name + "]: " + sArg);
	}
	
	public static void println(Class<?> cArg, String sArg){
		System.out.println("[" + cArg.getSimpleName() + "]: " + sArg);
	}
	
}
