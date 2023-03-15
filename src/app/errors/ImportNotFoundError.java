package app.errors;

import app.io.ImportPath;

public class ImportNotFoundError extends CompilationError {

	public ImportNotFoundError(ImportPath path) {
		super("Couldnot find " + path);
	}

}
