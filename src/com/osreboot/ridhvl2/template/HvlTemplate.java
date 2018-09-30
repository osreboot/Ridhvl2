package com.osreboot.ridhvl2.template;

public abstract class HvlTemplate {

	private static HvlTemplate newestInstance;
	
	public static HvlTemplate newest(){
		return newestInstance;
	}
	
	private HvlTimer timer;
	private boolean exiting = false;
	
	public HvlTemplate(int dontCareArg){//TODO better solution than this
		newestInstance = this;
		
		timer = new HvlTimer(){
			@Override
			public void tick(float delta){
				preUpdate(delta);
				update(delta);
				postUpdate(delta);
				
				if(exiting){
					exit();
					timer.setRunning(false);
				}
			}
		};
	}
	
	public final void start(){
		initialize();
		timer.start();
	}
	
	public abstract void initialize();
	
	public abstract void preUpdate(float delta);
	public abstract void update(float delta);
	public abstract void postUpdate(float delta);
	
	public void exit(){}

	public HvlTimer getTimer(){
		return timer;
	}

	public boolean isExiting(){
		return exiting;
	}

	public void setExiting(){
		exiting = true;
	}
	
}
