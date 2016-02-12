package secureci.exceptions;

import java.io.IOException;

public class InvalidActionException extends IOException {

	private static final long serialVersionUID = -5288393641127953902L;

	public InvalidActionException () {
		super();
	}
	public InvalidActionException (String msg) {
		super(msg);
	}
}
