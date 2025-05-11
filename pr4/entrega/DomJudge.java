import asint.SintaxisAbstractaTiny.Prog;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_ascendente.GestionErroresTiny.*;
import c_ast_descendente.ParseException;
import c_ast_descendente.TokenMgrError;
import cod_maquina_p.GenCode;
import errores_procesamiento.ErrorProcesamiento;
import etiquetado.Etiquetado;
import pretipado.Pretipado;
import tipadoPost.Tipado;
import vinculacion.Vinculado;

import java.io.Reader;

import asig_espacio.AsigEspacio;

public class DomJudge {
	public static void main(String[] args) throws Exception {
		Reader input = new BISReader((System.in));
		
		Prog prog = construye_ast(input);
		
		if (prog != null)
			procesa_prog(prog);
	}

	private static Prog construye_ast(Reader input) throws Exception {
		
		int constructor_type = input.read();
		
		if (constructor_type == 97) { // Reconocer a
			try {
				AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
				c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTiny(alex);
				Prog prog = (Prog) asint.parse().value;
				
				return prog;
			} catch (ErrorLexico e) {
				System.out.println("ERROR_LEXICO");
			} catch (ErrorSintactico e) {
				System.out.println("ERROR_SINTACTICO");
			}
		} else if (constructor_type == 100) { // Reconocer d)

			try {
				c_ast_descendente.ConstructorASTsTiny asint = new c_ast_descendente.ConstructorASTsTiny(input);

				asint.disable_tracing();
				return asint.analiza();
			} catch (TokenMgrError e) {
				System.out.println("ERROR_LEXICO");
			} catch (ParseException e) {
				System.out.println("ERROR_SINTACTICO");
			}
		}
		else{
			System.err.println("Metodo de construccion no soportado:"+ (char) constructor_type);
		}
		return null;
	}

	private static void procesa_prog(Prog prog) {
		Vinculado vinculado = new Vinculado();
		prog.procesa(vinculado);
		if (vinculado.hayErrores()) {
			for (ErrorProcesamiento e : vinculado.errores()) {
				System.out.println(e.toStringJuez());
			}
		}
		else{
			Pretipado pretipado = new Pretipado();
			prog.procesa(pretipado);
			if (pretipado.hayErrores()) {
				for (ErrorProcesamiento e : pretipado.errores()) {
					System.out.println(e.toStringJuez());
				}
			}
			else {
				Tipado tipado = new Tipado();
				prog.procesa(tipado);
				if (tipado.hayErrores()) {
					for (ErrorProcesamiento e : tipado.errores()) {
						System.out.println(e.toStringJuez());
					}
				}
				else {
					AsigEspacio asigEspacio = new AsigEspacio();
					prog.procesa(asigEspacio);

					Etiquetado etiquetado = new Etiquetado();
					prog.procesa(etiquetado);

					GenCode genCode = new GenCode();	//Inicialización de MáquinaP dentro de GenCode
					prog.procesa(genCode);
				}
			}
		}

	}
}
