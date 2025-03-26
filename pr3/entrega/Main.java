import asint.SintaxisAbstractaTiny.Prog;
import c_ast_ascendente.AnalizadorLexicoTiny;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args[0].equals("asc")) {
            Reader input = new InputStreamReader(new FileInputStream(args[1]));
            AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
            c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTinyDJ(alex);
            System.out.println("CONSTRUCCION AST ASCENDENTE");
            Prog prog = (Prog) asint.debug_parse().value;
            System.out.println("IMPRESION RECURSIVA");
            new pro_recursiva.Impresion().imprime(prog);
            System.out.println("IMPRESION INTERPRETE");
            prog.imprime();
            System.out.println("IMPRESION VISITANTE");
            prog.procesa(new visitante.Impresion());
        } else {
            c_ast_descendente.ConstructorASTsTiny asint = new c_ast_descendente.ConstructorASTsTinyDJ(
                    new FileReader(args[1]));
            System.out.println("CONSTRUCCION AST DESCENDENTE");
            Prog prog = asint.analiza();
            System.out.println("IMPRESION RECURSIVA");
            new pro_recursiva.Impresion().imprime(prog);
            System.out.println("IMPRESION INTERPRETE");
            prog.imprime();
            System.out.println("IMPRESION VISITANTE");
            prog.procesa(new visitante.Impresion());
        }
    }
}
