package app.io;

/**
 * Every module consists of up to four files:
 * - An import header file (containing the import declarations)
 * - An object header file (containing the object declaration)
 * - A function header file (containing the function declarations)
 * - A function implementation file (containing the function implementations)
 * <hr>
 * <pre> Rules:
 * - If there is a {@link #FUNC_IMPL} file, there has to be a {@link #FUNC_HEADER} file.
 * - The {@link #FUNC_IMPL} file has to include the {@link #FUNC_HEADER} file.
 * - If there is a {@link #OBJECT_HEADER} file
 *    - it has to include the {@link #IMPORT_HEADER} file.
 *    - the {@link #FUNC_HEADER} has to include the {@link #OBJECT_HEADER} file if it exists.
 * - If there is no {@link #OBJECT_HEADER} file the {@link #FUNC_HEADER} has to include the {@link #IMPORT_HEADER} file.
 * </pre>
 */
public enum CompFileType {

	IMPORT_HEADER,
	OBJECT_HEADER,
	FUNC_HEADER,
	FUNC_IMPL;

	public String suffix() {
		return switch (this) {
			case IMPORT_HEADER -> "imp_decl";
			case OBJECT_HEADER -> "obj_decl";
			case FUNC_HEADER -> "func_decl";
			case FUNC_IMPL -> "func_impl";
		};
	}

	public String fileExt() {
		return isHeader() ? ".h" : ".c";
	}

	public boolean isHeader() {
		return switch (this) {
			case IMPORT_HEADER, OBJECT_HEADER, FUNC_HEADER -> true;
			case FUNC_IMPL -> false;
		};
	}

	public static String include(ImportPath target, CompFileType objectHeader) {
		return "#include \"" + target.fileName() + "_" + objectHeader.suffix() + objectHeader.fileExt() + "\"\n";
	}
}