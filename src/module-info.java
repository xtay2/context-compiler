module context.compiler {

	requires recursive.descent.parser;
	requires helper;
	exports app;
	exports app.io;
	exports app.parts.tokens.blueprints;
}