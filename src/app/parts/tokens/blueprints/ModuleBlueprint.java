package app.parts.tokens.blueprints;

import app.io.ImportPath;
import app.parts.tokens.blueprints.content.Function;
import app.parts.tokens.blueprints.includes.FunctionProvider;

public class ModuleBlueprint extends Blueprint implements FunctionProvider {

	private final Function[] functions;

	public ModuleBlueprint(
			ImportPath myFilePath,
			ImportPath[] imports,
			Function[] functions
	) {
		super(myFilePath, imports);
		this.functions = functions;
	}

	@Override
	public Function[] getFunctions() {
		return functions;
	}
}
