package com.osreboot.ridhvl2.test;

import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlTimer;

/**
 * A test class for {@linkplain HvlTimer}. This test demonstrates all aspects of HvlTimer functionality. The test
 * starts by printing messages to the console at one second intervals, then sets the timer dilation to 0.5 and
 * repeats the test output at half speed, then sets the dilation to 2.0 and repeats the test output at double speed,
 * then simulates program idle time and repeats the test output after a short program delay (at normal speed).
 * 
 * <p>
 * 
 * The expected test output consists of four individual counts to ten, with (in order) one second delay, half second
 * delay, two second delay and standard delay after a short idle period. The test should conclude by printing the
 * message "Test completed!" in the console.
 * 
 * @author os_reboot
 *
 */
public class TestTimer extends HvlTimer{
	
	public TestTimer(){
		super(144f);
		
		HvlLogger.println("Starting timer...");
		setMaxDelta(MAXDELTA_CENTISECOND);
		start();
		HvlLogger.println("Test completed!");
	}
	
	int secondCounts = 0;
	int stage = 0;
	
	@Override
	public void tick(float delta){
		if(secondCounts < getTotalTime() - (stage * 10)){
			secondCounts++;
			HvlLogger.println("Time elapsed: " + secondCounts + " seconds @ " + getTickRate() + " Hz.");
			if(secondCounts >= 10){
				secondCounts = 0;
				stage++;
				doStageUpdate();
			}
		}
	}
	
	private void doStageUpdate(){
		if(stage == 1){
			HvlLogger.println("Setting dilation to 0.5f...");
			setDilation(0.5f);
		}else if(stage == 2){
			HvlLogger.println("Setting dilation to 2.0f...");
			setDilation(2f);
		}else if(stage == 3){
			setDilation(1f);
			HvlLogger.println("Simulating program idle to test max delta...");
			try{
				Thread.sleep(5000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}else{
			HvlLogger.println("Stopping timer...");
			setRunning(false);
		}
	}

}
