package app.parts.tokens.blueprints;

import app.io.ImportPath;
import app.parts.tokens.blueprints.content.Attribute;
import app.parts.tokens.blueprints.content.Function;
import app.parts.tokens.blueprints.includes.AttributeProvider;
import app.parts.tokens.blueprints.includes.FunctionProvider;

import java.util.List;

public class EnumBlueprint extends Blueprint implements AttributeProvider, FunctionProvider {

	private final List<Attribute> attributes;
	private final List<Function> functions;

	public EnumBlueprint(
			ImportPath importPath,
			List<ImportPath> imports,
			List<Attribute> attributes,
			List<Function> functions
	) {
		super(importPath, imports);
		this.attributes = attributes;
		this.functions = functions;
	}

	@Override
	public List<Attribute> getAttributes() {
		return attributes;
	}

	@Override
	public List<Function> getFunctions() {
		return functions;
	}
}
