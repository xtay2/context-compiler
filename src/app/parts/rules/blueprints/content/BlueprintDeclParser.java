package app.parts.rules.blueprints.content;

import app.errors.ParsingError;
import app.io.ImportPath;
import app.parts.tokens.blueprints.content.BlueprintDecl;
import app.parts.tokens.blueprints.content.BlueprintType;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.extensions.Alteration;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Literal;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;

import java.util.Arrays;
import java.util.stream.Collectors;

import static helper.Constants.DECLARATOR;

public final class BlueprintDeclParser extends TokenArray {

	// ------------------ Rules ---------------------------------------------------------------------

	public static Rule blueprintDecl(ImportPath myFilePath) {
		return new Ordered(
				tokens -> new BlueprintDeclParser(myFilePath, tokens),
				new Alteration(false, BlueprintType.keywords()),
				new Literal(DECLARATOR)
		);
	}

	// ------------------ Constructor ---------------------------------------------------------------

	public final BlueprintDecl declaration;

	private BlueprintDeclParser(ImportPath myFilePath, Token[] tokens) {
		super(tokens);
		assert tokens.length == 2;
		// Parts
		var type = extractType(myFilePath, tokens);
		checkDeclarator(myFilePath, tokens);
		// Initialize
		this.declaration = new BlueprintDecl(type);
	}

	// ------------------ Extractors / Checks --------------------------------------------------------

	private static BlueprintType extractType(ImportPath myFilePath, Token[] tokens) {
		if (tokens[0].hasError()) {
			throw new ParsingError(
					myFilePath,
					"Expected one of the blueprint types: " +
							Arrays.stream(BlueprintType.keywords())
							      .map(keyword -> "\"" + keyword + "\"")
							      .collect(Collectors.joining(", ")),
					tokens[0]
			);
		}
		return BlueprintType.fromString(tokens[0].section());
	}

	private static void checkDeclarator(ImportPath myFilePath, Token[] tokens) {
		if (tokens[1].hasError()) {
			throw new ParsingError(
					myFilePath,
					"Expected a \":\" at the end of the blueprint declaration",
					tokens[1]
			);
		}
	}
}
