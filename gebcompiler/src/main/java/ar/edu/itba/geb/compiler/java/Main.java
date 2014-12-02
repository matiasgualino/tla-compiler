package ar.edu.itba.geb.compiler.java;

import ar.edu.itba.geb.compiler.java.jasmin.BytecodeCreator;
import ar.edu.itba.geb.compiler.java.symboltable.ClassDescriptor;
import ar.edu.itba.geb.compiler.java.symboltable.SymbolTable;
import ar.edu.itba.geb.compiler.java.symboltable.SymbolTableBuilderVisitor;
import ar.edu.itba.geb.compiler.java.syntaxtree.*;
import ar.edu.itba.geb.compiler.java.visitors.DepthFirstVisitor;
import ar.edu.itba.geb.compiler.java.visitors.TypeVisitorImpl;
import java_cup.*;
import java_cup.runtime.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		String subCommand = args[0];

		if (subCommand.equals("generate")) {
			GEBHelper.generate();
			return;
		}

		File file = new File(args[1]);

		if (!file.isFile()) {
			System.err.println("error: file does not exist");
			System.exit(1);
		}

		try {
			AnalizadorSintactico parser_obj;
			Program root;
			TypeVisitorImpl typeVisitor = new TypeVisitorImpl();
			SymbolTableBuilderVisitor symtBuilder = new SymbolTableBuilderVisitor();
			switch (subCommand) {
			case "run":
				parser_obj = new AnalizadorSintactico(new AnalizadorLexico(
						new InputStreamReader(new FileInputStream(file))));
				root = (Program) parser_obj.parse().value;

				System.out.println("Armando la tabla de simbolos...");
				root.accept(symtBuilder);

				System.out.println("Verificando los tipos de datos...");
				root.accept(typeVisitor);

				System.out.println("Building class assembly ...\n");

				BytecodeCreator visitor = new BytecodeCreator();
				root.accept(visitor);
				String jasmin_path = new File("").getAbsolutePath().toString()
						+ "/src/jasmin.jar";

				for (ClassDescriptor c : SymbolTable.getInstance()
						.getClassDescriptors()) {
					String classN = c.getName();
					String jasmin_class = classN + ".jasmin";

					String cmd = "java -jar %s -g %s";
					cmd = String.format(cmd, jasmin_path, jasmin_class);
					System.out.println(cmd);

					Process proc = Runtime.getRuntime().exec(cmd);

					String stdout = readInputStream(proc.getInputStream());
					String stderr = readInputStream(proc.getErrorStream());

					if (!stdout.isEmpty())
						System.out.println(stdout);

					if (!stderr.isEmpty())
						System.out.println(stderr);
				}

				System.out.println("\nListo!");
				break;
			case "compile":
				break;
			case "bytecode":
				break;
			case "tokens":
				break;
			default:
				System.err.println("Comando invalido: " + subCommand);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}

	private static String readInputStream(InputStream stream) throws Exception {
		Reader r = new InputStreamReader(stream, "ASCII");

		int n;
		StringBuffer sb = new StringBuffer(200);
		char[] buff = new char[100];

		while ((n = r.read(buff)) != -1) {
			sb.append(buff, 0, n);
		}

		return sb.toString();
	}
}
