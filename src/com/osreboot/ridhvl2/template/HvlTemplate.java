package com.osreboot.ridhvl2.template;

import com.google.gwt.user.client.Timer;

/**
 * A framework for creating basic program loops. HvlTemplate manages the creation and life cycle of a 
 * {@linkplain HvlTimerUnlooped}. Upon construction, {@linkplain #initialize()} is called followed by repeated calls to
 * {@linkplain #preUpdate(float)}, {@linkplain #update(float)} and then {@linkplain #postUpdate(float)} (in order).
 * HvlTemplate is designed to be subclassed such that functionality can be added for systems like content loaders,
 * display management, chronology handling and similar, and then subclassed <i>again</i> to add actual program 
 * functionality.
 * 
 * <p>
 * 
 * NOTE: Users that aren't looking to directly extend the functionality of Ridhvl2 should instead explore one of
 * HvlTemplate's subclasses (such as {@linkplain HvlTemplateI}).
 * 
 * <p>
 * 
 * For documentation purposes, the creators of the second subclass will be called "subclass users". Subclasses
 * should implement functionality for {@linkplain #preUpdate(float)} and {@linkplain #postUpdate(float)}, but
 * should leave {@linkplain #initialize()} and {@linkplain #update(float)} abstract so subclass users can implement
 * them. See {@linkplain HvlTemplateI} for an example of proper subclassing.
 * 
 * @author os_reboot
 *
 */
public abstract class HvlTemplate {

	private static HvlTemplate newestInstance;
	
	/**
	 * @return the most recently constructed HvlTemplate instance
	 */
	public static HvlTemplate newest(){
		return newestInstance;
	}
	
	private HvlTimerUnlooped timerUnlooped;
	private Timer timer;
	private int refreshRate;
	
	private boolean exiting = false;
	
	/**
	 * Constructs an HvlTemplate instance. Also assigns the <code>newestInstance</code> variable and initializes 
	 * the {@linkplain HvlTimerUnlooped}. To start the timer loop, call {@linkplain #start()}.
	 * 
	 * @param dontCareArg mandates the calling of the constructor, serves no other purpose
	 */
	public HvlTemplate(int dontCareArg, int refreshRateArg){//TODO better solution than this
		newestInstance = this;
		refreshRate = refreshRateArg;
		
		timerUnlooped = new HvlTimerUnlooped();
		
		timer = new Timer(){
			@Override
			public void run(){
				timerUnlooped.tick();
				
				preUpdate(timerUnlooped.getLastDelta());
				update(timerUnlooped.getLastDelta());
				postUpdate(timerUnlooped.getLastDelta());
				
				if(exiting){
					timer.cancel();
					exit();
				}
			}
		};
	}
	
	/**
	 * Calls {@linkplain #initialize()} and starts the {@linkplain HvlTimerUnlooped} loop.
	 */
	public final void start(){
		initialize();
		timer.scheduleRepeating((int)(1000f / (float)refreshRate));
	}
	
	/**
	 * Called once immediately before the {@linkplain HvlTimerUnlooped} loop starts.
	 * 
	 * <p>
	 * 
	 * HvlTemplate <i>subclass users</i> should implement this.
	 */
	public abstract void initialize();
	
	/**
	 * Called once at the start of every {@linkplain HvlTimerUnlooped} loop cycle.
	 * 
	 * <p>
	 * 
	 * HvlTemplate <i>subclasses</i> should implement this.
	 * 
	 * @param delta the time (in seconds) since the last {@linkplain HvlTimerUnlooped} loop cycle
	 */
	public abstract void preUpdate(float delta);
	
	/**
	 * Called once during every {@linkplain HvlTimerUnlooped} loop cycle, after {@linkplain #preUpdate(float)} but before
	 * {@linkplain #postUpdate(float)}.
	 * 
	 * <p>
	 * 
	 * HvlTemplate <i>subclass users</i> should implement this.
	 * 
	 * @param delta the time (in seconds) since the last {@linkplain HvlTimerUnlooped} loop cycle
	 */
	public abstract void update(float delta);
	
	/**
	 * Called once at the end of every {@linkplain HvlTimerUnlooped} loop cycle.
	 * 
	 * <p>
	 * 
	 * HvlTemplate <i>subclasses</i> should implement this.
	 * 
	 * @param delta the time (in seconds) since the last {@linkplain HvlTimerUnlooped} loop cycle
	 */
	public abstract void postUpdate(float delta);
	
	/**
	 * Called after the last {@linkplain HvlTimerUnlooped} loop cycle (in the event that a program exit is 
	 * requested). This method does NOT request a program exit. To request a program exit, call 
	 * {@linkplain #setExiting()}.
	 * 
	 * <p>
	 * 
	 * HvlTemplate <i>subclass users</i> should implement this.
	 * 
	 */
	public void exit(){}

	/**
	 * @return the {@linkplain HvlTimerUnlooped} instance
	 */
	public HvlTimerUnlooped getTimer(){
		return timerUnlooped;
	}

	/**
	 * @return <code>true</code> if a program exit has been requested
	 */
	public boolean isExiting(){
		return exiting;
	}

	/**
	 * Gracefully requests a program exit. This will stop the {@linkplain HvlTimerUnlooped} loop at the conclusion of the
	 * next cycle. When the loop is stopped, {@linkplain #exit()} will automatically be called once. Using this
	 * method to close programs gives Ridhvl2 utilities a chance to exit.
	 * 
	 * <p>
	 * 
	 * This method is automatically called by {@linkplain HvlDisplay} subclasses in the event of a window closure.
	 */
	public void setExiting(){
		exiting = true;
	}
	
}
