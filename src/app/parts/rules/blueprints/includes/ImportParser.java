package app.parts.rules.blueprints.includes;

import app.errors.ParsingError;
import app.io.ImportPath;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.extensions.Alteration;
import parser.app.rules.nonterminals.extensions.Multiple;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Literal;
import parser.app.rules.terminals.Pattern;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;
import parser.app.tokens.collection.TokenList;

import static helper.Constants.*;

public final class ImportParser extends TokenArray {

	// ------------------ Rules ---------------------------------------------------------------------

	private static Rule imports(ImportPath myFilePath) {
		return new Multiple(
				true,
				new Ordered(
						(tokens) -> new ImportParser(myFilePath, tokens),
						new Literal(IMPORT_KEYWORD),
						new Alteration(false, new Pattern(SRC_PATH), new Pattern(STD_PATH))
				)
		);
	}

	// ------------------ Constructor ---------------------------------------------------------------

	private final ImportPath target;

	private ImportParser(ImportPath myFilePath, Token[] tokens) {
		super(tokens);
		assert tokens.length == 2;
		// Parts
		checkKeyword(myFilePath, tokens);
		var path = extractPath(myFilePath, tokens);
		// Initialize
		this.target = new ImportPath(path);
	}

	// ------------------ Extractors / Checks --------------------------------------------------------

	private static void checkKeyword(ImportPath myFilePath, Token[] tokens) {
		if (tokens[0].hasError())
			throw new ParsingError(myFilePath, "Expected the '" + IMPORT_KEYWORD + "' keyword", tokens[0]);
	}

	private static String extractPath(ImportPath myFilePath, Token[] tokens) {
		if (tokens[1].hasError()) {
			throw new ParsingError(
					myFilePath,
					"Expected a valid import path",
					"Import paths have to follow one of the following patterns: "
							+ "\n- " + SRC_PATH.pattern()
							+ "\n- " + STD_PATH.pattern(),
					tokens[1]
			);
		}
		return tokens[1].section();
	}

	// ------------------ Static Methods ------------------------------------------------------------

	public static ImportPath[] parse(ImportPath myFilePath, String input) {
		return ((TokenList) imports(myFilePath).tokenize(input))
				.stream()
				.map((token) -> ((ImportParser) token).target)
				.toArray(ImportPath[]::new);
	}
}
