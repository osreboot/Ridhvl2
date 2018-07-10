package com.osreboot.ridhvl2.test;

import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlTimer;

public class TestTimer extends HvlTimer{
	
	public static void main(String[] args){
		HvlLogger.println("Starting timer...");
		HvlTimer timer = new TestTimer();
		timer.setMaxDelta(MAXDELTA_CENTISECOND);
		timer.start();
		HvlLogger.println("Test completed successfully!");
	}
	
	int secondCounts = 0;
	int stage = 0;
	
	@Override
	public void update(float delta){
		if(secondCounts < getTotalTime() - (stage * 10)){
			secondCounts++;
			HvlLogger.println("Time elapsed: " + secondCounts + " @ " + getUpdateRate() + " Hz.");
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
