package app.parts.tokens.blueprints.content;

import app.io.ImportPath;
import app.parts.tokens.blueprints.SyntaxObject;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Function extends SyntaxObject {

	private final String name;

	private final Parameter[] params;

	private final Datatype returnType;

	public Function(ImportPath myFilePath, String name, Parameter[] params, Datatype returnType) {
		super(myFilePath);
		this.name = name;
		this.params = params;
		this.returnType = returnType;
	}

	private String c_funcName() {
		return myFilePath.path.replace('.', '_') + "$" + name;
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
		return "\n\n"; // TODO: Implement me!
	}

}
