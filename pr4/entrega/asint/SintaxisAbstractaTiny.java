package asint;

public class SintaxisAbstractaTiny {
    private static void imprimeOpnd(Exp opnd, int np) {
        if (opnd.prioridad() < np) {
            System.out.println("(");
        }
        ;
        opnd.imprime();
        if (opnd.prioridad() < np) {
            System.out.println(")");
        }
        ;
    }

    private static void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1, int i, int j) {
        imprimeOpnd(opnd0, np0);
        System.out.println(op + "$f:" + i + ",c:" + j + "$");
        imprimeOpnd(opnd1, np1);
    }

    public static abstract class Nodo {
        public Nodo() {
            fila = col = -1;
        }

        protected Tipo tipo;

        private int fila;

        private int col;

        private int tam;

        private int dir;

        private int nivel;

        private int sig;

        private int prim;

        private int fin;

        private Nodo vinculo = null;

        public Nodo ponFila(int fila) {
            this.fila = fila;
            return this;
        }

        public Nodo ponCol(int col) {
            this.col = col;
            return this;
        }

        public Nodo ponVinculo(Nodo vinculo) {
            this.vinculo = vinculo;
            return this;
        }

        public Nodo ponTipo(Tipo tipo) {
            this.tipo = tipo;
            return this;
        }

        public Nodo ponTam(int tam) {
            this.tam = tam;
            return this;
        }

        public Nodo ponDir(int dir) {
            this.dir = dir;
            return this;
        }

        public Nodo ponNivel(int nivel) {
            this.nivel = nivel;
            return this;
        }

        public Nodo ponSig(int sig) {
            this.sig = sig;
            return this;
        }

        public Nodo ponPrim(int prim) {
            this.prim = prim;
            return this;
        }

        public Nodo ponFin(int fin) {
            this.fin = fin;
            return this;
        }

        public int fin() {
            return this.fin;
        }

        public int sig() {
            return this.sig;
        }

        public int prim() {
            return this.prim;
        }

        public int nivel() {
            return this.nivel;
        }

        public int dir() {
            return this.dir;
        }

        public int tam() {
            return this.tam;
        }

        public Tipo tipo() {
            return tipo;
        }

        public int leeFila() {
            return fila;
        }

        public int leeCol() {
            return col;
        }

        public Nodo vinculo() {
            return this.vinculo;
        }

        public abstract void procesa(Procesamiento p);

        public abstract void procesa2(Procesamiento p);

        public abstract void imprime();
    }

    public static class Prog extends Nodo {

        private Bloque bloque;

        public Prog(Bloque bloque) {
            super();
            this.bloque = bloque;
        }

        public String toString() {
            return "prog(" + bloque + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        public Bloque bloque() {
            return this.bloque;
        }

        @Override
        public void imprime() {
            bloque.imprime();
        }

        @Override
        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }
    }

    public static class Bloque extends Nodo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private DecsOpt decs;
        private IntrsOpt intrs;

        public Bloque(DecsOpt decs, IntrsOpt intrs) {
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
            return "bloque(" + decs + "," + intrs + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
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

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            decs.imprime();
            System.out.println("&&");
        }
    }

    public static class No_Decs extends DecsOpt {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public No_Decs() {
            super();
        }

        public String toString() {
            return "no_decs()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
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

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            decs.imprime();
            System.out.println(";");
            dec.imprime();
        }
    }

    public static class Una_Dec extends LDecs {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
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

        public String id() {
            throw new UnsupportedOperationException();
        }

        public PForms pforms() {
            throw new UnsupportedOperationException();
        }

        public Bloque prog() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Dec_Var extends Dec {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private String id;

        public Dec_Var(Tipo tipo, String id) {
            super();
            this.tipo = tipo;
            this.id = id;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "dec_var(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Dec_Tipo extends Dec {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private String id;

        public Dec_Tipo(Tipo tipo, String id) {
            super();
            this.tipo = tipo;
            this.id = id;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "dec_tipo(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<type>");
            tipo.imprime();
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Dec_Proc extends Dec {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private String id;
        private PForms pforms;
        private Bloque prog;

        public Dec_Proc(String id, PForms pforms, Bloque prog) {
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

        public Bloque prog() {
            return prog;
        }

        public String toString() {
            return "dec_proc(" + id + "[" + leeFila() + "," + leeCol() + "]," + pforms + prog + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<proc>\n" + id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
            System.out.println("(");
            pforms.imprime();
            System.out.println(")");
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

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            pforms.imprime();
        }
    }

    public static class No_PForms extends PForms {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public No_PForms() {
            super();
        }

        public String toString() {
            return "no_pforms()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
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

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            pforms.imprime();
            System.out.println(",");
            pform.imprime();
        }
    }

    public static class Una_PForm extends LPForms {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            pform.imprime();
        }
    }

    public static class PForm extends Nodo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private Ref ref;
        private String id;

        public PForm(Tipo tipo, Ref ref, String id) {
            super();
            this.tipo = tipo;
            this.ref = ref;
            this.id = id;
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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            tipo.imprime();
            ref.imprime();
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static abstract class Ref extends Nodo {
        public Ref() {
            super();
        }
    }

    public static class Si_Ref extends Ref {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Si_Ref() {
            super();
        }

        public String toString() {
            return "si_ref()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("&");
        }
    }

    public static class No_Ref extends Ref {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public No_Ref() {
            super();
        }

        public String toString() {
            return "no_ref()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
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

        public String ent() {
            throw new UnsupportedOperationException();
        }
    }

    public static class T_Ok extends Tipo {

        @Override
        public void procesa(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa'");
        }

        @Override
        public void procesa2(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
        }

        @Override
        public void imprime() {
            throw new UnsupportedOperationException("Unimplemented method 'imprime'");
        }
    }

    public static class T_Error extends Tipo {

        @Override
        public void procesa(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa'");
        }

        @Override
        public void procesa2(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
        }

        @Override
        public void imprime() {
            throw new UnsupportedOperationException("Unimplemented method 'imprime'");
        }
    }

    public static class T_Null extends Tipo {

        @Override
        public void procesa(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa'");
        }

        @Override
        public void procesa2(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
        }

        @Override
        public void imprime() {
            throw new UnsupportedOperationException("Unimplemented method 'imprime'");
        }
    }

    public static class T_Iden extends Tipo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class T_String extends Tipo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public T_String() {
            super();
        }

        public String toString() {
            return "t_string()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<string>");
        }
    }

    public static class T_Int extends Tipo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public T_Int() {
            super();
        }

        public String toString() {
            return "t_int()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<int>");
        }
    }

    public static class T_Bool extends Tipo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public T_Bool() {
            super();
        }

        public String toString() {
            return "t_bool()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<bool>");
        }
    }

    public static class T_Real extends Tipo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public T_Real() {
            super();
        }

        public String toString() {
            return "t_real()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<real>");
        }
    }

    public static class T_Array extends Tipo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private String ent;

        public T_Array(Tipo tipo, String ent) {
            super();
            this.tipo = tipo;
            this.ent = ent;
        }

        public String ent() {
            return ent;
        }

        public String toString() {
            return "t_array(" + tipo + "," + ent + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.printf("[\n" + ent + "\n]$f:" + leeFila() + ",c:" + leeCol() + "$\n");
        }
    }

    public static class T_Puntero extends Tipo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public T_Puntero(Tipo tipo) {
            super();
            this.tipo = tipo;
        }

        public String toString() {
            return "t_puntero(" + tipo + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("^");
            tipo.imprime();
        }
    }

    public static class T_Struct extends Tipo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<struct>");
            System.out.println("{");
            camposS.imprime();
            System.out.println("}");
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

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            camposS.imprime();
            System.out.println(",");
            campoS.imprime();
        }
    }

    public static class Un_Cmp_S extends CamposS {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            campoS.imprime();
        }
    }

    public static class CampoS extends Nodo {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private String id;

        public CampoS(Tipo tipo, String id) {
            this.tipo = tipo;
            this.id = id;
        }

        public String id() {
            return id;
        }

        public String toString() {
            return "campo_s(" + tipo + "," + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
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

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            intrs.imprime();
        }
    }

    public static class No_Intrs extends IntrsOpt {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public No_Intrs() {
            super();
        }

        public String toString() {
            return "no_intrs()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
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

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            intrs.imprime();
            System.out.println(";");
            intr.imprime();
        }
    }

    public static class Una_Intr extends LIntrs {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
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

        public Bloque prog() {
            throw new UnsupportedOperationException();
        }

        public I_Else i_else() {
            throw new UnsupportedOperationException();
        }
    }

    public static class I_Eval extends Intr {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("@");
            exp.imprime();
        }
    }

    public static class I_If extends Intr {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private Exp exp;
        private Bloque prog;
        private I_Else i_else;

        public I_If(Exp exp, Bloque prog, I_Else i_else) {
            super();
            this.exp = exp;
            this.prog = prog;
            this.i_else = i_else;
        }

        public Exp exp() {
            return exp;
        }

        public Bloque prog() {
            return prog;
        }

        public I_Else i_else() {
            return i_else;
        }

        public String toString() {
            return "i_if(" + exp + "," + prog + "," + i_else + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
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

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private Exp exp;
        private Bloque prog;

        public I_While(Exp exp, Bloque prog) {
            super();
            this.exp = exp;
            this.prog = prog;
        }

        public Exp exp() {
            return exp;
        }

        public Bloque prog() {
            return prog;
        }

        public String toString() {
            return "i_while(" + exp + "," + prog + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<while>");
            exp.imprime();
            prog.imprime();
        }
    }

    public static class I_Read extends Intr {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<read>");
            exp.imprime();
        }
    }

    public static class I_Write extends Intr {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<write>");
            exp.imprime();
        }
    }

    public static class I_NL extends Intr {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public I_NL() {
            super();
        }

        public String toString() {
            return "i_nl()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<nl>");
        }
    }

    public static class I_New extends Intr {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<new>");
            exp.imprime();
        }
    }

    public static class I_Delete extends Intr {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<delete>");
            exp.imprime();
        }
    }

    public static class I_Call extends Intr {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<call>");
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
            preals.imprime();
        }
    }

    public static class I_Prog extends Intr {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private Bloque prog;

        public I_Prog(Bloque prog) {
            super();
            this.prog = prog;
        }

        public Bloque prog() {
            return prog;
        }

        public String toString() {
            return "i_prog(" + prog + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
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

        public Bloque prog() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Si_Else extends I_Else {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private Bloque prog;

        public Si_Else(Bloque prog) {
            super();
            this.prog = prog;
        }

        public Bloque prog() {
            return prog;
        }

        public String toString() {
            return "si_else(" + prog + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<else>");
            prog.imprime();
        }
    }

    public static class No_Else extends I_Else {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public No_Else() {
            super();
        }

        public String toString() {
            return "no_else()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
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

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("(");
            preals.imprime();
            System.out.println(")");
        }
    }

    public static class No_PReals extends PReals {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public No_PReals() {
            super();
        }

        public String toString() {
            return "no_preals()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("(");
            System.out.println(")");
        }
    }

    public static abstract class LPReals extends Nodo {
        public LPReals() {
            super();
        }

        public LPReals preals() {
            throw new UnsupportedOperationException();
        }

        public Exp exp() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Mas_PReals extends LPReals {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private LPReals preals;
        private Exp exp;

        public Mas_PReals(LPReals preals, Exp exp) {
            super();
            this.preals = preals;
            this.exp = exp;
        }

        public LPReals preals() {
            return preals;
        }

        public Exp exp() {
            return exp;
        }

        public String toString() {
            return "mas_preals(" + preals + "," + exp + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            preals.imprime();
            System.out.println(",");
            exp.imprime();
        }
    }

    public static class Un_PReal extends LPReals {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private Exp exp;

        public Un_PReal(Exp exp) {
            super();
            this.exp = exp;
        }

        public Exp exp() {
            return exp;
        }

        public String toString() {
            return "un_preal(" + exp + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            exp.imprime();
        }
    }

    public static abstract class Exp extends Nodo {
        public Exp() {
            super();
        }

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

        public abstract int prioridad();
    }

    public static abstract class ExpUni extends Exp {
        protected Exp opnd;

        public ExpUni(Exp opnd) {
            super();
            this.opnd = opnd;
        }

        public Exp opnd0() {
            return opnd;
        }
    }

    public static abstract class ExpBin extends Exp {
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

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Asig(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 0;
        }

        public String toString() {
            return "asig(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "=", opnd1, 1, 0, leeFila(), leeCol());
        }
    }

    public static class Comp extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Comp(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 1;
        }

        public String toString() {
            return "comp(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "==", opnd1, 1, 2, leeFila(), leeCol());
        }
    }

    public static class Dist extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Dist(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 1;
        }

        public String toString() {
            return "dist(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "!=", opnd1, 1, 2, leeFila(), leeCol());
        }
    }

    public static class Menor extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Menor(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 1;
        }

        public String toString() {
            return "menor(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "<", opnd1, 1, 2, leeFila(), leeCol());
        }
    }

    public static class Mayor extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Mayor(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 1;
        }

        public String toString() {
            return "mayor(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, ">", opnd1, 1, 2, leeFila(), leeCol());
        }
    }

    public static class MenorIgual extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public MenorIgual(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 1;
        }

        public String toString() {
            return "menorIgual(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "<=", opnd1, 1, 2, leeFila(), leeCol());
        }
    }

    public static class MayorIgual extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public MayorIgual(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 1;
        }

        public String toString() {
            return "mayorIgual(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, ">=", opnd1, 1, 2, leeFila(), leeCol());
        }
    }

    public static class Suma extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Suma(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 2;
        }

        public String toString() {
            return "suma(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "+", opnd1, 2, 3, leeFila(), leeCol());
        }
    }

    public static class Resta extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Resta(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 2;
        }

        public String toString() {
            return "resta(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "-", opnd1, 3, 3, leeFila(), leeCol());
        }
    }

    public static class And extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public And(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 3;
        }

        public String toString() {
            return "and(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "<and>", opnd1, 4, 3, leeFila(), leeCol());
        }
    }

    public static class Or extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Or(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 3;
        }

        public String toString() {
            return "or(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "<or>", opnd1, 4, 4, leeFila(), leeCol());
        }
    }

    public static class Mul extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Mul(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 4;
        }

        public String toString() {
            return "mul(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "*", opnd1, 4, 5, leeFila(), leeCol());
        }
    }

    public static class Div extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Div(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 4;
        }

        public String toString() {
            return "div(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "/", opnd1, 4, 5, leeFila(), leeCol());
        }
    }

    public static class Porcentaje extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Porcentaje(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 4;
        }

        public String toString() {
            return "porcentaje(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0, "%", opnd1, 4, 5, leeFila(), leeCol());
        }
    }

    public static class Negativo extends ExpUni {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Negativo(Exp opnd) {
            super(opnd);
        }

        @Override
        public int prioridad() {
            return 5;
        }

        public String toString() {
            return "negativo(" + opnd + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("-$f:" + leeFila() + ",c:" + leeCol() + "$");
            imprimeOpnd(opnd, 5);

        }
    }

    public static class Negado extends ExpUni {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Negado(Exp opnd) {
            super(opnd);
        }

        @Override
        public int prioridad() {
            return 5;
        }

        public String toString() {
            return "negado(" + opnd + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<not>$f:" + leeFila() + ",c:" + leeCol() + "$");
            imprimeOpnd(opnd, 5);
        }
    }

    public static class Index extends ExpBin {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Index(Exp opnd0, Exp opnd1) {
            super(opnd0, opnd1);
        }

        @Override
        public int prioridad() {
            return 6;
        }

        public String toString() {
            return "index(" + opnd0 + "," + opnd1 + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeOpnd(opnd0, 6);
            System.out.println("[$f:" + leeFila() + ",c:" + leeCol() + "$");
            imprimeOpnd(opnd1, 0);
            System.out.println("]");
        }
    }

    public static class Acceso extends Exp {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

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

        @Override
        public int prioridad() {
            return 6;
        }

        public String toString() {
            return "acceso(" + opnd + "," + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeOpnd(opnd, 6);
            System.out.println(".");
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Indireccion extends ExpUni {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Indireccion(Exp opnd) {
            super(opnd);
        }

        @Override
        public int prioridad() {
            return 6;
        }

        public String toString() {
            return "indireccion(" + opnd + ")";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeOpnd(opnd, 6);
            System.out.println("^$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Lit_ent extends Exp {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private String num;

        public Lit_ent(String num) {
            super();
            this.num = num;
        }

        public String num() {
            return num;
        }

        @Override
        public int prioridad() {
            return 7;
        }

        public String toString() {
            return "lit_ent(" + num + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println(num + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Lit_real extends Exp {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private String num;

        public Lit_real(String num) {
            super();
            this.num = num;
        }

        public String num() {
            return num;
        }

        @Override
        public int prioridad() {
            return 7;
        }

        public String toString() {
            return "lit_real(" + num + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println(num + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class True extends Exp {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public True() {
            super();
        }

        @Override
        public int prioridad() {
            return 7;
        }

        public String toString() {
            return "true([" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<true>" + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class False extends Exp {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public False() {
            super();
        }

        @Override
        public int prioridad() {
            return 7;
        }

        public String toString() {
            return "false([" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<false>" + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Cadena extends Exp {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private String string;

        public Cadena(String string) {
            super();
            this.string = string;
        }

        public String string() {
            return string;
        }

        @Override
        public int prioridad() {
            return 7;
        }

        public String toString() {
            return "iden(" + string + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println(string + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Iden extends Exp {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        private String id;

        public Iden(String id) {
            super();
            this.id = id;
        }

        public String id() {
            return id;
        }

        @Override
        public int prioridad() {
            return 7;
        }

        public String toString() {
            return "iden(" + id + "[" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    public static class Null extends Exp {

        public void procesa2(Procesamiento p) {
            p.procesa2(this);
        }

        public Null() {
            super();
        }

        @Override
        public int prioridad() {
            return 7;
        }

        public String toString() {
            return "null([" + leeFila() + "," + leeCol() + "])";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<null>" + "$f:" + leeFila() + ",c:" + leeCol() + "$");
        }
    }

    // Constructoras
    public Prog prog(Bloque bloque) {
        return new Prog(bloque);
    }

    public Bloque bloque(DecsOpt decs, IntrsOpt intrs) {
        return new Bloque(decs, intrs);
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

    public Dec dec_proc(String id, PForms pforms, Bloque prog) {
        return new Dec_Proc(id, pforms, prog);
    }

    public PForms si_pforms(LPForms pfroms) {
        return new Si_PForms(pfroms);
    }

    public PForms no_pforms() {
        return new No_PForms();
    }

    public LPForms mas_pforms(LPForms pforms, PForm pform) {
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

    public Tipo t_ok() {
        return new T_Ok();
    }

    public Tipo t_error() {
        return new T_Error();
    }

    public Tipo t_null() {
        return new T_Null();
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

    public Intr i_if(Exp exp, Bloque prog, I_Else ielse) {
        return new I_If(exp, prog, ielse);
    }

    public Intr i_while(Exp exp, Bloque prog) {
        return new I_While(exp, prog);
    }

    public Intr i_read(Exp exp) {
        return new I_Read(exp);
    }

    public Intr i_write(Exp exp) {
        return new I_Write(exp);
    }

    public Intr i_nl() {
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

    public Intr i_prog(Bloque prog) {
        return new I_Prog(prog);
    }

    public I_Else si_else(Bloque prog) {
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

    public LPReals mas_preals(LPReals preals, Exp exp) {
        return new Mas_PReals(preals, exp);
    }

    public LPReals un_preals(Exp exp) {
        return new Un_PReal(exp);
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
        return new Index(opnd0, opnd1);
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
