package app.tokens;

import app.errors.lexing.WrongOrMissingCodeError;
import app.main.Main;
import app.rules.abstractions.Rule;
import app.rules.nonterminals.MultipleRule;
import app.rules.nonterminals.OptionalRule;
import app.tokenization.tokens.ErroneousTerminal;
import app.tokenization.tokens.Token;
import app.tokenization.tokens.TokenSection;
import app.tokens.imports.ImportPath;

import java.nio.file.Path;
import java.util.Arrays;

import static app.tokens.imports.ImportPath.IMPORT_STATEMENT;

public class ProgramToken extends Token {

	public static final Rule IMPORTS = new OptionalRule(new MultipleRule(IMPORT_STATEMENT));

	final Path filePath;
	final ImportPath[] imports;

	public ProgramToken(Path filePath, Token... tokens) {
		this.filePath = filePath;
		if (tokens.length != 2)
			throw new WrongOrMissingCodeError(this, inputPath(), tokens);
		imports = initImports(tokens[0]);
	}

	private ImportPath[] initImports(Token token) {
		if (token instanceof TokenSection section) {
			return Arrays.stream(section.children).map(t -> {
				if (t instanceof ImportPath path)
					return path;
				else
					throw new WrongOrMissingCodeError(t, inputPath(), "import statement");
			}).toArray(ImportPath[]::new);
		} else if (token instanceof ErroneousTerminal) {
			return new ImportPath[0];
		} else
			throw new WrongOrMissingCodeError(token, inputPath(), "import statement");
	}


	public final String inputPath() {
		var path = filePath.subpath(Main.getSourcePath().getNameCount(), filePath.getNameCount()).toString();
		return "std." + path.substring(0, path.length() - Main.FILE_EXTENSION.length());
	}
}
