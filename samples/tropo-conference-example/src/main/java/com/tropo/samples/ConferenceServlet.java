package com.tropo.samples;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.Tropo;
import com.voxeo.tropo.TropoSession;

public class ConferenceServlet extends HttpServlet {

  private static final long serialVersionUID = 7658399518995371027L;

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    Tropo tropo = new Tropo();
    
    TropoSession tropoSession = tropo.session(req);
    System.out.println(tropoSession.getId());

    tropo.say("Thank you for calling. You will now join the conference.");

    tropo.conference(Key.ID("1234"), Key.ALLOW_SIGNALS("exit", "quit", "bye"), Key.INTERDIGIT_TIMEOUT(11.2F),
        //Key.JOIN_PROMPT("Welcome to the conference"), Key.LEAVE_PROMPT("There is someone that leave the conference"),
        Key.JOIN_PROMPT(true), Key.LEAVE_PROMPT(true),
        Key.MUTE(false), Key.NAME("foo"), Key.PLAY_TONES(true), Key.REQUIRED(true), Key.TERMINATOR("#"));

    tropo.render(resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
