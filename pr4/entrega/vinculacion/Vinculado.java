package vinculacion;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import errores_procesamiento.*;

import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;

public class Vinculado extends ProcesamientoDef {

    private class TablaSimbolos {
        private Stack<HashMap<String, Nodo>> ambitos = new Stack<>();

        public void abreAmbito() {
            ambitos.push(new HashMap<>());
        }

        public void cierraAmbito() {
            if (!ambitos.isEmpty()) {
                ambitos.pop();
            } else {
                throw new RuntimeException("No hay ambitos abiertos para cerrar.");
            }
        }

        public boolean contiene(String id) {
            return ambitos.peek().containsKey(id);
        }

        public void inserta(String id, Nodo nodo) {
            if (this.contiene(id)) {
                throw new RuntimeException("Error: el identificador '" + id + "' ya existe en el ambito actual.");
            }
            ambitos.peek().put(id, nodo);
        }

        public Nodo vinculoDe(String id) {
            for (int i = ambitos.size() - 1; i >= 0; i--) {
                if (ambitos.get(i).containsKey(id)) {
                    return ambitos.get(i).get(id);
                }
            }
            return null;
        }
    }

    private TablaSimbolos tablaSimbolos;
    private ArrayList<ErrorProcesamiento> errorProcesamientos = new ArrayList<>();

    @SuppressWarnings("rawtypes")
    private boolean claseDe(Object o, Class c) {
        return o.getClass() == c;
    }

    public boolean hayErrores() {
        return errorProcesamientos.size() > 0;
    }

    public ArrayList<ErrorProcesamiento> errores() {
        return errorProcesamientos;
    }

    private void contieneInserta(String id, Nodo nodo) {
        if (this.tablaSimbolos.contiene(id)) {
            errorProcesamientos.add(ErrorVinculacion.errorDuplicado(nodo.leeFila(), nodo.leeCol(), id));
        }
        else {
            this.tablaSimbolos.inserta(id, nodo);
        }
    }

    private void vinculaId(String id, Nodo nodo) {
        Nodo vinculo = this.tablaSimbolos.vinculoDe(id);
        if (vinculo == null) {
            errorProcesamientos.add(ErrorVinculacion.errorNoDeclarado(nodo.leeFila(), nodo.leeCol(), id));
        } else {
            nodo.ponVinculo(vinculo);
        }
    }

    public Vinculado() {
        this.tablaSimbolos = new TablaSimbolos();
    }

    public Vinculado vincula(Prog prog) {
        procesa(prog);
        return this;
    }

    @Override
    public void procesa(Prog prog) {
        this.tablaSimbolos.abreAmbito();
        prog.decs().procesa(this);
        prog.intrs().procesa(this);
        this.tablaSimbolos.cierraAmbito();
    }

    @Override
    public void procesa(Si_Decs decs) {
        decs.ldecs().procesa(this);
        decs.ldecs().procesa2(this);
    }

    @Override
    public void procesa(Mas_Decs decs) {
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
    }

    @Override
    public void procesa2(Mas_Decs decs) {
        decs.ldecs().procesa2(this);
        decs.dec().procesa2(this);
    }

    @Override
    public void procesa(Una_Dec dec) {
        dec.dec().procesa(this);
    }

    @Override
    public void procesa2(Una_Dec dec) {
        dec.dec().procesa2(this);
    }

    @Override
    public void procesa(Dec_Var dec_Var) {
        dec_Var.tipo().procesa(this);
        contieneInserta(dec_Var.id(), dec_Var);
    }

    @Override
    public void procesa2(Dec_Var dec_Var) {
        dec_Var.tipo().procesa2(this);
    }

    @Override
    public void procesa(Dec_Tipo dec_Tipo) {
        dec_Tipo.tipo().procesa(this);
        contieneInserta(dec_Tipo.id(), dec_Tipo);
    }

    @Override
    public void procesa2(Dec_Tipo dec_Tipo) {
        dec_Tipo.tipo().procesa2(this);
    }

