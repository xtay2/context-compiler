package app.io;

import app.errors.GeneralError;
import app.parts.tokens.blueprints.Blueprint;
import helper.Constants;
import helper.io.FileHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static helper.Constants.*;

public class FileManager {

	// Paths

	/**
	 * The path to the project folder containing
	 * - src (the Context-source code)
	 * - cmp (the compiled C-code)
	 * - lib (the libraries)
	 * - res (the resources)
	 * - dat (additional project data)
	 */
	private static String projectPath;

	/**
	 * Set the path to the project folder.
	 * (This should be called once at the start of the program, before any other FileManager method.)
	 */
	public static void setProjectPath(String pathStr) {
		var pathObj = Path.of(pathStr);
		if (Files.notExists(pathObj))
			throw new GeneralError("The project path does not exist: " + pathStr);
		if (!Files.isDirectory(pathObj))
			throw new GeneralError("The project path is not a directory: " + pathStr);
		projectPath = pathObj.toAbsolutePath().toString();
	}

	public static Path getSourcePath() {
		return Path.of(projectPath, SOURCE_DIR);
	}

	public static Path getCompilePath() {
		return Path.of(projectPath, COMPILED_DIR);
	}

	public static Path getCompilePath(CompFileType compFileType, ImportPath importPath) {
		return Path.of(projectPath,
		               COMPILED_DIR,
		               importPath.compilePath(compFileType)
		);
	}

	public static Path getBinaryPath() {
		return Path.of(projectPath, BINARY_DIR);
	}

	/**
	 * Turns all files in  ending in {@link Constants#FILE_EXT} into a stream of {@link SourceFile}s.
	 */
	@SuppressWarnings("all")
	public static Stream<SourceFile> getSourceFiles() {
		try {
			return Files.walk(getSourcePath())
			            .filter(Files::isRegularFile)
			            .filter(p -> p.toString().endsWith(FILE_EXT))
			            .map(p -> {
				            try {
					            return new SourceFile(new ImportPath(p), Files.readString(p));
				            } catch (IOException ioe) {
					            throw new GeneralError("Could not read file " + p + "\n" + ioe.getMessage());
				            }
			            });
		} catch (IOException ioe) {
			throw new GeneralError("Could not walk the source path: " + getSourcePath() + "\n" + ioe.getMessage());
		}
	}

	/**
	 * Takes compiled code from a {@link Blueprint} and writes it to the corresponding C-file.
	 * <p>
	 * (Creates the file if it does not exist.)
	 */
	public static void writeCompiled(CompFileType compFileType, ImportPath importPath, String content) {
		var compiledPath = getCompilePath(compFileType, importPath);
		// Surround with guards if it is a header file
		if (compFileType.isHeader()) {
			content = "#ifndef " + importPath.fileName().toUpperCase() + "_H\n" +
					"#define " + importPath.fileName().toUpperCase() + "_H\n" +
					"\n" +
					content +
					"\n" +
					"#endif\n";
		}
		if (!FileHelper.write(compiledPath, content))
			throw new GeneralError("Could not write to " + compiledPath);
	}
}
