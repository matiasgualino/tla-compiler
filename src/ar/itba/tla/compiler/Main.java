package ar.itba.tla.compiler;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		String path = "//";
		generarLexer(path);
	}

	
	public static void generarLexer(String archivo) {
		File f = new File(archivo);
		jflex.Main.generate(f);
	}
}
