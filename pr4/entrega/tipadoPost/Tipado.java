package tipadoPost;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class Tipado extends ProcesamientoDef {

    private TipoBase ambos_ok(TipoBase t0, TipoBase t1) {
        if (t0 != TipoBase.OK && t1 != TipoBase.OK) {
            return TipoBase.OK;
        } else {
            return TipoBase.ERROR;
        }
    }

    private void aviso_error(TipoBase t0, TipoBase t1) {
        if (t0 != TipoBase.ERROR && t1 != TipoBase.ERROR) {
            // TODO Almacenar error por tipos incompatibles
        }
    }

    @Override
    public void procesa(Prog prog) {
        prog.decs().procesa(this);
        prog.intrs().procesa(this);
        prog.ponTipoBase(ambos_ok(prog.decs().tipoNodo(), prog.intrs().tipoNodo()));
    }

    @Override
    public void procesa(Si_Decs decs) {
        decs.ldecs().procesa(this);
        decs.ponTipoBase(decs.ldecs().tipoNodo());
    }

    @Override
    public void procesa(No_Decs decs) {
        decs.ponTipoBase(TipoBase.OK);
    }

    @Override
    public void procesa(Mas_Decs decs) {
        decs.ldecs().procesa(this);
        decs.dec().procesa(this);
        if (decs.ldecs().tipoNodo() == TipoBase.ERROR || decs.dec().tipoNodo() == TipoBase.ERROR) {
            decs.ponTipoBase(TipoBase.ERROR);
        }
        else {
            decs.ponTipoBase(TipoBase.OK);
        }
    }

    @Override
    public void procesa(Una_Dec dec) {
        dec.dec().procesa(this);
        if (dec.dec().tipoNodo() == TipoBase.ERROR) {
            dec.ponTipoBase(TipoBase.ERROR);
        } else {
            dec.ponTipoBase(TipoBase.OK);
        }
    }

    @Override
    public void procesa(Dec_Var dec_Var) {
        dec_Var.tipo().procesa(this);
        dec_Var.ponTipoBase(dec_Var.tipo().tipoNodo());
    }

    @Override
    public void procesa(Dec_Tipo dec_Tipo) { 
        dec_Tipo.tipo().procesa(this);
        dec_Tipo.ponTipoBase(dec_Tipo.tipo().tipoNodo());
    }

    @Override
    public void procesa(Dec_Proc dec_Proc) {
        dec_Proc.prog().procesa(this);
        dec_Proc.pforms().procesa(this);
        dec_Proc.ponTipoBase(ambos_ok(dec_Proc.prog().tipoNodo(), dec_Proc.pforms().tipoNodo()));
    }

    @Override
    public void procesa(Si_PForms si_PForms) {
        si_PForms.pforms().procesa(this);
        si_PForms.ponTipoBase(si_PForms.pforms().tipoNodo());
    }

    @Override
    public void procesa(No_PForms no_PForms) {
        no_PForms.ponTipoBase(TipoBase.OK);
    }

    @Override
    public void procesa(Mas_PForms mas_PForm) {
        mas_PForm.pforms().procesa(this);
        mas_PForm.pform().procesa(this);
        mas_PForm.ponTipoBase(ambos_ok(mas_PForm.pforms().tipoNodo(), mas_PForm.pform().tipoNodo()));
    }

    @Override
    public void procesa(Una_PForm una_PForm) {
        una_PForm.pform().procesa(this);
        una_PForm.ponTipoBase(una_PForm.pform().tipoNodo());
    }

    @Override
    public void procesa(PForm pform) {
        pform.tipo().procesa(this);
        pform.ponTipoBase(pform.tipo().tipoNodo());
    }

    @Override
    public void procesa(T_Iden tIden) {
        if (tIden.vinculo().tipoNodo() == null) {
            tIden.vinculo().procesa(this);
        }
        tIden.ponTipoBase(tIden.vinculo().tipoNodo());
    }

    @Override
    public void procesa(T_String tstring) {
        tstring.ponTipoBase(TipoBase.STRING);
    }

    @Override
    public void procesa(T_Int tint) {
        tint.ponTipoBase(TipoBase.INT);
    }

    @Override
    public void procesa(T_Bool tbool) {
        tbool.ponTipoBase(TipoBase.BOOL);
    }

    @Override
    public void procesa(T_Real treal) {
        treal.ponTipoBase(TipoBase.REAL);
    }

    @Override
    public void procesa(T_Array tArray) {
        tArray.tipo().procesa(this);
        TipoBase tipo = TipoBase.ARRAY;
        tipo.setBase(tArray.tipo().tipoNodo());
        tArray.ponTipoBase(tipo);
    }

    @Override
    public void procesa(T_Puntero tPuntero) {
        tPuntero.tipo().procesa(this);
        TipoBase tipo = TipoBase.PUNT;
        tipo.setBase(tPuntero.tipo().tipoNodo());
        tPuntero.ponTipoBase(tipo);
    }

    @Override
    public void procesa(T_Struct tStruct) {
        tStruct.camposS().procesa(this);
        tStruct.ponTipoBase(tStruct.camposS().tipoNodo());
    }

    @Override
    public void procesa(Mas_Cmp_S mas_Cmp_S) {
        mas_Cmp_S.camposS().procesa(this);
        mas_Cmp_S.campoS().procesa(this);
        mas_Cmp_S.ponTipoBase(ambos_ok(mas_Cmp_S.camposS().tipoNodo(), mas_Cmp_S.campoS().tipoNodo()));
    }

    @Override
    public void procesa(Un_Cmp_S un_Cmp_S) {
        un_Cmp_S.campoS().procesa(this);
        un_Cmp_S.ponTipoBase(un_Cmp_S.campoS().tipoNodo());
    }

    @Override
    public void procesa(CampoS campoS) {
        campoS.tipo().procesa(this);
        campoS.ponTipoBase(campoS.tipo().tipoNodo());
    }

    @Override
    public void procesa(Si_Intrs si_Intrs) {
        si_Intrs.intrs().procesa(this);
        si_Intrs.ponTipoBase(si_Intrs.intrs().tipoNodo());
    }

    @Override
    public void procesa(No_Intrs no_Intrs) {
        no_Intrs.ponTipoBase(TipoBase.OK);
    }

    @Override
    public void procesa(Mas_Intrs mas_Intrs) {
        mas_Intrs.intrs().procesa(this);
        mas_Intrs.intr().procesa(this);
        mas_Intrs.ponTipoBase(ambos_ok(mas_Intrs.intrs().tipoNodo(), mas_Intrs.intr().tipoNodo()));
    }

    @Override
    public void procesa(Una_Intr una_Intr) {
        una_Intr.intr().procesa(this);
        una_Intr.ponTipoBase(una_Intr.intr().tipoNodo());
    }

    @Override
    public void procesa(I_Eval i_Eval) {
        i_Eval.exp().procesa(this);
        i_Eval.ponTipoBase(i_Eval.exp().tipoNodo() != TipoBase.ERROR ? TipoBase.OK : TipoBase.ERROR);
    }

    @Override
    public void procesa(I_If i_If) {
        i_If.exp().procesa(this);
        i_If.prog().procesa(this);
        i_If.i_else().procesa(this);
        if (i_If.exp().tipoNodo() != TipoBase.BOOL) {
            // TODO Almacenar error por tipo de expresion no booleana
            i_If.ponTipoBase(TipoBase.ERROR);
        } else {
            i_If.ponTipoBase(ambos_ok(i_If.i_else().tipoNodo(), i_If.prog().tipoNodo()));
        }
    }

    @Override
    public void procesa(I_While i_While) {
        i_While.exp().procesa(this);
        i_While.prog().procesa(this);
        if (i_While.exp().tipoNodo() != TipoBase.BOOL) {
            // TODO Almacenar error por tipo de expresion no booleana
            i_While.ponTipoBase(TipoBase.ERROR);
        } else {
            i_While.ponTipoBase(i_While.prog().tipoNodo());
        }
    }

    @Override
    public void procesa(I_Read i_Read) {
        i_Read.exp().procesa(this);
        TipoBase tipo = i_Read.exp().tipoNodo();
        if (claseDe(i_Read.exp(), Iden.class)) {
            // TODO Almacenar error porque solo se puede leer en una variable
            i_Read.ponTipoBase(TipoBase.ERROR);
        } else if (tipo != TipoBase.INT && tipo != TipoBase.REAL && tipo != TipoBase.STRING) {
            // TODO Almacenar error de que solo se puede leer un en una variable de tipo
            // entero, real o string
            i_Read.ponTipoBase(TipoBase.ERROR);
        } else {
            i_Read.ponTipoBase(TipoBase.OK);
        }
    }

    @Override
    public void procesa(I_Write i_Write) {
        i_Write.exp().procesa(this);
        TipoBase tipo = i_Write.exp().tipoNodo();
        if (tipo != TipoBase.INT && tipo != TipoBase.REAL && tipo != TipoBase.STRING) {
            // TODO Almacenar error de que solo se puede leer un en una variable de tipo
            // entero, real o string
            i_Write.ponTipoBase(TipoBase.ERROR);
        } else {
            i_Write.ponTipoBase(TipoBase.OK);
        }
    }

    @Override
    public void procesa(I_NL i_Nl) {
        i_Nl.ponTipoBase(TipoBase.OK);
    }

    @Override
    public void procesa(I_New i_New) {
        i_New.exp().procesa(this);
        if (i_New.exp().tipoNodo() != TipoBase.PUNT) {
            // TODO Almacenar error de que solo se puede leer un en una variable de tipo
            // entero, real o string
            i_New.ponTipoBase(TipoBase.ERROR);
        } else {
            i_New.ponTipoBase(TipoBase.OK);
        }
    }

    @Override
    public void procesa(I_Delete i_Delete) {
        i_Delete.exp().procesa(this);
        if (i_Delete.exp().tipoNodo() != TipoBase.PUNT) {
            // TODO Almacenar error de que solo se puede leer un en una variable de tipo
            // entero, real o string
            i_Delete.ponTipoBase(TipoBase.ERROR);
        } else {
            i_Delete.ponTipoBase(TipoBase.OK);
        }
    }

    @Override
    public void procesa(I_Call i_Call) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesa'");
    }

    @Override
    public void procesa(I_Prog i_Prog) {
        i_Prog.prog().procesa(this);
        i_Prog.ponTipoBase(i_Prog.prog().tipoNodo());
    }

    @Override
    public void procesa(Si_Else si_Else) {
        si_Else.prog().procesa(this);
        si_Else.ponTipoBase(si_Else.prog().tipoNodo());
    }

    @Override
    public void procesa(No_Else no_Else) {
        no_Else.ponTipoBase(TipoBase.OK);
    }

    private boolean asignable(Nodo exp) {
        return (claseDe(exp, Iden.class) || claseDe(exp, Acceso.class) || claseDe(exp, Index.class) || claseDe(exp, Indireccion.class));
    }

    @Override
    public void procesa(Asig exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        if (!asignable(exp.opnd0())) {
            // TODO Almacenar error de que solo se puede asignar a una variable
            exp.ponTipoBase(TipoBase.ERROR);
        } else if (TipoBase.compatibles(exp.opnd0().tipoNodo(), exp.opnd1().tipoNodo())) {
            exp.ponTipoBase(TipoBase.OK);
        }
        else {
            aviso_error(exp.opnd0().tipoNodo(), exp.opnd1().tipoNodo());
            exp.ponTipoBase(TipoBase.ERROR);
        }
    }

    private TipoBase tipado_comp(Exp opnd0, Exp opnd1) {
        opnd0.procesa(this);
        opnd1.procesa(this);
        TipoBase t0 = opnd0.tipoNodo(), t1 = opnd1.tipoNodo();
        if (((t0 == TipoBase.PUNT || t0 == TipoBase.NULL) && (t1 == TipoBase.PUNT || t1 == TipoBase.NULL)) || (t0 == TipoBase.STRING && t1 == TipoBase.STRING) || (t0 == TipoBase.BOOL && t1 == TipoBase.BOOL) || ((t0 == TipoBase.INT || t0 == TipoBase.REAL) && (t1 == TipoBase.INT || t1 == TipoBase.REAL))) {
            return TipoBase.OK;
        }
        else {
            aviso_error(opnd0.tipoNodo(), opnd1.tipoNodo());
        }
        return TipoBase.ERROR;
    }

    private TipoBase tipado_comp_ord(Exp opnd0, Exp opnd1) {
        opnd0.procesa(this);
        opnd1.procesa(this);
        TipoBase t0 = opnd0.tipoNodo(), t1 = opnd1.tipoNodo();
        if ((t0 == TipoBase.STRING && t1 == TipoBase.STRING) || (t0 == TipoBase.BOOL && t1 == TipoBase.BOOL) || ((t0 == TipoBase.INT || t0 == TipoBase.REAL) && (t1 == TipoBase.INT || t1 == TipoBase.REAL))) {
            return TipoBase.OK;
        }
        else {
            aviso_error(opnd0.tipoNodo(), opnd1.tipoNodo());
        }
        return TipoBase.ERROR;
    }

    private TipoBase tipado_arit(Exp opnd0, Exp opnd1) {
        opnd0.procesa(this);
        opnd1.procesa(this);
        TipoBase t0 = opnd0.tipoNodo(), t1 = opnd1.tipoNodo();
        if (t0 == TipoBase.BOOL && t1 == TipoBase.BOOL) {
            return TipoBase.BOOL;
        }
        else {
            aviso_error(opnd0.tipoNodo(), opnd1.tipoNodo());
        }
        return TipoBase.ERROR;
    }

    private TipoBase tipado_and_or(Exp opnd0, Exp opnd1) {
        opnd0.procesa(this);
        opnd1.procesa(this);
        TipoBase t0 = opnd0.tipoNodo(), t1 = opnd1.tipoNodo();
        if ((t0 == TipoBase.INT || t0 == TipoBase.REAL) && (t1 == TipoBase.INT || t1 == TipoBase.REAL)) {
            return (t0 == TipoBase.REAL || t1 == TipoBase.REAL) ? TipoBase.REAL : TipoBase.INT;
        }
        else {
            aviso_error(opnd0.tipoNodo(), opnd1.tipoNodo());
        }
        return TipoBase.ERROR;
    }

    @Override
    public void procesa(Comp exp) {
        TipoBase tipo = tipado_comp(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(Dist exp) {
        TipoBase tipo = tipado_comp(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(Menor exp) {
        TipoBase tipo = tipado_comp_ord(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(Mayor exp) {
        TipoBase tipo = tipado_comp_ord(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(MenorIgual exp) {
        TipoBase tipo = tipado_comp_ord(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(MayorIgual exp) {
        TipoBase tipo = tipado_comp_ord(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(Suma exp) {
        TipoBase tipo = tipado_arit(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(Resta exp) {
        TipoBase tipo = tipado_arit(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(And exp) {
        TipoBase tipo = tipado_and_or(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(Or exp) {
        TipoBase tipo = tipado_and_or(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(Mul exp) {
        TipoBase tipo = tipado_arit(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(Div exp) {
        TipoBase tipo = tipado_arit(exp.opnd0(), exp.opnd1());
        exp.ponTipoBase(tipo);
    }

    @Override
    public void procesa(Porcentaje exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        TipoBase t0 = exp.opnd0().tipoNodo(), t1 = exp.opnd1().tipoNodo();
        if (t0 == TipoBase.INT && t1 == TipoBase.INT) {
            exp.ponTipoBase(TipoBase.INT);
        }
        else {
            aviso_error(exp.opnd0().tipoNodo(), exp.opnd1().tipoNodo());
            exp.ponTipoBase(TipoBase.ERROR);
        }
    }

    @Override
    public void procesa(Negativo exp) {
        exp.opnd0().procesa(this);
        TipoBase tipo = exp.opnd0().tipoNodo();
        if (tipo == TipoBase.INT || tipo == TipoBase.REAL) {
            exp.ponTipoBase(tipo);
        }
        else {
            if (tipo != TipoBase.ERROR) {
                // TODO Almacenar error por tipo de expresion no numerica
            }
            exp.ponTipoBase(TipoBase.ERROR);
        }
    }

    @Override
    public void procesa(Negado exp) {
        exp.opnd0().procesa(this);
        TipoBase tipo = exp.opnd0().tipoNodo();
        if (tipo == TipoBase.BOOL) {
            exp.ponTipoBase(tipo);
        }
        else {
            if (tipo != TipoBase.ERROR) {
                // TODO Almacenar error por tipo de expresion no booleana
            }
            exp.ponTipoBase(TipoBase.ERROR);
        }
    }

    @Override
    public void procesa(Index exp) {
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
        TipoBase t0 = exp.opnd0().tipoNodo(), t1 = exp.opnd1().tipoNodo();
        
        if (t0 == TipoBase.ARRAY && t1 == TipoBase.INT) {
            exp.ponTipoBase(t0.getBase());
        }
        else {
            if (t0 != TipoBase.ERROR && t1 != TipoBase.ERROR) {
                // TODO Almacenar error
            }
            exp.ponTipoBase(TipoBase.ERROR);
        }
    }

    @Override
    public void procesa(Acceso exp) {
        exp.opnd0().procesa(this);
        TipoBase tipo = exp.opnd0().tipoNodo();
        
        if (tipo == TipoBase.STRUCT) {
            exp.ponTipoBase(tipo.busquedaCampo(exp.id()));
        }
        else {
            if (tipo != TipoBase.ERROR) {
                // TODO Almacenar error
            }
            exp.ponTipoBase(TipoBase.ERROR);
        }
    }

    @Override
    public void procesa(Indireccion exp) {
        exp.opnd0().procesa(this);
        TipoBase tipo = exp.opnd0().tipoNodo();
        if (tipo == TipoBase.PUNT) {
            exp.ponTipoBase(tipo.getBase());
        }
        else {
            if (tipo != TipoBase.ERROR) {
                // TODO Almacenar error por tipo de expresion no puntero
            }
            exp.ponTipoBase(TipoBase.ERROR);
        }
    }

    @Override
    public void procesa(Lit_ent exp) {
        exp.ponTipoBase(TipoBase.INT);
    }

    @Override
    public void procesa(True exp) {
        exp.ponTipoBase(TipoBase.BOOL);
    }

    @Override
    public void procesa(False exp) {
        exp.ponTipoBase(TipoBase.BOOL);
    }

    @Override
    public void procesa(Lit_real exp) {
        exp.ponTipoBase(TipoBase.REAL);
    }

    @Override
    public void procesa(Cadena exp) {
        exp.ponTipoBase(TipoBase.STRING);
    }

    @Override
    public void procesa(Iden exp) {
        if (claseDe(exp.vinculo(), Dec_Var.class)) {
            exp.ponTipoBase(exp.vinculo().tipoNodo());
        }
        else {
            // TODO tiene que ser una variable declarada
            exp.ponTipoBase(TipoBase.ERROR);
        }
    }

    @Override
    public void procesa(Null exp) {
        exp.ponTipoBase(TipoBase.NULL);
    }

}
