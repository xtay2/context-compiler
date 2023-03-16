package app.parts.tokens.blueprints.content;

import java.util.Arrays;

public enum BlueprintType {

	MODULE,
	ENUM,
	STRUCT,
	CLASS;

	public static BlueprintType fromString(String type) {
		return switch (type) {
			case "module" -> MODULE;
			case "enum" -> ENUM;
			case "struct" -> STRUCT;
			case "class" -> CLASS;
			default -> throw new IllegalArgumentException("Unknown blueprint type: " + type);
		};
	}

	public static String[] keywords() {
		return Arrays.stream(values())
		             .map(BlueprintType::toString)
		             .toArray(String[]::new);
	}

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
