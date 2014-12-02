package ar.itba.tla.compiler;

import java.io.File;

public class LexicalAnalyzer {

	public static void main(String[] args) {
		String path = "/Users/mgualino/Desktop/TLA/tla-javacompiler/src/ar/itba/tla/compiler/Lexer.flex";
		generarLexer(path);
	}

	
	public static void generarLexer(String archivo) {
		File f = new File(archivo);
		jflex.Main.generate(f);
	}
}
