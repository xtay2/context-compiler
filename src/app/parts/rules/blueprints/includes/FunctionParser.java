package app.parts.rules.blueprints.includes;

import app.parts.tokens.blueprints.content.Function;
import helper.Constants;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.extensions.Multiple;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Literal;
import parser.app.rules.terminals.Pattern;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;

import static app.parts.rules.blueprints.content.DatatypeParser.DATATYPE;
import static helper.Constants.CLOSE_BLOCK;
import static helper.Constants.OPEN_BLOCK;

public final class FunctionParser extends TokenArray {

	private static final Rule FUNCTION_NAME = new Pattern(Constants.FUNCTION_NAME);

	private static final Rule FUNCTION_DECLARATION = new Ordered(
			FUNCTION_NAME,
			new Literal('('), /* PARAMS */ new Literal(')'),
			DATATYPE,
			new Literal(OPEN_BLOCK),
			// content
			new Literal(CLOSE_BLOCK)
	);

	public static final Rule FUNCTIONS = new Multiple(true, FUNCTION_DECLARATION);
	public final Function function;

	private FunctionParser(Token... tokens) {
		super(tokens);
		this.function = null; // TODO: Implement me!
	}
}
