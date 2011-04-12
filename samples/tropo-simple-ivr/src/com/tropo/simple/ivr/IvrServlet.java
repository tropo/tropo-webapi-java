package com.tropo.simple.ivr;

import static com.voxeo.tropo.Key.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voxeo.tropo.Tropo;
import com.voxeo.tropo.actions.Do;
import static com.voxeo.tropo.enums.Mode.*;

@WebServlet(name="IVR Servlet", value="/ivr")
public class IvrServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, 
						   HttpServletResponse response) throws ServletException, IOException {

		// Note that Tropo will send you a POST request so either overriding service or post is ok
		
		Tropo tropo = new Tropo();
		tropo.say("Welcome to our company. Please enter the number of the department you wish to be forwarded to:");
		tropo.ask(NAME("userChoice"), BARGEIN(true), MODE(DTMF), TIMEOUT(10f), ATTEMPTS(2)).and(
			Do.say(VALUE("Sorry, I didn't hear anything"),EVENT("timeout"))
			  .say("Press #1 for Customer Support. Press #2 for sales. Press #3 for emergencies. Press #4 for any other thing."),
			Do.choices(VALUE("[1 DIGIT]"))
		);
		tropo.on(EVENT("continue"), NEXT("redirect"));
		tropo.render(response);		
	}
}