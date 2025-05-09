import asint.SintaxisAbstractaTiny.Prog;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_ascendente.GestionErroresTiny.*;
import c_ast_descendente.ParseException;
import c_ast_descendente.TokenMgrError;

import java.io.Reader;

public class DomJudge {
	public static void main(String[] args) throws Exception {

		Reader input = new BISReader((System.in));

		if (input.read() == 97) { // Reconocer a

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
		} else { // si no es a(en su defecto, es decir, d)

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
}
