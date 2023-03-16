package app.parts.tokens.blueprints.includes;

import app.io.CompFileType;
import app.io.FileManager;
import app.parts.tokens.blueprints.content.Attribute;
import app.parts.tokens.blueprints.content.Datatype;
import helper.annotations.DefaultFinal;

import static app.io.CompFileType.include;

public non-sealed interface AttributeProvider extends ImportProvider {

	@DefaultFinal
	default void generateObjectHeader() {
		var myFilePath = getImportPath();
		include(myFilePath, CompFileType.IMPORT_HEADER);
		FileManager.writeCompiled(CompFileType.OBJECT_HEADER, myFilePath, c_typedefStruct());
	}

	@DefaultFinal
	default String c_typedefStruct() {
		var structName = new Datatype(
				getImportPath(),
				getImportPath()
		).c_structName();
		var sb = new StringBuilder()
				.append("typedef struct ")
				.append(structName)
				.append(" {\n");
		for (var attribute : getAttributes()) {
			sb.append("\t")
			  .append(attribute.c_datatype())
			  .append(" ")
			  .append(attribute.c_name())
			  .append(";\n");
		}
		return sb.append("} ")
		         .append(structName)
		         .append(";\n")
		         .toString();
	}

	Attribute[] getAttributes();

}