    @Override
    public void procesa(Dec_Proc dec_Proc) {
        contieneInserta(dec_Proc.id(), dec_Proc);
        this.tablaSimbolos.abreAmbito();
        dec_Proc.pforms().procesa(this);
        dec_Proc.prog().procesa(this);
        this.tablaSimbolos.cierraAmbito();
    }

    @Override
    public void procesa(Si_PForms si_PForms) {
        si_PForms.pforms().procesa(this);
    }

    @Override
    public void procesa(Mas_PForms mas_PForm) {
        mas_PForm.pforms().procesa(this);
        mas_PForm.pform().procesa(this);
    }

    @Override
    public void procesa(Una_PForm una_PForm) {
        una_PForm.pform().procesa(this);
    }

    @Override
    public void procesa(PForm pform) {
        pform.tipo().procesa(this);
        contieneInserta(pform.id(), pform);
    }

    @Override
    public void procesa(T_Iden tIden) {
        vinculaId(tIden.id(), tIden);
    }

    @Override
    public void procesa(T_Array tArray) {
        tArray.tipo().procesa(this);
    }

    @Override
    public void procesa2(T_Array tArray) {
        tArray.tipo().procesa2(this);
    }

    @Override
    public void procesa(T_Puntero tPuntero) {
        if (!claseDe(tPuntero.tipo(), T_Iden.class)) {
            tPuntero.tipo().procesa(this);
        }
    }

    @Override
    public void procesa2(T_Puntero tPuntero) {
        if (claseDe(tPuntero.tipo(), T_Iden.class)) {
            tPuntero.tipo().procesa(this);
        } else {
            tPuntero.tipo().procesa2(this);
        }
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

    @Override
    public void procesa(I_Eval i_Eval) {
        i_Eval.exp().procesa(this);
    }

    @Override
    public void procesa(I_If i_If) {
        i_If.exp().procesa(this);
        i_If.prog().procesa(this);
        i_If.i_else().procesa(this);
    }

    @Override
    public void procesa(I_While i_While) {
        i_While.exp().procesa(this);
        i_While.prog().procesa(this);
    }

    @Override
    public void procesa(I_Read i_Read) {
        i_Read.exp().procesa(this);
    }

    @Override
    public void procesa(I_Write i_Write) {
        i_Write.exp().procesa(this);
    }

    @Override
    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
    }

    @Override
    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
    }

    @Override
    public void procesa(I_Call i_Call) {
        vinculaId(i_Call.id(), i_Call);
        ;
        i_Call.preals().procesa(this);
    }

    @Override
    public void procesa(I_Prog i_Prog) {
        i_Prog.prog().procesa(this);
    }

    @Override
    public void procesa(Si_Else si_Else) {
        si_Else.prog().procesa(this);
    }

    @Override
    public void procesa(Si_PReals si_PReals) {
        si_PReals.preals().procesa(this);
    }

    @Override
    public void procesa(Mas_PReals mas_PReal) {
        mas_PReal.preals().procesa(this);
        mas_PReal.exp().procesa(this);
    }

    @Override
    public void procesa(Un_PReal un_PReal) {
        un_PReal.exp().procesa(this);
    }

    @Override
    public void procesa(Asig exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Comp exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Dist exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Menor exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Mayor exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(MenorIgual exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(MayorIgual exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Suma exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Resta exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(And exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Or exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Mul exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Div exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Porcentaje exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Negativo exp) {
        exp.opnd0().procesa(this);
    }

    @Override
    public void procesa(Negado exp) {
        exp.opnd0().procesa(this);
    }

    @Override
    public void procesa(Index exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }

    @Override
    public void procesa(Acceso exp) {
        exp.opnd0().procesa(this);
    }

    @Override
    public void procesa(Indireccion exp) {
        exp.opnd0().procesa(this);
    }

    @Override
    public void procesa(Iden exp) {
        vinculaId(exp.id(), exp);
    }
}