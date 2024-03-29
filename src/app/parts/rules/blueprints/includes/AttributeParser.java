package app.parts.rules.blueprints.includes;

import app.errors.ParsingError;
import app.io.ImportPath;
import app.parts.rules.blueprints.content.DatatypeParser;
import app.parts.tokens.blueprints.content.Attribute;
import app.parts.tokens.blueprints.content.Datatype;
import helper.Constants;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.extensions.Multiple;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Pattern;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;

import static app.parts.rules.blueprints.content.DatatypeParser.datatype;

public final class AttributeParser extends TokenArray {

	// ------------------ Rules ---------------------------------------------------------------------

	private static final Rule ATTRIBUTE_NAME = new Pattern(Constants.VAR_NAME);

	private static Rule attributeDecl(ImportPath myFilePath, ImportPath[] targetImports) {
		return new Ordered(datatype(myFilePath, targetImports), ATTRIBUTE_NAME);
	}

	public static Rule attributes(ImportPath myFilePath, ImportPath[] targetImports) {
		return new Multiple(true, attributeDecl(myFilePath, targetImports));
	}

	// ------------------ Constructor ---------------------------------------------------------------

	public final Attribute attribute;

	private AttributeParser(ImportPath myFilePath, Token[] tokens) {
		super(tokens);
		assert tokens.length == 2;
		// Parts
		var datatype = extractDatatype(myFilePath, tokens);
		checkName(myFilePath, tokens);
		// Initialize
		this.attribute = new Attribute(
				myFilePath,
				datatype,
				tokens[1].section()
		);
	}

	// ------------------ Extractors / Checks --------------------------------------------------------

	private static Datatype extractDatatype(ImportPath myFilePath, Token[] tokens) {
		if (tokens[0].hasError())
			throw new ParsingError(myFilePath, "Expected a valid datatype", tokens[0]);
		return ((DatatypeParser) tokens[0]).datatype;
	}

	private static void checkName(ImportPath myFilePath, Token[] tokens) {
		if (tokens[1].hasError())
			throw new ParsingError(myFilePath, "Expected a valid attribute name", tokens[1]);
	}
}
