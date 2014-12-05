package com.tropo.samples;

import static com.voxeo.tropo.Key.BEEP;
import static com.voxeo.tropo.Key.EVENT;
import static com.voxeo.tropo.Key.EXIT_TONE;
import static com.voxeo.tropo.Key.ID;
import static com.voxeo.tropo.Key.MUTE;
import static com.voxeo.tropo.Key.NAME;
import static com.voxeo.tropo.Key.SEND_TONES;
import static com.voxeo.tropo.Key.URL;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voxeo.tropo.Tropo;
import com.voxeo.tropo.actions.Do;

@SuppressWarnings("serial")
public class RecordServlet extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		Tropo tropo = new Tropo();
		
		tropo.say("Thank you for calling. This call will be recorded.");
		
		tropo.record(NAME("foo"),URL("mailto:mperez@tropo.com"),BEEP(true),SEND_TONES(false),EXIT_TONE("#"));

		tropo.render(response);
	}
}
