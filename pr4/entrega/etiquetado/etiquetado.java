package etiquetado;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import java.util.Stack;

import javax.lang.model.element.VariableElement;

public class Etiquetado extends ProcesamientoDef {
    private int etqFinal = 0;
    private Stack<Dec_Proc> subPendientes = new Stack<>();

    // Método para generar nueva etiqueta
    private int nuevaEtiqueta() {
        return etqFinal++;
    }

    // Métodos para recolectar subprogramas
    private void recolectaSubs(DecsOpt ldecs) {
        ldecs.procesa(this);
    }

    // Procesamiento de subprogramas pendientes
    private void procesarSubPendientes() {
        while (!subPendientes.empty()) {
            Dec_Proc sub = subPendientes.pop();
            sub.ponPrim(nuevaEtiqueta());
            sub.pforms().procesa(this);
            sub.prog().procesa(this);
            etqFinal += 2;
            sub.ponSig(nuevaEtiqueta());
        }
    }

    // Detección de designadores
    private boolean esDesignador(Exp exp) {
        return exp instanceof Index || 
               exp instanceof Acceso || 
               exp instanceof Indireccion || 
               exp instanceof VariableElement;
    }

    // Procesamiento del programa principal
    @Override
    public void procesa(Prog prog) {
        prog.ponPrim(etqFinal);
        recolectaSubs(prog.bloque().decs());
        prog.bloque().intrs().procesa(this);
        procesarSubPendientes();
        prog.ponSig(nuevaEtiqueta());
    }

    // Procesamiento de declaraciones
    @Override
    public void procesa(Si_Decs si_decs) {
        si_decs.ldecs().procesa(this);
    }

    @Override
    public void procesa(Mas_Decs mas_decs) {
        mas_decs.ponPrim(etqFinal);
        mas_decs.ldecs().procesa(this);
        mas_decs.dec().procesa(this);
        mas_decs.ponSig(etqFinal);
    }

    @Override
    public void procesa(Una_Dec una_dec) {
        una_dec.ponPrim(etqFinal);
        una_dec.dec().procesa(this);
        una_dec.ponSig(etqFinal);
    }

    @Override
    public void procesa(Dec_Proc dec_proc) {
        subPendientes.push(dec_proc);
    }

    @Override
    public void procesa(Dec_Var dec_var) {
        dec_var.tipo().procesa(this);
    }

    // Procesamiento de parámetros formales
    @Override
    public void procesa(Si_PForms si_pforms) {
        si_pforms.pforms().procesa(this);
    }

    @Override
    public void procesa(Mas_PForms mas_pforms) {
        mas_pforms.ponPrim(etqFinal);
        mas_pforms.pforms().procesa(this);
        mas_pforms.pform().procesa(this);
        mas_pforms.ponSig(etqFinal);
    }

    @Override
    public void procesa(Una_PForm una_pform) {
        una_pform.ponPrim(etqFinal);
        una_pform.pform().procesa(this);
        una_pform.ponSig(etqFinal);
    }

    // Procesamiento de instrucciones
    @Override
    public void procesa(Si_Intrs si_intrs) {
        si_intrs.intrs().procesa(this);
    }

    @Override
    public void procesa(Mas_Intrs mas_intrs) {
        mas_intrs.ponPrim(etqFinal);
        mas_intrs.intrs().procesa(this);
        mas_intrs.intr().procesa(this);
        mas_intrs.ponSig(etqFinal);
    }

    @Override
    public void procesa(Una_Intr una_intr) {
        una_intr.ponPrim(etqFinal);
        una_intr.intr().procesa(this);
        una_intr.ponSig(etqFinal);
    }

    @Override
    public void procesa(I_Eval i_eval) {
        i_eval.ponPrim(etqFinal);
        i_eval.exp().procesa(this);
        if (esDesignador(i_eval.exp())) {
            etqFinal++;
        }
        i_eval.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_If i_if) {
        i_if.ponPrim(etqFinal);
        i_if.exp().procesa(this);
        if (esDesignador(i_if.exp())) {
            etqFinal++;
        }
        i_if.prog().procesa(this);
        etqFinal++;
        i_if.i_else().procesa(this);
        etqFinal++;
        i_if.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Si_Else si_else) {
        si_else.prog().procesa(this);
    }

    @Override
    public void procesa(No_Else no_else) {
        // No necesita etiquetado
    }

