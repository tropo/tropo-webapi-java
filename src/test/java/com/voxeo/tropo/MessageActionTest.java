package com.voxeo.tropo;

import static com.voxeo.tropo.Key.ANSWER_ON_MEDIA;
import static com.voxeo.tropo.Key.BARGEIN;
import static com.voxeo.tropo.Key.CHANNEL;
import static com.voxeo.tropo.Key.FROM;
import static com.voxeo.tropo.Key.NETWORK;
import static com.voxeo.tropo.Key.TIMEOUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.voxeo.tropo.actions.MessageAction.Say;
import com.voxeo.tropo.enums.Channel;
import com.voxeo.tropo.enums.Network;
import com.voxeo.tropo.enums.Voice;

public class MessageActionTest {

	@Test
	public void testMessage() {

		Tropo tropo = new Tropo();
		tropo.message(Key.SAY_OF_MESSAGE(new Say("This is an announcement"), new Say("Remember, you have a meeting at 2 PM")), Key.TO("305551212"), Key.ANSWER_ON_MEDIA(false), Key.CHANNEL(Channel.VOICE), Key.FROM("3055551000"),Key.NETWORK(Network.PSTN), Key.REQUIRED(true), Key.TIMEOUT(10.1f), Key.VOICE(Voice.TOM), Key.PROMPT_LOG_SECURITY());
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"message\":{\"say\":[{\"value\":\"This is an announcement\"},{\"value\":\"Remember, you have a meeting at 2 PM\"}],\"to\":\"305551212\",\"answerOnMedia\":false,\"channel\":\"VOICE\",\"from\":\"3055551000\",\"network\":\"PSTN\",\"required\":true,\"timeout\":10.1,\"voice\":\"tom\",\"promptLogSecurity\":\"suppress\"}}]}");
	}
	
	@Test
	public void testMessageFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.message(BARGEIN(true), FROM("bar"), NETWORK(Network.SMS), CHANNEL(Channel.TEXT), TIMEOUT(10.0f), ANSWER_ON_MEDIA(false));			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'bargein' for action");
		}
	}

	@Test
	public void testMessageShowSMS() {

		Tropo tropo = new Tropo();
		tropo.message(Key.SAY_OF_MESSAGE(new Say("This is an announcement")), Key.TO("+13055551212"), Key.NETWORK(Network.SMS));
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"message\":{\"say\":[{\"value\":\"This is an announcement\"}],\"to\":\"+13055551212\",\"network\":\"SMS\"}}]}");
	}

	@Test
  public void testMessageWithVoice() {

    Tropo tropo = new Tropo();
    tropo.message(Key.SAY_OF_MESSAGE(new Say("This is an announcement")), Key.TO("+13055551212"), Key.FROM("3055551000"), Key.VOICE(Voice.KATE), Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false));
              
    assertEquals(tropo.text(),"{\"tropo\":[{\"message\":{\"say\":[{\"value\":\"This is an announcement\"}],\"to\":\"+13055551212\",\"from\":\"3055551000\",\"voice\":\"kate\",\"timeout\":10.0,\"answerOnMedia\":false}}]}");
  }

	@Test
	public void testFailsMessageWithNoToParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.message(Key.NETWORK(Network.SMS));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'to'");
		}
	}

	@Test
  public void testFailsMessageWithNoSayParameter() {

    Tropo tropo = new Tropo();
    try {
      tropo.message(Key.TO("+13055551212"));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(), "Missing required property: 'say'");
    }
  }

	@Deprecated
  public void testFailsMessageWithNoNameParameter() {

    Tropo tropo = new Tropo();
    try {
      tropo.message(Key.TO("+13055551212"), Key.SAY_OF_MESSAGE(new Say("This is an announcement")));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(), "Missing required property: 'name'");
    }
  }

	@Test
  public void testFailsMessageWithNoSayValue() {

    Tropo tropo = new Tropo();
    try {
      tropo.message(Key.SAY_OF_MESSAGE(new Say(null)), Key.TO("+13055551212"), Key.NAME("message"));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(), "Missing required property: value of message.say");
    }
  }

	@Test
  public void testFailsMessageWithNoSayValue1() {

    Tropo tropo = new Tropo();
    try {
      tropo.message(Key.SAY_OF_MESSAGE(new Say("")), Key.TO("+13055551212"), Key.NAME("message"));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(), "Missing required property: value of message.say");
    }
  }
}
