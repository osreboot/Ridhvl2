package com.osreboot.ridhvl2;

import com.osreboot.ridhvl2.template.HvlChronology;

/**
 * A simple framework that provides methods useful for parameter-based functionality. Updated from the original
 * Ridhvl to be interfaces so they can work as Java 8 lambda expressions.
 * 
 * <p>
 * 
 * Example:<br>
 * <code>void runAction(HvlAction.A1<String> actionArg){<br>
 * &nbspactionArg.run("test");<br>
 * }<br></code>
 * ...<br>
 * <code>runAction((arg) -> System.out.println(arg));//prints "test" to console</code>
 * 
 * <p>
 * 
 * Interface names always start with 'A' followed by the number of arguments. If the interface name ends with 'r',
 * then the first type parameter represents the HvlAction's return type (otherwise the HvlAction is void).
 * 
 * <p>
 * 
 * {@linkplain HvlChronology} and TODO {@linkplain HvlMenu} depend heavily on this framework.
 * 
 * @author os_reboot
 *
 */
public final class HvlAction {
	
	private HvlAction(){}

	public static interface A0{
		public void run();
	}
	
	public static interface A1<A>{
		public void run(A a);
	}
	
	public static interface A2<A,B>{
		public void run(A a, B b);
	}
	
	public static interface A3<A,B,C>{
		public void run(A a, B b, C c);
	}
	
	public static interface A4<A,B,C,D>{
		public void run(A a, B b, C c, D d);
	}
	
	public static interface A5<A,B,C,D,E>{
		public void run(A a, B b, C c, D d, E e);
	}
	
	public static interface A6<A,B,C,D,E,F>{
		public void run(A a, B b, C c, D d, E e, F f);
	}
	
	public static interface A0r<R>{
		public R run();
	}
	
	public static interface A1r<R,A>{
		public R run(A a);
	}
	
	public static interface A2r<R,A,B>{
		public R run(A a, B b);
	}
	
	public static interface A3r<R,A,B,C>{
		public R run(A a, B b, C c);
	}
	
	public static interface A4r<R,A,B,C,D>{
		public R run(A a, B b, C c, D d);
	}
	
	public static interface A5r<R,A,B,C,D,E>{
		public R run(A a, B b, C c, D d, E e);
	}
	
	public static interface A6r<R,A,B,C,D,E,F>{
		public R run(A a, B b, C c, D d, E e, F f);
	}
	
}
