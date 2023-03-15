package app.io;

import helper.Constants;

import java.io.File;
import java.nio.file.Path;

import static helper.Constants.*;

/**
 * A relative path to a source-file.
 * <p>
 * Structure:
 * - std.folder*.File
 * - lib.creator.project.folder*.File
 * (With an arbitrary number of folders in between.)
 */
public final class ImportPath {

	public final String path;

	public ImportPath(String path) {
		this.path = path;
		assert STD_PATH.matcher(path).matches() ||
				SRC_PATH.matcher(path).matches()
				: "Invalid import path: " + path;
	}

	public ImportPath(Path pathObj) {
		this(buildImportPath(pathObj));
	}


	@Override
	public boolean equals(Object obj) {
		return obj instanceof ImportPath otherPath && path.equals(otherPath.path);
	}

	@Override
	public int hashCode() {
		return path.hashCode();
	}

	/** Turns a sub-path of the source or standard library into an import path. */
	private static String buildImportPath(Path pathObj) {
		var str = pathObj.toString();
		str = str.substring(FileManager.getCompilePath().toString().length());
		str = str.substring(0, str.length() - Constants.FILE_EXT.length())
		         .replace(File.separatorChar, '.');
		return SOURCE_DIR + str;
	}

	public String fileName() {
		return path.substring(path.lastIndexOf('.') + 1);
	}

	String compilePath(CompFileType compFileType) {
		var name = fileName();
		return path.replace('.', File.separatorChar)
		           .substring(0, path.length() - name.length())
				+ File.separatorChar
				+ name + "_"
				+ compFileType.suffix()
				+ compFileType.fileExt();
	}

	@Override
	public String toString() {
		return path;
	}
}
