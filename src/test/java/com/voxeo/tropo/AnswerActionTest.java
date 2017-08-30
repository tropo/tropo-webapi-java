package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.voxeo.tropo.actions.AnswerAction;

public class AnswerActionTest {

  @Test
  public void testAnswer() {

    Tropo tropo = new Tropo();
    tropo.answer();
    tropo.say("Hello, you were the first to answer.", "say");

    assertEquals(tropo.text(),
        "{\"tropo\":[{\"answer\":{}},{\"say\":[{\"value\":\"Hello, you were the first to answer.\",\"name\":\"say\"}]}]}");
  }

  @Test
  public void testAnswerFailsWithInvalidElement() {

    Tropo tropo = new Tropo();
    try {
      tropo.answer(Key.NAME("foo"));
      fail("Expected exception in test");
    }
    catch (TropoException te) {
      assertEquals(te.getMessage(), "Invalid key 'name' for action");
    }
  }

  @Test
  public void testAnswerWithHeadersTraditional() {

    Tropo tropo = new Tropo();
    AnswerAction answer = tropo.answer();
    answer.headers(new String[] {"P-Header", "value goes here"}, new String[] {"Remote-Party-ID",
        "\"John Doe\"<sip:jdoe@foo.com>;party=calling;id-type=subscriber;privacy=full;screen=yes"});
    tropo.say("Hello, you were the first to answer.", "say");

    assertEquals(tropo.text(),
        "{\"tropo\":[{\"answer\":{\"headers\":{\"P-Header\":\"value goes here\",\"Remote-Party-ID\":\"\\\"John Doe\\\"<sip:jdoe@foo.com>;party=calling;id-type=subscriber;privacy=full;screen=yes\"}}},{\"say\":[{\"value\":\"Hello, you were the first to answer.\",\"name\":\"say\"}]}]}");
  }

  @Test
  public void testNewAnswerWithHeadersTraditional() {

    Tropo tropo = new Tropo();
    Map<String, String> map = new LinkedHashMap<String, String>();
    map.put("P-Header", "value goes here");
    map.put("Remote-Party-ID",
        "\"John Doe\"<sip:jdoe@foo.com>;party=calling;id-type=subscriber;privacy=full;screen=yes");
    tropo.answer(Key.HEADERS(map));
    tropo.say("Hello, you were the first to answer.", "say");

    assertEquals(tropo.text(),
        "{\"tropo\":[{\"answer\":{\"headers\":{\"P-Header\":\"value goes here\",\"Remote-Party-ID\":\"\\\"John Doe\\\"<sip:jdoe@foo.com>;party=calling;id-type=subscriber;privacy=full;screen=yes\"}}},{\"say\":[{\"value\":\"Hello, you were the first to answer.\",\"name\":\"say\"}]}]}");
  }

}
