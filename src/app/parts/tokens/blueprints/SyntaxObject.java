package app.parts.tokens.blueprints;

import app.io.ImportPath;

import static java.util.Objects.requireNonNull;

public class SyntaxObject {

	/** The import path of the file in which this syntax object is defined. */
	public final ImportPath myFilePath;

	public SyntaxObject(ImportPath myFilePath) {
		this.myFilePath = requireNonNull(myFilePath);
	}

}
