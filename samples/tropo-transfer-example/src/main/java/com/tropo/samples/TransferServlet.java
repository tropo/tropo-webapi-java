package com.tropo.samples;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voxeo.tropo.Tropo;

@SuppressWarnings("serial")
public class TransferServlet extends HttpServlet {

	@Override
	public void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		Tropo tropo = new Tropo();
		tropo.say("Thank you for calling. Your call will be transfered.");
		tropo.transfer("+14074873689");
		tropo.render(response);
	}
}
