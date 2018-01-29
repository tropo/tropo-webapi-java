package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.Do;


public class RedirectActionTest {
	
	@Test
	public void testRedirect() {
		
		Tropo tropo = new Tropo();
		tropo.redirect(Key.TO("sip:1234"),Key.REQUIRED(true));
		
		assertEquals(tropo.text(),  "{\"tropo\":[{\"redirect\":{\"to\":\"sip:1234\",\"required\":true}}]}");
	}	

	@Test
	public void testFailsNestedRedirect() {

		Tropo tropo = new Tropo();
		try {
			tropo.conference(Key.NAME("foo"),Key.ID("1234")).and(
					Do.redirect(Key.TO("sip:1234"),Key.NAME("redirect")));
						
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Redirect should only be used alone and before the session is answered, use transfer instead");
		}
	}
	
	@Test
	public void testFailsRedirectWithNoToParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.redirect(Key.NAME("redirect"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'to'");
		}
	}

	@Deprecated
  public void testFailsRedirectWithNoNameParameter() {

    Tropo tropo = new Tropo();
    try {
      tropo.redirect(Key.TO("sip:1234"));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(), "Missing required property: 'name'");
    }
  }

	@Test
	public void testRedirectFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.redirect(Key.BARGEIN(true));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'bargein' for action");
		}
	}
	
}
