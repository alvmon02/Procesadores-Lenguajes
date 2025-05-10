package etiquetado;

import java.util.Stack;

import javax.lang.model.element.VariableElement;

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
                claseDe(exp, VariableElement.class);
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
        decs.ponPrim(etqFinal);
        decs.ldecs().procesa(this);
        decs.ponSig(etqFinal);
    }

    @Override
    public void procesa(Mas_Decs decs) {
        decs.ponPrim(etqFinal);
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
        decs.ponSig(etqFinal);
    }

    @Override
    public void procesa(Una_Dec dec) {
        dec.ponPrim(etqFinal);
        dec.dec().procesa(this);
        dec.ponSig(etqFinal);
    }

    @Override
    public void procesa(Dec_Proc dec_Proc) {
        subPendientes.push(dec_Proc);
    }

    @Override
    public void procesa(Si_Intrs si_Intrs) {
        si_Intrs.ponPrim(etqFinal);
        si_Intrs.intrs().procesa(this);
        si_Intrs.ponSig(etqFinal);
    }

    @Override
    public void procesa(Mas_Intrs mas_Intrs) {
        mas_Intrs.ponPrim(etqFinal);
        mas_Intrs.intrs().procesa(this);
        mas_Intrs.intr().procesa(this);
        mas_Intrs.ponSig(etqFinal);
    }

    @Override
    public void procesa(Una_Intr una_Intr) {
        una_Intr.ponPrim(etqFinal);
        una_Intr.intr().procesa(this);
        una_Intr.ponSig(etqFinal);
    }

    private void procesa_acc_valor(Exp exp) {
        exp.procesa(this);
        if (esDesignador(exp)) {
            etqFinal++;
        }
    }

    private void procesa_Intr(Intr intr) {
        intr.ponPrim(etqFinal);
        intr.exp().procesa(this);
        etqFinal++;
        intr.ponSig(etqFinal);
    }
    
    @Override
    public void procesa(I_Eval i_Eval) {
        procesa_Intr(i_Eval);
    }

    @Override
    public void procesa(I_If i_If) {
        i_If.ponPrim(etqFinal);
        procesa(i_If.exp());
        etqFinal++;
        i_If.prog().procesa(this);
        etqFinal++;
        i_If.ponSig(etqFinal);
        i_If.i_else().procesa(this);
        i_If.ponFin(etqFinal);
    }

    @Override
    public void procesa(I_While i_While) {
        i_While.ponPrim(etqFinal);
        procesa_acc_valor(i_While.exp());
        etqFinal++;
        i_While.prog().procesa(this);
        etqFinal++;
        i_While.ponSig(etqFinal);
    }

    private void procesa_Intr2(Intr intr){
        intr.ponPrim(etqFinal);
        intr.exp().procesa(this);
        etqFinal += 2;
        intr.ponSig(etqFinal);
        
    }

    @Override
    public void procesa(I_Read i_Read) {
        procesa_Intr2(i_Read);
    }

    @Override
    public void procesa(I_Write i_Write) {
        i_Write.ponPrim(etqFinal);
        procesa_acc_valor(i_Write.exp());
        etqFinal++;
        i_Write.ponSig(etqFinal);
    }

    @Override
    public void procesa(I_NL i_Nl) {
        procesa_lit(i_Nl.exp());  //Aunque no sea un literal, a efectos prácticos se aplican exactamente las mismas instrucciones
    }

    @Override
    public void procesa(I_New i_New) {
        procesa_Intr2(i_New);
    }

    @Override
    public void procesa(I_Delete i_Delete) {
        procesa_Intr2(i_Delete);
    }

    @Override
    public void procesa(I_Call i_Call) {
        i_Call.ponPrim(etqFinal);
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
        etqFinal++;
    }

    @Override
    public void procesa(I_Prog i_Prog) {
        procesa_Intr(i_Prog);
    }

    @Override
    public void procesa(Si_Else si_Else) {
        si_Else.ponPrim(etqFinal);
        si_Else.prog().procesa(this);
        si_Else.ponSig(etqFinal);
    }

    private void procesa_op_bin(Exp exp) {
        exp.ponPrim(etqFinal);
        procesa_acc_valor(exp.opnd0());
        procesa_conversion(exp.opnd0(), exp.opnd1());
        procesa_acc_valor(exp.opnd1());
        procesa_conversion(exp.opnd1(), exp.opnd0());
        etqFinal++;
        exp.ponSig(etqFinal);
    }

    private void procesa_conversion(Exp opnd0, Exp opnd1) {
        if (claseDe(refenciar(opnd0.tipo()), T_Int.class) && claseDe(refenciar(opnd1.tipo()), T_Real.class)) {
            etqFinal++;
        }
    }

    @Override
    public void procesa(Asig exp) {
        exp.ponPrim(etqFinal);
        exp.opnd0().procesa(this);
        if (claseDe(refenciar(exp.opnd0().tipo()), T_Int.class)
                && claseDe(refenciar(exp.opnd1().tipo()), T_Real.class)) {
            procesa_acc_valor(exp.opnd1());
            etqFinal += 2;
        } else {
            exp.opnd1().procesa(this);
            etqFinal++;
        }
        exp.ponSig(etqFinal);
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
        exp.ponPrim(etqFinal);
        procesa_acc_valor(exp.opnd0());
        etqFinal++;
        exp.ponSig(etqFinal);
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
        exp.ponPrim(etqFinal);
        exp.opnd0().procesa(this);
        procesa_acc_valor(exp.opnd1());
        etqFinal += 3;
        exp.ponSig(etqFinal);
    }

    @Override
    public void procesa(Acceso exp) {
        exp.ponPrim(etqFinal);
        exp.opnd0().procesa(this);
        etqFinal += 2;
        exp.ponSig(etqFinal);
    }

    @Override
    public void procesa(Indireccion exp) {
        exp.ponPrim(etqFinal);
        procesa_acc_valor(exp.opnd0());
        etqFinal++;
        exp.ponSig(etqFinal);
    }

    private void procesa_lit(Exp exp) {
        exp.ponPrim(etqFinal);
        etqFinal++;
        exp.ponSig(etqFinal);
    }

    @Override
    public void procesa(Lit_ent exp) {
        procesa_lit(exp);
    }

    @Override
    public void procesa(True exp) {
        procesa_lit(exp);
    }

    @Override
    public void procesa(False exp) {
        procesa_lit(exp);
    }

    @Override
    public void procesa(Lit_real exp) {
        procesa_lit(exp);
    }

    @Override
    public void procesa(Cadena exp) {
        procesa_lit(exp);
    }

    @Override
    public void procesa(Iden exp) {
        exp.ponPrim(etqFinal);
        procesa_acc_iden((Dec_Var) exp.vinculo());
        exp.ponSig(etqFinal);
    }

    private void procesa_acc_iden(Dec_Var dec_Var) {
        if (dec_Var.nivel() == 0) {
            etqFinal++;
        } else {
            etqFinal += 3;
        }
    }

    @Override
    public void procesa(Null exp) {
        procesa_lit(exp);
    }

}
