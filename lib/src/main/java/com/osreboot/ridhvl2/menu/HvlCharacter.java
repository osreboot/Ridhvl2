package com.osreboot.ridhvl2.menu;

import java.io.Serializable;

import com.osreboot.ridhvl2.HvlCoord;

public class HvlCharacter implements Serializable{
	private static final long serialVersionUID = 8163782759638323329L;
	
	private HvlCoord uv0, uv1;
	
	// Constructor for Jackson deserialization
	@SuppressWarnings("unused")
	private HvlCharacter(){}
	
	public HvlCharacter(HvlCoord uv0Arg, HvlCoord uv1Arg){
		uv0 = uv0Arg;
		uv1 = uv1Arg;
	}
	
	public HvlCoord getUV0(){
		return uv0;
	}

	public HvlCoord getUV1(){
		return uv1;
	}
	
}
