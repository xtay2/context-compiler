package app.errors;

import app.io.ImportPath;
import parser.app.tokens.Token;

public class ParsingError extends AbstractCompilerError {

	/**
	 * A simple ParsingError with a message.
	 *
	 * @param myFilePath The import path of the file that caused the error.
	 * @param message    Short description of what was expected.
	 * @param token      The token that should be displayed as the cause.
	 */
	public ParsingError(ImportPath myFilePath, String message, Token token) {
		super(myFilePath, message
				+ "\nWas: " + (token.section().isEmpty()
				               ? "<empty>"
				               : token.section()));
		assert token.hasError();
	}

	/**
	 * A ParsingError with a message and an improvement-tip.
	 *
	 * @param myFilePath The import path of the file that caused the error.
	 * @param message    Short description of what was expected.
	 * @param tip        A tip on how to avoid the error.
	 * @param token      The token that should be displayed as the cause.
	 */
	public ParsingError(ImportPath myFilePath, String message, String tip, Token token) {
		super(myFilePath, message
				+ "\nWas: " + (token.section().isEmpty()
				               ? "<empty>"
				               : token.section())
				+ "\nTip: " + tip);
		assert token.hasError();
	}

}
