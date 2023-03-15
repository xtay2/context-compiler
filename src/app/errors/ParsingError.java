package app.errors;

import app.io.ImportPath;
import parser.app.tokens.Token;

public class ParsingError extends AbstractCompilerError {

	public ParsingError(ImportPath importPath, String message, Token token) {
		super("[" + importPath + "]: " + message
				      + "\nWas: " + (token.section().isEmpty()
				                     ? "<empty>"
				                     : token.section()));
		assert token.hasError();
	}

	public ParsingError(ImportPath importPath, String message, String tip, Token token) {
		super("[" + importPath + "]: " + message
				      + "\nWas: " + (token.section().isEmpty()
				                     ? "<empty>"
				                     : token.section())
				      + "\nTip: " + tip);
		assert token.hasError();
	}

}
