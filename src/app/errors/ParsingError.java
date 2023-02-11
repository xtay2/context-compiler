package app.errors;

import parser.app.tokens.monads.ErrorToken;

public class ParsingError extends AbstractCompilerError {

	public ParsingError(String message, ErrorToken token) {
		super(message + " Was: " + token.section());
	}

}
