package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HangupActionTest {

	@Test
	public void testHangup() {
		
		Tropo tropo = new Tropo();
		tropo.hangup();
		
		assertEquals(tropo.text(), "{\"tropo\":[{\"hangup\":null}]}");
	}
}
