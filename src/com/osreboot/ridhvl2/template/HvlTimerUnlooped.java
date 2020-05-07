package com.osreboot.ridhvl2.template;

import com.google.gwt.core.client.Duration;

/**
 * TODO
 * 
 * @author os_reboot
 *
 */
public class HvlTimerUnlooped {

	/**
	 * Common values used to represent the maximum possible delta (in case the program briefly becomes 
	 * unresponsive).
	 */
	public static final double 
	MAXDELTA_UNLIMITED = Double.MAX_VALUE,
	MAXDELTA_SECOND = 1000,
	MAXDELTA_DECISECOND = 100,
	MAXDELTA_HALF_DECISECOND = 50,
	MAXDELTA_CENTISECOND = 10,
	MAXDELTA_MILLISECOND = 1;

	private float dilation = 1f, totalTimeS = 0f, lastDeltaS;
	//totalTimeMS <- not affected by dilation
	private double deltaMS, totalTimeMS, lastUpdateTimeMS, maxDeltaMS = MAXDELTA_UNLIMITED;

	public HvlTimerUnlooped(){}

	public void tick(){
		totalTimeMS = Duration.currentTimeMillis();
		deltaMS = Math.min(totalTimeMS - lastUpdateTimeMS, maxDeltaMS);
		lastUpdateTimeMS = totalTimeMS;
		if(deltaMS > 0 && deltaMS < totalTimeMS){
			totalTimeS += ((float)deltaMS / 1000) * dilation;
			lastDeltaS = (float)(deltaMS / 1000) * dilation;
		}
	}
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public float getLastDelta(){
		return lastDeltaS;
	}

	/**
	 * @return the number of <code>tick</code> calls per second (predicted based on the last <code>delta</code> value)
	 */
	public float getTickRate(){
		return 1f/((float)deltaMS / 1000);
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
	public double getMaxDeltaMS(){
		return maxDeltaMS;
	}

	/**
	 * Sets the maximum value (in milliseconds) to restrict <code>delta</code> to.
	 * 
	 * @param maxDeltaArg the new maximum <code>delta</code> restriction
	 */
	public void setMaxDelta(double maxDeltaArg){
		maxDeltaMS = maxDeltaArg;
	}

}
