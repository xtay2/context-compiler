package app.parts.rules.blueprints;

import app.errors.ParsingError;
import app.io.ImportPath;
import app.parts.rules.blueprints.content.BlueprintDeclParser;
import app.parts.rules.blueprints.includes.AttributeParser;
import app.parts.rules.blueprints.includes.FunctionParser;
import app.parts.rules.blueprints.includes.ImportParser;
import app.parts.tokens.blueprints.*;
import app.parts.tokens.blueprints.content.Attribute;
import app.parts.tokens.blueprints.content.BlueprintDecl;
import app.parts.tokens.blueprints.content.Function;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.nonterminals.extensions.Alteration;
import parser.app.rules.nonterminals.multi.Ordered;
import parser.app.rules.terminals.Literal;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;
import parser.app.tokens.collection.TokenList;

import java.util.List;

import static app.parts.rules.blueprints.includes.AttributeParser.ATTRIBUTES;
import static app.parts.rules.blueprints.includes.FunctionParser.FUNCTIONS;
import static helper.Constants.*;

/**
 * A generic parser that can expect any blueprint.
 */
public final class BlueprintParser extends TokenArray {

	private static final Rule BLUEPRINT_DECLARATION = new Ordered(
			new Alteration(false, CLASS_KEYWORD, ENUM_KEYWORD, MODULE_KEYWORD, STRUCT_KEYWORD),
			new Literal(DECLARATOR)
	);

	private static Rule blueprint(ImportPath importPath) {
		return new Ordered(
				(rule, tokens) -> new BlueprintParser(importPath, tokens),
				ImportParser.imports(importPath),
				BLUEPRINT_DECLARATION,
				ATTRIBUTES,
				FUNCTIONS
		);
	}

	private final ImportPath importPath;
	private final List<ImportPath> imports;

	private final BlueprintDecl declaration;
	private final List<Attribute> attributes;
	private final List<Function> functions;

	private BlueprintParser(ImportPath importPath, Token... tokens) {
		super(tokens);
		this.importPath = importPath;
		assert tokens.length == 4;
		// Imports
		if (tokens[0] instanceof TokenList importList)
			this.imports = importList.stream().map(t -> ((ImportParser) t).importPath).toList();
		else throw new AssertionError("Expected a list of imports.");
		// Blueprint type
		if (tokens[1] instanceof BlueprintDeclParser bdp)
			this.declaration = bdp.declaration;
		else throw new AssertionError("Expected a blueprint declaration.");
		// Attributes
		if (tokens[2] instanceof TokenList attributeList)
			this.attributes = attributeList.stream().map(t -> ((AttributeParser) t).attribute).toList();
		else throw new AssertionError("Expected a list of attributes.");
		// Functions
		if (tokens[3] instanceof TokenList functionList)
			this.functions = functionList.stream().map(t -> ((FunctionParser) t).function).toList();
		else throw new AssertionError("Expected a list of functions.");
	}

	public static Blueprint parse(ImportPath importPath, String input) {
		var token = blueprint(importPath).tokenize(input);
		if (token instanceof BlueprintParser bpp)
			return bpp.parse();
		throw new ParsingError(importPath, "Couldn't parse blueprint", token);
	}

	private Blueprint parse() {
		return switch (declaration.blueprintType) {
			case CLASS_KEYWORD -> new ClassBlueprint(importPath, imports, attributes, functions);
			case ENUM_KEYWORD -> new EnumBlueprint(importPath, imports, attributes, functions);
			case MODULE_KEYWORD -> new ModuleBlueprint(importPath, imports, functions);
			case STRUCT_KEYWORD -> new StructBlueprint(importPath, imports, attributes);
			default -> throw new IllegalStateException("Unexpected value: " + declaration.blueprintType);
		};
	}
}
