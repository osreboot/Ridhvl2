package com.osreboot.ridhvl2.menu;

public class HvlEnvironment{

	private float x, y, width, height;
	private boolean blocked;
	
	protected HvlEnvironment(){
		x = 0;
		y = 0;
		width = 0;
		height = 0;
		blocked = false;
	}
	
	public HvlEnvironment(float xArg, float yArg, float widthArg, float heightArg, boolean blockedArg){
		x = xArg;
		y = yArg;
		width = widthArg;
		height = heightArg;
		blocked = blockedArg;
	}

	void forceMutate(HvlEnvironment environmentArg){
		x = environmentArg.getX();
		y = environmentArg.getY();
		width = environmentArg.getWidth();
		height = environmentArg.getHeight();
		blocked = environmentArg.isBlocked();
	}
	
	void forceMutate(float xArg, float yArg, float widthArg, float heightArg, boolean blockedArg){
		x = xArg;
		y = yArg;
		width = widthArg;
		height = heightArg;
		blocked = blockedArg;
	}
	
	void forceMutateX(float xArg){
		x = xArg;
	}
	
	void forceMutateY(float yArg){
		y = yArg;
	}
	
	void forceMutateWidth(float widthArg){
		width = widthArg;
	}
	
	void forceMutateHeight(float heightArg){
		height = heightArg;
	}
	
	void forceMutateBlocked(boolean blockedArg){
		blocked = blockedArg;
	}
	
	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}

	public float getWidth(){
		return width;
	}

	public float getHeight(){
		return height;
	}

	public boolean isBlocked(){
		return blocked;
	}

}
