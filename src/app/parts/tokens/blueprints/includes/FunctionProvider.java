package app.parts.tokens.blueprints.includes;

import app.io.CompFileType;
import app.io.FileManager;
import app.parts.tokens.blueprints.content.Function;
import helper.annotations.DefaultFinal;

import java.util.stream.Collectors;

import static app.io.CompFileType.include;
import static java.util.Arrays.stream;

public non-sealed interface FunctionProvider extends ImportProvider {

	@DefaultFinal
	default void generateFunctionHeader() {
		var myFilePath = getImportPath();
		var content = (this instanceof AttributeProvider
		               ? include(myFilePath, CompFileType.OBJECT_HEADER)
		               : include(myFilePath, CompFileType.IMPORT_HEADER))
				+ c_funcSignatures();
		FileManager.writeCompiled(CompFileType.FUNC_HEADER, myFilePath, content);
	}

	@DefaultFinal
	default void generateFunctionImplementation() {
		var myFilePath = getImportPath();
		include(myFilePath, CompFileType.FUNC_HEADER);
		FileManager.writeCompiled(CompFileType.FUNC_IMPL, myFilePath, c_funcImpls());
	}

	@DefaultFinal
	default String c_funcSignatures() {
		return stream(getFunctions())
				.map(Function::c_funcSignature)
				.collect(Collectors.joining("\n\n"));
	}

	@DefaultFinal
	default String c_funcImpls() {
		return stream(getFunctions())
				.map(Function::c_funcImpl)
				.collect(Collectors.joining("\n\n"));
	}

	Function[] getFunctions();

}
