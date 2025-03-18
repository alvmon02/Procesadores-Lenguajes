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
                break;
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
        instruccion();
        rlista_instr();
    }

    private void rlista_instr() {
        switch (anticipo.clase()) {
            case PCOMA:
                empareja(ClaseLexica.PCOMA);
                instruccion();
                rlista_instr();
                break;
            default:
                esperados(ClaseLexica.PCOMA);
                break;
        }
    }

    private void instruccion() {
        switch (anticipo.clase()) {
            case INIEVAL:
                empareja(ClaseLexica.INIEVAL);
                E0();
                break;
            default:
                esperados(ClaseLexica.INIEVAL);
                error();
        }
    }

    private void E0() {
        // System.out.println("Pasando por E0");
        E1();
        FE0();
    }

    private void FE0() {
        // System.out.println("Pasando por RE0");
        switch (anticipo.clase()) {
            case IGUAL:
                empareja(ClaseLexica.IGUAL);
                E0();
                break;
            default:
                esperados(ClaseLexica.IGUAL);
                break;
        }
    }

    private void E1() {
        // System.out.println("Pasando por E1");
        E2();
        RE1();
    }

    private void RE1() {
        // System.out.println("Pasando por RE1");
        switch (anticipo.clase()) {
            case LT:
            case LEQ:
            case GT:
            case GEQ:
            case COMP:
            case DIST:
                OP1();
                E2();
                RE1();
                break;
            default:
                esperados(ClaseLexica.LT, ClaseLexica.LEQ, ClaseLexica.GT, ClaseLexica.GEQ, ClaseLexica.COMP,
                        ClaseLexica.DIST);
                break;
        }
    }

    private void OP1() {
        // System.out.println("Pasando por OP1");
        switch (anticipo.clase()) {
            case LT:
                empareja(ClaseLexica.LT);
                break;
            case LEQ:
                empareja(ClaseLexica.LEQ);
                break;
            case GT:
                empareja(ClaseLexica.GT);
                break;
            case GEQ:
                empareja(ClaseLexica.GEQ);
                break;
            case COMP:
                empareja(ClaseLexica.COMP);
                break;
            case DIST:
                empareja(ClaseLexica.DIST);
                break;
            default:
                esperados(ClaseLexica.LT, ClaseLexica.LEQ, ClaseLexica.GT, ClaseLexica.GEQ, ClaseLexica.COMP,
                        ClaseLexica.DIST);
                error();
        }
    }

    private void E2() {
        // System.out.println("Pasando por E2");
        E3();
        FE2(); // E2' Factorizado
        RE2(); // E2'' Recursivo
    }

    private void FE2() {
        // System.out.println("Pasando por FE2");
        switch (anticipo.clase()) {
            case MENOS:
                empareja(ClaseLexica.MENOS);
                E3();
                break;
            default:
                esperados(ClaseLexica.MENOS);
                break;
        }
    }

    private void RE2() {
        // System.out.println("Pasando por RE2");
        switch (anticipo.clase()) {
            case MAS:
                empareja(ClaseLexica.MAS);
                E3();
                RE2();
                break;
            default:
                esperados(ClaseLexica.MAS);
                break;
        }
    }

    private void E3() {
        // System.out.println("Pasando por E3");
        E4();
        RE3();
    }

    private void RE3() {
        // System.out.println("Pasando por RE3");
        switch (anticipo.clase()) {
            case P_AND:
                empareja(ClaseLexica.P_AND);
                E4();
                RE3();
                break;
            case P_OR:
                empareja(ClaseLexica.P_OR);
                E4();
                break;

            default:
                esperados(ClaseLexica.P_OR, ClaseLexica.P_AND);
                break;
        }
    }

    private void E4() {
        // System.out.println("Pasando por E4");
        E5();
        RE4();
    }

    private void RE4() {
        // System.out.println("Pasando por RE4");
        switch (anticipo.clase()) {
            case MUL:
            case DIV:
                OP4();
                E5();
                RE4();
                break;
            default:
                esperados(ClaseLexica.MUL, ClaseLexica.DIV);
                break;
        }
    }

    private void OP4() {
        // System.out.println("Pasando por OP4");
        switch (anticipo.clase()) {
            case MUL:
                empareja(ClaseLexica.MUL);
                break;
            case DIV:
                empareja(ClaseLexica.DIV);
                break;
            default:
                esperados(ClaseLexica.MUL, ClaseLexica.DIV);
                error();
        }
    }

    private void E5() {
        // System.out.println("Pasando por E5");
        switch (anticipo.clase()) {
            case MENOS:
            case P_NOT:
                OP5();
                E5();
                break;
            case PAP:
            case ENT:
            case P_FALSE:
            case P_TRUE:
            case REAL:
            case IDEN:
                E6();
                break;
            default:
                esperados(ClaseLexica.MENOS, ClaseLexica.P_NOT, ClaseLexica.PAP, ClaseLexica.ENT, ClaseLexica.P_FALSE,
                        ClaseLexica.P_TRUE, ClaseLexica.REAL, ClaseLexica.IDEN);
                error();
        }
    }

    private void OP5() {
        // System.out.println("Pasando por OP5");
        switch (anticipo.clase()) {
            case MENOS:
                empareja(ClaseLexica.MENOS);
                break;
            case P_NOT:
                empareja(ClaseLexica.P_NOT);
                break;
            default:
                esperados(ClaseLexica.MENOS, ClaseLexica.P_NOT);
                error();
        }
    }

    private void E6() {
        // System.out.println("Pasando por E6");
        switch (anticipo.clase()) {
            case IDEN:
                empareja(ClaseLexica.IDEN);
                break;
            case ENT:
                empareja(ClaseLexica.ENT);
                break;
            case REAL:
                empareja(ClaseLexica.REAL);
                break;
            case P_FALSE:
                empareja(ClaseLexica.P_FALSE);
                break;
            case P_TRUE:
                empareja(ClaseLexica.P_TRUE);
                break;
            case PAP:
                empareja(ClaseLexica.PAP);
                E0();
                empareja(ClaseLexica.PCIERRE);
                break;
            default:
                esperados(ClaseLexica.PAP, ClaseLexica.ENT, ClaseLexica.P_FALSE,
                        ClaseLexica.P_TRUE, ClaseLexica.REAL, ClaseLexica.IDEN);
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
            // System.out.printf("Consumiendo %s\n", claseEsperada.getImage());
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
