package app.errors.lexing;

import app.tokenization.tokens.ErroneousTerminal;
import app.tokenization.tokens.Token;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WrongOrMissingCodeError extends ErroneousCodeError {

	public WrongOrMissingCodeError(Token token, String parent, String child) {
		super(1010, token, "Missing " + child + " in " + parent + ".");
	}

	public WrongOrMissingCodeError(Token token, String parent, Token... tokens) {
		super(1010, token, "Unreadable or missing code in " + parent + ". Tokens: " + findErrorTokens(tokens));
	}

	private static String findErrorTokens(Token[] tokens) {
		if (Arrays.stream(tokens).anyMatch(t -> t instanceof ErroneousTerminal)) {
			return Arrays.stream(tokens)
					.filter(t -> t instanceof ErroneousTerminal et && !et.isEmpty())
					.filter(t -> !t.toString().isBlank())
					.map(t -> "\"" + t + "\"")
					.collect(Collectors.joining(", "));
		}
		return Arrays.stream(tokens)
				.filter(t -> !t.toString().isBlank())
				.map(t -> "\"" + t + "\"")
				.collect(Collectors.joining(", "));
	}

}
