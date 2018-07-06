package com.osreboot.ridhvl2.template;

import org.lwjgl.Sys;

/**
 * 
 * A managed single-thread loop that repeatedly calls <code>update(float delta)</code>. Can handle time
 * dilation, tracks total time elapsed, and calculates the time between <code>update</code> calls (in 
 * seconds)(<code>delta</code>).
 * 
 * @author os_reboot
 *
 */
public abstract class HvlTimer {

	/**
	 * Common values used to represent the maximum possible delta (in case the program briefly becomes 
	 * unresponsive).
	 */
	public static final long MAXDELTA_UNLIMITED = Long.MAX_VALUE,
			MAXDELTA_SECOND = 1000,
			MAXDELTA_DECISECOND = 100,
			MAXDELTA_HALF_DECISECOND = 50,
			MAXDELTA_CENTISECOND = 10,
			MAXDELTA_MILLISECOND = 1;

	private float dilation = 1f, totalTimeS = 0f;
	//totalTimeMS <- not affected by dilation
	private long deltaMS, totalTimeMS, lastUpdateTimeMS, maxDeltaMS = MAXDELTA_UNLIMITED;
	private boolean running = false;

	public HvlTimer(){}

	/**
	 * Starts the loop. This method will not exit until <code>setRunning(false)</code> is called.
	 */
	public final void start(){
		running = true;
		while(running){
			totalTimeMS = (Sys.getTime() * 1000) / Sys.getTimerResolution();
			deltaMS = Math.min(totalTimeMS - lastUpdateTimeMS, maxDeltaMS);
			lastUpdateTimeMS = totalTimeMS;
			if(deltaMS > 0 && deltaMS < totalTimeMS){
				totalTimeS += ((float)deltaMS / 1000) * dilation;
				update(((float)deltaMS / 1000) * dilation);
			}
		}
	}

	public abstract void update(float delta);

	public float getUpdateRate(){
		return 1f/((float)deltaMS / 1000);
	}

	public boolean isRunning(){
		return running;
	}

	public void setRunning(boolean runningArg){
		running = runningArg;
	}

	public float getDilation(){
		return dilation;
	}

	public void setDilation(float dilationArg){
		dilation = dilationArg;
	}

	@Deprecated
	public float getTotalTimeS() {
		return totalTimeS;
	}

	@Deprecated
	public long getMaxDeltaMS(){
		return maxDeltaMS;
	}

	@Deprecated
	public void setMaxDeltaMS(long maxDeltaMSArg){
		maxDeltaMS = maxDeltaMSArg;
	}

}
