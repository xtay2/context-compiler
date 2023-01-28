package app.main;

import app.errors.CompilerError;
import app.errors.ErrorHandler;
import app.errors.startup.IllegalArgumentError;
import helper.io.PathHelper;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

	public static final String FILE_EXTENSION = ".con";
	private static String projectPath;

	public static void main(String[] args) {
		try {
			loadProject(args);
			Compiler.compile(getSourcePath());
		} catch (CompilerError e) {
			ErrorHandler.handle(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadProject(String[] args) {
		// Project path
		if(args.length < 1)
			throw new IllegalArgumentError(10,  "Please provide a project path as the first compiler-argument.");
		projectPath = args[0].replace('\\', '/');
		if(!PathHelper.isValidPath(projectPath))
			throw new IllegalArgumentError(11,  "The provided project path has an invalid format.", args[0]);
		if(Files.notExists(getProjectPath()))
			throw new IllegalArgumentError(12, "The provided project path does not exist.", args[0]);
		// Source path
		if(Files.notExists(getSourcePath()))
			throw new IllegalArgumentError(13, "The provided project path does not contain a source folder at: " + getSourceStr());
	}

	public static String getProjectStr() {
		return projectPath;
	}

	public static Path getProjectPath() {
		return Path.of(getProjectStr());
	}

	public static String getSourceStr() {
		return getProjectStr() + "\\src";
	}

	public static Path getSourcePath() {
		return Path.of(getSourceStr());
	}

}