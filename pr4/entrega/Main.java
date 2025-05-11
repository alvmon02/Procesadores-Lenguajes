
import asint.SintaxisAbstractaTiny.Asig;
import asint.SintaxisAbstractaTiny.Prog;
import c_ast_ascendente.AnalizadorLexicoTiny;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import asig_espacio.AsigEspacio;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

		if (args.length == 0) // Pruebas con archivos de tipo '.in' en un directorio aparte
			main_casos(args);
		else if (args.length == 1) { // Acepta un único argumento, un archivo a provesar
			main_archivo(args);
		} else
			System.err.println("ERROR: Númrero incorrecto de argumentos");
	}

	public static void main_casos(String[] args) throws FileNotFoundException, IOException, Exception {
		String directorioPath = "./casos"; // Puedes cambiarlo por el path deseado
		File directorio = new File(directorioPath);

		String[] archivos = directorio.list((dir, name) -> name.endsWith(".in"));

		PrintStream salidaOriginal = System.out;

		if (archivos != null) {
			for (String archivo : archivos) {
				String outputName = archivo.substring(0, archivo.lastIndexOf('.')) + ".txt";
				Reader input = new InputStreamReader(new FileInputStream(directorioPath + "/" + archivo));
				PrintStream output = new PrintStream(new FileOutputStream(directorioPath + "/" + outputName));
				System.setOut(output);
				ejecturarPrograma(input);
				output.close();
			}
		} else {
			System.err.println("El directorio no existe o no es accesible.");
		}
		System.setOut(salidaOriginal);
	}

	public static void main_archivo(String[] args) throws FileNotFoundException, IOException, Exception {
		Reader input = new BISReader(new FileInputStream(args[0]));
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

		GenCode genCode = new GenCode(new MaquinaP(input, 1000, 1000, 1000, 10));
		prog.procesa(genCode);
		genCode.ejecutar();
	}
}
