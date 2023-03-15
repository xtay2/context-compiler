package app.parts.tokens.blueprints.content;

import app.io.ImportPath;

public class Datatype {

	private final ImportPath importPath;

	public Datatype(ImportPath importPath) {
		this.importPath = importPath;
	}

	public String c_signature() {
		return importPath.path.replace('.', '_') + "*";
	}

}
