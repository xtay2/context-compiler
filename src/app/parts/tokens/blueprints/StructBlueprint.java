package app.parts.tokens.blueprints;

import app.io.ImportPath;
import app.parts.tokens.blueprints.content.Attribute;
import app.parts.tokens.blueprints.includes.AttributeProvider;

public class StructBlueprint extends Blueprint implements AttributeProvider {

	private final Attribute[] attributes;

	public StructBlueprint(
			ImportPath myFilePath,
			ImportPath[] imports,
			Attribute[] attributes
	) {
		super(myFilePath, imports);
		this.attributes = attributes;
	}

	@Override
	public Attribute[] getAttributes() {
		return attributes;
	}
}
