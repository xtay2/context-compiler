package app.parts.tokens.blueprints;

import app.io.ImportPath;
import app.parts.tokens.blueprints.content.Attribute;
import app.parts.tokens.blueprints.includes.AttributeProvider;

import java.util.List;

public class StructBlueprint extends Blueprint implements AttributeProvider {

	private final List<Attribute> attributes;

	public StructBlueprint(
			ImportPath importPath,
			List<ImportPath> imports,
			List<Attribute> attributes
	) {
		super(importPath, imports);
		this.attributes = attributes;
	}

	@Override
	public List<Attribute> getAttributes() {
		return attributes;
	}
}
