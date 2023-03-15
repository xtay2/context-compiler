package app.parts.rules.blueprints.includes;

import app.errors.ParsingError;
import app.io.ImportPath;
import app.parts.rules.blueprints.content.DatatypeParser;
import app.parts.tokens.blueprints.content.Attribute;
import helper.Constants;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.extensions.Multiple;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Pattern;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;

import static app.parts.rules.blueprints.content.DatatypeParser.DATATYPE;

public final class AttributeParser extends TokenArray {

	private static final Rule ATTRIBUTE_NAME = new Pattern(Constants.ATTRIBUTE_NAME);

	private static final Rule ATTRIBUTE_DECLARATION = new Ordered(
			DATATYPE,
			ATTRIBUTE_NAME
	);

	public static final Rule ATTRIBUTES = new Multiple(true, ATTRIBUTE_DECLARATION);

	public final Attribute attribute;

	private AttributeParser(ImportPath importPath, Token... tokens) {
		super(tokens);
		assert tokens.length == 2;
		if (tokens[0].hasError())
			throw new ParsingError(importPath, "Expected a valid datatype", tokens[0]);
		DatatypeParser datatypeParser = null;
		if (tokens[1].hasError())
			throw new ParsingError(importPath, "Expected a valid attribute name", tokens[1]);
		this.attribute = new Attribute(
				datatypeParser.datatype,
				tokens[1].section()
		);
	}
}
