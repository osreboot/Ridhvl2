package com.osreboot.ridhvl2;

public class HvlLogger {

	public static void println(String sArg){
		System.out.println("[" + Thread.currentThread().getStackTrace()[2].getClassName() + "]: " + sArg);
	}
	
	public static void println(Class<?> cArg, String sArg){
		System.out.println("[" + cArg.getSimpleName() + "]: " + sArg);
	}
	
}
