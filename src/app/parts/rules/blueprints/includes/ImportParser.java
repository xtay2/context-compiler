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

import static helper.Constants.*;

public final class ImportParser extends TokenArray {

	public static Rule imports(ImportPath importPath) {
		return new Multiple(
				true,
				new Ordered(
						(tokens) -> new ImportParser(importPath, tokens),
						new Literal(IMPORT_KEYWORD),
						new Alteration(false, new Pattern(SRC_PATH), new Pattern(STD_PATH))
				)
		);
	}

	public final ImportPath importPath;

	private ImportParser(ImportPath importPath, Token... tokens) {
		super(tokens);
		assert tokens.length == 3;
		// Valid import-keyword
		if (tokens[0].hasError())
			throw new ParsingError(importPath, "Expected the '" + IMPORT_KEYWORD + "' keyword", tokens[0]);
		// Valid import-path
		if (tokens[1].hasError()) {
			throw new ParsingError(
					importPath,
					"Expected a valid import path",
					"Import paths have to follow one of the following patterns: "
							+ "\n- " + SRC_PATH.pattern()
							+ "\n- " + STD_PATH.pattern(),
					tokens[1]
			);
		}
		this.importPath = new ImportPath(tokens[1].section());
	}
}
