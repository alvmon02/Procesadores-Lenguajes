package etiquetado;

import java.util.Stack;

import javax.lang.model.element.VariableElement;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class Etiquetado2 extends ProcesamientoDef {

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

    @Override
    public void procesa(I_Eval i_Eval) {
        i_Eval.ponPrim(etqFinal);
        i_Eval.exp().procesa(this);
        etqFinal++;
        i_Eval.ponSig(etqFinal);
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

    @Override
    public void procesa(I_Read i_Read) {
        i_Read.ponPrim(etqFinal);
        i_Read.exp().procesa(this);
        etqFinal += 2;
        i_Read.ponSig(etqFinal);
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
        i_Nl.ponPrim(etqFinal);
        etqFinal++;
        i_Nl.ponSig(etqFinal);
    }

    @Override
    public void procesa(I_New i_New) {
        i_New.ponPrim(etqFinal);
        i_New.exp().procesa(this);
        etqFinal += 2;
        i_New.ponSig(etqFinal);
    }

    @Override
    public void procesa(I_Delete i_Delete) {
        i_Delete.ponPrim(etqFinal);
        i_Delete.exp().procesa(this);
        etqFinal += 2;
        i_Delete.ponSig(etqFinal);
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

    // TODO revisar que sea +=3
    private void procesa_paso_param(PForm pform, Exp exp) {
        etqFinal += 3;
        exp.procesa(this);
        etqFinal++;
    }

    @Override
    public void procesa(I_Prog i_Prog) {
        i_Prog.ponPrim(etqFinal);
        i_Prog.prog().procesa(this);
        etqFinal++;
        i_Prog.ponSig(etqFinal);
    }

    // TODO revisar que no haya que hacer un etq++
    @Override
    public void procesa(Si_Else si_Else) {
        si_Else.ponPrim(etqFinal);
        si_Else.prog().procesa(this);
        si_Else.ponSig(etqFinal);
    }

    @Override
    public void procesa(Asig exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Comp exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Dist exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Menor exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Mayor exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(MenorIgual exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(MayorIgual exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Suma exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Resta exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(And exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Or exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Mul exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Div exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Porcentaje exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Negativo exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Negado exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Index exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Acceso exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Indireccion exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Lit_ent exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(True exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(False exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Lit_real exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Cadena exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Iden exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(Null exp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

}
