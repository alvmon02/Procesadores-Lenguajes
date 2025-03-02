package alex;

import asint.ClaseLexica;

public class ALexOperations {

   private AnalizadorLexicoTiny alex;

   public ALexOperations(AnalizadorLexicoTiny alex) {
      this.alex = alex;
   }

   public UnidadLexica unidadId() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IDEN, alex.lexema());
   }

   public UnidadLexica unidadEnt() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ENT, alex.lexema());
   }

   public UnidadLexica unidadReal() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.REAL, alex.lexema());
   }

   public UnidadLexica unidadString() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.STRING, alex.lexema());
   }

   public UnidadLexica unidadPAp() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PAP,"(");
   }

   public UnidadLexica unidadPCierre() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PCIERRE,")");
   }

   public UnidadLexica unidadLlaveAp() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LLAVEAP,"{");
   }

   public UnidadLexica unidadLlaveCierre() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LLAVECIERRE,"}");
   }

   public UnidadLexica unidadMenor() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LT,"<");
   }

   public UnidadLexica unidadMenorIgual() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LEQ,"<=");
   }

   public UnidadLexica unidadMayor() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.GT,">");
   }

   public UnidadLexica unidadMayorIgual() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.GEQ,">=");
   }

   public UnidadLexica unidadComp() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMP,"==");
   }

   public UnidadLexica unidadDistinto() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIST,"!=");
   }

   public UnidadLexica unidadIgual() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IGUAL,"=");
   }

   public UnidadLexica unidadPuntoYComa() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PCOMA,";");
   }

   public UnidadLexica unidadSuma() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAS,"+");
   }

   public UnidadLexica unidadResta() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOS,"-");
   }

   public UnidadLexica unidadMul() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MUL,"+");
   }

   public UnidadLexica unidadDiv() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIV,"/");
   }

   public UnidadLexica unidadIniEval() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.INIEVAL,"@");
   }

   public UnidadLexica unidadFinDeclaracion() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FINDECLAR,"&&");
   }

   public UnidadLexica unidadP_Int() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_INT,"<int>");
   }

   public UnidadLexica unidadP_Real() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_REAL,"<real>");

   }

   public UnidadLexica unidadP_Bool() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_BOOL,"<bool>");
   }

   public UnidadLexica unidadP_And() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_AND,"<and>");
   }

   public UnidadLexica unidadP_Or() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_OR,"<or>");
   }

   public UnidadLexica unidadP_Not() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_NOT,"<not>");
   }

   public UnidadLexica unidadP_True() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_TRUE,"<true>");
   }

   public UnidadLexica unidadP_False() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_FALSE,"<false>");
   }

   public UnidadLexica unidadP_String() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_STRING,"<string>");
   }

   public UnidadLexica unidadP_Null() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_NULL,"<null>");
   }

   public UnidadLexica unidadP_Proc() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_PROC,"<proc>");
   }

   public UnidadLexica unidadP_If() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_IF,"<if>");
   }

   public UnidadLexica unidadP_Else() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_ELSE,"<else>");
   }

   public UnidadLexica unidadP_While() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_WHILE,"<while>");
   }

   public UnidadLexica unidadP_Struct() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_STRUCT,"<struct>");
   }

   public UnidadLexica unidadP_New() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_NEW,"<new>");
   }

   public UnidadLexica unidadP_Delete() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_DELETE,"<delete>");
   }

   public UnidadLexica unidadP_Read() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_READ,"<read>");
   }

   public UnidadLexica unidadP_Write() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_WRITE,"<write>");
   }

   public UnidadLexica unidadP_Nl() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_NL,"<nl>");
   }

   public UnidadLexica unidadP_Type() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_TYPE,"<type>");
   }

   public UnidadLexica unidadP_Call() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.P_CALL,"<call>");
   }

   public UnidadLexica unidadPorcentaje() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PORCENTAJE,"%");
   }

   public UnidadLexica unidadAnd() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AND,"&");
   }

   public UnidadLexica unidadComa() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMA,",");
   }

   public UnidadLexica unidadCorcheteAp() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CORCHETEAP,"[");
   }

   public UnidadLexica unidadCorcheteCierre() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CORCHETECIERRE,"]");
   }

   public UnidadLexica unidadPunto() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTO,".");
   }

   public UnidadLexica unidadExponente() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EXPONENTE,"^");
   }

   public UnidadLexica unidadEof() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.O_EOF,"<EOF>");
   }

}
