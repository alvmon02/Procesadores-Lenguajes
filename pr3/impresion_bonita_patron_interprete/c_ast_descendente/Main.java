package c_ast_descendente;
import java.io.FileReader;
public class Main{
   public static void main(String[] args) throws Exception {
      ConstructorASTsTiny asint = new ConstructorASTsTiny(new FileReader("D:/Clase/CUARTO/PL/Procesadores-Lenguajes/pr3/constructor_ats_cup/casos/sample4a.in"));
      asint.disable_tracing();
      // asint.analiza();
      asint.analiza().imprime();
   }
}