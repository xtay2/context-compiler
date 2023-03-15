package app.parts.tokens.blueprints.includes;

import app.io.CompFileType;
import app.io.FileManager;
import app.io.ImportPath;
import app.parts.tokens.blueprints.Blueprint;
import helper.annotations.DefaultFinal;

import java.util.List;
import java.util.stream.Collectors;

import static app.io.CompFileType.include;

public sealed interface ImportProvider permits Blueprint, AttributeProvider, FunctionProvider {

	/** Generates all .h and .c files for this blueprint. */
	@DefaultFinal
	default void c_generateAll() {
		generateImportHeader();
		if (this instanceof FunctionProvider funcProv) {
			funcProv.generateFunctionHeader();
			funcProv.generateFunctionImplementation();
		}
		if (this instanceof AttributeProvider attrbProv)
			attrbProv.generateObjectHeader();
	}

	@DefaultFinal
	default void generateImportHeader() {
		var importPath = getImportPath();
		var content = getImports()
				.stream()
				.map(imp -> switch (imp) {
					case FunctionProvider ignored -> include(importPath, CompFileType.FUNC_HEADER);
					case AttributeProvider ignored -> include(importPath, CompFileType.OBJECT_HEADER);
					default -> throw new AssertionError("Unexpected value: " + imp);
				})
				.collect(Collectors.joining("\n"));
		FileManager.writeCompiled(CompFileType.IMPORT_HEADER, importPath, content);
	}

	ImportPath getImportPath();

	List<Blueprint> getImports();

}
