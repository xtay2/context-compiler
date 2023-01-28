package app.errors.startup;

import app.errors.CompilerError;

public class IllegalArgumentError extends CompilerError {

	public IllegalArgumentError(int id, String message) {
		super(id,  message);
		assert id >= 10;
		assert id <= 19;
	}

	public IllegalArgumentError(int id, String message, String was) {
		super(id, message + "\n\tWas: " + was);
		assert id >= 10;
		assert id <= 19;
	}

}
