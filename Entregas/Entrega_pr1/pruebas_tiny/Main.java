package pruebas_tiny;

import implementacion_jflex.alex.ALexOperations.ECaracterInesperado;
import implementacion_jflex.alex.AnalizadorLexicoTiny;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import implementacion_jflex.alex.ClaseLexica;
import implementacion_jflex.alex.UnidadLexica;

public class Main {
  public static void main(String[] args) throws FileNotFoundException, IOException {
    String directorioPath = "./Entrega/pruebas_tiny/casos"; // Puedes cambiarlo por el path deseado
    File directorio = new File(directorioPath);

    String[] archivos = directorio.list((dir, name) -> name.endsWith(".in"));

    if (archivos != null) {
      for (String archivo : archivos) {
        Reader input = new InputStreamReader(new FileInputStream(directorioPath + "/" + archivo));
        AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
        UnidadLexica unidad = null;
        do {
          try {
            unidad = al.yylex();
            System.out.println(unidad);
          } catch (ECaracterInesperado e) {
            System.out.println(e.getMessage());
            System.exit(1);
          }
        } while (unidad.clase() != ClaseLexica.EOF);

        System.out.println(archivo);
      }
    } else {
      System.out.println("El directorio no existe o no es accesible.");
    }

    // String[] archivos = directorio.list((dir, name) -> name.endsWith(".in"))

    // return;

    //
  }
}
