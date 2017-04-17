package com.voxeo.tropo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.voxeo.tropo.actions.AskAction.Choices;
import com.voxeo.tropo.actions.AskAction.Say;
import com.voxeo.tropo.enums.AsrLogSecurity;
import com.voxeo.tropo.enums.Mode;
import com.voxeo.tropo.enums.Recognizer;
import com.voxeo.tropo.enums.Voice;

public class AskActionTest {
    
    @Before
    public void setUp() throws Exception {
    
    }
    
    @Test
    public void testAsk() {
    
        Tropo tropo = new Tropo();
        tropo.ask(Key.CHOICES("[1 DIGIT]"), Key.SAY_OF_ASK(new Say("Please say a digit")), Key.NAME("foo"), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true));
        
        assertEquals(tropo.text(), "{\"tropo\":[{\"ask\":{\"choices\":{\"value\":\"[1 DIGIT]\"},\"say\":[{\"value\":\"Please say a digit\"}],\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true}}]}");
    }
    
    @Test
    public void testAskWithSensitivity() {
    
        Tropo tropo = new Tropo();
        tropo.ask(Key.CHOICES("[1 DIGIT]"), Key.SAY_OF_ASK(new Say("Please say a digit")), Key.NAME("foo"), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.SENSITIVITY(30.1f), Key.REQUIRED(true));
        
        assertEquals(tropo.text(),
                "{\"tropo\":[{\"ask\":{\"choices\":{\"value\":\"[1 DIGIT]\"},\"say\":[{\"value\":\"Please say a digit\"}],\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"sensitivity\":30.1,\"required\":true}}]}");
    }

    @Test
    public void testNewAskWithSayAndOnBlocks() {

      Tropo tropo = new Tropo();
      tropo.ask(Key.NAME("foo"), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true), Key.SAY_OF_ASK(new Say("Please say your account number")), Key.CHOICES("[5 DIGITS]"));
      tropo.on(Key.EVENT("continue"), Key.NEXT("/result.json"));

      assertEquals(
          tropo.text(),
          "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true,\"say\":[{\"value\":\"Please say your account number\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}},{\"on\":{\"event\":\"continue\",\"next\":\"/result.json\"}}]}");
    }
    
    @Test
    public void testFailsAskWithNoNameParameter() {
    
        Tropo tropo = new Tropo();
        try {
            tropo.ask(Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true));
            fail("Expected exception in test");
        }
        catch (TropoException te) {
            assertEquals(te.getMessage(), "Missing required property: 'name'");
        }
    }

    @Test
    public void testFailsAskWithNoChoicesParameter() {
    
        Tropo tropo = new Tropo();
        try {
            tropo.ask(Key.NAME("foo"), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true));
            fail("Expected exception in test");
        }
        catch (TropoException te) {
            assertEquals(te.getMessage(), "Missing required property: 'choices'");
        }
    }

    @Test
    public void testFailsAskWithNoSayParameter() {
    
        Tropo tropo = new Tropo();
        try {
            tropo.ask(Key.NAME("foo"), Key.CHOICES("Please say a digit"), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true));
            fail("Expected exception in test");
        }
        catch (TropoException te) {
            assertEquals(te.getMessage(), "Missing required property: 'say'");
        }
    }

    @Test
    public void testFailsAskWithNoChoicesValue() {
    
        Tropo tropo = new Tropo();
        try {
            tropo.ask(Key.NAME("foo"), Key.CHOICES(new Choices(null)), Key.SAY_OF_ASK(new Say("[1 DIGIT]")), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true));
            fail("Expected exception in test");
        }
        catch (TropoException te) {
            assertEquals(te.getMessage(), "Missing required property: value of Choices");
        }
    }

    @Test
    public void testFailsAskWithNoChoicesValue1() {
    
        Tropo tropo = new Tropo();
        try {
            tropo.ask(Key.NAME("foo"), Key.CHOICES(new Choices("")), Key.SAY_OF_ASK(new Say("[1 DIGIT]")), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true));
            fail("Expected exception in test");
        }
        catch (TropoException te) {
            assertEquals(te.getMessage(), "Missing required property: value of Choices");
        }
    }

    @Test
    public void testFailsAskWithNoSayValue() {
    
        Tropo tropo = new Tropo();
        try {
            tropo.ask(Key.NAME("foo"), Key.CHOICES("Please say a digit"), Key.SAY_OF_ASK(new Say(null)), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true));
            fail("Expected exception in test");
        }
        catch (TropoException te) {
            assertEquals(te.getMessage(), "Missing required property: value of Say");
        }
    }

    @Test
    public void testFailsAskWithNoSayValue1() {
    
        Tropo tropo = new Tropo();
        try {
            tropo.ask(Key.NAME("foo"), Key.CHOICES("Please say a digit"), Key.SAY_OF_ASK(new Say("")), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true));
            fail("Expected exception in test");
        }
        catch (TropoException te) {
            assertEquals(te.getMessage(), "Missing required property: value of Say");
        }
    }
    
    @Test
    public void testAskFailsWithInvalidElement() {
    
        Tropo tropo = new Tropo();
        try {
            tropo.ask(Key.TO("foo"), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true));
            fail("Expected exception in test");
        }
        catch (TropoException te) {
            assertEquals(te.getMessage(), "Invalid key 'to' for action");
        }
    }
    
    @Test
    public void testAllowSignals() {
    
        Tropo tropo = new Tropo();
        tropo.ask(Key.CHOICES("[1 DIGIT]"), Key.SAY_OF_ASK(new Say("Please say a digit")), Key.NAME("foo"), Key.BARGEIN(true), Key.TIMEOUT(30.0f), Key.REQUIRED(true), Key.ALLOW_SIGNALS("exit", "stopHold"));
        
        assertEquals(tropo.text(),
                "{\"tropo\":[{\"ask\":{\"choices\":{\"value\":\"[1 DIGIT]\"},\"say\":[{\"value\":\"Please say a digit\"}],\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true,\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
    }

    @Test
    public void testNewAllParameters() {
  
      Tropo tropo = new Tropo();
      tropo.ask(Key.CHOICES(new Choices("[4 DIGITS]", Mode.DTMF, "*")), Key.ALLOW_SIGNALS("exit", "quit"),
          Key.ATTEMPTS(3), Key.BARGEIN(true), Key.INTERDIGIT_TIMEOUT(3.5f), Key.MIN_CONFIDENCE(30), Key.NAME("foo"),
          Key.RECOGNIZER(Recognizer.ENGLISH_US), Key.REQUIRED(true),
          Key.SAY_OF_ASK(new Say("Sorry, I did not hear anything.", "timeout"),
              new Say("Don't think that was a year.", "nomatch:1"), new Say("Nope, still not a year.", "nomatch:2"),
              new Say("What is your birth year?")),
          Key.SENSITIVITY(0.5f), Key.SPEECH_COMPLETE_TIMEOUT(0.5f), Key.SPEECH_INCOMPLETE_TIMEOUT(0.5f),
          Key.TIMEOUT(30.1f), Key.VOICE(Voice.ALLISON), Key.PROMPT_LOG_SECURITY(),
          Key.ASR_LOG_SECURITY(AsrLogSecurity.MASK), Key.MASK_TEMPLATE("XXD-"));
  
      assertEquals(tropo.text(),
          "{\"tropo\":[{\"ask\":{\"choices\":{\"value\":\"[4 DIGITS]\",\"mode\":\"DTMF\",\"terminator\":\"*\"},\"allowSignals\":[\"exit\",\"quit\"],\"attempts\":3,\"bargein\":true,\"interdigitTimeout\":3.5,\"minConfidence\":30,\"name\":\"foo\",\"recognizer\":\"en-us\",\"required\":true,\"say\":[{\"value\":\"Sorry, I did not hear anything.\",\"event\":\"timeout\"},{\"value\":\"Don't think that was a year.\",\"event\":\"nomatch:1\"},{\"value\":\"Nope, still not a year.\",\"event\":\"nomatch:2\"},{\"value\":\"What is your birth year?\"}],\"sensitivity\":0.5,\"speechCompleteTimeout\":0.5,\"speechIncompleteTimeout\":0.5,\"timeout\":30.1,\"voice\":\"allison\",\"promptLogSecurity\":\"suppress\",\"asrLogSecurity\":\"mask\",\"maskTemplate\":\"XXD-\"}}]}");
    }
}
