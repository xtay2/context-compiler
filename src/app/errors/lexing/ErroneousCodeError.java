package app.errors.lexing;

import app.errors.CompilerError;
import app.tokenization.tokens.Token;

/**
 * This error is thrown when the compiler encounters an unexpected character.
 */
public class ErroneousCodeError extends CompilerError {

	public ErroneousCodeError(int id, Token token, String message) {
		super(id, token, message);
	}
}
