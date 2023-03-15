package app.parts.tokens.blueprints.includes;

import app.io.CompFileType;
import app.io.FileManager;
import app.parts.tokens.blueprints.content.Attribute;
import app.parts.tokens.blueprints.content.Datatype;
import helper.annotations.DefaultFinal;

import java.util.List;

import static app.io.CompFileType.include;

public non-sealed interface AttributeProvider extends ImportProvider {

	@DefaultFinal
	default void generateObjectHeader() {
		var importPath = getImportPath();
		include(importPath, CompFileType.IMPORT_HEADER);
		FileManager.writeCompiled(CompFileType.OBJECT_HEADER, importPath, c_typedefStruct());
	}

	@DefaultFinal
	default String c_typedefStruct() {
		StringBuilder sb = new StringBuilder()
				.append("typedef struct ")
				.append(asDatatype().c_signature())
				.append(" {\n");
		for (var attribute : getAttributes()) {
			sb.append("\t")
			  .append(attribute.c_datatype())
			  .append(" ")
			  .append(attribute.c_name())
			  .append(";\n");
		}
		return sb.append("} ")
		         .append(";\n")
		         .toString();
	}

	/** Returns this as a {@link Datatype} that can get referenced by other blueprints. */
	@DefaultFinal
	default Datatype asDatatype() {
		return new Datatype(getImportPath());
	}

	List<Attribute> getAttributes();

}
