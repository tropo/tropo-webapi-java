package com.voxeo.tropo.mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class MockHttpServletResponse implements HttpServletResponse {

	private PrintWriter writer;
	private ByteArrayOutputStream output;
	
	public MockHttpServletResponse() {
		
		output = new ByteArrayOutputStream();
		this.writer = new PrintWriter(output);
	}
	
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public PrintWriter getWriter() throws IOException {
		
		return writer;
	}

	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public void resetBuffer() {
		// TODO Auto-generated method stub
		
	}

	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setContentType(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addCookie(Cookie arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addDateHeader(String arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	public void addHeader(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public void addIntHeader(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public boolean containsHeader(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public String encodeRedirectURL(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeRedirectUrl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeURL(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeUrl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendError(int arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void sendError(int arg0, String arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void sendRedirect(String arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void setDateHeader(String arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	public void setHeader(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public void setIntHeader(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void setStatus(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setStatus(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public String getContent() {
		
		return new String(output.toByteArray());
	}
}
