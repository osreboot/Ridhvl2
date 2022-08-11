package com.osreboot.ridhvl2.test;

import static com.osreboot.ridhvl2.HvlStatics.hvlDraw;
import static com.osreboot.ridhvl2.HvlStatics.hvlQuad;

import com.osreboot.ridhvl2.migration.Color;
import com.osreboot.ridhvl2.template.HvlDisplayWindowed;
import com.osreboot.ridhvl2.template.HvlTemplateI;

/**
 * A test class for {@linkplain HvlTemplateI}. This test creates a standard bordered window and draws a single blue
 * quad in the upper-left corner. The test also prints test messages into the console on program initialize and exit.
 * 
 * <p>
 * 
 * The expected test output consists of the creation of a non-resizable windowed 512x512 display running at 144Hz,
 * with a single blue quad in the upper-left corner. The message "Hello world!" should appear when the program
 * starts, and the message "Goodbye world!" should appear when the window is closed.
 * 
 * @author os_reboot
 *
 */
public class TestTemplateI extends HvlTemplateI{
	
	public TestTemplateI(){
		super(new HvlDisplayWindowed(144, 512, 512, "TestTemplateI", false));
	}

	@Override
	public void initialize(){
		System.out.println("Hello world!");
	}

	@Override
	public void update(float delta){
		hvlDraw(hvlQuad(10, 10, 64, 64), Color.blue);
	}
	
	@Override
	public void exit(){
		System.out.println("Goodbye world!");
	}

}
