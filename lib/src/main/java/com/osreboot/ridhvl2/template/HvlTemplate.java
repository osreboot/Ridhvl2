package com.osreboot.ridhvl2.template;

/**
 * A framework for creating basic program loops. HvlTemplate manages the creation and life cycle of a 
 * {@linkplain HvlTimer}. Upon construction, {@linkplain #initialize()} is called followed by repeated calls to
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
	
	private HvlTimer timer;
	private boolean exiting = false;
	
	/**
	 * Constructs an HvlTemplate instance. Also assigns the <code>newestInstance</code> variable and initializes 
	 * the {@linkplain HvlTimer}. To start the timer loop, call {@linkplain #start()}.
	 * 
	 * @param tickRateArg the rate at which {@linkplain #update(float)} is called, in Hz
	 */
	public HvlTemplate(double tickRateArg){
		newestInstance = this;
		
		timer = new HvlTimer(tickRateArg){
			@Override
			public void tick(float delta){
				preUpdate(delta);
				update(delta);
				postUpdate(delta);
				
				if(exiting){
					timer.setRunning(false);
					exit();
				}
			}
		};
	}
	
	/**
	 * Calls {@linkplain #initialize()} and starts the {@linkplain HvlTimer} loop.
	 */
	public final void start(){
		initialize();
		timer.start();
	}
	
	/**
	 * Called once immediately before the {@linkplain HvlTimer} loop starts.
	 * 
	 * <p>
	 * 
	 * HvlTemplate <i>subclass users</i> should implement this.
	 */
	public abstract void initialize();
	
	/**
	 * Called once at the start of every {@linkplain HvlTimer} loop cycle.
	 * 
	 * <p>
	 * 
	 * HvlTemplate <i>subclasses</i> should implement this.
	 * 
	 * @param delta the time (in seconds) since the last {@linkplain HvlTimer} loop cycle
	 */
	public abstract void preUpdate(float delta);
	
	/**
	 * Called once during every {@linkplain HvlTimer} loop cycle, after {@linkplain #preUpdate(float)} but before
	 * {@linkplain #postUpdate(float)}.
	 * 
	 * <p>
	 * 
	 * HvlTemplate <i>subclass users</i> should implement this.
	 * 
	 * @param delta the time (in seconds) since the last {@linkplain HvlTimer} loop cycle
	 */
	public abstract void update(float delta);
	
	/**
	 * Called once at the end of every {@linkplain HvlTimer} loop cycle.
	 * 
	 * <p>
	 * 
	 * HvlTemplate <i>subclasses</i> should implement this.
	 * 
	 * @param delta the time (in seconds) since the last {@linkplain HvlTimer} loop cycle
	 */
	public abstract void postUpdate(float delta);
	
	/**
	 * Called after the last {@linkplain HvlTimer} loop cycle (in the event that a program exit is 
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
	 * @return the {@linkplain HvlTimer} instance
	 */
	public HvlTimer getTimer(){
		return timer;
	}

	/**
	 * @return <code>true</code> if a program exit has been requested
	 */
	public boolean isExiting(){
		return exiting;
	}

	/**
	 * Gracefully requests a program exit. This will stop the {@linkplain HvlTimer} loop at the conclusion of the
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
