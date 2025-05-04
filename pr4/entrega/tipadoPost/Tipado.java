package tipadoPost;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import errores_procesamiento.ErrorProcesamiento;
import errores_procesamiento.ErrorTipado;

public class Tipado extends ProcesamientoDef {

    public Tipado tipar(Prog prog) {
        prog.procesa(this);
        return this;
    }

    private static class Compatibilizador {

        private static Set<ParTipos> yaCompatibles;

        private static class ParTipos {

            Tipo t0, t1;

            protected ParTipos(Tipo t0, Tipo t1) {
                this.t0 = t0;
                this.t1 = t1;
            }

            @Override
            public int hashCode() {
                return Objects.hash(t0, t1);
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj)
                    return true;
                if (obj == null)
                    return false;
                if (getClass() != obj.getClass())
                    return false;
                ParTipos other = (ParTipos) obj;
                if (t0 == null) {
                    if (other.t0 != null)
                        return false;
                } else if (!t0.equals(other.t0))
                    return false;
                if (t1 == null) {
                    if (other.t1 != null)
                        return false;
                } else if (!t1.equals(other.t1))
                    return false;
                return true;
            }

        }

        protected static boolean compatibles(Tipo t0, Tipo t1) {
            yaCompatibles = new HashSet<>();
            yaCompatibles.add(new ParTipos(t0, t1));
            return unificables(t0, t1);
        }

        private static boolean unificables(Tipo t0, Tipo t1) {
            Tipo t0p = refenciar(t0), t1p = refenciar(t1);

            if ((claseDe(t0p, T_Int.class) && claseDe(t1p, T_Int.class)) ||
                    (claseDe(t0p, T_Real.class) && (claseDe(t1p, T_Int.class) || claseDe(t1p, T_Real.class))) ||
                    (claseDe(t0p, T_Bool.class) && claseDe(t1p, T_Bool.class)) ||
                    (claseDe(t0p, T_String.class) && claseDe(t1p, T_String.class)))
                return true;
            else if (claseDe(t0p, T_Array.class) && claseDe(t1p, T_Array.class))
                return Integer.parseInt(t0p.ent()) == Integer.parseInt(t1p.ent())
                        && son_unificables(t0p.tipo(), t1p.tipo());
            else if (claseDe(t0p, T_Struct.class) && claseDe(t1p, T_Struct.class))
                return n_campos(t0p.camposS()) == n_campos(t1p.camposS())
                        && son_campos_unificables(t0p.camposS(), t1p.camposS());
            else if (claseDe(t0p, T_Puntero.class) && claseDe(t1p, T_Null.class))
                return true;
            else if (claseDe(t0p, T_Puntero.class) && claseDe(t1p, T_Puntero.class))
                return son_unificables(t0p.tipo(), t1p.tipo());
            else
                return false;
        }

        private static boolean son_unificables(Tipo t0, Tipo t1) {
            if (yaCompatibles.contains(new ParTipos(t0, t1))) {
                return true;
            }
            yaCompatibles.add(new ParTipos(t0, t1));
            return unificables(t0, t1);
        }

        private static int n_campos(CamposS camposs) {
            if (claseDe(camposs, Mas_Cmp_S.class))
                return 1 + n_campos(camposs.camposS());
            else
                return 1;
        }

        private static boolean son_campos_unificables(CamposS camposs0, CamposS camposs1) {
            boolean sonRestoUnificables = true;
            if (claseDe(camposs0, Mas_Cmp_S.class))
                sonRestoUnificables = son_campos_unificables(camposs0.camposS(), camposs1.camposS());
            return sonRestoUnificables && son_campos_unificables(camposs0.campoS(), camposs1.campoS());
        }

