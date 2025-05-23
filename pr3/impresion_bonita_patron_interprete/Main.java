import asint.SintaxisAbstractaEval.Prog;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_descendente.ConstructorASTsTiny;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args[0].equals("asc")) {
            Reader input = new InputStreamReader(new FileInputStream(args[1]));
            AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
            c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTiny(alex);
            Prog prog = (Prog) asint.parse().value;
            prog.imprime();
        } else {
            ConstructorASTsTiny asint = new ConstructorASTsTiny(new FileReader(args[1]));
            asint.disable_tracing();
            asint.analiza().imprime();
        }
    }
}
