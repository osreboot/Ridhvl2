package com.osreboot.ridhvl2.menu;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class TestTag {

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
