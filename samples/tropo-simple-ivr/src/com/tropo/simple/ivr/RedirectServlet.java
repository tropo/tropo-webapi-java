package com.tropo.simple.ivr;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voxeo.tropo.ActionResult;
import com.voxeo.tropo.Tropo;
import com.voxeo.tropo.TropoResult;

@WebServlet(name="Redirect Servlet", value="/redirect")
public class RedirectServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Tropo tropo = new Tropo();
		TropoResult result = tropo.parse(req);
		ActionResult actionResult = result.getActions().get(0);
		Integer choice = Integer.parseInt(actionResult.getValue());
		
		switch(choice) {
			case 1: tropo.say("Thank you. We are going to redirect you now to one of our customer support agents");
					break;
			case 2: tropo.say("Thank you. We are going to redirect you now to one of our sale agents");
					break;
			case 3: tropo.say("Thank you. We are going to redirect you now to one of our emergencies agents");
					break;
			default:tropo.say("Thank you. Please bear with us. One of our agents will respond your call very shortly.");
					break;
		}
		tropo.say("http://ccmixter.org/content/DoKashiteru/DoKashiteru_-_you_(na-na-na-na).mp3");
		tropo.hangup();
		
		tropo.render(resp);
	}
}

