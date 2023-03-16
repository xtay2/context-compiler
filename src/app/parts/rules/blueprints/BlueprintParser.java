package app.parts.rules.blueprints;

import app.errors.ParsingError;
import app.io.ImportPath;
import app.parts.rules.blueprints.content.BlueprintDeclParser;
import app.parts.rules.blueprints.includes.AttributeParser;
import app.parts.rules.blueprints.includes.FunctionParser;
import app.parts.tokens.blueprints.*;
import app.parts.tokens.blueprints.content.Attribute;
import app.parts.tokens.blueprints.content.BlueprintDecl;
import app.parts.tokens.blueprints.content.Function;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;
import parser.app.tokens.collection.TokenList;

import static app.parts.rules.blueprints.content.BlueprintDeclParser.blueprintDecl;
import static app.parts.rules.blueprints.includes.AttributeParser.attributes;
import static app.parts.rules.blueprints.includes.FunctionParser.functions;

/**
 * A generic parser that can expect any blueprint.
 */
public final class BlueprintParser extends TokenArray {

	private static Rule blueprint(ImportPath myFilePath, ImportPath[] targetImports) {
		return new Ordered(
				tokens -> new BlueprintParser(myFilePath, targetImports, tokens),
				blueprintDecl(myFilePath),
				attributes(myFilePath, targetImports),
				functions(myFilePath, targetImports)
		);
	}

	// ------------------ Constructor ---------------------------------------------------------------

	private final Blueprint blueprint;

	private BlueprintParser(ImportPath myFilePath, ImportPath[] targetImports, Token[] tokens) {
		super(tokens);
		assert tokens.length == 3;
		// Parts
		var declaration = extractDeclaration(myFilePath, tokens);
		var attributes = extractAttributes(myFilePath, tokens);
		var functions = extractFunctions(myFilePath, tokens);
		// Initialize
		blueprint = switch (declaration.type) {
			case MODULE -> new ModuleBlueprint(myFilePath, targetImports, functions);
			case ENUM -> new EnumBlueprint(myFilePath, targetImports, attributes, functions);
			case STRUCT -> new StructBlueprint(myFilePath, targetImports, attributes);
			case CLASS -> new ClassBlueprint(myFilePath, targetImports, attributes, functions);
		};
	}

	// ------------------ Extractors / Checks --------------------------------------------------------

	private BlueprintDecl extractDeclaration(ImportPath myFilePath, Token[] tokens) {
		if (tokens[0].hasError())
			throw new ParsingError(myFilePath, "Error in blueprint-declaration: ", tokens[0]);
		return ((BlueprintDeclParser) tokens[0]).declaration;
	}

	private Attribute[] extractAttributes(ImportPath myFilePath, Token[] tokens) {
		if (tokens[1].hasError())
			throw new ParsingError(myFilePath, "Error in attributes: ", tokens[1]);
		return ((TokenList) tokens[1])
				.stream()
				.map((token) -> ((AttributeParser) token).attribute)
				.toArray(Attribute[]::new);
	}

	private Function[] extractFunctions(ImportPath myFilePath, Token[] tokens) {
		if (tokens[2].hasError())
			throw new ParsingError(myFilePath, "Error in functions: ", tokens[2]);
		return ((TokenList) tokens[2])
				.stream()
				.map((token) -> ((FunctionParser) token).function)
				.toArray(Function[]::new);
	}

	// ------------------ Static Methods ------------------------------------------------------------

	public static Blueprint parse(ImportPath myFilePath, ImportPath[] imports, String input) {
		return ((BlueprintParser) blueprint(myFilePath, imports).tokenize(input)).blueprint;
	}

}
