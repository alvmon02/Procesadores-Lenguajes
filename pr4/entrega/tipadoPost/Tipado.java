package tipadoPost;

import java.util.Collection;

import java.util.List;
import java.util.ArrayList;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import asint.TiposBase.*;
import errores_procesamiento.*;

public class Tipado extends ProcesamientoDef {

    public Tipado tipar(Prog prog) {
        prog.procesa(this);
        return this;
    }

    private List<ErrorProcesamiento> errorProcesamientos = new ArrayList<>();

    public boolean hayErrores() {
        return errorProcesamientos.size() > 0;
    }

    public Collection<ErrorProcesamiento> errores() {
        return errorProcesamientos;
    }

    private TipoNodo ambos_ok(TipoNodo t0, TipoNodo t1) {
        if (t0.isOk() && t1.isOk()) {
            return new tOk();
        } else {
            return new tError();
        }
    }

    private void aviso_error(TipoNodo t0, TipoNodo t1, ErrorTipado error) {
        if (!t0.isError() && !t1.isError()) {
            errorProcesamientos.add(error);
        }
    }

    @Override
    public void procesa(Prog prog) {
        prog.decs().procesa(this);
        prog.intrs().procesa(this);
        prog.ponTipoNodo(ambos_ok(prog.decs().tipoNodo(), prog.intrs().tipoNodo()));
    }

    @Override
    public void procesa(Si_Decs decs) {
        decs.ldecs().procesa(this);
        decs.ponTipoNodo(decs.ldecs().tipoNodo());
    }

    @Override
    public void procesa(No_Decs decs) {
        decs.ponTipoNodo(new tOk());
    }

    @Override
    public void procesa(Mas_Decs decs) {
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
        if (decs.ldecs().tipoNodo().isError() || decs.dec().tipoNodo().isError()) {
            decs.ponTipoNodo(new tError());
        } else {
            decs.ponTipoNodo(new tOk());
        }
    }

    @Override
    public void procesa(Una_Dec dec) {
        dec.dec().procesa(this);
        if (dec.dec().tipoNodo().isError()) {
            dec.ponTipoNodo(new tError());
        } else {
            dec.ponTipoNodo(new tOk());
        }
    }

    @Override
    public void procesa(Dec_Var dec_Var) {
        dec_Var.tipo().procesa(this);
        dec_Var.ponTipoNodo(dec_Var.tipo().tipoNodo());
    }

    @Override
    public void procesa(Dec_Tipo dec_Tipo) {
        dec_Tipo.tipo().procesa(this);
        dec_Tipo.ponTipoNodo(dec_Tipo.tipo().tipoNodo());
    }

    @Override
    public void procesa(Dec_Proc dec_Proc) {
        dec_Proc.pforms().procesa(this);
        // dec_Proc.prog().procesa(this);
        dec_Proc.prog().ponTipoNodo(new tOk());
        dec_Proc.ponTipoNodo(ambos_ok(dec_Proc.prog().tipoNodo(), dec_Proc.pforms().tipoNodo()));
    }

    @Override
    public void procesa(Si_PForms si_PForms) {
        si_PForms.pforms().procesa(this);
        si_PForms.ponTipoNodo(si_PForms.pforms().tipoNodo());
    }

    @Override
    public void procesa(No_PForms no_PForms) {
        no_PForms.ponTipoNodo(new tOk());
    }

    @Override
    public void procesa(Mas_PForms mas_PForm) {
        mas_PForm.pforms().procesa(this);
        mas_PForm.pform().procesa(this);

        if (mas_PForm.pforms().tipoNodo().isError() || mas_PForm.pform().tipoNodo().isError())
            mas_PForm.ponTipoNodo(new tError());
        else
            mas_PForm.ponTipoNodo(new tOk());
    }

