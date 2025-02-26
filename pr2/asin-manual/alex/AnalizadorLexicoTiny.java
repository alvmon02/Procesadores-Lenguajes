package alex;

import java.io.FileInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;

public class AnalizadorLexicoTiny {

  public static class ECaracterInesperado extends RuntimeException {
    public ECaracterInesperado(String msg) {
      super(msg);
    }
  };

  private Reader input;
  private StringBuffer lex;
  private int sigCar;
  private int filaInicio;
  private int columnaInicio;
  private int filaActual;
  private int columnaActual;
  private static String NL = System.getProperty("line.separator");

  private static enum Estado {
    INICIO, REC_ID, REC_MUL, REC_DIV, REC_LT, REC_LEQ, REC_GT, REC_GEQ, REC_ASIG, REC_COMP, DIST, REC_DIST, REC_PAP,
    REC_PYC, REC_MENOS, REC_SUMA, REC_0, REC_ENT, REC_EOF, DEC, EXP, EXP_SIG, REC_EXP, REC_0EXP, REC_DEC, DEC0,
    REC_PCI, REC_LLAP, REC_LLCI, REC_EVAL, FIN_DECLAR, REC_FIN_DECLAR, COM, REC_COM
  }

  private Estado estado;

  public AnalizadorLexicoTiny(Reader input) throws IOException {
    this.input = input;
    lex = new StringBuffer();
    sigCar = input.read();
    filaActual = 1;
    columnaActual = 1;
  }

