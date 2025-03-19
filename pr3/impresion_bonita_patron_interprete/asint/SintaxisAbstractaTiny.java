package asint;

import java.nio.channels.UnsupportedAddressTypeException;

public class SintaxisAbstractaTiny {

    private static void imprimeOpnd(Exp opnd, int np) {
        if(opnd.prioridad() < np) {System.out.print("(");};
        opnd.imprime();
        if(opnd.prioridad() < np) {System.out.print(")");};        
    }
    private static void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1) {
        imprimeOpnd(opnd0,np0);
        System.out.print(" "+op+" ");
        imprimeOpnd(opnd1,np1);
    }

    private static void imprimeExpUni(String op, Exp opnd, int np) {
        System.out.print(op);
        imprimeOpnd(opnd, np);
    }

    public static abstract class Nodo {
        public Nodo() {
            fila = col = -1;
        }

        public abstract void imprime();
        private int fila;

        private int col;

        public Nodo ponFila(int fila) {
            this.fila = fila;
            return this;
        }

        public Nodo ponCol(int col) {
            this.col = col;
            return this;
        }

        public int leeFila() {
            return fila;
        }

        public int leeCol() {
            return col;
        }
    }

    public static class Prog extends Nodo {
        private DecsOpt decs;
        private IntrsOpt intrs;

        public Prog(DecsOpt decs, IntrsOpt intrs) {
            super();
            this.decs = decs;
            this.intrs = intrs;
        }

        public DecsOpt decs() {
            return decs;
        }

        public IntrsOpt intrs() {
            return intrs;
        }

        public String toString() {
            return "prog(" + decs + "," + intrs + ")";
        }

        @Override
        public void imprime() {
            System.out.println("{");
            decs.imprime();
            intrs.imprime();
            System.out.println("}");
        }
    }

    public static abstract class DecsOpt extends Nodo {
        public DecsOpt() {
            super();
        }

        public LDecs ldecs() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Si_Decs extends DecsOpt {
        private LDecs decs;

        public Si_Decs(LDecs decs) {
            super();
            this.decs = decs;
        }

        public String toString() {
            return "si_decs(" + decs + ")";
        }

        public LDecs ldecs() {
            return decs;
        }

        @Override
        public void imprime() {
            decs.imprime();
        }
    }

    public static class No_Decs extends DecsOpt {
        public No_Decs() {
            super();
        }

        public String toString() {
            return "no_decs()";
        }

        @Override
        public void imprime() {
            
        }
    }

    public static abstract class LDecs extends Nodo {
        public LDecs() {
            super();
        }

        public Dec dec() {
            throw new UnsupportedOperationException();
        }

        public LDecs ldecs() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Mas_Decs extends LDecs {
        private LDecs decs;
        private Dec dec;

        public Mas_Decs(LDecs decs, Dec dec) {
            super();
            this.decs = decs;
            this.dec = dec;
        }

        public LDecs ldecs() {
            return decs;
        }

        public Dec dec() {
            return dec;
        }

        public String toString() {
            return "mas_decs(" + decs + "," + dec + ")";
        }

        @Override
        public void imprime() {
            decs.imprime();
            dec.imprime();
        }
    }

    public static class Una_Dec extends LDecs {
        private Dec dec;

        public Una_Dec(Dec dec) {
            super();
            this.dec = dec;
        }

        public Dec dec() {
            return dec;
        }

        public String toString() {
            return "una_dec(" + dec + ")";
        }

        @Override
        public void imprime() {
            dec.imprime();
        }
    }

    public static abstract class Dec extends Nodo {
        public Dec() {
            super();
        }

        public Tipo tipo() {
            throw new UnsupportedOperationException();
        }

        public String id() {
            throw new UnsupportedOperationException();
        }

        public PForms pforms() {
            throw new UnsupportedOperationException();
        }

        public Prog prog() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Dec_Var extends Dec {
        private Tipo tipo;
        private String id;

        public Dec_Var(Tipo tipo, String id) {
            super();
            this.tipo = tipo;
            this.id = id;
        }

        public Tipo tipo() {
            return tipo;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "dec_var(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println(" " + id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Dec_Tipo extends Dec {
        private Tipo tipo;
        private String id;

        public Dec_Tipo(Tipo tipo, String id) {
            super();
            this.tipo = tipo;
            this.id = id;
        }

        public Tipo tipo() {
            return tipo;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "dec_tipo(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println(" " + id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Dec_Proc extends Dec {
        private String id;
        private PForms pforms;
        private Prog prog;

        public Dec_Proc(String id, PForms pforms, Prog prog) {
            super();
            this.id = id;
            this.pforms = pforms;
            this.prog = prog;
        }

        public String id() {
            return id;
        }

        public PForms pforms() {
            return pforms;
        }

        public Prog prog() {
            return prog;
        }

        public String toString() {
            return "dec_proc(" + id + "[" + leeFila() + "," + leeCol() + "]," + pforms + prog + ")";
        }

        @Override
        public void imprime() {
            System.out.println("<proc> " + id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
            pforms.imprime();
            prog.imprime();
        }
    }

    public static abstract class PForms extends Nodo {
        public PForms() {
            super();
        }

        public LPForms pforms() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Si_PForms extends PForms {
        private LPForms pforms;

        public Si_PForms(LPForms pforms) {
            super();
            this.pforms = pforms;
        }

        public LPForms pforms() {
            return pforms;
        }

        public String toString() {
            return "si_pforms(" + pforms + ")";
        }

        @Override
        public void imprime() {
            pforms.imprime();
        }
    }

    public static class No_PForms extends PForms {
        public No_PForms() {
            super();
        }

        public String toString() {
            return "no_pforms()";
        }

        @Override
        public void imprime() {
            
        }
    }

    public static abstract class LPForms extends Nodo {
        public LPForms() {
            super();
        }

        public LPForms pforms() {
            throw new UnsupportedOperationException();
        }

        public PForm pform() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Mas_PForms extends LPForms {
        private LPForms pforms;
        private PForm pform;

        public Mas_PForms(LPForms pforms, PForm pform) {
            super();
            this.pforms = pforms;
            this.pform = pform;
        }

        public LPForms pforms() {
            return pforms;
        }

        public PForm pform() {
            return pform;
        }

        public String toString() {
            return "mas_pforms(" + pforms + "," + pform + ")";
        }

        @Override
        public void imprime() {
            pforms.imprime();
            pform.imprime();
        }
    }

    public static class Una_PForm extends LPForms {
        private PForm pform;

        public Una_PForm(PForm pform) {
            super();
            this.pform = pform;
        }

        public PForm pform() {
            return pform;
        }

        public String toString() {
            return "una_pform(" + pform + ")";
        }

        @Override
        public void imprime() {
            pform.imprime();
        }
    }

    public static class PForm extends Nodo {
        private Tipo tipo;
        private Ref ref;
        private String id;

        public PForm(Tipo tipo, Ref ref, String id) {
            super();
            this.tipo = tipo;
            this.ref = ref;
            this.id = id;
        }

        public Tipo tipo() {
            return tipo;
        }

        public Ref ref() {
            return ref;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "pform(" + tipo + "," + ref + "," + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public void imprime() {
            tipo.imprime();
            ref.imprime();
            System.out.println(" " + id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static abstract class Ref extends Nodo {
        public Ref() {
            super();
        }
    }

    public static class Si_Ref extends Ref {
        public Si_Ref() {
            super();
        }

        public String toString() {
            return "si_ref()";
        }

        @Override
        public void imprime() {
            System.out.println("&");
        }
    }

    public static class No_Ref extends Ref {
        public No_Ref() {
            super();
        }

        public String toString() {
            return "no_ref()";
        }

        @Override
        public void imprime() {
        }
    }

    public static abstract class Tipo extends Nodo {
        public Tipo() {
            super();
        }

        public String id() {
            throw new UnsupportedOperationException();
        }

        public CamposS camposS() {
            throw new UnsupportedOperationException();
        }

        public Tipo tipo() {
            throw new UnsupportedOperationException();
        }

        public String ent() {
            throw new UnsupportedOperationException();
        }
    }

    public static class T_Iden extends Tipo {
        private String id;

        public T_Iden(String id) {
            super();
            this.id = id;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "tipo(" + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public void imprime() {
            System.out.println(" " + id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class T_String extends Tipo {
        public T_String() {
            super();
        }

        public String toString() {
            return "t_string()";
        }

        @Override
        public void imprime() {
            System.out.println("<string>");
        }
    }

    public static class T_Int extends Tipo {
        public T_Int() {
            super();
        }

        public String toString() {
            return "t_int()";
        }

        @Override
        public void imprime() {
            System.out.println("<int>");
        }
    }

    public static class T_Bool extends Tipo {
        public T_Bool() {
            super();
        }

        public String toString() {
            return "t_bool()";
        }

        @Override
        public void imprime() {
            System.out.println("<bool>");
        }
    }

    public static class T_Real extends Tipo {
        public T_Real() {
            super();
        }

        public String toString() {
            return "t_real()";
        }

        @Override
        public void imprime() {
            System.out.println("<real>");
        }
    }

    public static class T_Array extends Tipo {
        private Tipo tipo;
        private String ent;

        public T_Array(Tipo tipo, String ent) {
            super();
            this.tipo = tipo;
            this.ent = ent;
        }

        public Tipo tipo() {
            return tipo;
        }

        public String ent() {
            return ent;
        }

        public String toString() {
            return "t_array(" + tipo + "," + ent + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println(" [" + ent + "]");}
    }

    public static class T_Puntero extends Tipo {
        private Tipo tipo;

        public T_Puntero(Tipo tipo) {
            super();
            this.tipo = tipo;
        }

        public Tipo tipo() {
            return tipo;
        }

        public String toString() {
            return "t_puntero(" + tipo + ")";
        }

        @Override
        public void imprime() {
            System.out.println("^");
            tipo.imprime();
        }
    }

    public static class T_Struct extends Tipo {
        private CamposS camposS;

        public T_Struct(CamposS camposS) {
            super();
            this.camposS = camposS;
        }

        public CamposS camposS() {
            return camposS;
        }

        public String toString() {
            return "t_struct(" + camposS + ")";
        }

        @Override
        public void imprime() {
            System.out.println("<struct>");
            camposS.imprime();
        }
    }

    public static abstract class CamposS extends Nodo {
        public CamposS() {
            super();
        }

        public CamposS camposS() {
            throw new UnsupportedOperationException();
        }

        public CampoS campoS() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Mas_Cmp_S extends CamposS {
        private CamposS camposS;
        private CampoS campoS;

        public Mas_Cmp_S(CamposS camposS, CampoS campoS) {
            this.camposS = camposS;
            this.campoS = campoS;
        }

        public CamposS camposS() {
            return camposS;
        }

        public CampoS campoS() {
            return campoS;
        }

        public String toString() {
            return "mas_cmp_s(" + camposS + "," + campoS + ")";
        }

        @Override
        public void imprime() {
            camposS.imprime();
            campoS.imprime();
        }
    }

    public static class Un_Cmp_S extends CamposS {
        private CampoS campoS;

        public Un_Cmp_S(CampoS campoS) {
            this.campoS = campoS;
        }

        public CampoS campoS() {
            return campoS;
        }

        public String toString() {
            return "un_cmp_s(" + campoS + ")";
        }

        @Override
        public void imprime() {
            campoS.imprime();
        }
    }

    public static class CampoS extends Nodo {
        private Tipo tipo;
        private String id;

        public CampoS(Tipo tipo, String id) {
            this.tipo = tipo;
            this.id = id;
        }

        public Tipo tipo() {
            return tipo;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "campo_s(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println(" " + id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static abstract class IntrsOpt extends Nodo {
        public IntrsOpt() {
            super();
        }

        public LIntrs intrs() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Si_Intrs extends IntrsOpt {
        private LIntrs intrs;

        public Si_Intrs(LIntrs intrs) {
            super();
            this.intrs = intrs;
        }

        public LIntrs intrs() {
            return intrs;
        }

        public String toString() {
            return "si_intrs(" + intrs + ")";
        }

        @Override
        public void imprime() {
            intrs.imprime();
        }
    }

    public static class No_Intrs extends IntrsOpt {
        public No_Intrs() {
            super();
        }

        public String toString() {
            return "no_intrs()";
        }

        @Override
        public void imprime() {
            
        }
    }

    public static abstract class LIntrs extends Nodo {
        public LIntrs() {
            super();
        }

        public LIntrs intrs() {
            throw new UnsupportedOperationException();
        }

        public Intr intr() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Mas_Intrs extends LIntrs {
        private LIntrs intrs;
        private Intr intr;

        public Mas_Intrs(LIntrs intrs, Intr intr) {
            super();
            this.intrs = intrs;
            this.intr = intr;
        }

        public LIntrs intrs() {
            return intrs;
        }

        public Intr intr() {
            return intr;
        }

        public String toString() {
            return "mas_intrs(" + intrs + "," + intr + ")";
        }

        @Override
        public void imprime() {
            intrs.imprime();
            intr.imprime();
        }
    }

    public static class Una_Intr extends LIntrs {
        private Intr intr;

        public Una_Intr(Intr intr) {
            super();
            this.intr = intr;
        }

        public Intr intr() {
            return intr;
        }

        public String toString() {
            return "una_intr(" + intr + ")";
        }

        @Override
        public void imprime() {
            intr.imprime();
        }
    }

    public static abstract class Intr extends Nodo {
        public Intr() {
            super();
        }

        public String id() {
            throw new UnsupportedOperationException();
        }

        public PReals preals() {
            throw new UnsupportedOperationException();
        }

        public Exp exp() {
            throw new UnsupportedOperationException();
        }

        public Prog prog() {
            throw new UnsupportedOperationException();
        }

        public I_Else i_else() {
            throw new UnsupportedOperationException();
        }
    }

    public static class I_Eval extends Intr {
        private Exp exp;

        public I_Eval(Exp exp) {
            super();
            this.exp = exp;
        }

        public Exp exp() {
            return exp;
        }

        public String toString() {
            return "i_eval(" + exp + ")";
        }

        @Override
        public void imprime() {
            System.out.println("@");
            exp.imprime();
        }
    }

    public static class I_If extends Intr {
        private Exp exp;
        private Prog prog;
        private I_Else i_else;

        public I_If(Exp exp, Prog prog, I_Else i_else) {
            super();
            this.exp = exp;
            this.prog = prog;
            this.i_else = i_else;
        }

        public Exp exp() {
            return exp;
        }

        public Prog prog() {
            return prog;
        }

        public I_Else i_else() {
            return i_else;
        }

        public String toString() {
            return "i_if(" + exp + "," + prog + "," + i_else + ")";
        }

        @Override
        public void imprime() {
            System.out.println("<if>");
            exp.imprime();
            prog.imprime();
            i_else.imprime();
        }
    }

    public static class I_While extends Intr {
        private Exp exp;
        private Prog prog;

        public I_While(Exp exp, Prog prog) {
            super();
            this.exp = exp;
            this.prog = prog;
        }

        public Exp exp() {
            return exp;
        }

        public Prog prog() {
            return prog;
        }

        public String toString() {
            return "i_while(" + exp + "," + prog + ")";
        }

        @Override
        public void imprime() {
            System.out.println("<while>");
            exp.imprime();
            prog.imprime();
        }
    }

    public static class I_Read extends Intr {
        private Exp exp;

        public I_Read(Exp exp) {
            super();
            this.exp = exp;
        }

        public Exp exp() {
            return exp;
        }

        public String toString() {
            return "i_read(" + exp + ")";
        }

        @Override
        public void imprime() {
            System.out.println("<read>");
            exp.imprime();
        }
    }

    public static class I_Write extends Intr {
        private Exp exp;

        public I_Write(Exp exp) {
            super();
            this.exp = exp;
        }

        public Exp exp() {
            return exp;
        }

        public String toString() {
            return "i_write(" + exp + ")";
        }

        @Override
        public void imprime() {
            System.out.println("<write>");
            exp.imprime();
        }
    }

    public static class I_NL extends Intr {
        public I_NL() {
            super();
        }

        public String toString() {
            return "i_nl()";
        }

        @Override
        public void imprime() {
        }
    }

    public static class I_New extends Intr {
        private Exp exp;

        public I_New(Exp exp) {
            super();
            this.exp = exp;
        }

        public Exp exp() {
            return exp;
        }

        public String toString() {
            return "i_new(" + exp + ")";
        }

        @Override
        public void imprime() {
            System.out.println("<new>");
            exp.imprime();
        }
    }

    public static class I_Delete extends Intr {
        private Exp exp;

        public I_Delete(Exp exp) {
            super();
            this.exp = exp;
        }

        public Exp exp() {
            return exp;
        }

        public String toString() {
            return "i_delete(" + exp + ")";
        }

        @Override
        public void imprime() {
            System.out.println("<delete>");
            exp.imprime();
        }
    }

    public static class I_Call extends Intr {
        private String id;
        private PReals preals;

        public I_Call(String id, PReals preals) {
            super();
            this.id = id;
            this.preals = preals;
        }

        public String id() {
            return id;
        }

        public PReals preals() {
            return preals;
        }

        public String toString() {
            return "i_call(" + id + "[" + leeFila() + "," + leeCol() + "]," + preals + ")";
        }

        @Override
        public void imprime() {
            System.out.println("<call>");
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
            preals.imprime();
        }
    }

    public static class I_Prog extends Intr {
        private Prog prog;

        public I_Prog(Prog prog) {
            super();
            this.prog = prog;
        }

        public Prog prog() {
            return prog;
        }

        public String toString() {
            return "i_prog(" + prog + ")";
        }

        @Override
        public void imprime() {
            prog.imprime();
        }
    }

    public static abstract class I_Else extends Nodo {
        public I_Else() {
            super();
        }

        public Prog prog() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Si_Else extends I_Else {
        private Prog prog;

        public Si_Else(Prog prog) {
            super();
            this.prog = prog;
        }

        public Prog prog() {
            return prog;
        }

        public String toString() {
            return "si_else(" + prog + ")";
        }

        @Override
        public void imprime() {
            System.out.println("<else>");
            prog.imprime();
        }
    }

    public static class No_Else extends I_Else {
        public No_Else() {
            super();
        }

        public String toString() {
            return "no_else()";
        }

        @Override
        public void imprime() {
        }
    }

    public static abstract class PReals extends Nodo {
        public PReals() {
            super();
        }

        public LPReals preals() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Si_PReals extends PReals {
        private LPReals preals;

        public Si_PReals(LPReals preals) {
            super();
            this.preals = preals;
        }

        public LPReals preals() {
            return preals;
        }

        public String toString() {
            return "si_preals(" + preals + ")";
        }

        @Override
        public void imprime() {
            preals.imprime();
        }
    }

    public static class No_PReals extends PReals {
        public No_PReals() {
            super();
        }

        public String toString() {
            return "no_preals()";
        }

        @Override
        public void imprime() {
        }
    }

    public static abstract class LPReals extends Nodo {
        public LPReals() {
            super();
        }

        public LPReals preals() {
            throw new UnsupportedOperationException();
        }

        public String id() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Mas_PReals extends LPReals {
        private LPReals preals;
        private String id;

        public Mas_PReals(LPReals preals, String id) {
            super();
            this.preals = preals;
            this.id = id;
        }

        public LPReals preals() {
            return preals;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "mas_preals(" + preals + "," + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public void imprime() {
            preals.imprime();
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Un_PReal extends LPReals {
        private String id;

        public Un_PReal(String id) {
            super();
            this.id = id;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "un_preal(" + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public void imprime() {
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static abstract class Exp extends Nodo {
        public Exp() {
            super();
        }
       public abstract int prioridad();

        public Exp opnd0() {
            throw new UnsupportedOperationException();
        }

        public Exp opnd1() {
            throw new UnsupportedOperationException();
        }

        public String string() {
            throw new UnsupportedOperationException();
        }

        public String num() {
            throw new UnsupportedOperationException();
        }

        public String id() {
            throw new UnsupportedOperationException();
        }
    }

    private static abstract class ExpUni extends Exp {
        protected Exp opnd;

        public ExpUni(Exp opnd) {
            super();
            this.opnd = opnd;
        }

        public Exp opnd0() {
            return opnd;
        }
    }

    private static abstract class ExpBin extends Exp {
        protected Exp opnd0;
        protected Exp opnd1;

        public ExpBin(Exp opnd0, Exp opnd1) {
            super();
            this.opnd0 = opnd0;
            this.opnd1 = opnd1;
        }

        public Exp opnd0() {
            return opnd0;
        }

        public Exp opnd1() {
            return opnd1;
        }
    }

    public static class Asig extends ExpBin {
        public Asig(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "asig(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 0;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "=", opnd1, 1, 0);
        }
    }

    public static class Comp extends ExpBin {
        public Comp(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "comp(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 1;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "==", opnd1, 1, 2);
        }
    }

    public static class Dist extends ExpBin {
        public Dist(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "dist(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 1;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "!=", opnd1, 1, 2);
        }
    }

    public static class Menor extends ExpBin {
        public Menor(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "menor(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 1;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "<", opnd1, 1, 2);
        }
    }

    public static class Mayor extends ExpBin {
        public Mayor(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "mayor(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 1;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, ">", opnd1, 1, 2);
        }
    }

    public static class MenorIgual extends ExpBin {
        public MenorIgual(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "menorIgual(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 1;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "<=", opnd1, 1, 2);
        }
    }

    public static class MayorIgual extends ExpBin {
        public MayorIgual(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "mayorIgual(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 1;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, ">=", opnd1, 1, 2);
        }
    }

    public static class Suma extends ExpBin {
        public Suma(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "suma(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 2;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "+", opnd1, 2, 3);
        }
    }

    public static class Resta extends ExpBin {
        public Resta(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "resta(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 2;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "-", opnd1, 3, 3);
        }
    }

    public static class And extends ExpBin {
        public And(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "and(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 3;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "and", opnd1, 4, 3);
        }
    }

    public static class Or extends ExpBin {
        public Or(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "or(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 3;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "or", opnd1, 4, 4);
        }
    }

    public static class Mul extends ExpBin {
        public Mul(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "mul(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 4;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "*", opnd1, 4, 5);
        }
    }

    public static class Div extends ExpBin {
        public Div(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "div(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 4;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "/", opnd1, 4, 5);
        }
    }

    public static class Porcentaje extends ExpBin {
        public Porcentaje(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "porcentaje(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 4;
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "%", opnd1, 4, 5);
        }
    }

    public static class Negativo extends ExpUni {
        public Negativo(Exp opnd) {
            super(opnd);
        }

        public String toString() {
            return "negativo(" + opnd + ")";
        }

        @Override
        public int prioridad() {
            return 5;
        }

        @Override
        public void imprime() {
            imprimeExpUni("-", opnd, 5);
            
        }
    }

    public static class Negado extends ExpUni {
        public Negado(Exp opnd) {
            super(opnd);
        }

        public String toString() {
            return "negado(" + opnd + ")";
        }

        @Override
        public int prioridad() {
            return 5;
        }

        @Override
        public void imprime() {
            imprimeExpUni("not", opnd, 5);
        }
    }

    public static class Index extends ExpBin {
        public Index(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        public String toString() {
            return "index(" + opnd0 + "," + opnd1 + ")";
        }

        @Override
        public int prioridad() {
            return 6;
        }

        @Override
        public void imprime() {
            imprimeExpUni("[", opnd0, 6);
            imprimeExpUni("]", opnd1, 6);
        }
    }

    public static class Acceso extends Exp {
        private Exp opnd;
        private String id;

        public Acceso(Exp opnd, String id) {
            super();
            this.opnd = opnd;
            this.id = id;
        }

        public Exp opnd0() {
            return opnd;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "acceso(" + opnd + "," + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public int prioridad() {
            return 6;
        }

        @Override
        public void imprime() {
            imprimeExpUni(".", opnd, 6);
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Indireccion extends ExpUni {
        public Indireccion(Exp opnd) {
            super(opnd);
        }

        public String toString() {
            return "indireccion(" + opnd + ")";
        }

        @Override
        public int prioridad() {
            return 6;
        }

        @Override
        public void imprime() {
            imprimeExpUni("^", opnd, 6);
        }
    }

    public static class Lit_ent extends Exp {
        private String num;

        public Lit_ent(String num) {
            super();
            this.num = num;
        }

        public String num() {
            return num;
        }

        public String toString() {
            return "lit_ent(" + num + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public int prioridad() {
            return 7;
        }

        @Override
        public void imprime() {
            System.out.println(num + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Lit_real extends Exp {
        private String num;

        public Lit_real(String num) {
            super();
            this.num = num;
        }

        public String num() {
            return num;
        }

        public String toString() {
            return "lit_real(" + num + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public int prioridad() {
            return 7;
        }

        @Override
        public void imprime() {
            System.out.println(num + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class True extends Exp {
        public True() {
            super();
        }

        public String toString() {
            return "true([" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public int prioridad() {
            return 7;
        }

        @Override
        public void imprime() {
            System.out.println("<true>");
        }
    }

    public static class False extends Exp {
        public False() {
            super();
        }

        public String toString() {
            return "false([" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public int prioridad() {
            return 7;
        }

        @Override
        public void imprime() {
            System.out.println("<false>");
        }
    }

    public static class Cadena extends Exp {
        private String string;

        public Cadena(String string) {
            super();
            this.string = string;
        }

        public String string() {
            return string;
        }

        public String toString() {
            return "iden(" + string + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public int prioridad() {
            return 7;
        }

        @Override
        public void imprime() {
            System.out.println(string + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Iden extends Exp {
        private String id;

        public Iden(String id) {
            super();
            this.id = id;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "iden(" + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public int prioridad() {
            return 7;
        }

        @Override
        public void imprime() {
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Null extends Exp {
        public Null() {
            super();
        }

        public String toString() {
            return "null([" + leeFila() + "," + leeCol() + "])";
        }

        @Override
        public int prioridad() {
            return 7;
        }

        @Override
        public void imprime() {
            System.out.println("<null>");
        }
    }

    // Constructoras
    public Prog prog(DecsOpt decs, IntrsOpt intrs) {
        return new Prog(decs, intrs);
    }

    public DecsOpt si_decs(LDecs decs) {
        return new Si_Decs(decs);
    }

    public DecsOpt no_decs() {
        return new No_Decs();
    }

    public LDecs mas_decs(LDecs decs, Dec dec) {
        return new Mas_Decs(decs, dec);
    }

    public LDecs una_dec(Dec dec) {
        return new Una_Dec(dec);
    }

    public Dec dec_var(Tipo tipo, String id) {
        return new Dec_Var(tipo, id);
    }

    public Dec dec_tipo(Tipo tipo, String id) {
        return new Dec_Tipo(tipo, id);
    }

    public Dec dec_proc(String id, PForms pforms, Prog prog) {
        return new Dec_Proc(id, pforms, prog);
    }

    public PForms si_pforms(LPForms pfroms) {
        return new Si_PForms(pfroms);
    }

    public PForms no_pforms() {
        return new No_PForms();
    }

    public LPForms mas_pfroms(LPForms pforms, PForm pform) {
        return new Mas_PForms(pforms, pform);
    }

    public LPForms una_pform(PForm pform) {
        return new Una_PForm(pform);
    }

    public PForm pform(Tipo tipo, Ref ref, String id) {
        return new PForm(tipo, ref, id);
    }

    public Ref si_ref() {
        return new Si_Ref();
    }

    public Ref no_ref() {
        return new No_Ref();
    }

    public Tipo t_iden(String id) {
        return new T_Iden(id);
    }

    public Tipo t_string() {
        return new T_String();
    }

    public Tipo t_int() {
        return new T_Int();
    }

    public Tipo t_bool() {
        return new T_Bool();
    }

    public Tipo t_real() {
        return new T_Real();
    }

    public Tipo t_array(Tipo tipo, String ent) {
        return new T_Array(tipo, ent);
    }

    public Tipo t_punter(Tipo tipo) {
        return new T_Puntero(tipo);
    }

    public Tipo t_struct(CamposS camposs) {
        return new T_Struct(camposs);
    }

    public CamposS mas_cmp_s(CamposS camposs, CampoS campos) {
        return new Mas_Cmp_S(camposs, campos);
    }

    public CamposS un_cmp_s(CampoS campos) {
        return new Un_Cmp_S(campos);
    }

    public CampoS cmp_s(Tipo tipo, String id) {
        return new CampoS(tipo, id);
    }

    public IntrsOpt si_intrs(LIntrs intrs) {
        return new Si_Intrs(intrs);
    }

    public IntrsOpt no_intrs() {
        return new No_Intrs();
    }

    public LIntrs mas_intrs(LIntrs intrs, Intr intr) {
        return new Mas_Intrs(intrs, intr);
    }

    public LIntrs una_intr(Intr intr) {
        return new Una_Intr(intr);
    }

    public Intr i_eval(Exp exp) {
        return new I_Eval(exp);
    }

    public Intr i_if(Exp exp, Prog prog, I_Else ielse) {
        return new I_If(exp, prog, ielse);
    }

    public Intr i_while(Exp exp, Prog prog) {
        return new I_While(exp, prog);
    }

    public Intr i_read(Exp exp) {
        return new I_Read(exp);
    }

    public Intr i_write(Exp exp) {
        return new I_Write(exp);
    }

    public Intr i_ln() {
        return new I_NL();
    }

    public Intr i_new(Exp exp) {
        return new I_New(exp);
    }

    public Intr i_delete(Exp exp) {
        return new I_Delete(exp);
    }

    public Intr i_call(String id, PReals preals) {
        return new I_Call(id, preals);
    }

    public Intr i_prog(Prog prog) {
        return new I_Prog(prog);
    }

    public I_Else si_else(Prog prog) {
        return new Si_Else(prog);
    }

    public I_Else no_else() {
        return new No_Else();
    }

    public PReals si_preals(LPReals preals) {
        return new Si_PReals(preals);
    }

    public PReals no_preals() {
        return new No_PReals();
    }

    public LPReals mas_preals(LPReals preals, String id) {
        return new Mas_PReals(preals, id);
    }

    public LPReals un_preals(String id) {
        return new Un_PReal(id);
    }

    public Exp e_asig(Exp opnd0, Exp opnd1) {
        return new Asig(opnd0, opnd1);
    }

    public Exp e_comp(Exp opnd0, Exp opnd1) {
        return new Comp(opnd0, opnd1);
    }

    public Exp e_dist(Exp opnd0, Exp opnd1) {
        return new Dist(opnd0, opnd1);
    }

    public Exp e_lt(Exp opnd0, Exp opnd1) {
        return new Menor(opnd0, opnd1);
    }

    public Exp e_gt(Exp opnd0, Exp opnd1) {
        return new Mayor(opnd0, opnd1);
    }

    public Exp e_leq(Exp opnd0, Exp opnd1) {
        return new MenorIgual(opnd0, opnd1);
    }

    public Exp e_geq(Exp opnd0, Exp opnd1) {
        return new MayorIgual(opnd0, opnd1);
    }

    public Exp e_suma(Exp opnd0, Exp opnd1) {
        return new Suma(opnd0, opnd1);
    }

    public Exp e_resta(Exp opnd0, Exp opnd1) {
        return new Resta(opnd0, opnd1);
    }

    public Exp e_and(Exp opnd0, Exp opnd1) {
        return new And(opnd0, opnd1);
    }

    public Exp e_or(Exp opnd0, Exp opnd1) {
        return new Or(opnd0, opnd1);
    }

    public Exp e_mul(Exp opnd0, Exp opnd1) {
        return new Mul(opnd0, opnd1);
    }

    public Exp e_div(Exp opnd0, Exp opnd1) {
        return new Div(opnd0, opnd1);
    }

    public Exp e_porcentaje(Exp opnd0, Exp opnd1) {
        return new Porcentaje(opnd0, opnd1);
    }

    public Exp e_negativo(Exp opnd) {
        return new Negativo(opnd);
    }

    public Exp e_negado(Exp opnd) {
        return new Negado(opnd);
    }

    public Exp e_indexado(Exp opnd0, Exp opnd1) {
        return new Index(opnd0, opnd0);
    }

    public Exp e_campo(Exp opnd, String id) {
        return new Acceso(opnd, id);
    }

    public Exp e_puntero(Exp opnd) {
        return new Indireccion(opnd);
    }

    public Exp e_lit_ent(String num) {
        return new Lit_ent(num);
    }

    public Exp e_lit_real(String num) {
        return new Lit_real(num);
    }

    public Exp e_true() {
        return new True();
    }

    public Exp e_false() {
        return new False();
    }

    public Exp e_string(String string) {
        return new Cadena(string);
    }

    public Exp e_iden(String id) {
        return new Iden(id);
    }

    public Exp e_null() {
        return new Null();
    }
}
