import asint.AnalizadorSintacticoTiny;
import asint.AnalizadorSintacticoEvalDJ;
import errors.GestionErroresTiny.ErrorLexico;
import errors.GestionErroresTiny.ErrorSintactico;
import java.io.InputStreamReader;
public class DomJudge{
   public static void main(String[] args) throws Exception {
     try{  
      AnalizadorSintacticoTiny asint = new AnalizadorSintacticoEvalDJ(new InputStreamReader(System.in));
      asint.analiza();
     }
     catch(ErrorSintactico e) {
        System.out.println("ERROR_SINTACTICO"); 
     }
     catch(ErrorLexico e) {
        System.out.println("ERROR_LEXICO"); 
     }
   }
}