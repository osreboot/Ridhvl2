package com.osreboot.ridhvl2.test;

import static com.osreboot.ridhvl2.HvlStatics.hvlDraw;
import static com.osreboot.ridhvl2.HvlStatics.hvlQuad;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl2.template.HvlDisplayWindowed;
import com.osreboot.ridhvl2.template.HvlTemplateI;

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

}
