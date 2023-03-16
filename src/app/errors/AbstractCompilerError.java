package app.errors;

import app.io.ImportPath;

public class AbstractCompilerError extends Error {

	AbstractCompilerError(String message) {
		super(message);
	}

	AbstractCompilerError(ImportPath myFilePath, String message) {
		super("[" + myFilePath + "]: " + message);
	}

}
