package evaluacion;

import asint.Procesamiento;
import asint.SintaxisAbstractaTiny.Prog;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_descendente.ConstructorASTsTiny;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {
   public static void main(String[] args) throws Exception {
         Prog prog;
         if(args[0].equals("asc")) {
           Reader input = new InputStreamReader(new FileInputStream(args[1]));
  	   AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
	   c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTiny(alex);
           prog = (Prog)asint.parse().value;
         }
         else {
             ConstructorASTsTiny asint = new ConstructorASTsTiny(new FileReader(args[1]));
             asint.disable_tracing();
             prog = asint.analiza();
         }
         Evaluacion evaluacion = new Evaluacion();
  	 prog.procesa(evaluacion);
         System.out.println(evaluacion.leeResul());
    }
}   
