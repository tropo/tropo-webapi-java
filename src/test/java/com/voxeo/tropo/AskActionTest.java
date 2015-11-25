package com.voxeo.tropo;

import static com.voxeo.tropo.Key.*;
import static com.voxeo.tropo.enums.Mode.DTMF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.voxeo.tropo.actions.Do;
import com.voxeo.tropo.enums.Recognizer;
import com.voxeo.tropo.enums.Voice;

public class AskActionTest {
    
    @Before
    public void setUp() throws Exception {
    
    }
    
    @Test
    public void testAsk() {
    
        Tropo tropo = new Tropo();
        tropo.ask(NAME("foo"), BARGEIN(true), TIMEOUT(30.0f), REQUIRED(true));
        
        assertEquals(tropo.text(), "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true}}]}");
    }
    
    @Test
    public void testAskWithSensitivity() {
    
        Tropo tropo = new Tropo();
        tropo.ask(NAME("foo"), BARGEIN(true), TIMEOUT(30.0f), SENSITIVITY(30.1f), REQUIRED(true));
        
        assertEquals(tropo.text(),
                "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"sensitivity\":30.1,\"required\":true}}]}");
    }
    
    @Test
    public void testAskWithSayBlock() {
    
        Tropo tropo = new Tropo();
        tropo.ask(NAME("foo"), BARGEIN(true), TIMEOUT(30.0f), REQUIRED(true), CHOICES("[5 DIGITS]")).say("Please say your account number");
        
        assertEquals(
                tropo.text(),
                "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true,\"choices\":\"[5 DIGITS]\",\"say\":[{\"value\":\"Please say your account number\"}]}}]}");
    }
    
    @Test
    public void testAskWithSayAndOnBlocks() {
    
        Tropo tropo = new Tropo();
        tropo.ask(NAME("foo"), BARGEIN(true), TIMEOUT(30.0f), REQUIRED(true)).and(Do.say("Please say your account number"),
                Do.on(EVENT("success"), NEXT("/result.json")), Do.choices(VALUE("[5 DIGITS]")));
        
        assertEquals(
                tropo.text(),
                "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true,\"say\":[{\"value\":\"Please say your account number\"}],\"on\":[{\"event\":\"success\",\"next\":\"/result.json\"}],\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
    }
    
    @Test
    public void testFailsAskWithNoNameParameter() {
    
        Tropo tropo = new Tropo();
        try {
            tropo.ask(BARGEIN(true), TIMEOUT(30.0f), REQUIRED(true));
            fail("Expected exception in test");
        }
        catch (TropoException te) {
            assertEquals(te.getMessage(), "Missing required property: 'name'");
        }
    }
    
    @Test
    public void testAskFailsWithInvalidElement() {
    
        Tropo tropo = new Tropo();
        try {
            tropo.ask(TO("foo"), BARGEIN(true), TIMEOUT(30.0f), REQUIRED(true));
            fail("Expected exception in test");
        }
        catch (TropoException te) {
            assertEquals(te.getMessage(), "Invalid key 'to' for action");
        }
    }
    
    @Test
    public void testAllowSignals() {
    
        Tropo tropo = new Tropo();
        tropo.ask(NAME("foo"), BARGEIN(true), TIMEOUT(30.0f), REQUIRED(true), ALLOW_SIGNALS("exit", "stopHold"));
        
        assertEquals(tropo.text(),
                "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true,\"allowSignals\":[\"exit\",\"stopHold\"]}}]}");
    }
    
    @Test
    public void testAskAllParameters() {
    
        Tropo tropo = new Tropo();
        tropo.ask(NAME("foo"), BARGEIN(true), TIMEOUT(30.0f), INTERDIGIT_TIMEOUT(5), REQUIRED(true), ALLOW_SIGNALS("exit", "stopHold"), ATTEMPTS(5),
                MIN_CONFIDENCE(3), RECOGNIZER(Recognizer.SPANISH), VOICE(Voice.ALLISON));
        
        assertEquals(
                tropo.text(),
                "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"interdigitTimeout\":5,\"required\":true,\"allowSignals\":[\"exit\",\"stopHold\"],\"attempts\":5,\"minConfidence\":3,\"recognizer\":\"es-es\",\"voice\":\"allison\"}}]}");
    }
    
    @Test
    public void testAskModeIsSupportedParameters() {
    
        Tropo tropo = new Tropo();
        tropo.ask(NAME("foo"), MODE(DTMF), TIMEOUT(30.0f), REQUIRED(true), ALLOW_SIGNALS("exit", "stopHold"), ATTEMPTS(5), MIN_CONFIDENCE(3),
                RECOGNIZER(Recognizer.SPANISH), VOICE(Voice.ALLISON));
        
        assertEquals(
                tropo.text(),
                "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"mode\":\"dtmf\",\"timeout\":30.0,\"required\":true,\"allowSignals\":[\"exit\",\"stopHold\"],\"attempts\":5,\"minConfidence\":3,\"recognizer\":\"es-es\",\"voice\":\"allison\"}}]}");
    }
    
    @Test
    public void testAskTraditionalWay() {
    
        Tropo tropo = new Tropo();
        tropo.ask("foo", true, 30.0f, true);
        
        assertEquals(tropo.text(), "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true}}]}");
    }
    
    @Test
    public void testAskAndChoicesTraditionalWay() {
    
        Tropo tropo = new Tropo();
        tropo.ask("foo", true, 30.0f, true).choices(VALUE("[5 DIGITS]"));
        
        assertEquals(tropo.text(),
                "{\"tropo\":[{\"ask\":{\"name\":\"foo\",\"bargein\":true,\"timeout\":30.0,\"required\":true,\"choices\":{\"value\":\"[5 DIGITS]\"}}}]}");
    }
    
    @Test
    public void testChoicesMethods() {
    
        String output = "{\"tropo\":[{\"ask\":{\"attempts\":3,\"name\":\"question\",\"bargein\":true,\"timeout\":20.0,\"required\":true,\"choices\":{\"value\":\"red,green,blue\"}}}]}";
        // From evolution bug
        Tropo tropo = new Tropo();
        tropo.ask(ATTEMPTS(3), NAME("question"), BARGEIN(true), TIMEOUT(20f), REQUIRED(true)).choices("red,green,blue");
        assertEquals(tropo.text(), output);
        
        tropo.reset();
        tropo.ask(ATTEMPTS(3), NAME("question"), BARGEIN(true), TIMEOUT(20f), REQUIRED(true)).choices(VALUE("red,green,blue"));
        assertEquals(tropo.text(), output);
        tropo.reset();
    }
}
