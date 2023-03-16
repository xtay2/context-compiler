package app.parts.tokens.blueprints.content;

import app.errors.ImportError;
import app.io.ImportPath;
import app.parts.tokens.blueprints.SyntaxObject;

import static java.util.Arrays.stream;

public class Datatype extends SyntaxObject {

	private final ImportPath target;

	public Datatype(ImportPath myFilePath, ImportPath[] targetImports, String name) {
		super(myFilePath);
		var matchingPaths = stream(targetImports)
				.filter(i -> i.fileName().equals(name))
				.toArray(ImportPath[]::new);
		if (matchingPaths.length == 1)
			target = matchingPaths[0];
		else {
			if (matchingPaths.length == 0)
				throw new ImportError(myFilePath, name);
			throw new ImportError(myFilePath, name, matchingPaths);
		}
	}

	public Datatype(ImportPath myFilePath, ImportPath target) {
		super(myFilePath);
		this.target = target;
	}

	public String c_structName() {
		assert !isVoid();
		return target.path.replace('.', '_');
	}

	public String c_signature() {
		if (isVoid())
			return "void";
		return c_structName() + "*";
	}

	public boolean isVoid() {
		return target == null;
	}


	public static Datatype returnVoid(ImportPath myFilePath) {
		return new Datatype(myFilePath, null);
	}
}
