package asint;

import asint.SintaxisAbstractaTiny.*;

public interface Procesamiento {
    void procesa(Prog prog);

    void procesa2(Prog prog);

    void procesa(Bloque bloque);

    void procesa2(Bloque bloque);

    void procesa(Si_Decs decs);

    void procesa2(Si_Decs decs);

    void procesa(No_Decs decs);

    void procesa2(No_Decs decs);

    void procesa(Mas_Decs decs);

    void procesa2(Mas_Decs decs);

    void procesa(Una_Dec dec);

    void procesa2(Una_Dec dec);

    void procesa(Dec_Var dec_Var);

    void procesa2(Dec_Var dec_Var);

    void procesa(Dec_Tipo dec_Tipo);

    void procesa2(Dec_Tipo dec_Tipo);

    void procesa(Dec_Proc dec_Proc);

    void procesa2(Dec_Proc dec_Proc);

    void procesa(Si_PForms si_PForms);

    void procesa2(Si_PForms si_PForms);

    void procesa(No_PForms no_PForms);

    void procesa2(No_PForms no_PForms);

    void procesa(Mas_PForms mas_PForm);

    void procesa2(Mas_PForms mas_PForm);

    void procesa(Una_PForm una_PForm);

    void procesa2(Una_PForm una_PForm);

    void procesa(PForm pform);

    void procesa2(PForm pform);

    void procesa(Si_Ref si_Ref);

    void procesa2(Si_Ref si_Ref);

    void procesa(No_Ref no_Ref);

    void procesa2(No_Ref no_Ref);

    void procesa(T_Iden tIden);

    void procesa2(T_Iden tIden);

    void procesa(T_String tstring);

    void procesa2(T_String tstring);

    void procesa(T_Int tint);

    void procesa2(T_Int tint);

    void procesa(T_Bool tbool);

    void procesa2(T_Bool tbool);

    void procesa(T_Real treal);

    void procesa2(T_Real treal);

    void procesa(T_Array tArray);

    void procesa2(T_Array tArray);

    void procesa(T_Puntero tPuntero);

    void procesa2(T_Puntero tPuntero);

    void procesa(T_Struct tStruct);

    void procesa2(T_Struct tStruct);

    void procesa(Mas_Cmp_S mas_Cmp_S);

    void procesa2(Mas_Cmp_S mas_Cmp_S);

    void procesa(Un_Cmp_S un_Cmp_S);

    void procesa2(Un_Cmp_S un_Cmp_S);

    void procesa(CampoS campoS);

    void procesa2(CampoS campoS);

    void procesa(Si_Intrs si_Intrs);

    void procesa2(Si_Intrs si_Intrs);

    void procesa(No_Intrs no_Intrs);

    void procesa2(No_Intrs no_Intrs);

    void procesa(Mas_Intrs mas_Intrs);

    void procesa2(Mas_Intrs mas_Intrs);

    void procesa(Una_Intr una_Intr);

    void procesa2(Una_Intr una_Intr);

    void procesa(I_Eval i_Eval);

    void procesa2(I_Eval i_Eval);

    void procesa(I_If i_If);

    void procesa2(I_If i_If);

    void procesa(I_While i_While);

    void procesa2(I_While i_While);

    void procesa(I_Read i_Read);

    void procesa2(I_Read i_Read);

    void procesa(I_Write i_Write);

    void procesa2(I_Write i_Write);

    void procesa(I_NL i_Nl);

    void procesa2(I_NL i_Nl);

    void procesa(I_New i_New);

    void procesa2(I_New i_New);

    void procesa(I_Delete i_Delete);

    void procesa2(I_Delete i_Delete);

    void procesa(I_Call i_Call);

    void procesa2(I_Call i_Call);

    void procesa(I_Prog i_Prog);

    void procesa2(I_Prog i_Prog);

    void procesa(Si_Else si_Else);

    void procesa2(Si_Else si_Else);

    void procesa(No_Else no_Else);

    void procesa2(No_Else no_Else);

    void procesa(Si_PReals si_PReals);

    void procesa2(Si_PReals si_PReals);

    void procesa(No_PReals no_PReals);

    void procesa2(No_PReals no_PReals);

    void procesa(Mas_PReals mas_PReal);

    void procesa2(Mas_PReals mas_PReal);

    void procesa(Un_PReal un_PReal);

    void procesa2(Un_PReal un_PReal);

    void procesa(Asig exp);

    void procesa2(Asig exp);

    void procesa(Comp exp);

    void procesa2(Comp exp);

    void procesa(Dist exp);

    void procesa2(Dist exp);

    void procesa(Menor exp);

    void procesa2(Menor exp);

    void procesa(Mayor exp);

    void procesa2(Mayor exp);

    void procesa(MenorIgual exp);

    void procesa2(MenorIgual exp);

    void procesa(MayorIgual exp);

    void procesa2(MayorIgual exp);

    void procesa(Suma exp);

    void procesa2(Suma exp);

    void procesa(Resta exp);

    void procesa2(Resta exp);

    void procesa(And exp);

    void procesa2(And exp);

    void procesa(Or exp);

    void procesa2(Or exp);

    void procesa(Mul exp);

    void procesa2(Mul exp);

    void procesa(Div exp);

    void procesa2(Div exp);

    void procesa(Porcentaje exp);

    void procesa2(Porcentaje exp);

    void procesa(Negativo exp);

    void procesa2(Negativo exp);

    void procesa(Negado exp);

    void procesa2(Negado exp);

    void procesa(Index exp);

    void procesa2(Index exp);

    void procesa(Acceso exp);

    void procesa2(Acceso exp);

    void procesa(Indireccion exp);

    void procesa2(Indireccion exp);

    void procesa(Lit_ent exp);

    void procesa2(Lit_ent exp);

    void procesa(True exp);

    void procesa2(True exp);

    void procesa(False exp);

    void procesa2(False exp);

    void procesa(Lit_real exp);

    void procesa2(Lit_real exp);

    void procesa(Cadena exp);

    void procesa2(Cadena exp);

    void procesa(Iden exp);

    void procesa2(Iden exp);

    void procesa(Null exp);

    void procesa2(Null exp);
}
