package secureci.exceptions;

import java.io.IOException;

public class InvalidBrowserException extends IOException {

	private static final long serialVersionUID = 1560310848170077852L;

	public InvalidBrowserException () {
		super();
	}
	public InvalidBrowserException (String msg) {
		super(msg);
	}
}
