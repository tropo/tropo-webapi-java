package com.voxeo.tropo;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

public class IOUtils {

	public static String read(Reader input) {
		
		try {
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			OutputStreamWriter output = new OutputStreamWriter(byteArray);
			
	        char[] buffer = new char[1024*4];
	        int n = 0;
	        while (-1 != (n = input.read(buffer))) {
	        	output.write(buffer, 0, n);
	        }
	        output.flush();
	        return new String(byteArray.toByteArray());
		} catch (Exception e) {
			throw new TropoException("Error reading from Reader", e);
		}
	}
}
