package app.parts.tokens.blueprints.includes;

import app.io.CompFileType;
import app.io.FileManager;
import app.parts.tokens.blueprints.content.Function;
import helper.annotations.DefaultFinal;

import java.util.List;
import java.util.stream.Collectors;

import static app.io.CompFileType.include;

public non-sealed interface FunctionProvider extends ImportProvider {

	@DefaultFinal
	default void generateFunctionHeader() {
		var importPath = getImportPath();
		var content = (this instanceof AttributeProvider
		               ? include(importPath, CompFileType.OBJECT_HEADER)
		               : include(importPath, CompFileType.IMPORT_HEADER))
				+ c_funcSignatures();
		FileManager.writeCompiled(CompFileType.FUNC_HEADER, importPath, content);
	}

	@DefaultFinal
	default void generateFunctionImplementation() {
		var importPath = getImportPath();
		include(importPath, CompFileType.FUNC_HEADER);
		FileManager.writeCompiled(CompFileType.FUNC_IMPL, importPath, c_funcImpls());
	}

	@DefaultFinal
	default String c_funcSignatures() {
		return getFunctions()
				.stream()
				.map(Function::c_funcSignature)
				.collect(Collectors.joining("\n\n"));
	}

	@DefaultFinal
	default String c_funcImpls() {
		return getFunctions()
				.stream()
				.map(Function::c_funcImpl)
				.collect(Collectors.joining("\n\n"));
	}

	List<Function> getFunctions();

}
