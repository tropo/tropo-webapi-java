package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WaitActionTest {

  @Test
  public void testWait() {

    Tropo tropo = new Tropo();
    tropo.say("Connected!", "connected");
    tropo.serverWait(Key.ALLOW_SIGNALS("exit", "quit"), Key.MILLISECONDS(10000L));
    tropo.say("Bye!", "bye");

    assertEquals(tropo.text(), "{\"tropo\":[{\"say\":[{\"value\":\"Connected!\",\"name\":\"connected\"}]},{\"wait\":{\"allowSignals\":[\"exit\",\"quit\"],\"milliseconds\":10000}},{\"say\":[{\"value\":\"Bye!\",\"name\":\"bye\"}]}]}");
  }
}
