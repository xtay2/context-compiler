package app.parts.tokens.blueprints.content;

public abstract class Attribute {

	public final Datatype type;
	public final String name;

	public Attribute(Datatype type, String name) {
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
