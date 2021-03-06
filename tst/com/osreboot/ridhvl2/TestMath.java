package com.osreboot.ridhvl2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
		
		HashSet<Integer> testAccumulator = new HashSet<Integer>();
		for(int i=0; i<10000; i++) {
			testAccumulator.add(HvlMath.randomInt(2, 5));
		}
		for(int i = 2; i <= 5; i++) {
			assertTrue(testAccumulator.contains(i));	
		}	
		
		testAccumulator.clear();
		
		for(int i=0; i<10000; i++) {
			testAccumulator.add(HvlMath.randomInt(5, 2));
		}
		for(int i = 2; i <= 5; i++) {
			assertTrue(testAccumulator.contains(i));	
		}
	}
	
	@Test
	public void testRandomFloat() {
		assertEquals(1, HvlMath.randomFloat(1, 1),0);
		
		ArrayList<Float> testAccumulator = new ArrayList<Float>();
		float highestPoint = 2;
		float lowestPoint = 5;
		
		for(int i=0; i<10000; i++) {
			testAccumulator.add(HvlMath.randomFloat(2, 5));
		}
		
		for(int i=0; i<testAccumulator.size(); i++) {

			if(testAccumulator.get(i) > highestPoint) {
				highestPoint = testAccumulator.get(i);
			}
			
			if(testAccumulator.get(i) < lowestPoint) {
				lowestPoint = testAccumulator.get(i);
			}	
		}
	
		assertTrue(lowestPoint > 2);
		assertTrue(highestPoint < 5);
		assertTrue(lowestPoint < 2.2);
		assertTrue(highestPoint > 4.8);
		
		testAccumulator.clear();
	}
}
