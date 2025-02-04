package alex;

public class ALexOperations {
   public static class ECaracterInesperado extends RuntimeException {
      public ECaracterInesperado(String msg) {
         super(msg);
      }
   }

   private AnalizadorLexicoTiny alex;

   public ALexOperations(AnalizadorLexicoTiny alex) {
      this.alex = alex;
   }

   public UnidadLexica unidadId() {
      return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.IDEN,
            alex.lexema());
   }

   public UnidadLexica unidadEnt() {
      return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.ENT, alex.lexema());
   }

   public UnidadLexica unidadReal() {
      return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.REAL, alex.lexema());
   }

   public UnidadLexica unidadString() {
      return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.STRING, alex.lexema());
   }

   public UnidadLexica unidadPAp() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PAP);
   }

   public UnidadLexica unidadPCierre() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PCIERRE);
   }

   public UnidadLexica unidadLlaveAp() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.LLAVEAP);
   }

   public UnidadLexica unidadLlaveCierre() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.LLAVECIERRE);
   }

   public UnidadLexica unidadMenor() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.LT);
   }

   public UnidadLexica unidadMenorIgual() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.LEQ);
   }

   public UnidadLexica unidadMayor() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.GT);
   }

   public UnidadLexica unidadMayorIgual() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.GEQ);
   }

   public UnidadLexica unidadIgual() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.IGUAL);
   }

   public UnidadLexica unidadComp() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.COMP);
   }

   public UnidadLexica unidadDistinto() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DIST);
   }

   public UnidadLexica unidadPuntoYComa() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PCOMA);
   }

   public UnidadLexica unidadSuma() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MAS);
   }

   public UnidadLexica unidadResta() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MENOS);
   }

   public UnidadLexica unidadMul() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MUL);
   }

   public UnidadLexica unidadDiv() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DIV);
   }

   public UnidadLexica unidadIniEval() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.INIEVAL);
   }

   public UnidadLexica unidadFinDeclaracion() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.FINDECLAR);
   }

   public UnidadLexica unidadP_Int() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_INT);
   }

   public UnidadLexica unidadP_Real() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_REAL);

   }

   public UnidadLexica unidadP_Bool() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_BOOL);
   }

   public UnidadLexica unidadP_And() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_AND);
   }

   public UnidadLexica unidadP_or() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_OR);
   }

   public UnidadLexica unidadP_Not() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_NOT);
   }

   public UnidadLexica unidadP_True() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_TRUE);
   }

   public UnidadLexica unidadP_False() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_FALSE);
   }

   public UnidadLexica unidadP_String() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_STRING);
   }

   public UnidadLexica unidadP_Null() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_NULL);

   }

   public UnidadLexica unidadP_Proc() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_PROC);
   }

   public UnidadLexica unidadP_If() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_IF);
   }

   public UnidadLexica unidadP_Else() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_ELSE);
   }

   public UnidadLexica unidadP_While() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_WHILE);
   }

   public UnidadLexica unidadP_Struct() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_STRUCT);
   }

   public UnidadLexica unidadP_New() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_NEW);
   }

   public UnidadLexica unidadP_Delete() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_DELETE);
   }

   public UnidadLexica unidadP_Read() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_READ);
   }

   public UnidadLexica unidadP_Write() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_WRITE);
   }

   public UnidadLexica unidadP_Nl() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_NL);
   }

   public UnidadLexica unidadP_Type() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_TYPE);
   }

   public UnidadLexica unidadP_Call() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.P_CALL);
   }

   public UnidadLexica unidadPorcentaje() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PORCENTAJE);
   }

   public UnidadLexica unidadAnd() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.AND);
   }

   public UnidadLexica unidadComa() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.COMA);
   }

   public UnidadLexica unidadCorcheteAp() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.CORCHETEAP);
   }

   public UnidadLexica unidadCorcheteCierre() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.CORCHETECIERRE);
   }

   public UnidadLexica unidadPunto() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PUNTO);
   }

   public UnidadLexica unidadExponente() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.EXPONENTE);
   }

   public UnidadLexica unidadEof() {
      return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.EOF);
   }

   public void error() {
      throw new ECaracterInesperado(
            "***" + alex.fila() + "," + alex.columna() + ": Caracter inexperado: " + alex.lexema());
   }
}
