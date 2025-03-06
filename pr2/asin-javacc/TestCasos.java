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

import asint.*;

public class TestCasos {

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
            for (String archivo : archivos) {
                String outputName = archivo.substring(0, archivo.lastIndexOf('.')) + ".txt";
                Reader input = new InputStreamReader(new FileInputStream(directorioPath + "/" + archivo));
                PrintStream output = new PrintStream(new FileOutputStream(directorioPath + "/" + outputName));
                System.setOut(output);
                try {
                    AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(input);
                    asint.analiza();
                } catch (ParseException e) {
                    System.out.println("ERROR_SINTACTICO");
                } catch (TokenMgrError e) {
                    System.out.println("ERROR_LEXICO");
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
        try {
            AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(input);
            System.setOut(new NullPrintStream());
            asint.analiza();
            System.setOut(salida);
        	System.out.println("OK");
        } catch (ParseException e) {
            System.setOut(salida);
            System.out.println("ERROR_SINTACTICO");
        } catch (TokenMgrError e) {
            System.setOut(salida);
            System.out.println("ERROR_LEXICO");
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