    @Override
    public void procesa(I_While i_while) {
        i_while.ponPrim(etqFinal);
        i_while.exp().procesa(this);
        if (esDesignador(i_while.exp())) {
            etqFinal++;
        }
        i_while.prog().procesa(this);
        etqFinal++;
        i_while.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_Read i_read) {
        i_read.ponPrim(etqFinal);
        i_read.exp().procesa(this);
        i_read.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_Write i_write) {
        i_write.ponPrim(etqFinal);
        i_write.exp().procesa(this);
        i_write.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_NL i_nl) {
        i_nl.ponPrim(etqFinal);
        i_nl.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_New i_new) {
        i_new.ponPrim(etqFinal);
        i_new.exp().procesa(this);
        i_new.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_Delete i_delete) {
        i_delete.ponPrim(etqFinal);
        i_delete.exp().procesa(this);
        i_delete.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_Call i_call) {
        i_call.ponPrim(etqFinal);
        i_call.exp().procesa(this);
        etqFinal++;
        i_call.preals().procesa(this);
        etqFinal++;
        i_call.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_Prog i_prog) {
        i_prog.ponPrim(etqFinal);
        i_prog.prog().procesa(this);
        i_prog.ponSig(nuevaEtiqueta());
    }

    // Procesamiento de parámetros reales
    @Override
    public void procesa(Si_PReals si_preals) {
        si_preals.preals().procesa(this);
    }

    @Override
    public void procesa(Mas_PReals mas_preals) {
        mas_preals.ponPrim(etqFinal);
        mas_preals.preals().procesa(this);
        mas_preals.exp().procesa(this);
        mas_preals.ponSig(etqFinal);
    }

    @Override
    public void procesa(Un_PReal un_preals) {
        un_preals.ponPrim(etqFinal);
        un_preals.exp().procesa(this);
        un_preals.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(No_PReals no_preals) {
        // No necesita etiquetado
    }

    // Procesamiento completo de expresiones
    @Override
    public void procesa(Asig exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(Suma exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(Resta exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(Mul exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(Div exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(Porcentaje exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(Index exp) {
        exp.ponPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (esDesignador(exp)) {
            etqFinal++;
        }
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Acceso exp) {
        exp.ponPrim(etqFinal);
        exp.opnd0().procesa(this);
        if (esDesignador(exp)) {
            etqFinal++;
        }
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Indireccion exp) {
        exp.ponPrim(etqFinal);
        exp.opnd0().procesa(this);
        if (esDesignador(exp)) {
            etqFinal++;
        }
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Negativo exp) {
        exp.ponPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Negado exp) {
        exp.ponPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Comp exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(Dist exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(Menor exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(Mayor exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(MenorIgual exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(MayorIgual exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(And exp) {
        procesa_opnds(exp);
    }

    @Override
    public void procesa(Or exp) {
        procesa_opnds(exp);
    }

    // Procesamiento de literales y variables
    @Override
    public void procesa(Lit_ent exp) {
        exp.ponPrim(etqFinal);
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Lit_real exp) {
        exp.ponPrim(etqFinal);
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Cadena exp) {
        exp.ponPrim(etqFinal);
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(True exp) {
        exp.ponPrim(etqFinal);
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(False exp) {
        exp.ponPrim(etqFinal);
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Null exp) {
        exp.ponPrim(etqFinal);
        exp.ponSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Exp exp) {
        exp.ponPrim(etqFinal);
        if (esDesignador(exp)) {
            etqFinal++;
        }
        exp.ponSig(nuevaEtiqueta());
    }

    // Procesamiento de tipos
    @Override
    public void procesa(T_Int tipo) {
        // No necesita etiquetado
    }

    @Override
    public void procesa(T_Real tipo) {
        // No necesita etiquetado
    }

    @Override
    public void procesa(T_Bool tipo) {
        // No necesita etiquetado
    }

    @Override
    public void procesa(T_String tipo) {
        // No necesita etiquetado
    }

    @Override
    public void procesa(T_Array tipo) {
        tipo.tipo().procesa(this);
    }

    @Override
    public void procesa(T_Puntero tipo) {
        tipo.tipo().procesa(this);
    }

    @Override
    public void procesa(T_Iden tipo) {
        // No necesita etiquetado
    }

    // Procesamiento de campos

    @Override
    public void procesa(Mas_Cmp_S campos) {
        campos.camposS().procesa(this);
        campos.campoS().procesa(this);
    }

    @Override
    public void procesa(Un_Cmp_S campo) {
        campo.tipo().procesa(this);
    }
    //Funciones auxiliares
    public void procesa_opnds(Exp exp){
        exp.ponPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.ponSig(nuevaEtiqueta());
    }
}