    @Override
    public void procesa(Una_PForm una_PForm) {
        una_PForm.pform().procesa(this);
        if (una_PForm.pform().tipoNodo().isError())
            una_PForm.ponTipoNodo(new tError());
        else
            una_PForm.ponTipoNodo(new tOk());
    }

    @Override
    public void procesa(PForm pform) {
        pform.tipo().procesa(this);
        pform.ponTipoNodo(pform.tipo().tipoNodo());
    }

    @Override
    public void procesa(T_Iden tIden) {
        if (tIden.vinculo().tipoNodo() == null) {
            tIden.vinculo().procesa(this);
        }
        tIden.ponTipoNodo(tIden.vinculo().tipoNodo());
    }

    @Override
    public void procesa(T_String tstring) {
        tstring.ponTipoNodo(new tCadena());
    }

    @Override
    public void procesa(T_Int tint) {
        tint.ponTipoNodo(new tInt());
    }

    @Override
    public void procesa(T_Bool tbool) {
        tbool.ponTipoNodo(new tBool());
    }

    @Override
    public void procesa(T_Real treal) {
        treal.ponTipoNodo(new tReal());
    }

    @Override
    public void procesa(T_Array tArray) {
        tArray.tipo().procesa(this);
        TipoNodo tipo = new tArray(Integer.parseInt(tArray.ent()));
        tipo.setBase(tArray.tipo().tipoNodo());
        tArray.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(T_Puntero tPuntero) {
        tPuntero.tipo().procesa(this);
        TipoNodo tipo = new tPunt();
        tipo.setBase(tPuntero.tipo().tipoNodo());
        tPuntero.ponTipoNodo(tipo);
    }

    private TipoNodo addCampoStruct(TipoNodo struct, TipoNodo campo, String idCampo) {
        if (struct.isStruct() && !campo.isError())
            return struct.addBase(idCampo, campo);
        else
            return new tError();
    }

    @Override
    public void procesa(T_Struct tStruct) {
        tStruct.camposS().procesa(this);
        tStruct.ponTipoNodo(tStruct.camposS().tipoNodo());
    }

    @Override
    public void procesa(Mas_Cmp_S mas_Cmp_S) {
        mas_Cmp_S.camposS().procesa(this);
        mas_Cmp_S.campoS().procesa(this);
        mas_Cmp_S.ponTipoNodo(
                addCampoStruct(mas_Cmp_S.camposS().tipoNodo(), mas_Cmp_S.campoS().tipoNodo(), mas_Cmp_S.campoS().id()));
    }

    @Override
    public void procesa(Un_Cmp_S un_Cmp_S) {
        un_Cmp_S.campoS().procesa(this);
        un_Cmp_S.ponTipoNodo(addCampoStruct(new tStruct(), un_Cmp_S.campoS().tipoNodo(), un_Cmp_S.campoS().id()));
    }

    @Override
    public void procesa(CampoS campoS) {
        campoS.tipo().procesa(this);
        campoS.ponTipoNodo(campoS.tipo().tipoNodo());
    }

    @Override
    public void procesa(Si_Intrs si_Intrs) {
        si_Intrs.intrs().procesa(this);
        si_Intrs.ponTipoNodo(si_Intrs.intrs().tipoNodo());
    }

    @Override
    public void procesa(No_Intrs no_Intrs) {
        no_Intrs.ponTipoNodo(new tOk());
    }

    @Override
    public void procesa(Mas_Intrs mas_Intrs) {
        mas_Intrs.intrs().procesa(this);
        mas_Intrs.intr().procesa(this);
        mas_Intrs.ponTipoNodo(ambos_ok(mas_Intrs.intrs().tipoNodo(), mas_Intrs.intr().tipoNodo()));
    }

    @Override
    public void procesa(Una_Intr una_Intr) {
        una_Intr.intr().procesa(this);
        una_Intr.ponTipoNodo(una_Intr.intr().tipoNodo());
    }

    @Override
    public void procesa(I_Eval i_Eval) {
        i_Eval.exp().procesa(this);
        i_Eval.ponTipoNodo(i_Eval.exp().tipoNodo().isError() ? new tError() : new tOk());
    }

    @Override
    public void procesa(I_If i_If) {
        i_If.exp().procesa(this);
        boolean condicionBienFormada = i_If.exp().tipoNodo().isBool();

        if (!condicionBienFormada)
            errorProcesamientos.add(ErrorTipado.errorBooleana(i_If.exp().leeFila(), i_If.exp().leeCol()));

        i_If.prog().procesa(this);
        i_If.i_else().procesa(this);
        if (condicionBienFormada) {
            i_If.ponTipoNodo(ambos_ok(i_If.i_else().tipoNodo(), i_If.prog().tipoNodo()));
        } else {
            i_If.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(I_While i_While) {
        i_While.exp().procesa(this);
        boolean condicionBienFormada = i_While.exp().tipoNodo().isBool();

        if (!condicionBienFormada)
            errorProcesamientos.add(ErrorTipado.errorBooleana(i_While.exp().leeFila(), i_While.exp().leeCol()));

        i_While.prog().procesa(this);
        if (condicionBienFormada) {
            i_While.ponTipoNodo(i_While.prog().tipoNodo());
        } else {
            i_While.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(I_Read i_Read) {
        i_Read.exp().procesa(this);
        TipoNodo tipo = i_Read.exp().tipoNodo();
        if (!asignable(i_Read.exp())) {
            errorProcesamientos.add(ErrorTipado.errorDesignadorEsperado(i_Read.exp().leeFila(), i_Read.exp().leeCol()));
            i_Read.ponTipoNodo(new tError());
        } else if (tipo.isInt() || tipo.isReal() || tipo.isCadena()) {
            i_Read.ponTipoNodo(new tOk());
        } else {
            errorProcesamientos.add(ErrorTipado.errorNoLegible(i_Read.exp().leeFila(), i_Read.exp().leeCol()));
            i_Read.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(I_Write i_Write) {
        i_Write.exp().procesa(this);
        TipoNodo tipo = i_Write.exp().tipoNodo();
        if (tipo.isInt() || tipo.isReal() || tipo.isCadena() || tipo.isBool()) {
            i_Write.ponTipoNodo(new tOk());
        } else {
            errorProcesamientos.add(ErrorTipado.errorNoImprimible(i_Write.exp().leeFila(), i_Write.exp().leeCol()));
            i_Write.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(I_NL i_Nl) {
        i_Nl.ponTipoNodo(new tOk());
    }

    @Override
    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
        if (i_New.exp().tipoNodo().isPunt()) {
            i_New.ponTipoNodo(new tOk());
        } else {
            errorProcesamientos.add(ErrorTipado.errorTipoPuntero(i_New.exp().leeFila(), i_New.exp().leeCol()));
            i_New.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
        if (i_Delete.exp().tipoNodo().isPunt()) {
            i_Delete.ponTipoNodo(new tOk());
        } else {
            errorProcesamientos.add(ErrorTipado.errorTipoPuntero(i_Delete.exp().leeFila(), i_Delete.exp().leeCol()));
            i_Delete.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(I_Call i_Call) {
        if (claseDe(i_Call.vinculo(), Dec_Proc.class)) {
            i_Call.ponTipoNodo(compatibles_preals_pforms(((Dec_Proc) i_Call.vinculo()).pforms(), i_Call.preals(),
                    i_Call.leeFila(), i_Call.leeCol()));
        } else {
            errorProcesamientos.add(ErrorTipado.errorNoSubprograma(i_Call.leeFila(), i_Call.leeCol(), i_Call.id()));
            i_Call.ponTipoNodo(new tError());
        }
    }

    private TipoNodo compatibles_preals_pforms(PForms pforms, PReals preals, int fila, int col) {
        if (claseDe(pforms, No_PForms.class) && claseDe(preals, No_PReals.class)) {
            return new tOk();
        } else if (claseDe(pforms, Si_PForms.class) && claseDe(preals, Si_PReals.class)) {
            return compatibles_preals_pforms(pforms.pforms(), preals.preals(), fila, col);
        } else {
            errorProcesamientos.add(ErrorTipado.errorNParamDist(fila, col));
            return new tError();
        }

    }

    private TipoNodo compatibles_preals_pforms(LPForms pforms, LPReals preals, int fila, int col) {
        if (claseDe(pforms, Mas_PForms.class) && claseDe(preals, Mas_PReals.class)) {
            TipoNodo t0 = compatibles_preals_pforms(pforms.pforms(), preals.preals(), fila, col);
            TipoNodo t1 = compatibles_preals_pforms(pforms.pform(), preals.exp(), fila, col);
            return ambos_ok(t0, t1);
        } else if (claseDe(pforms, Una_PForm.class) && claseDe(preals, Un_PReal.class)) {
            TipoNodo t1 = compatibles_preals_pforms(pforms.pform(), preals.exp(), fila, col);
            return t1;
        } else {
            errorProcesamientos.add(ErrorTipado.errorNParamDist(preals.leeFila(), preals.leeCol()));
            return new tError();
        }
    }

    private TipoNodo compatibles_preals_pforms(PForm pform, Exp exp, int fila, int col) {
        exp.procesa(this);
        if (claseDe(pform.ref(), Si_Ref.class) && pform.tipoNodo().isReal()
                && !exp.tipoNodo().isReal()) {
            errorProcesamientos.add(ErrorTipado.errorTipoReal(exp.leeFila(), exp.leeCol()));
            return new tError();
        }
        if (!pform.tipoNodo().compatible(exp.tipoNodo())) {
            errorProcesamientos.add(ErrorTipado.errorTipoIncompatiblePFormal(exp.leeFila(), exp.leeCol()));
            return new tError();
        }
        if (claseDe(pform.ref(), Si_Ref.class) && !asignable(exp)) {
            errorProcesamientos.add(ErrorTipado.errorEsperabaDesignador(exp.leeFila(), exp.leeCol()));
            return new tError();
        }
        return new tOk();
    }

    @Override
    public void procesa(I_Prog i_Prog) {
        i_Prog.prog().procesa(this);
        i_Prog.ponTipoNodo(i_Prog.prog().tipoNodo());
    }

    @Override
    public void procesa(Si_Else si_Else) {
        si_Else.prog().procesa(this);
        si_Else.ponTipoNodo(si_Else.prog().tipoNodo());
    }

    @Override
    public void procesa(No_Else no_Else) {
        no_Else.ponTipoNodo(new tOk());
    }

    private boolean asignable(Nodo exp) {
        return (claseDe(exp, Iden.class) || claseDe(exp, Acceso.class) || claseDe(exp, Index.class)
                || claseDe(exp, Indireccion.class));
    }

    @Override
    public void procesa(Asig exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (!asignable(exp.opnd0())) {
            errorProcesamientos.add(ErrorTipado.errorDesignadorIzq(exp.opnd0().leeFila(), exp.opnd0().leeCol()));
            exp.ponTipoNodo(new tError());
        } else if (exp.opnd0().tipoNodo().compatible(exp.opnd1().tipoNodo())) {
            exp.ponTipoNodo(new tOk());
        } else {
            aviso_error(exp.opnd0().tipoNodo(), exp.opnd1().tipoNodo(),
                    ErrorTipado.errorTiposIncompatiblesAsig(exp.leeFila(), exp.leeCol(),
                            exp.opnd0().tipoNodo() == null ? "null" : exp.opnd0().tipoNodo().toString(),
                            exp.opnd1().tipoNodo() == null ? "null" : exp.opnd1().tipoNodo().toString()));
            exp.ponTipoNodo(new tError());
        }
    }

    private TipoNodo tipado_comp(Exp exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        TipoNodo t0 = exp.opnd0().tipoNodo(), t1 = exp.opnd1().tipoNodo();
        if (((t0.isPunt() || t0.isNull()) && (t1.isPunt() || t1.isNull())) || (t0.isCadena() && t1.isCadena())
                || (t0.isBool() && t1.isBool()) || ((t0.isInt() || t0.isReal()) && (t1.isInt() || t1.isReal()))) {
            return new tBool();
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesOp(exp.leeFila(), exp.leeCol(),
                    t0 == null ? "null" : t0.toString(),
                    t1 == null ? "null" : t1.toString()));
        }
        return new tError();
    }

    private TipoNodo tipado_comp_ord(Exp exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        TipoNodo t0 = exp.opnd0().tipoNodo(), t1 = exp.opnd1().tipoNodo();
        if ((t0.isCadena() && t1.isCadena()) || (t0.isBool() && t1.isBool())
                || ((t0.isInt() || t0.isReal()) && (t1.isInt() || t1.isReal()))) {
            return new tBool();
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesOp(exp.leeFila(), exp.leeCol(),
                    t0 == null ? "null" : t0.toString(),
                    t1 == null ? "null" : t1.toString()));
        }
        return new tError();
    }

    private TipoNodo tipado_arit(Exp exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        TipoNodo t0 = exp.opnd0().tipoNodo(), t1 = exp.opnd1().tipoNodo();
        if ((t0.isInt() || t0.isReal()) && (t1.isInt() || t1.isReal())) {
            return (t0.isReal() || t1.isReal()) ? new tReal() : new tInt();
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesOp(exp.leeFila(), exp.leeCol(),
                    t0 == null ? "null" : t0.toString(),
                    t1 == null ? "null" : t1.toString()));
        }
        return new tError();
    }

    private TipoNodo tipado_and_or(Exp exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        TipoNodo t0 = exp.opnd0().tipoNodo(), t1 = exp.opnd1().tipoNodo();
        if (t0.isBool() && t0.isBool()) {
            return new tBool();
        } else {
            aviso_error(t0, t1, ErrorTipado.errorTiposIncompatiblesOp(exp.leeFila(), exp.leeCol(),
                    t0 == null ? "null" : t0.toString(),
                    t1 == null ? "null" : t1.toString()));
        }
        return new tError();
    }

    @Override
    public void procesa(Comp exp) {
        TipoNodo tipo = tipado_comp(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(Dist exp) {
        TipoNodo tipo = tipado_comp(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(Menor exp) {
        TipoNodo tipo = tipado_comp_ord(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(Mayor exp) {
        TipoNodo tipo = tipado_comp_ord(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(MenorIgual exp) {
        TipoNodo tipo = tipado_comp_ord(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(MayorIgual exp) {
        TipoNodo tipo = tipado_comp_ord(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(Suma exp) {
        TipoNodo tipo = tipado_arit(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(Resta exp) {
        TipoNodo tipo = tipado_arit(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(And exp) {
        TipoNodo tipo = tipado_and_or(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(Or exp) {
        TipoNodo tipo = tipado_and_or(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(Mul exp) {
        TipoNodo tipo = tipado_arit(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(Div exp) {
        TipoNodo tipo = tipado_arit(exp);
        exp.ponTipoNodo(tipo);
    }

    @Override
    public void procesa(Porcentaje exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        TipoNodo t0 = exp.opnd0().tipoNodo(), t1 = exp.opnd1().tipoNodo();
        if (t0.isInt() && t1.isInt()) {
            exp.ponTipoNodo(new tInt());
        } else {
            aviso_error(exp.opnd0().tipoNodo(), exp.opnd1().tipoNodo(),
                    ErrorTipado.errorTiposIncompatiblesOp(exp.leeFila(), exp.leeCol(),
                            exp.opnd0().tipoNodo() == null ? "null" : exp.opnd0().tipoNodo().toString(),
                            exp.opnd1().tipoNodo() == null ? "null" : exp.opnd1().tipoNodo().toString()));
            exp.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(Negativo exp) {
        exp.opnd0().procesa(this);
        TipoNodo tipo = exp.opnd0().tipoNodo();
        if (tipo.isInt() || tipo.isReal()) {
            exp.ponTipoNodo(tipo);
        } else {
            if (!tipo.isError()) {
                errorProcesamientos.add(ErrorTipado.errorTipoIncompatibleOp(exp.leeFila(), exp.leeCol(),
                        tipo == null ? "null" : tipo.toString()));
            }
            exp.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(Negado exp) {
        exp.opnd0().procesa(this);
        TipoNodo tipo = exp.opnd0().tipoNodo();
        if (tipo.isBool()) {
            exp.ponTipoNodo(tipo);
        } else {
            if (!tipo.isError()) {
                errorProcesamientos.add(ErrorTipado.errorTipoIncompatibleOp(exp.leeFila(), exp.leeCol(),
                        tipo == null ? "null" : tipo.toString()));
            }
            exp.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(Index exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        TipoNodo t0 = exp.opnd0().tipoNodo(), t1 = exp.opnd1().tipoNodo();

        if (t0.isArray() && t1.isInt()) {
            exp.ponTipoNodo(t0.base());
        } else {
            if (!t0.isError() && !t1.isError()) {
                errorProcesamientos.add(ErrorTipado.errorTiposIncompatiblesIndx(exp.leeFila(), exp.leeCol(),
                        (t0 == null ? "null" : t0.toString()) + " " + (t1 == null ? "null" : t1.toString())));
            }
            exp.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(Acceso exp) {
        exp.opnd0().procesa(this);
        TipoNodo tipo = exp.opnd0().tipoNodo();

        if (tipo.isStruct()) {
            TipoNodo tCampo = tipo.busquedaCampo(exp.id());
            if (tCampo.isError()) {
                errorProcesamientos.add(ErrorTipado.errorCampoInexistente(exp.leeFila(), exp.leeCol(), exp.id()));
            }
            exp.ponTipoNodo(tCampo);
        } else {
            if (!tipo.isError()) {
                errorProcesamientos.add(ErrorTipado.errorAccesoNoReg(exp.leeFila(), exp.leeCol(),
                        (tipo == null ? "null" : tipo.toString()) + " " + exp.opnd0().vinculo().getClass()));
            }
            exp.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(Indireccion exp) {
        exp.opnd0().procesa(this);
        TipoNodo tipo = exp.opnd0().tipoNodo();
        if (tipo.isPunt()) {
            exp.ponTipoNodo(tipo.base());
        } else {
            if (!tipo.isError()) {
                errorProcesamientos.add(ErrorTipado.errorTipoPuntero(exp.leeFila(), exp.leeCol()));
            }
            exp.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(Lit_ent exp) {
        exp.ponTipoNodo(new tInt());
    }

    @Override
    public void procesa(True exp) {
        exp.ponTipoNodo(new tBool());
    }

    @Override
    public void procesa(False exp) {
        exp.ponTipoNodo(new tBool());
    }

    @Override
    public void procesa(Lit_real exp) {
        exp.ponTipoNodo(new tReal());
    }

    @Override
    public void procesa(Cadena exp) {
        exp.ponTipoNodo(new tCadena());
    }

    @Override
    public void procesa(Iden exp) {
        if (claseDe(exp.vinculo(), Dec_Var.class) || claseDe(exp.vinculo(), PForm.class)) {
            exp.ponTipoNodo(exp.vinculo().tipoNodo());
        } else {
            errorProcesamientos.add(ErrorTipado.errorNoVariable(exp.leeFila(), exp.leeCol(), exp.id()));
            exp.ponTipoNodo(new tError());
        }
    }

    @Override
    public void procesa(Null exp) {
        exp.ponTipoNodo(new tNull());
    }
}
