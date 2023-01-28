package app.rules;

import app.rules.abstractions.Rule;
import app.rules.nonterminals.OptionalRule;
import app.rules.terminals.LiteralRule;

public interface Rules {

	Rule OPT_LINE_BREAK = new OptionalRule(new LiteralRule("\n"));
	Rule DECLARATOR = new LiteralRule(":");

}
