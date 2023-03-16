package helper;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public interface Constants {

	// Symbols

	char OPEN_BLOCK = '{',
			CLOSE_BLOCK = '}',
			DECLARATOR = ':';

	// Files

	String FILE_EXT = ".con";

	String SOURCE_DIR = "src",
			COMPILED_DIR = "cmp",
			STD_LIB_DIR = "std",
			BINARY_DIR = "bin";

	// Keywords

	String IMPORT_KEYWORD = "import";


	// Patterns

	Pattern BLUEPRINT_NAME = compile("\\b[A-Z][A-Za-z]{0,31}\\b"),
			FUNCTION_NAME = compile("\\b[a-z]{1,32}\\b"),
			VAR_NAME = compile("\\b[a-z]{1,32}\\b");

	Pattern SRC_PATH = compile(SOURCE_DIR + "(\\.[a-z]+(-[a-z])*)*\\." + BLUEPRINT_NAME),
			STD_PATH = compile(STD_LIB_DIR + "(\\.[a-z]+(-[a-z])*)*\\." + BLUEPRINT_NAME);

	// Misc

	String PROGRAM_NAME = "Program.exe";
}
