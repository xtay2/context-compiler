package app.parts.tokens.blueprints.content;

import app.io.ImportPath;
import app.parts.tokens.blueprints.SyntaxObject;

public class Attribute extends SyntaxObject {

	public final Datatype type;
	public final String name;

	public Attribute(ImportPath myFilePath, Datatype type, String name) {
		super(myFilePath);
		this.type = type;
		this.name = name;
	}

	public final String c_datatype() {
		return type.c_signature();
	}

	public final String c_name() {
		return "";
	}

}
