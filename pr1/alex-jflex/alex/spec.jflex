package alex;

%%
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode
%public

%{
  private ALexOperations ops;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z]| _)
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = {signo}?({digitoPositivo}{digito}*|0)
parteDecimal = ({digito}*{digitoPositivo}|0)
parte_exponencial = (e | E) {parteEntera}
separador = [ \t\r\b\n]
signo = (\+, \-)
comentario = #[^\n]* 
int = int
real = real
bool = bool
and = and
or = or
not = not
true = true
false = false
string = string
null = null
proc = proc
if = if
else = else
while = while
struct = struct
new = new
delete = delete
read = read
write = write
nl = nl
type = type
call = call
identificador = {letra}({letra}|{digito})*
numeroEntero = [\+\-]?{parteEntera}
numeroReal = {numeroEntero} ({parte_exponencial} | \.{parteDecimal} | \.{parteDecimal}{parte_exponencial})
cadena = "([^"])*" 
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
parentesisApertura = \(
parentesisCierre = \)
igual = \=
coma  = \,
llaveApertura = \{
llaveCierre = \}
menorQue = \<
menorIgualQue = \<\=
mayorQue = \>
mayorIgualQue = \>\=
comparacion = \=\=
distinto = \!\=
puntoComa = \;
punto = .;
porcentaje = %
inicioEvaluacion = \@
finDeclaracion = \&\&
ampersand = \&
exponente = ^
%%
{separador}               {}
{comentario}              {}

{llaveApertura}            {return ops.unidadLlaveAp();}
{llaveCierre}              {return ops.unidadLlaveCierre();}
{menorQue}                 {return ops.unidadMenor();}
{menorIgualQue}            {return ops.unidadMenorIgual();}
{mayorQue}                 {return ops.unidadMayor();}
{mayorIgualQue}            {return ops.unidadMayorIgual();}
{comparacion}              {return ops.unidadComp();}
{distinto}                 {return ops.unidadDistinto();}
{puntoComa}                {return ops.unidadPuntoYComa();}
{inicioEvaluacion}         {return ops.unidadIniEval();}
{finDeclaracion}           {return ops.unidadFinDeclaracion();}
{porcentaje}               {return ops.unidadPorcentaje();}
{ampersand}                {return ops.unidadAmpersand();}


{int}                    {return ops.unidadP_Int();}
{real}                   {return ops.unidadP_Real();}
{bool}                   {return ops.unidadP_Bool();}
{and}                    {return ops.unidadP_And();}
{or}                     {return ops.unidadP_Or();}
{not}                    {return ops.unidadP_Not();}
{true}                   {return ops.unidadP_True();}
{false}                  {return ops.unidadP_False();}
{string}                 {return ops.unidadP_String();}
{null}                   {return ops.unidadP_Null();}
{proc}                   {return ops.unidadP_Proc();}
{if}                     {return ops.unidadP_If();}
{else}                   {return ops.unidadP_Else();}
{while}                  {return ops.unidadP_While();}
{struct}                 {return ops.unidadP_Struct();}
{new}                    {return ops.unidadP_New();}
{delete}                 {return ops.unidadP_Delete();}
{read}                   {return ops.unidadP_Read();}
{write}                  {return ops.unidadP_Write();}
{nl}                     {return ops.unidadP_Nl();}
{type}                   {return ops.unidadP_Type();}
{call}                   {return ops.unidadP_Call();}

{identificador}           {return ops.unidadId();}
{numeroEntero}            {return ops.unidadEnt();}
{numeroReal}              {return ops.unidadReal();}
{cadena}                  {return ops.unidadCad();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{igual}                   {return ops.unidadIgual();} 
{coma}                    {return ops.unidadComa();}
{punto}                   {return ops.unidadPunto();}
{exponente}               {return ops.unidadExponente();}
[^]                       {ops.error();}  