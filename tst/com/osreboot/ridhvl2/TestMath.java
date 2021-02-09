package com.osreboot.ridhvl2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class TestMath {

	@Test
	public void testStepTowards(){
		assertEquals(5, HvlMath.stepTowards(0, 5, 10), 0);
		assertEquals(-5, HvlMath.stepTowards(0, 5, -10), 0);
		assertEquals(10, HvlMath.stepTowards(0, 20, 10), 0);
		assertEquals(-10, HvlMath.stepTowards(0, 20, -10), 0);
		assertEquals(1, HvlMath.stepTowards(0, -1, 2), 0);
		assertEquals(-1, HvlMath.stepTowards(0, -1, -2), 0);
	}
	
	@Test
	public void testMap(){
		assertEquals(1, HvlMath.map(0, 0, 1, 1, 2), 0);
		assertEquals(-1, HvlMath.map(0, 0, 1, -1, -2), 0);
		assertEquals(3, HvlMath.map(2, 0, 1, 1, 2), 0);
		assertEquals(0, HvlMath.map(-1, 0, 1, 1, 2), 0);
		assertEquals(2, HvlMath.map(1, 1, 0, 2, 1), 0);
		assertEquals(1, HvlMath.map(0, 1, 0, 2, 1), 0);
		assertEquals(0.5, HvlMath.map(1, 0, 2, 0, 1), 0);
	}
	
	@Test
	public void testLimit(){
		assertEquals(1, HvlMath.limit(5, -1, 1), 0);
		assertEquals(-1, HvlMath.limit(-5, -1, 1), 0);
		assertEquals(1, HvlMath.limit(5, 1, -1), 0);
		assertEquals(-1, HvlMath.limit(-5, 1, -1), 0);
		assertEquals(0, HvlMath.limit(0, 5, -5), 0);
		assertEquals(3, HvlMath.limit(3, 5, -5), 0);
		assertEquals(10, HvlMath.limit(0, 10, 10), 0);
		assertEquals(10, HvlMath.limit(10, 10, 10), 0);
	}
	
	@Test
	public void testRandomInt() {
		assertEquals(1, HvlMath.randomInt(1, 1),0);
		int testAccumulator = 0;
		for(int i=0; i<1000; i++) {
			testAccumulator += HvlMath.randomInt(0, 10);
		}
		assertEquals(5, testAccumulator/1000, 1);
	}
	
}
