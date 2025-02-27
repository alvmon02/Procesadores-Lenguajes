package asint;

import alex.UnidadLexica;
import alex.AnalizadorLexicoTiny;
import alex.ClaseLexica;
import errors.GestionErroresTiny;
import java.io.IOException;
import java.io.Reader;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalizadorSintacticoTiny {
    private UnidadLexica anticipo; // token adelantado
    private AnalizadorLexicoTiny alex; // analizador léxico
    private GestionErroresTiny errores; // gestor de errores
    private Set<ClaseLexica> esperados; // clases léxicas esperadas

    public AnalizadorSintacticoTiny(Reader input) throws IOException {
        // se crea el gestor de errores
        errores = new GestionErroresTiny();
        // se crea el analizador léxico
        alex = new AnalizadorLexicoTiny(input, errores);
        esperados = EnumSet.noneOf(ClaseLexica.class);
        // Se lee el primer token adelantado
        sigToken();
    }

    public void analiza() {
        programa();
        empareja(ClaseLexica.EOF);
    }

    private void programa() {
        empareja(ClaseLexica.LLAVEAP);
        declaraciones();
        instrucciones();
        empareja(ClaseLexica.LLAVECIERRE);
    }

    private void declaraciones() {
        switch (anticipo.clase()) {
            case P_INT:
            case P_BOOL:
            case P_REAL:
                lista_declaraciones();
                empareja(ClaseLexica.FINDECLAR);
                break;
            default:
                esperados(ClaseLexica.P_INT, ClaseLexica.P_BOOL, ClaseLexica.P_REAL);
                break;
        }
    }

    private void lista_declaraciones() {
        declaracion();
        rlista_decs();
    }

    private void tipo() {
        switch (anticipo.clase()) {
            case P_INT:
                empareja(ClaseLexica.P_INT);
                break;
            case P_BOOL:
                empareja(ClaseLexica.P_BOOL);
                break;
            case P_REAL:
                empareja(ClaseLexica.P_REAL);
                break;
            default:
                esperados(ClaseLexica.P_INT, ClaseLexica.P_BOOL, ClaseLexica.P_REAL);
                error();
        }
    }

    private void declaracion() {
        switch (anticipo.clase()) {
            case P_INT:
            case P_BOOL:
            case P_REAL:
                tipo();
                empareja(ClaseLexica.IDEN);
            default:
                esperados(ClaseLexica.P_INT, ClaseLexica.P_BOOL, ClaseLexica.P_REAL);
                error();
        }
    }

    private void rlista_decs() {
        switch (anticipo.clase()) {
            case PCOMA:
                empareja(ClaseLexica.PCOMA);
                declaracion();
                rlista_decs();
                break;
            default:
                esperados(ClaseLexica.PCOMA);
                break;
        }
    }

    private void instrucciones() {
        switch (anticipo.clase()) {
            case INIEVAL:
                lista_instrucciones();
                break;
            default:
                esperados(ClaseLexica.INIEVAL);
                break;
        }
    }

    private void lista_instrucciones() {
    }

    private void E0() {
        E1();
        RE0();
    }

    private void RE0() {
        switch (anticipo.clase()) {
            case MAS:
            case MENOS:
                OP0();
                E1();
                RE0();
                break;
            default:
                esperados(ClaseLexica.MAS, ClaseLexica.MENOS);
                break;
        }
    }

    private void E1() {
        E2();
        RE1();
    }

    private void RE1() {
        switch (anticipo.clase()) {
            case POR:
            case DIV:
                OP1();
                E2();
                RE1();
                break;
            default:
                esperados(ClaseLexica.POR, ClaseLexica.DIV);
                break;
        }
    }

    private void E2() {
        switch (anticipo.clase()) {
            case ENT:
                empareja(ClaseLexica.ENT);
                break;
            case REAL:
                empareja(ClaseLexica.REAL);
                break;
            case IDEN:
                empareja(ClaseLexica.IDEN);
                break;
            case PAP:
                empareja(ClaseLexica.PAP);
                E0();
                empareja(ClaseLexica.PCIERRE);
                break;
            default:
                esperados(ClaseLexica.ENT, ClaseLexica.REAL, ClaseLexica.IDEN, ClaseLexica.PAP);
                error();
        }
    }

    private void OP0() {
        switch (anticipo.clase()) {
            case MAS:
                empareja(ClaseLexica.MAS);
                break;
            case MENOS:
                empareja(ClaseLexica.MENOS);
                break;
            default:
                esperados(ClaseLexica.MAS, ClaseLexica.MENOS);
                error();
        }
    }

    private void OP1() {
        switch (anticipo.clase()) {
            case POR:
                empareja(ClaseLexica.POR);
                break;
            case DIV:
                empareja(ClaseLexica.DIV);
                break;
            default:
                esperados(ClaseLexica.POR, ClaseLexica.DIV);
                error();
        }
    }

    private void esperados(ClaseLexica... esperadas) {
        for (ClaseLexica c : esperadas) {
            esperados.add(c);
        }
    }

    private void empareja(ClaseLexica claseEsperada) {
        if (anticipo.clase() == claseEsperada) {
            traza_emparejamiento(anticipo);
            sigToken();
        } else {
            esperados(claseEsperada);
            error();
        }
    }

    private void sigToken() {
        try {
            anticipo = alex.sigToken();
            esperados.clear();
        } catch (IOException e) {
            errores.errorFatal(e);
        }
    }

    private void error() {
        errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), esperados);
    }

    protected void traza_emparejamiento(UnidadLexica anticipo) {
    }
}
