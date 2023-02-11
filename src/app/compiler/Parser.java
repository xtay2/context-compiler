package app.compiler;

import app.errors.GeneralError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static app.compiler.Constants.FILE_EXT;
import static app.tokens.Blueprint.BLUEPRINT;

public class Parser {

	public static void parseFiles() {
		try (var stream = Files.walk(Path.of(Main.getProjectPath()))) {
			stream.filter(Files::isRegularFile)
					.filter(path -> path.endsWith(FILE_EXT))
					.forEach(Parser::parseFile);
		} catch (IOException e) {
			throw new GeneralError("Could not parse files at " + Main.getProjectPath());
		}
	}

	public static void parseFile(Path path) {
		try {
			var token = BLUEPRINT.tokenize(Files.readString(path));
			System.out.println("Valid file: " + path);
		} catch (IOException e) {
			throw new GeneralError("Could not parse file at " + Main.getProjectPath());
		}
	}

}
