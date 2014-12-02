package ar.edu.itba.geb.compiler.java;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import ar.edu.itba.geb.compiler.java.syntaxtree.Program;
import java_cup.runtime.Symbol;

public class GEBHelper {

	public static void run(String file, String[] args) {
		/*try {
			Symbol sy;
			Program collect;
			AnalizadorSintactico parser_obj = new AnalizadorSintactico(new AnalizadorLexico(new InputStreamReader(new FileInputStream(file)))); 
			sy = parser_obj.parse();
			System.out.println("Codigo aceptado!");
			System.out.println("Root Node: " + sy.value);
		}
		catch (IOException e)
		{
			System.err.println("ERROR: Unable to open file: " + args[0]);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
		}*/
	}
	
	public static void generate() {
		System.out.println("\n*** Generando ***\n");
		String flexDir = new File("").getAbsolutePath().toString() + "/src/main/java/ar/edu/itba/geb/compiler/jflex/";
		String archLexico = flexDir + "Parser.flex";
        String archSintactico = new File("").getAbsolutePath().toString() + "/src/main/java/ar/edu/itba/geb/compiler/cup/Parser.cup";
        
        String[] alexico = {archLexico};
        String[] asintactico = {"-parser", "AnalizadorSintactico", archSintactico};
        jflex.Main.main(alexico);
        try {
            java_cup.Main.main(asintactico);
        } catch (Exception ex) {
            Logger.getLogger(GEBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        //movemos los archivos generados
        boolean mvAL = moverArch(flexDir + "AnalizadorLexico.java");
        boolean mvAS = moverArch("AnalizadorSintactico.java");
        boolean mvSym = moverArch("sym.java");
        if(mvAL && mvAS && mvSym){
            System.exit(0);
        }
        System.out.println("Generado!");
	}
	
	private static boolean moverArch(String archNombre) {
        boolean efectuado = false;
        File arch = new File(archNombre);
        if (arch.exists()) {
            System.out.println("\n*** Moviendo " + arch + " \n***");
            String nuevoDir = new File("").getAbsolutePath().toString() + "/src/main/java/ar/edu/itba/geb/compiler/java/" + arch.getName();
            File archViejo = new File(nuevoDir);
            archViejo.delete();
            
            if (arch.renameTo(new File(nuevoDir))) {
                System.out.println("\n*** Generado " + archNombre + "***\n");
                efectuado = true;
            } else {
                System.out.println("\n*** No movido " + archNombre + " ***\n");
            }

        } else {
            System.out.println("\n*** Codigo no existente ***\n");
        }
        return efectuado;
    }
	
}
