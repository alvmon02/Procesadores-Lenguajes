package pruebas_tiny_0;

import implementacion_manual.alex.AnalizadorLexicoTiny.ECaracterInesperado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import implementacion_manual.alex.*;

public class Main {
  private static void imprime(UnidadLexica unidad) {
    switch (unidad.clase()) {
      case IDEN:
      case ENT:
      case REAL:
        System.out.println(unidad.lexema());
        break;
      default:
        System.out.println(unidad.clase().getImage());
    }
  }

  public static void main(String[] args) throws FileNotFoundException, IOException {
    String directorioPath = "./Entrega/pruebas_tiny_0/casos"; // Puedes cambiarlo por el path deseado
    File directorio = new File(directorioPath);

    String[] archivos = directorio.list((dir, name) -> name.endsWith(".in"));

    if (archivos != null) {
      for (String archivo : archivos) {
        Reader input = new InputStreamReader(new FileInputStream(directorioPath + "/" + archivo));
        AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
        UnidadLexica unidad = null;
        boolean error;
        do {
          error = false;
          try {
            unidad = al.sigToken();
            imprime(unidad);
          } catch (ECaracterInesperado e) {
            System.out.println("ERROR");
            error = true;
          }
        } while (error || unidad.clase() != ClaseLexica.EOF);
      }
    } else {
      System.out.println("El directorio no existe o no es accesible.");
    }
  }
}
