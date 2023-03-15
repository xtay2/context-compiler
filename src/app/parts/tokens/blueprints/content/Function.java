package app.parts.tokens.blueprints.content;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Function {

	private final String name;

	private final Parameter[] params;

	private final Datatype returnType;

	public Function(String name, Parameter[] params, Datatype returnType) {
		this.name = name;
		this.params = params;
		this.returnType = returnType;
	}

	private String c_funcName() {
		return null; // TODO: Implement me!
	}

	private String c_params() {
		return Arrays.stream(params)
		             .map(Parameter::c_paramSignature)
		             .collect(Collectors.joining(", "));
	}

	public String c_funcSignature() {
		return returnType.c_signature() + " " + c_funcName() + "(" + c_params() + ");";
	}

	public String c_funcImpl() {
		return c_funcSignature() + " {\n" + c_funcBody() + "\n}";
	}

	private String c_funcBody() {
		return null; // TODO: Implement me!
	}

}
