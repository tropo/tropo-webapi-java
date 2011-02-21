package com.voxeo.tropo;

import static com.voxeo.tropo.Key.BARGEIN;
import static com.voxeo.tropo.Key.FROM;
import static com.voxeo.tropo.Key.ID;
import static com.voxeo.tropo.Key.NAME;
import static com.voxeo.tropo.Key.TO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.Do;


public class RedirectActionTest {
	
	@Test
	public void testRedirect() {
		
		Tropo tropo = new Tropo();
		tropo.redirect(TO("sip:1234"),FROM("4155551212"));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"redirect\":{\"to\":\"sip:1234\",\"from\":\"4155551212\"}}]}");
	}	

	@Test
	public void testFailsNestedRedirect() {

		Tropo tropo = new Tropo();
		try {
			tropo.conference(NAME("foo"),ID("1234")).and(
					Do.redirect(TO("sip:1234"),FROM("4155551212")));
						
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Redirect should only be used alone and before the session is answered, use transfer instead");
		}
	}
	
	@Test
	public void testFailsRedirectWithNoToParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.redirect(FROM("4155551212"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'to'");
		}
	}

	@Test
	public void testRedirectFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.redirect(BARGEIN(true),FROM("4155551212"));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'bargein' for action");
		}
	}
	
	@Test
	public void testRedirectTraditional() {
		
		Tropo tropo = new Tropo();
		tropo.redirect("sip:1234","4155551212");
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"redirect\":{\"to\":\"sip:1234\",\"from\":\"4155551212\"}}]}");
	}
	
	@Test
	public void testRedirectTraditional2() {
		
		Tropo tropo = new Tropo();
		tropo.redirect("sip:1234");
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"redirect\":{\"to\":\"sip:1234\"}}]}");
	}
}
