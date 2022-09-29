package com.osreboot.ridhvl2.template;

import org.lwjgl.glfw.GLFW;

/**
 * 
 * A managed single-thread loop that repeatedly calls <code>tick(float delta)</code>. Can handle time
 * dilation, tracks total time elapsed, and calculates the time between <code>tick</code> calls (in 
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
	public static final long 
	MAXDELTA_UNLIMITED = Long.MAX_VALUE,
	MAXDELTA_SECOND = 1000,
	MAXDELTA_DECISECOND = 100,
	MAXDELTA_HALF_DECISECOND = 50,
	MAXDELTA_CENTISECOND = 10,
	MAXDELTA_MILLISECOND = 1;

	private static final long
	NANOS_PER_SECOND = 1000L * 1000L * 1000L;

	private final double tickRate;
	private float dilation = 1f, totalTimeS = 0f;
	//totalTimeMS <- not affected by dilation
	private long deltaMS, totalTimeMS, lastUpdateTimeMS, maxDeltaMS = MAXDELTA_UNLIMITED;
	private boolean running = false;

	private long tickNextNS;

	private long[] historicSleepNS = new long[10];
	private long[] historicYieldNS = new long[10];
	private int historicSleepOffset, historicYieldOffset;

	public HvlTimer(double tickRateArg){
		tickRate = tickRateArg;

		for(int i = 0; i < historicSleepNS.length; i++) historicSleepNS[i] = 1000L * 1000L;
		for(int i = 0; i < historicYieldNS.length; i++) historicYieldNS[i] = 0L;

		historicSleepOffset = 0;
		historicYieldOffset = 0;

		tickNextNS = (long)(GLFW.glfwGetTime() * NANOS_PER_SECOND);
	}

	/**
	 * Starts the loop. This method will not exit until <code>setRunning(false)</code> is called.
	 */
	public final void start(){
		running = true;
		while(running){
			// Handle updating external methods / values
			totalTimeMS = (long)(GLFW.glfwGetTime() * 1000.0);
			deltaMS = Math.min(totalTimeMS - lastUpdateTimeMS, maxDeltaMS);
			lastUpdateTimeMS = totalTimeMS;
			if(deltaMS > 0 && deltaMS < totalTimeMS){
				totalTimeS += ((float)deltaMS / 1000) * dilation;
				tick(((float)deltaMS / 1000) * dilation);
			}

			// Handle sleeping until next tick (inspiration: https://github.com/LWJGL/lwjgl/blob/master/src/java/org/lwjgl/opengl/Sync.java)
			try{
				long timeToNextTickNS = tickNextNS - getTimeNS();
				
				// Sleep as long as possible
				long timeSleep;
				while(timeToNextTickNS > historicAverage(historicSleepNS)){
					timeSleep = getTimeNS();
					Thread.sleep(1);
					historicSleepNS[historicSleepOffset++ % historicSleepNS.length] = getTimeNS() - timeSleep;
					timeToNextTickNS = tickNextNS - getTimeNS();
				}

				// Dampen to avoid excessive yields
				if(historicAverage(historicSleepNS) > 10L * 1000L * 1000L){
					for(int i = 0; i < historicSleepNS.length; i++) historicSleepNS[i] *= 0.9;
				}
				
				// Yield as long as possible
				long timeYield;
				while(timeToNextTickNS > historicAverage(historicYieldNS)){
					timeYield = getTimeNS();
					Thread.yield();
					historicYieldNS[historicYieldOffset++ % historicYieldNS.length] = getTimeNS() - timeYield;
					timeToNextTickNS = tickNextNS - getTimeNS();
				}
			}catch(InterruptedException e){}

			tickNextNS = Math.max(tickNextNS + (NANOS_PER_SECOND / (long)tickRate), getTimeNS());
		}
	}

	private long getTimeNS(){
		return (long)(GLFW.glfwGetTime() * NANOS_PER_SECOND);
	}

	private long historicAverage(long[] history){
		long sum = 0;
		for(int i = 0; i < history.length; i++) sum += history[i];
		return sum / history.length;
	}

	/**
	 * Called once per loop.
	 * 
	 * @param delta the time (in seconds) since the last <code>tick</code> call
	 */
	public abstract void tick(float delta);

	/**
	 * @return the number of <code>tick</code> calls per second (predicted based on the last <code>delta</code> value)
	 */
	public float getTickRate(){
		return 1f/((float)deltaMS / 1000);
	}

	/**
	 * @return whether or not the loop is running
	 */
	public boolean isRunning(){
		return running;
	}

	/**
	 * Sets the loop's <code>running</code> value. The loop will not cycle again if this is set 
	 * to false.
	 * 
	 * @param runningArg the new <code>running</code> value
	 */
	public void setRunning(boolean runningArg){
		running = runningArg;
	}

	/**
	 * @return the current <code>dilation</code> value of the loop
	 */
	public float getDilation(){
		return dilation;
	}

	/**
	 * Sets the loop's <code>dilation</code> value. This is the value by which <code>delta</code> is 
	 * multiplied every cycle. This setting affects the <code>totalTime</code> of the loop. This is 
	 * useful for creating bullet-time effects or similar.
	 * 
	 * @param dilationArg the new <code>dilation</code> value
	 */
	public void setDilation(float dilationArg){
		dilation = dilationArg;
	}

	/**
	 * @return the time (in seconds) since the loop started
	 */
	public float getTotalTime() {
		return totalTimeS;
	}

	/**
	 * @return the maximum value (in milliseconds) that <code>delta</code> is restricted to
	 */
	public long getMaxDeltaMS(){
		return maxDeltaMS;
	}

	/**
	 * Sets the maximum value (in milliseconds) to restrict <code>delta</code> to.
	 * 
	 * @param maxDeltaArg the new maximum <code>delta</code> restriction
	 */
	public void setMaxDelta(long maxDeltaArg){
		maxDeltaMS = maxDeltaArg;
	}

}
