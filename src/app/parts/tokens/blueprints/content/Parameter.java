package app.parts.tokens.blueprints.content;

public class Parameter {

	private final Datatype type;

	private final String name;

	public Parameter(Datatype type, String name) {
		this.type = type;
		this.name = name;
	}

	String c_paramSignature() {
		return type.c_signature() + " " + name;
	}

}
