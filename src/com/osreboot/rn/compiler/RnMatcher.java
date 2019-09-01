package com.osreboot.rn.compiler;

public abstract class RnMatcher {

	private String identifier;

	public RnMatcher(String identifierArg){
		identifier = identifierArg;
	}

	@Override
	public boolean equals(Object objectArg){
		return objectArg instanceof RnMatcher &&
				((RnMatcher)objectArg).identifier.equals(identifier);
	}

}
