package tipadoPost;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import errores_procesamiento.ErrorProcesamiento;
import errores_procesamiento.ErrorTipado;

public class TipadoV2 extends ProcesamientoDef {

    public TipadoV2 tipar(Prog prog) {
        prog.procesa(this);
        return this;
    }

    private static class Compatibilizador {

        private static Set<ParTipos> yaCompatibles;

        private static class ParTipos {

            @SuppressWarnings("unused")
            int col0, col1, fila0, fila1;

            protected ParTipos(Tipo t0, Tipo t1) {
                col0 = t0.leeCol();
                col0 = t0.leeFila();
                col1 = t1.leeCol();
                col1 = t1.leeFila();
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
            return tipo.vinculo().tipo();
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
        if ((claseDe(tipoExp, T_Int.class) || claseDe(tipoExp, T_Real.class) || claseDe(tipoExp, T_String.class))
                && asignable(i_Read.exp()))
            i_Read.ponTipo(new T_Ok());
        else
            i_Read.ponTipo(new T_Error());

    }

    @Override
    public void procesa(I_Write i_Write) {
        i_Write.exp().procesa(this);
        Tipo tipoExp = i_Write.exp().tipo();
        if (claseDe(tipoExp, T_Int.class) || claseDe(tipoExp, T_Real.class)
                || claseDe(tipoExp, T_Bool.class) || claseDe(tipoExp, T_String.class))
            i_Write.ponTipo(new T_Ok());
        else
            i_Write.ponTipo(new T_Error());

    }

    @Override
    public void procesa(I_NL i_Nl) {
        i_Nl.ponTipo(new T_Ok());
    }

    @Override
    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
        Tipo tipoExp = i_New.exp().tipo();
        if (claseDe(tipoExp, T_Puntero.class))
            i_New.ponTipo(new T_Ok());
        else
            i_New.ponTipo(new T_Error());
    }

    @Override
    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
        Tipo tipoExp = i_Delete.exp().tipo();
        if (claseDe(tipoExp, T_Puntero.class))
            i_Delete.ponTipo(new T_Ok());
        else
            i_Delete.ponTipo(new T_Error());
    }

    @Override
    public void procesa(I_Call i_Call) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
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
