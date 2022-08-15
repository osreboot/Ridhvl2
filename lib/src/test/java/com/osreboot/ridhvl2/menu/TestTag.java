package com.osreboot.ridhvl2.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.osreboot.ridhvl2.template.HvlChronology;

public final class TestTag {

	@BeforeAll
	public static void setup(){
		HvlChronology.registerChronology(HvlType.class);
		HvlChronology.loadEvents(Long.MAX_VALUE, Long.MAX_VALUE);

		HvlChronology.initialize();
	}
	
	@AfterAll
	public static void cleanup(){
		HvlChronology.unloadEvents();
	}
	
	@Test
	public void testTagString(){
		HvlTag<String> tag = new HvlTag<String>(String.class, "test");
		
		assertEquals(String.class, tag.getType());
		assertEquals("test", tag.getKey());
	}
	
	@Test
	public void testTagFloat(){
		HvlTag<Float> tag = new HvlTag<Float>(Float.class, "float");
		
		assertEquals(Float.class, tag.getType());
		assertEquals("float", tag.getKey());
	}
	
	@Test
	public void testTagByte(){
		HvlTag<Byte> tag = new HvlTag<Byte>(Byte.class, "myByte");
		
		assertEquals(Byte.class, tag.getType());
		assertEquals("myByte", tag.getKey());
	}
	
}
