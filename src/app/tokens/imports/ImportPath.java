package app.tokens.imports;

import app.errors.lexing.WrongOrMissingCodeError;
import app.rules.Keywords;
import app.rules.abstractions.Rule;
import app.rules.nonterminals.SequenceRule;
import app.rules.terminals.LiteralRule;
import app.tokenization.tokens.TerminalToken;
import app.tokenization.tokens.Token;

public class ImportPath extends Token {

	static final Rule IMPORT_KEYWORD = new LiteralRule(Keywords.IMPORT);
	static final Rule IMPORT_PATH = new LiteralRule("path");
	public static final Rule IMPORT_STATEMENT = new SequenceRule(IMPORT_KEYWORD, IMPORT_PATH);

	final String path;

	public ImportPath(Token... tokens) {
		if (tokens.length != 2)
			throw new WrongOrMissingCodeError(this, "import statement", tokens);
		// Import keyword
		if (tokens[0] instanceof TerminalToken keywordToken && keywordToken.value.equals(Keywords.IMPORT))
			;
		else
			throw new WrongOrMissingCodeError(tokens[0], "import statement", "import keyword");
		// Path
		if (tokens[1] instanceof TerminalToken pathToken && pathToken.value.equals("path"))
			this.path = pathToken.value;
		else
			throw new WrongOrMissingCodeError(tokens[1], "import statement", "path");

	}

}
