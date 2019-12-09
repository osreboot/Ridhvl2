package com.osreboot.ridhvl2.menu;

import com.osreboot.ridhvl2.HvlStatics;

public class HvlEnvironmentVolatile extends HvlEnvironment{

	private boolean expiredX, expiredY, expiredWidth, expiredHeight, expiredBlocked;
	
	private float x, y, width, height;
	private boolean blocked;
	
	public HvlEnvironmentVolatile(float xArg, float yArg, float widthArg, float heightArg, boolean blockedArg) {
		super(0, 0, 0, 0, false);
		x = xArg;
		y = yArg;
		width = widthArg;
		height = heightArg;
		blocked = blockedArg;
		expiredX = false;
		expiredY = false;
		expiredWidth = false;
		expiredHeight = false;
		expiredBlocked = false;
	}
	
	/**
	 * <b><i>IMPORTANT: this method should only be used by internal Ridhvl2 processes!</i></b>
	 * 
	 * <p>
	 * 
	 * The point of HvlEnvironmentVolatile is to reveal unstable memory location pitfalls while using
	 * {@linkplain HvlStatics}' shortcut methods. This method actively counteracts that feature! Improper use
	 * will result in values being unintentionally mutated at random times, without warning. For a safe and flexible
	 * alternative, construct an instance of {@linkplain HvlEnvironmentMutable} from this HvlEnvironmentVolatile instance
	 * and immediately discard the latter.
	 * 
	 * @param xArg
	 * @param yArg
	 * @param widthArg
	 * @param heightArg
	 * @param blockedArg
	 */
	@Deprecated
	public void setAndRefresh(float xArg, float yArg, float widthArg, float heightArg, boolean blockedArg){
		x = xArg;
		y = yArg;
		width = widthArg;
		height = heightArg;
		blocked = blockedArg;
		expiredX = false;
		expiredY = false;
		expiredWidth = false;
		expiredHeight = false;
		expiredBlocked = false;
	}
	
	@Override
	public float getX(){
		if(expiredX)
			throw new ExpiredEnvironmentException();
		else{
			expiredX = true;
			return x;
		}
	}

	@Override
	public float getY(){
		if(expiredY)
			throw new ExpiredEnvironmentException();
		else{
			expiredY = true;
			return y;
		}
	}

	@Override
	public float getWidth(){
		if(expiredWidth)
			throw new ExpiredEnvironmentException();
		else{
			expiredWidth = true;
			return width;
		}
	}

	@Override
	public float getHeight(){
		if(expiredHeight)
			throw new ExpiredEnvironmentException();
		else{
			expiredHeight = true;
			return height;
		}
	}

	@Override
	public boolean isBlocked(){
		if(expiredBlocked)
			throw new ExpiredEnvironmentException();
		else{
			expiredBlocked = true;
			return blocked;
		}
	}
	
	/**
	 * Thrown if an attempt is made to access a value from an HvlEnvironmentVolatile instance after the value has
	 * already been used. To avoid this exception, copy the environment's values into a new
	 * {@linkplain HvlEnvironmentMutable} instance or other representative data structure.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class ExpiredEnvironmentException extends RuntimeException{
		private static final long serialVersionUID = -3793094739537436830L;
	}


}
