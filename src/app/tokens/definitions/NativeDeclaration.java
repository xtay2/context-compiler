package app.tokens.definitions;

import app.rules.Keywords;
import app.rules.abstractions.Rule;
import app.rules.nonterminals.SequenceRule;
import app.rules.terminals.LiteralRule;
import app.tokenization.tokens.Token;

import static app.rules.Rules.DECLARATOR;

public class NativeDeclaration extends Token {

	public static final Rule NATIVE_KEYWORD = new LiteralRule(Keywords.NATIVE);

	public static final Rule NATIVE_DECLARATION = new SequenceRule(NATIVE_KEYWORD, DECLARATOR);

	public NativeDeclaration(Token... tokens) {
		super(tokens);
	}

}
