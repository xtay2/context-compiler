package app.parts.rules.blueprints.content;

import app.errors.ParsingError;
import app.io.ImportPath;
import app.parts.tokens.blueprints.content.Datatype;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Literal;
import parser.app.rules.terminals.Pattern;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;

import static helper.Constants.BLUEPRINT_NAME;

public final class DatatypeParser extends TokenArray {

	// ------------------ Rules ---------------------------------------------------------------------

	private static final Rule DATATYPE_NAME = new Pattern(BLUEPRINT_NAME);

	public static Rule datatype(ImportPath myFilePath, ImportPath[] targetImports) {
		return new Ordered(
				tokens -> new DatatypeParser(myFilePath, targetImports, tokens),
				DATATYPE_NAME,
				new Literal('?')
		);
	}

	// ------------------ Constructor ---------------------------------------------------------------

	public Datatype datatype;

	private DatatypeParser(ImportPath myFilePath, ImportPath[] targetImports, Token[] tokens) {
		super(tokens);
		assert tokens.length == 1;
		// Parts
		var name = extractName(myFilePath, tokens);
		// Initialize
		this.datatype = new Datatype(myFilePath, targetImports, name);
	}

	// ------------------ Extractors / Checks --------------------------------------------------------

	private static String extractName(ImportPath myFilePath, Token[] tokens) {
		if (tokens[0].hasError()) {
			throw new ParsingError(
					myFilePath,
					"Expected a valid name for a datatype",
					"Names for datatypes have to match the regex: " + BLUEPRINT_NAME.pattern(),
					tokens[0]
			);
		}
		return tokens[0].toString();
	}

}