        private static boolean son_campos_unificables(CampoS campos0, CampoS campos1) {
            return son_unificables(refenciar(campos0.tipo()), refenciar(campos1.tipo()));
        }
    }

    private List<ErrorProcesamiento> errorProcesamientos = new ArrayList<>();

    public boolean hayErrores() {
        return errorProcesamientos.size() > 0;
    }

    public Collection<ErrorProcesamiento> errores() {
        return errorProcesamientos;
    }

    private boolean asignable(Nodo exp) {
        return (claseDe(exp, Iden.class) || claseDe(exp, Acceso.class) || claseDe(exp, Index.class)
                || claseDe(exp, Indireccion.class));
    }

    private Tipo ambos_ok(Tipo t0, Tipo t1) {
        if (claseDe(t0, T_Ok.class) && claseDe(t1, T_Ok.class))
            return new T_Ok();
        else
            return new T_Error();
    }

    private void aviso_error(Tipo t0, Tipo t1, ErrorTipado error) {
        if (!claseDe(t0, T_Error.class) && !claseDe(t1, T_Error.class)) {
            errorProcesamientos.add(error);
        }
    }

    public static Tipo refenciar(Tipo tipo) {
        if (claseDe(tipo, T_Iden.class))
            return refenciar(tipo.vinculo().tipo());
        else
            return tipo;
    }

    @Override
    public void procesa(Prog prog) {
        prog.decs().procesa(this);
        prog.intrs().procesa(this);
        prog.ponTipo(ambos_ok(prog.decs().tipo(), prog.intrs().tipo()));
    }

    @Override
    public void procesa(Si_Decs decs) {
        decs.ldecs().procesa(this);
        decs.ponTipo(decs.ldecs().tipo());
    }

    @Override
    public void procesa(No_Decs decs) {
        decs.ponTipo(new T_Ok());
    }

    @Override
    public void procesa(Mas_Decs decs) {
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
        decs.ponTipo(ambos_ok(decs.ldecs().tipo(), decs.dec().tipo()));
    }

    @Override
    public void procesa(Una_Dec dec) {
        dec.dec().procesa(this);
        dec.ponTipo(dec.dec().tipo());
    }

    @Override
    public void procesa(Dec_Proc dec_Proc) {
        dec_Proc.prog().procesa(this);
        dec_Proc.ponTipo(dec_Proc.prog().tipo());
    }

    @Override
    public void procesa(Si_Intrs si_Intrs) {
        si_Intrs.intrs().procesa(this);
        si_Intrs.ponTipo(si_Intrs.intrs().tipo());
    }

    @Override
    public void procesa(No_Intrs no_Intrs) {
        no_Intrs.ponTipo(new T_Ok());
    }

    @Override
    public void procesa(Mas_Intrs mas_Intrs) {
        mas_Intrs.intrs().procesa(this);
        mas_Intrs.intr().procesa(this);
        mas_Intrs.ponTipo(ambos_ok(mas_Intrs.intrs().tipo(), mas_Intrs.intr().tipo()));
    }

    @Override
    public void procesa(Una_Intr una_Intr) {
        una_Intr.intr().procesa(this);
        una_Intr.ponTipo(una_Intr.intr().tipo());
    }

    @Override
    public void procesa(I_Eval i_Eval) {
        i_Eval.exp().procesa(this);
        if (claseDe(i_Eval.exp().tipo(), T_Error.class))
            i_Eval.ponTipo(new T_Error());
        else
            i_Eval.ponTipo(new T_Ok());

    }

    @Override
    public void procesa(I_If i_If) {
        i_If.exp().procesa(this);
        if (!claseDe(i_If.exp().tipo(), T_Bool.class))
            errorProcesamientos.add(ErrorTipado.errorBooleana(i_If.exp().leeFila(), i_If.exp().leeCol()));

        i_If.prog().procesa(this);
        i_If.i_else().procesa(this);
        if (claseDe(i_If.exp().tipo(), T_Bool.class))
            i_If.ponTipo(ambos_ok(i_If.prog().tipo(), i_If.i_else().tipo()));
        else
            i_If.ponTipo(new T_Error());
    }

    @Override
    public void procesa(I_While i_While) {
        i_While.exp().procesa(this);
        if (!claseDe(i_While.exp().tipo(), T_Bool.class))
            errorProcesamientos.add(ErrorTipado.errorBooleana(i_While.exp().leeFila(), i_While.exp().leeCol()));

        i_While.prog().procesa(this);
        if (claseDe(i_While.exp().tipo(), T_Bool.class))
            i_While.ponTipo(i_While.prog().tipo());
        else
            i_While.ponTipo(new T_Error());
    }

    @Override
    public void procesa(I_Read i_Read) {
        i_Read.exp().procesa(this);
        Tipo tipoExp = i_Read.exp().tipo();
        if (!asignable(i_Read.exp())) {
            errorProcesamientos.add(ErrorTipado.errorDesignadorEsperado(i_Read.exp().leeFila(), i_Read.exp().leeCol()));
            i_Read.ponTipo(new T_Error());
        } else if ((claseDe(tipoExp, T_Int.class) || claseDe(tipoExp, T_Real.class)
                || claseDe(tipoExp, T_String.class)))
            i_Read.ponTipo(new T_Ok());
        else {
            errorProcesamientos.add(ErrorTipado.errorNoLegible(i_Read.exp().leeFila(), i_Read.exp().leeCol()));
            i_Read.ponTipo(new T_Error());
        }

    }

    @Override
    public void procesa(I_Write i_Write) {
        i_Write.exp().procesa(this);
        Tipo tipoExp = i_Write.exp().tipo();
        if (claseDe(tipoExp, T_Int.class) || claseDe(tipoExp, T_Real.class)
                || claseDe(tipoExp, T_Bool.class) || claseDe(tipoExp, T_String.class)) {

            i_Write.ponTipo(new T_Ok());
        } else {
            errorProcesamientos.add(ErrorTipado.errorNoImprimible(i_Write.exp().leeFila(), i_Write.exp().leeCol()));
            i_Write.ponTipo(new T_Error());
        }

    }

    @Override
    public void procesa(I_NL i_Nl) {
        i_Nl.ponTipo(new T_Ok());
    }

    @Override
    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
        Tipo tipoExp = i_New.exp().tipo();
        if (claseDe(tipoExp, T_Puntero.class)) {
            i_New.ponTipo(new T_Ok());
        } else {
            errorProcesamientos.add(ErrorTipado.errorTipoPuntero(i_New.exp().leeFila(), i_New.exp().leeCol()));
            i_New.ponTipo(new T_Error());
        }
    }

    @Override
    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
        Tipo tipoExp = i_Delete.exp().tipo();
        if (claseDe(tipoExp, T_Puntero.class)) {
            i_Delete.ponTipo(new T_Ok());
        } else {
            errorProcesamientos.add(ErrorTipado.errorTipoPuntero(i_Delete.exp().leeFila(), i_Delete.exp().leeCol()));
            i_Delete.ponTipo(new T_Error());
        }
    }

    @Override
    public void procesa(I_Call i_Call) {
        if (claseDe(i_Call.vinculo(), Dec_Proc.class)) {
            PForms pforms = ((Dec_Proc) i_Call.vinculo()).pforms();
            PReals preals = i_Call.preals();
            if (n_preals(preals) == n_pforms(pforms)) {
                i_Call.ponTipo(son_parametros_compatibles(pforms, preals));
            } else {
                errorProcesamientos
                        .add(ErrorTipado.errorNParamDist(i_Call.leeFila(), i_Call.leeCol()));
                i_Call.ponTipo(new T_Error());
            }
        } else {
            errorProcesamientos.add(ErrorTipado.errorNoSubprograma(i_Call.leeFila(), i_Call.leeCol(), i_Call.id()));
            i_Call.preals().procesa(this);
            i_Call.ponTipo(new T_Error());
        }
    }

    private Tipo son_parametros_compatibles(PForms pforms, PReals preals) {
        if (claseDe(pforms, No_PForms.class) && claseDe(preals, No_PReals.class)) {
            return new T_Ok();
        } else if (claseDe(pforms, Si_PForms.class) && claseDe(preals, Si_PReals.class)) {
            return son_parametros_compatibles(pforms.pforms(), preals.preals());
        } else {
            // No deberia ocurrir
            System.out
                    .println("Hay un fallo, se ha calculado que tienen el mismo tamaño, pero claramente no lo tienen");
            return new T_Error();
        }
    }

    private Tipo son_parametros_compatibles(LPForms pforms, LPReals preals) {
        if ((claseDe(pforms, Mas_PForms.class) && claseDe(preals, Un_PReal.class)) ||
                (claseDe(pforms, Una_PForm.class) && claseDe(preals, Mas_PReals.class))) {
            // No deberia ocurrir
            System.out.println(
                    "Hay un fallo, se ha calculado que tienen el mismo tamaño, pero claramente no lo tienen");
            return new T_Error();
        }
        Tipo resto = new T_Ok();
        if (claseDe(pforms, Mas_PForms.class) && claseDe(preals, Mas_PReals.class)) {
            resto = son_parametros_compatibles(pforms.pforms(), preals.preals());
        }
        return ambos_ok(resto, es_parametro_compatible(pforms.pform(), preals.exp()));
    }

    private Tipo es_parametro_compatible(PForm pform, Exp exp) {
        exp.procesa(this);
        if (claseDe(pform.ref(), Si_Ref.class) && claseDe(pform.tipo(), T_Real.class)
                && !claseDe(exp.tipo(), T_Real.class)) {
            errorProcesamientos.add(ErrorTipado.errorTipoReal(exp.leeFila(), exp.leeCol()));
            return new T_Error();
        }
        if (!Compatibilizador.compatibles(pform.tipo(), exp.tipo())) {
            errorProcesamientos.add(ErrorTipado.errorTipoIncompatiblePFormal(exp.leeFila(), exp.leeCol(),
                    pform.tipo().toString() + " " + exp.tipo()));
            return new T_Error();
        }
        if (claseDe(pform.ref(), Si_Ref.class) && !asignable(exp)) {
            errorProcesamientos.add(ErrorTipado.errorEsperabaDesignador(exp.leeFila(), exp.leeCol()));
            return new T_Error();
        }
        return new T_Ok();
    }

    private int n_pforms(PForms pforms) {
        if (claseDe(pforms, Si_PForms.class)) {
            return n_pforms(pforms.pforms());
        } else {
            return 0;
        }
    }

    private int n_pforms(LPForms pforms) {
        if (claseDe(pforms, Mas_PForms.class)) {
            return 1 + n_pforms(pforms.pforms());
        } else {
            return 1;
        }
    }

    private int n_preals(PReals preals) {
        if (claseDe(preals, Si_PReals.class)) {
            return n_preals(preals.preals());
        } else {
            return 0;
        }
    }

    private int n_preals(LPReals preals) {
        if (claseDe(preals, Mas_PReals.class)) {
            return 1 + n_preals(preals.preals());
        } else {
            return 1;
        }
    }

    @Override
    public void procesa(Si_PReals si_PReals) {
        si_PReals.preals().procesa(this);
    }

    @Override
    public void procesa(Mas_PReals mas_PReals) {
        mas_PReals.preals().procesa(this);
        mas_PReals.exp().procesa(this);
    }

    @Override
    public void procesa(Un_PReal un_PReal) {
        un_PReal.exp().procesa(this);
    }

    @Override
    public void procesa(I_Prog i_Prog) {
        i_Prog.prog().procesa(this);
        i_Prog.ponTipo(i_Prog.prog().tipo());
    }

    @Override
    public void procesa(Si_Else si_Else) {
        si_Else.prog().procesa(this);
        si_Else.ponTipo(si_Else.prog().tipo());
    }

    @Override
    public void procesa(No_Else no_Else) {
        no_Else.ponTipo(new T_Ok());
    }

    @Override
    public void procesa(Asig exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        Tipo t0 = refenciar(exp.opnd0().tipo()), t1 = refenciar(exp.opnd1().tipo());
        if (!asignable(exp.opnd0())) {
            errorProcesamientos.add(ErrorTipado.errorDesignadorIzq(exp.leeFila(), exp.leeCol()));
            exp.ponTipo(new T_Error());
        } else if (Compatibilizador.compatibles(t0, t1)) {
            exp.ponTipo(t0);
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesAsig(exp.leeFila(), exp.leeCol(),
                    exp.opnd0().tipo() == null ? "null" : exp.opnd0().tipo().toString(),
                    exp.opnd1().tipo() == null ? "null" : exp.opnd1().tipo().toString()));
            exp.ponTipo(new T_Error());
        }

    }

    private Tipo tipado_comp(Exp exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        Tipo t0 = refenciar(exp.opnd0().tipo()), t1 = refenciar(exp.opnd1().tipo());
        if (((claseDe(t0, T_Puntero.class) || claseDe(t0, T_Null.class))
                && (claseDe(t1, T_Puntero.class) || claseDe(t1, T_Null.class))) ||
                (claseDe(t0, T_String.class) && claseDe(t1, T_String.class)) ||
                (claseDe(t0, T_Bool.class) && claseDe(t1, T_Bool.class)) ||
                ((claseDe(t0, T_Int.class) || claseDe(t0, T_Real.class))
                        && (claseDe(t1, T_Int.class) || claseDe(t1, T_Real.class)))) {
            return new T_Bool();
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesOp(exp.leeFila(), exp.leeCol(),
                    t0 == null ? "null" : t0.toString(),
                    t1 == null ? "null" : t1.toString()));
            return new T_Error();
        }
    }

    private Tipo tipado_comp_ord(Exp exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        Tipo t0 = refenciar(exp.opnd0().tipo()), t1 = refenciar(exp.opnd1().tipo());
        if ((claseDe(t0, T_String.class) && claseDe(t1, T_String.class)) ||
                (claseDe(t0, T_Bool.class) && claseDe(t1, T_Bool.class)) ||
                ((claseDe(t0, T_Int.class) || claseDe(t0, T_Real.class))
                        && (claseDe(t1, T_Int.class) || claseDe(t1, T_Real.class)))) {
            return new T_Bool();
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesOp(exp.leeFila(), exp.leeCol(),
                    t0 == null ? "null" : t0.toString(),
                    t1 == null ? "null" : t1.toString()));
            return new T_Error();
        }
    }

    private Tipo tipado_arit(Exp exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        Tipo t0 = refenciar(exp.opnd0().tipo()), t1 = refenciar(exp.opnd1().tipo());
        if ((claseDe(t0, T_Int.class) || claseDe(t0, T_Real.class))
                && (claseDe(t1, T_Int.class) || claseDe(t1, T_Real.class))) {
            return (claseDe(t0, T_Real.class) || claseDe(t1, T_Real.class)) ? new T_Real() : new T_Int();
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesOp(exp.leeFila(), exp.leeCol(),
                    t0 == null ? "null" : t0.toString(),
                    t1 == null ? "null" : t1.toString()));
            return new T_Error();
        }
    }

    private Tipo tipado_and_or(Exp exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        Tipo t0 = refenciar(exp.opnd0().tipo()), t1 = refenciar(exp.opnd1().tipo());
        if (claseDe(t0, T_Bool.class) && claseDe(t1, T_Bool.class)) {
            return new T_Bool();
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesOp(exp.leeFila(), exp.leeCol(),
                    t0 == null ? "null" : t0.toString(),
                    t1 == null ? "null" : t1.toString()));
            return new T_Error();
        }
    }

    @Override
    public void procesa(Comp exp) {
        exp.ponTipo(tipado_comp(exp));
    }

    @Override
    public void procesa(Dist exp) {
        exp.ponTipo(tipado_comp(exp));
    }

    @Override
    public void procesa(Menor exp) {
        exp.ponTipo(tipado_comp_ord(exp));
    }

    @Override
    public void procesa(Mayor exp) {
        exp.ponTipo(tipado_comp_ord(exp));
    }

    @Override
    public void procesa(MenorIgual exp) {
        exp.ponTipo(tipado_comp_ord(exp));
    }

    @Override
    public void procesa(MayorIgual exp) {
        exp.ponTipo(tipado_comp_ord(exp));
    }

    @Override
    public void procesa(Suma exp) {
        exp.ponTipo(tipado_arit(exp));
    }

    @Override
    public void procesa(Resta exp) {
        exp.ponTipo(tipado_arit(exp));
    }

    @Override
    public void procesa(And exp) {
        exp.ponTipo(tipado_and_or(exp));
    }

    @Override
    public void procesa(Or exp) {
        exp.ponTipo(tipado_and_or(exp));
    }

    @Override
    public void procesa(Mul exp) {
        exp.ponTipo(tipado_arit(exp));
    }

    @Override
    public void procesa(Div exp) {
        exp.ponTipo(tipado_arit(exp));
    }

    @Override
    public void procesa(Porcentaje exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        Tipo t0 = refenciar(exp.opnd0().tipo()), t1 = refenciar(exp.opnd1().tipo());
        if (claseDe(t0, T_Int.class) && claseDe(t1, T_Int.class)) {
            exp.ponTipo(new T_Int());
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesOp(exp.leeFila(), exp.leeCol(),
                    t0 == null ? "null" : t0.toString(),
                    t1 == null ? "null" : t1.toString()));
            exp.ponTipo(new T_Error());
        }
    }

    @Override
    public void procesa(Negativo exp) {
        exp.opnd0().procesa(this);
        Tipo tipo = refenciar(exp.opnd0().tipo());
        if (claseDe(tipo, T_Int.class) || claseDe(tipo, T_Real.class)) {
            exp.ponTipo(tipo);
        } else {
            if (!claseDe(tipo, T_Error.class)) {
                errorProcesamientos.add(ErrorTipado.errorTipoIncompatibleOp(exp.leeFila(), exp.leeCol(),
                        tipo == null ? "null" : tipo.toString()));
            }
            exp.ponTipo(new T_Error());
        }
    }

    @Override
    public void procesa(Negado exp) {
        exp.opnd0().procesa(this);
        Tipo tipo = refenciar(exp.opnd0().tipo());
        if (claseDe(tipo, T_Bool.class)) {
            exp.ponTipo(tipo);
        } else {
            if (!claseDe(tipo, T_Error.class)) {
                errorProcesamientos.add(ErrorTipado.errorTipoIncompatibleOp(exp.leeFila(), exp.leeCol(),
                        tipo == null ? "null" : tipo.toString()));
            }
            exp.ponTipo(new T_Error());
        }
    }

    @Override
    public void procesa(Index exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        Tipo t0 = refenciar(exp.opnd0().tipo()), t1 = refenciar(exp.opnd1().tipo());
        if (claseDe(t0, T_Array.class) && claseDe(t1, T_Int.class)) {
            exp.ponTipo(refenciar(t0.tipo()));
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesIndx(exp.leeFila(), exp.leeCol(),
                    (t0 == null ? "null" : t0.toString()) + " " + (t1 == null ? "null" : t1.toString())));
            exp.ponTipo(new T_Error());
        }
    }

    @Override
    public void procesa(Acceso exp) {
        exp.opnd0().procesa(this);
        Tipo tipo = refenciar(exp.opnd0().tipo());
        if (claseDe(tipo, T_Struct.class)) {
            Tipo tCampo = busquedaCampo(exp.id(), tipo.camposS());
            if (claseDe(tCampo, T_Error.class)) {
                errorProcesamientos.add(ErrorTipado.errorCampoInexistente(exp.leeFila(), exp.leeCol(), exp.id()));
            }
            exp.ponTipo(tCampo);

        } else {
            if (!claseDe(tipo, T_Error.class)) {
                errorProcesamientos.add(ErrorTipado.errorAccesoNoReg(exp.leeFila(), exp.leeCol(),
                        (tipo == null ? "null" : tipo.toString()) + " " + exp + " " + exp.opnd0().tipo()));
            }
            exp.ponTipo(new T_Error());
        }
    }

    private Tipo busquedaCampo(String id, CamposS camposS) {
        if (id.equals(camposS.campoS().id())) {
            return camposS.campoS().tipo();
        } else {
            if (claseDe(camposS, Mas_Cmp_S.class)) {
                return busquedaCampo(id, camposS.camposS());
            } else {
                return new T_Error();
            }
        }
    }

    @Override
    public void procesa(Indireccion exp) {
        exp.opnd0().procesa(this);
        Tipo tipo = refenciar(exp.opnd0().tipo());
        if (claseDe(tipo, T_Puntero.class)) {
            exp.ponTipo(tipo.tipo());
        } else {
            if (!claseDe(tipo, T_Error.class)) {
                errorProcesamientos.add(ErrorTipado.errorTipoPuntero(exp.leeFila(), exp.leeCol()));
            }
            exp.ponTipo(new T_Error());
        }
    }

    @Override
    public void procesa(Lit_ent exp) {
        exp.ponTipo(new T_Int());
    }

    @Override
    public void procesa(True exp) {
        exp.ponTipo(new T_Bool());
    }

    @Override
    public void procesa(False exp) {
        exp.ponTipo(new T_Bool());
    }

    @Override
    public void procesa(Lit_real exp) {
        exp.ponTipo(new T_Real());
    }

    @Override
    public void procesa(Cadena exp) {
        exp.ponTipo(new T_String());
    }

    @Override
    public void procesa(Iden exp) {
        if (claseDe(exp.vinculo(), Dec_Var.class) || claseDe(exp.vinculo(), PForm.class)) {
            exp.ponTipo(exp.vinculo().tipo());
        } else {
            errorProcesamientos.add(ErrorTipado.errorNoVariable(exp.leeFila(), exp.leeCol(), exp.id()));
            exp.ponTipo(new T_Error());
        }
    }

    @Override
    public void procesa(Null exp) {
        exp.ponTipo(new T_Null());
    }

}
