package com.voxeo.tropo;

import static com.voxeo.tropo.Key.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.Do;
import com.voxeo.tropo.actions.TransferAction;



public class TransferActionTest {

	@Test
	public void testTransfer() {

		Tropo tropo = new Tropo();
		tropo.transfer(TO("tel:+14157044517"));

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"to\":\"tel:+14157044517\"}}]}");
	}

	@Test
	public void testTransferWithOnAndChoices() {

		Tropo tropo = new Tropo();
		tropo.transfer(TO("tel:+14157044517")).and(
			Do.on(EVENT("unbounded"), NEXT("/error.json")),
			Do.choices(VALUE("[5 DIGITS]"))
		);

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"to\":\"tel:+14157044517\",\"on\":[{\"event\":\"unbounded\",\"next\":\"/error.json\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}


  @Test
  public void testTransferWithAnswerOnMedia() {
    Tropo tropo = new Tropo();
    tropo.transfer(TO("tel:+14157044517"),ANSWER_ON_MEDIA(true));
    assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"to\":\"tel:+14157044517\",\"answerOnMedia\":true}}]}");
  }


	@Test
	public void testTransferFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.transfer(TO("tel:+14157044517"), BEEP(true));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'beep' for action");
		}
	}

	@Test
	public void testFailsRedirectWithNoToParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.transfer(FROM("4155551212"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'to'");
		}
	}

	@Test
	public void testTransferTraditionalWay() {

		Tropo tropo = new Tropo();
		tropo.transfer("tel:+14157044517");

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"to\":\"tel:+14157044517\"}}]}");
	}

	@Test
	public void testTransferTraditionalWay2() {

		Tropo tropo = new Tropo();
		tropo.transfer("tel:+14157044517","tel:+14157044522");

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"to\":\"tel:+14157044517\",\"from\":\"tel:+14157044522\"}}]}");
	}

	@Test
	public void testAllowSignals() {

		Tropo tropo = new Tropo();
		tropo.transfer(TO("tel:+14157044517"),ALLOW_SIGNALS("exit","stopHold"));

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"to\":\"tel:+14157044517\",\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
	}

	@Test
	public void testTransferWithHeadersTraditional() {

		Tropo tropo = new Tropo();
		TransferAction transfer = tropo.transfer(TO("tel:+14157044517"));
		transfer.headers(new String[]{"fooKey","fooValue"}, new String[]{"barKey","barValue"});

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"to\":\"tel:+14157044517\",\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"}}}]}");
	}


	@Test
	public void testTransferWithOnAndChoicesTraditional() {

		Tropo tropo = new Tropo();
		TransferAction transfer = tropo.transfer(TO("tel:+14157044517"));
		transfer.on(EVENT("unbounded"), NEXT("/error.json"));
		transfer.choices(VALUE("[5 DIGITS]"));

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"to\":\"tel:+14157044517\",\"on\":[{\"event\":\"unbounded\",\"next\":\"/error.json\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}
}
