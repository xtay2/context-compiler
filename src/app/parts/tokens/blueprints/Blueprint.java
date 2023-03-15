package app.parts.tokens.blueprints;

import app.compiler.Main;
import app.io.ImportPath;
import app.parts.tokens.blueprints.includes.ImportProvider;

import java.util.List;

public abstract non-sealed class Blueprint implements ImportProvider {

	private final ImportPath importPath;

	private final List<ImportPath> imports;

	Blueprint(
			ImportPath importPath,
			List<ImportPath> imports
	) {
		this.importPath = importPath;
		this.imports = imports;
	}

	@Override
	public final ImportPath getImportPath() {
		return importPath;
	}

	public final List<Blueprint> getImports() {
		return imports.stream().map(Main::getBlueprint).toList();
	}
}
