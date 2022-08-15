package com.osreboot.ridhvl2.menu;

public class HvlEnvironmentMutable extends HvlEnvironment{

	private float x, y, width, height;
	private boolean blocked;
	
	public HvlEnvironmentMutable(){
		set(0, 0, 0, 0, false);
	}
	
	public HvlEnvironmentMutable(float xArg, float yArg, float widthArg, float heightArg, boolean blockedArg){
		set(xArg, yArg, widthArg, heightArg, blockedArg);
	}
	
	public HvlEnvironmentMutable(HvlEnvironment environmentArg){
		set(environmentArg);
	}
	
	public void set(float xArg, float yArg, float widthArg, float heightArg, boolean blockedArg){
		x = xArg;
		y = yArg;
		width = widthArg;
		height = heightArg;
		blocked = blockedArg;
	}
	
	public void set(HvlEnvironment environmentArg){
		x = environmentArg.getX();
		y = environmentArg.getY();
		width = environmentArg.getWidth();
		height = environmentArg.getHeight();
		blocked = environmentArg.isBlocked();
	}
	
	public void setX(float xArg){
		x = xArg;
	}
	
	public void setY(float yArg){
		y = yArg;
	}
	
	public void setWidth(float widthArg){
		width = widthArg;
	}
	
	public void setHeight(float heightArg){
		height = heightArg;
	}
	
	public void setBlocked(boolean blockedArg){
		blocked = blockedArg;
	}
	
	@Override
	public float getX(){
		return x;
	}

	@Override
	public float getY(){
		return y;
	}

	@Override
	public float getWidth(){
		return width;
	}

	@Override
	public float getHeight(){
		return height;
	}

	@Override
	public boolean isBlocked(){
		return blocked;
	}

}
