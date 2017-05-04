package com.voxeo.tropo;

import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.NEXT;
import static com.voxeo.tropo.Key.VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.voxeo.tropo.actions.AskAction.Say;
import com.voxeo.tropo.actions.Do;
import com.voxeo.tropo.actions.TransferAction;
import com.voxeo.tropo.enums.Mode;
import com.voxeo.tropo.enums.Terminator;
import com.voxeo.tropo.enums.Voice;


public class TransferActionTest {

	@Test
	public void testTransfer() {

		Tropo tropo = new Tropo();
		tropo.transfer(Key.NAME("transfer"), Key.TO("tel:+14157044517"));

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"name\":\"transfer\",\"to\":\"tel:+14157044517\"}}]}");
	}

	@Test
	public void testTransferWithOnAndChoices() {

		Tropo tropo = new Tropo();
		tropo.transfer(Key.NAME("transfer"),Key.TO("tel:+14157044517"),Key.CHOICES(Terminator.POUND)).and(
			Do.on(EVENT("unbounded"), NEXT("/error.json")));

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"name\":\"transfer\",\"to\":\"tel:+14157044517\",\"choices\":{\"terminator\":\"#\"},\"on\":[{\"event\":\"unbounded\",\"next\":\"/error.json\"}]}}]}");
	}


  @Test
  public void testTransferWithAnswerOnMedia() {
    Tropo tropo = new Tropo();
    tropo.transfer(Key.NAME("transfer"),Key.TO("tel:+14157044517"),Key.ANSWER_ON_MEDIA(true));
    assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"name\":\"transfer\",\"to\":\"tel:+14157044517\",\"answerOnMedia\":true}}]}");
  }


	@Test
	public void testTransferFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.transfer(Key.NAME("transfer"), Key.TO("tel:+14157044517"), Key.BEEP(true));
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'beep' for action");
		}
	}

	@Test
	public void testTransferFailsWithNoToParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.transfer(Key.FROM("4155551212"));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'to'");
		}
	}

	@Test
  public void testTransferFailsWithNoNameParameter() {

    Tropo tropo = new Tropo();
    try {
      tropo.transfer(Key.TO("4155551212"));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(), "Missing required property: 'name'");
    }
  }

	@Test
	public void testTransferTraditionalWay() {

		Tropo tropo = new Tropo();
		tropo.transfer("tel:+14157044517", "transfer");

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"to\":\"tel:+14157044517\",\"name\":\"transfer\"}}]}");
	}

	@Test
	public void testTransferTraditionalWay2() {

		Tropo tropo = new Tropo();
		tropo.transfer("tel:+14157044517","tel:+14157044522","transfer");

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"to\":\"tel:+14157044517\",\"from\":\"tel:+14157044522\",\"name\":\"transfer\"}}]}");
	}

	@Test
	public void testAllowSignals() {

		Tropo tropo = new Tropo();
		tropo.transfer(Key.NAME("transfer"),Key.TO("tel:+14157044517"),Key.ALLOW_SIGNALS("exit","stopHold"));

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"name\":\"transfer\",\"to\":\"tel:+14157044517\",\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
	}

	@Test
	public void testTransferWithHeadersTraditional() {

		Tropo tropo = new Tropo();
		TransferAction transfer = tropo.transfer(Key.NAME("transfer"),Key.TO("tel:+14157044517"));
		transfer.headers(new String[]{"fooKey","fooValue"}, new String[]{"barKey","barValue"});

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"name\":\"transfer\",\"to\":\"tel:+14157044517\",\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"}}}]}");
	}

	@Test
  public void testTransferWithHeaders() {

    Tropo tropo = new Tropo();
    Map<String, String> headers = new LinkedHashMap<String, String>();
    headers.put("fooKey","fooValue");
    headers.put("barKey","barValue");
    tropo.transfer(Key.NAME("transfer"),Key.TO("tel:+14157044517"),Key.HEADERS(headers));

    assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"name\":\"transfer\",\"to\":\"tel:+14157044517\",\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"}}}]}");
  }

	@Test
	public void testTransferWithOnAndChoicesTraditional() {

		Tropo tropo = new Tropo();
		TransferAction transfer = tropo.transfer(Key.NAME("transfer"),Key.TO("tel:+14157044517"));
		transfer.on(EVENT("unbounded"), NEXT("/error.json"));
		transfer.choices(VALUE("[5 DIGITS]"));

		assertEquals(tropo.text(), "{\"tropo\":[{\"transfer\":{\"name\":\"transfer\",\"to\":\"tel:+14157044517\",\"on\":[{\"event\":\"unbounded\",\"next\":\"/error.json\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
	}

	@Test
  public void testTransferWithOnAndAsk() {

    Tropo tropo = new Tropo();
    tropo.say(Key.NAME("say"), Key.VALUE("you are now being transfered"));
    TransferAction transfer = tropo.transfer(Key.NAME("transfer"), Key.TO("14075550100"), Key.FROM("14075550122"),
        Key.TIMEOUT(60f), Key.ANSWER_ON_MEDIA(false), Key.REQUIRED(true), Key.ALLOW_SIGNALS("exit", "quit"),
        Key.MACHINE_DETECTION(false), Key.CHOICES(Terminator.ZERO), Key.INTERDIGIT_TIMEOUT(5.5f), Key.RING_REPEAT(5),
        Key.PLAY_TONES(true), Key.VOICE(Voice.TOM), Key.CALLBACK_URL("http://localhost:8080/tropo"),
        Key.LABEL("transferLabel"));
    transfer.and(Do.on(Key.EVENT("ring")).say("http://www.phono.com/audio/holdmusic.mp3","say"));
    transfer.and(Do.on(Key.EVENT("connect")).ask(Key.ATTEMPTS(3),
        Key.SAY_OF_ASK(new Say("Sorry. Please enter you 5 digit account number again.", "nomatch"),
            new Say("Sorry, I did not hear anything.", "timeout"), new Say("Please enter 5 digit account number")),
        Key.REQUIRED(true), Key.BARGEIN(true), Key.TIMEOUT(10f), Key.NAME("ask"),
        Key.CHOICES_OF_ASK(new com.voxeo.tropo.actions.AskAction.Choices("[5 DIGITS]", Mode.DTMF))));

    assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"name\":\"say\",\"value\":\"you are now being transfered\"}]},{\"transfer\":{\"name\":\"transfer\",\"to\":\"14075550100\",\"from\":\"14075550122\",\"timeout\":60.0,\"answerOnMedia\":false,\"required\":true,\"allowSignals\":[\"exit\",\"quit\"],\"machineDetection\":false,\"choices\":{\"terminator\":\"0\"},\"interdigitTimeout\":5.5,\"ringRepeat\":5,\"playTones\":true,\"voice\":\"tom\",\"callbackUrl\":\"http://localhost:8080/tropo\",\"label\":\"transferLabel\",\"on\":[{\"event\":\"ring\",\"say\":[{\"value\":\"http://www.phono.com/audio/holdmusic.mp3\",\"name\":\"say\"}]},{\"event\":\"connect\",\"ask\":{\"attempts\":3,\"say\":[{\"value\":\"Sorry. Please enter you 5 digit account number again.\",\"event\":\"nomatch\"},{\"value\":\"Sorry, I did not hear anything.\",\"event\":\"timeout\"},{\"value\":\"Please enter 5 digit account number\"}],\"required\":true,\"bargein\":true,\"timeout\":10.0,\"name\":\"ask\",\"choices\":{\"value\":\"[5 DIGITS]\",\"mode\":\"DTMF\"}}}]}}]}");
  }
}
