package app.compiler;

import app.errors.GeneralError;

public class Main {

	private static String projectPath;

	public static void main(String[] args) {
		if (args.length != 1)
			throw new GeneralError("Expected a project path.");
		projectPath = args[0];
		Parser.parseFiles();
	}

	public static String getProjectPath() {
		return projectPath;
	}

}
