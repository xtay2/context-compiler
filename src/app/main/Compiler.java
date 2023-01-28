package app.main;

import app.errors.CompilerError;
import app.errors.startup.FileReadingError;
import app.rules.nonterminals.SequenceRule;
import app.tokens.ProgramToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import static app.tokens.ProgramToken.IMPORTS;
import static app.tokens.definitions.NativeDeclaration.NATIVE_DECLARATION;

public class Compiler {

	private static Map<Path, ProgramToken> blueprints;

	public static void compile(Path srcPath) throws CompilerError {
		try {
			blueprints = Files.walk(srcPath)
					.filter(Files::isRegularFile)
					.filter(path -> path.toString().endsWith(Main.FILE_EXTENSION))
					.collect(Collectors.toMap(path -> path, path -> {
						try {
							return compile(path, Files.readString(path));
						} catch (OutOfMemoryError oome) {
							throw new FileReadingError(21, "Source file too large: " + path, oome);
						} catch (IOException ioe) {
							throw new FileReadingError(22, "Could not read source file at " + path, ioe);
						}
					}));
		} catch (IOException ioe) {
			throw new FileReadingError(20, "Could not walk source files at: " + srcPath, ioe);
		}
	}

	private static ProgramToken compile(Path filePath, String fileContent) {
		var program = new SequenceRule(t -> new ProgramToken(filePath, t), IMPORTS, NATIVE_DECLARATION).tokenizeWhole(fileContent);
		if (program instanceof ProgramToken p)
			return p;
		throw new AssertionError("ProgramToken expected");
	}

	public static ProgramToken getBlueprint(Path path) {
		return blueprints.get(path);
	}


}
