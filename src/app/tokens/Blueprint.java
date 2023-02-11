package app.tokens;

import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.Multiple;
import parser.app.rules.nonterminals.Sequence;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;

import static app.tokens.NativeBlock.NATIVE_BLOCK;

public final class Blueprint extends TokenArray {

	static final Rule NATIVES = new Multiple(NATIVE_BLOCK);

	public static final Rule BLUEPRINT = new Sequence(
			Blueprint::new,
			NATIVES
	);

	/**
	 * Creates a new {@link Blueprint} from the given tokens.
	 *
	 * @param rule   {@link #BLUEPRINT}
	 * @param tokens Must be of length 1, where the only token is a {@link TokenArray}
	 *               with the rule {@link #NATIVES}.
	 */
	public Blueprint(Rule rule, Token[] tokens) {
		super(rule, tokens);
		assert tokens.length == 1;
	}
}
