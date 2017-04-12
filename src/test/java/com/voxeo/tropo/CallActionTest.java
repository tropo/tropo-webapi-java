package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.voxeo.tropo.actions.CallAction;
import com.voxeo.tropo.enums.Channel;
import com.voxeo.tropo.enums.Network;
import com.voxeo.tropo.enums.Voice;

public class CallActionTest {

	@Test
	public void testCall() {

		Tropo tropo = new Tropo();
		tropo.call(Key.NAME("call"), Key.TO("foo"), Key.FROM("bar"), Key.NETWORK(Network.SMS), Key.CHANNEL(Channel.TEXT), Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false), Key.VOICE(Voice.TOM), Key.CALLBACK_URL("http://requestb.in/11fu6h91"), Key.PROMPT_LOG_SECURITY(), Key.LABEL("callLabel"));
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"call\",\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"voice\":\"tom\",\"callbackUrl\":\"http://requestb.in/11fu6h91\",\"promptLogSecurity\":\"suppress\",\"label\":\"callLabel\"}}]}");
	}

	@Test
	public void testCallFailsWithInvalidElement() {

		Tropo tropo = new Tropo();
		try {
			tropo.call(Key.BARGEIN(true), Key.FROM("bar"), Key.NETWORK(Network.SMS), Key.CHANNEL(Channel.TEXT), Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Invalid key 'bargein' for action");
		}
	}	
		
	@Test
	public void testAllowSignals() {
		
		Tropo tropo = new Tropo();
		tropo.call(Key.NAME("name"), Key.TO("foo"), Key.FROM("bar"), Key.NETWORK(Network.SMS), Key.CHANNEL(Channel.TEXT), Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false), Key.ALLOW_SIGNALS("exit","stopHold"));
	
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"name\",\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
	}
	
	@Test
	public void testCallwithToArray() {
		
		Tropo tropo = new Tropo();
		tropo.call(Key.NAME("call"), Key.TO("+14155551212","+15105551212"), Key.FROM("bar"), Key.NETWORK(Network.SMS), Key.CHANNEL(Channel.TEXT), Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false),Key.ALLOW_SIGNALS("exit","stopHold"));
	
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"call\",\"to\":[\"+14155551212\",\"+15105551212\"],\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
	}	

	@Test
	public void testCallTraditionalWithTo() {

		Tropo tropo = new Tropo();
		tropo.call("call", "+13055551212");
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"call\",\"to\":\"+13055551212\"}}]}");
	}

	@Test
	public void testCallTraditionalWithToAndNetwork() {

		Tropo tropo = new Tropo();
		tropo.call("call", "+13055551212",Network.SMS);
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"call\",\"to\":\"+13055551212\",\"network\":\"SMS\"}}]}");
	}

	@Test
	public void testCallTraditionalWithToAndNetworkAndFrom() {

		Tropo tropo = new Tropo();
		tropo.call("call", "+13055551212",Network.SMS,"+13055551234");
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"call\",\"to\":\"+13055551212\",\"network\":\"SMS\",\"from\":\"+13055551234\"}}]}");
	}

	@Test
	public void testCallTraditionalWithToAndNetworkAndFromAndChannel() {

		Tropo tropo = new Tropo();
		tropo.call("call", "+13055551212",Network.SMS,"+13055551234",Channel.TEXT);
							
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"call\",\"to\":\"+13055551212\",\"network\":\"SMS\",\"from\":\"+13055551234\",\"channel\":\"TEXT\"}}]}");
	}	
	
	@Test
	public void testFailsCallWithNoToParameter() {

		Tropo tropo = new Tropo();
		try {
			tropo.call(Key.NETWORK(Network.SMS));
			fail("Expected exception in test");
		} catch (TropoException te) {
			assertEquals(te.getMessage(), "Missing required property: 'to'");
		}
	}

	@Test
  public void testFailsCallWithNoNameParameter() {

    Tropo tropo = new Tropo();
    try {
      tropo.call(Key.TO("+13055551212"));
      fail("Expected exception in test");
    } catch (TropoException te) {
      assertEquals(te.getMessage(), "Missing required property: 'name'");
    }
  }

	@Test
	public void testCallWithHeadersTraditional() {

		Tropo tropo = new Tropo();
		CallAction call = tropo.call(Key.NAME("name"), Key.TO("foo"), Key.FROM("bar"), Key.NETWORK(Network.SMS), Key.CHANNEL(Channel.TEXT), Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false));
		call.headers(new String[]{"fooKey","fooValue"}, new String[]{"barKey","barValue"});
			
		assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"name\",\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"}}}]}");
	}

	@Test
  public void testNewCallWithHeadersTraditional() {

    Tropo tropo = new Tropo();
    Map<String, String> map = new LinkedHashMap<String, String>();
    map.put("fooKey", "fooValue");
    map.put("barKey", "barValue");
    tropo.call(Key.NAME("name"), Key.TO("foo"), Key.FROM("bar"), Key.NETWORK(Network.SMS), Key.CHANNEL(Channel.TEXT), Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false), Key.HEADERS(map));
      
    assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"name\",\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"headers\":{\"fooKey\":\"fooValue\",\"barKey\":\"barValue\"}}}]}");
  }

	@Test
  public void testMachineDetectionIsBoolean() {
    
    Tropo tropo = new Tropo();
    tropo.call(Key.NAME("name"), Key.TO("foo"), Key.FROM("bar"), Key.NETWORK(Network.SMS), Key.CHANNEL(Channel.TEXT), Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false), Key.MACHINE_DETECTION(true));
  
    assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"name\",\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"machineDetection\":true}}]}");
  }

	@Test
  public void testMachineDetection() {
    
    Tropo tropo = new Tropo();
    tropo.call(Key.NAME("name"), Key.TO("foo"), Key.FROM("bar"), Key.NETWORK(Network.SMS), Key.CHANNEL(Channel.TEXT), Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false), Key.MACHINE_DETECTION("If set to true, two parameters are available -- introduction and voice, and the field will be a Hash.", Voice.TOM));
  
    assertEquals(tropo.text(),"{\"tropo\":[{\"call\":{\"name\":\"name\",\"to\":\"foo\",\"from\":\"bar\",\"network\":\"SMS\",\"channel\":\"TEXT\",\"timeout\":10.0,\"answerOnMedia\":false,\"machineDetection\":{\"introduction\":\"If set to true, two parameters are available -- introduction and voice, and the field will be a Hash.\",\"voice\":\"tom\"}}}]}");
  }

}