  public UnidadLexica sigToken() throws IOException {
    estado = Estado.INICIO;
    filaInicio = filaActual;
    columnaInicio = columnaActual;
    lex.delete(0, lex.length());
    while (true) {
      switch (estado) {
        case INICIO:
          if (hayLetra())
            transita(Estado.REC_ID);
          else if (hayMul())
            transita(Estado.REC_MUL);
          else if (hayDiv())
            transita(Estado.REC_DIV);
          else if (hayMenor())
            transita(Estado.REC_LT);
          else if (hayMayor())
            transita(Estado.REC_GT);
          else if (hayIgual())
            transita(Estado.REC_ASIG);
          else if (hayDist())
            transita(Estado.DIST);
          else if (hayPAp())
            transita(Estado.REC_PAP);
          else if (hayPCierre())
            transita(Estado.REC_PCI);
          else if (hayLlaveAp())
            transita(Estado.REC_LLAP);
          else if (hayLlaveCierre())
            transita(Estado.REC_LLCI);
          else if (hayArroba())
            transita(Estado.REC_EVAL);
          else if (hayAnd())
            transita(Estado.FIN_DECLAR);
          else if (hayPyComa())
            transita(Estado.REC_PYC);
          else if (hayEOF())
            transita(Estado.REC_EOF);
          else if (hayCero())
            transita(Estado.REC_0);
          else if (hayDigitoPos())
            transita(Estado.REC_ENT);
          else if (haySuma())
            transita(Estado.REC_SUMA);
          else if (hayResta())
            transita(Estado.REC_MENOS);
          else if (hayAlmohadilla())
            transitaIgnorando(Estado.COM);
          else if (haySep())
            transitaIgnorando(Estado.INICIO);
          else
            error();
          break;
        case REC_ID:
          if (hayLetra() || hayDigito())
            transita(Estado.REC_ID);
          else
            return unidadId();
          break;
        case REC_MUL:
          return unidadMul();
        case REC_DIV:
          return unidadDiv();
        case REC_LT:
          if (hayIgual())
            transita(Estado.REC_LEQ);
          else
            return unidadLt();
          break;
        case REC_LEQ:
          return unidadLeq();
        case REC_GT:
          if (hayIgual())
            transita(Estado.REC_GEQ);
          else
            return unidadGt();
          break;
        case REC_GEQ:
          return unidadGeq();
        case REC_ASIG:
          if (hayIgual())
            transita(Estado.REC_COMP);
          else
            return unidadIgual();
          break;
        case REC_COMP:
          return unidadComp();
        case DIST:
          if (hayIgual())
            transita(Estado.REC_DIST);
          else
            error();
          break;
        case REC_DIST:
          return unidadDist();
        case REC_PAP:
          return unidadPAp();
        case REC_PCI:
          return unidadPCierre();
        case REC_LLAP:
          return unidadLlaveAP();
        case REC_LLCI:
          return unidadLlaveCierre();
        case REC_EVAL:
          return unidadInieVal();
        case FIN_DECLAR:
          if (hayAnd())
            transita(Estado.REC_FIN_DECLAR);
          else
            error();
          break;
        case REC_FIN_DECLAR:
          return unidadFindDeclar();
        case REC_PYC:
          return unidadPComa();
        case REC_EOF:
          return unidadEof();
        case REC_SUMA:
          if (hayDigitoPos())
            transita(Estado.REC_ENT);
          else if (hayCero())
            transita(Estado.REC_0);
          else
            return unidadMas();
          break;
        case REC_MENOS:
          if (hayDigitoPos())
            transita(Estado.REC_ENT);
          else if (hayCero())
            transita(Estado.REC_0);
          else
            return unidadMenos();
          break;
        case REC_0:
          if (hayPunto())
            transita(Estado.DEC);
          else if (hayExponente())
            transita(Estado.EXP);
          else
            return unidadEnt();
          break;
        case REC_ENT:
          if (hayDigito())
            transita(Estado.REC_ENT);
          else if (hayPunto())
            transita(Estado.DEC);
          else if (hayExponente())
            transita(Estado.EXP);
          else
            return unidadEnt();
          break;
        case DEC:
          if (hayDigito())
            transita(Estado.REC_DEC);
          else
            error();
          break;
        case REC_DEC:
          if (hayDigitoPos())
            transita(Estado.REC_DEC);
          else if (hayCero())
            transita(Estado.DEC0);
          else if (hayExponente())
            transita(Estado.EXP);
          else
            return unidadReal();
          break;
        case DEC0:
          if (hayCero())
            transita(Estado.DEC0);
          else if (hayDigitoPos())
            transita(Estado.REC_DEC);
          else
            error();
          break;
        case EXP:
          if (hayCero())
            transita(Estado.REC_0EXP);
          else if (hayDigitoPos())
            transita(Estado.REC_EXP);
          else if (haySigno())
            transita(Estado.EXP_SIG);
          else
            error();
          break;
        case EXP_SIG:
          if (hayCero())
            transita(Estado.REC_0EXP);
          else if (hayDigitoPos())
            transita(Estado.REC_EXP);
          else
            error();
          break;
        case REC_EXP:
          if (hayDigito())
            transita(Estado.REC_EXP);
          else
            return unidadReal();
          break;
        case REC_0EXP:
          return unidadReal();
        case COM:
          if (hayAlmohadilla())
            transita(Estado.REC_COM);
          else
            error();
          break;
        case REC_COM:
          if (hayNL())
            transitaIgnorando(Estado.INICIO);
          else if (hayEOF())
            transita(Estado.REC_EOF);
          else
            transitaIgnorando(Estado.REC_COM);
          break;
      }
    }
  }

  private void transita(Estado sig) throws IOException {
    lex.append((char) sigCar);
    sigCar();
    estado = sig;
  }

  private void transitaIgnorando(Estado sig) throws IOException {
    sigCar();
    filaInicio = filaActual;
    columnaInicio = columnaActual;
    estado = sig;
  }

  private void sigCar() throws IOException {
    sigCar = input.read();
    if (sigCar == NL.charAt(0))
      saltaFinDeLinea();
    if (sigCar == '\n') {
      filaActual++;
      columnaActual = 0;
    } else {
      columnaActual++;
    }
  }

  private void saltaFinDeLinea() throws IOException {
    for (int i = 1; i < NL.length(); i++) {
      sigCar = input.read();
      if (sigCar != NL.charAt(i))
        error();
    }
    sigCar = '\n';
  }

  private boolean hayLetra() {
    return sigCar >= 'a' && sigCar <= 'z' ||
        sigCar >= 'A' && sigCar <= 'Z' || sigCar == '_';
  }

  private boolean hayExponente() {
    return sigCar == 'e' || sigCar == 'E';
  }

  private boolean hayDigitoPos() {
    return sigCar >= '1' && sigCar <= '9';
  }

  private boolean hayCero() {
    return sigCar == '0';
  }

  private boolean hayDigito() {
    return hayDigitoPos() || hayCero();
  }

  private boolean haySuma() {
    return sigCar == '+';
  }

