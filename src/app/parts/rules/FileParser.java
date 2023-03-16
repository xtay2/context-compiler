package app.parts.rules;

import app.io.ImportPath;
import app.parts.rules.blueprints.BlueprintParser;
import app.parts.rules.blueprints.includes.ImportParser;
import app.parts.tokens.blueprints.Blueprint;
import helper.Constants;

import java.util.regex.Pattern;

public class FileParser {

	public static Blueprint parse(ImportPath myFilePath, String input) {
		var split = splitImportBlueprint(input);
		assert split.length == 2;
		return BlueprintParser.parse(
				myFilePath,
				ImportParser.parse(myFilePath, split[0]),
				split[1]
		);
	}

	private static String[] splitImportBlueprint(String input) {
		var matcher = Pattern.compile(Constants.IMPORT_KEYWORD + "\s*[a-zA-Z.-]+").matcher(input);
		var end = 0;
		while (matcher.find()) end = matcher.end();
		return new String[]{input.substring(0, end).strip(), input.substring(end).strip()};
	}

}
