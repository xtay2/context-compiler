package app.parts.tokens.blueprints;

import app.io.ImportPath;
import app.parts.tokens.blueprints.content.Attribute;
import app.parts.tokens.blueprints.content.Function;
import app.parts.tokens.blueprints.includes.AttributeProvider;
import app.parts.tokens.blueprints.includes.FunctionProvider;

public class EnumBlueprint extends Blueprint implements AttributeProvider, FunctionProvider {

	private final Attribute[] attributes;
	private final Function[] functions;

	public EnumBlueprint(
			ImportPath myFilePath,
			ImportPath[] imports,
			Attribute[] attributes,
			Function[] functions
	) {
		super(myFilePath, imports);
		this.attributes = attributes;
		this.functions = functions;
	}

	@Override
	public Attribute[] getAttributes() {
		return attributes;
	}

	@Override
	public Function[] getFunctions() {
		return functions;
	}
}
