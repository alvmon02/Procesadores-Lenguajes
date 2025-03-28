
import asint.SintaxisAbstractaTiny.Prog;
import c_ast_ascendente.AnalizadorLexicoTiny;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import asint.SintaxisAbstractaTiny.Prog;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_ascendente.GestionErroresTiny.*;
import c_ast_descendente.ParseException;
import c_ast_descendente.TokenMgrError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.ByteArrayOutputStream;


public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

        if (args.length == 0) // Pruebas con archivos de tipo '.in' en un directorio aparte
            main_casos(args);
        else if (args.length == 1) { // Acepta un único argumento, un archivo a provesar
            main_archivo(args);
        } else
            System.err.println("ERROR: Númrero incorrecto de argumentos");
    }

    public static void main_casos(String[] args) throws FileNotFoundException, IOException, Exception {
        String directorioPath = "./casos"; // Puedes cambiarlo por el path deseado
        File directorio = new File(directorioPath);

        String[] archivos = directorio.list((dir, name) -> name.endsWith(".in"));

        PrintStream salidaOriginal = System.out;

        if (archivos != null) {
            for (String archivo : archivos) 
			{
                String outputName = archivo.substring(0, archivo.lastIndexOf('.')) + ".txt";
                Reader input = new InputStreamReader(new FileInputStream(directorioPath + "/" + archivo));
                PrintStream output = new PrintStream(new FileOutputStream(directorioPath + "/" + outputName));
                System.setOut(output);
				if (input.read()==97) {	//Reconocer a 
			

					try {
						AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
						c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTinyDJ(alex);
						System.out.println("CONSTRUCCION AST ASCENDENTE");
						Prog prog = (Prog) asint.debug_parse().value;
			
						System.out.println("IMPRESION RECURSIVA");
						new recursiva.Impresion().imprime(prog);
		
						System.out.println("IMPRESION INTERPRETE");
						prog.imprime();
		
						System.out.println("IMPRESION VISITANTE");
						prog.procesa(new visitante.Impresion());
						} catch (ErrorLexico e) {
							System.out.println("ERROR_LEXICO");
						} catch (ErrorSintactico | UnsupportedOperationException e) {
							System.out.println("ERROR_SINTACTICO");
						}
					} 
			else {	//si no es a(en su defecto, es decir, d)

				try {
					c_ast_descendente.ConstructorASTsTiny asint = new c_ast_descendente.ConstructorASTsTinyDJ(input);
					System.out.println("CONSTRUCCION AST DESCENDENTE");
					Prog prog = asint.analiza();
	
					System.out.println("IMPRESION RECURSIVA");
					new recursiva.Impresion().imprime(prog);

					System.out.println("IMPRESION INTERPRETE");
					prog.imprime();

					System.out.println("IMPRESION VISITANTE");
					prog.procesa(new visitante.Impresion());
				} catch (TokenMgrError e) {
					System.out.println("ERROR_LEXICO");
				} catch (ParseException | UnsupportedOperationException e) {
					System.out.println("ERROR_SINTACTICO");
				}
            }
                output.close();
        	}
		} else {
            System.err.println("El directorio no existe o no es accesible.");
        }
        System.setOut(salidaOriginal);
    }

    public static void main_archivo(String[] args) throws FileNotFoundException, IOException, Exception {
        PrintStream salida = System.out;
        Reader input = new InputStreamReader(new FileInputStream(args[0]));
				if (input.read()==97) {	//Reconocer a 
			

					try {
						AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
						c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTinyDJ(alex);
						System.out.println("CONSTRUCCION AST ASCENDENTE");
						Prog prog = (Prog) asint.debug_parse().value;
			
						System.out.println("IMPRESION RECURSIVA");
						new recursiva.Impresion().imprime(prog);
		
						System.out.println("IMPRESION INTERPRETE");
						prog.imprime();
		
						System.out.println("IMPRESION VISITANTE");
						prog.procesa(new visitante.Impresion());
						} catch (ErrorLexico e) {
							System.out.println("ERROR_LEXICO");
						} catch (ErrorSintactico e) {
							System.out.println("ERROR_SINTACTICO");
						}
					} 
			else {	//si no es a(en su defecto, es decir, d)

				try {
					c_ast_descendente.ConstructorASTsTiny asint = new c_ast_descendente.ConstructorASTsTinyDJ(input);
					System.out.println("CONSTRUCCION AST DESCENDENTE");
					Prog prog = asint.analiza();
	
					System.out.println("IMPRESION RECURSIVA");
					new recursiva.Impresion().imprime(prog);

					System.out.println("IMPRESION INTERPRETE");
					prog.imprime();

					System.out.println("IMPRESION VISITANTE");
					prog.procesa(new visitante.Impresion());
				} catch (TokenMgrError e) {
					System.out.println("ERROR_LEXICO");
				} catch (ParseException e) {
					System.out.println("ERROR_SINTACTICO");
				}
            }
    }

    private static class NullPrintStream extends PrintStream {

        public NullPrintStream() {
            super(new NullByteArrayOutputStream());
        }

        private static class NullByteArrayOutputStream extends ByteArrayOutputStream {

            @Override
            public void write(int b) {
                // do nothing
            }

            @Override
            public void write(byte[] b, int off, int len) {
                // do nothing
            }

            @Override
            public void writeTo(OutputStream out) throws IOException {
                // do nothing
            }
        }

    }

}


