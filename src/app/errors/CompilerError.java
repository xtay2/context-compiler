package app.errors;

import app.tokenization.tokens.Token;

public class CompilerError extends RuntimeException {

	public CompilerError(int id, String message) {
		super("CompilerError: " + message);
		assert id <= 1000;
	}


	public CompilerError(int id, Token token, String message) {
		super("[" + id + "] " + token.toString() + "\n" + message);
	}

}
