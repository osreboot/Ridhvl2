package com.osreboot.ridhvl2;

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
