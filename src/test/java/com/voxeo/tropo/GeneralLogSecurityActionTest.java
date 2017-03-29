package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.voxeo.tropo.enums.LogSecurityState;

public class GeneralLogSecurityActionTest {

  @Test
  public void testGeneralLogSecurity() {

    Tropo tropo = new Tropo();
    tropo.generalLogSecurity(LogSecurityState.SUPPRESS);
    tropo.say("this is not logged");
    tropo.generalLogSecurity(LogSecurityState.NONE);
    tropo.say("this will be logged");

    assertEquals(tropo.text(),
        "{\"tropo\":[{\"generalLogSecurity\":\"suppress\"},{\"say\":[{\"value\":\"this is not logged\"}]},{\"generalLogSecurity\":\"none\"},{\"say\":[{\"value\":\"this will be logged\"}]}]}");
  }
}
