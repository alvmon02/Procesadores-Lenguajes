package etiquetado;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import java.util.Stack;

import javax.lang.model.element.VariableElement;

public class etiquetado extends ProcesamientoDef {
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
            sub.setPrim(nuevaEtiqueta());
            sub.pforms().procesa(this);
            sub.prog().procesa(this);
            etqFinal += 2;
            sub.setSig(nuevaEtiqueta());
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
        prog.setPrim(etqFinal);
        recolectaSubs(prog.decs());
        prog.intrs().procesa(this);
        procesarSubPendientes();
        prog.setSig(nuevaEtiqueta());
    }

    // Procesamiento de declaraciones
    @Override
    public void procesa(Si_Decs si_decs) {
        si_decs.ldecs().procesa(this);
    }

    @Override
    public void procesa(Mas_Decs mas_decs) {
        mas_decs.setPrim(etqFinal);
        mas_decs.ldecs().procesa(this);
        mas_decs.dec().procesa(this);
        mas_decs.setSig(etqFinal);
    }

    @Override
    public void procesa(Una_Dec una_dec) {
        una_dec.setPrim(etqFinal);
        una_dec.dec().procesa(this);
        una_dec.setSig(etqFinal);
    }

    @Override
    public void procesa(Dec_Proc dec_proc) {
        subPendientes.push(dec_proc);
    }

    @Override
    public void procesa(Dec_Var dec_var) {
        dec_var.tipo().procesa(this);
    }

    @Override
    public void procesa(Dec_Tipo dec_tipo) {
        // No necesita etiquetado
    }

    // Procesamiento de parámetros formales
    @Override
    public void procesa(Si_PForms si_pforms) {
        si_pforms.pforms().procesa(this);
    }

    @Override
    public void procesa(Mas_PForms mas_pforms) {
        mas_pforms.setPrim(etqFinal);
        mas_pforms.pforms().procesa(this);
        mas_pforms.pform().procesa(this);
        mas_pforms.setSig(etqFinal);
    }

    @Override
    public void procesa(Una_PForm una_pform) {
        una_pform.setPrim(etqFinal);
        una_pform.pform().procesa(this);
        una_pform.setSig(etqFinal);
    }

    // Procesamiento de instrucciones
    @Override
    public void procesa(Si_Intrs si_intrs) {
        si_intrs.intrs().procesa(this);
    }

    @Override
    public void procesa(Mas_Intrs mas_intrs) {
        mas_intrs.setPrim(etqFinal);
        mas_intrs.intrs().procesa(this);
        mas_intrs.intr().procesa(this);
        mas_intrs.setSig(etqFinal);
    }

    @Override
    public void procesa(Una_Intr una_intr) {
        una_intr.setPrim(etqFinal);
        una_intr.intr().procesa(this);
        una_intr.setSig(etqFinal);
    }

    @Override
    public void procesa(I_Eval i_eval) {
        i_eval.setPrim(etqFinal);
        i_eval.exp().procesa(this);
        if (esDesignador(i_eval.exp())) {
            etqFinal++;
        }
        i_eval.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_If i_if) {
        i_if.setPrim(etqFinal);
        i_if.exp().procesa(this);
        if (esDesignador(i_if.exp())) {
            etqFinal++;
        }
        i_if.prog().procesa(this);
        etqFinal++;
        i_if.i_else().procesa(this);
        etqFinal++;
        i_if.setSig(nuevaEtiqueta());
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
        i_while.setPrim(etqFinal);
        i_while.exp().procesa(this);
        if (esDesignador(i_while.exp())) {
            etqFinal++;
        }
        i_while.prog().procesa(this);
        etqFinal++;
        i_while.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_Read i_read) {
        i_read.setPrim(etqFinal);
        i_read.exp().procesa(this);
        i_read.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_Write i_write) {
        i_write.setPrim(etqFinal);
        i_write.exp().procesa(this);
        i_write.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_NL i_nl) {
        i_nl.setPrim(etqFinal);
        i_nl.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_New i_new) {
        i_new.setPrim(etqFinal);
        i_new.exp().procesa(this);
        i_new.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_Delete i_delete) {
        i_delete.setPrim(etqFinal);
        i_delete.exp().procesa(this);
        i_delete.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_Call i_call) {
        i_call.setPrim(etqFinal);
        i_call.exp().procesa(this);
        etqFinal++;
        i_call.preals().procesa(this);
        etqFinal++;
        i_call.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(I_Prog i_prog) {
        i_prog.setPrim(etqFinal);
        i_prog.prog().procesa(this);
        i_prog.setSig(nuevaEtiqueta());
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
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Suma exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Resta exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Mul exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Div exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Porcentaje exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Index exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (esDesignador(exp)) {
            etqFinal++;
        }
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Acceso exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        if (esDesignador(exp)) {
            etqFinal++;
        }
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Indireccion exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        if (esDesignador(exp)) {
            etqFinal++;
        }
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Negativo exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Negado exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Comp exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Dist exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Menor exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Mayor exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(MenorIgual exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(MayorIgual exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(And exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Or exp) {
        exp.setPrim(etqFinal);
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        exp.setSig(nuevaEtiqueta());
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
        exp.setPrim(etqFinal);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(False exp) {
        exp.setPrim(etqFinal);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Null exp) {
        exp.setPrim(etqFinal);
        exp.setSig(nuevaEtiqueta());
    }

    @Override
    public void procesa(Exp exp) {
        exp.setPrim(etqFinal);
        if (esDesignador(exp)) {
            etqFinal++;
        }
        exp.setSig(nuevaEtiqueta());
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
        campos.campos().procesa(this);
        campos.campo().procesa(this);
    }

    @Override
    public void procesa(Un_Cmp_S campo) {
        campo.tipo().procesa(this);
    }
}