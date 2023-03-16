package app.parts.tokens.blueprints;

import app.Main;
import app.io.ImportPath;
import app.parts.tokens.blueprints.includes.ImportProvider;

import java.util.Arrays;
import java.util.List;

public abstract non-sealed class Blueprint
		extends SyntaxObject
		implements ImportProvider {

	private final ImportPath[] imports;

	Blueprint(
			ImportPath myFilePath,
			ImportPath[] imports
	) {
		super(myFilePath);
		this.imports = imports;
	}

	@Override
	public final ImportPath getImportPath() {
		return myFilePath;
	}

	public final List<Blueprint> getImports() {
		return Arrays.stream(imports).map(p -> Main.getBlueprint(myFilePath, p)).toList();
	}
}
