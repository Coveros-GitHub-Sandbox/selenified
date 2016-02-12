package secureci.exceptions;

import java.io.IOException;

public class InvalidLocatorTypeException extends IOException {

	private static final long serialVersionUID = 4953865602802786545L;

	public InvalidLocatorTypeException () {
		super();
	}
	public InvalidLocatorTypeException (String msg) {
		super(msg);
	}
}
