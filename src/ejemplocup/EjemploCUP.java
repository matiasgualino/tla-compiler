package ejemplocup;

import java.io.File;
import java.lang.reflect.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mgualino
 */
public class EjemploCUP {

    public final static int GENERAR = 1;
    public final static int EJECUTAR = 2;
    public final static int SALIR = 3;

    /**
     * Es un menu para elegir entre generar el analizador lexico y sintactico, o
     * ejecutarlos sobre un archivo de prueba
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.util.Scanner in = new Scanner(System.in);
        int valor = 0;
        do {
            System.out.println("Elija una opcion: ");
            System.out.println("1) Generar");
            System.out.println("2) Ejecutar");
            System.out.println("3) Salir");
            System.out.print("Opcion: ");
            valor = in.nextInt();
            switch (valor) {
                /*  Generamos el analizador lexico y sintactico.
                 .flex contiene la definicion del analizador lexico
                 .cup contiene la definicion del analizador sintactico
                 */
                case GENERAR: {
                    System.out.println("\n*** Generando ***\n");
                    String archLexico = "";
                    String archSintactico = "";
                    
                    if (args.length > 0) {
                        System.out.println("\n*** Procesando archivos custom ***\n");
                        archLexico = args[0];
                        archSintactico = args[1];
                    } else {
                        System.out.println("\n*** Procesando archivo default ***\n");
                        archLexico = new File(".").getAbsolutePath().toString() + "/src/ejemplocup/alexico.flex";
                        archSintactico = new File(".").getAbsolutePath().toString() + "/src/ejemplocup/asintactico.cup";
                    }
                    String[] alexico = {archLexico};
                    String[] asintactico = {"-parser", "AnalizadorSintactico", archSintactico};
                    jflex.Main.main(alexico);
                    try {
                        java_cup.Main.main(asintactico);
                    } catch (Exception ex) {
                        Logger.getLogger(EjemploCUP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //movemos los archivos generados
                    boolean mvAL = moverArch("AnalizadorLexico.java");
                    boolean mvAS = moverArch("AnalizadorSintactico.java");
                    boolean mvSym= moverArch("sym.java");
                    if(mvAL && mvAS && mvSym){
                        System.exit(0);
                    }
                    System.out.println("Generado!");
                    break;
                }
                case EJECUTAR: {
					try {
						/*  Ejecutamos el analizador lexico y sintactico
	                     sobre un archivo de pruebas. 
	                     */
	                    String[] archivoPrueba = {new File(".").getAbsolutePath().toString() + "/src/ejemplocup/calc.gpb"};
	                    
	                    Class cls = Class.forName("ejemplocup.AnalizadorSintactico");
						Class partypes[] = new Class[1];
	                     partypes[0] = archivoPrueba.getClass();
	                     Method meth = cls.getMethod(
	                       "main", partypes);
	                     Object retobj 
	                       = meth.invoke(null, archivoPrueba);
	                    
	                    System.out.println("Ejecutado!");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                    break;
                }
                case SALIR: {
                    System.out.println("Fin");
                    break;
                }
                default: {
                    System.out.println("Opcion no valida");
                    break;
                }
            }
        } while (valor != 3);

    }

    public static boolean moverArch(String archNombre) {
        boolean efectuado = false;
        File arch = new File(archNombre);
        if (arch.exists()) {
            System.out.println("\n*** Moviendo " + arch + " \n***");
            
            String nuevoDir = new File(".").getAbsolutePath().toString()
                    + File.separator + "src" + File.separator
                    + "ejemplocup" + File.separator + arch.getName();
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