package app.errors.startup;

import app.errors.CompilerError;

import java.io.IOException;

public class FileReadingError extends CompilerError {

	public FileReadingError(int id, String message, Throwable reason) {
		super(id, message + "\n\tReason: " + reason.getMessage());
		assert id >= 20;
		assert id <= 25;
	}

}
