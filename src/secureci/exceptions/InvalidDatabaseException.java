package secureci.exceptions;

import java.io.IOException;

public class InvalidDatabaseException extends IOException {

	private static final long serialVersionUID = -5422368067006446079L;

	public InvalidDatabaseException () {
		super();
	}
	public InvalidDatabaseException (String msg) {
		super(msg);
	}
}
