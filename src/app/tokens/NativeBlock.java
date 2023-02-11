package app.tokens;

import app.compiler.Constants;
import app.errors.ParsingError;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.Sequence;
import parser.app.rules.terminals.Literal;
import parser.app.rules.terminals.Section;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;
import parser.app.tokens.monads.ErrorToken;

import static app.compiler.Constants.CLOSE_BLOCK;
import static app.compiler.Constants.OPEN_BLOCK;

public final class NativeBlock extends TokenArray {

	public static final Rule NATIVE_BLOCK = new Sequence(
			NativeBlock::new,
			new Literal("native"),
			new Section(OPEN_BLOCK, CLOSE_BLOCK)
	);

	/**
	 * Creates a new {@link NativeBlock} from the given tokens.
	 *
	 * @param rule   {@link #NATIVE_BLOCK}
	 * @param tokens Must be of length 2, where the first token is a {@link Literal}
	 *               with the value "native" and the second token is a {@link Section}
	 *               surrounded by {@link Constants#OPEN_BLOCK} and {@link Constants#CLOSE_BLOCK}.
	 */
	public NativeBlock(Rule rule, Token[] tokens) {
		super(rule, tokens);
		assert tokens.length == 2;
		if (tokens[0] instanceof ErrorToken e)
			throw new ParsingError("Expected native keyword.", e);
		if (tokens[1] instanceof ErrorToken e)
			throw new ParsingError("Expected section after native keyword.", e);
	}

	public String getContent() {
		return get(1).section();
	}
}
