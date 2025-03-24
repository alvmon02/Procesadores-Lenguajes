package impresion;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class Impresion extends ProcesamientoDef {
    public void imprimeOpnd(Exp opnd, int np) {
        if (opnd.prioridad() < np) {
            System.out.print("(");
        }
        opnd.procesa(this);
        if (opnd.prioridad() < np) {
            System.out.print(")");
        }
    }

    public void imprimeStringLocalizado(String string, int fila, int columna) {
        System.out.println(string + "$f:" + fila + ",c:" + columna + "$");
    }

    public void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1, int fila, int columna) {
        imprimeOpnd(opnd0, np0);
        imprimeStringLocalizado(op, fila, columna);
        imprimeOpnd(opnd1, np1);
    }

    public void procesa(Prog prog) {
        System.out.println("{");
        prog.decs().procesa(this);
        prog.intrs().procesa(this);
        System.out.println("}");
    }

    public void procesa(Si_Decs decs) {
        decs.ldecs().procesa(this);
        System.out.println("&&");
    }

    public void procesa(Mas_Decs ldecs) {
        ldecs.ldecs().procesa(this);
        System.out.println(";");
        ldecs.dec().procesa(this);
    }

    public void procesa(Una_Dec ldecs) {
        ldecs.dec().procesa(this);
    }

    public void procesa(Dec_Var dec) {
        dec.tipo().procesa(this);
        imprimeStringLocalizado(dec.id(), dec.leeFila(), dec.leeCol());
    }

    public void procesa(Dec_Tipo dec) {
        System.out.println("<type>");
        dec.tipo().procesa(this);
        imprimeStringLocalizado(dec.id(), dec.leeFila(), dec.leeCol());
    }

    public void procesa(Dec_Proc dec) {
        System.out.println("<proc>");
        imprimeStringLocalizado(dec.id(), dec.leeFila(), dec.leeCol());
        System.out.println("(");
        dec.pforms().procesa(this);
        System.out.println(")");
        dec.prog().procesa(this);
    }

    public void procesa(Si_PForms pforms) {
        pforms.pforms().procesa(this);
    }

    public void procesa(Mas_PForms pforms) {
        pforms.pforms().procesa(this);
        System.out.println(",");
        pforms.pform().procesa(this);
    }

    public void procesa(Una_PForm pforms) {
        pforms.pform().procesa(this);
    }

    public void procesa(PForm pform) {
        pform.tipo().procesa(this);
        pform.ref().procesa(this);
        imprimeStringLocalizado(pform.id(), pform.leeFila(), pform.leeCol());
    }

    public void procesa(Si_Ref ref) {
        System.out.println("&");
    }

    public void procesa(T_Iden tipo) {
        imprimeStringLocalizado(tipo.id(), tipo.leeFila(), tipo.leeCol());
    }

    public void procesa(T_String tipo) {
        System.out.println("<string>");
    }

    public void procesa(T_Int tipo) {
        System.out.println("<int>");
    }

    public void procesa(T_Bool tipo) {
        System.out.println("<bool>");
    }

    public void procesa(T_Real tipo) {
        System.out.println("<real>");
    }

    public void procesa(T_Array tipo) {
        tipo.tipo().procesa(this);
        imprimeStringLocalizado("[\n" + tipo.ent() + "\n]", tipo.leeFila(), tipo.leeCol());
    }

    public void procesa(T_Puntero tipo) {
        System.out.println("^");
        tipo.tipo().procesa(this);
    }

    public void procesa(T_Struct tipo) {
        System.out.println("<struct>");
        System.out.println("{");
        tipo.camposS().procesa(this);
        System.out.println("}");
    }

    public void procesa(Mas_Cmp_S camposS) {
        camposS.camposS().procesa(this);
        System.out.println(",");
        camposS.campoS().procesa(this);
    }

    public void procesa(Un_Cmp_S camposS) {
        camposS.campoS().procesa(this);
    }

    public void procesa(CampoS campoS) {
        campoS.tipo().procesa(this);
        imprimeStringLocalizado(campoS.id(), campoS.leeFila(), campoS.leeCol());
    }

    public void procesa(Si_Intrs intrs) {
        intrs.intrs().procesa(this);
    }

    public void procesa(Mas_Intrs intrs) {
        intrs.intrs().procesa(this);
        System.out.println(";");
        intrs.intr().procesa(this);
    }

    public void procesa(Una_Intr intrs) {
        intrs.intr().procesa(this);
    }

    public void procesa(I_Eval intr) {
        System.out.println("@");
        intr.exp().procesa(this);
    }

    public void procesa(I_If intr) {
        System.out.println("<if>");
        intr.exp().procesa(this);
        intr.prog().procesa(this);
        intr.i_else().procesa(this);
    }

    public void procesa(I_While intr) {
        System.out.println("<while>");
        intr.exp().procesa(this);
        intr.prog().procesa(this);
    }

    public void procesa(I_Read intr) {
        System.out.println("<read>");
        intr.exp().procesa(this);
    }

    public void procesa(I_Write intr) {
        System.out.println("<write>");
        intr.exp().procesa(this);
    }

    public void procesa(I_NL intr) {
        System.out.println("<nl>");
    }

    public void procesa(I_New intr) {
        System.out.println("<new>");
        intr.exp().procesa(this);
    }

    public void procesa(I_Delete intr) {
        System.out.println("<delete>");
        intr.exp().procesa(this);
    }

    public void procesa(I_Call intr) {
        System.out.println("<call>");
        imprimeStringLocalizado(intr.id(), intr.leeFila(), intr.leeCol());
        System.out.println("(");
        intr.preals().procesa(this);
        System.out.println(")");
    }

    public void procesa(I_Prog intr) {
        intr.prog().procesa(this);
    }

    public void procesa(Si_Else i_else) {
        System.out.println("<else>");
        i_else.prog().procesa(this);
    }

    public void procesa(Si_PReals preals) {
        preals.preals().procesa(this);
    }

    public void procesa(Mas_PReals preals) {
        preals.preals().procesa(this);
        System.out.println(",");
        preals.exp().procesa(this);
    }

    public void procesa(Un_PReal preals) {
        preals.exp().procesa(this);
    }

    public void procesa(Asig exp) {
        imprimeExpBin(exp.opnd0(), "=", exp.opnd1(), 1, 0, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Comp exp) {
        imprimeExpBin(exp.opnd0(), "==", exp.opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Dist exp) {
        imprimeExpBin(exp.opnd0(), "!=", exp.opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Menor exp) {
        imprimeExpBin(exp.opnd0(), "<", exp.opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Mayor exp) {
        imprimeExpBin(exp.opnd0(), ">", exp.opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    }

    public void procesa(MenorIgual exp) {
        imprimeExpBin(exp.opnd0(), "<=", exp.opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    }

    public void procesa(MayorIgual exp) {
        imprimeExpBin(exp.opnd0(), ">=", exp.opnd1(), 1, 2, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Suma exp) {
        imprimeExpBin(exp.opnd0(), "+", exp.opnd1(), 2, 3, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Resta exp) {
        imprimeExpBin(exp.opnd0(), "-", exp.opnd1(), 3, 3, exp.leeFila(), exp.leeCol());
    }

    public void procesa(And exp) {
        imprimeExpBin(exp.opnd0(), "<and>", exp.opnd1(), 4, 3, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Or exp) {
        imprimeExpBin(exp.opnd0(), "<or>", exp.opnd1(), 4, 4, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Mul exp) {
        imprimeExpBin(exp.opnd0(), "*", exp.opnd1(), 4, 5, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Div exp) {
        imprimeExpBin(exp.opnd0(), "/", exp.opnd1(), 4, 5, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Porcentaje exp) {
        imprimeExpBin(exp.opnd0(), "%", exp.opnd1(), 4, 5, exp.leeFila(), exp.leeCol());
    }

    public void procesa(Negativo exp) {
        imprimeStringLocalizado("-", exp.leeFila(), exp.leeCol());
        imprimeOpnd(exp.opnd0(), 5);
    }

    public void procesa(Negado exp) {
        imprimeStringLocalizado("<not>", exp.leeFila(), exp.leeCol());
        imprimeOpnd(exp.opnd0(), 5);
    }

    public void procesa(Index exp) {
        exp.opnd0().procesa(this);
        imprimeStringLocalizado("[", exp.leeFila(), exp.leeCol());
        exp.opnd1().procesa(this);
        System.out.println("]");
    }

    public void procesa(Acceso exp) {
        exp.opnd0().procesa(this);
        System.out.println(".");
        imprimeStringLocalizado(exp.id(), exp.leeFila(), exp.leeCol());
    }

    public void procesa(Indireccion exp) {
        exp.opnd0().procesa(this);
        imprimeStringLocalizado("^", exp.leeFila(), exp.leeCol());
    }

    public void procesa(Lit_ent exp) {
        imprimeStringLocalizado(exp.num(), exp.leeFila(), exp.leeCol());
    }

    public void procesa(Lit_real exp) {
        imprimeStringLocalizado(exp.num(), exp.leeFila(), exp.leeCol());
    }

    public void procesa(True exp) {
        imprimeStringLocalizado("<true>", exp.leeFila(), exp.leeCol());
    }

    public void procesa(False exp) {
        imprimeStringLocalizado("<false>", exp.leeFila(), exp.leeCol());
    }

    public void procesa(Cadena exp) {
        imprimeStringLocalizado(exp.string(), exp.leeFila(), exp.leeCol());
    }

    public void procesa(Iden exp) {
        imprimeStringLocalizado(exp.id(), exp.leeFila(), exp.leeCol());
    }

    public void procesa(Null exp) {
        imprimeStringLocalizado("<null>", exp.leeFila(), exp.leeCol());
    }

}
