package app.parts.rules.blueprints.content;

import app.parts.tokens.blueprints.content.Datatype;
import parser.app.rules.abstractions.Rule;
import parser.app.rules.terminals.Pattern;

import static helper.Constants.BLUEPRINT_NAME;

public class DatatypeParser {
	public static final Rule DATATYPE = new Pattern(BLUEPRINT_NAME);

	public Datatype datatype;

	public DatatypeParser(Rule rule, Datatype datatype) {
		super(rule);
		this.datatype = datatype;
	}
}
