package app.errors;

public class ErrorHandler {

	public static <E extends CompilerError> void handle(E error) {
		error.printStackTrace();
	}

}
