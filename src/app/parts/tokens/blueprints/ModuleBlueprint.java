package app.parts.tokens.blueprints;

import app.io.ImportPath;
import app.parts.tokens.blueprints.content.Function;
import app.parts.tokens.blueprints.includes.FunctionProvider;

import java.util.List;

public class ModuleBlueprint extends Blueprint implements FunctionProvider {

	private final List<Function> functions;

	public ModuleBlueprint(
			ImportPath importPath,
			List<ImportPath> imports,
			List<Function> functions
	) {
		super(importPath, imports);
		this.functions = functions;
	}

	@Override
	public List<Function> getFunctions() {
		return functions;
	}
}
