package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HangupTest {

  @Test
  public void testHangup() {

    Tropo tropo = new Tropo();
    tropo.hangup();

    assertEquals(tropo.text(), "{\"tropo\":[{\"hangup\":null}]}");
  }

  @Test
  public void testHangupWithSay() {

    Tropo tropo = new Tropo();
    tropo.say(Key.NAME("say1"), Key.VALUE("Hello,this is tropo.com"));
    tropo.hangup();
    tropo.say(Key.NAME("say2"), Key.VALUE(
        "Tropo makes it easy to add capabilities like calls, text messages, and conferences into any application or business process."));

    assertEquals(tropo.text(),
        "{\"tropo\":[{\"say\":[{\"name\":\"say1\",\"value\":\"Hello,this is tropo.com\"}]},{\"hangup\":null},{\"say\":[{\"name\":\"say2\",\"value\":\"Tropo makes it easy to add capabilities like calls, text messages, and conferences into any application or business process.\"}]}]}");
  }

  @Test
  public void testHangupWithCall() {
    Tropo tropo = new Tropo();
    tropo.call("call", "+14155551212");
    tropo.say(Key.NAME("say1"), Key.VALUE("Hello,this is tropo.com"));
    tropo.hangup();
    tropo.say(Key.NAME("say2"), Key.VALUE(
        "Tropo makes it easy to add capabilities like calls, text messages, and conferences into any application or business process."));

    assertEquals(tropo.text(),
        "{\"tropo\":[{\"call\":{\"name\":\"call\",\"to\":\"+14155551212\"}},{\"say\":[{\"name\":\"say1\",\"value\":\"Hello,this is tropo.com\"}]},{\"hangup\":null},{\"say\":[{\"name\":\"say2\",\"value\":\"Tropo makes it easy to add capabilities like calls, text messages, and conferences into any application or business process.\"}]}]}");
  }
}
