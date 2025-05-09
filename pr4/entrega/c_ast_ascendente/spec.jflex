package c_ast_ascendente;

import c_ast_ascendente.GestionErroresTiny;

%%
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode
%public
%cup

%{
  private ALexOperations ops;
  private GestionErroresTiny errores; 
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores=errores;
  }

%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}


/* Definiciones auxiliares */
letra  = ([A-Z]|[a-z]|_)
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
signo = [\+\-]
parteEntera = {signo}?({digitoPositivo}{digito}*|0)
parteDecimal = ({digito}*{digitoPositivo}|0)
parteExponencial = ([eE]{parteEntera})


/* Cadenas ignorables */
separador = [ \t\r\b\n]
comentario = ##[^\n]*


/* Clases lexicas */
identificador = {letra}({letra}|{digito})*
numeroEntero = {parteEntera}
numeroReal = {parteEntera}({parteExponencial}|\.{parteDecimal}|\.{parteDecimal}{parteExponencial})
string = \"([^\"])*\"
parentesisApertura = \(
parentesisCierre = \)
llaveApertura = \{
llaveCierre = \}
operadorMenor = \<
operadorMenorIgual = \<\=
operadorMayor = \>
operadorMayorIgual = \>\=
operadorComparacion = \=\=
operadorDistinto = \!\=
operadorAsignacion = \=
puntoYComa = \; 
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
evaluacion = \@
finDeclaracion = \&\&
porcentaje = \%
and = \&
coma = \,
corcheteApertura = \[
corcheteCierre = \]
punto = \.
exponente = \^
/* Palabras reservadas */
p_int = [iI][nN][tT]
p_real = [rR][eE][aA][lL]
p_bool = [bB][oO][oO][lL]
p_and = [aA][nN][dD]
p_or = [oO][rR]
p_not = [nN][oO][tT]
p_true = [tT][rR][uU][eE]
p_false = [fF][aA][lL][sS][eE]
p_string = [sS][tT][rR][iI][nN][gG]
p_null = [nN][uU][lL][lL]
p_proc = [pP][rR][oO][cC]
p_if = [iI][fF]
p_else = [eE][lL][sS][eE]
p_while = [wW][hH][iI][lL][eE]
p_struct = [sS][tT][rR][uU][cC][tT]
p_new = [nN][eE][wW]
p_delete = [dD][eE][lL][eE][tT][eE]
p_read = [rR][eE][aA][dD]
p_write = [wW][rR][iI][tT][eE]
p_nl = [nN][lL]
p_type = [tT][yY][pP][eE]
p_call = [cC][aA][lL][lL]
eof = \$
%%
{separador}               {}
{comentario}              {}
{eof}                    {return ops.unidadEof();}
{p_int}                   {return ops.unidadP_Int();}
{p_real}                  {return ops.unidadP_Real();}
{p_bool}                  {return ops.unidadP_Bool();}
{p_and}                   {return ops.unidadP_And();}
{p_or}                    {return ops.unidadP_Or();}
{p_not}                   {return ops.unidadP_Not();}
{p_true}                  {return ops.unidadP_True();}
{p_false}                 {return ops.unidadP_False();}
{p_string}                {return ops.unidadP_String();}
{p_null}                  {return ops.unidadP_Null();}
{p_proc}                  {return ops.unidadP_Proc();}
{p_if}                    {return ops.unidadP_If();}
{p_else}                  {return ops.unidadP_Else();}
{p_while}                 {return ops.unidadP_While();}
{p_struct}                {return ops.unidadP_Struct();}
{p_new}                   {return ops.unidadP_New();}
{p_delete}                {return ops.unidadP_Delete();}
{p_read}                  {return ops.unidadP_Read();}
{p_write}                 {return ops.unidadP_Write();}
{p_nl}                    {return ops.unidadP_Nl();}
{p_type}                  {return ops.unidadP_Type();}
{p_call}                  {return ops.unidadP_Call();}
{identificador}           {return ops.unidadId();}
{numeroEntero}            {return ops.unidadEnt();}
{numeroReal}              {return ops.unidadReal();}
{string}                  {return ops.unidadString();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();}
{llaveApertura}           {return ops.unidadLlaveAp();}
{llaveCierre}             {return ops.unidadLlaveCierre();}
{operadorMenor}           {return ops.unidadMenor();}
{operadorMenorIgual}      {return ops.unidadMenorIgual();}
{operadorMayor}           {return ops.unidadMayor();}
{operadorMayorIgual}      {return ops.unidadMayorIgual();}
{operadorComparacion}     {return ops.unidadComp();}
{operadorDistinto}        {return ops.unidadDistinto();}
{operadorAsignacion}      {return ops.unidadIgual();}
{puntoYComa}              {return ops.unidadPuntoYComa();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{evaluacion}              {return ops.unidadIniEval();}
{finDeclaracion}          {return ops.unidadFinDeclaracion();}
{porcentaje}              {return ops.unidadPorcentaje();}
{and}                     {return ops.unidadAnd();}
{coma}                    {return ops.unidadComa();}
{corcheteApertura}        {return ops.unidadCorcheteAp();}
{corcheteCierre}          {return ops.unidadCorcheteCierre();}
{punto}                   {return ops.unidadPunto();}
{exponente}               {return ops.unidadExponente();}
[^]                       {errores.errorLexico(fila(),columna(),lexema());}  
