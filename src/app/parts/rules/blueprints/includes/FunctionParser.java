package app.parts.rules.blueprints.includes;

import app.errors.ParsingError;
import app.io.ImportPath;
import app.parts.rules.blueprints.content.DatatypeParser;
import app.parts.tokens.blueprints.content.Datatype;
import app.parts.tokens.blueprints.content.Function;
import app.parts.tokens.blueprints.content.Parameter;
import helper.Constants;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.extensions.Multiple;
import parser.app.rules.nonterminals.extensions.Optional;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Literal;
import parser.app.rules.terminals.Pattern;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;
import parser.app.tokens.collection.TokenList;
import parser.app.tokens.monads.OptionalToken;

import static app.parts.rules.blueprints.content.DatatypeParser.datatype;
import static app.parts.rules.blueprints.includes.ParameterParser.params;
import static helper.Constants.*;

public final class FunctionParser extends TokenArray {

	// ------------------ Rules ---------------------------------------------------------------------

	private static final Rule FUNCTION_NAME = new Pattern(Constants.FUNCTION_NAME);

	private static Rule functionDecl(ImportPath myFilePath, ImportPath[] targetImports) {
		return new Ordered(
				FUNCTION_NAME,
				new Literal('('), params(myFilePath, targetImports), new Literal(')'),
				new Optional(
						new Literal(DECLARATOR),
						datatype(myFilePath, targetImports)
				),
				new Literal(OPEN_BLOCK),
				// content
				new Literal(CLOSE_BLOCK)
		);
	}

	// ------------------ Constructor ---------------------------------------------------------------

	public static Rule functions(ImportPath myFilePath, ImportPath[] targetImports) {
		return new Multiple(true, functionDecl(myFilePath, targetImports));
	}

	public final Function function;

	private FunctionParser(ImportPath myFilePath, Token[] tokens) {
		super(tokens);
		assert tokens.length == 6;
		// Parts
		var name = extractName(myFilePath, tokens);
		checkParamParentheses(myFilePath, tokens);
		var params = extractParams(myFilePath, tokens);
		var returnType = extractReturntype(myFilePath, tokens);
		// Initialize
		this.function = new Function(myFilePath, name, params, returnType);
	}

	// ------------------ Extractors / Checks --------------------------------------------------------

	private static String extractName(ImportPath myFilePath, Token[] tokens) {
		if (tokens[0].hasError())
			throw new ParsingError(myFilePath, "Expected a valid function name", tokens[0]);
		return tokens[0].section();
	}

	private void checkParamParentheses(ImportPath myFilePath, Token[] tokens) {
		if (tokens[1].hasError() || tokens[3].hasError())
			throw new ParsingError(myFilePath, "A function name has to be followed by parentheses", this);
	}

	private static Parameter[] extractParams(ImportPath myFilePath, Token[] tokens) {
		if (tokens[2].hasError())
			throw new ParsingError(myFilePath, "Expected a valid parameter list", tokens[3]);
		return ((TokenList) tokens[2])
				.stream()
				.map(t -> ((ParameterParser) t).param)
				.toArray(Parameter[]::new);
	}

	private static Datatype extractReturntype(ImportPath myFilePath, Token[] tokens) {
		if (tokens[4].hasError())
			throw new ParsingError(myFilePath, "Expected a valid return type", tokens[3]);
		return ((OptionalToken) tokens[4])
				.map(t -> ((DatatypeParser) t).datatype)
				.orElse(Datatype.returnVoid(myFilePath));

	}
}
