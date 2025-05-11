import asint.SintaxisAbstractaTiny.Prog;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_ascendente.GestionErroresTiny.*;
import c_ast_descendente.ParseException;
import c_ast_descendente.TokenMgrError;
import cod_maquina_p.GenCode;
import errores_procesamiento.ErrorProcesamiento;
import etiquetado.Etiquetado;
import maquinap.MaquinaP;
import pretipado.Pretipado;
import tipadoPost.Tipado;
import vinculacion.Vinculado;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import asig_espacio.AsigEspacio;

public class DomJudge {
	public static void main(String[] args) throws Exception {
		Reader input = new BISReader((System.in));

		ejecturarPrograma(input);
	}

	private static Prog construirAST(Reader input) throws FileNotFoundException, IOException, Exception {
		if (input.read() == 97) { // Reconocer a
			try {
				AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
				c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTinyDJ(alex);
				return (Prog) asint.parse().value;
			} catch (ErrorLexico e) {
				System.out.println("ERROR_LEXICO");
				return null;
			} catch (ErrorSintactico e) {
				System.out.println("ERROR_SINTACTICO");
				return null;
			}
		} else { // si no es a(en su defecto, es decir, d)
			try {
				c_ast_descendente.ConstructorASTsTiny asint = new c_ast_descendente.ConstructorASTsTinyDJ(
						input);
				asint.disable_tracing();
				return asint.analiza();
			} catch (TokenMgrError e) {
				System.out.println("ERROR_LEXICO");
				return null;
			} catch (ParseException e) {
				System.out.println("ERROR_SINTACTICO");
				return null;
			}
		}
	}

	private static void ejecturarPrograma(Reader input) throws FileNotFoundException, IOException, Exception {
		Prog prog = construirAST(input);

		if (prog == null) {
			return;
		}

		Vinculado vinculado = new Vinculado();
		prog.procesa(vinculado);
		if (vinculado.hayErrores()) {
			for (ErrorProcesamiento e : vinculado.errores()) {
				System.out.println(e.toStringJuez());
			}
			return;
		}

		Pretipado pretipado = new Pretipado();
		prog.procesa(pretipado);
		if (pretipado.hayErrores()) {
			for (ErrorProcesamiento e : pretipado.errores()) {
				System.out.println(e.toStringJuez());
			}
			return;
		}

		Tipado tipado = new Tipado();
		prog.procesa(tipado);
		if (tipado.hayErrores()) {
			for (ErrorProcesamiento e : tipado.errores()) {
				System.out.println(e.toString());
			}
			return;
		}

		AsigEspacio asigEspacio = new AsigEspacio();
		prog.procesa(asigEspacio);

		Etiquetado etiquetado = new Etiquetado();
		prog.procesa(etiquetado);
		MaquinaP m = new MaquinaP(input, 10000, 10000, 1000, 10);
		GenCode genCode = new GenCode(m);
		prog.procesa(genCode);
		m.muestraCodigo();
		genCode.ejecutar();
	}
}
