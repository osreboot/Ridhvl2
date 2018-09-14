package com.osreboot.ridhvl2.template;

public abstract class HvlTemplate {

	private static HvlTemplate newestInstance;
	
	public static HvlTemplate newest(){
		return newestInstance;
	}
	
	private HvlTimer timer;
	
	public HvlTemplate(int dontCareArg){//TODO better solution than this
		newestInstance = this;
	}
	
	public final void start(){
		timer = new HvlTimer(){
			@Override
			public void tick(float delta){
				preUpdate(delta);
				update(delta);
				postUpdate(delta);
			}
		};
		
		initialize();
		
		timer.start();
	}
	
	public abstract void initialize();
	
	public abstract void preUpdate(float delta);
	public abstract void update(float delta);
	public abstract void postUpdate(float delta);

	public HvlTimer getTimer(){
		return timer;
	}
	
}
