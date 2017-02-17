package secureci.exceptions;

import java.io.IOException;

public class InvalidTestTypeException extends IOException {

	private static final long serialVersionUID = -4707815990022887449L;

	public InvalidTestTypeException () {
		super();
	}
	public InvalidTestTypeException (String msg) {
		super(msg);
	}
}
