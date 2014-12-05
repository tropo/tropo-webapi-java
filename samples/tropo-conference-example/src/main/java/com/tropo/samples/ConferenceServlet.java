package com.tropo.samples;

import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.EXIT_TONE;
import static com.voxeo.tropo.Key.ID;
import static com.voxeo.tropo.Key.MUTE;
import static com.voxeo.tropo.Key.NAME;
import static com.voxeo.tropo.Key.SEND_TONES;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voxeo.tropo.Tropo;
import com.voxeo.tropo.actions.Do;

@SuppressWarnings("serial")
public class ConferenceServlet extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		Tropo tropo = new Tropo();
		
		tropo.say("Thank you for calling. You will now join the conference.");
		
		tropo.conference(NAME("foo"),ID("1234"),MUTE(false),SEND_TONES(false),EXIT_TONE("#")).and(
			Do.on(EVENT("join")).and(
			Do.say("Welcome to the conference")));
		
		tropo.render(response);
	}
}
