package secureci.exceptions;

import java.io.IOException;

public class InvalidEnvironmentException extends IOException {

	private static final long serialVersionUID = 3726739755420042312L;

	public InvalidEnvironmentException () {
		super();
	}
	public InvalidEnvironmentException (String msg) {
		super(msg);
	}
}
