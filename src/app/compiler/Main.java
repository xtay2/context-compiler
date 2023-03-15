package app.compiler;

import app.errors.AbstractCompilerError;
import app.errors.GeneralError;
import app.io.FileManager;
import app.io.ImportPath;
import app.parts.rules.blueprints.BlueprintParser;
import app.parts.tokens.blueprints.Blueprint;
import helper.Constants;
import helper.io.ANSI;
import helper.io.FileHelper;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Collectors;

import static helper.Constants.PROGRAM_NAME;
import static helper.io.ANSI.*;

public class Main {

	private static Map<ImportPath, Blueprint> blueprints;

	public static void main(String[] args) {
		if (args.length != 1)
			throw new GeneralError("Expected a project path.");
		try {
			System.out.println("--- " + ANSI.color(ORANGE, "[Parsing Sources]") + " ---");
			FileManager.setProjectPath(args[0]);
			blueprints = FileManager.getSourceFiles()
			                        .collect(Collectors.toMap(
					                                 sf -> sf.path,
					                                 sf -> BlueprintParser.parse(sf.path, sf.content)
			                                 )
			                        );
			if (blueprints.isEmpty())
				throw new GeneralError("No source files found.\n" + FileManager.getSourcePath() + "---" + Constants.FILE_EXT);
			FileHelper.delete(FileManager.getCompilePath());
			blueprints.values().forEach(Blueprint::c_generateAll);
			compileToExe();
			runExe();
		} catch (AbstractCompilerError | AssertionError e) {
			e.printStackTrace();
		} catch (Throwable e) {
			throw new AssertionError("This java-Throwable should get caught.", e);
		}
	}

	public static Blueprint getBlueprint(ImportPath importPath) {
		var blueprint = blueprints.get(importPath);
		assert blueprint != null;
		return blueprint;
	}

	private static void compileToExe() {
		try {
			System.out.println("--- " + ANSI.color(CYAN, "[Compiling to C]") + " ---");
			var bin = FileManager.getBinaryPath();
			if (Files.notExists(bin))
				Files.createDirectories(bin);
			if (new ProcessBuilder()
					.directory(bin.toFile())
					.inheritIO()
					.command(
							"cmd.exe", "/c",
							"for /r \"" + FileManager.getCompilePath() + "\" %f in (*.c *.h) do gcc \"%f\" -o \"" + PROGRAM_NAME + "\"")
					.start().waitFor() != 0) throw new GeneralError("GCC failed.");
		} catch (Exception e) {
			throw new GeneralError("Could not compile to exe:\n" + e.getMessage());
		}
	}

	private static void runExe() {
		try {
			System.out.println("--- " + ANSI.color(GREEN, "[Running Executable]") + " ---");
			new ProcessBuilder()
					.directory(FileManager.getBinaryPath().toFile())
					.inheritIO()
					.command(
							"cmd.exe", "/c",
							FileManager.getBinaryPath() + File.separator + PROGRAM_NAME)
					.start().waitFor();
		} catch (Exception e) {
			throw new GeneralError("Could not run exe:\n" + e.getMessage());
		}
	}
}
