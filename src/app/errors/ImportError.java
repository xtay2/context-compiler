package app.errors;

import app.io.ImportPath;

import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class ImportError extends AbstractCompilerError {

	public ImportError(ImportPath myFilePath, String datatypeName) {
		super(myFilePath, "Unknown type: " + datatypeName);
	}

	public ImportError(ImportPath myFilePath, String datatypeName, ImportPath[] targetImports) {
		super(
				myFilePath,
				"Ambiguous imports for \"" + datatypeName + "\":\n"
						+ stream(targetImports)
						.map(ImportPath::toString)
						.collect(Collectors.joining("\n-"))
		);
	}

}
