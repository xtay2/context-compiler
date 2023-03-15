package app.io;

public class SourceFile {

	public final ImportPath path;
	public final String content;

	public SourceFile(ImportPath path, String content) {
		this.path = path;
		this.content = content;
	}
}
