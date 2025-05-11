package etiquetado;

import java.util.Stack;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class Etiquetado extends ProcesamientoDef {

    private int etqFinal = 0;
    private Stack<Dec_Proc> subPendientes = new Stack<>();

    private void procesarSubPendientes() {
        while (!subPendientes.empty()) {
            Dec_Proc sub = subPendientes.pop();
            sub.ponPrim(etqFinal);
            etqFinal++;
            sub.pforms().procesa(this);
            sub.prog().procesa(this);
            etqFinal += 2;
            sub.ponSig(etqFinal);
        }
    }

    private boolean esDesignador(Exp exp) {
        return claseDe(exp, Index.class) ||
                claseDe(exp, Acceso.class) ||
                claseDe(exp, Indireccion.class) ||
                claseDe(exp, Iden.class);
    }

    @Override
    public void procesa(Prog prog) {
        prog.ponPrim(etqFinal);
        prog.bloque().procesa(this);
        etqFinal++;
        procesarSubPendientes();
        prog.ponSig(etqFinal);
    }

    @Override
    public void procesa(Bloque bloque) {
        bloque.ponPrim(etqFinal);
        bloque.decs().procesa(this);
        bloque.intrs().procesa(this);
        bloque.ponSig(etqFinal);
    }

    @Override
    public void procesa(Si_Decs decs) {
        decs.ldecs().procesa(this);
    }

    @Override
    public void procesa(Mas_Decs decs) {
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
    }

    @Override
    public void procesa(Una_Dec dec) {
        dec.dec().procesa(this);
    }

    @Override
    public void procesa(Dec_Proc dec_Proc) {
        subPendientes.push(dec_Proc);
    }

    @Override
    public void procesa(Si_Intrs si_Intrs) {
        si_Intrs.intrs().procesa(this);
    }

    @Override
    public void procesa(Mas_Intrs mas_Intrs) {
        mas_Intrs.intrs().procesa(this);
        mas_Intrs.intr().procesa(this);
    }

    @Override
    public void procesa(Una_Intr una_Intr) {
        una_Intr.intr().procesa(this);
    }

    private void procesa_acc_exp(Exp exp) {
        exp.procesa(this);
        procesa_acc_valor(exp);
    }

    private void procesa_acc_valor(Exp exp) {
        if (esDesignador(exp)) {
            etqFinal++;
        }
    }

    @Override
    public void procesa(I_Eval i_Eval) {
        i_Eval.exp().procesa(this);
        etqFinal++;
    }

    @Override
    public void procesa(I_If i_If) {
        i_If.ponPrim(etqFinal);
        procesa_acc_exp(i_If.exp());
        etqFinal++;
        i_If.prog().procesa(this);

        if (claseDe(i_If.i_else(), Si_Else.class)) {
            etqFinal++;
        }
        i_If.ponSig(etqFinal);
        i_If.i_else().procesa(this);
        i_If.ponFin(etqFinal);
    }

    @Override
    public void procesa(I_While i_While) {
        i_While.ponPrim(etqFinal);
        procesa_acc_exp(i_While.exp());
        etqFinal++;
        i_While.prog().procesa(this);
        etqFinal++;
        i_While.ponSig(etqFinal);
    }

    @Override
    public void procesa(I_Read i_Read) {
        i_Read.exp().procesa(this);
        etqFinal += 2;
    }

    @Override
    public void procesa(I_Write i_Write) {
        procesa_acc_exp(i_Write.exp());
        etqFinal++;
    }

    @Override
    public void procesa(I_NL i_Nl) {
        etqFinal++;
    }

    @Override
    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
        etqFinal += 2;
    }

    @Override
    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
        etqFinal += 2;
    }

    @Override
    public void procesa(I_Call i_Call) {
        etqFinal++;
        procesa_paso_param(((Dec_Proc) i_Call.vinculo()).pforms(), i_Call.preals());
        etqFinal++;
        i_Call.ponSig(etqFinal);
    }

    private void procesa_paso_param(PForms pforms, PReals preals) {
        if (claseDe(pforms, Si_PForms.class)) {
            procesa_paso_param(pforms.pforms(), preals.preals());
        }
    }

    private void procesa_paso_param(LPForms pforms, LPReals preals) {
        if (claseDe(pforms, Mas_PForms.class)) {
            procesa_paso_param(pforms.pforms(), preals.preals());
        }
        procesa_paso_param(pforms.pform(), preals.exp());
    }

    private void procesa_paso_param(PForm pform, Exp exp) {
        etqFinal += 3;
        exp.procesa(this);
        if (claseDe(pform.ref(), Si_Ref.class) && claseDe(referenciar(exp.tipo()), T_Int.class)
                && claseDe(referenciar(pform.tipo()), T_Real.class)) {
            etqFinal += 2;
        }
        etqFinal++;
    }

    @Override
    public void procesa(I_Prog i_Prog) {
        i_Prog.prog().procesa(this);
    }

    @Override
    public void procesa(Si_Else si_Else) {
        si_Else.prog().procesa(this);
        si_Else.ponSig(etqFinal);
    }

    private void procesa_op_bin(Exp exp) {
        procesa_acc_exp(exp.opnd0());
        procesa_conversion(exp.opnd0(), exp.opnd1());
        procesa_acc_exp(exp.opnd1());
        procesa_conversion(exp.opnd1(), exp.opnd0());
        etqFinal++;
    }

    private void procesa_conversion(Exp opnd0, Exp opnd1) {
        if (claseDe(referenciar(opnd0.tipo()), T_Int.class) && claseDe(referenciar(opnd1.tipo()), T_Real.class)) {
            etqFinal++;
        }
    }

    @Override
    public void procesa(Asig exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (claseDe(referenciar(exp.opnd0().tipo()), T_Int.class)
                && claseDe(referenciar(exp.opnd1().tipo()), T_Real.class)) {
            procesa_acc_valor(exp.opnd1());
            etqFinal++;
        }
        etqFinal++;

    }

    @Override
    public void procesa(Comp exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(Dist exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(Menor exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(Mayor exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(MenorIgual exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(MayorIgual exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(Suma exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(Resta exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(And exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(Or exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(Mul exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(Div exp) {
        procesa_op_bin(exp);
    }

    @Override
    public void procesa(Porcentaje exp) {
        procesa_op_bin(exp);
    }

    private void procesa_op_un(Exp exp) {
        procesa_acc_exp(exp.opnd0());
        etqFinal++;
    }

    @Override
    public void procesa(Negativo exp) {
        procesa_op_un(exp);
    }

    @Override
    public void procesa(Negado exp) {
        procesa_op_un(exp);
    }

    @Override
    public void procesa(Index exp) {
        exp.opnd0().procesa(this);
        procesa_acc_exp(exp.opnd1());
        etqFinal += 3;
    }

    @Override
    public void procesa(Acceso exp) {
        exp.opnd0().procesa(this);
        etqFinal += 2;
    }

    @Override
    public void procesa(Indireccion exp) {
        procesa_acc_exp(exp.opnd0());
        etqFinal++;
    }

    @Override
    public void procesa(Lit_ent exp) {
        etqFinal++;
    }

    @Override
    public void procesa(True exp) {
        etqFinal++;
    }

    @Override
    public void procesa(False exp) {
        etqFinal++;
    }

    @Override
    public void procesa(Lit_real exp) {
        etqFinal++;
    }

    @Override
    public void procesa(Cadena exp) {
        etqFinal++;
    }

    @Override
    public void procesa(Iden exp) {
        exp.ponPrim(etqFinal);
        procesa_acc_iden(exp.vinculo());
        exp.ponSig(etqFinal);
    }

    private void procesa_acc_iden(Nodo nodo) {
        if (claseDe(nodo, Dec_Var.class)) {
            if (nodo.nivel() == 0) {
                etqFinal++;
            } else {
                etqFinal += 3;
            }
        } else { // Tiene que ser un parametro formal
            if (claseDe(((PForm) nodo).ref(), Si_Ref.class)) {
                etqFinal++;
            }
            etqFinal += 3;
        }
    }

    @Override
    public void procesa(Null exp) {
        etqFinal++;
    }

}
