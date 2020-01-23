package com.osreboot.ridhvl2;


/**
 * 
 * A simple utility that handles neatly printing messages to the console. In the future this will capture and 
 * format all console outputs, such as those from LWJGL.
 * 
 * @author os_reboot
 *
 */
public final class HvlLogger {
	
	private HvlLogger(){}
	
	/**
	 * A prefix used to indicate that a message has originated from outside Ridhvl's core library.
	 */
	public static final String PREFIX_EXTENSION = "+|";

	/**
	 * Prints a message to the console, with the method caller's class name clearly identified. Uses
	 * <code>Thread.currentThread().getStackTrace()[2].getClassName()</code> to get the method caller's 
	 * class name.
	 * 
	 * <p>
	 * 
	 * For {@linkplain com.osreboot.ridhvl2.template.HvlChronology HvlChronology} convenience purposes, this 
	 * method only runs if <code>debugArg</code> is true.
	 * 
	 * @param debugArg whether or not to print the message
	 * @param sArg the message to print
	 */
	public static void println(boolean debugArg, String sArg){
		if(debugArg){
			String name = Thread.currentThread().getStackTrace()[2].getClassName();
			name = name.substring(name.lastIndexOf('.') + 1);
			System.out.println("[" + name + "]: " + sArg);
		}
	}

	/**
	 * Prints a message to the console, with the alternative given class name clearly identified. A
	 * substitute for the {@linkplain #println(String)} method that specifies the class to blame.
	 * 
	 * <p>
	 * 
	 * For {@linkplain com.osreboot.ridhvl2.template.HvlChronology HvlChronology} convenience purposes, this 
	 * method only runs if <code>debugArg</code> is true.
	 * 
	 * @param debugArg whether or not to print the message
	 * @param cArg the alternative class to blame
	 * @param sArg the message to print
	 */
	public static void println(boolean debugArg, Class<?> cArg, String sArg){
		if(debugArg){
			System.out.println("[" + cArg.getSimpleName() + "]: " + sArg);
		}
	}

	/**
	 * Prints a message to the console, with the method caller's class name clearly identified. Uses
	 * <code>Thread.currentThread().getStackTrace()[2].getClassName()</code> to get the method caller's 
	 * class name.
	 * 
	 * @param sArg the message to print
	 */
	public static void println(String sArg){
		String name = Thread.currentThread().getStackTrace()[2].getClassName();
		name = name.substring(name.lastIndexOf('.') + 1);
		System.out.println("[" + name + "]: " + sArg);
	}

	/**
	 * Prints a message to the console, with the alternative given class name clearly identified. A
	 * substitute for the {@linkplain #println(String)} method that specifies the class to blame.
	 * 
	 * @param cArg the alternative class to blame
	 * @param sArg the message to print
	 */
	public static void println(Class<?> cArg, String sArg){
		System.out.println("[" + cArg.getSimpleName() + "]: " + sArg);
	}
	
	/**
	 * Prints a message to the console, with the method caller's class name clearly identified. Uses
	 * <code>Thread.currentThread().getStackTrace()[2].getClassName()</code> to get the method caller's 
	 * class name.
	 * 
	 * <p>
	 * 
	 * For clarity, the source name is accompanied by a {@linkplain #PREFIX_EXTENSION} to indicate that the
	 * message has originated from outside Ridhvl's core library.
	 * 
	 * <p>
	 * 
	 * For {@linkplain com.osreboot.ridhvl2.template.HvlChronology HvlChronology} convenience purposes, this 
	 * method only runs if <code>debugArg</code> is true.
	 * 
	 * @param debugArg whether or not to print the message
	 * @param sArg the message to print
	 */
	public static void printlnExtension(boolean debugArg, String sArg){
		if(debugArg){
			String name = Thread.currentThread().getStackTrace()[2].getClassName();
			name = name.substring(name.lastIndexOf('.') + 1);
			System.out.println("[" + PREFIX_EXTENSION + name + "]: " + sArg);
		}
	}

	/**
	 * Prints a message to the console, with the alternative given class name clearly identified. A
	 * substitute for the {@linkplain #printlnExtension(String)} method that specifies the class to blame.
	 * 
	 * <p>
	 * 
	 * For clarity, the source name is accompanied by a {@linkplain #PREFIX_EXTENSION} to indicate that the
	 * message has originated from outside Ridhvl's core library.
	 * 
	 * <p>
	 * 
	 * For {@linkplain com.osreboot.ridhvl2.template.HvlChronology HvlChronology} convenience purposes, this 
	 * method only runs if <code>debugArg</code> is true.
	 * 
	 * @param debugArg whether or not to print the message
	 * @param cArg the alternative class to blame
	 * @param sArg the message to print
	 */
	public static void printlnExtension(boolean debugArg, Class<?> cArg, String sArg){
		if(debugArg){
			System.out.println("[" + PREFIX_EXTENSION + cArg.getSimpleName() + "]: " + sArg);
		}
	}

	/**
	 * Prints a message to the console, with the method caller's class name clearly identified. Uses
	 * <code>Thread.currentThread().getStackTrace()[2].getClassName()</code> to get the method caller's 
	 * class name.
	 * 
	 * <p>
	 * 
	 * For clarity, the source name is accompanied by a {@linkplain #PREFIX_EXTENSION} to indicate that the
	 * message has originated from outside Ridhvl's core library.
	 * 
	 * @param sArg the message to print
	 */
	public static void printlnExtension(String sArg){
		String name = Thread.currentThread().getStackTrace()[2].getClassName();
		name = name.substring(name.lastIndexOf('.') + 1);
		System.out.println("[" + PREFIX_EXTENSION + name + "]: " + sArg);
	}

	/**
	 * Prints a message to the console, with the alternative given class name clearly identified. A
	 * substitute for the {@linkplain #printlnExtension(String)} method that specifies the class to blame.
	 * 
	 * <p>
	 * 
	 * For clarity, the source name is accompanied by a {@linkplain #PREFIX_EXTENSION} to indicate that the
	 * message has originated from outside Ridhvl's core library.
	 * 
	 * @param cArg the alternative class to blame
	 * @param sArg the message to print
	 */
	public static void printlnExtension(Class<?> cArg, String sArg){
		System.out.println("[" + PREFIX_EXTENSION + cArg.getSimpleName() + "]: " + sArg);
	}

}
