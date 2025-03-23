package c_ast_ascendente;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import asint.SintaxisAbstractaTiny.Prog;

public class Main {
	public static void main(String[] args) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(args[0]));
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		ConstructorASTTiny asint = new ConstructorASTTiny(alex);
		// asint.setScanner(alex);
		Prog prog = (Prog) asint.parse().value;
		prog.imprime();
		// System.out.println(asint.parse().value);
	}
}
