import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

import asint.*;
import errors.GestionErroresTiny.ErrorLexico;
import errors.GestionErroresTiny.ErrorSintactico;

public class TestCasos {
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
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
                } catch (ErrorSintactico e) {
                    System.out.println("ERROR_SINTACTICO");
                } catch (ErrorLexico e) {
                    System.out.println("ERROR_LEXICO");
                }
                output.close();
            }
        } else {
            System.err.println("El directorio no existe o no es accesible.");
        }
        System.setOut(salidaOriginal);
    }
}