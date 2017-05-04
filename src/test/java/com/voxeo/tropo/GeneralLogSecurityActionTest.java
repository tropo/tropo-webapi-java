package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.voxeo.tropo.enums.LogSecurityState;

public class GeneralLogSecurityActionTest {

  @Test
  public void testGeneralLogSecurity() {

    Tropo tropo = new Tropo();
    tropo.generalLogSecurity(LogSecurityState.SUPPRESS);
    tropo.say("this is not logged", "nolog");
    tropo.generalLogSecurity(LogSecurityState.NONE);
    tropo.say("this will be logged", "log");

    assertEquals(tropo.text(),
        "{\"tropo\":[{\"generalLogSecurity\":\"suppress\"},{\"say\":[{\"value\":\"this is not logged\",\"name\":\"nolog\"}]},{\"generalLogSecurity\":\"none\"},{\"say\":[{\"value\":\"this will be logged\",\"name\":\"log\"}]}]}");
  }
}
