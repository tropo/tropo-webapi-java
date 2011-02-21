package com.voxeo.tropo;

@SuppressWarnings("serial")
public class TropoException extends RuntimeException {

	public TropoException() {

	}

	public TropoException(String message) {
		super(message);

	}

	public TropoException(Throwable cause) {
		super(cause);

	}

	public TropoException(String message, Throwable cause) {
		super(message, cause);

	}

}