  private boolean hayResta() {
    return sigCar == '-';
  }

  private boolean haySigno() {
    return haySuma() || hayResta();
  }

  private boolean hayMul() {
    return sigCar == '*';
  }

  private boolean hayDiv() {
    return sigCar == '/';
  }

  private boolean hayPAp() {
    return sigCar == '(';
  }

  private boolean hayPCierre() {
    return sigCar == ')';
  }

  private boolean hayLlaveAp() {
    return sigCar == '{';
  }

  private boolean hayLlaveCierre() {
    return sigCar == '}';
  }

  private boolean hayMenor() {
    return sigCar == '<';
  }

  private boolean hayMayor() {
    return sigCar == '>';
  }

  private boolean hayIgual() {
    return sigCar == '=';
  }

  private boolean hayDist() {
    return sigCar == '!';
  }

  private boolean hayPyComa() {
    return sigCar == ';';
  }

  private boolean hayPunto() {
    return sigCar == '.';
  }

  private boolean hayAlmohadilla() {
    return sigCar == '#';
  }

  private boolean hayArroba() {
    return sigCar == '@';
  }

  private boolean hayAnd() {
    return sigCar == '&';
  }

  private boolean haySep() {
    return sigCar == ' ' || sigCar == '\t' || sigCar == '\n';
  }

  private boolean hayNL() {
    return sigCar == '\r' || sigCar == '\b' || sigCar == '\n';
  }

  private boolean hayEOF() {
    return sigCar == -1;
  }

  private UnidadLexica unidadId() {
    switch (lex.toString().toLowerCase()) {
      case "int":
        return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.P_INT);
      case "real":
        return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.P_REAL);
      case "bool":
        return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.P_BOOL);
      case "and":
        return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.P_AND);
      case "or":
        return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.P_OR);
      case "not":
        return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.P_NOT);
      case "true":
        return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.P_TRUE);
      case "false":
        return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.P_FALSE);
      default:
        return new UnidadLexicaMultivaluada(filaInicio, columnaInicio, ClaseLexica.IDEN, lex.toString());
    }
  }

  private UnidadLexica unidadEnt() {
    return new UnidadLexicaMultivaluada(filaInicio, columnaInicio, ClaseLexica.ENT, lex.toString());
  }

  private UnidadLexica unidadReal() {
    return new UnidadLexicaMultivaluada(filaInicio, columnaInicio, ClaseLexica.REAL, lex.toString());
  }

  private UnidadLexica unidadMas() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.MAS);
  }

  private UnidadLexica unidadMenos() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.MENOS);
  }

  private UnidadLexica unidadMul() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.MUL);
  }

  private UnidadLexica unidadDiv() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.DIV);
  }

  private UnidadLexica unidadLeq() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.LEQ);
  }

  private UnidadLexica unidadLt() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.LT);
  }

  private UnidadLexica unidadGt() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.GT);
  }

  private UnidadLexica unidadGeq() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.GEQ);
  }

  private UnidadLexica unidadPAp() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.PAP);
  }

  private UnidadLexica unidadPCierre() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.PCIERRE);
  }

  private UnidadLexica unidadLlaveAP() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.LLAVEAP);
  }

  private UnidadLexica unidadLlaveCierre() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.LLAVECIERRE);
  }

  private UnidadLexica unidadIgual() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.IGUAL);
  }

  private UnidadLexica unidadDist() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.DIST);
  }

  private UnidadLexica unidadComp() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.COMP);
  }

  private UnidadLexica unidadInieVal() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.INIEVAL);
  }

  private UnidadLexica unidadFindDeclar() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.FINDECLAR);
  }

  private UnidadLexica unidadPComa() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.PCOMA);
  }

  private UnidadLexica unidadEof() {
    return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.EOF);
  }

  private void error() {
    int curCar = sigCar;
    try {
      sigCar();
    } catch (IOException e) {
    }
    throw new ECaracterInesperado("(" + filaActual + ',' + columnaActual + "):Caracter inexperado:" + (char) curCar);
  }

  public static void main(String arg[]) throws IOException {
    Reader input = new InputStreamReader(new FileInputStream("input.txt"));
    AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
    UnidadLexica unidad;
    do {
      unidad = al.sigToken();
      System.out.println(unidad);
    } while (unidad.clase() != ClaseLexica.EOF);
  }
}