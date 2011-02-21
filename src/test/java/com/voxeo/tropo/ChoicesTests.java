package com.voxeo.tropo;

import static com.voxeo.tropo.Key.MODE;
import static com.voxeo.tropo.Key.TO;
import static com.voxeo.tropo.Key.VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.enums.Mode;

public class ChoicesTests {

	public void testChoice() {

		Tropo tropo = new Tropo();
		tropo.choices(VALUE("[5 DIGITS]"));
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"choices\":{\"value\":\"[5 DIGITS]\"}}]}");
	}
	
	@Test
	public void testChoiceWithMode() {

		Tropo tropo = new Tropo();
		tropo.choices(VALUE("[5 DIGITS]"),MODE(Mode.DTMF));
		
		assertEquals(tropo.text(),"{\"tropo\":[{\"choices\":{\"value\":\"[5 DIGITS]\",\"mode\":\"dtmf\"}}]}");
	}	
	
	@Test
	public void testChoicesFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.choices(TO("[5 DIGITS]"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'to' for action");
		}
	}	
}
