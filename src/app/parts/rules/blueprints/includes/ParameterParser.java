package app.parts.rules.blueprints.includes;

import app.errors.ParsingError;
import app.io.ImportPath;
import app.parts.rules.blueprints.content.DatatypeParser;
import app.parts.tokens.blueprints.content.Datatype;
import app.parts.tokens.blueprints.content.Parameter;
import helper.Constants;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.extensions.Multiple;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Pattern;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;

import static app.parts.rules.blueprints.content.DatatypeParser.datatype;

final class ParameterParser extends TokenArray {

	// ------------------ Rules ---------------------------------------------------------------------

	private static final Rule PARAM_NAME = new Pattern(Constants.VAR_NAME);

	private static Rule param(ImportPath myFilePath, ImportPath[] targetImports) {
		return new Ordered(
				tokens -> new ParameterParser(myFilePath, tokens),
				datatype(myFilePath, targetImports),
				PARAM_NAME
		);
	}

	// ------------------ Constructor ---------------------------------------------------------------

	static Rule params(ImportPath myFilePath, ImportPath[] targetImports) {
		return new Multiple(true, param(myFilePath, targetImports));
	}

	final Parameter param;

	private ParameterParser(ImportPath myFilePath, Token[] tokens) {
		super(tokens);
		assert tokens.length == 2;
		// Parts
		var datatype = extractDatatype(myFilePath, tokens);
		var name = extractName(myFilePath, tokens);
		// Initialize
		this.param = new Parameter(datatype, name);
	}

	// ------------------ Extractors / Checks --------------------------------------------------------

	private static Datatype extractDatatype(ImportPath myFilePath, Token[] tokens) {
		if (tokens[0].hasError())
			throw new ParsingError(myFilePath, "Expected a valid datatype", tokens[0]);
		return ((DatatypeParser) tokens[0]).datatype;
	}

	private static String extractName(ImportPath myFilePath, Token[] tokens) {
		if (tokens[1].hasError())
			throw new ParsingError(myFilePath, "Expected a valid parameter name", tokens[1]);
		return tokens[1].section();
	}
}
