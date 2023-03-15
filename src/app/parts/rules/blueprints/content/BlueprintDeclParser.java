package app.parts.rules.blueprints.content;

import app.parts.tokens.blueprints.content.BlueprintDecl;
import parser.app.tokens.Token;
import parser.app.tokens.collection.TokenArray;

public class BlueprintDeclParser extends TokenArray {

	public final BlueprintDecl declaration;

	private BlueprintDeclParser(Token... tokens) {
		super(tokens);
	}
}
